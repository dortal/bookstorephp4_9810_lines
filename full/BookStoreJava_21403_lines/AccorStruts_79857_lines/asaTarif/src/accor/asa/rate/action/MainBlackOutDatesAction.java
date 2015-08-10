package com.accor.asa.rate.action;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.commun.reference.metier.StatutGrilleRefBean;
import com.accor.asa.rate.RatesException;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.model.GrilleBean;
import com.accor.asa.rate.service.PoolRateFactory;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class MainBlackOutDatesAction extends MainAction {

    private long    idGrille;
    private String  key;
    private GrilleBean grille;
    private boolean screenReadOnly;

    public long getIdGrille() {
        return idGrille;
    }

    public void setIdGrille(long idGrille) {
        this.idGrille = idGrille;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public GrilleBean getGrille() {
        return grille;
    }

    public void setGrille(GrilleBean grille) {
        this.grille = grille;
    }

   public boolean isScreenReadOnly() {
        return screenReadOnly;
    }

    public void setScreenReadOnly(boolean screenReadOnly) {
        this.screenReadOnly = screenReadOnly;
    }

    /**
     * Implementation de la methode init grille
     *
     * @return
     * @throws RatesException
     */
    protected void initGrille() throws RatesException {
        try {
            Contexte contexte = getAsaContexte();
            GrilleBean myGrille = PoolRateFactory.getInstance().getRateFacade().getGrilleById(getIdGrille(),contexte);
            if(myGrille!=null) {
                myGrille.setPeriodeValidite(PeriodeValiditeRefBean.getCacheList(contexte).getPeriodeValiditeById(myGrille.getIdPeriodeValidite()));
                myGrille.setHotel((Hotel)getFromSession(HOTEL));
                myGrille.setStatutGrille(StatutGrilleRefBean.getCacheList(contexte).getStatutGrilleById(myGrille.getIdStatut()));
            } else
                new RatesTechnicalException("Grille introuvable :" + getIdGrille(), "BOD_DET_MSG_NOGRID");
            setGrille(myGrille);
        } catch (IncoherenceException e) {
            throw new RatesTechnicalException(e);
        } catch (TechnicalException e) {
            throw new RatesTechnicalException(e);
        }
    }
}
