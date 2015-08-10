/*
 * Created on 31 déc. 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.accor.asa.commun.utiles;

import java.net.URL;
import java.net.URLConnection;

/**
 * @author FCHIVAUX
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class StaticDocs {
  private final static String FILE_NAME_GLOBAL_CONF = "global";
  private String DOCUMENT_ROOT_URL;
  private final String AIDE_URL = "/aide";
  private final String CONTACTUS_URL = "/contactUs.htm";
  private final String HOMEVENTE_URL = "/vente/homepage.htm";
  private final String RECOMMANDATION_URL = "/tarif/recommandation";
  private final String INTRANETACCES_URL = "/tarif/intranet_acces.htm";

  public static final String MODULE_VENTE = "vente";
  public static final String MODULE_TARIF = "tarif";
  public static final String MODULE_PMS   = "pms";

  private static StaticDocs _instance = new StaticDocs();

  private StaticDocs() {
    DOCUMENT_ROOT_URL = FilesPropertiesCache.getInstance().getValue(FILE_NAME_GLOBAL_CONF,"asa.document_url");
  }
  
  public static StaticDocs getInstance() {
  	return _instance; 
  }
  
  public String getAideUrl( String module, String codeLangue ) {
  	String sUrl = "";
  	
  	if( MODULE_VENTE.equals( module ) )
  	 sUrl = checkUrl( DOCUMENT_ROOT_URL + "/" + module + AIDE_URL + "/" + codeLangue + "/index.htm" );
  	else 
  	  if( MODULE_TARIF.equals( module ) )
  	    sUrl = DOCUMENT_ROOT_URL + "/" + module + AIDE_URL + "/";
  	  else
		if( MODULE_PMS.equals( module ) ) {
		  sUrl = checkUrl( DOCUMENT_ROOT_URL + "/" + module + AIDE_URL + "/index_" + codeLangue + ".htm" );
		  if( sUrl == null )
			sUrl = checkUrl( DOCUMENT_ROOT_URL + "/" + module + AIDE_URL + "/index.htm" );
		} 
			
    return sUrl;
  }

  public String getContactUsUrl() {
    String sUrl = DOCUMENT_ROOT_URL + CONTACTUS_URL;
    return checkUrl( sUrl );
  }

  public String getHomeVenteUrl() {
	String sUrl = DOCUMENT_ROOT_URL + HOMEVENTE_URL;
	return checkUrl( sUrl );
  }

  public String getRecommandationUrl() {
	return DOCUMENT_ROOT_URL + RECOMMANDATION_URL + "/";
  }

  public String getIntranetAccesUrl() {
    String sUrl = DOCUMENT_ROOT_URL + INTRANETACCES_URL;
    return checkUrl( sUrl );
  }
  
  public String checkUrl( String url ) {
    URL urlTest;
    URLConnection conn;
	try	{
      urlTest = new URL( url );
      conn = urlTest.openConnection();
      conn.getInputStream();
	} catch( Exception e ) {
      return null;  	
  	} finally {
	  conn = null;
	}
	return url;
  }
}
