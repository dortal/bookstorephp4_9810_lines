package  com.accor.asa.commun.hotel.persistance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.hotel.metier.descriptive.HotelPhoto;
import com.accor.asa.commun.hotel.metier.sl.AsaDescriptiveContentHotel;
import com.accor.asa.commun.hotel.metier.sl.CriteresCapacitesRechercheHotels;
import com.accor.asa.commun.hotel.metier.sl.CriteresCommercialsRechercheHotels;
import com.accor.asa.commun.hotel.metier.sl.CriteresInformatifsRechercheHotels;
import com.accor.asa.commun.hotel.metier.sl.CriteresRechercheHotels;
import com.accor.asa.commun.hotel.metier.sl.SearchHotelResult;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.AsaDate;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.DAO;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.utiles.FilesPropertiesCache;
import com.accor.asa.commun.utiles.Proprietes;
import com.accor.services.framework.enterprise.EnterpriseException;
import com.accor.services.framework.enterprise.EnterpriseFactory;
import com.accor.services.framework.enterprise.EnterpriseServiceFacade;
import com.accor.services.framework.enterprise.UserContext;
import com.accor.services.framework.enterprise.common.Label;
import com.accor.services.framework.enterprise.common.basictype.BasicType;
import com.accor.services.framework.enterprise.common.basictype.BasicTypeEvaluatorHelper;
import com.accor.services.framework.enterprise.common.brand.BrandFilter;
import com.accor.services.framework.enterprise.hotel.Hotel;
import com.accor.services.framework.enterprise.hotel.amenities.DescriptiveContentHotel;
import com.accor.services.framework.enterprise.hotel.amenities.DescriptiveContentService;
import com.accor.services.framework.enterprise.hotel.amenities.Room;
import com.accor.services.framework.enterprise.hotel.amenities.lounge.Lounge;
import com.accor.services.framework.enterprise.hotel.amenities.photo.Photo;
import com.accor.services.framework.enterprise.hotel.amenities.restaurant.Restaurant;
import com.accor.services.framework.enterprise.hotel.availability.AvailabilityHotel;
import com.accor.services.framework.enterprise.hotel.availability.AvailabilitySegment;
import com.accor.services.framework.enterprise.hotel.availability.UniquePeriodSegment;
import com.accor.services.framework.enterprise.hotel.geo.ComplementaryType;
import com.accor.services.framework.enterprise.hotel.geo.GeoHotelSearch;
import com.accor.services.framework.enterprise.hotel.geo.GeoZoneHotelSearch;
import com.accor.services.framework.enterprise.hotel.geo.GeographicalZone;
import com.accor.services.framework.enterprise.hotel.geo.GeographicalZoneService;
import com.accor.services.framework.enterprise.hotel.geo.HotelSearch;
import com.accor.services.framework.enterprise.hotel.geo.HotelSearchResult;
import com.accor.services.framework.enterprise.hotel.geo.HotelSearchService;
import com.accor.services.framework.enterprise.hotel.geo.NameHotelSearch;
import com.accor.services.framework.enterprise.hotel.geo.RidHotelSearch;
import com.accor.services.framework.enterprise.rate.ReservationCode;


/**
 * Title:       RechercheHotelDAO.class
 * Description: Classe permetant d'effectuer des recherches multicriteres( disponibilite, capacite, produits, 
 *              .. ) d'hotels via l'application mutualisée d'Accor.
 * Copyright:   Copyright (c) 2001
 * Company:     Accor DGSIT  
 * @author      Franck Chivaux
 * @version 1.0
 */
public class SearchHotelSLDAO extends DAO {

  private static SearchHotelSLDAO _instance = new SearchHotelSLDAO();

  private final ComplementaryType 	DEFAULT_COMPLEMENTARY_TYPE 	= ComplementaryType.DEFAULT_COMPLEMENTARY;
  //private final ComplementaryType CITY_COMPLEMENTARY_TYPE 	= ComplementaryType.CITY_COMPLEMENTARY_TYPE;
  private final BrandFilter 		BRAND_TYPE 					= BrandFilter.ASA;
  private final String	 			_HOTEL_MICE 				= "mice";
  private final String 				GEO_TYPE_PAYS;
  private final String 				HOTEL_PHOTO_ACCESS_TYPE 	= "a";

  public static int NB_MAX_RESULT; 

