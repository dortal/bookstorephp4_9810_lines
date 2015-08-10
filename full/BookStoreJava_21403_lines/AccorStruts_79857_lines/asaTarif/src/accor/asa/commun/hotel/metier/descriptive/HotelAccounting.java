package com.accor.asa.commun.hotel.metier.descriptive;

import java.io.Serializable;

import com.accor.asa.commun.metier.Address;
import com.accor.asa.commun.metier.Coordinates;

@SuppressWarnings("serial")
public class HotelAccounting implements Serializable {

	protected String		accountantName;
	protected String		accountantNumber;
	protected String		accountantTitle;
	protected String		functionCode;
	protected Address 		address;
	protected Coordinates	coordinates;
	protected String		invoicingName;
	protected Address		invoicingAddress;

	public HotelAccounting() {
		address				= new Address();
		coordinates			= new Coordinates();
		invoicingAddress	= new Address();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "[accountantName=" ).append( accountantName ).append( "], " );
		sb.append( "[accountantNumber=" ).append( accountantNumber ).append( "], " );
		sb.append( "[accountantTitle=" ).append( accountantTitle ).append( "], " );
		sb.append( "[functionCode=" ).append( functionCode ).append( "], " );
		sb.append( "[address=" ).append( address ).append( "], " );
		sb.append( "[coordinates=" ).append( coordinates ).append( "], " );
		sb.append( "[invoicingName=" ).append( invoicingName ).append( "], " );
		sb.append( "[invoicingAddress=" ).append( invoicingAddress ).append( "], " );
		return sb.toString();
	}

	public String getAccountantName() {
		return accountantName;
	}

	public void setAccountantName(String accountantName) {
		this.accountantName = accountantName;
	}

	public String getAccountantNumber() {
		return accountantNumber;
	}

	public void setAccountantNumber(String accountantNumber) {
		this.accountantNumber = accountantNumber;
	}

	public String getAccountantTitle() {
		return accountantTitle;
	}

	public void setAccountantTitle(String accountantTitle) {
		this.accountantTitle = accountantTitle;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
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

	public String getInvoicingName() {
		return invoicingName;
	}

	public void setInvoicingName(String invoicingName) {
		this.invoicingName = invoicingName;
	}

	public Address getInvoicingAddress() {
		return invoicingAddress;
	}

	public void setInvoicingAddress(Address invoicingAddress) {
		this.invoicingAddress = invoicingAddress;
	}
	
}
