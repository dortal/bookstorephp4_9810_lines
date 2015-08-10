package com.accor.asa.rate.action;

import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.rate.RatesException;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.RatesUserException;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.decorator.BlackOutDatesDecorator;
import com.accor.asa.rate.model.BlackOutDatesBean;
import com.accor.asa.rate.model.Rate;
import com.accor.asa.rate.model.RateBean;
import com.accor.asa.rate.service.PoolRateFactory;
import com.accor.asa.rate.service.process.RateFacade;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletDispatcherResult;
import org.apache.struts2.interceptor.validation.SkipValidation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
@Results({
    @Result(name = Action.SUCCESS,      type = ServletDispatcherResult.class, value = "/rate/blackOutDates/liste.jsp"),
    @Result(name = Action.ERROR,        type = ServletDispatcherResult.class, value = "/error.jsp")
})
public class ListBlackOutDatesAction  extends MainBlackOutDatesAction {

    private List<Rate> rates;

    /**
     * Initaliser la liste des BOD
     *
     * @return
     */
    protected String initListe() {
        Contexte contexte = getAsaContexte();
        try {
            if (getGrille().getIdGrille() != null) {
                BlackOutDatesBean bean = new BlackOutDatesBean();
                bean.setIdGrille(getGrille().getIdGrille());
                List<RateBean> rates = PoolRateFactory.getInstance().getRateFacade().getRatesList(RateFacade.BLACK_OUT_DATES_KEY, bean, contexte);
                List<Rate> ratesDecorators = new ArrayList<Rate>();
                for (RateBean rate : rates) {
                    BlackOutDatesDecorator dec = new BlackOutDatesDecorator((BlackOutDatesBean) rate);
                    ratesDecorators.add(dec);
                }
                setRates(ratesDecorators);
            }
            return Action.SUCCESS;
        } catch (RatesException e) {
            Log.critical(contexte.getCodeUtilisateur(), "ListBlackOutDatesAction", "initListe", e.getMessage());
            addActionError(translateRateException(e));
            return Action.ERROR;
        }
    }

    /**
     * La methode execute par defaut de l'action
     *
     * @return
     */
    public String execute() {
        try {
            initGrille();
            return initListe();
        } catch (RatesException e) {
            Log.critical(getAsaContexte().getCodeUtilisateur(), "ListHomeRateAction", "export", e.getMessage());
            addActionError(translateRateException(e));
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
            RateBean rateBean = new BlackOutDatesBean();
            rateBean.setKey(getKey());
            PoolRateFactory.getInstance().getRateFacade().deleteRate(RateFacade.BLACK_OUT_DATES_KEY, rateBean, getAsaContexte());
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

    //==================================  GETTER AND SETTER ========================================

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }
}
