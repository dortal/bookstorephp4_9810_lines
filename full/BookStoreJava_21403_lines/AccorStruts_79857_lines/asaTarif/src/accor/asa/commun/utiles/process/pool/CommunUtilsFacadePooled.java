package com.accor.asa.commun.utiles.process.pool;

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
import com.accor.asa.commun.utiles.persistance.CommunUtilsDAO;
import com.accor.asa.commun.utiles.process.CommunUtilsFacade;
import com.accor.asa.commun.versioninfo.metier.DatabaseVersionInformation;
import com.accor.asa.commun.versioninfo.persistance.VersionInfoDAO;
import com.accor.asa.commun.versioninfo.presentation.VersionInfoHTMLHelper;
import com.accor.asa.rate.RatesTechnicalException;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 14 juin 2005
 * Time: 16:33:13
 */
public class CommunUtilsFacadePooled implements CommunUtilsFacade{
	/**
	 * Constructeur
	 */
	public CommunUtilsFacadePooled() {

	}

	/**
	 * récupération des infos de version pour une source (pool, TC, appli)
	 * @see com.accor.asa.commun.versioninfo.persistance.VersionInfoDAO
	 */
	public DatabaseVersionInformation getVersionInfoForSource(ContexteAppelDAO ctx, int codeInfo) throws TechnicalException {
		DatabaseVersionInformation info = null;
		VersionInfoDAO dao = VersionInfoDAO.getInstance();
		if (codeInfo<=VersionInfoHTMLHelper.INFO_TC) {
			if (codeInfo == VersionInfoHTMLHelper.INFO_TC)
				info = dao.getDBInfoForTC(ctx);
			else
				info = dao.getDBInfoForPool(ctx,codeInfo);
		}
		else if (codeInfo == VersionInfoHTMLHelper.INFO_APPLI) {
			info = new DatabaseVersionInformation("" + new Date(), "weblogic",
				"", ctx.getLogin(), "", dao.getSourceVersion());
		}
		return info;
	}



	/***
	 * Retourne la liste des pays en provenance du cache ou de la BD s il n est pas instancie
	 * @param contexte
	 * @return List<Pays>
	 * @throws TechnicalException
	 */
	public List<Pays> getPays( final Contexte contexte ) throws TechnicalException {
		return CommunUtilsDAO.getInstance().getPays( contexte );
	}

    /***
     * Methode de recuperation des pays GEO depuis le service ServiceLayer
     * @param contexte
     * @return List<Pays>
     * @throws TechnicalException
     */
	public List<Pays> getPaysGEO( final Contexte contexte ) throws TechnicalException {
		return CommunUtilsDAO.getInstance().getPaysGEO( contexte );
	}

	/**
	 * Recherche les infos du pays dont le code_geo est passe en parametre
	 * @param code
	 * @param contexte
	 * @return Pays
	 * @throws TechnicalException
	 */
	public Pays getPays( final String code, final Contexte contexte ) throws TechnicalException {
    	
		if( StringUtils.isBlank( code ) )
			return null;
		
		List<Pays> pays = getPays(contexte);

        if( pays != null ) {
        	for( Pays p : pays ) {
        		if( StringUtils.equalsIgnoreCase( code, p.getCode() ) )
                    return p;
            }
        }
        return null;
	}

    /**
     * Retourne la liste des etats d'un pays
     * @param codePays
     * @param contexte
     * @return List<Element>
     * @throws TechnicalException
     */
	public List<Element> getStates( final String codePays, final Contexte contexte ) 
			throws TechnicalException {
		return CommunUtilsDAO.getInstance().getStates( codePays, contexte );
	}

	/**
	 * Renvoie un Etat a partir de son code et de son codePays
	 * @param codePays
	 * @param codeEtat
	 * @param contexte
	 * @return Element
	 * @throws TechnicalException
	 */
	public Element getState( final String codeEtat, final String codePays, final Contexte contexte ) 
			throws TechnicalException {

		if( StringUtils.isBlank( codeEtat ) ) 
			return null;
		
		List<Element> states = getStates( codePays, contexte );
        
		if( states != null ) {
			for( Element s : states ) {
	            if( StringUtils.equalsIgnoreCase( s.getCode(), codeEtat ) ) {
	                return s;
	            }
	        }
        }
        return null;
    }

