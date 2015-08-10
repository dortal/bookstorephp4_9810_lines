package com.accor.asa.commun.metier.periodegenerique;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 4 mai 2006
 * Time: 11:04:58
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class PeriodeGenerique implements Serializable {

    protected int code;
    protected String  type;
    protected String  nom;
    protected String  libelleVente;
    protected int nbMaxSaisons;
    protected int nbMaxPeriodes;
    protected int codeGroupeTarif;


    /**
     * Constructeur
     */
    public PeriodeGenerique() {
    }

    /**
     * Constructeur
     * @param code
     * @param type
     * @param nom
     * @param libelleVente
     * @param nbMaxSaisons
     * @param nbMaxPeriodes
     * @param codeGroupeTarif
     */
    public PeriodeGenerique(int code, String type, String nom, String libelleVente, int nbMaxSaisons, int nbMaxPeriodes, int codeGroupeTarif) {
        this.code = code;
        this.type = type;
        this.nom = nom;
        this.libelleVente = libelleVente;
        this.nbMaxSaisons = nbMaxSaisons;
        this.nbMaxPeriodes = nbMaxPeriodes;
        this.codeGroupeTarif = codeGroupeTarif;
    }


    // ================ GETTER AND SETTER ======================

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLibelleVente() {
        return libelleVente;
    }

    public void setLibelleVente(String libelleVente) {
        this.libelleVente = libelleVente;
    }

    public int getNbMaxSaisons() {
        return nbMaxSaisons;
    }

    public void setNbMaxSaisons(int nbMaxSaisons) {
        this.nbMaxSaisons = nbMaxSaisons;
    }

    public int getNbMaxPeriodes() {
        return nbMaxPeriodes;
    }

    public void setNbMaxPeriodes(int nbMaxPeriodes) {
        this.nbMaxPeriodes = nbMaxPeriodes;
    }

    public int getCodeGroupeTarif() {
        return codeGroupeTarif;
    }

    public void setCodeGroupeTarif(int codeGroupeTarif) {
        this.codeGroupeTarif = codeGroupeTarif;
    }


    /**
     * To string
     * @return  String
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[code=").append(code).append("]  ");
        sb.append("[type=").append(type).append("]  ");
        sb.append("[nom=").append(nom).append("]  ");
        sb.append("[libelleVente=").append(libelleVente).append("]  ");
        sb.append("[nbMaxSaisons=").append(nbMaxSaisons).append("]  ");
        sb.append("[nbMaxPeriodes=").append(nbMaxPeriodes).append("]  ");
        sb.append("[codeGroupeTarif=").append(codeGroupeTarif).append("]  ");
        return sb.toString();
    }

}
