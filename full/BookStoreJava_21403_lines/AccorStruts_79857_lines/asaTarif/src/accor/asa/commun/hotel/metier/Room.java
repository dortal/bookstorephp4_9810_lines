package com.accor.asa.commun.hotel.metier;

import java.io.Serializable;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
@SuppressWarnings("serial")
public class Room implements Serializable {

    protected String  code;
    protected String  nom;
    protected Integer nbPers;
    protected String codeType;

    public Room(String code, String nom, Integer nbPers) {
        this.code = code;
        this.nom = nom;
        this.nbPers = nbPers;
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

    public Integer getNbPers() {
        return nbPers;
    }

    public void setNbPers(Integer nbPers) {
        this.nbPers = nbPers;
    }

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

    
}
