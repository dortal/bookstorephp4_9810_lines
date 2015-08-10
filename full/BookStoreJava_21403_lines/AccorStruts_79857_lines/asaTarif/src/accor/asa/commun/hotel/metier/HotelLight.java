package com.accor.asa.commun.hotel.metier;

import java.io.Serializable;

import com.accor.asa.commun.metier.Address;
import com.accor.asa.commun.metier.Coordinates;
import com.accor.asa.commun.metier.categorie.AsaCategory;
import com.accor.asa.commun.metier.devise.Devise;

@SuppressWarnings("serial")
public class HotelLight implements Serializable {

    protected String    	code;
    protected String    	name;
    protected Address   	address;
    protected Coordinates	coordinates; 
    protected Devise    	currency;
    protected String    	languageCode;
    protected AsaCategory 	asaCategory;

    public HotelLight() {
        address 	= new Address();
        coordinates = new Coordinates(); 
        currency	= new Devise();
        asaCategory	= new AsaCategory();
    }
    
    /**
     * To string
     * @return  String
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[code=").append(code).append("]  ");
        sb.append("[name=").append(name).append("]  ");
        sb.append("[address=").append(address).append("]  ");
        sb.append("[coordinates=").append(coordinates).append("]  ");
        sb.append("[currency=").append(currency).append("]  ");
        sb.append("[languageCode=").append(languageCode).append("]  ");
        sb.append("[asaCategory=").append(asaCategory).append("]  ");
        return sb.toString();
    }

	public AsaCategory getAsaCategory() {
		return asaCategory;
	}

	public void setAsaCategory(AsaCategory asaCategory) {
		this.asaCategory = asaCategory;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Devise getCurrency() {
		return currency;
	}

	public String getCurrencyCode() {
		if( currency == null )
			return null;
		return currency.getCode();
	}

	public String getCurrencyLabel() {
		if( currency == null )
			return null;
		return currency.getLibelle();
	}

	public void setCurrency(Devise currency) {
		this.currency = currency;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	
	public String getCodeAsaCategory() {
		if( asaCategory != null )
			return asaCategory.getCode();
		return null;
	}

	public String getCity() {
		return this.address.getCity();
	}
	
	public String getCountryId() {
		return this.address.getCountryId();
	}
	
	public String getCountryName() {
		return this.address.getCountryName();
	}

	public String getPhone() {
		return this.coordinates.getPhone();
	}

	public String getFax() {
		return this.coordinates.getFax();
	}

	public String getMail() {
		return this.coordinates.getMail();
	}

	public String getPhonePrefix() {
		return this.coordinates.getPhonePrefix();
	}
}
