package com.accor.asa.rate.action;

import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.devise.Devise;
import com.accor.asa.commun.metier.ratelevel.RateLevel;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.rate.RatesException;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.RatesUserException;
import com.accor.asa.rate.form.BusinessRateFormBean;
import com.accor.asa.rate.form.RateFormBean;
import com.accor.asa.rate.model.BusinessRateBean;
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
    @Result(name = MainAction.RELOAD,   type = ServletActionRedirectResult.class,   value = "listBusinessRate.action", params = {"idPeriodeValidite", "${idPeriodeValidite}", "codeEcran", "${codeEcran}"})
})
public class DetailBusinessRateAction extends DetailHomeRateAction {

    @Override
    protected String getRateTypeKey() {
        return RateFacade.BUSINESS_RATE_KEY;
    }

    private BusinessRateFormBean getForm() {
        return (BusinessRateFormBean) getModel();
    }


    @Override
    protected RateFormBean createRateForm(RateBean dataRate) {
        BusinessRateBean rateBean = (BusinessRateBean) dataRate;
        return new BusinessRateFormBean(rateBean, getContexte(), getGrille());
    }

    @Override
    protected void prepareAditionlDataForDisplayPage(Contexte contexte) throws RatesException {
        BusinessRateFormBean form = getForm();
        form.getDefaultGrilleValuesProvider().init();
        form.getListsProvider().initBusiness();

        if (form.getKey().equals("")) {
            form.setIdDivSemaine(getDefaultIdDivisionSemaine());
            form.setCodePeriode(form.getListsProvider().getDefaultCodePeriodeGenerique());

            List<RateLevel> rateLevels=form.getListsProvider().getRateLevels();
            String codeRateLevel = rateLevels.get(0).getCode();
            DefaultGrilleValuesProvider  defaultValuesProvider= form.getDefaultGrilleValuesProvider();
            form.getBean().setCodeMealPlan( defaultValuesProvider.getDefaultCodeMealplan(codeRateLevel));
            form.setUniteCommission(defaultValuesProvider.getUniteCommission(codeRateLevel));
            form.setValueCommission(defaultValuesProvider.getValueCommission(codeRateLevel));
            form.setPdjInclus(defaultValuesProvider.isDefaultPdjInclu(codeRateLevel));
            form.setBlackOutDates(defaultValuesProvider.isBlackOutDates(codeRateLevel));
            form.setOpenNewContrat(defaultValuesProvider.isNewContrat(codeRateLevel));
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

    public boolean isCompaniesRates() {
        return getCodeEcran().equals(RateScrean.RATES_BUSINESS);
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
            PoolRateFactory.getInstance().getRateFacade().updateHistoGrille(getGrille().getIdGrille(),((BusinessRateBean)rateBean).getCodeRateLevel(), getAsaContexte());
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
        BusinessRateFormBean form = (BusinessRateFormBean) getModel();
        BusinessRateBean bean = (BusinessRateBean)getModel().getBean();
        DefaultGrilleValuesProvider helper = form.getDefaultGrilleValuesProvider();
        PeriodeValiditeRefBean activePeriodeValidite = getGrille().getPeriodeValidite();
        int rateLevelValidity = helper.getRateLevelValidity(bean.getCodeRateLevel());
        checkDatesValidities(bean.getDateDebut(), bean.getDateFin(), activePeriodeValidite.getDateDebut(), activePeriodeValidite.getDateFin(), rateLevelValidity);
        List<RateBean> rates = PoolRateFactory.getInstance().getRateFacade().getRatesList(RateFacade.BUSINESS_RATE_KEY, bean, getAsaContexte());
        if (rates != null && !rates.isEmpty()) {
			BusinessRateBean unBean;
            int nbPeriodes;
            for (String codeProduit : bean.getCodesProduit()) {
                nbPeriodes = 0;
                for (RateBean rate : rates) {
                    unBean = (BusinessRateBean) rate;
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
        if (isCompaniesRates())
            checkMinMaxNight(bean.getNbreNuitsMin(),bean.getNbreNuitsMax());
    }

    @Override
    protected void completeRateData() throws RatesException {
        BusinessRateFormBean model = (BusinessRateFormBean)getModel();
        model.setIdGrille(getGrille().getIdGrille());
        model.setIdDureeSejour(getDefaultIdDureeSejour());
    }
}
