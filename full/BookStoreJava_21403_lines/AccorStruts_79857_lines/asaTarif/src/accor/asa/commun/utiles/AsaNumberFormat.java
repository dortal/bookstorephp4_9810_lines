package com.accor.asa.commun.utiles;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 12 avr. 2006
 * Time: 08:48:23
 * Cette classe sert à centraliser le formatage des données numérique
 * sur les ecrans de l'application.
 */
public class AsaNumberFormat {
    /**
     * Affichage d'une valeur numerique (double) avec un separateur des milliers
     * et 2 chiffres aprés le le point décimal.
     * Si la valeur est null ou invalid on renvoie 0
     * @param valeur
     * @return
     */
    public static String formatNumber(double valeur) {
        String valeurFormate = "0";
        try {
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);
            valeur = Math.round(valeur*100)/100f;
            valeurFormate =  nf.format(valeur).replace(',','.');
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valeurFormate;
    }

    /**
     * Affichage d'une valeur numerique (double) avec  2 chiffres aprés le point décimal.
     * Si la valeur egale à 0 ou null ou invalid on renvoie chaine vide
     * Utilisé pour l'appli Tarif Ibis
     * @param valeur
     * @return
     */
    public static String formatSimpleNumber(double valeur) {
        String valeurFormate;
        try {
            if (valeur != 0.0) {
                NumberFormat nf = new DecimalFormat("#.00");
                valeurFormate =  nf.format(valeur).replace(',','.');
            } else {
                valeurFormate = "";
            }
        } catch (Exception e) {
            valeurFormate = "";
        }
        return valeurFormate;
    }

    /**
     * Methode de test.
     * @param args
    public static void main(String[] args) {
        float x;
        x= Float.parseFloat("21"); System.out.println(formatSimpleNumber(x));
        x= Float.parseFloat("654.65546"); System.out.println(formatSimpleNumber(x));
        x= Float.parseFloat("6546.56546"); System.out.println(formatSimpleNumber(x));
        x= Float.parseFloat("56654546546.2"); System.out.println(formatSimpleNumber(x));
        double xx;
        xx= 187878787.546; System.out.println(formatSimpleNumber(xx));
        xx= 187878787.56546; System.out.println(formatSimpleNumber(xx));
        xx= 187878787.1; System.out.println(formatSimpleNumber(xx));
        xx= 999999999999.66; System.out.println(formatSimpleNumber(xx));
    }
    */

}
