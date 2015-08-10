package com.accor.asa.rate.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class GrilleValidationExport extends GrilleBean {

    private List<Rate> rates;
    private boolean businessGrille;

    public GrilleValidationExport(GrilleBean grille) {
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

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public void addRates(Rate rate) {
        if(rates==null) rates = new ArrayList<Rate>();
        rates.add(rate);
    }

    public boolean isBusinessGrille() {
        return businessGrille;
    }

    public void setBusinessGrille(boolean businessGrille) {
        this.businessGrille = businessGrille;
    }
}
