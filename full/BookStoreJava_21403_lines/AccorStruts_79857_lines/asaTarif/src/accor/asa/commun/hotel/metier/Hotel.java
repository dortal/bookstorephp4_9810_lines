package com.accor.asa.commun.hotel.metier;

import com.accor.asa.commun.metier.Element;
import com.accor.asa.commun.user.metier.SaleZone;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 16 mars 2006
 * Time: 14:42:19
 */
@SuppressWarnings("serial")
public class Hotel extends HotelLight {
    
    protected Element		mark;
    protected Element 		chain;
    protected Element 		DOP;
    protected Element 		DGR;
    protected Element		place;
    protected Element		widePlace;
    protected SaleZone		saleZone;
    protected String 		flagAsaTarifLight;
    protected String 		flagAsaTarifLoisir;
    protected String		resaPhone;
    protected String		resaFax;

    // ================ GETTER AND SETTER ======================

    public Hotel() {
    	super();
		mark 	= new Element();
		chain 	= new Element();
    }
    
    /**
     * To string
     * @return  String
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append( super.toString() );
        sb.append("[mark=").append(mark).append("]  ");
        sb.append("[chain=").append(chain).append("]  ");
        sb.append("[DOP=").append(DOP).append("]  ");
        sb.append("[DGR=").append(DGR).append("]  ");
        sb.append("[DOP=").append(place).append("]  ");
        sb.append("[DGR=").append(widePlace).append("]  ");
        sb.append("[saleZone=").append(saleZone).append("]  ");
        sb.append("[flagAsaTarifLight=").append(flagAsaTarifLight).append("]  ");
        sb.append("[resaPhone=").append(resaPhone).append("]  ");
        sb.append("[resaFax=").append(resaFax).append("]  ");
        return sb.toString();
    }

	public Element getMark() {
		return mark;
	}

	public String getMarkCode() {
		if( mark == null )
			return null;
		return mark.getCode();
	}

	public String getMarkLabel() {
		if( mark == null )
			return null;
		
		return mark.getLibelle();
	}
	
	public void setMark(Element mark) {
		this.mark = mark;
	}

	public Element getChain() {
		return chain;
	}

	public String getChainCode() {
		if( chain == null )
			return null;
		return chain.getCode();
	}

	public String getChainLabel() {
		if( chain == null )
			return null;
		return chain.getLibelle();
	}

	public void setChain(Element chain) {
		this.chain = chain;
	}

	public Element getDOP() {
		return DOP;
	}

	public String getDOPCode() {
		if( DOP == null )
			return null;
		return DOP.getCode();
	}

	public String getDOPLabel() {
		if( DOP == null )
			return null;
		return DOP.getLibelle();
	}

	public void setDOP(Element DOP) {
		this.DOP = DOP;
	}

	public Element getDGR() {
		return DGR;
	}

	public String getDGRCode() {
		if( DGR == null )
			return null;
		return DGR.getCode();
	}

	public String getDGRLabel() {
		if( DGR == null )
			return null;
		return DGR.getLibelle();
	}

	public void setDGR(Element DGR) {
		this.DGR = DGR;
	}

	public SaleZone getSaleZone() {
		return saleZone;
	}

	public String getSaleZoneCode() {
		if( saleZone == null )
			return null;
		return saleZone.getCode();
	}

	public String getSaleZoneLabel() {
		if( saleZone == null )
			return null;
		return saleZone.getLibelle();
	}

	public void setSaleZone(SaleZone saleZone) {
		this.saleZone = saleZone;
	}

	public String getFlagAsaTarifLight() {
		return flagAsaTarifLight;
	}

	public void setFlagAsaTarifLight(String flagAsaTarifLight) {
		this.flagAsaTarifLight = flagAsaTarifLight;
	}

    public String getFlagAsaTarifLoisir() {
        return flagAsaTarifLoisir;
    }

    public void setFlagAsaTarifLoisir(String flagAsaTarifLoisir) {
        this.flagAsaTarifLoisir = flagAsaTarifLoisir;
    }

	public String getResaPhone() {
		return resaPhone;
	}

	public void setResaPhone(String resaPhone) {
		this.resaPhone = resaPhone;
	}

	public String getResaFax() {
		return resaFax;
	}

	public void setResaFax(String resaFax) {
		this.resaFax = resaFax;
	}

	public Element getPlace() {
		return place;
	}

	public String getPlaceCode() {
		if( place == null )
			return null;
		return place.getCode();
	}

	public String getPlaceLabel() {
		if( place == null )
			return null;
		return place.getLibelle();
	}

	public void setPlace(Element place) {
		this.place = place;
	}

	public Element getWidePlace() {
		return widePlace;
	}

	public String getWidePlaceCode() {
		if( widePlace == null )
			return null;
		return widePlace.getCode();
	}

	public String getWidePlaceLabel() {
		if( widePlace == null )
			return null;
		return widePlace.getLibelle();
	}

	public void setWidePlace(Element widePlace) {
		this.widePlace = widePlace;
	}
}
