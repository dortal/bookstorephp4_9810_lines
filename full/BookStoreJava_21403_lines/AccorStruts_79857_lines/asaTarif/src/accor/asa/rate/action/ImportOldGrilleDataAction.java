package com.accor.asa.rate.action;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.PeriodeValiditeCacheList;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.model.GrilleBean;
import com.accor.asa.rate.service.PoolRateFactory;
import com.accor.asa.rate.util.RateScrean;
import com.accor.asa.rate.util.RateScrean.HotelContext;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletDispatcherResult;

import java.util.Date;
import java.util.List;

@Results({
    @Result(name = Action.SUCCESS, type = ServletDispatcherResult.class, value = "/rate/importTarifGrille.jsp")
})
public class ImportOldGrilleDataAction extends MainAction {

    private int idPeriodeValidite;
    private String codeEcran;

    private String rateKey;
    private String actionString;
    private boolean withErrors;

    @Override
    public String execute() throws Exception {
        Contexte contexte = getAsaContexte();
        String retour = SUCCESS;
        try {
            RateScrean rateScrean = loadRateScrean();
            actionString = rateScrean.getActionString();
            int idFamilleTarif = rateScrean.getIdFamilleTarif();
            Hotel hotel = (Hotel) getFromSession(HOTEL);
            GrilleBean grille = getGrilleFromDB(hotel.getCode(), idPeriodeValidite, idFamilleTarif, contexte);
            if (grille != null)
                throw new IncoherenceException("The grille is already initialized");
            PeriodeValiditeRefBean activePeriodeValidite = PeriodeValiditeRefBean.getCacheList(contexte).getPeriodeValiditeById(idPeriodeValidite);
            PeriodeValiditeRefBean oldPeriodeValidite = getPreviousPeriodeValidite(idPeriodeValidite, contexte);
            if (oldPeriodeValidite == null)
                PoolRateFactory.getInstance().getRateFacade().addGrille(hotel.getCode(), idPeriodeValidite, idFamilleTarif, contexte);
            else {
                grille = new GrilleBean();
                grille.setCodeHotel(hotel.getCode());
                grille.setIdPeriodeValidite(Integer.parseInt(oldPeriodeValidite.getCode()));
                grille.setIdFamilleTarif(idFamilleTarif);
                PoolRateFactory.getInstance().getRateFacade().importGrilleTarif(rateKey, grille, activePeriodeValidite, contexte);
            }
        } catch (TechnicalException ex) {
            Log.critical(getAsaContexte().getCodeUtilisateur(), "ImportOldGrilleDataAction", "execute", ex.getMessage());
            addActionError(ex.getMessage());
        } catch (IncoherenceException ex) {
            Log.critical(getAsaContexte().getCodeUtilisateur(), "ImportOldGrilleDataAction", "execute", ex.getMessage());
            addActionError(ex.getMessage());
        } catch (Exception ex) {
            Log.critical(getAsaContexte().getCodeUtilisateur(), "ImportOldGrilleDataAction", "execute", ex.getMessage());
            addActionError(ex.getMessage());
        }
        withErrors = hasErrors();
        return retour;
    }

    public PeriodeValiditeRefBean getPreviousPeriodeValidite(int idPeriodeValidite, Contexte contexte) throws TechnicalException, IncoherenceException {
        PeriodeValiditeRefBean periodeValidite = null;
        PeriodeValiditeCacheList pvCache = PeriodeValiditeRefBean.getCacheList(contexte);
        PeriodeValiditeRefBean activePeriodeValidite = pvCache.getPeriodeValiditeById(idPeriodeValidite);
        int idGroupTarif = activePeriodeValidite.getIdGroupeTarif();
        Date from = activePeriodeValidite.getDateDebut();
        List<PeriodeValiditeRefBean> periodes = pvCache.getPeriodesForGroupeTarif(idGroupTarif);
        for (PeriodeValiditeRefBean pv : periodes) {
            Date db = pv.getDateDebut();
            if (db.before(from) && (periodeValidite == null || db.after(periodeValidite.getDateDebut())))
                periodeValidite = pv;
        }
        return periodeValidite;
    }

    private RateScrean loadRateScrean() throws IncoherenceException {
        HotelContext hc = (HotelContext) getFromSession(HOTEL_CONTEXT);
        if (codeEcran == null)
            throw new IncoherenceException("No codeEcran");
        return RateScrean.getRateScrean(codeEcran, hc);

    }

    private GrilleBean getGrilleFromDB(String codeHotel, int idPeriodeValidite, int idFamilleTarif, Contexte contexte) throws RatesTechnicalException {
        return PoolRateFactory.getInstance().getRateFacade().getGrille(codeHotel, idPeriodeValidite, idFamilleTarif, contexte);
    }

    public int getIdPeriodeValidite() {
        return idPeriodeValidite;
    }

    public void setIdPeriodeValidite(int idPeriodeValidite) {
        this.idPeriodeValidite = idPeriodeValidite;
    }

    public String getCodeEcran() {
        return codeEcran;
    }

    public void setCodeEcran(String codeEcran) {
        this.codeEcran = codeEcran;
    }

    public String getRateKey() {
        return rateKey;
    }

    public void setRateKey(String rateKey) {
        this.rateKey = rateKey;
    }

    public String getActionString() {
        return actionString;
    }

    public boolean isWithErrors() {
        return withErrors;
    }


}
