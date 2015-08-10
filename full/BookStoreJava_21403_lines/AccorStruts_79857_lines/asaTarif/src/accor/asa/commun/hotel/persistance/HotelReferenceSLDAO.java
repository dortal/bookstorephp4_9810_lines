package com.accor.asa.commun.hotel.persistance;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.hotel.metier.sl.ListInfoHotelCachable;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.DAO;
import com.accor.asa.commun.utiles.FilesPropertiesCache;
import com.accor.asa.commun.utiles.Proprietes;
import com.accor.services.framework.enterprise.EnterpriseException;
import com.accor.services.framework.enterprise.EnterpriseFactory;
import com.accor.services.framework.enterprise.UserContext;
import com.accor.services.framework.enterprise.common.basictype.BasicType;
import com.accor.services.framework.enterprise.common.basictype.BasicTypesService;

/**
 * Title:       InfoHotelDAO.class
 * Description: Classe d'acces aux donnees de references de la recherche-hotel. 
 * Copyright:   Copyright (c) 2001
 * Company:     Accor DGSIT  
 * @author      Franck Chivaux
 * @version 1.0
 */
public class HotelReferenceSLDAO extends DAO {

  private static HotelReferenceSLDAO _instance = new HotelReferenceSLDAO();
  private UserContext ctx = null;
  
  private final String SHOP_TYPE;
  private final String TELE_TYPE;
  private final String BU_IT_ATTRACTION_TYPE;
  
  /**
   * description: Contructeur de la classe 
   */
  private HotelReferenceSLDAO() {
    ctx = new UserContext();
    String shop = "SHOP";
    String tele = "TELE";
    String type = "1";
    shop = FilesPropertiesCache.getInstance().getValue(Proprietes.PROPERTIES_FILE_NAME,Proprietes.CODE_SHOP );
    tele = FilesPropertiesCache.getInstance().getValue(Proprietes.PROPERTIES_FILE_NAME,Proprietes.CODE_TELE );
    type = FilesPropertiesCache.getInstance().getValue(Proprietes.PROPERTIES_FILE_NAME,Proprietes.CODE_BU_IT_ATTRACTION );

    SHOP_TYPE = shop;
    TELE_TYPE = tele;
    BU_IT_ATTRACTION_TYPE = type;
  }
  
  public static HotelReferenceSLDAO getInstance() {
    return _instance;
  }
  
  /**
   * description:     Retourne la liste des localisation disponibles.
   * @param contexte  Contexte de l'utilisateur
   * @return          List d'objets BasicType 
   * @throws TechnicalException
   */
  @SuppressWarnings("unchecked")
  public List<BasicType> getLocalisation( final Contexte contexte ) throws TechnicalException {
    
    	String[] params = new String[] { ListInfoHotelCachable.INFO_TYPE_LOCALISATION, 
    									 contexte.getCodeLangue() };
    	
    	ListInfoHotelCachable objInCache = (ListInfoHotelCachable) 
    		CacheManager.getInstance().getObjectInCache( ListInfoHotelCachable.class, params );

    	try {
    		if( objInCache == null ) {
    			if( LogCommun.isTechniqueDebug() ) {
    				LogCommun.debug( "HotelReferenceSLDAO", "getLocalisation", "Liste non présente ou obsolète dans le cache" );
    				LogCommun.debug( "HotelReferenceSLDAO", "getLocalisation", "Chargement de la liste dans le cache -- Key = " + params[0] + " - " + params[1] );
    			}
        
    			BasicTypesService srv = getTypeService( contexte.getCodeLangue() );
    			List<BasicType> listeLocalisation = srv.getLocalisationBasics().getBasicTypesList();
    			
    			if( listeLocalisation != null && listeLocalisation.size() > 0 ) {
    				objInCache = new ListInfoHotelCachable( listeLocalisation, params ); 
    				CacheManager.getInstance().setObjectInCache( objInCache );
    				
    				if( LogCommun.isTechniqueDebug() )
    					LogCommun.debug( "HotelReferenceSLDAO", "getLocalisation", "liste chargée");
    			
    			} else {
    				LogCommun.major( contexte.getCodeUtilisateur(), "HotelReferenceSLDAO", "getLocalisation", "Erreur lors du chargement de la liste -- liste vide" );
    				throw new TechnicalException( "Erreur lors du chargement de la liste" );
    			}
    		}
    } catch( EnterpriseException e ) {
    	LogCommun.major( contexte.getCodeUtilisateur(), "HotelReferenceSLDAO", "getLocalisation", "Echec lors de la connexion au service ServiceLayer : " + e );
    	throw new TechnicalException( e );
    } finally {
    	releaseTypeService(); 
    }
    return objInCache.getElements();
  }

