package com.accor.asa.commun.utiles.process;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.hotel.metier.Room;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.Element;
import com.accor.asa.commun.metier.categorie.AsaCategory;
import com.accor.asa.commun.metier.devise.Devise;
import com.accor.asa.commun.metier.donneesdereference.CibleCommerciale;
import com.accor.asa.commun.metier.donneesdereference.Continent;
import com.accor.asa.commun.metier.donneesdereference.Pays;
import com.accor.asa.commun.metier.periodedevalidite.PeriodeDeValidite;
import com.accor.asa.commun.metier.ratelevel.RateLevel;
import com.accor.asa.commun.metier.segment.Segment;
import com.accor.asa.commun.metier.taxe.TaxeTarsBean;
import com.accor.asa.commun.persistance.ContexteAppelDAO;
import com.accor.asa.commun.versioninfo.metier.DatabaseVersionInformation;
import com.accor.asa.rate.RatesTechnicalException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 14 juin 2005
 * Time: 16:31:31
 */
public interface CommunUtilsFacade {
	/** Le nom du pool d'objet*/
	public static final String POOL_NAME   = "communCommunUtilsFacade";

	/**
	 * récupération des infos de version pour une source (pool, TC, appli)
	 */
	public DatabaseVersionInformation getVersionInfoForSource(ContexteAppelDAO ctx, int codeSource) throws TechnicalException;

	/***
	 * Retourne la liste des pays en provenance du cache ou de la BD s il n est pas instancie
	 * @param contexte
	 * @return List<Pays>
	 * @throws TechnicalException
	 */
	public List<Pays> getPays( final Contexte contexte ) throws TechnicalException;

    /***
     * Methode de recuperation des pays GEO depuis le service ServiceLayer
     * @param contexte
     * @return List<Pays>
     * @throws TechnicalException
     */
	public List<Pays> getPaysGEO( final Contexte contexte ) throws TechnicalException;

	/**
	 * Recherche les infos du pays dont le code_geo est passe en parametre
	 * @param code
	 * @param contexte
	 * @return Pays
	 * @throws TechnicalException
	 */
	public Pays getPays( final String code, final Contexte contexte ) throws TechnicalException;

    /**
     * Retourne la liste des etats d'un pays
     * @param codePays
     * @param contexte
     * @return List<Element>
     * @throws TechnicalException
     */
	public List<Element> getStates( final String codePays, final Contexte contexte ) 
			throws TechnicalException;

	/**
	 * Renvoie un Etat a partir de son code et de son codePays
	 * @param codePays
	 * @param codeEtat
	 * @param contexte
	 * @return Element
	 * @throws TechnicalException
	 */
	public Element getState( final String codeEtat, final String codePays, final Contexte contexte ) 
			throws TechnicalException;

    /**
     * Renvoie tous les langues gérées par ASA
     * @param  contexte
     * @return List<Element>
     * @throws TechnicalException
     */
	public List<Element> getLanguages( final Contexte contexte ) throws TechnicalException;

    /**
     * Renvoie tous les civilités du system
     * @param contexte
     * @return List<Element>
     * @throws TechnicalException
     */
	public List<Element> getCivilites( final Contexte contexte ) throws TechnicalException;

    /**
     * Renvoie une civilite a partir de son code
     * @param code
     * @param contexte
     * @return Element
     * @throws TechnicalException
     */
	public Element getCivilite( final String code, final Contexte contexte ) throws TechnicalException;

	/**
     * Renvoie tous les cibles commerciales du system
     * @param  contexte
     * @return List<CibleCommerciale>
     * @throws TechnicalException
     */
	public List<CibleCommerciale> getCiblesCommerciales( final Contexte contexte ) throws TechnicalException;

    /**
     * Renvoie une cible commerciale a partir de son code
     * @param  code
     * @param  contexte
     * @return CibleCommerciale
     * @throws TechnicalException
     */
	public CibleCommerciale getCibleCommerciale( final String code, final Contexte contexte ) 
			throws TechnicalException;
	
    /**
     * Recupérer la liste des devises
     * @param isLoadFromBase
     * @param contexte
     * @return List<Devise>
     * @throws TechnicalException
     */
	public List<Devise> getDevises( final boolean isLoadFromBase, final Contexte contexte )
            throws TechnicalException;

