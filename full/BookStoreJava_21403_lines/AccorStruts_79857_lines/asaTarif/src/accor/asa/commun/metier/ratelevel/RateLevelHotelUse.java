/*
 * Created on 25 juil. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.accor.asa.commun.metier.ratelevel;

/**
 * @author FCHIVAUX
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("serial")
public class RateLevelHotelUse extends RateLevel {

  protected String  description;


  /**
   * To string
   * @return  String
   */
  public String toString() {
  		StringBuffer sb = new StringBuffer();
  		sb.append( super.toString() );
      sb.append("[description=").append(description).append("]  ");
      return sb.toString();
  }

  
  public RateLevelHotelUse() {super();}
  
  public RateLevelHotelUse( String code, String libelle, String description, Integer idFamilleTarif,
  		String libFamilleTarif, String codeLangue ) {
  	super( code, libelle, idFamilleTarif, libFamilleTarif, codeLangue );
  	this.description = description;
  }
  
  public String getDescription() {
    return description;
  }
  /**
   * @param descTarif The descTarif to set.
   */
  public void setDescription(String description) {
    this.description = description;
  }
}