  /**
   * Constructeur de la classe
   */
  private SearchHotelSLDAO() {
    int nb = 100;
    String code = "PA";
    nb = FilesPropertiesCache.getInstance().getIntValue( Proprietes.PROPERTIES_FILE_NAME, Proprietes.TAILLE_MAX_RSET );
    code =  FilesPropertiesCache.getInstance().getValue( Proprietes.PROPERTIES_FILE_NAME, Proprietes.GEOTYPE_CODE_PAYS );
    NB_MAX_RESULT = nb;
    GEO_TYPE_PAYS = code;
  }

  public static SearchHotelSLDAO getInstance () {
    return _instance;
  }

  /**
   * description:         Effectue la recherche demandée via l'application ServiceLayer et retourne une liste 
   *                      d'hotels ou de zones gégraphiques.
   * @param listCriteres  objet CriteresRechercheHotels stockant l'ensemble des criteres saisies
   * @param contexte      contexte de l'utilisateur
   * @return              SearchHotelResult
   * @throws TechnicalException
   * @throws IncoherenceException
   */
  @SuppressWarnings("unchecked")
  public SearchHotelResult searchHotels( final CriteresRechercheHotels criterias, final Contexte contexte ) 
  		throws TechnicalException, IncoherenceException {

	  SearchHotelResult shr = new SearchHotelResult();
	  HotelSearch hs = null;
	  Label label = null;
	  BrandFilter brand = BRAND_TYPE;
	  String LOG_DAO = "";
    
	  UserContext ctx = new UserContext();
	  ctx.setLocale( getLocale( contexte.getCodeLangue() ) );
    
	  final String rid 			= criterias.getRid();
	  final String nomHotel 	= criterias.getNomHotel();
	  final String ville 		= criterias.getVille();
	  final String codeMarque 	= criterias.getCodeMarque();
	  final String codePays 	= criterias.getCodePays(); 
	  final String zoneGeoType 	= criterias.getZoneGeoType(); 
	  final String zoneGeoValue = criterias.getZoneGeoValue(); 
    
	  if( codeMarque != null && codeMarque.trim().length() >  0 )
		  brand = new BrandFilter( codeMarque );

	  if( criterias.getHotelMice() )
		  label = new Label( _HOTEL_MICE );
    
	  try{
		  if( ! StringUtils.isEmpty( rid ) && rid.length() == 4 ) {
			  LOG_DAO = "Recherche par RID -- codeRid = " + rid;
			  hs = new RidHotelSearch( rid );
		  } else {
			  if( ! StringUtils.isEmpty( nomHotel ) ) {
				  LOG_DAO = "Recherche par NOM_HOTEL -- nomHotel = " + nomHotel;
				  hs = new NameHotelSearch( DEFAULT_COMPLEMENTARY_TYPE, 
						  					brand, null, label, nomHotel, false, NB_MAX_RESULT );
			  } else { 
				  if( ! StringUtils.isEmpty( zoneGeoType ) && ( ! StringUtils.isEmpty( zoneGeoValue ) ) ) {
					  LOG_DAO = "Recherche par zone géographique -- zonegeotype = " + zoneGeoType + " - zonegeovalue" + zoneGeoValue;
					  GeographicalZoneService gzs = EnterpriseFactory.connect( ctx )
					  									.getGeographicalZoneService();
					  GeographicalZone gz = gzs.getGeographicalZone( zoneGeoType, zoneGeoValue );
					  hs = new GeoZoneHotelSearch( 	DEFAULT_COMPLEMENTARY_TYPE, 
							  						brand, null, label, gz, false, NB_MAX_RESULT );
				  } else {
					  if( ! StringUtils.isEmpty( ville ) ) {
						  LOG_DAO = "Recherche par mot-clé -- motCle = " + ville;
						  hs = new GeoHotelSearch( DEFAULT_COMPLEMENTARY_TYPE, 
								  				   brand, null, label, ville, false, NB_MAX_RESULT );
					  } else { 
						  if( ! StringUtils.isEmpty( codePays ) ) {
							  LOG_DAO = "Recherche par zone géographique -- zonegeo = " + codePays;
							  GeographicalZoneService gzs = EnterpriseFactory.connect( ctx )
							  									.getGeographicalZoneService();
							  GeographicalZone gz = gzs.getGeographicalZone( GEO_TYPE_PAYS, codePays ); // ANO 3971
							  hs = new GeoZoneHotelSearch( DEFAULT_COMPLEMENTARY_TYPE, 
									  					   brand, null, label, gz, false, NB_MAX_RESULT );
						  } else {
							  throw new IncoherenceException( "Aucun critere de recherche detecte!" );
						  }
					  }
				  }
			  }
		  }
	  
		  if( LogCommun.isTechniqueDebug() )
			  LogCommun.debug( "SearchHotelSLDAO", "searchHotels", LOG_DAO );

		  HotelSearchService hss = EnterpriseFactory.connect( ctx ).getHotelSearchService();
		  HotelSearchResult hsr = hss.searchHotels( hs );

		  shr.setType( hsr.getReturnType() );
		  if( hsr.getReturnType() == HotelSearchResult.EMPTY ) {

			  if( LogCommun.isTechniqueDebug() )
				  LogCommun.debug( "SearchHotelSLDAO", "searchHotels", " aucun hotel ou aucune zone geographique trouvé" );
			  
			  shr.setHotels( new ArrayList<Hotel>() );
		  } else {
			  if( hsr.getReturnType() == HotelSearchResult.HOTELS ) {
				  if( LogCommun.isTechniqueDebug() )
					  LogCommun.debug( "SearchHotelSLDAO", "searchHotels", hsr.getNumberHotelsFound() + " hotels trouvés" );
        
				  shr.setHotels( hsr.getHotels() );  
			  } else { 
				  if( hsr.getReturnType() == HotelSearchResult.GEOZONES ) {
					  if( LogCommun.isTechniqueDebug() )
						  LogCommun.debug( "SearchHotelSLDAO", "searchHotels", hsr.getNumberHotelsFound() + " zones geos trouvées" );

					  if( ! ( hs instanceof GeoZoneHotelSearch ) && StringUtils.isNotBlank( codePays ) ) {
						  shr.setGeoZones( getAlternativesResultsHotels( hsr, codePays ) );
					  } else {
						  shr.setGeoZones( hsr.getHotelSearchAlternatives() );
					  }
				  }
			  }
		  }
	  } catch( EnterpriseException e ) {
		  LogCommun.major( contexte.getCodeUtilisateur(), "SearchHotelSLDAO", "searchHotels", "Echec lors " +
				  "de la connexion au service ServiceLayer : " + e );
		  throw new TechnicalException( e );
	  } finally {
		  EnterpriseFactory.disconnect( ctx );
	  }
	  return shr;
  }

