package com.accor.asa.rate.action;

import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.reference.metier.ParamPeriodeValiditeRefBean;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.rate.RatesException;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.RatesUserException;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.form.TaxeFormBean;
import com.accor.asa.rate.model.TaxeBean;
import com.accor.asa.rate.service.PoolRateFactory;
import com.accor.asa.rate.util.FormatCompareUtil;
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

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
@Conversion
@Results({
    @Result(name = Action.INPUT, type = ServletDispatcherResult.class, value = "/taxe/detail.jsp"),
    @Result(name = MainAction.DISPLAY, type = ServletDispatcherResult.class, value = "/taxe/detail.jsp"),
    @Result(name = Action.ERROR, type = ServletDispatcherResult.class, value = "/error.jsp"),
    @Result(name = MainAction.RELOAD, type = ServletActionRedirectResult.class, value = "listTaxe.action")
})
public class DetailTaxeAction extends MainTaxeAction  implements ModelDriven<TaxeFormBean>, Preparable {

    private TaxeFormBean taxeForm;
    private TaxeBean     taxeBean;

    private String prepareForDisplay() {
        String exitCode = DISPLAY;
        try {
            TaxeFormBean form = getForm();
            form.getListsProvider().initTaxe();
        } catch (RatesException ex) {
            exitCode = Action.ERROR;
            addActionError(translateRateException(ex));
        } catch (Exception ex) {
            Log.major(getAsaContexte().getCodeUtilisateur(), "DetailTaxeAction", "prepareForDisplay", "Unexpected exception:" + ex.getClass() + ": " + ex.getMessage());
            addActionError("Unexpected error");
            exitCode = Action.ERROR;
        }
        return exitCode;
    }

    @SkipValidation
    public String showCreateNew() {
        String code = prepareForDisplay();
        taxeBean.setSupprime(false);
        prepareAditionlDataForDisplayPage();
        return code;
    }

    @SkipValidation
    public String showEdit() {
        return prepareForDisplay();
    }

    @SkipValidation
    public String showDuplicate() {
        String code = prepareForDisplay();
        taxeBean.setIdTaxe(null);
        taxeBean.setSupprime(false);
        prepareAditionlDataForDisplayPage();
        return code;
    }

    @SkipValidation
    public String showInvalidate() {
        String code = prepareForDisplay();
        taxeBean.setIdTaxe(null);
        taxeBean.setInclus(false);
        taxeBean.setMontant(null);
        taxeBean.setIdTypePrix(TaxeFormBean.TYPEPRIX_NONE);
        taxeBean.setIdUniteTaxe(TaxeFormBean.UNITETAXE_NONE);
        taxeBean.setSupprime(true);
        prepareAditionlDataForDisplayPage();
        return code;
    }

    public TaxeFormBean getModel() {
        return taxeForm;
    }

    protected TaxeBean loadTaxeBan(Contexte contexte) throws RatesException {
        taxeBean = new TaxeBean();
        taxeBean.setIdTaxe(getIdTaxe());
        return PoolRateFactory.getInstance().getRateFacade().getTaxe(taxeBean.getIdTaxe(), contexte);
    }

    public void prepare() throws Exception {
        if (getIdTaxe() == null)
            taxeBean = new TaxeBean();
        else
            taxeBean = loadTaxeBan(getContexte());
        if (taxeBean == null)
            addActionError("The requested rate has been deleted");
        taxeForm = createTaxeForm(taxeBean);
    }

