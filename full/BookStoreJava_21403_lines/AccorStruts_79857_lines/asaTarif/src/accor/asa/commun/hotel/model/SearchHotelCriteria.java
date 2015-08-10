package com.accor.asa.commun.hotel.model;

import com.accor.asa.commun.hotel.util.SearchHotelListsProvider;
import com.accor.asa.commun.metier.Contexte;

import java.io.Serializable;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class SearchHotelCriteria implements SearchCriteria, Serializable {

    private String code;
    private String nom;
    private String ville;
    private String codeMarque;
    private String codeChaine;
    private String codePays;
    private String codePlace;
    private String codeDirOper;
    private String codeGroupe;
    private String codeLocOp;
    private String url;
    private String idHotel;
    private String multiSelect;
    private String target;

    private SearchHotelListsProvider listsProvider;


    public SearchHotelCriteria(Contexte contexte) {
        this.listsProvider = new SearchHotelListsProvider(contexte);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodeMarque() {
        return codeMarque;
    }

    public void setCodeMarque(String codeMarque) {
        this.codeMarque = codeMarque;
    }

    public String getCodeChaine() {
        return codeChaine;
    }

    public void setCodeChaine(String codeChaine) {
        this.codeChaine = codeChaine;
    }

    public String getCodePays() {
        return codePays;
    }

    public void setCodePays(String codePays) {
        this.codePays = codePays;
    }

    public String getCodePlace() {
        return codePlace;
    }

    public void setCodePlace(String codePlace) {
        this.codePlace = codePlace;
    }

    public String getCodeDirOper() {
        return codeDirOper;
    }

    public void setCodeDirOper(String codeDirOper) {
        this.codeDirOper = codeDirOper;
    }

    public String getCodeGroupe() {
        return codeGroupe;
    }

    public void setCodeGroupe(String codeGroupe) {
        this.codeGroupe = codeGroupe;
    }

    public String getCodeLocOp() {
        return codeLocOp;
    }

    public void setCodeLocOp(String codeLocOp) {
        this.codeLocOp = codeLocOp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(String idHotel) {
        this.idHotel = idHotel;
    }

    public String getMultiSelect() {
        return multiSelect;
    }

    public void setMultiSelect(String multiSelect) {
        this.multiSelect = multiSelect;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public SearchHotelListsProvider getListsProvider() {
        return listsProvider;
    }

    public void setListsProvider(SearchHotelListsProvider listsProvider) {
        this.listsProvider = listsProvider;
    }
}