  /**
   * description:     Retourne la fiche descriptive d'un hotel.  
   * @param codeHotel Code tars identifiant l'hotel
   * @param contexte  Contexte de l'utilisateur
   * @return          Objet DescriptiveContentHotel contenant la fiche-hotel   
   * @throws TechnicalException
   */
  public DescriptiveContentHotel getHotel( final String rid, final Contexte contexte ) 
         throws TechnicalException {
    DescriptiveContentHotel result = null;
    
    UserContext ctx = new UserContext();
    ctx.setLocale( getLocale( contexte.getCodeLangue() ) );
    
   try {
      DescriptiveContentService hcs = EnterpriseFactory.connect( ctx ).getHotelDescriptiveContentService();
      result = hcs.getDescriptiveContent( rid );
      
     } catch( EnterpriseException e ) {
       LogCommun.major( contexte.getCodeUtilisateur(), "SearchHotelSLDAO", "getFicheHotel", "Echec lors " +
           "de la connexion au service ServiceLayer : " + e );
       throw new TechnicalException( e );
     } finally {
       EnterpriseFactory.disconnect( ctx );
     }
     return result;
   }

  /**
   * description:         	Retourne la liste des fiches descriptives des hotels repondant aux criteres selectionnes.
   * @param hotels			List d objet Hotels
   * @param criterias  		Objet CriteresRechercheHotels stockant l'ensemble des criteres selectionnes 
   * @param contexte      	Contexte de l'utilisateur
   * @return              	List d'objet AsaDescriptiveContentHotel 
   * @throws TechnicalException
   * @throws IncoherenceException
   */
  @SuppressWarnings("unchecked")
  public List<AsaDescriptiveContentHotel> getHotels( List<Hotel> hotels, 
		  final CriteresRechercheHotels criterias, final Contexte contexte ) 
		  throws TechnicalException, IncoherenceException {
      
	  if( hotels == null )
		  return null;
   
	  List<AsaDescriptiveContentHotel> result = new ArrayList<AsaDescriptiveContentHotel>();
	  int[] hotelCapacityValues = null;
	  boolean[] hotelInfoValues = null;

	  final String codeUtilisateur = contexte.getCodeUtilisateur();
   
	  final CriteresCommercialsRechercheHotels commSelected 	= criterias.getCommercial();
	  final CriteresCapacitesRechercheHotels capacitySelected 	= criterias.getCapacity();
	  final CriteresInformatifsRechercheHotels infoSelected 	= criterias.getInformative();
	  final BasicType segmentSelected 							= criterias.getSegment();
   
	  final boolean withCritSegment 	= (segmentSelected != null);
	  final boolean withCritCapacite 	= ( capacitySelected != null && capacitySelected.getCritereCount() > 0 );
	  final boolean withCritInformatif 	= ( infoSelected != null && infoSelected.getCriteresCount() > 0 );
	  final boolean withCritComm 		= ( commSelected != null );
  
	  final Object[] capacityNames = withCritCapacite?capacitySelected.getCritereName().toArray():null;

	  if( withCritComm && ( commSelected.getDateDebut() == null 
			  				|| commSelected.getDateFin()== null 
			  				|| StringUtils.isEmpty( commSelected.getCodeTarif() ) ) ) {
		  throw new IncoherenceException( "donnees manquante pour une recherche de disponibilite-hotel : " 
                                   + commSelected.getDateDebut().toString() + " - " 
                                   + commSelected.getDateFin().toString() + " - " 
                                   + commSelected.getCodeTarif() );
	  }
   
	  UserContext ctx = new UserContext();
	  ctx.setLocale( getLocale( contexte.getCodeLangue() ) );
    
	  try {
		  DescriptiveContentService hcs = EnterpriseFactory.connect( ctx )
		  									.getHotelDescriptiveContentService();

		  if( withCritComm ) {
			  hotels = getHotelsWithCommercialCriterias( hotels, commSelected, contexte );
		  }
      
		  final List<DescriptiveContentHotel> listDescHotels = hcs.getDescriptiveContents( hotels );
		  final int nbFicheHotels = listDescHotels.size();
		  Map<String,Object> mapDescHotelsWithSeg = null; 
            
		  if( withCritSegment )
			  mapDescHotelsWithSeg = DescriptiveContentHotel.transformDescriptiveContentHotelListToMap( 
                                		BasicTypeEvaluatorHelper.evaluate( 
                                				segmentSelected, listDescHotels ) 
                                	 );

		  DescriptiveContentHotel dch;
		  boolean ok,segment;
		  for( int i=0; i<nbFicheHotels; i++ ) {
			  dch = listDescHotels.get(i);

			  ok = true;

			  if( withCritCapacite ) {
				  hotelCapacityValues = haveCapacityCriterias( codeUtilisateur, capacityNames, dch, capacitySelected );
				  ok = ( hotelCapacityValues != null );
			  }
        
			  if( ok && withCritInformatif ) {
				  hotelInfoValues = haveInformativeCriterias( codeUtilisateur, dch, infoSelected );
				  ok = ( hotelInfoValues != null );
			  }
 
			  if( ok ) {
				  segment = ( withCritSegment )?( mapDescHotelsWithSeg.get( dch.getCode() ) != null ):withCritSegment;
				  result.add( new AsaDescriptiveContentHotel( dch, hotelCapacityValues, hotelInfoValues, segment ) );
			  }
		  }
  
		  if( LogCommun.isTechniqueDebug() )
			  LogCommun.debug( "SearchHotelSLDAO", "getFicheHotelsWithAsaFilter", result.size() + " hotels trouvés apres filtre ASA" );
  
	  } catch( EnterpriseException e ) {
		  LogCommun.major( contexte.getCodeUtilisateur(), "SearchHotelSLDAO", "getFicheHotelsWithAsaFilter", 
				  "Echec lors de la connexion au service ServiceLayer : " + e );
		  throw new TechnicalException( e ); 
	  } finally {
		  EnterpriseFactory.disconnect( ctx );
	  }    
	  return result;
  }
  
