/**
 * Cette classe permet de générer un fichier PDF à partir d'une source XML
 * et d'un fichier XSL.
 *
 * @author FCHIVAUX
 */
package com.accor.asa.commun.utiles;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;

import org.apache.avalon.framework.logger.ConsoleLogger;
import org.apache.avalon.framework.logger.Logger;
import org.apache.fop.apps.Driver;
import org.apache.xalan.processor.TransformerFactoryImpl;
import org.xml.sax.InputSource;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.reporting.TemplateXsl;

public class FopTransformer {
  
	/** EXTENSION ASSOCIE AUX TRAITEMENT DES FICHIERS PDF */ 
  public static final String  FILTER_EXTENSION = "fopFilter"; 
  public static final String  PDF_EXTENSION = "pdf"; 

  private static FopTransformer _instance = null; 
  private static TransformerFactory _factory = null;
  
  private FopTransformer() {}
  
  /**
   * Recupération de l'instance du transformer deployée
   * @return  une instance de la classe FopTransformer 
   */
  public static FopTransformer getInstance() {
    if( _instance == null )
      _instance = new FopTransformer();
    return _instance;
  }

  /**
   * Recupération de l'instance du TransformerFactory deployée
   * @return  une instance de la classe TransformerFactory 
   */
  public TransformerFactory getTransformerFactory() {
    if( _factory == null) {
      initTransformerFactory();         
    }
    return _factory;  
  }

  /**
   * Récupération d'un template XSL:FO depuis le cache.
   * S'il n est pas présent, chargement depuis le file system et sauvegarde dans celui-ci.
   * @param req     HttpServletRequest fournissant les infos liées au serveur HHTP et à l'application en cours
   * @param path    Url relative d'accès au fichier XSL:FO 
   * @return        Template contenant le fichier XSL:FO
   * @throws IOException
   * @throws TransformerException
   * @throws TechnicalException
   */
  public Templates getTemplate( final HttpServletRequest req, final String path ) 
    throws IOException, TransformerException, TechnicalException {

    String xslFile = path;
    if( path.lastIndexOf( "?" ) != -1 )
    	xslFile = path.substring( 0, path.lastIndexOf( "?" ) );
    
    if( LogCommun.isTechniqueDebug() )
      LogCommun.debug( "FopTransformer", "getTemplate", "recuperation du template : " + xslFile );

    String[] params = { xslFile, null };
    TemplateXsl templ = (TemplateXsl) 
    	CacheManager.getInstance().getObjectInCache( TemplateXsl.class, params );
    
    if( templ == null ) {
      
      String url = "http://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + path.toString();
      final InputStream in = ( new URL( url ).openConnection() ).getInputStream();
      final Templates obj = this.getTransformerFactory().newTemplates( new StreamSource( in ) );
        
      if( LogCommun.isTechniqueDebug() )
        LogCommun.debug( "FopTransformer", "getTemplate", "Mise en cache du template : " + xslFile );
  
      templ = new TemplateXsl();
      templ.setTemplate( obj );
      templ.setName( xslFile );
      CacheManager.getInstance().setObjectInCache( templ );
    }
    return templ.getElements();
  }

  /**
   * Transformation d'une source XML et d'un fichier XSL:FO en un flux représentant le document PDF généré
   * @param req     HttpServletRequest fournissant les infos liées au serveur HHTP et à l'application en cours
   * @param xml     Objet Source contenant la trame XML des infos associés à l'offre 
   * @param xsl     Url relative d'accès au fichier XSL:FO
   * @return        ByteArrayOutputStream représentant le document PDF généré
   * @throws TechnicalException
   */
  public ByteArrayOutputStream renderXML( final HttpServletRequest req, final InputSource xml, final String xsl ) 
    throws TechnicalException {
    
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    try {
        
      Logger log = new ConsoleLogger( ConsoleLogger.LEVEL_DISABLED );  
      Driver driver = new Driver();
      driver.setLogger( log );
      driver.setRenderer( Driver.RENDER_PDF );

      //Setup a buffer to obtain the content length
      driver.setOutputStream( out );

      //TransformerFactory factory = this.getTransformerFactory();
      Transformer transformer = this.getTemplate( req, xsl ).newTransformer();
      Result res = new SAXResult(driver.getContentHandler());

      transformer.transform( new SAXSource( xml ), res );
        
    } catch( IOException e ) {
      LogCommun.major( "", "FopTransformer", "renderXML", "Fichier XSL introuvable : " + xsl );
      throw new TechnicalException(e);
    } catch( TransformerException e ) {
      LogCommun.major( "", "FopTransformer", "renderXML", "Erreur lors de la transformation XML-XSL : " + e );
      throw new TechnicalException(e);
    }
    return out;
  }
    
  /**
   * Récupération de l'instance du TransformerFactory qui sera utilisé pour la génération des Templates XSL 
   */
  private void initTransformerFactory() {
    if( LogCommun.isTechniqueDebug() )
      LogCommun.debug( "FopTransformer", "initTransformerFactory", "initTransformerFactory" );
    if( _factory == null) {
      synchronized( FopTransformer.class ) {
        if( _factory == null ) {
          _factory = this.getTransformerFactoryNewInstance();
        }
      }
    }
  }

  /**
   * Création de l'instance du TransformerFactory qui sera utilisé pour la génération des Templates XSL 
   */
  private TransformerFactory getTransformerFactoryNewInstance() {

    if( LogCommun.isTechniqueDebug() )
      LogCommun.debug( "FopTransformer", "getTransformerFactoryNewInstance", "getTransformerFactoryNewInstance" );
    
    TransformerFactory tf = TransformerFactoryImpl.newInstance();
    
    if( LogCommun.isTechniqueDebug() )
      LogCommun.debug( "FopTransformer", "getTransformerFactoryNewInstance", "TransformerFactory.class.getName() : " 
               + TransformerFactory.class.getName() );

    return tf;      
  }
  
}