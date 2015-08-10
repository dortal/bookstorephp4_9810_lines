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
public interface TranslatorTagImage extends Translator {
	/**
	 * Methode utilitaire simplifiant l'inclusion de tags <img...> dans les JSP
	 * Elle fournit le même service que Translator.getLibelle(String cle) mais permet
	 * dans la JSP de faire la difference entre un libelle internationalisable et une image
	 * internationalisable
	 * Dans ce cas, tout le tag <img src=""...> est entièrement remplacé par une expression JSP
	 * <%=unTranslator.getImage()%>; la valeur du tag est contenue dans les fichiers de propriétés
	 * ex : image.logo=<img src="../pics/gb/accueil/logo.gif" width="176" height="80" alt="" border="0">
	 */
	public String getImage(String cleTagImage);

}