  /***
   * Retourne la liste des photos d'un hotel
   * @param rid
   * @param contexte
   * @return List<HotelPhoto>
   * @throws TechnicalException
   */
  @SuppressWarnings("unchecked")
public List<HotelPhoto> getPhotos( final String rid, final Contexte contexte ) 
  		throws TechnicalException {
	  
	  List<HotelPhoto> photos = new ArrayList<HotelPhoto>();
	  
	  try { 
		  DescriptiveContentHotel hotel = getHotel( rid, contexte );
		  if( hotel != null ) {
			  List<Photo> res = hotel.getPhotos();
			  if( res != null ) {
				  HotelPhoto hp;
				  for( Photo p : res ) {
					  if( StringUtils.equalsIgnoreCase( HOTEL_PHOTO_ACCESS_TYPE, String.valueOf( p.getPhotoType() ) ) ) {
						  hp = new HotelPhoto();
						  hp.setName( p.getName() );
						  hp.setUpdateDate( new AsaDate( p.getUpdateDate() ) );
						  photos.add( hp );
					  }
				  }
			  }
		  }
	  } catch( EnterpriseException e ) {
		  LogCommun.major( contexte.getCodeUtilisateur(), "SearchHotelSLDAO", "getPhotos", "Echec lors de la connexion au service ServiceLayer : " + e );
		  throw new TechnicalException( e ); 
	  }    
	  return photos;
  }
  