    /**
     * Renvoie tous les langues gérées par ASA
     * @param  contexte
     * @return List<Element>
     * @throws TechnicalException
     */
	public List<Element> getLanguages( final Contexte contexte ) throws TechnicalException {
		return CommunUtilsDAO.getInstance().getLanguages( contexte );
	}

    /**
     * Renvoie tous les civilités du system
     * @param contexte
     * @return List<Element>
     * @throws TechnicalException
     */
	public List<Element> getCivilites( final Contexte contexte ) throws TechnicalException {
		return CommunUtilsDAO.getInstance().getCivilites( contexte );
	}

    /**
     * Renvoie une civilite a partir de son code
     * @param code
     * @param contexte
     * @return Element
     * @throws TechnicalException
     */
	public Element getCivilite( final String code, final Contexte contexte ) throws TechnicalException {
		
		List<Element> civs = getCivilites(contexte);
		
		if( civs != null ) {
			for( Element c : civs ) {
				if( StringUtils.equalsIgnoreCase( c.getCode(), code ) )
					return c;
			}
		}
		return null;
	}

    /**
     * Renvoie tous les cibles commerciales du system
     * @param  contexte
     * @return List<CibleCommerciale>
     * @throws TechnicalException
     */
	public List<CibleCommerciale> getCiblesCommerciales( final Contexte contexte ) throws TechnicalException {
		return CommunUtilsDAO.getInstance().getCiblesCommerciales( contexte );
	}

    /**
     * Renvoie une cible commerciale a partir de son code
     * @param  code
     * @param  contexte
     * @return CibleCommerciale
     * @throws TechnicalException
     */
	public CibleCommerciale getCibleCommerciale( final String code, final Contexte contexte ) 
			throws TechnicalException {
		
		List<CibleCommerciale> cibles = getCiblesCommerciales(contexte);
		
		if( cibles != null ) {
			for( CibleCommerciale c : cibles ) {
				if( StringUtils.equalsIgnoreCase( c.getCode(), code ) )
					return c;
			}
		}
		return null;
	}

    /**
     * Recupérer la liste des devises
     * @param isLoadFromBase
     * @param contexte
     * @return List<Devise>
     * @throws TechnicalException
     */
	public List<Devise> getDevises( final boolean isLoadFromBase, final Contexte contexte )
            throws TechnicalException {
		return CommunUtilsDAO.getInstance().getDevises(isLoadFromBase, contexte);
	}

    /**
     * Retourne la liste des devises d'un pays
     *
     * @param codeHotel
     * @param contexte
     * @return List<Devise>
     * @throws TechnicalException
     */
	public List<Devise> getDevisesHotelPays( final String codeHotel, final Contexte contexte )
            throws TechnicalException {
		return CommunUtilsDAO.getInstance().getDevisesHotelPays(codeHotel, contexte);
	}

    /**
     * Recharger la liste des Marques
     * @param contexte
     * @return List<Element>
     * @throws TechnicalException
     */
	public List<Element> getMarques( final Contexte contexte ) throws TechnicalException {
		return CommunUtilsDAO.getInstance().getMarques(contexte);
	}

    /**
     * Retourne la marque selectionnée
     * @param codeMarque
     * @param contexte
     * @return Element
     * @throws TechnicalException
     */
	public Element getMarque( final String codeMarque, final Contexte contexte ) throws TechnicalException {
		List<Element> marks = CommunUtilsDAO.getInstance().getMarques(contexte);
		if( marks != null && StringUtils.isNotEmpty( codeMarque ) ) {
			for( Element m : marks ) {
				if( StringUtils.equals( m.getCode(), codeMarque ) )
					return m;
			}
		}
		return null;
	}

    /**
     * Recharger la liste des Chaines
     * @param contexte
     * @return List<Element>
     * @throws TechnicalException
     */
    public List<Element> getChaines( final Contexte contexte ) throws TechnicalException {
		return CommunUtilsDAO.getInstance().getChaines(contexte);
	}

    /**
     * Recharger la liste des ASA Categories
     * @param contexte
     * @return List<AsaCategory>
     * @throws TechnicalException
     */
	public List<AsaCategory> getCategories( final Contexte contexte ) throws TechnicalException {
		return CommunUtilsDAO.getInstance().getCategories( contexte );
	}

    /**
     * Methode de recuperation des segments
     * @param contexte
     * @return List<Segment>
     * @throws TechnicalException
     */
	public List<Segment> getSegmentPrincipaux( final Contexte contexte ) throws TechnicalException {
		return CommunUtilsDAO.getInstance().getSegmentPrincipaux( contexte );
	}

