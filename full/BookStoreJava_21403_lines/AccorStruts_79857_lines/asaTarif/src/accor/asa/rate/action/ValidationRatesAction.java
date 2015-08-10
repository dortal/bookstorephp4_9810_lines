package com.accor.asa.rate.action;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.habilitation.metier.Role;
import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.commun.hotel.metier.Room;
import com.accor.asa.commun.hotel.model.SearchCriteria;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.Element;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.metier.FamilleTarifRefBean;
import com.accor.asa.commun.reference.metier.GroupeTarifRefBean;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.commun.reference.metier.StatutGrilleRefBean;
import com.accor.asa.rate.RatesException;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.RatesUserException;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.decorator.AdagioBusinessRateDecorator;
import com.accor.asa.rate.decorator.BusinessRateDecorator;
import com.accor.asa.rate.decorator.LeisureRateDecorator;
import com.accor.asa.rate.model.*;
import com.accor.asa.rate.service.PoolRateFactory;
import com.accor.asa.rate.service.process.RateFacade;
import com.accor.asa.rate.util.HotelContextManager;
import com.accor.asa.rate.util.RateScrean;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletDispatcherResult;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
@Results({
    @Result(name = Action.INPUT,        type = ServletDispatcherResult.class, value = "/validation/validation.jsp"),
    @Result(name = MainAction.EXPORT,   type = ServletDispatcherResult.class, value = "/validation/export.jsp"),
    @Result(name = Action.SUCCESS,      type = ServletDispatcherResult.class, value = "/hotel/selectedHotel.jsp"),
    @Result(name = Action.ERROR,        type = ServletDispatcherResult.class, value = "/error.jsp")
})
public class ValidationRatesAction extends MainSearchHotelAction {

    private List<HotelValidation>       hotels;
    private List<FamilleTarifRefBean>   familleTarifs;
    private List<String>                validations;
    private String[]                    codeHotels;
    private PeriodeValiditeRefBean      periodeValidite;
    private List<HotelValidationExport> hotelExports;
    private boolean                     screenReadOnly;