  /**
   * description:       retourne l'objet Locale associe au code_langue donne 
   * @param codeLangue  
   */
  private Locale getLocale( String codeLangue ) {
    Locale loc = Locale.ENGLISH;

    if( ! codeLangue.equals( "GB" ) ) 
      loc = new Locale( codeLangue.toLowerCase(), "" );

    return loc;
  }

  /**
   * description:     Permet de préciser une recherche effectuée précédemment en fonction d'un codepays. 
   * @param hss       Service d'acces au ServiceLayer
   * @param hsr       List d'objet GeographicalZone 
   * @param codePays  Codepays servant de filtre supplementaire
   * @return          List d'objet Hotel ou GeographicalZone
   * @throws EnterpriseException
   */
  @SuppressWarnings("unchecked")
  private List<GeographicalZone> getAlternativesResultsHotels( final HotelSearchResult hsr, 
		  final String codePays ) throws EnterpriseException {
	  
	  List<GeographicalZone> result = new ArrayList<GeographicalZone>();

	  List<GeographicalZone> geoZones = hsr.getHotelSearchAlternatives();
	  
      if( LogCommun.isTechniqueDebug() )
    	  LogCommun.debug( "SearchHotelSLDAO", "getAlternativesResultsHotels", "Affinage automatique de la recherche via le codePays : " + codePays );

      for( int i=0, size=geoZones.size(); i<size; i++ ) {
    	  GeographicalZone gz = hsr.getGeographicalZoneAt( i );
    	  while( gz!=null && ( ! GEO_TYPE_PAYS.equals( gz.getType() ) ) ) {
    		  gz = gz.getParent();
    	  }

    	  if( gz!=null && gz.getValue().equals( codePays ) ) {
    		  result.add( geoZones.get( i ) );
    	  }
      }
      return result;
  }

