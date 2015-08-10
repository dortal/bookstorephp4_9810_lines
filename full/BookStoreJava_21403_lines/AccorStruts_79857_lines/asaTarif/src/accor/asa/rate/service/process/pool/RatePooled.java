package com.accor.asa.rate.service.process.pool;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.ratelevel.RateLevel;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.RatesUserException;
import com.accor.asa.rate.model.*;
import com.accor.asa.rate.service.process.RateFacade;
import com.accor.asa.rate.service.process.persistance.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class RatePooled implements RateFacade {

	private static HashMap<String, RateDAO> rateDAOsMap = new HashMap<String, RateDAO>();

	static {
		rateDAOsMap.put(RateFacade.BUSINESS_RATE_KEY, BusinessRateDAO.getInstance());
		rateDAOsMap.put(RateFacade.ADAGIO_BUSINESS_RATE_KEY, AdagioBusinessRateDAO.getInstance());
		rateDAOsMap.put(RateFacade.LEISURE_RATE_KEY, LeisureRateDAO.getInstance());
		rateDAOsMap.put(RateFacade.CHILD_RATE_KEY, ChildRateDAO.getInstance());
		rateDAOsMap.put(RateFacade.SPECIAL_RATE_KEY, SpecialRateDAO.getInstance());
        rateDAOsMap.put(RateFacade.BLACK_OUT_DATES_KEY, BlackOutDatesDAO.getInstance());
    }

	public List<RateBean> getRatesList(String rateKey, RateBean bean, Contexte contexte) throws RatesTechnicalException {
		return getRateDAO(rateKey).getRatesList(bean, contexte);
	}

	public void insertRate(String rateKey, RateBean bean, Contexte contexte) throws RatesTechnicalException, RatesUserException {
		getRateDAO(rateKey).insertRate(bean, contexte);
	}

	public void updateRate(String rateKey, RateBean bean, Contexte contexte) throws RatesTechnicalException, RatesUserException {
		getRateDAO(rateKey).updateRate(bean, contexte);
	}

	public int deleteRate(String rateKey, RateBean bean, Contexte contexte) throws RatesTechnicalException, RatesUserException {
		return getRateDAO(rateKey).deleteRate(bean, contexte);
	}

	private RateDAO getRateDAO(String rateKey) throws RatesTechnicalException {
		RateDAO dao = rateDAOsMap.get(rateKey);
		if (dao == null)
			throw new RatesTechnicalException("Pas de classe DAO pour le type : " + rateKey + "\n");
		return dao;
	}

	// ================================ RATES INFOS  ========================================

	/**
	 * Retourne la liste des tarifs d'un hotel, d'une famille de tarif et d'une
	 * periode de validite
	 * 
	 * @param codeHotel
	 * @param idFamilleTarif
	 * @param idPeriodeValidite
	 * @param contexte
	 * @return
	 * @throws TechnicalException
	 */
	public List<RateLevel> getRateLevelsList(String codeAsaCategory, int idFamilleTarif, int idPeriodeValidite, Contexte contexte) throws RatesTechnicalException {
		return RateInfoDAO.getInstance().getRateLevelsList(codeAsaCategory, idFamilleTarif, idPeriodeValidite, contexte);
	}

	/**
	 * Retourne la periode de validite en cours ou passée
	 * 
	 * @param codeHotel
	 * @param idGroupeTarif
	 * @param validite
	 * @param contexte
	 * @return
	 * @throws TechnicalException
	 */
	public PeriodeValiditeRefBean getPeriodeValidite(String codeHotel, int idGroupeTarif, int validite, Contexte contexte) throws RatesTechnicalException {
		return RateInfoDAO.getInstance().getPeriodeValidite(codeHotel, idGroupeTarif, validite, contexte);
	}

	/**
	 * Retourne la grille d'un hotel pour une periode de validite et une famille
	 * de tarif
	 * 
	 * @param codeHotel
	 * @param idPeriodeValidite
	 * @param idFamilleTarif
	 * @param contexte
	 * @return
	 * @throws TechnicalException
	 */
	public GrilleBean getGrille(String codeHotel, int idPeriodeValidite, int idFamilleTarif, Contexte contexte) throws RatesTechnicalException {
		return RateInfoDAO.getInstance().getGrille(codeHotel, idPeriodeValidite, idFamilleTarif, contexte);
	}
    /**
     * Retourne une grille par son id
     * @param idGrille
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     */
    public GrilleBean getGrilleById( long idGrille, Contexte contexte )
            throws RatesTechnicalException {
        return RateInfoDAO.getInstance().getGrilleById(idGrille, contexte);
    }

    public List<GrilleBean> getGrilles (String codeHotel, int idGroupeTarif, int idPeriodeValidite, Contexte contexte) throws RatesTechnicalException {
		return RateInfoDAO.getInstance().getGrilles(codeHotel, idGroupeTarif, idPeriodeValidite, contexte);
	}

	/**
	 * Creer une nouvelle grille
	 * 
	 * @param grille
	 * @param contexte
	 * @return
	 * @throws TechnicalException
	 */
	public long addGrille(String codeHotel, int idPeriodeValidite, int idFamilleTarif, Contexte contexte) throws RatesTechnicalException {
		return RateInfoDAO.getInstance().addGrille(codeHotel, idPeriodeValidite, idFamilleTarif, contexte);
	}

    /**
     * Maj de l'historique grille
     * @param idGrille
     * @param codeRateLevel
     * @param contexte
     * @throws RatesTechnicalException
     */
    public void updateHistoGrille(long idGrille, String codeRateLevel, Contexte contexte)
            throws RatesTechnicalException {
        RateInfoDAO.getInstance().updateHistoGrille(idGrille, codeRateLevel, contexte);
    }

    public List<RateContractInfo> getContractsPeriodes(long idGrille, Contexte contexte) throws RatesTechnicalException {
		return RateInfoDAO.getInstance().getContractsPeriodes(idGrille, contexte);
	}

	public List<RateContractInfo> getContractsForRateLevel(long idGrille, String codeRateLevel, String status, Contexte contexte) throws RatesTechnicalException {
		return RateInfoDAO.getInstance().getContractsForRateLevel(idGrille, codeRateLevel, status, contexte);
	}

	public void importGrilleTarif(String rateKey, GrilleBean oldGrilleData, PeriodeValiditeRefBean activePeriodeValidite, Contexte contexte) throws RatesTechnicalException, RatesUserException{
		getRateDAO(rateKey).importGrilleTarif(oldGrilleData, activePeriodeValidite, contexte);
	}

	public RateBean getRateById(String rateKey, RateBean rate, Contexte contexte) throws RatesTechnicalException{
		return getRateDAO(rateKey).getRateByKey(rate, contexte) ;
	}

	public List<ControleFinSaisieBean> getControlesEnEchec (String codeHotel, int idGroupeTarif, int idPeriodeValidite, Contexte contexte) throws RatesTechnicalException {
		return ControlesFinSaisieDAO.getInstance().getControlesEnEchec(codeHotel, idGroupeTarif, idPeriodeValidite, contexte);
	}

	public void updateStatutGrille (String codeHotel, int idGroupeTarif, int idPeriodeValidite, int idStatut, Contexte contexte) throws RatesTechnicalException {
		RateInfoDAO.getInstance().updateStatutGrille(codeHotel, idGroupeTarif, idPeriodeValidite, idStatut, contexte);
	}

    public void updateStatutGrille (long idGrille, int idStatut, Contexte contexte) throws RatesTechnicalException {
        RateInfoDAO.getInstance().updateStatutGrille(idGrille, idStatut, contexte);
    }

    //================================  TAXES  ========================================
    /**
     * Retourne la liste des taxes d'un hotel
     * @param codeHotel
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     */
    public List<TaxeBean> getTaxes(String codeHotel, Contexte contexte) throws RatesTechnicalException {
        return TaxeDAO.getInstance().getTaxes(codeHotel, contexte);
    }
    /**
     * Retourne une taxe d'un hotel
     * @param codeHotel
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     */
    public TaxeBean getTaxe(long idTaxe, Contexte contexte) throws RatesTechnicalException {
        return TaxeDAO.getInstance().getTaxe(idTaxe, contexte);
    }
    /**
     * Ajouter une taxe
     * @param taxe
     * @param contexte
     * @throws RatesTechnicalException
     * @throws RatesUserException
     */
    public void addTaxe(TaxeBean taxe, Contexte contexte) throws RatesTechnicalException, RatesUserException {
        TaxeDAO.getInstance().addTaxe(taxe, contexte);
    }
    /**
     * Supprimer une taxe
     * @param idTaxe
     * @param contexte
     * @throws RatesTechnicalException
     * @throws RatesUserException
     */
    public void deleteTaxe(long idTaxe, Contexte contexte) throws RatesTechnicalException, RatesUserException {
        TaxeDAO.getInstance().deleteTaxe(idTaxe, contexte);
    }
    
}