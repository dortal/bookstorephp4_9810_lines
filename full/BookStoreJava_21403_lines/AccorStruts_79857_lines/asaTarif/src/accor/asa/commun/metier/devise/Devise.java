package com.accor.asa.commun.metier.devise;

import com.accor.asa.commun.metier.Element;

@SuppressWarnings("serial")
public class Devise extends Element {
    protected float coursUnEuro;
    protected boolean deviseHotel;


    /**
     * Constructeur
     */
    public Devise() {
    }

    /**
     * Constructeur
     * @param codeDevise
     */
    public Devise(String codeDevise) {
        this.code = codeDevise;
    }


    public Devise(String codeDevise, String nomDevise, float coursUnEuro) {
        this.code = codeDevise;
        this.libelle = nomDevise;
        this.coursUnEuro = coursUnEuro;
    }

    public String getCodeDevise() {
		return code;
	}

	public void setCodeDevise(String codeDevise) {
		this.code = codeDevise;
	}

    public String getNomDevise() {
        return libelle;
    }

    public void setNomDevise(String nomDevise) {
        this.libelle = nomDevise;
    }

    public float getCoursUnEuro() {
        return coursUnEuro;
    }

    public void setCoursUnEuro(float coursUnEuro) {
        this.coursUnEuro = coursUnEuro;
    }


    public boolean isDeviseHotel() {
        return deviseHotel;
    }

    public void setDeviseHotel(boolean deviseHotel) {
        this.deviseHotel = deviseHotel;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Devise devise = (Devise) o;

        if (!code.equals(devise.code)) return false;

        return true;
    }

    public int hashCode() {
        return code.hashCode();
    }
    /**
   * To string
   * @return  String
   */
  public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("[code=").append(code).append("]  ");
      sb.append("[libelle=").append(libelle).append("]  ");
      sb.append("[coursUnEuro=").append(coursUnEuro).append("]  ");
      sb.append("[deviseHotel=").append(deviseHotel).append("]  ");
      return sb.toString();
  }
}
