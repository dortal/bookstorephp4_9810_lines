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
 * Time: 13:39:01
 */
@SuppressWarnings("serial")
public class RemunerationTypeRefBean  extends RefBean {

    public RemunerationTypeRefBean() {
    }

    /**
     * Cette méthode statique fournit la liste des modes
     * à partir du cache  dans une Liste
     * @param contexte
     * @return List<RemunerationTypeRefBean>
     * @throws com.accor.asa.commun.TechnicalException
     */
	public static List<RemunerationTypeRefBean> getListRemunerationType( final Contexte contexte )
            throws TechnicalException {
        try {
            return ((RemunerationTypeCachList) PoolCommunFactory.getInstance().getRefFacade().
                    getCacheRefList(RefFacade.REMUNERATION_TYPE_REF_KEY, contexte)).getElements();
        } catch (IncoherenceException e) {
            throw new TechnicalException(e);
        }
    }

    /**
     * Cette méthode statique fournit la liste des modes
     * à partir du cache dans une Map
     * @param contexte
     * @return Map<String,RemunerationTypeRefBean>
     * @throws TechnicalException
     */
	public static Map<String,RemunerationTypeRefBean> getMapRemunerationType( final Contexte contexte )
            throws TechnicalException {
        Map<String,RemunerationTypeRefBean> mapTypes = new HashMap<String,RemunerationTypeRefBean>();
        List<RemunerationTypeRefBean> types = getListRemunerationType(contexte);
        if(types!=null && types.size()>0) {
            for( RemunerationTypeRefBean tr : types ) {
                if (tr!=null) mapTypes.put(tr.getCode(),tr);
            }
        }
        return mapTypes;
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
            RemunerationTypeByTOCachList params = (RemunerationTypeByTOCachList)PoolCommunFactory.getInstance().getRefFacade().
                    getCacheRefList(RefFacade.REMUNERATION_TYPE_BY_TO_REF_KEY, contexte);
            
            if( params!=null ) {
                for( ParamRefBean prb : params.getElements() ) {
                    if( codeTyypeOffre.equals( prb.getCode() ) ) {
                        listParams = prb.getParams();
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
     * @return List<RemunerationTypeRefBean>
     * @throws TechnicalException
     */
	public static List<RemunerationTypeRefBean> getRemunerationTypeByTO( final String codeTyypeOffre, 
			final Contexte contexte ) throws TechnicalException {
        List<RemunerationTypeRefBean> modesByParams = new ArrayList<RemunerationTypeRefBean>();
        List<ElementWithState> 				listParams 	= getListParams(codeTyypeOffre, contexte);
        Map<String,RemunerationTypeRefBean> mapModes    = getMapRemunerationType(contexte);
        RemunerationTypeRefBean mode;
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
     * @return RemunerationTypeRefBean
     * @throws TechnicalException
     */
	public static RemunerationTypeRefBean getDefaultRemunerationTypeByTO( final String codeTyypeOffre, 
			final Contexte contexte ) throws TechnicalException {
        RemunerationTypeRefBean mrb = null;
        List<ElementWithState> 				listParams 	= getListParams(codeTyypeOffre, contexte);
        Map<String,RemunerationTypeRefBean> mapModes    = getMapRemunerationType(contexte);
        if( listParams!=null && listParams.size()>0 ) {
            for( ElementWithState element : listParams ) {
                if (    element!=null &&
                        element.isState() &&
                        mapModes.get( element.getCode() )!=null   )
                    mrb = mapModes.get( element.getCode() );
            }
        }
        return mrb;
    }

}
