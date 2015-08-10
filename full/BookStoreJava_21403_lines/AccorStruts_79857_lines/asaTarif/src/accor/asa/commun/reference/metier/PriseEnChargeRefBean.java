package com.accor.asa.commun.reference.metier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 14 août 2007
 * Time: 10:22:04
 */
@SuppressWarnings("serial")
public class PriseEnChargeRefBean extends RefBean {

    public PriseEnChargeRefBean() {
    }

    /**
     * Cette méthode statique fournit la liste des modes
     * à partir du cache  dans une Liste
     * @param contexte
     * @return
     * @throws com.accor.asa.commun.TechnicalException
     */
	public static List<PriseEnChargeRefBean> getListPriseEnCharge(Contexte contexte)
            throws TechnicalException {
        try {
            return ((PriseEnChargeCachList) PoolCommunFactory.getInstance().getRefFacade().
                    getCacheRefList(RefFacade.PRISE_EN_CHARGE_REF_KEY, contexte)).getElements();
        } catch (IncoherenceException e) {
            throw new TechnicalException(e);
        }
    }

    /**
     * Cette méthode statique fournit la liste des modes
     * à partir du cache dans une Map
     * @param contexte
     * @return Map<String,PriseEnChargeRefBean>
     * @throws TechnicalException
     */
	public static Map<String,PriseEnChargeRefBean> getMapPriseEnCharge(Contexte contexte)
            throws TechnicalException {
        Map<String,PriseEnChargeRefBean> mapCares = new HashMap<String,PriseEnChargeRefBean>();
        List<PriseEnChargeRefBean> cares  = getListPriseEnCharge(contexte);
        if( cares !=null && cares .size()>0) {
            for( PriseEnChargeRefBean mr : cares ) {
                if (mr!=null) mapCares.put(mr.getCode(),mr);
            }
        }
        return mapCares;
    }
}
