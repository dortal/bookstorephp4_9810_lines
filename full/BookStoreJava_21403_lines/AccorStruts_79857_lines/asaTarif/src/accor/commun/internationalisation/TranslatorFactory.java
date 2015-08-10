package com.accor.commun.internationalisation;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;

/**
 * Construit des Translator pour une langue donnee
 * les conserve dans une java.util.Hashtable pour reference future
 * @version 1.0
 * @see Translator
 * @see BasicTranslator
 * @author SSA
 */
public class TranslatorFactory {

    public static String LIBELLE_NON_PRESENT ="Libelle non present : ";

    private static Hashtable<String, Translator> translators = new Hashtable<String, Translator>();

	/**
	 * Methode factory retournant un com.accor.resa.i18n.Translator initialise
	 * avec un java.util.PropertyResourceBundle, lui meme cree en fonction de
	 * la langue passee en parametre.
	 *
	 * @param String langue : en, de, fr
	 * @return com.accor.resa.i18n.Translator ou null si le Translator pour
	 * la langue choisie n'a pas pu etre cree (absence de ResourceBundle par exemple)
	 * si param = null on cree un Translator par defaut en anglais
	 * @exception java.util.MissingResourceException - Runtime exception
	 * si le ResourceBundle pour la langue specifiee ne peut pas etre cree
	 */
	public static synchronized Translator getTranslator(String langue) {
		Translator myTranslator = null;
		if ((myTranslator = lookup(langue)) !=null) {
			return myTranslator;
		}

		myTranslator = new DBBasicTranslator(langue);
		translators.put(langue, myTranslator);
		return myTranslator;
	}

	/**
	 * Permet de récupérer un objet de traduction dans la langue demandée
	 * useDB n'est pas utilisé car l'application ASAV2 n'utilise pas de fichier Locale
	 * mais va toujours chercher les libellés dans la base de données
	 */
	public static synchronized Translator getTranslator(String langue, boolean useDB)
	{
		return getTranslator(langue);
	}

	/**
	 * Cette méthode permet de forcer le rechargement de toutes les ressources.
	 * Ceci permet de mettre à jour les données sans redémarrer le serveur d'application.
	 */
	public static synchronized void reloadRessources()
	{
		translators.clear();
/*
		ResourceBundle bundle = ResourceBundle.getBundle("com.accor.commun.internationalisation.DBResourceBundle");

		try {
			System.out.println("DBBasicTranslator0 bundle " + bundle);
			Class klass = bundle.getClass().getSuperclass().getSuperclass();
			System.out.println("DBBasicTranslator1 klass " + klass.getName());
			java.lang.reflect.Field field = klass.getDeclaredField ("cacheList");
			System.out.println("DBBasicTranslator3 field " + field);
			field.setAccessible (true);
			System.out.println("DBBasicTranslator32 field " + field);
			sun.misc.SoftCache cache = (sun.misc.SoftCache) field.get(null);
			System.out.println("DBBasicTranslator4 cache " + cache);
			cache.clear();    // Vide tous les caches de ResourceBundle dans la JVM
			System.out.println("DBBasicTranslator5 cache " + cache);

		} catch(java.lang.NoSuchFieldException e) {
			System.out.println("DBBasicTranslator6 " + e.getMessage());
			e.printStackTrace();
		} catch(java.lang.IllegalAccessException e) {
			System.out.println("DBBasicTranslator7 " + e.getMessage());
			e.printStackTrace();
		}

		System.out.println("DBBasicTranslator8 " + bundle);
*/

		DBResourceBundle.clearBundles();

	}

	/**
	 * Cette méthode permet de forcer le rechargement de toutes la ressource de la langue passée en paramètre.
	 * Ceci permet de mettre à jour les données sans redémarrer le serveur d'application.
	 */
	public static synchronized void reloadRessources( String langue)
	{
		translators.remove(langue);
		DBResourceBundle.clearABundles(langue);

	}


	private static Translator lookup(String langue) {
		if (langue == null)
			langue = Locale.ENGLISH.getLanguage(); // si aucune langue n'est spécifiée, on utilise l'anglais par défaut
		return (Translator)translators.get(langue);
	}

	/* modifié le 13/11/2000
	 * tests dans inner classe => permet de ne livrer que le .class expurgé du code de test
	 */
	public static class TranslatorFactoryTest {
		/*
		* Usage
		* java com.accor.resa.i18n.TranslatorFactory <langue> [<libelle>]
		* <langue> = FR, DE, US, ALL=>affiche tous les translators disponibles
		* <libelle> = resaexpress1, ...
		*/
		public static void main(String[] args) {
			Translator t = null;
			if ("ALL".equalsIgnoreCase(args[0])) {
				TranslatorFactory.getTranslator(null);
				TranslatorFactory.getTranslator("EN");
				TranslatorFactory.getTranslator("FR");
				TranslatorFactory.getTranslator("FR");
				TranslatorFactory.getTranslator(null);
				TranslatorFactory.getTranslator("EN");
				TranslatorFactory.getTranslator("EN");
				TranslatorFactory.getTranslator("EN");
				TranslatorFactory.getTranslator("EN");
				TranslatorFactory.getTranslator("DE");
				TranslatorFactory.getTranslator("FR");
				TranslatorFactory.getTranslator("DE");
				listTranslators();
			}
			else if (args.length == 0) {
				t = TranslatorFactory.getTranslator(null);
				for (Enumeration<String> e = t.getBundle().getKeys();e.hasMoreElements();) {
					String cle = e.nextElement();
					String valeur = t.getLibelle(cle);
					System.out.println(cle + " => " + valeur);
				}
			} else if (args.length == 1) {
				t = TranslatorFactory.getTranslator(args[0], true);
				//System.out.println("Translator pour langue : " + t.getLocale().getLanguage());
				// for (java.util.Enumeration e = t.getBundle().getKeys();e.hasMoreElements();) {
				// String cle = (String)e.nextElement();
					String valeur = t.getLibelle("RATES_TAXES&SERVICES_ALLINCLUDEINHOTEL");
					System.out.println(" => " + valeur);
				// }
			} else {
				t = TranslatorFactory.getTranslator(args[0]);
				String libelle = t.getLibelle(args[1]);
				System.out.println("1. Valeur de <" + args[1] + "> en <" + args[0] + "> = " + libelle);
				t = TranslatorFactory.getTranslator(args[0]);
				libelle = t.getLibelle(args[1]);
				System.out.println("2. Valeur de <" + args[1] + "> en <" + args[0] + "> = " + libelle);
			}
		}
	}

	/*
	 * Pour test uniquement
	 */
	public static void listTranslators() {
		Enumeration<String> enumeration = translators.keys();
		while (enumeration.hasMoreElements())
			System.out.println(enumeration.nextElement());
	}
}