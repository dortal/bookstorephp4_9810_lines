package com.accor.asa.commun.tronccommun;

import java.io.InputStream;

import com.accor.asa.commun.TechnicalException;

/**
 * Client Tronc Commun.
 * <p>
 * Un client Tronc Commun est une classe permettant d'invoquer une methode presente sur 
 * et de recuperer le resultat sous la forme d'un objet java.
 * </p>
 */
public interface TcClient {
    /** Le nom du pool d'objet*/
    public static final String POOL_NAME   = "communTcClient";

    /**
     * Method call
     * @param procedureName
     * @param parametersNames
     * @param queryParameters
     * @param codeUtilisateur
     * @return
     * @throws TechnicalException
     */
    public InputStream call(    String procedureName,
                                String[] parametersNames,
                                String[] queryParameters,
                                String codeUtilisateur )
            throws TechnicalException;
    /**
     * createObjectFromCommand
     * @param objectFactoryClass
     * @param methodName
     * @param paramNames
     * @param paramValues
     * @param login
     * @return
     * @throws TechnicalException
     */
    public Object createObjectFromCommand (Class<?> objectFactoryClass,
                                           String methodName,
                                           String[] paramNames,
                                           String[] paramValues,
                                           String login)
            throws TechnicalException;
}
