package com.accor.asa.rate.model;

import java.util.Date;

import com.accor.asa.commun.metier.AsaDate;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class BlackOutDatesBean extends RateBean {

    private Date dateDebut;
    private Date dateFin;

    public String generateBeanKey() {
        StringBuffer sb = new StringBuffer(String.valueOf(getIdGrille()));
        sb.append("_").append(new AsaDate(getDateDebut()));
        return sb.toString();
    }

    public Date getDateDebut() {
        return dateDebut;
    }

        public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
}
