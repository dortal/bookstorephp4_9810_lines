package com.accor.asa.rate.action;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.user.metier.exception.UtilisateurInexistantException;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.model.GrilleBean;
import com.accor.asa.rate.model.HistoGrilleBean;
import com.accor.asa.rate.service.PoolRateFactory;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletDispatcherResult;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
@Results({
    @Result(name = Action.SUCCESS, type = ServletDispatcherResult.class, value = "/rate/histoGrille.jsp")
})
public class HistoGrilleAction  extends MainAction {

    private Long idGrille;
    private HistoGrilleBean histo;

    @Override
    public String execute() throws Exception {
        Contexte contexte = getAsaContexte();
        String retour = SUCCESS;
        try {
            if (getIdGrille()!=null) {
                HistoGrilleBean histo = new HistoGrilleBean();
                GrilleBean grille =  PoolRateFactory.getInstance().getRateFacade().getGrilleById(getIdGrille(),contexte);
                if (grille!=null) {
                    histo.setGrille(grille);
                    if(StringUtils.isNotBlank(grille.getLoginCreation())) {
                        try {
                            histo.setUserCreation(PoolCommunFactory.getInstance().getUserFacade().
                                    getUser( grille.getLoginCreation(), contexte ));
                        } catch(UtilisateurInexistantException e) {
                            // rien juste l'user introuvable
                            if (Log.isDebug)
                                Log.warn(contexte.getCodeUtilisateur(),"HistoGrilleAction","execute","User introuvable: "+grille.getLoginCreation());
                        }
                    }

                    if(StringUtils.isNotBlank(grille.getLoginAddCXX())) {
                        try {
                            histo.setUserAddCXX(PoolCommunFactory.getInstance().getUserFacade().
                                    getUser( grille.getLoginAddCXX(), contexte ));
                        } catch(UtilisateurInexistantException e) {
                            // rien juste l'user introuvable
                            if (Log.isDebug)
                                Log.warn(contexte.getCodeUtilisateur(),"HistoGrilleAction","execute","User introuvable: "+grille.getLoginAddCXX());
                        }
                    }
                    if(StringUtils.isNotBlank(grille.getLoginLastModif())) {
                        try {
                            histo.setUserLastModif(PoolCommunFactory.getInstance().getUserFacade().
                                    getUser( grille.getLoginLastModif(), contexte ));
                        } catch(UtilisateurInexistantException e) {
                            // rien juste l'user introuvable
                            if (Log.isDebug)
                                Log.warn(contexte.getCodeUtilisateur(),"HistoGrilleAction","execute","User introuvable: "+grille.getLoginLastModif());
                        }
                    }
                }
                setHisto(histo);
            } else {
                addActionError(getText("COM_HIST_LBL_NOIDGRILLE"));
            }
        } catch (TechnicalException ex) {
            Log.critical(getAsaContexte().getCodeUtilisateur(), "HistoGrilleAction", "execute", ex.getMessage());
            addActionError(ex.getMessage());
        } catch (IncoherenceException ex) {
            Log.critical(getAsaContexte().getCodeUtilisateur(), "HistoGrilleAction", "execute", ex.getMessage());
            addActionError(ex.getMessage());
        } catch (Exception ex) {
            Log.critical(getAsaContexte().getCodeUtilisateur(), "HistoGrilleAction", "execute", ex.getMessage());
            addActionError(ex.getMessage());
        }
        return retour;
    }

    //====================  GETTER AND SETTER ===================================

    public Long getIdGrille() {
        return idGrille;
    }

    public void setIdGrille(Long idGrille) {
        this.idGrille = idGrille;
    }

    public HistoGrilleBean getHisto() {
        return histo;
    }

    public void setHisto(HistoGrilleBean histo) {
        this.histo = histo;
    }
}