    protected void prepareAditionlDataForDisplayPage() {
        try {
            Contexte contexte = getContexte();
            TaxeFormBean form = getForm();
            if (form.getIdTaxe() == null) {
                ParamPeriodeValiditeRefBean ppv  =  ParamPeriodeValiditeRefBean.getCacheList(contexte).
                        getParamPeriodeValidite(getHotel().getCodeAsaCategory(), 0);
                if (ppv != null) {
                    form.setDateDebut(PeriodeValiditeRefBean.getCacheList(contexte).
                            getPeriodeValiditeById(ppv.getIdPeriodeValidite()).
                            getDateDebut());
                } else {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE,1);
                    form.setDateDebut(cal.getTime());
                }
                form.setDateFin(FormatCompareUtil.getNoLimitAsaDate());
            }
        } catch (Exception ex) {
            Log.major(getAsaContexte().getCodeUtilisateur(), "DetailTaxeAction", "prepareForDisplay", "Unexpected exception:" + ex.getClass() + ": " + ex.getMessage());
        }
    }

    /**
     * here we can wrap the data into a useful decorator
     *
     * @param dataTaxe
     */
    protected TaxeFormBean createTaxeForm(TaxeBean dataTaxe) {
        return new TaxeFormBean(dataTaxe, getHotel(), getContexte());
    }

    protected void saveBeanData(Contexte contexte) throws RatesTechnicalException, RatesUserException {
        TaxeBean taxeBean = getModel().getBean();
        PoolRateFactory.getInstance().getRateFacade().
                addTaxe(taxeBean, contexte);
    }

    protected void completeTaxeData(TaxeBean taxeBean) throws RatesException {
        TaxeBean bean = (getModel()).getBean();
        bean.setCodeHotel(getHotel().getCode());
    }


    private TaxeFormBean getForm() {
        return getModel();
    }

    @VisitorFieldValidator(message = "Default message", appendPrefix = false, fieldName = "model")
    public String save() {
        String exitCode = RELOAD;
        try {
            TaxeBean taxeBean = getModel().getBean();
            taxeBean.setIdStatut(TaxeFormBean.STATUT_NONTRANSFERE);
            validateTaxeData(taxeBean);
            completeTaxeData(taxeBean);
            saveBeanData(getContexte());
            setIdTaxe(taxeBean.getIdTaxe());
        } catch (RatesException ex) {
            if (ex instanceof RatesUserException)
                exitCode = INPUT;
            else
                exitCode = ERROR;
            addActionError(translateRateException(ex));
        }
        return exitCode;
    }

    // -----------------------------------------------------------------------------------------------
    // -------------------      VALIDATION GENERIQUE            --------------------------------------
    // -----------------------------------------------------------------------------------------------
    protected void validateTaxeData(TaxeBean taxeBean) throws RatesTechnicalException, RatesUserException {
        checkFields(taxeBean);
    }

    private void checkFields(TaxeBean bean)
            throws RatesUserException, RatesTechnicalException {
        try {
            // Dates
            Calendar now = Calendar.getInstance();
            if (FormatCompareUtil.isDateAfter(now.getTime(), bean.getDateDebut())) {
                String[] params = {FormatCompareUtil.formatDateToAsaString(bean.getDateDebut()), FormatCompareUtil.formatDateToAsaString(now.getTime())};
                throw new RatesUserException(getText("TAX_MSG_DATEDEBUTINFNOW", params));
            }
            if (FormatCompareUtil.isDateAfter(bean.getDateDebut(), bean.getDateFin()) ) {
                String[] params = {FormatCompareUtil.formatDateToAsaString(bean.getDateDebut()), FormatCompareUtil.formatDateToAsaString(bean.getDateFin())};
                throw new RatesUserException(getText("TAX_MSG_DATEDEBUTSUPFIN", params));
            }
            if (FormatCompareUtil.isDateAfterStrict(bean.getDateFin(), FormatCompareUtil.getNoLimitAsaDate())) {
                String[] params = {FormatCompareUtil.formatDateToAsaString(bean.getDateDebut()), FormatCompareUtil.formatDateToAsaString(now.getTime())};
                throw new RatesUserException(getText("TAX_MSG_DATEFINSUPNOLIMIT", params));
            }
            // Montant
            if(!bean.getSupprime()) {
                if (TaxeFormBean.TYPEPRIX_NONE.equals(bean.getIdTypePrix()))
                    throw new RatesUserException(getText("TAX_MSG_TYPEPRIXREQUIRED"));
                if (TaxeFormBean.UNITETAXE_NONE.equals(bean.getIdUniteTaxe()))
                    throw new RatesUserException(getText("TAX_MSG_UNITETAXEREQUIRED"));
                if (bean.getMontant()==null || bean.getMontant()<=0)
                    throw new RatesUserException(getText("TAX_MSG_MONTANTREQUIRED"));
                if(TaxeFormBean.TYPEPRIX_MONTANT.equals(bean.getIdTypePrix()) && StringUtils.isBlank(bean.getCodeDevise()))
                    throw new RatesUserException(getText("TAX_MSG_DEVISEREQUIRED"));
            }
            // Controles unicite et Maj
            boolean isDelExist = false;
            List<TaxeBean> taxes = PoolRateFactory.getInstance().getRateFacade().getTaxes(getHotel().getCode(), getContexte());
            for (TaxeBean taxe : taxes) {
                if (!taxe.getIdTaxe().equals(bean.getIdTaxe()) && bean.getCode().equals(taxe.getCode())) {
                    // Unicite
                    if (isTaxeExist(bean,taxe)  )
                        throw new RatesUserException(getText("TAX_MSG_EXISTTAXE"));
                    // Unicite RL
                    if (isTaxeRateLevelExist(bean,taxe)  )
                        throw new RatesUserException(getText("TAX_MSG_EXISTTAXERL"));
                    // Vérifier qu'on ne peut pas ajouter une taxe sans rien modifier de ses valeurs
                    if(isTaxeNotModified(bean,taxe) )
                        throw new RatesUserException(getText("TAX_MSG_ADDIDENTICTAXE"));
                    // Vérifier qu'on ne peut pas supprimer une taxe inexistante
                    if(isTaxeDelExist(bean,taxe) )
                        isDelExist = true;
                }
            }
            if(bean.getSupprime() && !isDelExist)
                throw new RatesUserException(getText("TAX_MSG_DELETENOTEXIST"));
        } catch(ParseException e) {
            throw new RatesTechnicalException(e);
        }
    }
    /*
    return  myBean.getSupprime() == myTaxe.getSupprime() &&
            !(myBean.getCodeDevise() != null ? !myBean.getCodeDevise().equals(myTaxe.getCodeDevise()) : myTaxe.getCodeDevise() != null) &&
            !(myBean.getDateDebut() != null ? !myBean.getDateDebut().equals(myTaxe.getDateDebut()) : myTaxe.getDateDebut() != null) &&
            !(myBean.getIdStatut() != null ? !myBean.getIdStatut().equals(myTaxe.getIdStatut()) : myTaxe.getIdStatut() != null) &&
            !(myBean.getIdTaxe() != null ? !myBean.getIdTaxe().equals(myTaxe.getIdTaxe()) : myTaxe.getIdTaxe() != null) &&
            !(myBean.getIdTypePrix() != null ? !myBean.getIdTypePrix().equals(myTaxe.getIdTypePrix()) : myTaxe.getIdTypePrix() != null) &&
            !(myBean.getIdUniteTaxe() != null ? !myBean.getIdUniteTaxe().equals(myTaxe.getIdUniteTaxe()) : myTaxe.getIdUniteTaxe() != null) &&
            !(myBean.getMontant() != null ? !myBean.getMontant().equals(myTaxe.getMontant()) : myTaxe.getMontant() != null) &&
            Arrays.equals(myBean.getrateLevels, myTaxe.rateLevels);
    */
    public boolean isTaxeExist(TaxeBean myBean, TaxeBean myTaxe) {
        return  !(myBean.getDateDebut() != null ? !myBean.getDateDebut().equals(myTaxe.getDateDebut()) : myTaxe.getDateDebut() != null) &&
                !(myBean.getMontant() != null ? !myBean.getMontant().equals(myTaxe.getMontant()) : myTaxe.getMontant() != null) &&
                !(myBean.getIdTypePrix() != null ? !myBean.getIdTypePrix().equals(myTaxe.getIdTypePrix()) : myTaxe.getIdTypePrix() != null) &&
                !(myBean.getIdUniteTaxe() != null ? !myBean.getIdUniteTaxe().equals(myTaxe.getIdUniteTaxe()) : myTaxe.getIdUniteTaxe() != null);
    }
    public boolean isTaxeRateLevelExist(TaxeBean myBean, TaxeBean myTaxe) {
        return !(myBean.getIdStatut() != null ? !myBean.getIdStatut().equals(myTaxe.getIdStatut()) : myTaxe.getIdStatut() != null) &&
                (FormatCompareUtil.isDateBetween(myBean.getDateDebut(), myTaxe.getDateDebut(), myTaxe.getDateFin()) ||
                 FormatCompareUtil.isDateBetween(myBean.getDateFin(), myTaxe.getDateDebut(), myTaxe.getDateFin())   ||
                 FormatCompareUtil.isDateBetween(myTaxe.getDateDebut(), myBean.getDateDebut(), myBean.getDateFin()) ||
                 FormatCompareUtil.isDateBetween(myTaxe.getDateFin(), myBean.getDateDebut(), myBean.getDateFin())   );
    }
    public boolean isTaxeNotModified(TaxeBean myBean, TaxeBean myTaxe) {
        return  TaxeFormBean.STATUT_TRANSFERE.equals(myTaxe.getIdStatut()) &&
                !(myBean.getMontant() != null ? !myBean.getMontant().equals(myTaxe.getMontant()) : myTaxe.getMontant() != null) &&
                !(myBean.getIdTypePrix() != null ? !myBean.getIdTypePrix().equals(myTaxe.getIdTypePrix()) : myTaxe.getIdTypePrix() != null) &&
                !(myBean.getIdUniteTaxe() != null ? !myBean.getIdUniteTaxe().equals(myTaxe.getIdUniteTaxe()) : myTaxe.getIdUniteTaxe() != null) &&
                (myBean.getInclus() == myTaxe.getInclus()) &&
                (FormatCompareUtil.isDateBetween(myBean.getDateDebut(), myTaxe.getDateDebut(), myTaxe.getDateFin()) ||
                 FormatCompareUtil.isDateBetween(myBean.getDateFin(), myTaxe.getDateDebut(), myTaxe.getDateFin())   ||
                 FormatCompareUtil.isDateBetween(myTaxe.getDateDebut(), myBean.getDateDebut(), myBean.getDateFin()) ||
                 FormatCompareUtil.isDateBetween(myTaxe.getDateFin(), myBean.getDateDebut(), myBean.getDateFin())) &&
                 Arrays.equals(myBean.getRateLevels(), myTaxe.getRateLevels());
    }
    public boolean isTaxeDelExist(TaxeBean myBean, TaxeBean myTaxe) {
        return  myBean.getSupprime() &&
                TaxeFormBean.STATUT_TRANSFERE.equals(myTaxe.getIdStatut()) &&
                (FormatCompareUtil.isDateBetween(myBean.getDateDebut(), myTaxe.getDateDebut(), myTaxe.getDateFin()) ||
                 FormatCompareUtil.isDateBetween(myBean.getDateFin(), myTaxe.getDateDebut(), myTaxe.getDateFin()) ||
                 FormatCompareUtil.isDateBetween(myTaxe.getDateDebut(), myBean.getDateDebut(), myBean.getDateFin()) ||
                 FormatCompareUtil.isDateBetween(myTaxe.getDateFin(), myBean.getDateDebut(), myBean.getDateFin()));
    }

    @Override
    public String execute() throws Exception {
        return WELCOME;
    }

}
