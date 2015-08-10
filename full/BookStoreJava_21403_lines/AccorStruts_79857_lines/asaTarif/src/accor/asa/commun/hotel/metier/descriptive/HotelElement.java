package com.accor.asa.commun.hotel.metier.descriptive;

import java.io.Serializable;

import com.accor.asa.commun.metier.Element;

@SuppressWarnings("serial")
public class HotelElement implements Serializable {

	protected String 	name;
	protected Element	type;

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "[name=" ).append( name ).append( "], " );
		sb.append( "[type=" ).append( type ).append( "], " );
		return sb.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Element getType() {
		return type;
	}

	public void setType(Element type) {
		this.type = type;
	}
	
	public String getTypeCode() {
		if( type == null )
			return null;
		
		return type.getCode();
	}
	
	public String getTypeLabel() {
		if( type == null )
			return null;
		
		return type.getLibelle();
	}
}
