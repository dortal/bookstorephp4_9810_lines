package com.accor.commun.internationalisation;

/**
 * Cette interface définit les services minimums
 * rendus par un composant d'internationalisation l'implementant
 * 
 * @version 1.0
 * @see TranslatorFactory
 * @see BasicTranslator
 * @see Tranlator
 * @author SSA
 */
public interface TranslatorImagePath extends Translator {
	/**
	 * Methode utilitaire simplifiant les expressions dans les JSP
	 * Utile si les repertoires des images specifiques a chaque langue contiennent
	 * fr, us, gb
	 * @return String fr, de, gb
	 */
	public String getImagePath();

}