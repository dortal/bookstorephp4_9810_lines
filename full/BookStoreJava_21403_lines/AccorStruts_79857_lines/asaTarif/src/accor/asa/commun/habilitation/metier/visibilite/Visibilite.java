package com.accor.asa.commun.habilitation.metier.visibilite;

import java.io.Serializable;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.commun.internationalisation.Translator;
import com.accor.commun.internationalisation.TranslatorFactory;

/**
 * Classe représentant une valeur de visibilite
 * @author Jerome Pietri
 */

@SuppressWarnings("serial")
public class Visibilite implements Serializable {

	public static final int NO_CONSTRAINT_ERROR  		= 0;
	public static final int UNKNOWN_VISIBILITY_ERROR	= 1;
	
	public static final String ALL_VALUE_CODE  		= "_all";
	public static final String SELECTED_VALUE_CODE  = "_selected";
	public static final String NONE_VALUE_CODE  	= "_none";
	
	protected String login;
	protected String value;
	protected String codeAxe;
	protected String type;
	protected String label;
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "[login=" ).append( login ).append( "]," );
		sb.append( "[value=" ).append( value ).append( "]," );
		sb.append( "[codeAxe=" ).append( codeAxe ).append( "]," );
		sb.append( "[type=" ).append( type ).append( "]," );
		sb.append( "[label=" ).append( label ).append( "]," );
		return sb.toString();
	}

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

			case UNKNOWN_VISIBILITY_ERROR :
				message.append( trans.getLibelle( "ADMIN_USER_VISIBILITY_UNKNOWN_VISIBILITY_ERROR" ) );
				break;
		}
		
		return message.toString();
	}
	
	public String getCodeAxe() {
		return codeAxe;
	}

	public void setCodeAxe(String codeAxe) {
		this.codeAxe = codeAxe;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
