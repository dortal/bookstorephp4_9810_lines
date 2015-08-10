package com.accor.asa.rate.action;

import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.devise.Devise;
import com.accor.asa.commun.metier.ratelevel.RateLevel;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.rate.RatesException;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.RatesUserException;
import com.accor.asa.rate.form.LeisureRateFormBean;
import com.accor.asa.rate.form.RateFormBean;
import com.accor.asa.rate.model.LeisureRateBean;
import com.accor.asa.rate.model.RateBean;
import com.accor.asa.rate.service.PoolRateFactory;
import com.accor.asa.rate.service.process.RateFacade;
import com.accor.asa.rate.util.DefaultGrilleValuesProvider;
import com.accor.asa.rate.util.RateScrean;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.conversion.annotations.Conversion;
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
    @Result(name = Action.ERROR,        type = ServletDispatcherResult.class,       value = "/error.jsp"),
    @Result(name = MainAction.RELOAD,   type = ServletActionRedirectResult.class,   value = "listLeisureRate.action", params = {"idPeriodeValidite", "${idPeriodeValidite}", "codeEcran", "${codeEcran}"})
})
public class DetailLeisureRateAction  extends DetailHomeRateAction {

    @Override
    protected String getRateTypeKey() {
        return RateFacade.LEISURE_RATE_KEY;
    }

    private LeisureRateFormBean getForm() {
        return (LeisureRateFormBean) getModel();
    }


    @Override
    protected RateFormBean createRateForm(RateBean dataRate) {
        LeisureRateBean rateBean = (LeisureRateBean) dataRate;
        return new LeisureRateFormBean(rateBean, getContexte(), getGrille());
    }

    @Override
    protected void prepareAditionlDataForDisplayPage(Contexte contexte) throws RatesException {

        LeisureRateFormBean form = getForm();
        form.getDefaultGrilleValuesProvider().init();
        form.getListsProvider().initLeisure();

        if (form.getKey().equals("")) {
            form.setIdDivSemaine(getDefaultIdDivisionSemaine());
            form.setCodePeriode(form.getListsProvider().getDefaultCodePeriodeGenerique());
            List<RateLevel> rateLevels=form.getListsProvider().getRateLevels();
            String codeRateLevel = rateLevels.get(0).getCode();
            DefaultGrilleValuesProvider  defaultValuesProvider= form.getDefaultGrilleValuesProvider();
            form.getBean().setCodeMealPlan( defaultValuesProvider.getDefaultCodeMealplan(codeRateLevel));
            form.setUniteCommission(defaultValuesProvider.getUniteCommission(codeRateLevel));
            form.setValueCommission(defaultValuesProvider.getValueCommission(codeRateLevel));
            form.setLunWe(defaultValuesProvider.getDefaultLunWe(codeRateLevel));
            form.setMarWe(defaultValuesProvider.getDefaultMarWe(codeRateLevel));
            form.setMerWe(defaultValuesProvider.getDefaultMerWe(codeRateLevel));
            form.setJeuWe(defaultValuesProvider.getDefaultJeuWe(codeRateLevel));
            form.setVenWe(defaultValuesProvider.getDefaultVenWe(codeRateLevel));
            form.setSamWe(defaultValuesProvider.getDefaultSamWe(codeRateLevel));
            form.setDimWe(defaultValuesProvider.getDefaultDimWe(codeRateLevel));
            // Default devise
            List<Devise> devises=form.getListsProvider().getDevises();
            if(devises!=null)
                for (Devise devise:devises)
                    if(devise.isDeviseHotel()) {
                        form.setCodeDevise(devise.getCode());
                        break;
                    }
        }
    }

    public boolean isLeisuresRates() {
        return getCodeEcran().equals(RateScrean.RATES_LEISURE);
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
            PoolRateFactory.getInstance().getRateFacade().updateHistoGrille(getGrille().getIdGrille(),((LeisureRateBean)rateBean).getCodeRateLevel(), getAsaContexte());
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
        LeisureRateFormBean form = (LeisureRateFormBean) getModel();
        LeisureRateBean bean = (LeisureRateBean) getModel().getBean();
        PeriodeValiditeRefBean activePeriodeValidite = getGrille().getPeriodeValidite();
        checkDatesValidities(bean.getDateDebut(), bean.getDateFin(), activePeriodeValidite.getDateDebut(), activePeriodeValidite.getDateFin(), 1);
        List<RateBean> rates = PoolRateFactory.getInstance().getRateFacade().getRatesList(RateFacade.LEISURE_RATE_KEY, bean, getAsaContexte());
        if (rates != null && !rates.isEmpty()) {
			LeisureRateBean unBean;
            int nbPeriodes;
            for (String codeProduit : bean.getCodesProduit()) {
                nbPeriodes = 0;
                for (RateBean rate : rates) {
                    unBean = (LeisureRateBean) rate;
                    if(!bean.getKey().equals(unBean.getKey())) {
                        if (    codeProduit.equals(unBean.getCodeProduit()) &&
                                bean.getCodeRateLevel().equals(unBean.getCodeRateLevel())   ) {
                            checkCurrencyDifferent(bean.getCodeDevise(), unBean.getCodeDevise());
                            if (    bean.getCodeMealPlan().equals(unBean.getCodeMealPlan()) &&
                                    bean.getCodePeriode().equals(unBean.getCodePeriode()) &&
                                    bean.getIdDivSemaine().equals(unBean.getIdDivSemaine()) &&
                                    bean.getIdDureeSejour().equals(unBean.getIdDureeSejour()) )
                                checkPeriodsDisjointed(bean.getDateDebut(), bean.getDateFin(), unBean.getDateDebut(), unBean.getDateFin());
                            if(bean.getCodePeriode().equals(unBean.getCodePeriode()))
                                nbPeriodes++;
                        }
                    }
				}
                checkMaxPeriods(bean.getCodePeriode(),nbPeriodes);
            }
		}
        checkBreakfast(bean.getPrixPdj(), form.isPdjInclus(), bean.getCodePetitDej());
    }

    @Override
    protected void completeRateData() throws RatesException {
        LeisureRateFormBean model = (LeisureRateFormBean)getModel();
        model.setIdGrille(getGrille().getIdGrille());
        model.setIdDureeSejour(getDefaultIdDureeSejour());
    }
}