    private List<Element> getListValidateLevel(long idGrille, int level) {
        List<Element> validateLevel = new ArrayList<Element>(2);
        if(level==1) {
            validateLevel.add(new Element(String.valueOf(idGrille)+"_"+StatutGrilleRefBean.ID_STATUT_VALID_1ST_LEVEL,getText("VAL_VAL_LBL_VALIDERGRILLE")));
            validateLevel.add(new Element(String.valueOf(idGrille)+"_"+StatutGrilleRefBean.ID_STATUT_REFUS_1ST_LEVEL,getText("VAL_VAL_LBL_REFUSERGRILLE")));
        } else if (level==2) {
            validateLevel.add(new Element(String.valueOf(idGrille)+"_"+StatutGrilleRefBean.ID_STATUT_VALID_2ND_LEVEL,getText("VAL_VAL_LBL_VALIDERGRILLE")));
            validateLevel.add(new Element(String.valueOf(idGrille)+"_"+StatutGrilleRefBean.ID_STATUT_REFUS_2ND_LEVEL,getText("VAL_VAL_LBL_REFUSERGRILLE")));
        }
        return validateLevel;
    }
    /**
     * Chercher des hotels
     *
     * @return
     */
    public String search() {
        Contexte contexte = getAsaContexte();
        String login = contexte.getCodeUtilisateur();
        try {
            List<Hotel> hotels = searchHotels();
            HotelValidation hotelValidation;
            GrilleValidation grilleValidation;
            List<HotelValidation> hotelValidations = new ArrayList<HotelValidation>();
            if(hotels!=null && !hotels.isEmpty()) {
                setScreenReadOnly();
                SearchValidationCriteria model = (SearchValidationCriteria)getModel();
                familleTarifs = FamilleTarifRefBean.getCacheList(contexte).getFamillesTarifByGroupeTarif(model.getIdGroupeTarif());
                for (Hotel hotel:hotels) {
                    hotelValidation = new HotelValidation(hotel);
                    for (FamilleTarifRefBean familletarif:familleTarifs) {
                        GrilleBean grille = PoolRateFactory.getInstance().getRateFacade().
                                getGrille(hotel.getCode(),model.getIdPeriodeValidite(), Integer.parseInt(familletarif.getId()), contexte );
                        if (grille==null) {
                            grille = new GrilleBean();
                            grille.setIdPeriodeValidite(model.getIdPeriodeValidite());
                            grille.setIdFamilleTarif(Integer.parseInt(familletarif.getId()));
                            grille.setIdStatut(StatutGrilleRefBean.ID_STATUT_NO_SEIZED);
                        }
                        grille.setFamilleTarif(familletarif);
                        grille.setStatutGrille(StatutGrilleRefBean.getCacheList(contexte).getStatutGrilleById(grille.getIdStatut()));
                        grilleValidation = new GrilleValidation(grille);
                        if(!isScreenReadOnly()) {
                            List<Integer> statuts24 = new ArrayList<Integer>(2);
                            statuts24.add(StatutGrilleRefBean.ID_STATUT_SEIZED);
                            statuts24.add(StatutGrilleRefBean.ID_STATUT_REFUS_1ST_LEVEL);
                            List<Integer> statuts247 = new ArrayList<Integer>(3);
                            statuts247.add(StatutGrilleRefBean.ID_STATUT_SEIZED);
                            statuts247.add(StatutGrilleRefBean.ID_STATUT_REFUS_1ST_LEVEL);
                            statuts247.add(StatutGrilleRefBean.ID_STATUT_REFUS_1ST_LEVEL);
                            List<Integer> statuts23457 = new ArrayList<Integer>(5);
                            statuts23457.add(StatutGrilleRefBean.ID_STATUT_SEIZED);
                            statuts23457.add(StatutGrilleRefBean.ID_STATUT_VALID_1ST_LEVEL);
                            statuts23457.add(StatutGrilleRefBean.ID_STATUT_REFUS_1ST_LEVEL);
                            statuts23457.add(StatutGrilleRefBean.ID_STATUT_VALID_2ND_LEVEL);
                            statuts23457.add(StatutGrilleRefBean.ID_STATUT_REFUS_2ND_LEVEL);
                            Role role = getAsaContexte().getProfil().getRole();
                            if (role == null)
                                throw new RatesTechnicalException( "Aucun role associe a ce profil : " + getAsaContexte().getCodeProfil());
                            if("1".equals(hotelValidation.getFlagAsaTarifLight())) {
                                if(statuts24.contains(grille.getIdStatut()) && role.isRoleValidateurPremierNiveau()) {
                                    grilleValidation.setValidation1stLevel(true);
                                    grilleValidation.setListValidate1stLevel(getListValidateLevel(grilleValidation.getIdGrille(), 1));
                                } else if ((statuts247.contains(grille.getIdStatut()) && role.isRoleValidateurDeuxiemeNiveau()) ||
                                        (statuts23457.contains(grille.getIdStatut()) && (role.isRoleValidateurTroisiemeNiveau() || role.isRoleDbm())) ) {
                                    grilleValidation.setValidation2ndLevel(true);
                                    grilleValidation.setListValidate2ndLevel(getListValidateLevel(grilleValidation.getIdGrille(), 2));
                                }
                            } else {
                                if ( (statuts24.contains(grille.getIdStatut()) && role.isRoleValidateurPremierNiveau()) ||
                                     (statuts23457.contains(grille.getIdStatut()) && (role.isRoleValidateurTroisiemeNiveau() || role.isRoleDbm()))   ) {
                                    grilleValidation.setValidation2ndLevel(true);
                                    grilleValidation.setListValidate2ndLevel(getListValidateLevel(grilleValidation.getIdGrille(), 2));
                                }
                            }
                        }
                        hotelValidation.addGrille(grilleValidation);
                    }
                    //Ajouter les familles tarif et leurs statut grille
                    hotelValidations.add(hotelValidation);
                }
            }
            setHotels(hotelValidations);
            return prepareForDisplay();
        } catch (Exception e) {
            if (e instanceof RatesUserException) {
                addActionError(e.getMessage());
                return Action.INPUT;
            } else {
                Log.major(login, "ValidationRatesAction", "search", e.getMessage(), e);
                addActionError(e.getMessage());
                return Action.ERROR;
            }
        }
    }

    /**
     * Save validation
     * @return
     */
    public String save() {
        Contexte contexte = getAsaContexte();
        String login = contexte.getCodeUtilisateur();
        try {
            if(validations!=null && validations.size()>0) {
                long idGrille;
                int idNewStatut;
                for(String gs :  validations) {
                    if(StringUtils.isNotBlank(gs)) {
                        idGrille = Long.parseLong(gs.substring(0,gs.indexOf("_")));
                        idNewStatut = Integer.parseInt(gs.substring(gs.indexOf("_")+1,gs.length()));
                        PoolRateFactory.getInstance().getRateFacade().updateStatutGrille(idGrille,idNewStatut, contexte);
                    }
                }
            }
            return search();
        } catch (Exception e) {
            if (e instanceof RatesUserException) {
                addActionError(e.getMessage());
                return Action.INPUT;
            } else {
                Log.major(login, "ValidationRatesAction", "search", e.getMessage(), e);
                addActionError(e.getMessage());
                return Action.ERROR;
            }
        }
    }

