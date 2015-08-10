package com.accor.asa.commun.hotel.metier.descriptive;

import java.io.Serializable;

import com.accor.asa.commun.metier.Address;
import com.accor.asa.commun.metier.Coordinates;

@SuppressWarnings("serial")
public class HotelBank implements Serializable {

	protected String		bankName;
	protected String		bankName2;
	protected String 		accountNumber;
	protected String		amexCode;
	protected String		vatNumber;
	protected String 		citiHotelCode;
	protected Address 		address;
	protected Coordinates	coordinates;
	
	public HotelBank() {
		address		= new Address();
		coordinates	= new Coordinates();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "[bankName=" ).append( bankName ).append( "], " );
		sb.append( "[bankName2=" ).append( bankName2 ).append( "], " );
		sb.append( "[accountNumber=" ).append( accountNumber ).append( "], " );
		sb.append( "[amexCode=" ).append( amexCode ).append( "], " );
		sb.append( "[vatNumber=" ).append( vatNumber ).append( "], " );
		sb.append( "[citiHotelCode=" ).append( citiHotelCode ).append( "], " );
		sb.append( "[address=" ).append( address ).append( "], " );
		sb.append( "[coordinates=" ).append( coordinates ).append( "], " );
		return sb.toString();
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankName2() {
		return bankName2;
	}
	public void setBankName2(String bankName2) {
		this.bankName2 = bankName2;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAmexCode() {
		return amexCode;
	}
	public void setAmexCode(String amexCode) {
		this.amexCode = amexCode;
	}
	public String getVatNumber() {
		return vatNumber;
	}
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}
	public String getCitiHotelCode() {
		return citiHotelCode;
	}
	public void setCitiHotelCode(String citiHotelCode) {
		this.citiHotelCode = citiHotelCode;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Coordinates getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
}
