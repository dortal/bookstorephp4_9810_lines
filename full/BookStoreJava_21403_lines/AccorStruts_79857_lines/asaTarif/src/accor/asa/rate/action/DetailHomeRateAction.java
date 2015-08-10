package com.accor.asa.rate.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.accor.asa.administration.commun.log.Log;
import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.DureeSejourRefBean;
import com.accor.asa.commun.reference.metier.FamilleTarifRefBean;
import com.accor.asa.commun.reference.metier.ParamPeriodeGeneriqueExcluHotelRefBean;
import com.accor.asa.commun.reference.metier.ParamPeriodeGeneriqueRefBean;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.rate.RatesException;
import com.accor.asa.rate.RatesParamException;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.RatesUserException;
import com.accor.asa.rate.form.RateFormBean;
import com.accor.asa.rate.model.RateBean;
import com.accor.asa.rate.service.PoolRateFactory;
import com.accor.asa.rate.service.process.RateFacade;
import com.accor.asa.rate.util.RateScrean;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;


public abstract class DetailHomeRateAction extends MainRateAction implements ModelDriven<RateFormBean>, Preparable {

    private RateFormBean rateForm;
    private RateBean rateBean;
    private Contexte contexte;

    private String prepareForDisplay() {
        String exitCode = DISPLAY;
        try {
            prepareAditionlDataForDisplayPage(contexte);
        } catch (RatesException ex) {
            exitCode = Action.ERROR;
            addActionError(translateRateException(ex));
        } catch(Exception ex) {
        	Log.major(getAsaContexte().getCodeUtilisateur(), "DetailHomeRateAction", "prepareForDisplay", "Unexpected exception:"+ex.getClass()+": "+ex.getMessage());
        	addActionError("Unexpected error");
        	exitCode=Action.ERROR;
        }
        return exitCode;
    }

    @SkipValidation
    public String showCreateNew() {
        return prepareForDisplay();
    }

    @SkipValidation
    public String showEdit() {
        return prepareForDisplay();
    }

    @SkipValidation
    public String showDuplicate() {
        String code = prepareForDisplay();
        rateBean.setKey("");
        return code;
    }

    
    public RateFormBean getModel() {
        return rateForm;
    }


    protected RateBean loadRateBan(Contexte contexte) throws RatesException {
        rateBean = createRateInstance();
        rateBean.setKey(getKey());
        if (rateBean == null)
            throw new RatesTechnicalException("No Rate instance prepared for rateKey=" + getRateTypeKey());
        return PoolRateFactory.getInstance().getRateFacade().getRateById(getRateTypeKey(), rateBean, contexte);
    }

    public void prepare() throws Exception {
        contexte = getAsaContexte();
        initGrille();
        if (StringUtils.isEmpty(getKey()))
            rateBean = createRateInstance();
        else
            rateBean = loadRateBan(contexte);
        if (rateBean == null)
            addActionError("The requested rate has been deleted");
        else
            rateBean.setKey(getKey());
        rateForm = createRateForm(rateBean);
    }

    protected abstract void prepareAditionlDataForDisplayPage(Contexte contexte) throws RatesException;

    /**
     * here we can wrap the data into a useful decorator
     *
     * @param dataRate
     */
    protected abstract RateFormBean createRateForm(RateBean dataRate);


    protected void initGrille() throws RatesException {
        Contexte contexte = getAsaContexte();
        Hotel hotel = (Hotel) getFromSession(HOTEL);
        try {
            RateScrean.HotelContext hc = (RateScrean.HotelContext) getFromSession(HOTEL_CONTEXT);
            if (getCodeEcran() != null)
                setRateScrean(RateScrean.getRateScrean(getCodeEcran(), hc));
            if (getRateScrean() == null)
                throw new RatesParamException("Code écran inconnu :" + getCodeEcran(), "COM_PRM_MSG_INVALIDECRAN");
            FamilleTarifRefBean familleTarif = getFamilleTarifRefBean(getRateScrean(), contexte);
            setPeriodeValiditeOuverte(getActivePeriodeValidite(hotel, familleTarif, contexte));
            PeriodeValiditeRefBean periodeValidite = getChoosedPeriodeValidite(contexte);
            setGrille(createGrille(hotel, familleTarif, periodeValidite, contexte));

        } catch (TechnicalException ex) {
            throw new RatesTechnicalException(ex);
        } catch (IncoherenceException ex) {
            throw new RatesTechnicalException(ex);
        }

    }


    public Contexte getContexte() {
        return contexte;
    }


    protected int getDefaultIdDivisionSemaine() {
        return 1;
    }


    protected abstract void validateRateData() throws RatesTechnicalException, RatesUserException;

