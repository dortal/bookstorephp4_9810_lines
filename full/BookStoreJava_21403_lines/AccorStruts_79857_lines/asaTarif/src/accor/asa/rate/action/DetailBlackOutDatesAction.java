package com.accor.asa.rate.action;

import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.rate.RatesException;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.RatesUserException;
import com.accor.asa.rate.form.BlackOutDatesFormBean;
import com.accor.asa.rate.form.RateFormBean;
import com.accor.asa.rate.model.BlackOutDatesBean;
import com.accor.asa.rate.model.RateBean;
import com.accor.asa.rate.service.PoolRateFactory;
import com.accor.asa.rate.service.process.RateFacade;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.conversion.annotations.Conversion;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;
import org.apache.struts2.dispatcher.ServletDispatcherResult;
import org.apache.struts2.interceptor.validation.SkipValidation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
@Conversion
@Results({
    @Result(name = Action.INPUT, type = ServletDispatcherResult.class,          value = "/rate/blackOutDates/detail.jsp"),
    @Result(name = MainAction.DISPLAY, type = ServletDispatcherResult.class,    value = "/rate/blackOutDates/detail.jsp"),
    @Result(name = Action.ERROR, type = ServletDispatcherResult.class,          value = "/error.jsp"),
    @Result(name = MainAction.RELOAD, type = ServletActionRedirectResult.class, value = "listBlackOutDates.action", params = {"idGrille", "${idGrille}"})
})
public class DetailBlackOutDatesAction  extends MainBlackOutDatesAction implements ModelDriven<RateFormBean>, Preparable {

    private RateFormBean rateForm;
    private RateBean rateBean;
    private Contexte contexte;

    @SkipValidation
    public String showCreateNew() {
        return DISPLAY;
    }
    @SkipValidation
    public String showEdit() {
        return DISPLAY;
    }
    @SkipValidation
    public String showDuplicate() {
        rateBean.setKey("");
        return DISPLAY;
    }

    protected void saveBeanData(Contexte contexte) throws RatesTechnicalException, RatesUserException {
        RateBean rateBean = getModel().getBean();
        if (StringUtils.isNotEmpty(rateBean.getKey()))
            PoolRateFactory.getInstance().getRateFacade().deleteRate(RateFacade.BLACK_OUT_DATES_KEY, rateBean, contexte);
        PoolRateFactory.getInstance().getRateFacade().insertRate(RateFacade.BLACK_OUT_DATES_KEY, rateBean, contexte);
    }

    @VisitorFieldValidator(message = "Default message", appendPrefix = false, fieldName="model")
    public String save() {
        String exitCode = RELOAD;
        try {
            RateBean rateBean = getModel().getBean();
            validateRateData();
            saveBeanData(getContexte());
            setKey(rateBean.getKey());
        } catch (RatesException ex) {
            if (ex instanceof RatesUserException)
                exitCode = INPUT;
            else
                exitCode = ERROR;
            addActionError(translateRateException(ex));
        }
        return exitCode;
    }


    public RateFormBean getModel() {
        return rateForm;
    }

    protected RateBean loadRateBean(Contexte contexte) throws RatesException {
        rateBean = new BlackOutDatesBean();
        rateBean.setKey(getKey());
        return PoolRateFactory.getInstance().getRateFacade().getRateById(RateFacade.BLACK_OUT_DATES_KEY, rateBean, contexte);
    }

    public void prepare() throws Exception {
        contexte = getAsaContexte();
        initGrille();
        if (StringUtils.isEmpty(getKey()))
            rateBean = new BlackOutDatesBean();
        else
            rateBean = loadRateBean(contexte);
        if (rateBean == null)
            addActionError("The requested rate has been deleted");
        else
            rateBean.setKey(getKey());
        rateForm = new BlackOutDatesFormBean((BlackOutDatesBean)rateBean);
    }

    public Contexte getContexte() {
        return contexte;
    }

    // -----------------------------------------------------------------------------------------------
    // -------------------              VALIDATION              --------------------------------------
    // -----------------------------------------------------------------------------------------------
    protected void validateRateData() throws RatesTechnicalException, RatesUserException {
        checkFields();
    }
    private void checkFields() throws RatesUserException, RatesTechnicalException {
        BlackOutDatesBean bean = (BlackOutDatesBean)getModel().getBean();
        PeriodeValiditeRefBean activePeriodeValidite = getGrille().getPeriodeValidite();
        bean.setIdGrille(getGrille().getIdGrille());
        checkDatesValidities(bean.getDateDebut(), bean.getDateFin(), activePeriodeValidite.getDateDebut(), activePeriodeValidite.getDateFin());
        List<RateBean> rates = PoolRateFactory.getInstance().getRateFacade().getRatesList(RateFacade.BLACK_OUT_DATES_KEY, bean, getAsaContexte());
        if (rates != null && !rates.isEmpty()) {
			BlackOutDatesBean unBean;
            for (RateBean rate : rates) {
                unBean = (BlackOutDatesBean) rate;
                if(!bean.getKey().equals(unBean.getKey())) {
                    checkPeriodsDisjointed(bean.getDateDebut(), bean.getDateFin(), unBean.getDateDebut(), unBean.getDateFin());
                }
            }
		}
    }
    protected void checkDatesValidities(Date dateDebut, Date dateFin, Date dateDebutValidite, Date dateFinValidite) throws RatesUserException {
        if (dateDebut != null && dateFin != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            if (dateDebut.getTime() > dateFin.getTime()) {
                throw new RatesUserException(getText("COM_PRM_MSG_DATEDEBUTSUPFIN"));
            }
            if (dateDebut.getTime() < dateDebutValidite.getTime() || dateDebut.getTime() > dateFin.getTime()) {
                String[] params = {formatter.format(dateDebut), formatter.format(dateDebutValidite), formatter.format(dateFin)};
                throw new RatesUserException(getText("COM_PRM_MSG_DATEOUTVALIDITE", params));
            }
            if (dateFin.getTime() < dateDebutValidite.getTime() || dateFin.getTime() > dateFin.getTime()) {
                String[] params = {formatter.format(dateFin), formatter.format(dateDebutValidite), formatter.format(dateFin)};
                throw new RatesUserException(getText("COM_PRM_MSG_DATEOUTVALIDITE", params));
            }
        }
    }
    protected void checkPeriodsDisjointed(Date dateDebut, Date dateFin, Date thisDateDebut, Date thisDateFin) throws RatesUserException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if ((dateDebut.getTime() >= thisDateDebut.getTime() && dateDebut.getTime() <= thisDateFin.getTime())
                || (dateFin.getTime() >= thisDateDebut.getTime() && dateFin.getTime() <= thisDateFin.getTime())
                || (thisDateDebut.getTime() >= dateDebut.getTime() && thisDateDebut.getTime() <= dateFin.getTime())
                || (thisDateFin.getTime() >= dateDebut.getTime() && thisDateFin.getTime() <= dateFin.getTime())) {
            String[] params = {formatter.format(dateDebut) + "-" + formatter.format(dateFin), formatter.format(thisDateDebut) + "-" + formatter.format(thisDateFin)};
            throw new RatesUserException(getText("COM_PRM_MSG_PERIODESDISJOINTES", params));
        }
    }


}
