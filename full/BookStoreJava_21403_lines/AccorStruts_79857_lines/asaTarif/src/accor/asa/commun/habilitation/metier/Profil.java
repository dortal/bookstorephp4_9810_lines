package com.accor.asa.commun.habilitation.metier;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.habilitation.metier.visibilite.AxeVisibilite;
import com.accor.asa.commun.habilitation.process.HabilitationFacade;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.Element;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.user.metier.UserLight;
import com.accor.asa.commun.utiles.FilesPropertiesCache;
import com.accor.asa.commun.utiles.Proprietes;
import com.accor.commun.internationalisation.Translator;
import com.accor.commun.internationalisation.TranslatorFactory;

/**
 * Title:        Projet Asa
 * Classe représentant un profil (CodeProfil, LibelleProfil).
 * Un profil dans ASA Tarifs est l'association d'un rôle (pour les droits) et d'un axe (pour la visibilité)
 * Une information supplémentaire indique si le profil a accès ou non à ASA Vente
 * Copyright:    Copyright (c) 2001
 * Company:      Valtech
 * @author David Dreistadt
 * @version 1.0
 *
 * 09/07/01 : Ajout de la liste des axes de visibilite sous Vente
 */
@SuppressWarnings("serial")
public class Profil extends Element {

	public static final int NO_CONSTRAINT_ERROR					= 0;
	public static final int USER_CONSTRAINT_ERROR				= -1;
	public static final int DUPLICATE_PROFIL_CONSTRAINT_ERROR	= -2;
	public static final int NO_ACCES_DEFINE_ERROR				= -3;

	private Role roleTarif;
	private Role roleVente;
	private List<AxeVisibilite> axesTarif;
	private List<AxeVisibilite> axesVente;
	private boolean tarifLight;
	private boolean deleted;

	public Profil() {
	}

	public Profil(String code, String libelle, Role role) {
		super(code, libelle);
		setRole(role);
	}

	public Profil(String code, String libelle, Role role, List<AxeVisibilite> axesTarif ) {
		super(code, libelle);
		setRole(role);
		this.axesTarif = axesTarif;
	}

	public Profil(String code, String libelle, Role roleTarif, Role roleVente) {
		super(code, libelle);
		setRole(roleTarif);
		setRoleVente(roleVente);
		axesVente = null;
	}

	public Profil(String code, String libelle, Role roleTarif, List<AxeVisibilite> axesTarif, 
			Role roleVente, List<AxeVisibilite> axesVente) {
		super(code, libelle);
		setRole(roleTarif);
		this.axesTarif = axesTarif;
		setRoleVente(roleVente);
		this.axesVente = axesVente;
	}

	/***
	 * Methode de verification de l'absence de l objet a inserer dans la liste
	 * @param listItems
	 * @return
	 */
	public boolean notExist( final List<Profil> profils ) {

		if( profils == null )
			return true;

		Profil o;
		
		for( int i=0, size=profils.size(); i<size; i++ ) {
			o = profils.get( i );
			if( StringUtils.equalsIgnoreCase( ( (Profil) o ).getLibelle(), libelle ) )
				return false;
		}
		return true;
	}

	/***
	 * Methode de verification de l'unicite de l'objet dans la liste
	 * @param listItems
	 * @return
	 */
	public boolean isUnique( final List<Profil> profils ) {

		if( profils == null )
			return true;

		Profil o;
		
		for( int i=0, size=profils.size(); i<size; i++ ) {
			o = profils.get( i );
			if( StringUtils.equalsIgnoreCase( ( (Profil) o ).getLibelle(), libelle )
				&& ( ! StringUtils.equalsIgnoreCase( ( (Profil) o ).getCode(), code ) ) )
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
	 * @throws IncoherenceException
	 */
	public static String getErrorMessage( final int codeError, final String codeProfil, final Contexte contexte )
			throws TechnicalException, IncoherenceException {

		StringBuffer message = new StringBuffer();
		Translator trans = TranslatorFactory.getTranslator( contexte.getCodeLangue(), true );
		HabilitationFacade hFacade = PoolCommunFactory.getInstance().getHabilitationFacade();
		int maxUserInError = Integer.parseInt( FilesPropertiesCache.getInstance().getValue(
				Proprietes.PROPERTIES_FILE_NAME, Proprietes.NB_MAX_ERRORS_IN_USERS ) );

		switch( codeError ) {

			case USER_CONSTRAINT_ERROR :
				message.append( trans.getLibelle( "ADMIN_USERS_PROFILS_USER_ERROR" ) );
				List<UserLight> users = hFacade.getUsersForProfile( codeProfil, contexte );
				if( users != null ) {
					UserLight user;

					boolean moreUsers = false;
					int size = users.size();
					if( size > maxUserInError ) {
						size = maxUserInError;
						moreUsers = true;
					}

					for( int i=0; i<size; i++ ) {
						user = users.get(i);
							message.append( "\\n\\t" ).append( user.getCivility() )
									.append( " " ).append( user.getFirstName() )
									.append( " " ).append( user.getLastName() )
									.append( " (" ).append( user.getLogin() ).append( ")" );
					}

					if( moreUsers )
						message.append( "\\n\\t...." );
				}
				break;

			case DUPLICATE_PROFIL_CONSTRAINT_ERROR :
				message.append( trans.getLibelle( "ADMIN_USERS_PROFILS_DUPLICATE_PROFIL" ) );
				break;

			case NO_ACCES_DEFINE_ERROR :
				message.append( trans.getLibelle( "ADMIN_USERS_PROFILS_NO_ACCESS_DEFINE_ERROR" ) );
				break;
		}
		return message.toString();
	}

	public Role getRole() {
		return roleTarif;
	}

	public void setRole(Role unRole) {
		roleTarif = unRole;
	}

	public Role getRoleVente() {
		return roleVente;
	}

	public void setRoleVente(Role role)	{
		roleVente = role;
	}

	public List<AxeVisibilite> getAxesVente() {
		return axesVente;
	}

	public void setAxesVente(List<AxeVisibilite> liste) {
		axesVente = liste;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( super.toString() );
		sb.append("[roleTarif=").append(roleTarif).append("],");
		sb.append("[roleVente=").append(roleVente).append("],");
		sb.append("[tarifLight=").append(tarifLight).append("],");
		sb.append("[deleted=").append(deleted).append("],");
		sb.append("[axesTarif=");
		if( axesTarif != null ) {
			for( int i=0, size=axesTarif.size(); i<size; i++ ) {
				sb.append( axesTarif.get(i) ).append( "," );
			}
		}
		sb.append("],");
		sb.append("[axesVente=");
		if( axesVente != null ) {
			for( int i=0, size=axesVente.size(); i<size; i++ ) {
				sb.append( axesVente.get(i) ).append( "," );
			}
		}
		sb.append("],");
		return sb.toString();
	}

	public List<AxeVisibilite> getAxesTarif() {
		return axesTarif;
	}

	public void setAxesTarif(List<AxeVisibilite> axesTarif) {
		this.axesTarif = axesTarif;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean haveSaleAccess() {
		return ( roleVente != null && axesVente != null && axesVente.size() > 0 );
	}

	public boolean haveOtherAccess() {
		return ( roleTarif != null && axesTarif != null && axesTarif.size() > 0 );
	}

	public boolean isTarifLight() {
		return tarifLight;
	}

	public void setTarifLight(boolean tarifLight) {
		this.tarifLight = tarifLight;
	}
}