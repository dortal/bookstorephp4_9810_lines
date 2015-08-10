package com.accor.asa.commun.user.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.AsaDate;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.commun.internationalisation.Translator;
import com.accor.commun.internationalisation.TranslatorFactory;

@SuppressWarnings("serial")
public class User extends UserLight {
	
	public static final int LOGIN_MAX_LENGTH 	= 6;
	public static final int PASSWORD_MAX_LENGTH = 12;

	public static final String STATUS_DELETED 	= "d"; //Supprimé
    public static final String STATUS_IN 		= "i"; //Identifie dans le systeme (Connecté à l'application)

	public static final int NO_CONSTRAINT_ERROR 		= 0;
	public static final int HOTEL_NOT_IN_TARS_ERROR 	= -1;
	public static final int SALE_ZONE_ERROR 			= -2;
	public static final int ACTION_ERROR 				= -3;
	public static final int TERRITORY_ERROR 			= -4;
	public static final int DUPLICATE_USER_ERROR		= -5;
	public static final int INSUFFICIENT_DATA_ERROR		= -6;
	public static final int TOO_MANY_USERS_ERROR		= -7;
	public static final int UNKNOWN_USER_ERROR			= -8;
	public static final int UNKNOWN_MANAGER_ERROR		= -9;
	
	protected String 		password;
	protected String 		languageCode;
	protected String 		countryCode;
	protected String 		city;
	protected String 		saleRegionId;
	protected String 		marketId;
	protected String 		profileId;
	protected String 		status;
	protected String 		comments;
	protected Integer 		saleOfficeCode;
	protected String 		managerCode;
	protected String 		defaultTerritoryId;
	protected AsaDate 		dateCreation;
	protected AsaDate 		dateSuppression;
	protected AsaDate 		dateMaj;
	protected String		author;
	protected String		updater;
	
	public User() {}
	
	public String toString() {
		StringBuffer sb = new StringBuffer(); 
		sb.append( super.toString() );
		sb.append( "[password=" ).append( password ).append( "], " );
		sb.append( "[languageCode=" ).append( languageCode ).append( "], " );
		sb.append( "[countryCode=" ).append( countryCode ).append( "], " );
		sb.append( "[city=" ).append( city ).append( "], " );
		sb.append( "[saleRegionId=" ).append( saleRegionId ).append( "], " );
		sb.append( "[marketId=" ).append( marketId ).append( "], " );
		sb.append( "[profileId=" ).append( profileId ).append( "], " );
		sb.append( "[status=" ).append( status ).append( "], " );
		sb.append( "[comments=" ).append( comments ).append( "], " );
		sb.append( "[saleOfficeCode=" ).append( saleOfficeCode ).append( "], " );
		sb.append( "[managerCode=" ).append( managerCode ).append( "], " );
		sb.append( "[defaultTerritoryId=" ).append( defaultTerritoryId ).append( "], " );
		return sb.toString();
	}

