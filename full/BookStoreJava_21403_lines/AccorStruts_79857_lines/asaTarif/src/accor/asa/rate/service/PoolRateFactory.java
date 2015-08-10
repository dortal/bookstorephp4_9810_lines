/**
 *
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
package com.accor.asa.rate.service;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.process.pool.GlobalPoolUtil;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.service.process.RateFacade;

public class PoolRateFactory {
    private static PoolRateFactory ourInstance = new PoolRateFactory();

    public static PoolRateFactory getInstance() {
        return ourInstance;
    }

    private PoolRateFactory() {
    }

    /**
     * Interface  RateFacade *
     */
    private RateFacade rateFacade = null;
    /**
     * return l'interface RateFacade
     *
     * @return
     * @throws TechnicalException
     */
    public RateFacade getRateFacade() throws RatesTechnicalException {
        if (rateFacade == null) {
            synchronized (PoolRateFactory.class) {
                try {
                    rateFacade = (RateFacade) GlobalPoolUtil.getClassPoolable(RateFacade.POOL_NAME).newInstance();
                } catch (InstantiationException e) {
                    Log.major("TARIF", "PoolTarifFactory", "getRateFacade", "Pb while initializing ObjectPoolable", e);
                    throw new RatesTechnicalException(e);
                } catch (IllegalAccessException e) {
                    Log.major("TARIF", "PoolTarifFactory", "getRateFacade", "Pb while initializing ObjectPoolable", e);
                    throw new RatesTechnicalException(e);
                }
                catch(TechnicalException e)
                {
                	throw new RatesTechnicalException(e);
                }
            }
        }
        return rateFacade;
    }


}
