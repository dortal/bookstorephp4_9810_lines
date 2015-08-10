package com.accor.commun.internationalisation;

import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Implementation de base de l'interface com.accor.accorhotels.i18n.Translator
 * construite a partir d'un java.util.PropertyResourceBundle cree par defaut
 * a partir des fichiers message.properties et variantes (message_fr.properties,...)
 * @version 1.0
 * @see TranslatorFactory
 * @see Translator
 * @author SSA
 */
public class BasicTranslator implements TranslatorImagePath, TranslatorTagImage {
	/*
	 * INSTANCE ATTRIBUTES SECTION
	 */
	private Locale 			theLocale	= null;
	private ResourceBundle	bundle		= null;
	private String			bundleName	= DEFAULT_BUNDLE_NAME;
	private static final String  DEFAULT_BUNDLE_NAME = "i18n";

	/*
	 * API SECTION
	 */

	/**
	 * Retourne la chaine correspondant a la cle donnee en parametre
	 * pour le ResourceBundle de la Locale courante
	 * @param String la cle a rechercher dans le ResourceBundle de la Locale courante
	 * @return String le libelle traduit dans la langue de la Locale courante
	 */
	public String getLibelle(String cle){
		return bundle.getString(cle);
	}

	/**
	 * Methode utilitaire simplifiant les expressions dans les JSP
	 * Utile si les repertoires des images specifiques a chaque langue contiennent
	 * fr, us, gb
	 * @return String fr, de, gb
	 */
	// modifié le 14/11/2000 par SSA
	public String getImagePath() {
		// bidouille car le repertoire des images en anglais est 'gb' et non 'en'
		if ("en".equals(theLocale.getLanguage()))
			return "gb";
		return theLocale.getLanguage();
	}

	/**
	 * Methode utilitaire simplifiant l'inclusion de tags <img...> dans les JSP
	 * Dans ce cas, tout le tag <img src=""...> est entièrement remplacé par une expression JSP
	 * <%=unTranslator.getImage()%>; la valeur du tag est contenue dans les fichiers de propriétés
	 * ex : image.logo=<img src="../pics/gb/accueil/logo.gif" width="176" height="80" alt="" border="0">
	 */
	public String getImage(String cleTagImage) {
		return getLibelle(cleTagImage);
	}

	/**
	 * @return Locale
	 */
	public Locale getLocale() {
		return  theLocale;
	}

	/**
	 * @return ResourceBundle
	 */
	public ResourceBundle getBundle() {
		return bundle;
	}

	/*
	 * CONSTRUCTORS SECTION
	 *
	 * Visibilite limitee au package car un Translator doit uniquement
	 * etre cree par un TranslatorFactory
	 */

	BasicTranslator(String langue) {
		setLocale(langue);
		bundle = ResourceBundle.getBundle(getBundleName(), theLocale);
	}

	BasicTranslator(String langue, String bundleName) {
		setLocale(langue);
		bundle = ResourceBundle.getBundle(bundleName, theLocale);
	}

	/*
	 * PRIVATE SECTION
	 */
	private String getBundleName() {
		return bundleName;
	}

	// Modifiée le 13/11/2000
	/*
	 * 1. inversion des tests 'equals'
	 * 2. modification des equals en equalsIgnoreCase car la langue des Locale Java est en minuscule
	 * 3. modification de Locale.FRANCE->Locale.FRENCH / Locale.GERMANY->Locale.GERMAN : homogène avec Locale.ENGLISH !
	 * La TranslatorFactory a préalablement vérifiée que la langue à partir de laquelle
	 * le ResourceBundle doit être créée est bien prise en compte par le système - cf. config.properties
	 */
	// modifié le 14/11/2000 par SSA
	private void setLocale(String langue) {
		// ATTENTION, ceci ne fonctionne que pour les locales dont le 'country' est égal à ""
		// on pourrait aussi récupérer la Locale voulue dans Locale.getAvalaibleLocales()
		theLocale = new Locale(langue, "");
	}

	/* modifié le 13/11/2000 par SSA
	 * tests dans inner classe => permet de ne livrer que le .class expurgé du code de test
	 */
	static class BasicTranslatorTest {
		/*
		* MAIN - Pour tests unitaires
		* Usage :
		* java com.accor.resa.i18n.BasicTranslator [<langue> <libelle> <resourceBundle>]
		* <langue> = FR, DE, US, ...
		* <libelle> = resaexpress1, ...
		* <resourceBundle> = message (par défaut), resa_express, ...
		*/
		public static void main(String[] args) {
			TranslatorImagePath t = null;
			try {
				if (args.length == 0) { // pas de langue
					t = new BasicTranslator(null);
					for (Enumeration<String> e = t.getBundle().getKeys();e.hasMoreElements();) {
						String cle = e.nextElement();
						String valeur = t.getLibelle(cle);
						System.out.println(cle + " => " + valeur);
					}
				} else if (args.length == 1) { // langue
					t = new BasicTranslator(args[0]);
					System.out.println("Langue par defaut : " + Locale.getDefault().getLanguage());
					for (Enumeration<String> e = t.getBundle().getKeys();e.hasMoreElements();) {
						String cle = e.nextElement();
						String valeur = t.getLibelle(cle);
						System.out.println(cle + " => " + valeur);
					}
					System.out.println("Sous chemin des images : " + t.getImagePath());
				} else if (args.length == 2) { // langue + libellé
					t = new BasicTranslator(args[0]);
					System.out.println("Traduction du libelle <" + args[1] + "> => " + t.getLibelle(args[1]));
				} else if (args.length == 3) { // langue + libellé + nom du ressource bundle
					t = new BasicTranslator(args[0], args[2]);
					System.out.println("Traduction du libelle <" + args[1] + "> => " + t.getLibelle(args[1]));
				}
			} catch(MissingResourceException ex) {
				ex.printStackTrace();
				System.out.println("Ressource <" + ex.getKey() + "> inexistante");
			}
		}
	}
}