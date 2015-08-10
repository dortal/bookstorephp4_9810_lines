package com.accor.asa.commun.tronccommun;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.process.pool.GlobalPoolUtil;


public final class TcClientFactory {
    /** single instance */
    private static TcClientFactory singleInstance = null;
    /** unique instance */
    private TcClient tcClient;
    /**
     * Single Instance
     * @return
     */
    public static TcClientFactory getInstance() {
        if (singleInstance == null) {
           singleInstance = new TcClientFactory();
        }
        return singleInstance;
    }
    /**
     *  see name
     */
    private TcClientFactory() {
        // pour empêcher construction externe
    }
    /**
     * Method init
     */
    public TcClient getTcClient() throws TechnicalException {
        if (tcClient == null) {
            synchronized (TcClientFactory.class) {
                    try {
                        tcClient = (TcClient) GlobalPoolUtil.getClassPoolable(TcClient.POOL_NAME).newInstance();
                    } catch (InstantiationException e) {
                        LogCommun.major("COMMUN","TcClientFactory", "init","Pb while initializing TcClientFactory", e);
                         throw new TechnicalException(e);
                    } catch (IllegalAccessException e) {
                        LogCommun.major("COMMUN","TcClientFactory", "init","Pb while initializing TcClientFactory", e);
                         throw new TechnicalException(e);
                    }
            }
        }
        return  tcClient;

    }
}