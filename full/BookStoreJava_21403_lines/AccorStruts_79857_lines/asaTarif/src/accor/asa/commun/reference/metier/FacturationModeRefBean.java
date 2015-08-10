package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.ElementWithState;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 2 août 2007
 * Time: 15:14:48
 */
@SuppressWarnings("serial")
public class FacturationModeRefBean extends RefBean {

    public FacturationModeRefBean() {
    }

    /**
     * Cette méthode statique fournit la liste des modes
     * à partir du cache  dans une Liste
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public static List<FacturationModeRefBean> getListFacturationMode(Contexte contexte)
            throws TechnicalException {
        try {
            return ((FacturationModeCachList) PoolCommunFactory.getInstance().getRefFacade().
                    getCacheRefList(RefFacade.FACTURATION_MODE_REF_KEY, contexte)).get();
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
    public static Map<String, FacturationModeRefBean> getMapFacturationMode(Contexte contexte)
            throws TechnicalException {
        Map<String, FacturationModeRefBean> mapModes = new HashMap<String, FacturationModeRefBean>();
        List<FacturationModeRefBean> modes  = getListFacturationMode(contexte);
        FacturationModeRefBean mr;
        if (modes!=null && modes.size()>0) {
            for (int i=0,size=modes.size(); i<size; i++) {
                mr = modes.get(i);
                if (mr!=null) mapModes.put(mr.getCode(),mr);
            }
        }
        return mapModes;
    }
    /**
     * Cette méthode statique fournit la liste des modes filtrée
     * en fonction du type d'offre à partir du cache,
     * @param codeTyypeOffre
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	private static List getListParams(String codeTyypeOffre, Contexte contexte)
            throws TechnicalException {
        List listParams = null;
        try {
            FacturationModeByTOCachList params = (FacturationModeByTOCachList)PoolCommunFactory.getInstance().getRefFacade().
                    getCacheRefList(RefFacade.FACTURATION_MODE_BY_TO_REF_KEY, contexte);
            ParamRefBean prb;
            if (params!=null && params.size()>0) {
                for (int i=0,size=params.size(); i<size; i++) {
                    prb = (ParamRefBean)params.get(i);
                    if (codeTyypeOffre.equals(prb.getCode())) {
                        listParams = prb.getParams();
                        i=size;
                    }
                }
            }
        } catch (IncoherenceException e) {
            throw new TechnicalException(e);
        }
        return listParams;
    }
    /**
     * Cette méthode statique fournit la liste des modes filtrée
     * en fonction du type d'offre à partir du cache ,
     * le premier element est celui par defaut.
     * @param codeTyypeOffre
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public static List getFacturationModeByTO(String codeTyypeOffre, Contexte contexte)
            throws TechnicalException {
        List modesByParams = new ArrayList();
        List listParams = getListParams(codeTyypeOffre, contexte);
        Map mapModes    = getMapFacturationMode(contexte);
        ElementWithState element;
        FacturationModeRefBean mode;
        if (listParams!=null && listParams.size()>0) {
            for (int i=0,size=listParams.size(); i<size; i++) {
                element = (ElementWithState)listParams.get(i);
                if (element!=null) {
                    mode = (FacturationModeRefBean)mapModes.get(element.getCode());
                    if(mode!=null) {
                        mode.setActif(element.isState());
                        modesByParams.add(mode);
                    }
                }
            }
        }
        return modesByParams;
    }

    /**
     * Cette méthode statique fournit la liste des modes filtrée
     * en fonction du type d'offre à partir du cache ,
     * le premier element est celui par defaut.
     * @param codeTyypeOffre
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public static FacturationModeRefBean getDefaultFacturationModeByTO(String codeTyypeOffre, Contexte contexte)
            throws TechnicalException {
        FacturationModeRefBean mrb = null;
        List listParams = getListParams(codeTyypeOffre, contexte);
        Map<String, FacturationModeRefBean> mapModes    = getMapFacturationMode(contexte);
        ElementWithState element;
        if (listParams!=null && listParams.size()>0) {
            for (int i=0,size=listParams.size(); i<size; i++) {
                element = (ElementWithState)listParams.get(i);
                if (    element!=null &&
                        element.isState() &&
                        mapModes.get(element.getCode())!=null   )
                    mrb = mapModes.get(element.getCode());
            }
        }
        return mrb;
    }

}
