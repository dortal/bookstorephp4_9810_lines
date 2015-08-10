package com.accor.asa.commun.metier.week;

import java.io.Serializable;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class WeekDivision implements Serializable {

    private boolean lunWe;
    private boolean marWe;
    private boolean merWe;
    private boolean jeuWe;
    private boolean venWe;
    private boolean samWe;
    private boolean dimWe;

    public WeekDivision() {
    }

    public WeekDivision(boolean lunWe, boolean marWe, boolean merWe, boolean jeuWe, boolean venWe, boolean samWe, boolean dimWe) {
        this.lunWe = lunWe;
        this.marWe = marWe;
        this.merWe = merWe;
        this.jeuWe = jeuWe;
        this.venWe = venWe;
        this.samWe = samWe;
        this.dimWe = dimWe;
    }


    public boolean isLunWe() {
        return lunWe;
    }

    public void setLunWe(boolean lunWe) {
        this.lunWe = lunWe;
    }

    public boolean isMarWe() {
        return marWe;
    }

    public void setMarWe(boolean marWe) {
        this.marWe = marWe;
    }

    public boolean isMerWe() {
        return merWe;
    }

    public void setMerWe(boolean merWe) {
        this.merWe = merWe;
    }

    public boolean isJeuWe() {
        return jeuWe;
    }

    public void setJeuWe(boolean jeuWe) {
        this.jeuWe = jeuWe;
    }

    public boolean isVenWe() {
        return venWe;
    }

    public void setVenWe(boolean venWe) {
        this.venWe = venWe;
    }

    public boolean isSamWe() {
        return samWe;
    }

    public void setSamWe(boolean samWe) {
        this.samWe = samWe;
    }

    public boolean isDimWe() {
        return dimWe;
    }

    public void setDimWe(boolean dimWe) {
        this.dimWe = dimWe;
    }
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("lunWe=").append(lunWe).
                append(",marWe=").append(marWe).
                append(",merWe=").append(merWe).
                append(",jeuWe=").append(jeuWe).
                append(",venWe=").append(venWe).
                append(",samWe=").append(samWe).
                append(",dimWe=").append(dimWe);
        return sb.toString();
    }
}
