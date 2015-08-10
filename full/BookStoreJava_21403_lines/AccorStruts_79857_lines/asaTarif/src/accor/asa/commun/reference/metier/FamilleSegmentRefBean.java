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
 * Date: 31 oct. 2007
 * Time: 10:04:40
 */
@SuppressWarnings("serial")
public class FamilleSegmentRefBean  extends RefBean {

    public FamilleSegmentRefBean() {
    }

    /**
     * Cette méthode statique fournit la liste des modes
     * à partir du cache  dans une Liste
     * @param contexte
     * @return List<FamilleSegmentRefBean>
     * @throws com.accor.asa.commun.TechnicalException
     */
	public static List<FamilleSegmentRefBean> getListFamilleSegment(Contexte contexte)
            throws TechnicalException {
        try {
            return ((FamilleSegmentCachList) PoolCommunFactory.getInstance().getRefFacade().
                    getCacheRefList(RefFacade.FAMILLE_SEGMENT_REF_KEY, contexte)).getElements();
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
	public static Map<String,FamilleSegmentRefBean> getMapFamilleSegment(Contexte contexte)
            throws TechnicalException {
        Map<String,FamilleSegmentRefBean> mapModes = new HashMap<String,FamilleSegmentRefBean>();
        List<FamilleSegmentRefBean> modes  = getListFamilleSegment(contexte);
        if( modes!=null && modes.size()>0 ) {
            for( FamilleSegmentRefBean mr : modes ) {
                if (mr!=null) mapModes.put(mr.getCode(),mr);
            }
        }
        return mapModes;
    }
}