  /**
   * description:     Retourne la liste des centres d'interets Business et Loisirs disponibles.
   * @param contexte  Contexte de l'utilisateur
   * @return          List d'objets BasicType 
   * @throws TechnicalException
   */
  @SuppressWarnings("unchecked")
  public List<BasicType> getCI( final Contexte contexte ) throws TechnicalException {
    
      String[] params = new String[] { ListInfoHotelCachable.INFO_TYPE_CI,contexte.getCodeLangue() };
    
      ListInfoHotelCachable objInCache = (ListInfoHotelCachable) 
    		CacheManager.getInstance().getObjectInCache( ListInfoHotelCachable.class, params );
    
      try {
    	  if( objInCache == null ) {
    		  if( LogCommun.isTechniqueDebug() ) {
    			  LogCommun.debug( "HotelReferenceSLDAO", "getCI", "Liste non présente ou obsolète dans le cache" );
    			  LogCommun.debug( "HotelReferenceSLDAO", "getCI", "Chargement de la liste dans le cache -- Key = " + params[0] +  " - " + params[1] );
    		  }
        
    		  BasicTypesService srv = getTypeService( contexte.getCodeLangue() );
    		  List<BasicType> listeCI = srv.getInterestedPointBasics().getBasicTypesList();
    		  List<BasicType> res = new ArrayList<BasicType>();
        
    		  if( listeCI != null && listeCI.size() > 0 ) {
    			  BasicType bt;
    			  for( int i=0, nbCI=listeCI.size(); i<nbCI; i++ ) {
    				  bt = listeCI.get( i ); 
    				  if( BU_IT_ATTRACTION_TYPE.equals( bt.getCode() ) )
    					  res.add( bt );
    			  }
    		  }
        
    		  if( res.size() > 0) {
    			  objInCache = new ListInfoHotelCachable( res, params );
    			  CacheManager.getInstance().setObjectInCache( objInCache );
        		
    			  if( LogCommun.isTechniqueDebug() )
    				  LogCommun.debug( "HotelReferenceSLDAO", "getCI", "liste chargée");
    		  } else {
    			  LogCommun.major( contexte.getCodeUtilisateur(), "HotelReferenceSLDAO", "getCI", "Erreur lors du chargement de la liste -- liste vide" );
    			  throw new TechnicalException( "Erreur lors du chargement de la liste" );
    		  }
    	  }
      } catch( EnterpriseException e ) {
    	  LogCommun.major( contexte.getCodeUtilisateur(), "HotelReferenceSLDAO", "getCI", "Echec lors de la connexion au service ServiceLayer : " + e );
    	  throw new TechnicalException( e );
      } finally {
    	  releaseTypeService(); 
      }
      return objInCache.getElements();
  }

  /**
   * description:     Retourne la liste des systemes de securites disponibles.
   * @param contexte  Contexte de l'utilisateur
   * @return          List d'objets BasicType 
   * @throws TechnicalException
   */
  @SuppressWarnings("unchecked")
  public List<BasicType> getSecurite( final Contexte contexte ) throws TechnicalException {

	  String[] params = new String[] { ListInfoHotelCachable.INFO_TYPE_SECURITE, contexte.getCodeLangue() };

	  ListInfoHotelCachable objInCache = (ListInfoHotelCachable) 
    		CacheManager.getInstance().getObjectInCache( ListInfoHotelCachable.class, params );
    
	  try {
		  if( objInCache == null ) {
			  if( LogCommun.isTechniqueDebug() ) {
				  LogCommun.debug( "HotelReferenceSLDAO", "getSecurite", "Liste non présente ou obsolète dans le cache" );
				  LogCommun.debug( "HotelReferenceSLDAO", "getSecurite", "Chargement de la liste dans le cache -- Key = " + params[0] + " - " + params[1] );
			  }
			  
			  BasicTypesService srv = getTypeService( contexte.getCodeLangue() );
			  List<BasicType> listeSecurite = srv.getSecurityBasics().getBasicTypesList();
			  if( listeSecurite != null && listeSecurite.size() > 0 ) {
				  objInCache = new ListInfoHotelCachable( listeSecurite, params );
				  CacheManager.getInstance().setObjectInCache( objInCache );
				  
				  if( LogCommun.isTechniqueDebug() )
					  LogCommun.debug( "HotelReferenceSLDAO", "getSecurite", "liste chargée");
			  } else {
				  LogCommun.major( contexte.getCodeUtilisateur(), "HotelReferenceSLDAO", "getSecurite", "Erreur lors du chargement de la liste -- liste vide" );
				  throw new TechnicalException( "Erreur lors du chargement de la liste" );
			  }
		  }
	  } catch( EnterpriseException e ) {
		  LogCommun.major( contexte.getCodeUtilisateur(), "HotelReferenceSLDAO", "getSecurite", "Echec lors de la connexion au service ServiceLayer : " + e );
		  throw new TechnicalException( e );
	  } finally {
		  releaseTypeService(); 
	  }
	  return objInCache.getElements();
  }