    /**
     * Retourne la liste des devises d'un pays
     *
     * @param codeHotel
     * @param contexte
     * @return List<Devise>
     * @throws TechnicalException
     */
	public List<Devise> getDevisesHotelPays( final String codeHotel, final Contexte contexte )
            throws TechnicalException;

    /**
     * Recharger la liste des Marques
     * @param contexte
     * @return List<Element>
     * @throws TechnicalException
     */
	public List<Element> getMarques( final Contexte contexte ) throws TechnicalException;

    /**
     * Retourne la marque selectionnée
     * @param codeMarque
     * @param contexte
     * @return Element
     * @throws TechnicalException
     */
	public Element getMarque( final String codeMarque, final Contexte contexte ) throws TechnicalException;

	/**
     * Recharger la liste des Chaines
     * @param contexte
     * @return List<Element>
     * @throws TechnicalException
     */
    public List<Element> getChaines( final Contexte contexte ) throws TechnicalException;

    /**
     * Recharger la liste des ASA Categories
     * @param contexte
     * @return List<AsaCategory>
     * @throws TechnicalException
     */
	public List<AsaCategory> getCategories( final Contexte contexte ) throws TechnicalException;

    /**
     * Methode de recuperation des segments
     * @param contexte
     * @return List<Segment>
     * @throws TechnicalException
     */
	public List<Segment> getSegmentPrincipaux( final Contexte contexte ) throws TechnicalException;
    
    /**
     * Retourne le code langue d'un utilisateur
     * Utilisé dans le module translate et vente
     * @param login
     * @param contexte
     * @return String
     * @throws TechnicalException
     */
    public String getCodeLangue( final String login, final Contexte contexte ) throws TechnicalException;

    /**
     * Retourne la liste des ratelevels
     * @param contexte
     * @return List<RateLevel>
     * @throws TechnicalException
     */
	public List<RateLevel> getRateLevels( final Contexte contexte ) throws TechnicalException;

	
	/**
     * Retourne la liste des ratelevels pour les grilles
     * @param contexte
     * @return List<RateLevel>
     * @throws TechnicalException
     */
	public List<RateLevel> getRateLevelsForGrille( final Contexte contexte , String codeAsaCategory, int idFamilleTarif, int idPeriodeValidite) throws TechnicalException;

    /**
     * Recharger la liste des Periodes de validite
     * @param contexte
     * @return List<PeriodeDeValidite>
     * @throws TechnicalException
     */
	public List<PeriodeDeValidite> getPeriodesValidite( final Contexte contexte ) throws TechnicalException;

    /**
     * Recharger la liste des Periodes de validite d'un groupe de tarifs
     * @param codeGroupeTarifs
     * @param contexte
     * @return List<PeriodeDeValidite>
     * @throws TechnicalException
     */
	public List<PeriodeDeValidite> getPeriodesValidite( final int codeGroupeTarifs, final Contexte contexte ) 
			throws TechnicalException;

    /***
     * 
     * @param codeHotel
     * @param contexte
     * @return String
     * @throws TechnicalException
     */
	public String getCodeAsaCategoryForHotel( final String codeHotel, final Contexte contexte) 
    		throws TechnicalException;

    /**
     * Retourne la liste des chambres d'un hotel
     * @param codeHotel
     * @param contexte
     * @return List<Room>
     * @throws TechnicalException
     */
	public List<Room> getRooms( final String codeHotel, final Contexte contexte )
            throws TechnicalException;


    /**
     * 
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public List<Continent> getContinents( final Contexte contexte ) throws TechnicalException;
    
    /**
     * 
     * @param codeContinent
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public Continent getContinentByCode(String codeContinent, Contexte contexte) throws TechnicalException;
    
    public Pays getPaysByCode(String codePays, Contexte contexte) throws TechnicalException;

    /**
     * Retourne la liste des taxes d'un hotel
     * @param codeHotel
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<TaxeTarsBean> getTaxes( final String codeHotel, final Contexte contexte )
            throws TechnicalException;

    /**
     * Retourne la liste des tarifs d'un hotel
     * @param codeHotel
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<RateLevel> getRateLevelsForHotel( String codeHotel, Contexte contexte )
            throws TechnicalException;
}
