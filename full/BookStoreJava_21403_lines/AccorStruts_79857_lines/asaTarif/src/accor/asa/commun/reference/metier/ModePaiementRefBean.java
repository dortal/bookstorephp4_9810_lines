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

@SuppressWarnings("serial")
public class ModePaiementRefBean extends RefBean {

	private String description = null;
	private boolean deposit = false;
	private boolean dateExigibilite = false;

	public ModePaiementRefBean () {
	}

	public String getDescription () {
		return description;
	}

	public boolean getDeposit () {
		return deposit;
	}

	public boolean getDateExigibilite () {
		return dateExigibilite;
	}

	public void setDescription (String description) {
		this.description = description;
	}

	public void setDeposit (boolean deposit) {
		this.deposit = deposit;
	}

	public void setDateExigibilite (boolean dateExigibilite) {
		this.dateExigibilite = dateExigibilite;
	}
    /**
     * Cette méthode statique fournit la liste des modes
     * à partir du cache  dans une Liste
     * @param contexte
     * @return List<ModePaiementRefBean>
     * @throws TechnicalException
     */
	public static List<ModePaiementRefBean> getListModePaiement( final Contexte contexte )
            throws TechnicalException {
        try {
            return ((ModePaiementCachList)PoolCommunFactory.getInstance().getRefFacade().
                    getCacheRefList(RefFacade.MODE_PAIEMENT_REF_KEY, contexte)).getElements();
        } catch (IncoherenceException e) {
            throw new TechnicalException(e);
        }
    }

    /**
     * Cette méthode statique fournit la liste des modes
     * à partir du cache dans une Map
     * @param contexte
     * @return Map<String,ModePaiementRefBean>
     * @throws TechnicalException
     */
	public static Map<String,ModePaiementRefBean> getMapModePaiement( final Contexte contexte )
            throws TechnicalException {
		Map<String,ModePaiementRefBean> mapModes = new HashMap<String,ModePaiementRefBean>();
        List<ModePaiementRefBean> modes  = getListModePaiement(contexte);
        if( modes!=null && modes.size()>0 ) {
            for( ModePaiementRefBean mr : modes ) {
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
        	ModePaiementByTOCachList params = (ModePaiementByTOCachList)PoolCommunFactory.getInstance().getRefFacade().
                    getCacheRefList(RefFacade.MODE_PAIEMENT_BY_TO_REF_KEY, contexte);
            if( params != null ) {
                for( ParamRefBean prb : params.getElements() ) {
                    if (codeTyypeOffre.equals(prb.getCode())) {
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
     * @return List<ModePaiementRefBean>
     * @throws TechnicalException
     */
	public static List<ModePaiementRefBean> getModePaiementByTO( final String codeTyypeOffre, 
			final Contexte contexte ) throws TechnicalException {
        List<ModePaiementRefBean> modesByParams = new ArrayList<ModePaiementRefBean>();
        List<ElementWithState> 			listParams  = getListParams(codeTyypeOffre, contexte);
        Map<String,ModePaiementRefBean> mapModes    = getMapModePaiement(contexte);
        ModePaiementRefBean mode;
        if( listParams!=null && listParams.size()>0 ) {
            for( ElementWithState element : listParams ) {
                if( element!=null ) {
                    mode = mapModes.get(element.getCode());
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
	public static ModePaiementRefBean getDefaultModePaiementByTO( final String codeTyypeOffre, 
			final Contexte contexte ) throws TechnicalException {
        ModePaiementRefBean mrb = null;
        List<ElementWithState> 			listParams 	= getListParams(codeTyypeOffre, contexte);
        Map<String,ModePaiementRefBean> mapModes    = getMapModePaiement(contexte);
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