package com.accor.asa.rate.model;

import java.util.List;

import com.accor.asa.commun.metier.Element;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class GrilleValidation extends GrilleBean {

    private boolean validation1stLevel;
    private boolean validation2ndLevel;
    private List<Element> listValidate1stLevel;
    private List<Element> listValidate2ndLevel;

    public GrilleValidation(GrilleBean grille) {
        setIdGrille(grille.getIdGrille());
        setCodeHotel(grille.getCodeHotel());
        setIdPeriodeValidite(grille.getIdPeriodeValidite());
        setIdFamilleTarif(grille.getIdFamilleTarif());
        setIdStatut(grille.getIdStatut());
        setDateCreation(grille.getDateCreation());
        setLoginCreation(grille.getLoginCreation());
        setDateAddCXX(grille.getDateAddCXX());
        setLoginAddCXX(grille.getLoginAddCXX());
        setDateLastModif(grille.getDateLastModif());
        setLoginLastModif(grille.getLoginLastModif());
        setHotel(grille.getHotel());
        setPeriodeValidite(grille.getPeriodeValidite());
        setFamilleTarif(grille.getFamilleTarif());
        setStatutGrille(grille.getStatutGrille());
    }

    public boolean getValidation1stLevel() {
        return validation1stLevel;
    }

    public void setValidation1stLevel(boolean validation1stLevel) {
        this.validation1stLevel = validation1stLevel;
    }

    public boolean getValidation2ndLevel() {
        return validation2ndLevel;
    }

    public void setValidation2ndLevel(boolean validation2ndLevel) {
        this.validation2ndLevel = validation2ndLevel;
    }

    public List<Element> getListValidate1stLevel() {
        return listValidate1stLevel;
    }

    public void setListValidate1stLevel(List<Element> listValidate1stLevel) {
        this.listValidate1stLevel = listValidate1stLevel;
    }

    public List<Element> getListValidate2ndLevel() {
        return listValidate2ndLevel;
    }

    public void setListValidate2ndLevel(List<Element> listValidate2ndLevel) {
        this.listValidate2ndLevel = listValidate2ndLevel;
    }
}
