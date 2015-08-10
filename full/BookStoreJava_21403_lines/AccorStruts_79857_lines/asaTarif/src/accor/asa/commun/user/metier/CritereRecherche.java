package com.accor.asa.commun.user.metier;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.utiles.FilesPropertiesCache;
import com.accor.asa.commun.utiles.Proprietes;
import com.accor.asa.vente.commun.log.Log;

/**
 * Title:        Projet Asa
 * Criteres de recherche sur les utilisateurs
 * <b>extends</b>      com.accor.asa.vente.utilisateurs.metier.UtilisateurValue
 * Copyright:    Copyright (c) 2001
 * Company:      Valtech
 * @author David Dreistadt
 * @version 1.0
 */

@SuppressWarnings("serial")
public class CritereRecherche extends User {

	public static final int ALL_USERS = -1;

	private int maxRowsInReturn = ALL_USERS;
	private boolean responsableDossier = false;
	private String creationDateDebut;
	private String deletedDateDebut;
	private String majDateDebut;
	private String creationDateFin;
	private String deletedDateFin;
	private String majDateFin;
	
	public CritereRecherche() {
		super();

		int nbMaxUtilisateursRetour = 0;  // on prendra cettevaleur par défaut, qui correspond à une non limitation
        try {
            nbMaxUtilisateursRetour = Integer.parseInt(
            		FilesPropertiesCache.getInstance().getValue(
            				Proprietes.PROPERTIES_FILE_NAME, Proprietes.TAILLE_MAX_RSET_GRANDE_LISTE )
            );
        } catch (NumberFormatException e) {
            if( Log.isTechniqueDebug() ) {        
              Log.info( "", "CritereRecherche", "CritereRecherche","la propriété NB_MAX_UTILISATEURS_RETOUR a une valeur abbérante : "+e.getMessage());
            }
        }
        this.maxRowsInReturn = nbMaxUtilisateursRetour;
	}
	
	/** recherche multi critere 
	 * 
	 * @param status mis à actif par défaut si null
	 */
	public CritereRecherche( final String login, final String password, final String firstName, final String lastName,
			 final String civility, final String phone, final String fax, final String email, final String hotelId,
			 final String languageCode, final String countryCode, final String saleZoneCode, String profileId,
			 final String status, final String saleOfficeCode, final String manager, final String saleRegionCode,
			 final String city, final String marketId, int maxRowsInReturn ) {
		
		super();
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.civility = civility;
		this.coordinates.setPhone( phone );
		this.coordinates.setFax( fax );
		this.coordinates.setMail( email );
		this.hotelId = hotelId;
		this.languageCode = languageCode;
		this.countryCode = countryCode;
		this.saleZoneId = saleZoneCode;
		this.profileId = profileId;
		// recherche multi critere : utilisateur activé par défaut
		this.status = (status==null)?User.STATUS_IN:status;
		if( StringUtils.isNotBlank( saleOfficeCode ) )
			this.saleOfficeCode = new Integer( saleOfficeCode );
		this.managerCode = manager;
		this.marketId = marketId;
		this.saleRegionId = saleRegionCode;
		this.city = city;
		this.maxRowsInReturn = maxRowsInReturn;
	}

	public int getNbRetourLignesMax() {
		return maxRowsInReturn;
	}
	
	public void setNbRetourLignesMax( int nbRows ) {
		this.maxRowsInReturn = nbRows;
	}
	public void setResponsableDossier(boolean isResponsable) {
		responsableDossier = isResponsable;
	}
	
	public boolean isResponsableDossier() {
		return responsableDossier;
	}

	public String toString() {
		return super.toString()
			+ " maxRowsInReturn "
			+ String.valueOf(maxRowsInReturn);
	}

	/**
	 * @return the maxRowsInReturn
	 */
	public int getMaxRowsInReturn() {
		return maxRowsInReturn;
	}

	/**
	 * @param maxRowsInReturn the maxRowsInReturn to set
	 */
	public void setMaxRowsInReturn(int maxRowsInReturn) {
		this.maxRowsInReturn = maxRowsInReturn;
	}

	/**
	 * @return the creationDateDebut
	 */
	public String getCreationDateDebut() {
		return creationDateDebut;
	}

	/**
	 * @param creationDateDebut the creationDateDebut to set
	 */
	public void setCreationDateDebut(String creationDateDebut) {
		this.creationDateDebut = creationDateDebut;
	}

	/**
	 * @return the deletedDateDebut
	 */
	public String getDeletedDateDebut() {
		return deletedDateDebut;
	}

	/**
	 * @param deletedDateDebut the deletedDateDebut to set
	 */
	public void setDeletedDateDebut(String deletedDateDebut) {
		this.deletedDateDebut = deletedDateDebut;
	}

	/**
	 * @return the majDateDebut
	 */
	public String getMajDateDebut() {
		return majDateDebut;
	}

	/**
	 * @param majDateDebut the majDateDebut to set
	 */
	public void setMajDateDebut(String majDateDebut) {
		this.majDateDebut = majDateDebut;
	}

	/**
	 * @return the creationDateFin
	 */
	public String getCreationDateFin() {
		return creationDateFin;
	}

	/**
	 * @param creationDateFin the creationDateFin to set
	 */
	public void setCreationDateFin(String creationDateFin) {
		this.creationDateFin = creationDateFin;
	}

	/**
	 * @return the deletedDateFin
	 */
	public String getDeletedDateFin() {
		return deletedDateFin;
	}

	/**
	 * @param deletedDateFin the deletedDateFin to set
	 */
	public void setDeletedDateFin(String deletedDateFin) {
		this.deletedDateFin = deletedDateFin;
	}

	/**
	 * @return the majDateFin
	 */
	public String getMajDateFin() {
		return majDateFin;
	}

	/**
	 * @param majDateFin the majDateFin to set
	 */
	public void setMajDateFin(String majDateFin) {
		this.majDateFin = majDateFin;
	}

}