	/***
	 * Methode de verification de l'absence de l objet a inserer dans la liste
	 * @param listItems
	 * @return
	 */
/*
	public boolean notExist( final ListUsers users ) {

		if( users == null )
			return true;
		
		Object o;
		
		for( int i=0, size=users.size(); i<size; i++ ) {
			o = users.get( i );
			if( o instanceof User ) {
				if( StringUtils.equalsIgnoreCase( ( (User) o ).getFirstName(), firstName )
					&& StringUtils.equalsIgnoreCase( ( (User) o ).getLastName(), lastName ) )
					return false;
			}
		}
		
		return true;
	}
*/
	/***
	 * Methode de verification de l'unicite de l'objet dans la liste
	 * @param listItems
	 * @return
	 */
/*
	public boolean isUnique( final ListUsers users ) {

		if( users == null )
			return true;
		
		Object o;
		
		for( int i=0, size=users.size(); i<size; i++ ) {
			o = users.get( i );
			if( o instanceof User ) {
				if( StringUtils.equalsIgnoreCase( ( (User) o ).getFirstName(), firstName )
					&& StringUtils.equalsIgnoreCase( ( (User) o ).getLastName(), lastName )
					&& ( ! ( ( (User) o ).getId().equals( id ) ) ) )
					return false;
			}
		}
		
		return true;
	}
*/	
	/***
	 * Methode de récupération du message d'erreur Sybase associé au codeErreur
	 * @param codeError
	 * @param contexte
	 * @return
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 */
	public static String getErrorMessage( final int codeError, final Contexte contexte ) {
		
		StringBuffer message = new StringBuffer();
		Translator trans = TranslatorFactory.getTranslator( contexte.getCodeLangue(), true );
		
		switch( codeError ) {

			case HOTEL_NOT_IN_TARS_ERROR :
				message.append( trans.getLibelle( "ADMIN_USERS_ERROR_HOTEL_NOT_IN_TARS" ) );
				break;
			
			case SALE_ZONE_ERROR : 
				message.append( trans.getLibelle( "ADMIN_USERS_ERROR_SALE_ZONE" ) );
				break;
				
			case ACTION_ERROR :
				message.append( trans.getLibelle( "ADMIN_USERS_ERROR_ACTION" ) );
				break;

				
			case TERRITORY_ERROR : 
				message.append( trans.getLibelle( "ADMIN_USERS_ERROR_TERRITORY" ) );
				break;

			case DUPLICATE_USER_ERROR : 
				message.append( trans.getLibelle( "ADMIN_USERS_ERROR_DUPLICATE_USER" ) );
				break;

			case INSUFFICIENT_DATA_ERROR :
				message.append( trans.getLibelle( "ADMIN_USERS_ERROR_INSUFFICIENT_DATA" ) );
				break;

			case TOO_MANY_USERS_ERROR :
				message.append( trans.getLibelle( "ADMIN_USERS_ERROR_TOO_MANY_USERS" ) );
				break;
				
			case UNKNOWN_USER_ERROR :
				message.append( trans.getLibelle( "ADMIN_USERS_ERROR_UNKNOWN_USER" ) );
				break;
				
			case UNKNOWN_MANAGER_ERROR :
				message.append( trans.getLibelle( "ADMIN_USERS_ERROR_UNKNOWN_MANAGER" ) );
				break;
		}
		return message.toString();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getDefaultTerritoryId() {
		return defaultTerritoryId;
	}

	public void setDefaultTerritoryId(String defaultTerritoryId) {
		this.defaultTerritoryId = defaultTerritoryId;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getManagerCode() {
		return managerCode;
	}

	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}

	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public Integer getSaleOfficeCode() {
		return saleOfficeCode;
	}

	public void setSaleOfficeCode(Integer saleOfficeCode) {
		this.saleOfficeCode = saleOfficeCode;
	}

	public void setSaleOfficeCode(int saleOfficeCode) {
		this.saleOfficeCode = new Integer(saleOfficeCode);
	}

	public String getSaleRegionId() {
		return saleRegionId;
	}

	public void setSaleRegionId(String saleRegionId) {
		this.saleRegionId = saleRegionId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the dateCreation
	 */
	public AsaDate getDateCreation() {
		return dateCreation;
	}

	/**
	 * @param dateCreation the dateCreation to set
	 */
	public void setDateCreation(AsaDate dateCreation) {
		this.dateCreation = dateCreation;
	}

	/**
	 * @return the dateSuppression
	 */
	public AsaDate getDateSuppression() {
		return dateSuppression;
	}

	/**
	 * @param dateSuppression the dateSuppression to set
	 */
	public void setDateSuppression(AsaDate dateSuppression) {
		this.dateSuppression = dateSuppression;
	}

	/**
	 * @return the dateMaj
	 */
	public AsaDate getDateMaj() {
		return dateMaj;
	}

	/**
	 * @param dateMaj the dateMaj to set
	 */
	public void setDateMaj(AsaDate dateMaj) {
		this.dateMaj = dateMaj;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the updater
	 */
	public String getUpdater() {
		return updater;
	}

	/**
	 * @param updater the updater to set
	 */
	public void setUpdater(String updater) {
		this.updater = updater;
	}
}
