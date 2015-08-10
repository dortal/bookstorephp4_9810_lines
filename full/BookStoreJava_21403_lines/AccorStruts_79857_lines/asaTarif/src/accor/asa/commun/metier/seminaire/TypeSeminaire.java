package com.accor.asa.commun.metier.seminaire;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 18 avr. 2007
 * Time: 12:52:22
 */
public class TypeSeminaire implements Serializable {

    protected int idTypeSeminaire;
    protected String nomSeminaire;
    protected String dateDebut;
    protected String dateFin;

    /**
     * Constructeur
     */
    public TypeSeminaire() {
    }

    /**
     * Constructeur
     * @param idTypeSeminaire
     * @param nomSeminaire
     * @param dateDebut
     * @param dateFin
     */
    public TypeSeminaire(int idTypeSeminaire, String nomSeminaire, String dateDebut, String dateFin) {
        this.idTypeSeminaire = idTypeSeminaire;
        this.nomSeminaire = nomSeminaire;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }


    public int getIdTypeSeminaire() {
        return idTypeSeminaire;
    }

    public void setIdTypeSeminaire(int idTypeSeminaire) {
        this.idTypeSeminaire = idTypeSeminaire;
    }

    public String getNomSeminaire() {
        return nomSeminaire;
    }

    public void setNomSeminaire(String nomSeminaire) {
        this.nomSeminaire = nomSeminaire;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    /**
   * To string
   * @return  String
   */
  public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("[idTypeSeminaire=").append(idTypeSeminaire).append("]  ");
      sb.append("[nomSeminaire=").append(nomSeminaire).append("]  ");
      sb.append("[dateDebut=").append(dateDebut).append("]  ");
      sb.append("[dateFin=").append(dateFin).append("]  ");
      return sb.toString();
  }

}
