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
public class ModeReservationRefBean extends RefBean {

	private boolean tars = false;
	private boolean gds = false;

	public ModeReservationRefBean () {
	}

	public boolean getTars () {
		return tars;
	}

	public boolean getGds () {
		return gds;
	}

	public void setTars (boolean tars) {
		this.tars = tars;
	}

	public void setGds (boolean gds) {
		this.gds = gds;
	}

    /**
     * Cette méthode statique fournit la liste des modes
     * à partir du cache  dans une Liste
     * @param contexte
     * @return List<ModeReservationRefBean>
     * @throws TechnicalException
     */
	public static List<ModeReservationRefBean> getListModeReservation( final Contexte contexte )
            throws TechnicalException {
        try {
            return ((ModeReservationCachList)PoolCommunFactory.getInstance().getRefFacade().
                    getCacheRefList(RefFacade.MODE_RESERVATION_REF_KEY, contexte)).getElements();
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
	public static Map<String,ModeReservationRefBean> getMapModeReservation( final Contexte contexte )
            throws TechnicalException {
        Map<String,ModeReservationRefBean> mapModes = new HashMap<String,ModeReservationRefBean>();
        List<ModeReservationRefBean> modes  = getListModeReservation(contexte);
        if( modes!=null && modes.size()>0 ) {
            for( ModeReservationRefBean mr : modes ) {
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
            ModeReservationByTOCachList params = (ModeReservationByTOCachList)PoolCommunFactory.getInstance().getRefFacade().
                    getCacheRefList(RefFacade.MODE_RESERVATION_BY_TO_REF_KEY, contexte);
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
     * @return List<ModeReservationRefBean>
     * @throws TechnicalException
     */
	public static List<ModeReservationRefBean> getModeReservationByTO( final String codeTyypeOffre, 
			final Contexte contexte ) throws TechnicalException {
        List<ModeReservationRefBean> modesByParams = new ArrayList<ModeReservationRefBean>();
        List<ElementWithState> 				listParams 	= getListParams(codeTyypeOffre, contexte);
        Map<String,ModeReservationRefBean> 	mapModes    = getMapModeReservation(contexte);
        ModeReservationRefBean mode;
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
	public static ModeReservationRefBean getDefaultModeReservationByTO( final String codeTyypeOffre, 
			final Contexte contexte ) throws TechnicalException {
        ModeReservationRefBean mrb = null;
        List<ElementWithState> 				listParams 	= getListParams(codeTyypeOffre, contexte);
        Map<String,ModeReservationRefBean> 	mapModes    = getMapModeReservation(contexte);
        if( listParams!=null && listParams.size()>0 ) {
            for( ElementWithState element : listParams ) {
                if (    element!=null &&
                        element.isState() &&
                        mapModes.get( element.getCode() )!=null   )
                    mrb = mapModes.get(element.getCode());
            }
        }
        return mrb;
    }
}