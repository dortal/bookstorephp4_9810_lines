package com.accor.asa.commun.utiles;

/**
 * Classe utilitaire permettant le passage WLS6.1 a WLS8.1. Contient des méthodes statiques retournant une chaine ""
 * pour tout objet null passe. Cela permet d'eviter les fields initialises a null.
 */
public class StringUtilities {

    public static String valueOf(boolean b) {
        return String.valueOf(b);
    }

    public static String valueOf(char c) {
        return String.valueOf(c);
    }

    public static String valueOf(double d) {
        return String.valueOf(d);
    }

    public static String valueOf(float f) {
        return String.valueOf(f);
    }

    public static String valueOf(int i) {
        return String.valueOf(i);
    }

    public static String valueOf(long l) {
        return String.valueOf(l);
    }

    public static String valueOf(Object o) {
        return (o == null) ? "" : o.toString();
    }

    public static String valueOf(char[] c) {
        return (c == null) ? "" : c.toString();
    }
}
