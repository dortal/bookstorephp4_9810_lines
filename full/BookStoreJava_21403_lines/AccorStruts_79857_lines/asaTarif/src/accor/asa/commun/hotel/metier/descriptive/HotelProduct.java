package com.accor.asa.commun.hotel.metier.descriptive;

@SuppressWarnings("serial")
public class HotelProduct extends HotelElement {

	protected String 	code;

	protected Integer	nbPax;
	protected Integer	quantity;
	protected String	situ;
	protected boolean 	free;
	protected boolean	booking;
	protected boolean	deleted;
	protected String 	pmsProductCode;
	protected Integer	displayOrder;
	protected String	flagType;
	protected Integer	productInRoom;
	protected String	vuGdsWeb;
	
	protected Integer	nbMaxRoom;
	protected Integer	nbMaxPax;
	protected Integer	nbSingleBed;
	protected Integer	nbDoubleBed;

	public HotelProduct() {}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( super.toString() );
		sb.append( "[code=" ).append( code ).append( "], " );
		sb.append( "[nbPax=" ).append( nbPax ).append( "], " );
		sb.append( "[quantity=" ).append( quantity ).append( "], " );
		sb.append( "[situ=" ).append( situ ).append( "], " );
		sb.append( "[free=" ).append( free ).append( "], " );
		sb.append( "[booking=" ).append( booking ).append( "], " );
		sb.append( "[deleted=" ).append( deleted ).append( "], " );
		sb.append( "[pmsProductCode=" ).append( pmsProductCode ).append( "], " );
		sb.append( "[displayOrder=" ).append( displayOrder ).append( "], " );
		sb.append( "[flagType=" ).append( flagType ).append( "], " );
		sb.append( "[productInRoom=" ).append( productInRoom ).append( "], " );
		sb.append( "[vuGdsWeb=" ).append( vuGdsWeb ).append( "], " );
		sb.append( "[nbMaxRoom=" ).append( nbMaxRoom ).append( "], " );
		sb.append( "[nbMaxPax=" ).append( nbMaxPax ).append( "], " );
		sb.append( "[nbSingleBed=" ).append( nbSingleBed ).append( "], " );
		sb.append( "[nbDoubleBed=" ).append( nbDoubleBed ).append( "], " );
		return sb.toString();
	}

	public Integer getNbPax() {
		return nbPax;
	}

	public void setNbPax(Integer nbPax) {
		this.nbPax = nbPax;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Boolean isSitu() {
		if( situ == null )
			return null;
		
		return new Boolean( "1".equals( situ ) );
	}

	public void setSitu(String situ) {
		this.situ = situ;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public boolean isBooking() {
		return booking;
	}

	public void setBooking(boolean booking) {
		this.booking = booking;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getPmsProductCode() {
		return pmsProductCode;
	}

	public void setPmsProductCode(String pmsProductCode) {
		this.pmsProductCode = pmsProductCode;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getFlagType() {
		return flagType;
	}

	public void setFlagType(String flagType) {
		this.flagType = flagType;
	}

	public Integer getProductInRoom() {
		return productInRoom;
	}

	public void setProductInRoom(Integer productInRoom) {
		this.productInRoom = productInRoom;
	}

	public String getVuGdsWeb() {
		return vuGdsWeb;
	}

	public void setVuGdsWeb(String vuGdsWeb) {
		this.vuGdsWeb = vuGdsWeb;
	}

	public Integer getNbMaxRoom() {
		return nbMaxRoom;
	}

	public void setNbMaxRoom(Integer nbMaxRoom) {
		this.nbMaxRoom = nbMaxRoom;
	}

	public Integer getNbMaxPax() {
		return nbMaxPax;
	}

	public void setNbMaxPax(Integer nbMaxPax) {
		this.nbMaxPax = nbMaxPax;
	}

	public Integer getNbSingleBed() {
		return nbSingleBed;
	}

	public void setNbSingleBed(Integer nbSingleBed) {
		this.nbSingleBed = nbSingleBed;
	}

	public Integer getNbDoubleBed() {
		return nbDoubleBed;
	}

	public void setNbDoubleBed(Integer nbDoubleBed) {
		this.nbDoubleBed = nbDoubleBed;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
