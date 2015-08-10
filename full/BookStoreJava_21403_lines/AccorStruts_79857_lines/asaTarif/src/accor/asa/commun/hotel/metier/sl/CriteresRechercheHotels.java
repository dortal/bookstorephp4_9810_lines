/*
 * Created on 12 oct. 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.accor.asa.commun.hotel.metier.sl;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.accor.services.framework.enterprise.common.basictype.BasicType;

/**
 * @author FCHIVAUX
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
public class CriteresRechercheHotels implements Serializable {
  
  private String  rid;
  private String  nomHotel;
  private String  ville;
  private String  codePays;
  private String  codeMarque;
  private boolean hotelMice;
  private String  zoneGeoType;
  private String  zoneGeoValue;
  private CriteresCommercialsRechercheHotels commercial;
  private CriteresCapacitesRechercheHotels   capacity; 
  private CriteresInformatifsRechercheHotels informative; 
  private BasicType segment = null;
  
	public String getCodePays() {
		return codePays;
	}
	public void setCodePays(String codePays) {
		this.codePays = codePays;
	}
	public String getCodeMarque() {
		return codeMarque;
	}
	public void setCodeMarque(String codeMarque) {
		this.codeMarque = codeMarque;
	}
	public String getNomHotel() {
		return nomHotel;
	}
	public void setNomHotel(String nomHotel) {
		this.nomHotel = nomHotel;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public CriteresCapacitesRechercheHotels getCapacity() {
		return capacity;
	}
	public void setCapacity(CriteresCapacitesRechercheHotels capacity) {
		this.capacity = capacity;
	}
	public CriteresInformatifsRechercheHotels getInformative() {
		return informative;
	}
	public void setInformative(CriteresInformatifsRechercheHotels informative) {
		this.informative = informative;
	}
  public CriteresCommercialsRechercheHotels getCommercial() {
    return commercial;
  }
  public void setCommercial(CriteresCommercialsRechercheHotels commercial) {
    this.commercial = commercial;
  }
  public boolean getHotelMice() {
    return hotelMice;
  }
  public void setHotelMice(boolean hotelMice) {
    this.hotelMice = hotelMice;
  }
  public String getZoneGeoType() {
    return zoneGeoType;
  }
  public void setZoneGeoType(String zoneGeoType) {
    this.zoneGeoType = zoneGeoType;
  }
  public String getZoneGeoValue() {
    return zoneGeoValue;
  }
  public void setZoneGeoValue(String zoneGeoValue) {
    this.zoneGeoValue = zoneGeoValue;
  }

  public BasicType getSegment() {
    return segment;
  }
  
  public void setSegment(BasicType segment) {
    this.segment = segment;
  }
  
  /**
   * retourne true si au moins l un des critères obligatoires a été renseignés
   * @return
   */
  public boolean haveValidCriteria() {
    return ( ! StringUtils.isEmpty( rid ) ) || ( ! StringUtils.isEmpty( nomHotel ) )
            || ( ! StringUtils.isEmpty( ville ) )|| ( ! StringUtils.isEmpty( codePays ) )
            || ( ! StringUtils.isEmpty( codeMarque ) );  
  }
  
  public String toString() {
		StringBuffer sb = new StringBuffer();
    sb.append( "rid = " + rid + "\n" );
    sb.append( "nomHotel = " + nomHotel + "\n" ); 
    sb.append( "ville = " + ville+ "\n" );
    sb.append( "codePays = " + codePays + "\n" );
    sb.append( "codeMarque = " + codeMarque + "\n" );
    sb.append( "zone_geo = " + zoneGeoType + " - " + zoneGeoValue );
    if( segment != null ) {
      sb.append( "codeTypeProduit = " + segment.getCodeType() + " - " );
      sb.append( "codeProduit = " + segment.getCode() + " - " );
      sb.append( "nomProduit = " + segment.getName() );
    }
    if( commercial != null )
      sb.append( "\ncommercial = \n " + commercial.toString() + "\n" ); 
    if( capacity != null )
    	sb.append( "\ncapacity = \n " + capacity.toString() + "\n" ); 
    if( informative != null )
    	sb.append( "\n" + informative.toString() + "\n" );; 
    return sb.toString();
  }
}
