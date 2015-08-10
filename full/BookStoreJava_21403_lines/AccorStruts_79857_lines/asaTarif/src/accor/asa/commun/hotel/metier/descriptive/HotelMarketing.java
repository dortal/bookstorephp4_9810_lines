package com.accor.asa.commun.hotel.metier.descriptive;

import java.io.Serializable;

import com.accor.asa.commun.metier.Address;
import com.accor.asa.commun.metier.Coordinates;

@SuppressWarnings("serial")
public class HotelMarketing implements Serializable {

	protected String        contact;
	protected String 		contactTitle;
	protected String  		functionCode;
	protected Address		address;
	protected Coordinates	coordinates;
	
	public HotelMarketing() {
		address		= new Address();
		coordinates	= new Coordinates();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "[contact=" ).append( contact ).append( "], " );
		sb.append( "[contactTitle=" ).append( contactTitle ).append( "], " );
		sb.append( "[functionCode=" ).append( functionCode ).append( "], " );
		sb.append( "[address=" ).append( address ).append( "], " );
		sb.append( "[coordinates=" ).append( coordinates ).append( "], " );
		return sb.toString();
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactTitle() {
		return contactTitle;
	}

	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	

}
