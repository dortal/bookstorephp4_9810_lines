package com.accor.asa.rate.action;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.model.RateContractInfo;
import com.accor.asa.rate.service.PoolRateFactory;
import com.accor.asa.vente.statut.metier.StatutOffreHotel;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletDispatcherResult;

import java.util.List;

@Results({
    @Result(name = Action.SUCCESS, type = ServletDispatcherResult.class,    value = "/contracts/displayContractsForGrille.jsp"),
    @Result(name = Action.INPUT,    type = ServletDispatcherResult.class,   value = "/contracts/displayContractsForGrille.jspp"),
    @Result(name = Action.ERROR,    type = ServletDispatcherResult.class,   value = "/contracts/displayContractsForGrille.jsp")
})
public class DisplayListContractsAction extends MainAction {

    private long idGrille;
    private String codeRateLevel;
    private String status;

    private List<StatutOffreHotel> listStatus;
    private List<RateContractInfo> contracts;

    @Override
    public String execute() throws Exception {
        if (status != null && status.equals(""))
            status = null;
        Contexte contexte = getAsaContexte();
        try {
            listStatus = StatutOffreHotel.getStatutAll();
            contracts = PoolRateFactory.getInstance().getRateFacade().getContractsForRateLevel(idGrille, codeRateLevel, status, contexte);
            return SUCCESS;
        } catch (TechnicalException e) {
            Log.critical(contexte.getCodeUtilisateur(), "BusinessRateAction", "init", e.getMessage());
            addActionError(e.getMessage());
            return Action.ERROR;
        }
    }

    public long getIdGrille() {
        return idGrille;
    }

    public void setIdGrille(long idGrille) {
        this.idGrille = idGrille;
    }

    public String getCodeRateLevel() {
        return codeRateLevel;
    }

    public void setCodeRateLevel(String codeRateLevel) {
        this.codeRateLevel = codeRateLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RateContractInfo> getContracts() {
        return contracts;
    }

    public List<StatutOffreHotel> getListStatus() {
        return listStatus;
    }


}