    /**
     * Show all tariffs
     * @return
     */
    public String showAllRates() {
        Contexte contexte = getAsaContexte();
        String login = contexte.getCodeUtilisateur();
        try {
            List<Hotel> hotels = searchHotels();
            HotelValidationExport hotelExport;
            GrilleValidationExport grilleExport;
            List<HotelValidationExport> hotelExports = new ArrayList<HotelValidationExport>();
            if (codeHotels!=null && codeHotels.length>0) {
                List<String> hotelsSelected = Arrays.asList(codeHotels);
                if(hotels!=null && !hotels.isEmpty()) {
                    SearchValidationCriteria model = (SearchValidationCriteria)getModel();
                    familleTarifs  = FamilleTarifRefBean.getCacheList(contexte).getFamillesTarifByGroupeTarif(model.getIdGroupeTarif());
                    periodeValidite= PeriodeValiditeRefBean.getCacheList(contexte).getPeriodeValiditeById(model.getIdPeriodeValidite());
                    List<Room> rooms;
                    for (Hotel hotel:hotels) {
                        if(hotelsSelected.contains(hotel.getCode())) {
                            hotelExport = new HotelValidationExport(hotel);
                            hotelExport.setAdagioContext(HotelContextManager.getHotelContext(hotel).equals(RateScrean.HotelContext.ADAGIO_CONTEXT));
                            rooms = PoolCommunFactory.getInstance().getCommunUtilsFacade().getRooms(hotel.getCode(), contexte);
                            StringBuffer sb;
                            for (FamilleTarifRefBean familletarif:familleTarifs) {
                                GrilleBean grille = PoolRateFactory.getInstance().getRateFacade().
                                        getGrille(hotel.getCode(),model.getIdPeriodeValidite(), Integer.parseInt(familletarif.getId()), contexte );
                                if(grille!=null) {
                                    grille.setFamilleTarif(familletarif);
                                    grille.setStatutGrille(StatutGrilleRefBean.getCacheList(contexte).getStatutGrilleById(grille.getIdStatut()));
                                    grilleExport = new GrilleValidationExport(grille);
                                    switch(model.getIdGroupeTarif()) {
                                        case GroupeTarifRefBean.ID_GROUPETARIF_BUSINESS: {
                                            grilleExport.setBusinessGrille(true);
                                            BusinessRateBean bean = new BusinessRateBean();
                                            bean.setIdGrille(grilleExport.getIdGrille());
                                            List<Rate> ratesDecorators = new ArrayList<Rate>();
                                            if(hotelExport.isAdagioContext()) {
                                                List<RateBean> dataRows = PoolRateFactory.getInstance().getRateFacade().getRatesList(RateFacade.ADAGIO_BUSINESS_RATE_KEY, bean, contexte);
                                                for (Rate rate : dataRows) {
                                                    BusinessRateBean tmp = (BusinessRateBean) rate;
                                                    AdagioBusinessRateDecorator dec = new AdagioBusinessRateDecorator(tmp, contexte);
                                                    dec.setLibProduit(dec.getCodeProduit());
                                                    for (Room room : rooms) {
                                                        if(room.getCode().equals(dec.getCodeProduit())) {
                                                            sb = new StringBuffer(room.getNom());
                                                            sb.append(" (").append(dec.getCodeProduit()).append(")");
                                                            dec.setLibProduit(sb.toString());
                                                            break;
                                                        }
                                                    }
                                                    ratesDecorators.add(dec);
                                                }
                                            } else {
                                                List<RateBean> rates = PoolRateFactory.getInstance().getRateFacade().getRatesList(RateFacade.BUSINESS_RATE_KEY, bean, contexte);
                                                for (RateBean rate : rates) {
                                                    BusinessRateDecorator dec = new BusinessRateDecorator((BusinessRateBean) rate, contexte);
                                                    dec.setLibProduit(dec.getCodeProduit());
                                                    for (Room room : rooms) {
                                                        if(room.getCode().equals(dec.getCodeProduit())) {
                                                            sb = new StringBuffer(room.getNom());
                                                            sb.append(" (").append(dec.getCodeProduit()).append(")");
                                                            dec.setLibProduit(sb.toString());
                                                            break;
                                                        }
                                                    }
                                                    ratesDecorators.add(dec);
                                                }
                                            }
                                            grilleExport.setRates(ratesDecorators);
                                        }
                                        break;
                                        case GroupeTarifRefBean.ID_GROUPETARIF_TOURISM: {
                                            grilleExport.setBusinessGrille(false);
                                            LeisureRateBean bean = new LeisureRateBean();
                                            bean.setIdGrille(grilleExport.getIdGrille());
                                            List<RateBean> rates = PoolRateFactory.getInstance().getRateFacade().getRatesList(RateFacade.LEISURE_RATE_KEY, bean, contexte);
                                            List<Rate> ratesDecorators = new ArrayList<Rate>();
                                            for (RateBean rate : rates) {
                                                LeisureRateDecorator dec = new LeisureRateDecorator((LeisureRateBean) rate, contexte);
                                                dec.setLibProduit(dec.getCodeProduit());
                                                for (Room room : rooms) {
                                                    if(room.getCode().equals(dec.getCodeProduit())) {
                                                        sb = new StringBuffer(room.getNom());
                                                        sb.append(" (").append(dec.getCodeProduit()).append(")");
                                                        dec.setLibProduit(sb.toString());
                                                        break;
                                                    }
                                                }
                                                ratesDecorators.add(dec);
                                            }
                                            grilleExport.setRates(ratesDecorators);
                                        }
                                        break;
                                    }
                                    hotelExport.addGrille(grilleExport);
                                }
                            }
                            hotelExports.add(hotelExport);
                        }
                    }
                }
            }
            setHotelExports(hotelExports);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition","attachment;filename=export.xls");
            return EXPORT;
        } catch (Exception e) {
            if (e instanceof RatesUserException) {
                addActionError(e.getMessage());
                return Action.INPUT;
            } else {
                Log.major(login, "ValidationRatesAction", "search", e.getMessage(), e);
                addActionError(e.getMessage());
                return Action.ERROR;
            }
        }
    }
    /**
     * Add the data for display
     *
     * @param dataRate
     */
    protected void prepareAditionlDataForDisplayPage(Contexte contexte)
            throws RatesException {
        try {
            SearchValidationCriteria model = (SearchValidationCriteria)getModel();
            model.getListsProvider().initSearchValidation(model.getIdGroupeTarif());
        } catch(TechnicalException e) {
            throw new RatesTechnicalException(e);
        }
    }

