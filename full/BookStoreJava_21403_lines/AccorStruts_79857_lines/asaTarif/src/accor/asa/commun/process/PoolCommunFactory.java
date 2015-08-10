package com.accor.asa.commun.process;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.donneesdereference.process.DonneeRefGeneriqueFacade;
import com.accor.asa.commun.habilitation.process.HabilitationFacade;
import com.accor.asa.commun.hotel.process.HotelFacade;
import com.accor.asa.commun.infos.process.CommunInfos;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.process.pool.GlobalPoolUtil;
import com.accor.asa.commun.reference.process.RefFacade;
import com.accor.asa.commun.user.process.UserFacade;
import com.accor.asa.commun.utiles.process.CommunUtilsFacade;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 14 juin 2005
 * Time: 14:47:58
 * To change this template use File | Settings | File Templates.
 */
public class PoolCommunFactory  extends AbstractPoolFactory {


    /** single instance **/
    private static PoolCommunFactory instance = null;

    /** Interface  DonneeRefGeneriqueFacade **/
    private DonneeRefGeneriqueFacade donneeRefGeneriqueFacade = null;
    /** Interface  ReferenceDataFacade **/
    private RefFacade refFacade = null;
    /** Interface  DonneeRefGeneriqueFacade **/
    private CommunUtilsFacade communUtilsFacade = null;
    /** Interface  CommunInfos **/
    private CommunInfos communInfos = null;
    /** Interface UserFacade **/
    private UserFacade userFacade = null;
    /** l'interface HabilitationFacade **/
    private HabilitationFacade habilitationFacade = null;
    /** l'interface HotelFacade **/
    private HotelFacade hotelFacade = null;


    /**
     * Constructeur
     */
    private PoolCommunFactory() {
    }

    /**
     * getInstance
     * @return
     */
    public static PoolCommunFactory getInstance() {
       if (instance == null) {
          instance = new PoolCommunFactory();
       }
       return instance;
    }

    /**
     * Return l'interfqce DonneeRefGeneriqueFacade
     * @return
     * @throws IncoherenceException
     * @throws TechnicalException
     */
    public DonneeRefGeneriqueFacade getDonneeRefGenerique () throws IncoherenceException,
            TechnicalException {
        if (donneeRefGeneriqueFacade == null) {
            synchronized (PoolCommunFactory.class) {
                    try {
                        donneeRefGeneriqueFacade = (DonneeRefGeneriqueFacade) GlobalPoolUtil.getClassPoolable(DonneeRefGeneriqueFacade.POOL_NAME).newInstance();
                    } catch (InstantiationException e) {
                        LogCommun.major("COMMUN","PoolCommunFactory", "getDonneeRefGenerique","Pb while initializing ObjectPoolable", e);
                        throw new TechnicalException(e);
                    } catch (IllegalAccessException e) {
                        LogCommun.major("COMMUN","PoolCommunFactory", "getDonneeRefGenerique","Pb while initializing ObjectPoolable", e);
                        throw new TechnicalException(e);
                    }
            }
        }
        return donneeRefGeneriqueFacade;
    }

    public RefFacade getRefFacade () throws IncoherenceException, TechnicalException {
    	if (refFacade == null) {
    		synchronized (PoolCommunFactory.class) {
	            try {
	            	refFacade = (RefFacade) GlobalPoolUtil.getClassPoolable(RefFacade.POOL_NAME).newInstance();
	            } catch (InstantiationException e) {
	                LogCommun.major("COMMUN","PoolCommunFactory", "getRefFacade","Pb while initializing ObjectPoolable", e);
	                throw new TechnicalException(e);
	            } catch (IllegalAccessException e) {
	                LogCommun.major("COMMUN","PoolCommunFactory", "getRefFacade","Pb while initializing ObjectPoolable", e);
	                throw new TechnicalException(e);
	            }
            }
    	}
    	return refFacade;
	}

