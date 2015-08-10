package com.accor.asa.rate.action;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.habilitation.metier.HabilitationException;
import com.accor.asa.commun.habilitation.metier.Profil;
import com.accor.asa.commun.habilitation.metier.Role;
import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.FamilleTarifCacheList;
import com.accor.asa.commun.reference.metier.FamilleTarifRefBean;
import com.accor.asa.commun.reference.metier.ParamPeriodeValiditeRefBean;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.commun.reference.metier.StatutGrilleRefBean;
import com.accor.asa.rate.RatesException;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.model.BusinessRateBean;
import com.accor.asa.rate.model.ChildRateBean;
import com.accor.asa.rate.model.GrilleBean;
import com.accor.asa.rate.model.LeisureRateBean;
import com.accor.asa.rate.model.RateBean;
import com.accor.asa.rate.model.SpecialRateBean;
import com.accor.asa.rate.service.PoolRateFactory;
import com.accor.asa.rate.service.process.RateFacade;
import com.accor.asa.rate.util.RateScrean;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
abstract public class MainRateAction extends MainAction {


    private String codeEcran;
    private int idPeriodeValidite;

    private GrilleBean grille;
    private RateScrean rateScrean;
    private PeriodeValiditeRefBean periodeValiditeOuverte;
    private String key;
    private boolean screenReadOnly;


    public String getCodeEcran() {
        return codeEcran;
    }

    public void setCodeEcran(String codeEcran) {
        this.codeEcran = codeEcran;
    }

    public int getIdPeriodeValidite() {
        return idPeriodeValidite;
    }

    public void setIdPeriodeValidite(int idPeriodeValidite) {
        this.idPeriodeValidite = idPeriodeValidite;
    }

    public GrilleBean getGrille() {
        return grille;
    }

    public void setGrille(GrilleBean grille) {
        this.grille = grille;
    }

    public RateScrean getRateScrean() {
        return rateScrean;
    }

    public void setRateScrean(RateScrean rateScrean) {
        this.rateScrean = rateScrean;
    }

    public PeriodeValiditeRefBean getPeriodeValiditeOuverte() {
        return periodeValiditeOuverte;
    }

    public void setPeriodeValiditeOuverte(PeriodeValiditeRefBean periodeValiditeOuverte) {
        this.periodeValiditeOuverte = periodeValiditeOuverte;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }




    protected abstract void initGrille() throws RatesException;

    protected GrilleBean createGrille(Hotel hotel, FamilleTarifRefBean familleTarif, PeriodeValiditeRefBean periodeValidite, Contexte contexte) throws RatesTechnicalException {
        GrilleBean grille = new GrilleBean();
        grille.setHotel(hotel);
        grille.setPeriodeValidite(periodeValidite);
        grille.setFamilleTarif(familleTarif);
        StatutGrilleRefBean statutGrille;
        try {
            GrilleBean dbGrille = PoolRateFactory.getInstance().getRateFacade().getGrille(grille.getHotel().getCode(), Integer.parseInt(grille.getPeriodeValidite().getId()),
                    Integer.parseInt(grille.getFamilleTarif().getId()), contexte);
            if (dbGrille != null) {
                grille = dbGrille;
                grille.setHotel(hotel);
                grille.setPeriodeValidite(periodeValidite);
                grille.setFamilleTarif(familleTarif);
                statutGrille = StatutGrilleRefBean.getCacheList(contexte).getStatutGrilleById(grille.getIdStatut());
            } else
                statutGrille = StatutGrilleRefBean.getCacheList(contexte).getStatutGrilleById(StatutGrilleRefBean.ID_STATUT_NO_SEIZED);
            grille.setStatutGrille(statutGrille);
        } catch (TechnicalException ex) {
            throw new RatesTechnicalException(ex);
        } catch (IncoherenceException ex) {
            throw new RatesTechnicalException(ex);
        }
        return grille;
    }

    protected FamilleTarifRefBean getFamilleTarifRefBean(RateScrean screan, Contexte contexte) throws IncoherenceException, TechnicalException {
        FamilleTarifCacheList cache = FamilleTarifRefBean.getCacheList(contexte);
        return cache.getFamilleTarifById(screan.getIdFamilleTarif());
    }

