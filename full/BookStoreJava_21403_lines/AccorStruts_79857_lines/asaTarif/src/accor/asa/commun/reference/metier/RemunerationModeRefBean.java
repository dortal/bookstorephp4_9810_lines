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
 * Time: 13:39:13
 */
@SuppressWarnings("serial")
public class RemunerationModeRefBean  extends RefBean {

    public RemunerationModeRefBean() {
    }

    /**
     * Cette méthode statique fournit la liste des modes
     * à partir du cache  dans une Liste
     * @param contexte
     * @return List<RemunerationModeRefBean>
     * @throws com.accor.asa.commun.TechnicalException
     */
	public static List<RemunerationModeRefBean> getListRemunerationMode( final Contexte contexte )
            throws TechnicalException {
        try {
            return ((RemunerationModeCachList) PoolCommunFactory.getInstance().getRefFacade().
                    getCacheRefList(RefFacade.REMUNERATION_MODE_REF_KEY, contexte)).getElements();
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
    public static Map<String,RemunerationModeRefBean> getMapRemunerationMode( final Contexte contexte )
            throws TechnicalException {
        Map<String,RemunerationModeRefBean> mapModes = new HashMap<String,RemunerationModeRefBean>();
        List<RemunerationModeRefBean> modes  = getListRemunerationMode(contexte);
        if( modes!=null && modes.size()>0 ) {
            for( RemunerationModeRefBean mr : modes ) {
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
     * @return List<ElementWithState>
     * @throws TechnicalException
     */
    private static List<ElementWithState> getListParams( final String codeTyypeOffre, 
    		final Contexte contexte ) throws TechnicalException {
        List<ElementWithState> listParams = null;
        try {
            RemunerationModeByTOCachList params = (RemunerationModeByTOCachList)PoolCommunFactory.getInstance().getRefFacade().
                    getCacheRefList(RefFacade.REMUNERATION_MODE_BY_TO_REF_KEY, contexte);

            if( params!=null ) {
                for( RefBean prb : params.getElements() ) {
                    if( codeTyypeOffre.equals( prb.getCode() ) ) {
                        listParams = ( (ParamRefBean) prb ).getParams();
                        break;
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
     * @return List<RemunerationModeRefBean>
     * @throws TechnicalException
     */
    public static List<RemunerationModeRefBean> getRemunerationModeByTO(
    		final String codeTyypeOffre, final Contexte contexte ) throws TechnicalException {
        List<RemunerationModeRefBean> modesByParams = new ArrayList<RemunerationModeRefBean>();
        List<ElementWithState> listParams = getListParams(codeTyypeOffre, contexte);
        Map<String,RemunerationModeRefBean> mapModes    = getMapRemunerationMode(contexte);
        RemunerationModeRefBean mode;
        if( listParams!=null && listParams.size()>0 ) {
            for( ElementWithState element : listParams ) {
                if( element!=null ) {
                    mode = mapModes.get( element.getCode() );
                    if( mode!=null ) {
                        mode.setActif( element.isState() );
                        modesByParams.add( mode );
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
    public static RemunerationModeRefBean getDefaultRemunerationModeByTO( final String codeTyypeOffre, 
    		final Contexte contexte ) throws TechnicalException {
        RemunerationModeRefBean mrb = null;
        List<ElementWithState> listParams = getListParams(codeTyypeOffre, contexte);
        Map<String,RemunerationModeRefBean> mapModes  = getMapRemunerationMode(contexte);
        if( listParams!=null && listParams.size()>0 ) {
            for( ElementWithState element : listParams ) {
                if (    element!=null &&
                        element.isState() &&
                        mapModes.get(element.getCode())!=null   )
                    mrb = mapModes.get(element.getCode());
            }
        }
        return mrb;
    }

}
