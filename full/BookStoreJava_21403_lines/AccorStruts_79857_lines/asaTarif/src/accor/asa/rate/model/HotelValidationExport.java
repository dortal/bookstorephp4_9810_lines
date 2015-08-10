package com.accor.asa.rate.model;

import java.util.ArrayList;
import java.util.List;

import com.accor.asa.commun.hotel.metier.Hotel;


/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class HotelValidationExport extends Hotel {

    private List<GrilleValidationExport> grilles;
    private boolean adagioContext;

    public HotelValidationExport(Hotel hotel) {
        setCode(hotel.getCode());
        setName(hotel.getName());
        setAddress(hotel.getAddress());
        setCoordinates(hotel.getCoordinates());
        setCurrency(hotel.getCurrency());
        setLanguageCode(hotel.getLanguageCode());
        setAsaCategory(hotel.getAsaCategory());
        setMark(hotel.getMark());
        setChain(hotel.getChain());
        setDOP(hotel.getDOP());
        setDGR(hotel.getDGR());
        setSaleZone(hotel.getSaleZone());
        setFlagAsaTarifLight(getFlagAsaTarifLight());
    }


    public List<GrilleValidationExport> getGrilles() {
        return grilles;
    }

    public void setGrilles(List<GrilleValidationExport> grilles) {
        this.grilles = grilles;
    }

    public void addGrille(GrilleValidationExport grille) {
        if(grilles==null) grilles = new ArrayList<GrilleValidationExport>();
        grilles.add(grille);
    }

    public boolean isAdagioContext() {
        return adagioContext;
    }

    public void setAdagioContext(boolean adagioContext) {
        this.adagioContext = adagioContext;
    }
}