  /**
   * description:           Retourne la valeur de la capacite demandee si celle-ci est superieure au filtre 
   *                        selectionne. sinon 0.
   * @param codeUtilisateur Login de l'utilisateur
   * @param propsName       Nom de la capacite selectionnee
   * @param propsValue      Valeur de la capacite selectionnee
   * @param dch             Objet fiche-hotel
   * @return                La valeur de cette capacite dans l'hotel si elle est superieure a la valeur selectionnee.
   * @throws TechnicalException
   */
  @SuppressWarnings("unchecked")
  private int getNbRooms( final String codeUtilisateur, final String propsName, 
		  final int propsValue, final DescriptiveContentHotel dch ) throws TechnicalException {
    
	  int valueReturn = 0;
    
	  try {
		  if( CriteresCapacitesRechercheHotels._NB_TOTAL.equals( propsName ) 
				  && propsValue <= dch.getRoomNumber() ) {
			  valueReturn = dch.getRoomNumber(); 
		  } else { 
			  if( CriteresCapacitesRechercheHotels._NB_CHAMBRES_DOUBLES.equals( propsName ) ) {
				  valueReturn = getNbRooms( dch.getRooms(), 2 );
			  } else { 
				  if( CriteresCapacitesRechercheHotels._NB_CHAMBRES_TRIPLES.equals( propsName ) ) {
					  valueReturn = getNbRooms( dch.getRooms(), 3 );
				  } else { 
					  if( CriteresCapacitesRechercheHotels._NB_CHAMBRES_QUADS.equals( propsName ) ) {
						  valueReturn = getNbRooms( dch.getRooms(), 4 );
					  } else {
						  if( CriteresCapacitesRechercheHotels._NB_SALLES_REUNIONS.equals( propsName ) ) {
							  List<Lounge> listeInfos = dch.getLounges();
							  if( listeInfos != null && propsValue <= listeInfos.size() ) {
								  valueReturn = listeInfos.size();
							  }
						  } else { 
							  if( CriteresCapacitesRechercheHotels._CAPACITE_SALLE_REUNION_PAX
									  .equals( propsName ) ) {
								  
								  List<Lounge> listeInfos = dch.getLounges();
								  if( listeInfos != null ) {
									  Lounge lg;
									  for( int i=0, nbInfos=listeInfos.size(); i<nbInfos; i++ ) {
										  lg = listeInfos.get( i );
										  if( valueReturn < lg.getMaxNumberPeople() )
											  valueReturn = lg.getMaxNumberPeople();
									  }
								  } 
							  } else {
								  if( CriteresCapacitesRechercheHotels._CAPACITE_SALLE_REUNION_M2
										  	.equals( propsName ) ) {

									  List<Lounge> listeInfos = dch.getLounges();
									  if( listeInfos != null ) {
										  Lounge lg;
										  for( int i=0, nbInfos=listeInfos.size(); i<nbInfos; i++ ) {
											  lg = listeInfos.get( i );
											  if( valueReturn <= lg.getSurface() )
												  valueReturn = lg.getSurface();
										  }
									  } 
								  } else {
									  if( CriteresCapacitesRechercheHotels._NB_PLACES_RESTAURANT
											  .equals( propsName ) ) {
										  
										  List<Restaurant> listeInfos = dch.getRestaurants();
										  if( listeInfos != null ) {
											  Restaurant rt;
											  for( int i=0, nbInfos=listeInfos.size(); i<nbInfos; i++ ) {
												  rt = (Restaurant) listeInfos.get( i );
												  if( valueReturn <= rt.getPlacesNumber() )
													  valueReturn = rt.getPlacesNumber();
											  }
										  } 
									  }
								  }
							  }
						  }
					  }
				  }
			  }
		  }
	  } catch( EnterpriseException e ) {
		  LogCommun.major( codeUtilisateur, "SearchHotelSLDAO", "capacity", "Echec lors " +
				  "de la connexion au service ServiceLayer : " + e );
		  throw new TechnicalException( e ); 
	  }
    
	  if( propsValue > valueReturn ) {
		  valueReturn = 0;
	  }
	  return valueReturn;
  }

  /**
   * description:     Retourne le nombre de chambres d'une categorie donnee
   * @param rooms     List d'objets Room d'un hotel
   * @param roomSize  Categorie selectionnee ( 1pax, 2pax, 3pax... )
   * @return          Nombre de chambres correspondant
   */
  private int getNbRooms( final List<Room> rooms, final int roomSize ) {
    int nb = 0;
    if( rooms != null ) {
      int nbInfos = rooms.size();
      Room rm;
      for( int i=0; i<nbInfos; i++ ) {
        rm = rooms.get( i );
        if( rm.getMaxPax() == roomSize ) {
          nb += rm.getQuantity();  
        }
      }
    }
    return nb;
  }

  /**
   * description:             Retourne la liste des capacites d'un hotel si elles repondent à l'ensemble des 
   *                          criteres selectionnes. sinon null  
   * @param codeUtilisateur   Login de l'utilisateur
   * @param capacityNames     Tableau stockant les noms des capacites selectionnes
   * @param dch               Objet fiche-hotel 
   * @param capacitySelected  Objet CriteresCapacitesRechercheHotels stockant les valeurs des capacites selectionnes  
   * @return                  Tableau des capacites de l'hotel
   * @throws TechnicalException
   */
  private int[] haveCapacityCriterias( final String codeUtilisateur, final Object[] capacityNames, 
		  	final DescriptiveContentHotel dch, final CriteresCapacitesRechercheHotels capacitySelected ) 
  			throws TechnicalException {
    
	  int capacityValue;
    String capacityName;
    int[] hotelCapacityValues = new int[ capacityNames.length ];

    if( LogCommun.isTechniqueDebug() ) {
      LogCommun.debug( "SearchHotelSLDAO","haveCapacityCriterias", "Recherche criteres capacites - code_hotel : " + dch.getCode() 
                 + " - capacite : " + capacitySelected.toString() );
    }
      
    for( int j=0; j<capacityNames.length; j++ ) {
      capacityName = (String) capacityNames[j];
      capacityValue = ( (Integer) capacitySelected.getCritere( capacityName ) ).intValue();
      hotelCapacityValues[j] = getNbRooms( codeUtilisateur, capacityName, capacityValue, dch );
      if( hotelCapacityValues[j] <= 0 )
        return null;
    }
    return hotelCapacityValues;
  }
  