    protected PeriodeValiditeRefBean getActivePeriodeValidite(Hotel hotel, FamilleTarifRefBean familleTarif, Contexte contexte) throws TechnicalException, IncoherenceException {
        PeriodeValiditeRefBean pv = null;
        String codeAsaCategory = hotel.getCodeAsaCategory();
        int idGroupeTarif = familleTarif.getIdGroupeTarif();
        ParamPeriodeValiditeRefBean ppv = ParamPeriodeValiditeRefBean.getCacheList(contexte).getParamPeriodeValidite(codeAsaCategory, idGroupeTarif);
        if (ppv != null) {
            pv = PeriodeValiditeRefBean.getCacheList(contexte).getPeriodeValiditeById(ppv.getIdPeriodeValidite());
        }
        return pv;
    }

    protected PeriodeValiditeRefBean getChoosedPeriodeValidite(Contexte contexte) throws TechnicalException, IncoherenceException {
        Integer id = getIdPeriodeValidite();
        if (id == null)
            return null;
        return PeriodeValiditeRefBean.getCacheList(contexte).getPeriodeValiditeById(id);
    }

    protected abstract String getRateTypeKey();

    protected RateBean createRateInstance() {
        RateBean rateBean = null;
        if (getRateTypeKey().equals(RateFacade.BUSINESS_RATE_KEY))
            rateBean = new BusinessRateBean();
        else if (getRateTypeKey().equals(RateFacade.ADAGIO_BUSINESS_RATE_KEY))
            rateBean = new BusinessRateBean();
        else if (getRateTypeKey().equals(RateFacade.LEISURE_RATE_KEY))
            rateBean = new LeisureRateBean();
        else if (getRateTypeKey().equals(RateFacade.SPECIAL_RATE_KEY))
            rateBean = new SpecialRateBean();
        else if (getRateTypeKey().equals(RateFacade.CHILD_RATE_KEY))
            rateBean = new ChildRateBean();
        return rateBean;
    }

    public String getPagesPath() {
        if (rateScrean == null)
            return "";
        return rateScrean.getPath();
    }


    public boolean isScreenReadOnly() {
        return screenReadOnly;
    }

    public void setScreenReadOnly(boolean screenReadOnly) {
        this.screenReadOnly = screenReadOnly;
    }

    /**
     * Habilitation de l'ecran
     * @throws RatesTechnicalException
     */
    public void setScreenReadOnly()
            throws RatesTechnicalException {
        try {
            Contexte contexte               = getAsaContexte();
            Hotel hotel                     = (Hotel) getFromSession(HOTEL);
            String codeHabilEcran           = getRateScrean().getCodeHabilitation();
            int idPeriodeValidite           = getIdPeriodeValidite();
            int idPeriodeValidditeOuverte   = (getPeriodeValiditeOuverte()!=null)?Integer.valueOf(getPeriodeValiditeOuverte().getId()):-1;
            int idFamilleTarif              = getRateScrean().getIdFamilleTarif();
            int idStatut                    = getGrille().getIdStatut();
            if(  contexte.getProfil() == null )
                throw new HabilitationException( "Aucun profil associe a cet utilisateur : " + contexte.getCodeUtilisateur() );
            Profil profil = contexte.getProfil();
            if(  profil.getRole() == null )
                throw new HabilitationException( "Aucun role associe a ce profil : " + profil.getCode() );
            Role role = profil.getRole();
            this.screenReadOnly = (!(role.isRoleValidateurTroisiemeNiveau() || role.isRoleDbm()) && (idPeriodeValidite!=idPeriodeValidditeOuverte)) ||
                    isReadOnly(codeHabilEcran,
                    hotel.getCode(),
                    idPeriodeValidite,
                    idFamilleTarif,
                    idStatut,
                    contexte);
            if(Log.isDebug) {
                StringBuffer sb = new StringBuffer(" >> Verifier l'habilitation ");
                sb.append(" ecran=").append(codeHabilEcran).
                        append(" Hotel=").append(hotel.getCode()).
                        append(" idPeriodeValidite=").append(idPeriodeValidite).
                        append(" idPeriodeValiditeOuverte=").append(idPeriodeValidditeOuverte).
                        append(" idFamilleTarif=").append(idFamilleTarif).
                        append(" idStatut=").append(idStatut).
                        append(" profil=").append(contexte.getCodeProfil()).
                        append(" >> readOnly=").append(screenReadOnly);
                Log.debug("MainRateAction","isReadOnly",sb.toString());
            }
        } catch(HabilitationException e) {
            throw new RatesTechnicalException(e); 
        }
    }
}