    /**
     * here we can wrap the data into a useful decorator
     *
     * @param dataTaxe
     */
    protected SearchCriteria createSearchCriteria() {
        return new SearchValidationCriteria(getAsaContexte());
    }

    //-----------------------------------------------------------------
    //----------            G E T AND S E T           -----------------
    //-----------------------------------------------------------------

    public List<HotelValidation> getHotels() {
        return hotels;
    }

    public void setHotels(List<HotelValidation> hotels) {
        this.hotels = hotels;
    }

    public List<FamilleTarifRefBean> getFamilleTarifs() {
        return familleTarifs;
    }

    public void setFamilleTarifs(List<FamilleTarifRefBean> familleTarifs) {
        this.familleTarifs = familleTarifs;
    }

    public List<String> getValidations() {
        return validations;
    }

    public void setValidations(List<String> validations) {
        this.validations = validations;
    }

    public String[] getCodeHotels() {
        return codeHotels;
    }

    public void setCodeHotels(String[] codeHotels) {
        this.codeHotels = codeHotels;
    }

    public PeriodeValiditeRefBean getPeriodeValidite() {
        return periodeValidite;
    }

    public void setPeriodeValidite(PeriodeValiditeRefBean periodeValidite) {
        this.periodeValidite = periodeValidite;
    }

    public List<HotelValidationExport> getHotelExports() {
        return hotelExports;
    }

    public void setHotelExports(List<HotelValidationExport> hotelExports) {
        this.hotelExports = hotelExports;
    }

    public boolean isScreenReadOnly() {
        return screenReadOnly;
    }
    /**
     * Habilitation de l'ecran
     *
     * @throws RatesTechnicalException
     */
    public void setScreenReadOnly()
            throws RatesTechnicalException {
        String codeHabilEcran = RateScrean.RATES_ECRAN_OPTION_VALIDATERATES;
        Contexte contexte = getAsaContexte();
        screenReadOnly = isReadOnly(codeHabilEcran, contexte);
        if (Log.isDebug) {
            StringBuffer sb = new StringBuffer(" >> Verifier l'habilitation ");
            sb.append(" ecran=").append(codeHabilEcran).
                    append(" profil=").append(contexte.getCodeProfil()).
                    append(" >> readOnly=").append(isScreenReadOnly());
            Log.debug("ListTaxeAction", "isReadOnly", sb.toString());
        }
    }
}
