package com.accor.asa.rate.model;

import java.util.ArrayList;
import java.util.List;

import com.accor.asa.commun.hotel.metier.Hotel;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class HotelValidation extends Hotel {

    private List<GrilleValidation> grilles;

    public HotelValidation(Hotel hotel) {
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

    public List<GrilleValidation> getGrilles() {
        return grilles;
    }

    public void setGrilles(List<GrilleValidation> grilles) {
        this.grilles = grilles;
    }

    public void addGrille(GrilleValidation grille) {
        if(grilles==null)  grilles = new ArrayList<GrilleValidation>();
        grilles.add(grille);
    }

}
