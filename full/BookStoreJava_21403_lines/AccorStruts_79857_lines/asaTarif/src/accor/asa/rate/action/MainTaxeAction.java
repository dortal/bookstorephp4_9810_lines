package com.accor.asa.rate.action;

import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.commun.metier.Contexte;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class MainTaxeAction extends MainAction {

    private Long idTaxe;
    private Hotel hotel;
    private Contexte contexte;
    private boolean screenReadOnly;

    public Long getIdTaxe() {
        return idTaxe;
    }

    public void setIdTaxe(Long idTaxe) {
        this.idTaxe = idTaxe;
    }

    public Hotel getHotel() {
        if (hotel == null)
            hotel = (Hotel)getFromSession(HOTEL);
        return hotel;
    }


    public Contexte getContexte() {
        if (contexte == null)
            contexte = getAsaContexte();
        return contexte;
    }

    public boolean isScreenReadOnly() {
        return screenReadOnly;
    }

    public void setScreenReadOnly(boolean screenReadOnly) {
        this.screenReadOnly = screenReadOnly;
    }
}
