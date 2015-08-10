/*
 * put your module comment here
 * formatted with JxBeauty (c) johann.langhofer@nextra.at
 */


package  com.accor.asa.commun.hotel.metier;

import java.util.ArrayList;
import java.util.List;

import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.process.IncoherenceException;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @pattern enum
 * @version 1.0
 */

/**
 * Classe enum fournissant les critères de sélection de la Big Picture Hotel
 */
public class CriteresBigPictureHotel {
    private String label;
    private int code;
    public static final int _INFO_GEN_HOTEL = 128;
    public static final int _CENTRES_INTERETS = 64;
    public static final int _LISTE_CONTACTS = 32;
    public static final int _DESCRIP_GEN = 16;
    public static final int _LISTE_PRODUITS = 8;
    public static final int _REST_BARS_SALLES = 4;
    public static final int _INF_BANCAIRES = 2;
    public static final int _LISTE_ACCES = 1;
    public static final CriteresBigPictureHotel INFO_GEN_HOTEL = new CriteresBigPictureHotel(_INFO_GEN_HOTEL,
            "VENTE_HOTEL_BP_INFO_GEN_HOTEL");
    public static final CriteresBigPictureHotel CENTRES_INTERETS = new CriteresBigPictureHotel(_CENTRES_INTERETS,
            "VENTE_HOTEL_BP_CENTRES_INTERETS");
    public static final CriteresBigPictureHotel LISTE_CONTACTS = new CriteresBigPictureHotel(_LISTE_CONTACTS,
            "VENTE_HOTEL_BP_LISTE_CONTACTS");
    public static final CriteresBigPictureHotel DESCRIP_GEN = new CriteresBigPictureHotel(_DESCRIP_GEN,
            "VENTE_HOTEL_BP_DESCRIP_GEN");
    public static final CriteresBigPictureHotel LISTE_PRODUITS = new CriteresBigPictureHotel(_LISTE_PRODUITS,
            "VENTE_HOTEL_BP_LISTE_PRODUITS");
    public static final CriteresBigPictureHotel REST_BARS_SALLES = new CriteresBigPictureHotel(_REST_BARS_SALLES,
            "VENTE_HOTEL_BP_REST_BARS_SALLES");
    public static final CriteresBigPictureHotel INF_BANCAIRES = new CriteresBigPictureHotel(_INF_BANCAIRES,
            "VENTE_HOTEL_BP_INF_BANCAIRES");
    public static final CriteresBigPictureHotel LISTE_ACCES = new CriteresBigPictureHotel(_LISTE_ACCES,
            "VENTE_HOTEL_BP_LISTE_ACCES");

    /**
     * Constructeur privé de la classe
     * @param     int code
     * @param     String label
     */
    private CriteresBigPictureHotel (int code, String label) {
        this.code = code;
        this.label = label;
    }

    /**
     * Accesseur du code
     * @return int
     */
    public int getCode () {
        return  code;
    }

    /**
     * Accesseur du label
     * @return String
     */
    public String getLabel () {
        return  label;
    }

    /**
     * méthode retournant la liste de tous les critères
     * @return List de CriteresBigPictureHotel
     */
    public static List<CriteresBigPictureHotel> getAllCriteres () {
        List<CriteresBigPictureHotel> listeRetour = new ArrayList<CriteresBigPictureHotel>();
        listeRetour.add(INFO_GEN_HOTEL);
        listeRetour.add(CENTRES_INTERETS);
        listeRetour.add(LISTE_CONTACTS);
        listeRetour.add(DESCRIP_GEN);
        listeRetour.add(LISTE_PRODUITS);
        listeRetour.add(REST_BARS_SALLES);
        listeRetour.add(INF_BANCAIRES);
        listeRetour.add(LISTE_ACCES);
        return  listeRetour;
    }

    /**
     * Méthode permettant d'avoir une référence sur un objet critère à partir d'un code
     * @param code
     * @return CriteresBigPictureHotel
     * @exception IncoherenceException si aucun objet ne correspond au code
     */
    public static CriteresBigPictureHotel getInstance (int code) throws IncoherenceException {
        CriteresBigPictureHotel bpRetour = null;
        switch (code) {
            case _CENTRES_INTERETS:
                bpRetour = CENTRES_INTERETS;
                break;
            case _DESCRIP_GEN:
                bpRetour = DESCRIP_GEN;
                break;
            case _INF_BANCAIRES:
                bpRetour = INF_BANCAIRES;
                break;
            case _INFO_GEN_HOTEL:
                bpRetour = INFO_GEN_HOTEL;
                break;
            case _LISTE_ACCES:
                bpRetour = LISTE_ACCES;
                break;
            case _LISTE_CONTACTS:
                bpRetour = LISTE_CONTACTS;
                break;
            case _LISTE_PRODUITS:
                bpRetour = LISTE_PRODUITS;
                break;
            case _REST_BARS_SALLES:
                bpRetour = REST_BARS_SALLES;
                break;
            default:
                String message = "Aucun CriteresBigPictureHotel correspondant à ce code : "
                        + code;
                IncoherenceException exception = new IncoherenceException(message);
                LogCommun.major("Get Instance CriteresBigPictureHotel ", "CriteresBigPictureHotel", "getInstance()", message,
                        exception);
                throw  exception;
        }
        return  bpRetour;
    }
}