    protected void saveBeanData(String rateKey, Contexte contexte) throws RatesTechnicalException, RatesUserException {
        RateBean rateBean = getModel().getBean();
        RateFacade facade = PoolRateFactory.getInstance().getRateFacade();
        if (StringUtils.isNotEmpty(rateBean.getKey()))
            facade.deleteRate(rateKey, rateBean, contexte);
        facade.insertRate(rateKey, rateBean, contexte);
    }

    // -----------------------------------------------------------------------------------------------
    // -------------------      VALIDATION GENERIQUE            --------------------------------------
    // -----------------------------------------------------------------------------------------------
    protected void checkDatesValidities(Date dateDebut, Date dateFin, Date dateDebutValidite, Date dateFinValidite, int rateLevelValidity) throws RatesUserException {
        if (dateDebut != null && dateFin != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date myDateFinValidite = dateFinValidite;
            if (rateLevelValidity > 1) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(getGrille().getPeriodeValidite().getDateFin());
                cal.add(Calendar.YEAR, 1);
                myDateFinValidite = cal.getTime();
            }
            if (dateDebut.getTime() > dateFin.getTime()) {
                throw new RatesUserException(getText("COM_PRM_MSG_DATEDEBUTSUPFIN"));
            }
            if (dateDebut.getTime() < dateDebutValidite.getTime() || dateDebut.getTime() > myDateFinValidite.getTime()) {
                String[] params = {formatter.format(dateDebut), formatter.format(dateDebutValidite), formatter.format(myDateFinValidite)};
                throw new RatesUserException(getText("COM_PRM_MSG_DATEOUTVALIDITE", params));
            }
            if (dateFin.getTime() < dateDebutValidite.getTime() || dateFin.getTime() > myDateFinValidite.getTime()) {
                String[] params = {formatter.format(dateFin), formatter.format(dateDebutValidite), formatter.format(myDateFinValidite)};
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

    protected void checkCurrencyDifferent(String codeDevise, String thisCodeDevise) throws RatesUserException {
        if (!codeDevise.equals(thisCodeDevise)) {
            String[] params = {codeDevise, thisCodeDevise};
            throw new RatesUserException(getText("COM_PRM_MSG_DEVISEDIFFERENTE", params));
        }
    }

    protected void checkBreakfast(Double prixPdj, boolean isPdjInclus, String codePetitDej) throws RatesUserException {
        if (((prixPdj != null && prixPdj.floatValue() != 0) || isPdjInclus) && codePetitDej.equals("X")) {
            throw new RatesUserException(getText("COM_PRM_MSG_SELECTTYPEPETITDEJ"));
        }
        if ((!codePetitDej.equals("X") && !isPdjInclus && (prixPdj == null || prixPdj.floatValue() == 0))) {
            throw new RatesUserException(getText("COM_PRM_MSG_SELECTPRIXPETITDEJ"));
        }
    }

    protected void checkMaxPeriods(String codePeriode, int nbPeriodesSaisies) throws RatesUserException, RatesTechnicalException {
        try {
            if(nbPeriodesSaisies>0) {
                String codeHotel       = getGrille().getCodeHotel();
                String codeAsaCategory = getGrille().getHotel().getCodeAsaCategory();
                int maxPeriodes  = ParamPeriodeGeneriqueRefBean.getCacheList(getContexte()).getNbMaxPeriodes(codePeriode,codeAsaCategory);
                int maxPeriodesHotel  = ParamPeriodeGeneriqueExcluHotelRefBean.getCacheList(getContexte()).getNbMaxPeriodes(codePeriode,codeAsaCategory,codeHotel);
                if(maxPeriodesHotel!=0) maxPeriodes = maxPeriodesHotel;
                if(nbPeriodesSaisies>=maxPeriodes) {
                    String[] params = {String.valueOf(maxPeriodes)};
                    throw new RatesUserException(getText("COM_PRM_MSG_MAXPERIODS",params));
                }
            }
        } catch(IncoherenceException e) {
            throw new RatesTechnicalException(e);
        } catch(TechnicalException e) {
            throw new RatesTechnicalException(e);
        }
    }

    protected void checkMinMaxNight(Integer minValue, Integer maxValue)
            throws RatesUserException {
        if( minValue!=null &&
            maxValue!=null &&
            minValue>=maxValue) {
            String[] params = {String.valueOf(minValue),String.valueOf(maxValue)};
            throw new RatesUserException(getText("COM_PRM_MSG_MINMAXNIGHT",params));
        }
    }

    protected int getDefaultIdDureeSejour() {
        return DureeSejourRefBean.DEFAULT_ID;
    }

    protected abstract void completeRateData() throws RatesException;

    @VisitorFieldValidator(message = "Default message", appendPrefix = false, fieldName="model")
    public abstract String save();

    @Override
	public String execute() throws Exception {
		return WELCOME;
	}
}
