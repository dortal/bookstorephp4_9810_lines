package com.accor.commun.internationalisation;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Cette interface définit les services minimums
 * rendus par un composant d'internationalisation l'implementant
 * 
 * @version 1.0
 * @see TranslatorFactory
 * @see BasicTranslator
 * @author SSA
 */
public interface Translator {
	// API
	/**
	 * Retourne la chaine correspondant a la cle donnee en parametre
	 * pour le ResourceBundle de la Locale courante
	 * @param String la cle a rechercher dans le PropertyResourceBundle de la Locale courante
	 * @return String le libelle traduit dans la langue de la Locale courante
	 */ 
	public String getLibelle(String cle);

	
	/**
	 * @return Locale
	 */ 
	public Locale getLocale();	

	/**
	 * @return ResourceBundle
	 */ 
	public ResourceBundle getBundle();

}