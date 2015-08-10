package com.accor.asa.commun.user.metier;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Address;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.Coordinates;
import com.accor.asa.commun.metier.Element;
import com.accor.commun.internationalisation.Translator;
import com.accor.commun.internationalisation.TranslatorFactory;

@SuppressWarnings("serial")
public class SaleOffice extends Element {

	public static final int NO_CONSTRAINT_ERROR						= 0;
	public static final int USER_CONSTRAINT_ERROR					= 1;
	public static final int DUPLICATE_SALEOFFICE_CONSTRAINT_ERROR	= 2;

	protected Address 		address 		= new Address();
	protected Coordinates 	coordinates		= new Coordinates();
	protected boolean		deleted			= false;
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( super.toString() );
		sb.append( "[address=" ).append( address ).append( "]," );
		sb.append( "[coordinates=" ).append( coordinates ).append( "]," );
		sb.append( "[deleted=" ).append( deleted ).append( "]," );
		return sb.toString();
	}
	
	/***
	 * Methode de verification de l'absence de l objet a inserer dans la liste
	 * @param listItems
	 * @return
	 */
	public boolean notExist( final List<SaleOffice> offices ) {

		if( offices == null )
			return true;
		
		for( int i=0, size=offices.size(); i<size; i++ ) {
			if( StringUtils.equalsIgnoreCase( offices.get( i ).getCode(), code ) 
				|| StringUtils.equalsIgnoreCase( offices.get( i ).getLibelle(), libelle ) )
				return false;
		}
		
		return true;
	}
	
	/***
	 * Methode de verification de l'unicite de l'objet dans la liste
	 * @param listItems
	 * @return
	 */
	public boolean isUnique( final List<SaleOffice> offices ) {
		
		if( offices == null )
			return true;
		
		for( int i=0, size=offices.size(); i<size; i++ ) {
			if( StringUtils.equalsIgnoreCase( offices.get( i ).getLibelle(), libelle ) 
			&& ( ! ( StringUtils.equalsIgnoreCase( offices.get( i ).getCode(), code ) ) ) )
			return false;
		}
		
		return true;
	}

	/***
	 * Methode de récupération du message d'erreur Sybase associé au codeErreur
	 * @param codeError
	 * @param contexte
	 * @return
	 * @throws TechnicalException
	 */
	public static String getErrorMessage( final int codeError, final Contexte contexte ) throws TechnicalException {
		
		StringBuffer message = new StringBuffer();
		Translator trans = TranslatorFactory.getTranslator( contexte.getCodeLangue(), true );
	
		switch( codeError ) {
			case USER_CONSTRAINT_ERROR : 
				message.append(trans.getLibelle("ADMIN_USERS_SALESOFFICE_USER_CONSTRAINT_ERROR"));
				break;
			case DUPLICATE_SALEOFFICE_CONSTRAINT_ERROR : 
				message.append( trans.getLibelle( "ADMIN_USERS_SALESOFFICE_DUPLICATE_OFFICE" ) );
				break;
		}
		return message.toString();
	}

	public String getAddress1() {
		return address.getAddress1();
	}

	public String getAddress2() {
		return address.getAddress2();
	}
	public String getAddress3() {
		return address.getAddress3();
	}
	public String getCountryCode() {
		return address.getCountryId();
	}
	public String getZipCode() {
		return address.getZipCode();
	}
	public String getMail() {
		return coordinates.getMail();
	}
	public String getFax() {
		return coordinates.getFax();
	}
	public String getPhone() {
		return coordinates.getPhone();
	}
	public String getCity() {
		return address.getCity();
	}

	public String getCountryName() {
		return address.getCountryName();
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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