  /**
   * description:     Retourne la liste des restaurants et salles disponibles.
   * @param contexte  Contexte de l'utilisateur
   * @return          List d'objets BasicType 
   * @throws TechnicalException
   */
  @SuppressWarnings("unchecked")
  public List<BasicType> getRestaurant( final Contexte contexte ) throws TechnicalException {

	  String[] params = new String[] { ListInfoHotelCachable.INFO_TYPE_RESTAURANT, contexte.getCodeLangue() };

	  ListInfoHotelCachable objInCache = (ListInfoHotelCachable) 
    		CacheManager.getInstance().getObjectInCache( ListInfoHotelCachable.class, params );

	  try {
		  if( objInCache == null ) {
			  if( LogCommun.isTechniqueDebug() ) {
				  LogCommun.debug( "HotelReferenceSLDAO", "getRestaurant", "Liste non présente ou obsolète dans le cache" );
				  LogCommun.debug( "HotelReferenceSLDAO", "getRestaurant", "Chargement de la liste dans le cache -- Key = " + params[0] + " - " + params[1] );
			  }
			  
			  BasicTypesService srv = getTypeService( contexte.getCodeLangue() );
			  List<BasicType> listeRestaurant = new ArrayList<BasicType>();
			  listeRestaurant.addAll( srv.getRestaurantsBasics().getBasicTypesList() );
			  listeRestaurant.addAll( srv.getLoungesBasics().getBasicTypesList() );
			  
			  if( listeRestaurant != null && listeRestaurant.size() > 0 ) {
				  objInCache = new ListInfoHotelCachable( listeRestaurant, params );
				  CacheManager.getInstance().setObjectInCache( objInCache );
				  
				  if( LogCommun.isTechniqueDebug() )
					  LogCommun.debug( "HotelReferenceSLDAO", "getRestaurant", "liste chargée");
			  } else {
				  LogCommun.major( contexte.getCodeUtilisateur(), "HotelReferenceSLDAO", "getRestaurant", "Erreur lors du chargement de la liste -- liste vide" );
				  throw new TechnicalException( "Erreur lors du chargement de la liste" );
			  }
		  }
	  } catch( EnterpriseException e ) {
		  LogCommun.major( contexte.getCodeUtilisateur(), "HotelReferenceSLDAO", "getRestaurant", "Echec lors " +
				  "de la connexion au service ServiceLayer : " + e );
		  throw new TechnicalException( e );
	  } finally {
		  releaseTypeService(); 
	  }
	  return objInCache.getElements();
  }

  /**
   * description:     Retourne la liste des activites sportives disponibles.
   * @param contexte  Contexte de l'utilisateur
   * @return          List d'objets BasicType 
   * @throws TechnicalException
   */
  @SuppressWarnings("unchecked")
  public List<BasicType> getSport( final Contexte contexte ) throws TechnicalException {

	  String[] params = new String[] { ListInfoHotelCachable.INFO_TYPE_SPORT, contexte.getCodeLangue() };

	  ListInfoHotelCachable objInCache = (ListInfoHotelCachable) 
	  		CacheManager.getInstance().getObjectInCache( ListInfoHotelCachable.class, params );

	  try {
		  if( objInCache == null ) {
			  if( LogCommun.isTechniqueDebug() ) {
				  LogCommun.debug( "HotelReferenceSLDAO", "getSport", "Liste non présente ou obsolète dans le cache" );
				  LogCommun.debug( "HotelReferenceSLDAO", "getSport", "Chargement de la liste dans le cache -- Key = " + params[0] +  " - " + params[1] );
			  }
			  
			  BasicTypesService srv = getTypeService( contexte.getCodeLangue() );
			  List<BasicType> listeSport = srv.getActivityBasics().getBasicFamilyTypesList();
			  
			  if( listeSport != null && listeSport.size() > 0 ) {
				  objInCache = new ListInfoHotelCachable( listeSport, params );
				  CacheManager.getInstance().setObjectInCache( objInCache );
				  
				  if( LogCommun.isTechniqueDebug() )
					  LogCommun.debug( "HotelReferenceSLDAO", "getSport", "liste chargée");
			  } else {
				  LogCommun.major( contexte.getCodeUtilisateur(), "HotelReferenceSLDAO", "getSport", "Erreur lors du chargement de la liste -- liste vide" );
				  throw new TechnicalException( "Erreur lors du chargement de la liste" );
			  }
		  }
	  } catch( EnterpriseException e ) {
		  LogCommun.major( contexte.getCodeUtilisateur(), "HotelReferenceSLDAO", "getSport", "Echec lors de la connexion au service ServiceLayer : " + e );
		  throw new TechnicalException( e );
	  } finally {
		  releaseTypeService(); 
	  }
	  return objInCache.getElements();
  }

