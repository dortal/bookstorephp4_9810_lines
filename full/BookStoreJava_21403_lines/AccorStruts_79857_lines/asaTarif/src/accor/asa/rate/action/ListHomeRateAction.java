package com.accor.asa.rate.action;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.FamilleTarifRefBean;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.commun.utiles.FilesPropertiesCache;
import com.accor.asa.rate.RatesException;
import com.accor.asa.rate.RatesParamException;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.RatesUserException;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.model.Rate;
import com.accor.asa.rate.model.RateBean;
import com.accor.asa.rate.service.PoolRateFactory;
import com.accor.asa.rate.util.RateScrean;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletDispatcherResult;
import org.apache.struts2.interceptor.validation.SkipValidation;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
@Results({
    @Result(name = Action.SUCCESS,      type = ServletDispatcherResult.class, value = "/rate/liste.jsp"),
    @Result(name = MainAction.EXPORT,   type = ServletDispatcherResult.class, value = "/rate/export.jsp"),
    @Result(name = MainAction.NO_HOTEL, type = ServletDispatcherResult.class, value = "/hotel/noHotel.jsp"),
    @Result(name = Action.ERROR,        type = ServletDispatcherResult.class, value = "/error.jsp")
})
abstract public class ListHomeRateAction extends MainRateAction {

    private List<Rate> rates;
    private List<PeriodeValiditeRefBean> periodesValidite;


    /**
     * Methode abstract pour l'initialisation de la liste
     *
     * @return
     */
    abstract protected String initListe();

    /**
     * Créer la liste des période de validité
     *
     * @param contexte
     * @return
     * @throws IncoherenceException
     * @throws TechnicalException
     */
    protected List<PeriodeValiditeRefBean> createPeriodeValiditeList(Contexte contexte)
            throws RatesTechnicalException {
        try {
            FamilleTarifRefBean familleTarif = getGrille().getFamilleTarif();
            int idGroupeTarif = familleTarif.getIdGroupeTarif();
            List<PeriodeValiditeRefBean> periodes = new ArrayList<PeriodeValiditeRefBean>();
            List<PeriodeValiditeRefBean> tempList = PeriodeValiditeRefBean.getCacheList(contexte).getPeriodesForGroupeTarif(idGroupeTarif);
            int maxValididte = FilesPropertiesCache.getInstance().getIntValue(FILE_PROPERTIES, "rate.maxValidite");
            Date activeDate = getPeriodeValiditeOuverte().getDateDebut();
            Calendar cal = Calendar.getInstance();
            cal.setTime(activeDate);
            int activeYear = cal.get(Calendar.YEAR);
            for (PeriodeValiditeRefBean periode : tempList) {
                Date d = periode.getDateDebut();
                if (!d.after(getPeriodeValiditeOuverte().getDateDebut())) {
                    cal.setTime(d);
                    int y = cal.get(Calendar.YEAR);
                    if (activeYear - y < maxValididte)
                        periodes.add(periode);
                }
            }
            Collections.sort(periodes, new Comparator<PeriodeValiditeRefBean>() {
                public int compare(PeriodeValiditeRefBean p1, PeriodeValiditeRefBean p2) {
                    Date d1 = p1.getDateDebut();
                    Date d2 = p2.getDateDebut();
                    return (int) (d2.getTime() - d1.getTime());
                }
            });
            return periodes;
        } catch (IncoherenceException e) {
            throw new RatesTechnicalException(e);
        } catch (TechnicalException e) {
            throw new RatesTechnicalException(e);
        }
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
            Hotel hotel = (Hotel) getFromSession(HOTEL);
            RateScrean.HotelContext hc = (RateScrean.HotelContext) getFromSession(HOTEL_CONTEXT);
            if (getCodeEcran() != null)
                setRateScrean(RateScrean.getRateScrean(getCodeEcran(), hc));
            if (getRateScrean() == null)
                throw new RatesParamException("Code écran inconnu :" + getCodeEcran(), "COM_PRM_MSG_INVALIDECRAN");
            FamilleTarifRefBean familleTarif = getFamilleTarifRefBean(getRateScrean(), contexte);
            PeriodeValiditeRefBean myPeriodeValiditeOuverte = getActivePeriodeValidite(hotel, familleTarif, contexte);
            if (myPeriodeValiditeOuverte == null)
                throw new RatesParamException("Aucune période de validité disponible.", "COM_PRM_MSG_NOPERIODEVALIDITE");
            setPeriodeValiditeOuverte(myPeriodeValiditeOuverte);
            PeriodeValiditeRefBean choosedPeriodeValidite = getChoosedPeriodeValidite(contexte);
            if (choosedPeriodeValidite == null) {
                choosedPeriodeValidite = myPeriodeValiditeOuverte;
                setIdPeriodeValidite(Integer.parseInt(myPeriodeValiditeOuverte.getCode()));
            }
            setGrille(createGrille(hotel, familleTarif, choosedPeriodeValidite, contexte));
        } catch (IncoherenceException e) {
            throw new RatesTechnicalException(e);
        } catch (TechnicalException e) {
            throw new RatesTechnicalException(e);
        }
    }

    /**
     * La methode execute par defaut de l'action
     *
     * @return
     */
    public String execute() {
        try {
            Hotel hotel = (Hotel) getFromSession(HOTEL);
            if (hotel == null) {
                setToRequest("url", ServletActionContext.getRequest().getRequestURI() + "?" + ServletActionContext.getRequest().getQueryString());
                return NO_HOTEL;
            } else {
                initGrille();
                setScreenReadOnly();
                setPeriodesValidite(createPeriodeValiditeList(getAsaContexte()));
                if (this.hasActionErrors())
                    return ERROR;
                return initListe();
            }
        } catch (RatesException e) {
            Log.critical(getAsaContexte().getCodeUtilisateur(), "ListHomeRateAction", "export", e.getMessage());
            addActionError(e.getMessage());
            return Action.ERROR;
        }
    }


    /**
     * delete the grille
     *
     * @return
     */
    @SkipValidation
    public String delete() {
        try {
            RateBean rateBean = createRateInstance();
            rateBean.setKey(getKey());
            PoolRateFactory.getInstance().getRateFacade().deleteRate(getRateTypeKey(), rateBean, getAsaContexte());
            return execute();
        } catch (RatesUserException e) {
            Log.critical(getAsaContexte().getCodeUtilisateur(), "ListAdagioBusinessRateAction", "delete", e.getMessage());
            addActionError(e.getMessage());
            return execute();
        } catch (RatesTechnicalException e) {
            Log.critical(getAsaContexte().getCodeUtilisateur(), "ListAdagioBusinessRateAction", "delete", e.getMessage());
            addActionError(e.getMessage());
            return Action.ERROR;
        }
    }

    @SkipValidation
    public String export() {
        String retour;
        try {
            initGrille();
            if (this.hasActionErrors())
                return ERROR;
            initListe();
            HttpServletResponse response = ServletActionContext.getResponse();
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition","attachment;filename=export.xls");
            retour = EXPORT;
        } catch (Exception e) {
            Log.critical(getAsaContexte().getCodeUtilisateur(), "ListHomeRateAction", "export", e.getMessage());
            addActionError(e.getMessage());
            return Action.ERROR;
        }
        return retour;
    }

    //==================================  GETTER AND SETTER ========================================

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public List<PeriodeValiditeRefBean> getPeriodesValidite() {
        return periodesValidite;
    }

    public void setPeriodesValidite(List<PeriodeValiditeRefBean> periodesValidite) {
        this.periodesValidite = periodesValidite;
    }

}
