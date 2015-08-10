package com.accor.asa.commun.tronccommun.pool;

import java.io.InputStream;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.process.pool.GlobalPoolException;
import com.accor.asa.commun.process.pool.GlobalPoolable;
import com.accor.asa.commun.tronccommun.TcClient;
import com.accor.asa.commun.tronccommun.TcClientFactory;

/**
 * <p/>
 * Un TcClient utilisant un pool d'objet TcClientPooled.
 * </p>
 * NE PAS INSTANCIER DIRECTEMENT : utiliser {@link TcClientFactory#getTcClient}<br>
 * Cette classe utilise <a href="http://jakarta.apache.org/commons/pool">Jakarta Commons Pool</a>
 */
public final class TcClientPoolable extends GlobalPoolable implements TcClient {
    /** init du pool en static */
    static {
        initPool(TcClient.POOL_NAME);
    }

    //========================== METHODES METIERS =======================================================
    
    /**
     * Methode Call
     *
     * @param procedureName
     * @param parametersNames
     * @param queryParameters
     * @param codeUtilisateur
     * @return
     * @throws TechnicalException
     */
    public InputStream call(String procedureName,
                            String[] parametersNames,
                            String[] queryParameters,
                            String codeUtilisateur)
            throws TechnicalException {
        TcClientPooled objectPooled = null;
        try {
            objectPooled = (TcClientPooled)getObjectPooled(TcClient.POOL_NAME);
            return objectPooled.call(procedureName, parametersNames, queryParameters, codeUtilisateur);
        } catch (GlobalPoolException e) {
            LogCommun.minor("COMMUN", "TcClientPoolable", "getIdNational", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor("COMMUN", "TcClientPoolable", "getIdNational", "TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(TcClient.POOL_NAME,objectPooled);
        }
    }

    /**
     * @param objectFactoryClass
     * @param methodName
     * @param paramNames
     * @param paramValues
     * @param login
     * @return
     * @throws TechnicalException
     */
    public Object createObjectFromCommand(Class<?> objectFactoryClass,
                                          String methodName,
                                          String[] paramNames,
                                          String[] paramValues,
                                          String login)
            throws TechnicalException {
        TcClientPooled objectPooled = null;
        try {
            objectPooled = (TcClientPooled)getObjectPooled(TcClient.POOL_NAME);
            return objectPooled.createObjectFromCommand(objectFactoryClass, methodName, paramNames, paramValues, login);
        } catch (GlobalPoolException e) {
            LogCommun.minor("COMMUN", "TcClientPoolable", "getIdNational", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor("COMMUN", "TcClientPoolable", "getIdNational", "TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(TcClient.POOL_NAME,objectPooled);
        }
    }
}