  /**
   * description:             Retourne la liste des booleens identifiant si l'hotel possede ou non 
   *                          les produits selectionnes.
   * @param codeUtilisateur   Login de l'utilisateur
   * @param dch               Objet Fiche-hotel
   * @param infoSelected      Objet CriteresInformatifsRechercheHotels stockant les produits selectionnes
   * @return                  Tableau de boolean identifiant la reponse pour chaque produit
   * @throws TechnicalException
   */
  private boolean[] haveInformativeCriterias( final String codeUtilisateur, final DescriptiveContentHotel dch, 
		  final CriteresInformatifsRechercheHotels infoSelected  ) throws TechnicalException {
    
    List<BasicType> infos = infoSelected.getCriteres();
    int nbInfos = infoSelected.getCriteresCount();
    boolean[] hotelInfoValues = new boolean[ nbInfos ]; 
    
    if( LogCommun.isTechniqueDebug() ) {
      LogCommun.debug( "SearchHotelSLDAO","haveInformativeCriterias", "Recherche criteres informatifs - code_hotel : " + dch.getCode() + " - info : " + infoSelected.toString() );
    }

    try { 
      for( int j=0; j<nbInfos; j++ ) {
         hotelInfoValues[j] = BasicTypeEvaluatorHelper.evaluate( infos.get( j ), dch );
      }
    } catch( EnterpriseException e ) {
      LogCommun.major( codeUtilisateur, "SearchHotelSLDAO", "haveInformativeCriterias", "Echec lors de la connexion au service ServiceLayer : " + e );
      throw new TechnicalException( e ); 
    }
    return hotelInfoValues;
  }
  
