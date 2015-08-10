package com.accor.asa.rate.service.process.pool;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.ratelevel.RateLevel;
import com.accor.asa.commun.process.pool.GlobalPoolException;
import com.accor.asa.commun.process.pool.GlobalPoolable;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.RatesUserException;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.model.*;
import com.accor.asa.rate.service.process.RateFacade;

import java.util.List;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class RatePoolable extends GlobalPoolable implements RateFacade {

	/** Init du pool en static */
	static {
		initPool(RateFacade.POOL_NAME);
	}

    public List<RateBean> getRatesList( String rateKey,  RateBean bean, Contexte contexte )
            throws RatesTechnicalException {
        RatePooled objectPooled = null;
        try {
            objectPooled = (RatePooled) getObjectPooled(RateFacade.POOL_NAME);
            return objectPooled.getRatesList(rateKey,   bean, contexte);
        } catch (GlobalPoolException ex) {
            Log.minor("RATE", "RefPoolable", "getRatesList", "Exception au call en mode pool", ex);
            throw new RatesTechnicalException(ex);
        } finally {
            returnObjectToPool(RateFacade.POOL_NAME, objectPooled);
        }
    }
    public void insertRate( String rateKey, RateBean bean, Contexte contexte )
            throws RatesTechnicalException, RatesUserException {
        RatePooled objectPooled = null;
        try {
            objectPooled = (RatePooled) getObjectPooled(RateFacade.POOL_NAME);
            objectPooled.insertRate(rateKey, bean, contexte);
        } catch (GlobalPoolException ex) {
            Log.minor("RATE", "RefPoolable", "addRate", "Exception au call en mode pool", ex);
            throw new RatesTechnicalException(ex);
        }  finally {
            returnObjectToPool(RateFacade.POOL_NAME, objectPooled);
        }
    }
    public void updateRate( String rateKey, RateBean bean, Contexte contexte )
            throws RatesTechnicalException, RatesUserException {
        RatePooled objectPooled = null;
        try {
            objectPooled = (RatePooled) getObjectPooled(RateFacade.POOL_NAME);
            objectPooled.updateRate(rateKey, bean, contexte);
        } catch (GlobalPoolException ex) {
            Log.minor("RATE", "RefPoolable", "updateRate", "Exception au call en mode pool", ex);
            throw new RatesTechnicalException(ex);
        }  finally {
            returnObjectToPool(RateFacade.POOL_NAME, objectPooled);
        }
    }
    public int deleteRate( String rateKey, RateBean bean, Contexte contexte )
            throws RatesTechnicalException, RatesUserException {
        RatePooled objectPooled = null;
        try {
            objectPooled = (RatePooled) getObjectPooled(RateFacade.POOL_NAME);
            return objectPooled.deleteRate(rateKey, bean, contexte);
        } catch (GlobalPoolException ex) {
            Log.minor("RATE", "RefPoolable", "deleteRate", "Exception au call en mode pool", ex);
            throw new RatesTechnicalException(ex);
        } finally {
            returnObjectToPool(RateFacade.POOL_NAME, objectPooled);
        }
    }

    //================================  RATES INFOS  ========================================

    /**
     * Retourne la liste des tarifs d'un hotel, d'une famille de tarif
     * et d'une periode de validite
     * @param codeHotel
     * @param idFamilleTarif
     * @param idPeriodeValidite
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public List<RateLevel> getRateLevelsList( String codeAsaCategory, int idFamilleTarif, int idPeriodeValidite, Contexte contexte )
            throws RatesTechnicalException{
        RatePooled objectPooled = null;
        try {
            objectPooled = (RatePooled)getObjectPooled(RateFacade.POOL_NAME);
            return objectPooled.getRateLevelsList(codeAsaCategory, idFamilleTarif, idPeriodeValidite, contexte);
        } catch (GlobalPoolException e) {
            Log.minor(contexte.getCodeUtilisateur(), "RateFacadePoolable", "getRateLevelsList", "exception au call en mode pool", e);
            throw new RatesTechnicalException(e);
        } finally {
            returnObjectToPool(RateFacade.POOL_NAME,objectPooled);
        }
    }

    /**
     * Retourne la periode de validite en cours ou passée
     * @param codeHotel
     * @param idGroupeTarif
     * @param validite
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public PeriodeValiditeRefBean getPeriodeValidite( String codeHotel, int idGroupeTarif, int validite, Contexte contexte )
            throws RatesTechnicalException {
        RatePooled objectPooled = null;
        try {
            objectPooled = (RatePooled)getObjectPooled(RateFacade.POOL_NAME);
            return objectPooled.getPeriodeValidite(codeHotel, idGroupeTarif, validite, contexte);
        } catch (GlobalPoolException e) {
            Log.minor(contexte.getCodeUtilisateur(), "RateFacadePoolable", "getPeriodeValidite", "exception au call en mode pool", e);
            throw new RatesTechnicalException(e);
        } finally {
            returnObjectToPool(RateFacade.POOL_NAME,objectPooled);
        }
    }

    /**
     * Retourne la grille d'un hotel pour une periode de validite
     * et une famille de tarif
     * @param codeHotel
     * @param idPeriodeValidite
     * @param idFamilleTarif
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public GrilleBean getGrille( String codeHotel, int idPeriodeValidite, int idFamilleTarif, Contexte contexte )
            throws RatesTechnicalException {
        RatePooled objectPooled = null;
        try {
            objectPooled = (RatePooled)getObjectPooled(RateFacade.POOL_NAME);
            return objectPooled.getGrille(codeHotel, idPeriodeValidite, idFamilleTarif, contexte);
        } catch (GlobalPoolException e) {
            Log.minor(contexte.getCodeUtilisateur(), "RateFacadePoolable", "getGrille", "exception au call en mode pool", e);
            throw new RatesTechnicalException(e);
        } finally {
            returnObjectToPool(RateFacade.POOL_NAME,objectPooled);
        }
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
        RatePooled objectPooled = null;
        try {
            objectPooled = (RatePooled)getObjectPooled(RateFacade.POOL_NAME);
            return objectPooled.getGrilleById(idGrille, contexte);
        } catch (GlobalPoolException e) {
            Log.minor(contexte.getCodeUtilisateur(), "RateFacadePoolable", "getGrilleById", "exception au call en mode pool", e);
            throw new RatesTechnicalException(e);
        } finally {
            returnObjectToPool(RateFacade.POOL_NAME,objectPooled);
        }
    }

	public List<GrilleBean> getGrilles (String codeHotel, int idGroupeTarif, int idPeriodeValidite, Contexte contexte) throws RatesTechnicalException {
		RatePooled objectPooled = null;
		try {
			objectPooled = (RatePooled) getObjectPooled(RateFacade.POOL_NAME);
			return objectPooled.getGrilles(codeHotel, idGroupeTarif, idPeriodeValidite, contexte);
		} catch (GlobalPoolException e) {
			Log.minor(contexte.getCodeUtilisateur(), "RateFacadePoolable", "getStatutsGrilles", "Exception au call en mode pool", e);
			throw new RatesTechnicalException(e);
		} finally {
			returnObjectToPool(RateFacade.POOL_NAME,objectPooled);
		}
	}

    /**
     * Creer une nouvelle grille
     * @param grille
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public long addGrille(String codeHotel, int idPeriodeValidite,int idFamilleTarif, Contexte contexte)
            throws RatesTechnicalException {
        RatePooled objectPooled = null;
        try {
            objectPooled = (RatePooled)getObjectPooled(RateFacade.POOL_NAME);
            return objectPooled.addGrille(codeHotel, idPeriodeValidite, idFamilleTarif, contexte);
        } catch (GlobalPoolException e) {
            Log.minor(contexte.getCodeUtilisateur(), "RateFacadePoolable", "addGrille", "exception au call en mode pool", e);
            throw new RatesTechnicalException(e);
        } finally {
            returnObjectToPool(RateFacade.POOL_NAME,objectPooled);
        }
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
        RatePooled objectPooled = null;
        try {
            objectPooled = (RatePooled)getObjectPooled(RateFacade.POOL_NAME);
            objectPooled.updateHistoGrille(idGrille, codeRateLevel,  contexte);
        } catch (GlobalPoolException e) {
            Log.minor(contexte.getCodeUtilisateur(), "RateFacadePoolable", "addGrille", "exception au call en mode pool", e);
            throw new RatesTechnicalException(e);
        } finally {
            returnObjectToPool(RateFacade.POOL_NAME,objectPooled);
        }
    }

    public List<RateContractInfo> getContractsPeriodes(long idGrille, Contexte contexte) throws RatesTechnicalException {
		RatePooled objectPooled = null;
        try {
            objectPooled = (RatePooled)getObjectPooled(RateFacade.POOL_NAME);
            return objectPooled.getContractsPeriodes(idGrille, contexte);
        } catch (GlobalPoolException e) {
            Log.minor(contexte.getCodeUtilisateur(), "RateFacadePoolable", "getContractsPeriodes", "exception au call en mode pool", e);
            throw new RatesTechnicalException(e);
        } finally {
            returnObjectToPool(RateFacade.POOL_NAME,objectPooled);
        }
	}
	public List<RateContractInfo> getContractsForRateLevel(long idGrille, String codeRateLevel, String status, Contexte contexte) throws RatesTechnicalException {
		RatePooled objectPooled = null;
        try {
            objectPooled = (RatePooled)getObjectPooled(RateFacade.POOL_NAME);
            return objectPooled.getContractsForRateLevel(idGrille, codeRateLevel, status, contexte);
        } catch (GlobalPoolException e) {
            Log.minor(contexte.getCodeUtilisateur(), "RateFacadePoolable", "getContractsForRateLevel", "exception au call en mode pool", e);
            throw new RatesTechnicalException(e);
        } finally {
            returnObjectToPool(RateFacade.POOL_NAME,objectPooled);
        }
	}
	public void importGrilleTarif(String rateKey, GrilleBean oldGrilleData, PeriodeValiditeRefBean activePeriodeValidite, Contexte contexte) throws RatesTechnicalException, RatesUserException{
		RatePooled objectPooled = null;
        try {
            objectPooled = (RatePooled)getObjectPooled(RateFacade.POOL_NAME);
             objectPooled.importGrilleTarif(rateKey, oldGrilleData, activePeriodeValidite, contexte);
        } catch (GlobalPoolException e) {
            Log.minor(contexte.getCodeUtilisateur(), "RateFacadePoolable", "importGrilleTarif", "exception au call en mode pool", e);
            throw new RatesTechnicalException(e);
        } finally {
            returnObjectToPool(RateFacade.POOL_NAME,objectPooled);
        }
	}
	public RateBean getRateById(String rateKey, RateBean rate, Contexte contexte) throws RatesTechnicalException {
		RatePooled objectPooled = null;
        try {
            objectPooled = (RatePooled)getObjectPooled(RateFacade.POOL_NAME);
            return   objectPooled.getRateById(rateKey, rate, contexte);
        } catch (GlobalPoolException e) {
            Log.minor(contexte.getCodeUtilisateur(), "RateFacadePoolable", "getRateById", "exception au call en mode pool", e);
            throw new RatesTechnicalException(e);
        } finally {
            returnObjectToPool(RateFacade.POOL_NAME,objectPooled);
        }
	}
	public List<ControleFinSaisieBean> getControlesEnEchec (String codeHotel, int idGroupeTarif, int idPeriodeValidite, Contexte contexte) throws RatesTechnicalException {
		RatePooled objectPooled = null;
		try {
			objectPooled = (RatePooled) getObjectPooled(RateFacade.POOL_NAME);
			return objectPooled.getControlesEnEchec(codeHotel, idGroupeTarif, idPeriodeValidite, contexte);
		} catch (GlobalPoolException e) {
			Log.minor(contexte.getCodeUtilisateur(), "RateFacadePoolable", "getControlesEnEchec", "Exception au call en mode pool", e);
			throw new RatesTechnicalException(e);
		} finally {
			returnObjectToPool(RateFacade.POOL_NAME, objectPooled);
		}
	}
	public void updateStatutGrille (String codeHotel, int idGroupeTarif, int idPeriodeValidite, int idStatut, Contexte contexte) throws RatesTechnicalException {
		RatePooled objectPooled = null;
		try {
			objectPooled = (RatePooled) getObjectPooled(RateFacade.POOL_NAME);
			objectPooled.updateStatutGrille(codeHotel, idGroupeTarif, idPeriodeValidite, idStatut, contexte);
		} catch (GlobalPoolException e) {
			Log.minor(contexte.getCodeUtilisateur(), "RateFacadePoolable", "updateStatutGrille", "Exception au call en mode pool", e);
			throw new RatesTechnicalException(e);
		} finally {
			returnObjectToPool(RateFacade.POOL_NAME, objectPooled);
		}
	}

    public void updateStatutGrille (long idGrille, int idStatut, Contexte contexte) throws RatesTechnicalException {
        RatePooled objectPooled = null;
        try {
            objectPooled = (RatePooled) getObjectPooled(RateFacade.POOL_NAME);
            objectPooled.updateStatutGrille(idGrille, idStatut, contexte);
        } catch (GlobalPoolException e) {
            Log.minor(contexte.getCodeUtilisateur(), "RateFacadePoolable", "updateStatutGrille", "Exception au call en mode pool", e);
            throw new RatesTechnicalException(e);
        } finally {
            returnObjectToPool(RateFacade.POOL_NAME, objectPooled);
        }
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
        RatePooled objectPooled = null;
        try {
            objectPooled = (RatePooled) getObjectPooled(RateFacade.POOL_NAME);
            return objectPooled.getTaxes(codeHotel, contexte);
        } catch (GlobalPoolException e) {
            Log.minor(contexte.getCodeUtilisateur(), "RateFacadePoolable", "getTaxes", "Exception au call en mode pool", e);
            throw new RatesTechnicalException(e);
        } finally {
            returnObjectToPool(RateFacade.POOL_NAME, objectPooled);
        }
    }
    /**
     * Retourne une taxe d'un hotel
     * @param codeHotel
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     */
    public TaxeBean getTaxe(long idTaxe, Contexte contexte) throws RatesTechnicalException {
        RatePooled objectPooled = null;
        try {
            objectPooled = (RatePooled) getObjectPooled(RateFacade.POOL_NAME);
            return objectPooled.getTaxe(idTaxe, contexte);
        } catch (GlobalPoolException e) {
            Log.minor(contexte.getCodeUtilisateur(), "RateFacadePoolable", "getTaxe", "Exception au call en mode pool", e);
            throw new RatesTechnicalException(e);
        } finally {
            returnObjectToPool(RateFacade.POOL_NAME, objectPooled);
        }
    }
    /**
     * Ajouter une taxe
     * @param taxe
     * @param contexte
     * @throws RatesTechnicalException
     * @throws RatesUserException
     */
    public void addTaxe(TaxeBean taxe, Contexte contexte) throws RatesTechnicalException, RatesUserException {
        RatePooled objectPooled = null;
        try {
            objectPooled = (RatePooled) getObjectPooled(RateFacade.POOL_NAME);
            objectPooled.addTaxe(taxe, contexte);
        } catch (GlobalPoolException e) {
            Log.minor(contexte.getCodeUtilisateur(), "RateFacadePoolable", "addTaxe", "Exception au call en mode pool", e);
            throw new RatesTechnicalException(e);
        } finally {
            returnObjectToPool(RateFacade.POOL_NAME, objectPooled);
        }
    }
    /**
     * Supprimer une taxe
     * @param idTaxe
     * @param contexte
     * @throws RatesTechnicalException
     * @throws RatesUserException
     */
    public void deleteTaxe(long idTaxe, Contexte contexte) throws RatesTechnicalException, RatesUserException {
        RatePooled objectPooled = null;
        try {
            objectPooled = (RatePooled) getObjectPooled(RateFacade.POOL_NAME);
            objectPooled.deleteTaxe(idTaxe, contexte);
        } catch (GlobalPoolException e) {
            Log.minor(contexte.getCodeUtilisateur(), "RateFacadePoolable", "deleteTaxe", "Exception au call en mode pool", e);
            throw new RatesTechnicalException(e);
        } finally {
            returnObjectToPool(RateFacade.POOL_NAME, objectPooled);
        }
    }

}