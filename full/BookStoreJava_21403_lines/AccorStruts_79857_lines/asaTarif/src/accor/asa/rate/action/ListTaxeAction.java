package com.accor.asa.rate.action;

import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.rate.RatesException;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.RatesUserException;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.decorator.TaxeDecorator;
import com.accor.asa.rate.model.TaxeBean;
import com.accor.asa.rate.service.PoolRateFactory;
import com.accor.asa.rate.util.RateScrean;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletDispatcherResult;
import org.apache.struts2.interceptor.validation.SkipValidation;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
@Results({
    @Result(name = Action.SUCCESS, type = ServletDispatcherResult.class, value = "/taxe/liste.jsp"),
    @Result(name = MainAction.EXPORT, type = ServletDispatcherResult.class, value = "/taxe/export.jsp"),
    @Result(name = MainAction.NO_HOTEL, type = ServletDispatcherResult.class, value = "/hotel/noHotel.jsp"),
    @Result(name = Action.ERROR, type = ServletDispatcherResult.class, value = "/error.jsp")
})
public class ListTaxeAction extends MainTaxeAction {

    private List<TaxeDecorator> taxes;

    /**
     * Initaliser la liste des tarifs
     *
     * @return
     */
    protected String initListe(String codeHotel) {
        Contexte contexte = getAsaContexte();
        try {
            List<TaxeBean> taxes = PoolRateFactory.getInstance().getRateFacade().getTaxes(codeHotel, contexte);
            List<TaxeDecorator> ratesDecorators = new ArrayList<TaxeDecorator>();
            for (TaxeBean taxe : taxes) {
                TaxeDecorator dec = new TaxeDecorator(taxe, contexte);
                ratesDecorators.add(dec);
            }
            setTaxes(ratesDecorators);
            return Action.SUCCESS;
        } catch (RatesTechnicalException e) {
            Log.critical(contexte.getCodeUtilisateur(), "ListTaxeAction", "initListe", e.getMessage());
            addActionError(e.getMessage());
            return Action.ERROR;
        }
    }

    /**
     * Habilitation de l'ecran
     *
     * @throws RatesTechnicalException
     */
    public void setScreenReadOnly()
            throws RatesTechnicalException {
        String codeHabilEcran = RateScrean.RATES_ECRAN_OPTION_TAXESANDSERVICES;
        Contexte contexte = getAsaContexte();
        setScreenReadOnly(isReadOnly(codeHabilEcran, contexte));
        if (Log.isDebug) {
            StringBuffer sb = new StringBuffer(" >> Verifier l'habilitation ");
            sb.append(" ecran=").append(codeHabilEcran).
                    append(" profil=").append(contexte.getCodeProfil()).
                    append(" >> readOnly=").append(isScreenReadOnly());
            Log.debug("ListTaxeAction", "isReadOnly", sb.toString());
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
                setScreenReadOnly();
                if (this.hasActionErrors())
                    return ERROR;
                return initListe(hotel.getCode());
            }
        } catch (RatesException e) {
            Log.critical(getAsaContexte().getCodeUtilisateur(), "ListTaxeAction", "export", e.getMessage());
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
            PoolRateFactory.getInstance().getRateFacade().deleteTaxe(getIdTaxe(), getAsaContexte());
            return execute();
        } catch (RatesException ex) {
            addActionError(translateRateException(ex));
            if (ex instanceof RatesUserException)
                return execute();
            else
                return Action.ERROR;
        }
    }

    @SkipValidation
    public String export() {
        String retour;
        try {
            if (this.hasActionErrors())
                return ERROR;
            initListe(((Hotel) getFromSession(HOTEL)).getCode());
            HttpServletResponse response = ServletActionContext.getResponse();
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=export.xls");
            retour = EXPORT;
        } catch (Exception e) {
            Log.critical(getAsaContexte().getCodeUtilisateur(), "ListTaxeAction", "export", e.getMessage());
            addActionError(e.getMessage());
            return Action.ERROR;
        }
        return retour;
    }

    //==================================  GETTER AND SETTER ========================================

    public List<TaxeDecorator> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<TaxeDecorator> taxes) {
        this.taxes = taxes;
    }

}
