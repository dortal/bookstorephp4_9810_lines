package com.accor.asa.commun.donneesdereference.process.pool;

import java.util.List;
import java.util.Map;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.donneesdereference.metier.DonneeReference;
import com.accor.asa.commun.donneesdereference.process.DonneeRefGeneriqueFacade;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.persistance.ContexteAppelDAO;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.pool.GlobalPoolException;
import com.accor.asa.commun.process.pool.GlobalPoolable;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 14 juin 2005
 * Time: 10:48:14
 * To change this template use File | Settings | File Templates.
 */
public class DonneeRefGeneriqueFacadePoolable extends GlobalPoolable implements DonneeRefGeneriqueFacade {

    /** init du pool en static */
    static {
        initPool(DonneeRefGeneriqueFacade.POOL_NAME);
    }

    //========================== METHODES METIERS =======================================================

    /**
     * getDonneesRef
     * @param typeDonneeRef
     * @param codeLangue
     * @param context
     * @return
     * @throws TechnicalException
     */
    public List getDonneesRef( Class typeDonneeRef, String codeLangue, ContexteAppelDAO context ) throws   TechnicalException {
        DonneeRefGeneriqueFacadePooled objectPooled = null;
        try {
            objectPooled = (DonneeRefGeneriqueFacadePooled)getObjectPooled(DonneeRefGeneriqueFacade.POOL_NAME);
            return objectPooled.getDonneesRef(typeDonneeRef, codeLangue, context);
        } catch (GlobalPoolException e) {
            LogCommun.minor("COMMUN","DonneeRefGeneriqueFacadePoolable","getDonneesRef","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor("COMMUN","DonneeRefGeneriqueFacadePoolable","getDonneesRef","exception au call en mode pool", e);
            throw e;
        } finally {
            returnObjectToPool(DonneeRefGeneriqueFacade.POOL_NAME,objectPooled);
        }
    }

    public Map getDonneesRefIntoMap(Class typeDonneeRef, String codeLangue, ContexteAppelDAO context) throws TechnicalException {
        DonneeRefGeneriqueFacadePooled objectPooled = null;
        try {
            objectPooled = (DonneeRefGeneriqueFacadePooled)getObjectPooled(DonneeRefGeneriqueFacade.POOL_NAME);
            return objectPooled.getDonneesRefIntoMap(typeDonneeRef, codeLangue, context);
        } catch (GlobalPoolException e) {
            LogCommun.minor("COMMUN","DonneeRefGeneriqueFacadePoolable","getDonneesRefIntoMap","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor("COMMUN","DonneeRefGeneriqueFacadePoolable","getDonneesRefIntoMap","exception au call en mode pool", e);
            throw e;
        } finally {
            returnObjectToPool(DonneeRefGeneriqueFacade.POOL_NAME,objectPooled);
        }
    }

    /**
     * getDonneesRefAdmin
     * @param typeDonneeRef
     * @param context
     * @return une liste d'instance de typeDonneeRef pour Admin (langue = GB et statut indifferent)
     * @throws TechnicalException
     */
    public List getDonneesRefAdmin(Class typeDonneeRef, ContexteAppelDAO context) throws TechnicalException {
        DonneeRefGeneriqueFacadePooled objectPooled = null;
        try {
            objectPooled = (DonneeRefGeneriqueFacadePooled)getObjectPooled(DonneeRefGeneriqueFacade.POOL_NAME);
            return objectPooled.getDonneesRefAdmin(typeDonneeRef, context);
        } catch (GlobalPoolException e) {
            LogCommun.minor("COMMUN","DonneeRefGeneriqueFacadePoolable","getDonneesRefAdmin","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor("COMMUN","DonneeRefGeneriqueFacadePoolable","getDonneesRefAdmin","exception au call en mode pool", e);
            throw e;
        } finally {
            returnObjectToPool(DonneeRefGeneriqueFacade.POOL_NAME,objectPooled);
        }
    }

    /**
     * Insère ou update un objet, en fonction de son état (nouveau ou existant)
     * @param donnee
     * @param context
     * @throws TechnicalException
     * @throws com.accor.asa.commun.process.IncoherenceException
     */
    public void saveDonneeRef(DonneeReference donnee, ContexteAppelDAO context) throws TechnicalException, IncoherenceException {
        DonneeRefGeneriqueFacadePooled objectPooled = null;
        try {
            objectPooled = (DonneeRefGeneriqueFacadePooled)getObjectPooled(DonneeRefGeneriqueFacade.POOL_NAME);
            objectPooled.saveDonneeRef(donnee, context);
        } catch (GlobalPoolException e) {
            LogCommun.minor("COMMUN","DonneeRefGeneriqueFacadePoolable","saveDonneeRef","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor("COMMUN","DonneeRefGeneriqueFacadePoolable","saveDonneeRef","exception au call en mode pool", e);
            throw e;
        } finally {
            returnObjectToPool(DonneeRefGeneriqueFacade.POOL_NAME,objectPooled);
        }
    }
    /**
     * deleteDonneeRef
     * @param typeDonneeRef
     * @param codeDonneeRef
     * @param context
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    public void deleteDonneeRef(Class typeDonneeRef, String codeDonneeRef, ContexteAppelDAO context) throws TechnicalException, IncoherenceException {
        DonneeRefGeneriqueFacadePooled objectPooled = null;
        try {
            objectPooled = (DonneeRefGeneriqueFacadePooled)getObjectPooled(DonneeRefGeneriqueFacade.POOL_NAME);
            objectPooled.deleteDonneeRef(typeDonneeRef, codeDonneeRef, context);
        } catch (GlobalPoolException e) {
            LogCommun.minor("COMMUN","DonneeRefGeneriqueFacadePoolable","deleteDonneeRef","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor("COMMUN","DonneeRefGeneriqueFacadePoolable","deleteDonneeRef","exception au call en mode pool", e);
            throw e;
        } finally {
            returnObjectToPool(DonneeRefGeneriqueFacade.POOL_NAME,objectPooled);
        }
    }
}
