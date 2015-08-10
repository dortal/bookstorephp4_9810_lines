package com.accor.asa.commun.hotel.process;

import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.commun.hotel.metier.HotelContact;
import com.accor.asa.commun.hotel.metier.descriptive.HotelAccess;
import com.accor.asa.commun.hotel.metier.descriptive.HotelAccounting;
import com.accor.asa.commun.hotel.metier.descriptive.HotelBank;
import com.accor.asa.commun.hotel.metier.descriptive.HotelBar;
import com.accor.asa.commun.hotel.metier.descriptive.HotelDescriptive;
import com.accor.asa.commun.hotel.metier.descriptive.HotelInterestCenter;
import com.accor.asa.commun.hotel.metier.descriptive.HotelLounge;
import com.accor.asa.commun.hotel.metier.descriptive.HotelMarketing;
import com.accor.asa.commun.hotel.metier.descriptive.HotelPhoto;
import com.accor.asa.commun.hotel.metier.descriptive.HotelProduct;
import com.accor.asa.commun.hotel.metier.descriptive.HotelRestaurant;
import com.accor.asa.commun.hotel.metier.sl.AsaDescriptiveContentHotel;
import com.accor.asa.commun.hotel.metier.sl.CriteresRechercheHotels;
import com.accor.asa.commun.hotel.metier.sl.SearchHotelResult;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.Element;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.services.framework.enterprise.common.basictype.BasicType;
import com.accor.services.framework.enterprise.hotel.amenities.DescriptiveContentHotel;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 15 juin 2005
 * Time: 17:07:44
 * To change this template use File | Settings | File Templates.
 */
public interface HotelFacade {

    /** Le nom du pool d'objet*/
    public static final String POOL_NAME   = "communHotelFacade";

    /**************************************************************************************/
    /********************               HotelDAO                         ******************/
    /**************************************************************************************/
    
    /***
     * Retourne la liste des photos de plan d'accès a l'hotel
     * @param rid
     * @param contexte
     * @return List<HotelPhoto>
     * @throws TechnicalException
     */
    public List<HotelPhoto> getPhotos( final String rid, final Contexte contexte ) 
    		throws TechnicalException;

    /***
     * Retourne les infos marketing de l'hotel
     * @param rid
     * @param contexte
     * @return HotelInfoMark
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    public HotelMarketing getMarketingInfos( final String rid, final Contexte contexte ) 
    		throws TechnicalException, IncoherenceException;
    
    /***
     * Retourne les infos comptabilite de l'hotel
     * @param rid
     * @param contexte
     * @return HotelAccounting
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    public HotelAccounting getAccountingInfos( final String rid, final Contexte contexte ) 
    		throws TechnicalException, IncoherenceException;
    
    /***
     * Retourne les infos bancaires de l'hotel
     * @param rid
     * @param contexte
     * @return HotelBank
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    public HotelBank getBankInfos( final String rid, final Contexte contexte ) 
    		throws TechnicalException, IncoherenceException;

    /***
     * Retourne la liste des centres d'interêts de l'hotel
     * @param rid
     * @param contexte
     * @return List<HotelInterestCenter>
     * @throws TechnicalException
     */
    public List<HotelInterestCenter> getInterestCenters( final String rid, final Contexte contexte ) 
    		throws TechnicalException;
    
    /***
     * Retourne la liste des produits d'un hotel
     * @param rid
     * @param contexte
     * @return List<HotelProduct>
     * @throws TechnicalException
     */
    public List<HotelProduct> getProducts( final String rid, final Contexte contexte ) 
    		throws TechnicalException;

    /***
     * Retourne la liste des restaurants de l'hotel
     * @param rid
     * @param contexte
     * @return List<HotelRestaurant>
     * @throws TechnicalException
     */
    public List<HotelRestaurant> getRestaurants( final String rid, final Contexte contexte ) 
    		throws TechnicalException;
    
    /***
     * Retourne la liste des bars de l'hotel
     * @param rid
     * @param contexte
     * @return List<HotelBar>
     * @throws TechnicalException
     */
    public List<HotelBar> getBars( final String rid, final Contexte contexte ) 
    		throws TechnicalException;
    
    /***
     * Retourne la liste des salles de reunions de l'hotel
     * @param rid
     * @param contexte
     * @return List<Element>
     * @throws TechnicalException
     */
    public List<Element> getMeetingRooms( final String rid, final Contexte contexte ) 
    		throws TechnicalException;
    
    /***
     * Retourne la liste des salons de l'hotel
     * @param rid
     * @param contexte
     * @return List<HotelLounge>
     * @throws TechnicalException
     */
    public List<HotelLounge> getLounges( final String rid, final Contexte contexte ) 
    		throws TechnicalException;
    
    /***
     * Retourne la liste des accès à l'hotel
     * @param rid
     * @param contexte
     * @return List<HotelAccess>
     * @throws TechnicalException
     */
    public List<HotelAccess> getAccess( final String rid, final Contexte contexte ) 
    		throws TechnicalException;
    
