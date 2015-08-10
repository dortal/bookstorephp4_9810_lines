package com.accor.asa.rate.action;

import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.devise.Devise;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.rate.RatesException;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.RatesUserException;
import com.accor.asa.rate.form.RateFormBean;
import com.accor.asa.rate.form.SpecialRateFormBean;
import com.accor.asa.rate.model.RateBean;
import com.accor.asa.rate.model.SpecialRateBean;
import com.accor.asa.rate.service.PoolRateFactory;
import com.accor.asa.rate.service.process.RateFacade;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.conversion.annotations.Conversion;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;
import org.apache.struts2.dispatcher.ServletDispatcherResult;

import java.util.List;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
@Conversion
@Results({
    @Result(name = Action.INPUT,        type = ServletDispatcherResult.class,       value = "/rate/detail.jsp"),
    @Result(name = MainAction.DISPLAY,  type = ServletDispatcherResult.class,       value = "/rate/detail.jsp"),
    @Result(name = Action.ERROR, type = ServletDispatcherResult.class,              value = "/error.jsp"),
    @Result(name = MainAction.RELOAD,   type = ServletActionRedirectResult.class,   value = "/listSpecialRate.action", params = {"idPeriodeValidite", "${idPeriodeValidite}", "codeEcran", "${codeEcran}"})
})
public class DetailSpecialRateAction   extends DetailHomeRateAction {

    @Override
    protected String getRateTypeKey() {
        return RateFacade.SPECIAL_RATE_KEY;
    }

    private SpecialRateFormBean getForm() {
        return (SpecialRateFormBean) getModel();
    }


    @Override
    protected RateFormBean createRateForm(RateBean dataRate) {
        SpecialRateBean rateBean = (SpecialRateBean) dataRate;
        return new SpecialRateFormBean(rateBean, getContexte(), getGrille());
    }

    @Override
    protected void prepareAditionlDataForDisplayPage(Contexte contexte) throws RatesException {
        SpecialRateFormBean form = getForm();
        form.getDefaultGrilleValuesProvider().init();
        form.getListsProvider().initSpecial();
        // Default devise
        List<Devise> devises=form.getListsProvider().getDevises();
        if(devises!=null)
            for (Devise devise:devises)
                if(devise.isDeviseHotel()) {
                    form.setCodeDevise(devise.getCode());
                    break;
                }
    }

    public String save() {
        String exitCode = RELOAD;
        try {
            RateBean rateBean = getModel().getBean();
            completeRateData();
            validateRateData();
            if (getGrille().getIdGrille() == null) {
                getGrille().setIdGrille(
                        PoolRateFactory.getInstance().getRateFacade().addGrille(getGrille().getHotel().getCode(), Integer.parseInt(getGrille().getPeriodeValidite().getId()),
                                Integer.parseInt(getGrille().getFamilleTarif().getId()), getAsaContexte()));
                getModel().getBean().setIdGrille(getGrille().getIdGrille());
            }
            saveBeanData(getRateTypeKey(), getContexte());
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


    @Override
    protected void validateRateData() throws RatesTechnicalException, RatesUserException {
        checkFields();

    }

    private void checkFields() throws RatesUserException, RatesTechnicalException {
        SpecialRateBean bean = (SpecialRateBean) getModel().getBean();
        PeriodeValiditeRefBean activePeriodeValidite = getGrille().getPeriodeValidite();
        checkDatesValidities(bean.getDateDebut(), bean.getDateFin(), activePeriodeValidite.getDateDebut(), activePeriodeValidite.getDateFin(), 1);
        List<RateBean> rates = PoolRateFactory.getInstance().getRateFacade().getRatesList(RateFacade.SPECIAL_RATE_KEY, bean, getAsaContexte());
        if (rates != null && !rates.isEmpty()) {
			SpecialRateBean unBean;
            for (RateBean rate : rates) {
                unBean = (SpecialRateBean) rate;
                if(!bean.getKey().equals(unBean.getKey())) {
                    if (bean.getCodeOffreSpeciale().equals(unBean.getCodeOffreSpeciale())   ) {
                        checkCurrencyDifferent(bean.getCodeDevise(), unBean.getCodeDevise());
                        checkPeriodsDisjointed(bean.getDateDebut(), bean.getDateFin(), unBean.getDateDebut(), unBean.getDateFin());
                    }
                }
            }
        }
        if (    !bean.isTousMarches() &&
                StringUtils.isBlank(bean.getCodePays1()) &&
                StringUtils.isBlank(bean.getCodePays2()) &&
                StringUtils.isBlank(bean.getCodePays3()) &&
                StringUtils.isBlank(bean.getCodePays4()) &&
                StringUtils.isBlank(bean.getCodePays5()) &&
                StringUtils.isBlank(bean.getCodeContinent1()) &&
                StringUtils.isBlank(bean.getCodeContinent2()) &&
                StringUtils.isBlank(bean.getCodeContinent3())) {
            throw new RatesUserException(getText("SPE_MSG_ENTRER_MARCHE"));
        }
    }

    @Override
    protected void completeRateData() throws RatesException {
        SpecialRateFormBean model = (SpecialRateFormBean)getModel();
        model.setIdGrille(getGrille().getIdGrille());
    }
}
