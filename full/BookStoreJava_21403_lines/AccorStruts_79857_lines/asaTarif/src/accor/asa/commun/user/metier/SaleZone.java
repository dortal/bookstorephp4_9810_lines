package com.accor.asa.commun.user.metier;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.Element;
import com.accor.commun.internationalisation.Translator;
import com.accor.commun.internationalisation.TranslatorFactory;

@SuppressWarnings("serial")
public class SaleZone extends Element {

	public static final int NO_CONSTRAINT_ERROR					= 0;
	public static final int ACCOUNT_CONSTRAINT_ERROR			= 1;
	public static final int USER_CONSTRAINT_ERROR				= 2;
	public static final int DUPLICATE_SALEZONE_CONSTRAINT_ERROR	= 3;

	protected String 	regionCode;
	protected boolean	deleted	= false;

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( super.toString() );
		sb.append( "[regionCode=" ).append( regionCode ).append( "]," );
		sb.append( "[deleted=" ).append( deleted ).append( "]," );
		return sb.toString();
	}

	/***
	 * Methode de verification de l'absence de l objet a inserer dans la liste
	 * @param listItems
	 * @return
	 */
	public boolean notExist( final List<SaleZone> zones ) {

		if( zones == null )
			return true;
		
		for( int i=0, size=zones.size(); i<size; i++ ) {
			if( StringUtils.equalsIgnoreCase( zones.get( i ).getCode(), code ) 
				|| StringUtils.equalsIgnoreCase( zones.get( i ).getLibelle(), libelle ) )
				return false;
		}
		
		return true;
	}
	
	/***
	 * Methode de verification de l'unicite de l'objet dans la liste
	 * @param listItems
	 * @return
	 */
	public boolean isUnique( final List<SaleZone> zones ) {
		
		if( zones == null )
			return true;
		
		for( int i=0, size=zones.size(); i<size; i++ ) {
			if( StringUtils.equalsIgnoreCase( zones.get( i ).getLibelle(), libelle ) 
			&& ( ! ( StringUtils.equalsIgnoreCase( zones.get( i ).getCode(), code ) ) ) )
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
			case ACCOUNT_CONSTRAINT_ERROR :
				message.append(trans.getLibelle("ADMIN_USERS_SALESZONE_ACCOUNT_CONSTRAINT_ERROR"));
				break;
			case USER_CONSTRAINT_ERROR :
				message.append(trans.getLibelle("ADMIN_USERS_SALESZONE_USER_CONSTRAINT_ERROR"));
				break;
			case DUPLICATE_SALEZONE_CONSTRAINT_ERROR : 
				message.append( trans.getLibelle( "ADMIN_USERS_SALESZONE_DUPLICATE_ZONE" ) );
				break;
		}
		return message.toString();
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