    /***
     * Retourne la fiche descrptive de l'hotel
     * @param rid
     * @param contexte
     * @return HotelDescriptive
     * @throws TechnicalException
     */
    public HotelDescriptive getDescriptive( final String rid, final Contexte contexte ) 
    		throws TechnicalException;
    
    /***
     * Retourne la fiche hotel
     * @param rid
     * @param contexte
     * @return Hotel
     * @throws TechnicalException
     */
    public Hotel getHotel( final String rid, final Contexte contexte ) throws TechnicalException;

    
    /***
     * Retourne la liste des contacts de l'hotel
     * @param contexte
     * @param rid
     * @return List<HotelContact>
     * @throws TechnicalException
     */
    public List<HotelContact> getStaff( final String rid, final Contexte contexte ) 
    		throws TechnicalException;

    
    
    /**************************************************************************************/
    /********************            SearchHotelSLDAO                    ******************/
    /**************************************************************************************/

    /**
     * description:         Effectue la recherche demandée via l'application ServiceLayer et retourne une liste 
     *                      d'hotels ou de zones gégraphiques.
     * @param criterias 	objet CriteresRechercheHotels stockant l'ensemble des criteres saisies
     * @param contexte      contexte de l'utilisateur
     * @return              SearchHotelResult
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    public SearchHotelResult searchHotels( final CriteresRechercheHotels criterias, final Contexte contexte ) 
    	throws TechnicalException, IncoherenceException;

    /***
     * Retourne la fiche hotel issu du ServiceLayer
     * @param rid
     * @param contexte
     * @return DescriptiveContentHotel
     * @throws TechnicalException
     */
    public DescriptiveContentHotel getHotelSL( final String rid, final Contexte contexte ) 
    		throws TechnicalException;

    /**
     * description:         Retourne la liste des fiches descriptives des hotels repondant aux criteres selectionnes.
     * @param hotels	    List d'objets Hotel ServiceLayer 
     * @param criterias  	Objet CriteresRechercheHotels stockant l'ensemble des criteres selectionnes 
     * @param contexte      Contexte de l'utilisateur
     * @return              List d'objet AsaDescriptiveContentHotel 
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    public List<AsaDescriptiveContentHotel> getHotelsSL( 
    		List<com.accor.services.framework.enterprise.hotel.Hotel> hotels, 
  		  	final CriteresRechercheHotels criterias, final Contexte contexte ) 
  		  	throws TechnicalException, IncoherenceException;

    
    /**************************************************************************************/
    /********************           SearchHotelJDBCDAO                   ******************/
    /**************************************************************************************/
    
    /**
     * Recherche par critère des hotels - les elements de la liste sont des objet Hotel
     * @param paramValues
     * @param contexte
     * @return List<Hotel>
     * @throws TechnicalException
     */
    public List<Hotel> searchHotelsWithHabilitation( final String[] paramValues, final Contexte contexte ) 
    		throws TechnicalException;

    
    /**************************************************************************************/
    /********************          HotelReferenceSLDAO                   ******************/
    /**************************************************************************************/

    /**
     * description:     Retourne la liste des localisation disponibles.
     * @param contexte  Contexte de l'utilisateur
     * @return          List d'objets BasicType 
     * @throws TechnicalException
     */
    public List<BasicType> getLocalisation( final Contexte contexte ) throws TechnicalException;

    /**
     * description:     Retourne la liste des centres d'interets Business et Loisirs disponibles.
     * @param contexte  Contexte de l'utilisateur
     * @return          List d'objets BasicType 
     * @throws TechnicalException
     */
    public List<BasicType> getCI( final Contexte contexte ) throws TechnicalException;

    /**
     * description:     Retourne la liste des systemes de securites disponibles.
     * @param contexte  Contexte de l'utilisateur
     * @return          List d'objets BasicType 
     * @throws TechnicalException
     */
    public List<BasicType> getSecurite( final Contexte contexte ) throws TechnicalException;
    
    /**
     * description:     Retourne la liste des restaurants et salles disponibles.
     * @param contexte  Contexte de l'utilisateur
     * @return          List d'objets BasicType 
     * @throws TechnicalException
     */
    public List<BasicType> getRestaurant( final Contexte contexte ) throws TechnicalException;
    
    /**
     * description:     Retourne la liste des activites sportives disponibles.
     * @param contexte  Contexte de l'utilisateur
     * @return          List d'objets BasicType 
     * @throws TechnicalException
     */
    public List<BasicType> getSport( final Contexte contexte ) throws TechnicalException;
    
    /**
     * description:     Retourne la liste des services et equipements disponibles.
     * @param contexte  Contexte de l'utilisateur
     * @return          List d'objets BasicType 
     * @throws TechnicalException
     */
    public List<BasicType> getService( final Contexte contexte ) throws TechnicalException;
}