  /**
   * description: Retourne un booleen correspondant a la disponibilite de l'hotel sur la periode et la tarskey donnee. 
   * @param ctx   Contexte de l'utilisateur
   * @param dch   Objet fiche-hotel
   * @param comm  Objet CriteresCommercialsRechercheHotels stockant les criteres commerciaux selectionnes
   * @return      True si la disponibilite de l'hotel est validee
   * @throws TechnicalException
   * @throws IncoherenceException
   */
/*
  private boolean hasCritCommercial( final Contexte ctx, final DescriptiveContentHotel dch, 
      final CriteresCommercialsRechercheHotels comm )
      throws TechnicalException, IncoherenceException {

    boolean ok = false;

    AsaDate debut = comm.getDateDebut();
    AsaDate fin = comm.getDateFin();
    String codeTarif = comm.getCodeTarif();
    String codeHotel = dch.getCode(); 
      
    try {
      UserContext uc = new UserContext();
      uc.setLocale( getLocaleFromCodeLangue( ctx.getCodeLangue() ) );
      EnterpriseServiceFacade esf = EnterpriseFactory.connect( uc );
      
      UniquePeriodSegment ups = new UniquePeriodSegment( debut.getDate(), fin.getDate() );
      ReservationCode rc = ReservationCode.getAsaInstance( codeTarif );
      
      if( LogCommun.isTechniqueDebug() ) {
       LogCommun.debug( "SearchHotelSLDAO","hasCritComm", "Recherche disponibilite - code_hotel : " 
                  + codeHotel + " - date_debut : " + debut.toString() + " - date_fin : " 
                  + fin.toString() + " - code_tarif : " + rc.getValue() );
      }
      
      if( rc == null )
        throw new IncoherenceException( "Rate Acces introuvable" );

      ups.addReservationCode( rc );
      AvailabilityHotel ah = esf.getHotelAvailabilityService().getAvailabilityWithReservationCodes( codeHotel, ups );

      ok = false;
      if( ah != null && ah.isOpen() ) {
        AvailabilitySegment as = ah.getAvailabitySegment( rc );
        ok = as.getAvailabilityStatus() == AvailabilitySegment.AVAILABLE;
      }
      
      
    } catch( EnterpriseException e ) {
      LogCommun.major( ctx.getCodeUtilisateur(), "SearchHotelSLDAO", "hasCritComm", "Echec lors " +
          "de la connexion au service ServiceLayer : " + e );
      throw new TechnicalException( e ); 
    }

    return ok;
  }
*/
  /**
   * description: 	Retourne la sous-liste d'hotels repondant a la disponibilite sur la periode et la tarskey 
   *              		donnee depuis une liste d'hotels définie. 
   * @param hotels	liste d objets hotels sur lesquels on va vérifier la disponibilitée. 
   * @param comm   	Objet CriteresCommercialsRechercheHotels stockant les criteres commerciaux selectionnes
   * @param ctx    	Contexte de l'utilisateur
   * @return      	une liste d'objet Hotel
   * @throws TechnicalException
   * @throws IncoherenceException
   */
  @SuppressWarnings("unchecked")
  private List<Hotel> getHotelsWithCommercialCriterias( final List<Hotel> hotels, 
		  final CriteresCommercialsRechercheHotels criterias, final Contexte ctx ) 
		  throws TechnicalException, IncoherenceException {
    
	  List<Hotel> result = new ArrayList<Hotel>();
    
	  AsaDate debut 	= criterias.getDateDebut();
	  AsaDate fin 		= criterias.getDateFin();
	  String codeTarif	= criterias.getCodeTarif();

	  //ANO 3945 : si dateDeb = dateFin on incremente dateFin d un jour
	  if( debut.getDaysBetween( fin ) < 1 ) {
		  Calendar newDate  = Calendar.getInstance();
		  newDate.setTime( fin.getDate() );
		  newDate.add( Calendar.DATE, 1 );
		  fin.setDate( newDate.getTime() );
	  }
    
	  try {
		  UserContext uc = new UserContext();
		  uc.setLocale( getLocale( ctx.getCodeLangue() ) );
		  EnterpriseServiceFacade esf = EnterpriseFactory.connect( uc );
      
		  UniquePeriodSegment ups = new UniquePeriodSegment( debut.getDate(), fin.getDate() );
		  ReservationCode rc = ReservationCode.getRateAccesInstance( codeTarif );
      
		  if( LogCommun.isTechniqueDebug() ) {
			  LogCommun.debug( "SearchHotelSLDAO","getHotelsWithCommercialCriterias", "Recherche disponibilite - date_debut : " + 
					  debut.toString() + " - date_fin : " + fin.toString() + " - code_tarif : " + rc.getValue() );
		  }
      
		  if( rc == null )
			  throw new IncoherenceException( "Rate Acces introuvable" );

		  ups.addReservationCode( rc );
      
		  int nbHotels = hotels.size();
		  String[] codeHotels = new String[nbHotels];
		  Map<String,Integer> hotelsPos = new HashMap<String,Integer>();
		  for( int i=0; i<nbHotels; i++ ) {
			  String code = hotels.get(i).getCode();
			  codeHotels[i] = code;
			  hotelsPos.put( code, new Integer( i ) );
		  }
      
		  List<AvailabilityHotel> availabilitiesHotels = esf.getHotelAvailabilityService()
      									.getAvailabilitiesWithReservationCodes( codeHotels, ups );

		  if( availabilitiesHotels != null ) {
			  AvailabilityHotel ah;
			  AvailabilitySegment as;
			  for( int i=0, size=availabilitiesHotels.size(); i<size; i++ ) {
				  ah = availabilitiesHotels.get( i );

				  if( ah != null && ah.isOpen() ) {
					  as = ah.getAvailabitySegment( rc );
					  if( as != null && as.getAvailabilityStatus() == AvailabilitySegment.AVAILABLE ) {
						  result.add( hotels.get( hotelsPos.get( ah.getCode() ).intValue() ) );
					  }
				  }
			  }
		  }

		  if( LogCommun.isTechniqueDebug() ) {
			  LogCommun.debug( "SearchHotelSLDAO","getHotelsWithCommercialCriterias", "Recherche disponibilite - " + result.size() + " hotels found." );
		  }
	  } catch( EnterpriseException e ) {
		  LogCommun.major( ctx.getCodeUtilisateur(), "SearchHotelSLDAO", "getHotelsWithCommercialCriterias", "Echec lors de la connexion au service ServiceLayer : " + e );
		  throw new TechnicalException( e ); 
	  }
	  return result;
  }
}





