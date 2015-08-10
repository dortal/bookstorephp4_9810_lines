/*
 * Created on 12 oct. 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.accor.asa.commun.hotel.metier.sl;

import com.accor.asa.commun.metier.AsaDate;

/**
 * @author FCHIVAUX
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CriteresCommercialsRechercheHotels {

  private AsaDate dateDebut = null;
  private AsaDate dateFin = null;
  private String  codeTarif = null;

  public String getCodeTarif() {
    return codeTarif;
  }
  
  public void setCodeTarif(String codeTarif) {
    this.codeTarif = codeTarif;
  }
  
  public AsaDate getDateDebut() {
    return dateDebut;
  }
  
  public void setDateDebut(AsaDate dateDebut) {
    this.dateDebut = dateDebut;
  }
  
  public AsaDate getDateFin() {
    return dateFin;
  }

  public void setDateFin(AsaDate dateFin) {
    this.dateFin = dateFin;
  }

  public String toString() {
  	StringBuffer sb = new StringBuffer();
    sb.append( "dateDebut = " + dateDebut + " - " );
    sb.append( "dateFin = " + dateFin + " - " );
    sb.append( "codeTarif = " + codeTarif + " - " );
    return sb.toString();
  }
}
  