  /**
   * description:     Retourne la liste des services et equipements disponibles.
   * @param contexte  Contexte de l'utilisateur
   * @return          List d'objets BasicType 
   * @throws TechnicalException
   */
  @SuppressWarnings("unchecked")
  public List<BasicType> getService( final Contexte contexte ) throws TechnicalException {

	  String[] params = new String[] { ListInfoHotelCachable.INFO_TYPE_SERVICE, contexte.getCodeLangue() };

	  ListInfoHotelCachable objInCache = (ListInfoHotelCachable) 
	  		CacheManager.getInstance().getObjectInCache( ListInfoHotelCachable.class, params );

	  try {
		  if( objInCache == null ) {
			  if( LogCommun.isTechniqueDebug() ) {
				  LogCommun.debug( "HotelReferenceSLDAO", "getService", "Liste non présente ou obsolète dans le cache" );
				  LogCommun.debug( "HotelReferenceSLDAO", "getService", "Chargement de la liste dans le cache -- Key = " + params[0] + " - " + params[1] );
			  }
			  
			  BasicTypesService srv = getTypeService( contexte.getCodeLangue() );
			  List<BasicType> listeService = srv.getServicesBasics().getBasicTypesList();
			  List<BasicType> listeTV = srv.getRoomEquipmentBasics().getBasicTypesList();

			  BasicType bt;
			  List<BasicType> res = new ArrayList<BasicType>();
			  
			  if( listeService != null && listeService.size() > 0 ) {
				  for( int i=0, nbServices=listeService.size(); i<nbServices; i++ ) {
					  bt = listeService.get( i ); 
					  if( SHOP_TYPE.equals( bt.getCodeType() ) )
						  res.add( bt );
				  }
			  }
        
			  if( listeTV != null && listeTV.size() > 0 ) {
				  for( int i=0, nbRoomEquipment=listeTV.size(); i<nbRoomEquipment; i++ ) {
					  bt = listeTV.get( i );
					  if( TELE_TYPE.equals( bt.getCodeType() ) )
						  res.add( bt );
				  }
			  }  
        
			  objInCache = new ListInfoHotelCachable( res, params );
			  CacheManager.getInstance().setObjectInCache( objInCache );
			  
			  if( LogCommun.isTechniqueDebug() )
				  LogCommun.debug( "HotelReferenceSLDAO", "getService", "liste chargée");

			  if( res.size() <= 0 ) {
				  LogCommun.major( contexte.getCodeUtilisateur(), "HotelReferenceSLDAO", "getService", "Erreur lors du chargement de la liste -- liste vide" );
				  throw new TechnicalException( "Erreur lors du chargement de la liste" );
			  }          
		  }          
	  } catch( EnterpriseException e ) {
		  LogCommun.major( contexte.getCodeUtilisateur(), "HotelReferenceSLDAO", "getService", "Echec lors de la connexion au service ServiceLayer : " + e );
		  throw new TechnicalException( e );
	  } finally {
		  releaseTypeService(); 
	  }
	  return objInCache.getElements();
  }

  private BasicTypesService getTypeService( String codeLangue ) throws EnterpriseException {
    if( ctx == null || ! ctx.getLocale().getLanguage().equalsIgnoreCase( codeLangue ) ) {
      if( LogCommun.isTechniqueDebug() )
      	LogCommun.debug( "HotelReferenceSLDAO", "getTypeService", "modification du UserContext : " + codeLangue );
      
      Locale current = null;
      if( codeLangue.equals( "GB" ) ) 
        current = Locale.ENGLISH;
      else
        current = new Locale( codeLangue.toLowerCase(), "" );
      ctx.setLocale( current );
    }
    return EnterpriseFactory.connect( ctx ).getBasicTypesService();
  }

  private void releaseTypeService() {
  	EnterpriseFactory.disconnect( ctx );
  }
}
