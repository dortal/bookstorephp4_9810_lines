package com.accor.asa.rate.model;

import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.hotel.model.SearchHotelCriteria;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class SearchValidationCriteria extends SearchHotelCriteria {

    protected Integer idGroupeTarif = 0;
    protected Integer idPeriodeValidite;

    public SearchValidationCriteria(Contexte contexte) {
        super(contexte);
    }


    public Integer getIdGroupeTarif() {
        return idGroupeTarif;
    }

    public void setIdGroupeTarif(Integer idGroupeTarif) {
        this.idGroupeTarif = idGroupeTarif;
    }


    public Integer getIdPeriodeValidite() {
        return idPeriodeValidite;
    }

    public void setIdPeriodeValidite(Integer idPeriodeValidite) {
        this.idPeriodeValidite = idPeriodeValidite;
    }
}