    /**
     * Return l'interfqce DonneeRefGeneriqueFacade
     * @return
     * @throws IncoherenceException
     * @throws TechnicalException
     */
    public CommunUtilsFacade getCommunUtilsFacade () throws IncoherenceException,
            TechnicalException {
        if (communUtilsFacade == null) {
            synchronized (PoolCommunFactory.class) {
                    try {
                        communUtilsFacade = (CommunUtilsFacade) GlobalPoolUtil.getClassPoolable(CommunUtilsFacade.POOL_NAME).newInstance();
                    } catch (InstantiationException e) {
                        LogCommun.major("COMMUN","PoolCommunFactory", "getCommunUtilsFacade","Pb while initializing ObjectPoolable", e);
                        throw new TechnicalException(e);
                    } catch (IllegalAccessException e) {
                        LogCommun.major("COMMUN","PoolCommunFactory", "getCommunUtilsFacade","Pb while initializing ObjectPoolable", e);
                        throw new TechnicalException(e);
                    }
            }
        }
        return communUtilsFacade;
    }
    /**
     * Return l'interfqce CommunInfos
     * @return
     * @throws IncoherenceException
     * @throws TechnicalException
     */
    public CommunInfos getCommunInfos () throws IncoherenceException,
            TechnicalException {
        if (communInfos == null) {
            synchronized (PoolCommunFactory.class) {
                    try {
                        communInfos = (CommunInfos) GlobalPoolUtil.getClassPoolable(CommunInfos.POOL_NAME).newInstance();
                    } catch (InstantiationException e) {
                        LogCommun.major("COMMUN","PoolCommunFactory", "getCommunInfos","Pb while initializing ObjectPoolable", e);
                        throw new TechnicalException(e);
                    } catch (IllegalAccessException e) {
                        LogCommun.major("COMMUN","PoolCommunFactory", "getCommunInfos","Pb while initializing ObjectPoolable", e);
                        throw new TechnicalException(e);
                    }
            }
        }
        return communInfos;
    }


    public UserFacade getUserFacade() throws TechnicalException, IncoherenceException {
    	if( userFacade == null ) {
            synchronized (PoolCommunFactory.class) {
                try {
                	userFacade = (UserFacade) GlobalPoolUtil.getClassPoolable(UserFacade.POOL_NAME).newInstance();
                } catch (InstantiationException e) {
                    LogCommun.major("COMMUN", "PoolCommunFactory", "getUserFacade", 
                    		"Pb while initializing ObjectPoolable", e);
                    throw new TechnicalException(e);
                } catch (IllegalAccessException e) {
                	LogCommun.major("COMMUN", "PoolCommunFactory", "getUserFacade", 
                			"Pb while initializing ObjectPoolable", e);
                    throw new TechnicalException(e);
                }
            }
        }
        return userFacade;
    }

    /**
     * Return l'interface  HabilitationFacade
     * @return
     * @throws IncoherenceException
     * @throws TechnicalException
     */
    public HabilitationFacade getHabilitationFacade() throws IncoherenceException,
            TechnicalException {
        if (habilitationFacade == null) {
            synchronized (PoolCommunFactory.class) {
                    try {
                        habilitationFacade = (HabilitationFacade) GlobalPoolUtil.getClassPoolable(
                        			HabilitationFacade.POOL_NAME).newInstance();
                    } catch (InstantiationException e) {
                    	LogCommun.major("COMMUN","PoolCommunFactory", "getHabilitationFacade",
                        		"Pb while initializing ObjectPoolable", e);
                        throw new TechnicalException(e);
                    } catch (IllegalAccessException e) {
                    	LogCommun.major("COMMUN","PoolCommunFactory", "getHabilitationFacade",
                        		"Pb while initializing ObjectPoolable", e);
                        throw new TechnicalException(e);
                    }
            }
        }
        return habilitationFacade;
    }

    /**
     * Return l'interface  HotelFacade
     * @return
     * @throws IncoherenceException
     * @throws TechnicalException
     */
    public HotelFacade getHotelFacade() throws IncoherenceException, TechnicalException {
        if (hotelFacade == null) {
            synchronized (PoolCommunFactory.class) {
                    try {
                    	hotelFacade = (HotelFacade) GlobalPoolUtil.getClassPoolable(
                    			HotelFacade.POOL_NAME).newInstance();
                    } catch (InstantiationException e) {
                    	LogCommun.major("COMMUN","PoolCommunFactory", "getHotelFacade",
                        		"Pb while initializing ObjectPoolable", e);
                        throw new TechnicalException(e);
                    } catch (IllegalAccessException e) {
                    	LogCommun.major("COMMUN","PoolCommunFactory", "getHotelFacade",
                        		"Pb while initializing ObjectPoolable", e);
                        throw new TechnicalException(e);
                    }
            }
        }
        return hotelFacade;
    }
}
