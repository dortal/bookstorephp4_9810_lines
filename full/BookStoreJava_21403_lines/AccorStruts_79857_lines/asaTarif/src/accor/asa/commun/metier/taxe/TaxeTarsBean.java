package com.accor.asa.commun.metier.taxe;

import java.io.Serializable;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class TaxeTarsBean implements Serializable {
    private String code;
    private String nom;
    private String type;
    private Integer ttc;


    public TaxeTarsBean() {
    }

    public TaxeTarsBean(String code, String nom, String type, Integer ttc) {
        this.code = code;
        this.nom = nom;
        this.type = type;
        this.ttc = ttc;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTtc() {
        return ttc;
    }

    public void setTtc(Integer ttc) {
        this.ttc = ttc;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("code=").append(code).append(",  ");
        sb.append("nom=").append(nom).append(",  ");
        sb.append("type=").append(type).append(",  ");
        sb.append("ttc=").append(ttc);
        return sb.toString();
    }
}
