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
 * Date: 2 août 2007
 * Time: 15:41:49
 * 
 */

@SuppressWarnings("serial")
public class RemunerationFrequenceRefBean extends RefBean {

    public RemunerationFrequenceRefBean() {
    }

    /**
     * Cette méthode statique fournit la liste des modes
     * à partir du cache  dans une Liste
     * @param contexte
     * @return List<RemunerationFrequenceRefBean>
     * @throws com.accor.asa.commun.TechnicalException
     */
    
	public static List<RemunerationFrequenceRefBean> getListRemunerationFrequence(Contexte contexte)
            throws TechnicalException {
        try {
            return ((RemunerationFrequenceCachList) PoolCommunFactory.getInstance().getRefFacade().
                    getCacheRefList(RefFacade.REMUNERATION_FREQUENCE_REF_KEY, contexte)).getElements();
        } catch (IncoherenceException e) {
            throw new TechnicalException(e);
        }
    }

    /**
     * Cette méthode statique fournit la liste des modes
     * à partir du cache dans une Map
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public static Map<String,RemunerationFrequenceRefBean> getMapRemunerationFrequence( 
    		final Contexte contexte ) throws TechnicalException {
        Map<String,RemunerationFrequenceRefBean> mapFrequencies = new HashMap<String,RemunerationFrequenceRefBean>();
        List<RemunerationFrequenceRefBean> frequencies  = getListRemunerationFrequence(contexte);
        if( frequencies!=null && frequencies.size()>0 ) {
            for( RemunerationFrequenceRefBean fr : frequencies ) {
                if (fr!=null) mapFrequencies.put(fr.getCode(),fr);
            }
        }
        return mapFrequencies;
    }
}
