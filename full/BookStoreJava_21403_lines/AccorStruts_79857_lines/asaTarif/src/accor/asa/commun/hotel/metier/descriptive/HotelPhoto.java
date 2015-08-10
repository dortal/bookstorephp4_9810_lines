package com.accor.asa.commun.hotel.metier.descriptive;

import java.io.Serializable;

import com.accor.asa.commun.metier.AsaDate;

@SuppressWarnings("serial")
public class HotelPhoto implements Serializable {

	protected String 	name;
	protected AsaDate 	updateDate;
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "[name=" ).append( name ).append( "], " );
		sb.append( "[updateDate=" ).append( updateDate ).append( "], " );
		return sb.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AsaDate getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(AsaDate updateDate) {
		this.updateDate = updateDate;
	}

}
