package com.accor.asa.commun.infos.process.pool;

import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.infos.process.CommunInfos;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.grille.Grille;
import com.accor.asa.commun.metier.grille.ListStatutGrille;
import com.accor.asa.commun.metier.mealplan.ListMealPlan;
import com.accor.asa.commun.metier.validationinfo.ValidationHotel;
import com.accor.asa.commun.metier.validationinfo.ValidationInfo;
import com.accor.asa.commun.persistance.ContexteAppelDAO;
import com.accor.asa.commun.process.pool.GlobalPoolException;
import com.accor.asa.commun.process.pool.GlobalPoolable;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 25 oct. 2006
 * Time: 13:33:05
 * To change this template use File | Settings | File Templates.
 */
public class CommunInfosPoolable extends GlobalPoolable implements CommunInfos {
    /** init du pool en static */
    static {
        initPool(CommunInfos.POOL_NAME);
    }

    //========================== METHODES METIERS =======================================================

    /**
     * Renvoie les dates de validation d'un hotel
     *
     * @param codeHotel
     * @param contexte
     * @return
     * @throws com.accor.asa.commun.TechnicalException
     *
     */
    public ValidationInfo getValidationInfo(String codeHotel, ContexteAppelDAO contexte)
            throws TechnicalException {
        CommunInfosPooled objectPooled = null;
        try {
            objectPooled = (CommunInfosPooled) getObjectPooled(CommunInfos.POOL_NAME);
            return objectPooled.getValidationInfo(codeHotel, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getLogin(), "CommunInfosPoolable", "getValidationInfo", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getLogin(), "CommunInfosPoolable", "getValidationInfo", "exception au call en mode pool", e);
            throw e;
        } finally {
            returnObjectToPool(CommunInfos.POOL_NAME, objectPooled);
        }
    }
    /**
     * Retourne les dates de validations pour une liste d'hotels.
     * @param listehotels
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public List<ValidationHotel> getValidationHotels(String listehotels, ContexteAppelDAO contexte)
            throws TechnicalException {
        CommunInfosPooled objectPooled = null;
        try {
            objectPooled = (CommunInfosPooled) getObjectPooled(CommunInfos.POOL_NAME);
            return objectPooled.getValidationHotels(listehotels, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getLogin(), "CommunInfosPoolable", "getValidationHotels", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getLogin(), "CommunInfosPoolable", "getValidationHotels", "exception au call en mode pool", e);
            throw e;
        } finally {
            returnObjectToPool(CommunInfos.POOL_NAME, objectPooled);
        }
    }

    /**
     * etourne la liste des grilles tarifaires
     * pour un ou plusieurs hotels
     * @param listehotels
     * @param codelangue
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public List<Grille> getStatutGrilles(String listehotels, String codelangue, ContexteAppelDAO contexte)
            throws TechnicalException {
        CommunInfosPooled objectPooled = null;
        try {
            objectPooled = (CommunInfosPooled) getObjectPooled(CommunInfos.POOL_NAME);
            return objectPooled.getStatutGrilles(listehotels, codelangue, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getLogin(), "CommunInfosPoolable", "getStatutGrilles", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getLogin(), "CommunInfosPoolable", "getStatutGrilles", "exception au call en mode pool", e);
            throw e;
        } finally {
            returnObjectToPool(CommunInfos.POOL_NAME, objectPooled);
        }
    }

    /**
     * retourne la liste des statut grilles
     * @param codeLangue
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public ListStatutGrille getListeStatutGrille(String codeLangue, ContexteAppelDAO contexte)
            throws TechnicalException {
        CommunInfosPooled objectPooled = null;
        try {
            objectPooled = (CommunInfosPooled) getObjectPooled(CommunInfos.POOL_NAME);
            return objectPooled.getListeStatutGrille(codeLangue, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getLogin(), "CommunInfosPoolable", "getListeStatutGrille", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getLogin(), "CommunInfosPoolable", "getListeStatutGrille", "exception au call en mode pool", e);
            throw e;
        } finally {
            returnObjectToPool(CommunInfos.POOL_NAME, objectPooled);
        }
    }

    /**
     * Retourne la liste des MealPlans
     * @param codeLangue
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public ListMealPlan getListeMealPlan(String codeLangue, ContexteAppelDAO contexte)
            throws TechnicalException {
        CommunInfosPooled objectPooled = null;
        try {
            objectPooled = (CommunInfosPooled) getObjectPooled(CommunInfos.POOL_NAME);
            return objectPooled.getListeMealPlan(codeLangue, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getLogin(), "CommunInfosPoolable", "getListeMealPlan", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getLogin(), "CommunInfosPoolable", "getListeMealPlan", "exception au call en mode pool", e);
            throw e;
        } finally {
            returnObjectToPool(CommunInfos.POOL_NAME, objectPooled);
        }
    }
}
