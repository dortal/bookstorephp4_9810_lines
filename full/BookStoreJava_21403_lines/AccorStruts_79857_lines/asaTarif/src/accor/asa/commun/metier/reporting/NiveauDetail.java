package com.accor.asa.commun.metier.reporting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

@SuppressWarnings("serial")
public class NiveauDetail implements Serializable{
    public static final int CODE_DETAILLE         = 0;
    public static final int CODE_SEMI_DETAILLE    = 1;
    public static final int CODE_SYNTHETIQUE      = 2;

    public static NiveauDetail DETAILLE = new NiveauDetail (CODE_DETAILLE, "VENTE_REPORTING_NIVEAU_DETAILLE");
    public static NiveauDetail SEMI_DETAILLE = new NiveauDetail (CODE_SEMI_DETAILLE, "VENTE_REPORTING_NIVEAU_SEMI_DETAILLE");
    public static NiveauDetail SYNTHETIQUE = new NiveauDetail (CODE_SYNTHETIQUE, "VENTE_REPORTING_NIVEAU_SYNTHETIQUE");

    private String label;
    private int code;

    private NiveauDetail(int code, String label) {
        this.code = code;
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String newLabel) {
        label = newLabel;
    }
    public void setCode(int newCode) {
        code = newCode;
    }
    public int getCode() {
        return code;
    }

    public static List<NiveauDetail> getNiveauxDetails() {
    	List<NiveauDetail> niveaux = new ArrayList<NiveauDetail>();
    	niveaux.add( DETAILLE );
    	niveaux.add( SEMI_DETAILLE );
    	niveaux.add( SYNTHETIQUE );
    	return niveaux;
    }
    
    public static NiveauDetail getNiveauDetailFromCode (int code) {
        switch (code) {
            case CODE_SEMI_DETAILLE :
                return SEMI_DETAILLE;
            case CODE_SYNTHETIQUE :
                return SYNTHETIQUE;
            default :
                return DETAILLE;
        }
    }

    public String toString() {
        return "codeDetail:" + code + ", labelDetail:" + label;
    }
}