    /**
     * Retourne le code langue d'un utilisateur
     * Utilisé dans le module translate et vente
     * @param login
     * @param contexte
     * @return String
     * @throws TechnicalException
     */
    public String getCodeLangue( final String login, final Contexte contexte ) throws TechnicalException {
		return CommunUtilsDAO.getInstance().getCodeLangue(login,  contexte );
	}

    /**
     * Retourne la liste des ratelevels
     * @param contexte
     * @return List<RateLevel>
     * @throws TechnicalException
     */
	public List<RateLevel> getRateLevels( final Contexte contexte ) throws TechnicalException {
		return CommunUtilsDAO.getInstance().getRateLevels( contexte );
	}
	
	/**
     * Retourne la liste des ratelevels
     * @param contexte
     * @return List<RateLevel>
     * @throws TechnicalException
     */
	public List<RateLevel> getRateLevelsForGrille( final Contexte contexte, String codeAsaCategory, int idFamilleTarif, int idPeriodeValidite ) throws TechnicalException {
		return CommunUtilsDAO.getInstance().getRateLevelsForGrille( codeAsaCategory, idFamilleTarif, idPeriodeValidite, contexte);
	}
	

    /**
     * Recharger la liste des Periodes de validite
     * @param contexte
     * @return List<PeriodeDeValidite>
     * @throws TechnicalException
     */
	public List<PeriodeDeValidite> getPeriodesValidite( final Contexte contexte ) throws TechnicalException {
		return CommunUtilsDAO.getInstance().getPeriodesValidite(contexte );
	}

    /**
     * Recharger la liste des Periodes de validite d'un groupe de tarifs
     * @param codeGroupeTarifs
     * @param contexte
     * @return List<PeriodeDeValidite>
     * @throws TechnicalException
     */
	public List<PeriodeDeValidite> getPeriodesValidite( final int codeGroupeTarifs, final Contexte contexte ) 
			throws TechnicalException {
		List<PeriodeDeValidite> periods = getPeriodesValidite( contexte );
		List<PeriodeDeValidite> groupPeriods = new ArrayList<PeriodeDeValidite>(); 
		if( periods != null ) {
			for( PeriodeDeValidite p : periods ) {
				if( p.getIdGroupeTarifs() == codeGroupeTarifs ) 
					groupPeriods.add( p );
			}
		}
		return groupPeriods;
	}
	
    /***
     * 
     * @param codeHotel
     * @param contexte
     * @return String
     * @throws TechnicalException
     */
	public String getCodeAsaCategoryForHotel( final String codeHotel, final Contexte contexte) 
    		throws TechnicalException {
		return CommunUtilsDAO.getInstance().getCodeAsaCategoryForHotel(codeHotel,  contexte);
	}

    /**
     * Retourne la liste des chambres d'un hotel
     * @param codeHotel
     * @param contexte
     * @return List<Room>
     * @throws TechnicalException
     */
	public List<Room> getRooms( final String codeHotel, final Contexte contexte )
            throws TechnicalException {
		return CommunUtilsDAO.getInstance().getRooms(codeHotel, contexte);
	}


    /**
     * retourne la liste des continents
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public List<Continent> getContinents( final Contexte contexte ) throws TechnicalException
    {
    	return CommunUtilsDAO.getInstance().getContinents(contexte);
    }

	public Continent getContinentByCode(String codeContinent, Contexte contexte) throws TechnicalException {
		return CommunUtilsDAO.getInstance().getContinentByCode(codeContinent, contexte);
	}

	public Pays getPaysByCode(String codePays, Contexte contexte) throws TechnicalException {
		return CommunUtilsDAO.getInstance().getPaysByCode(codePays, contexte);
	}
    

    /**
     * Retourne la liste des taxes d'un hotel
     * @param codeHotel
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<TaxeTarsBean> getTaxes( final String codeHotel, final Contexte contexte )
            throws TechnicalException {
        return CommunUtilsDAO.getInstance().getTaxes(codeHotel, contexte);
    }

    /**
     * Retourne la liste des tarifs d'un hotel
     * @param codeHotel
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<RateLevel> getRateLevelsForHotel( String codeHotel, Contexte contexte )
            throws TechnicalException{
        return CommunUtilsDAO.getInstance().getRateLevelsForHotel(codeHotel, contexte);
    }
}
