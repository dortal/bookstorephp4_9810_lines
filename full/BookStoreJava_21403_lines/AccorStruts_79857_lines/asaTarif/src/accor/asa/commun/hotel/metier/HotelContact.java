package com.accor.asa.commun.hotel.metier;

/**
 * @author ASADEV5
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
@SuppressWarnings("serial")
public class HotelContact implements java.io.Serializable{

	protected String name;
	protected String function;

	/**
	 * Constructor for ContactHotel.
	 */
	public HotelContact() {}
	
	public String toString() {
    	StringBuffer sb = new StringBuffer();
    	sb.append( "[name=" ).append( name ).append( "]," );
    	sb.append( "[function=" ).append( function ).append( "]," );
    	return sb.toString();
	}
	
	public HotelContact(String name, String function) {
		this.name=name;
		this.function=function;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}
}
