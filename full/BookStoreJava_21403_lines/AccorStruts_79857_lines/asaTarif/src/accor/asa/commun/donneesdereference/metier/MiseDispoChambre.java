package com.accor.asa.commun.donneesdereference.metier;

import javax.servlet.http.HttpServletRequest;

public class MiseDispoChambre extends DonneeReference {
    private boolean m_taux_materialisation;
    private boolean m_allotement;
    private boolean m_defaut;
    private boolean m_inactif;

    public static final String AVEC_ALLOTEMENT = "WA";

    public boolean getTauxMaterialisation() {
        return m_taux_materialisation;
    }

    public void setTauxMaterialisation(boolean taux_materialisation) {
        m_taux_materialisation = taux_materialisation;
    }

    public boolean getAllotement() {
        return m_allotement;
    }

    public void setAllotement(boolean allotement) {
        m_allotement = allotement;
    }

    public boolean getDefaut() {
        return m_defaut;
    }

    public void setDefaut(boolean defaut) {
        m_defaut = defaut;
    }

    public boolean getInactif() {
        return m_inactif;
    }

    public void setInactif(boolean inactif) {
        m_inactif = inactif;
    }

    public DonneeReference getInstance(HttpServletRequest req) {
        MiseDispoChambre result = new MiseDispoChambre();
        result.setCode(req.getParameter("code"));
        result.setLibelle(req.getParameter("libelle"));
        result.setTauxMaterialisation(getBooleanParameter(req, "taux_materialisation"));
        result.setAllotement(getBooleanParameter(req, "allotement"));
        result.setDefaut(getBooleanParameter(req, "defaut"));
        result.setInactif(getBooleanParameter(req, "inactif"));
        return result;
    }

    private boolean getBooleanParameter(HttpServletRequest req, String parameter) {
        if (req.getParameter(parameter) == null) {
            return false;
        }
        else {
            return true;
        }
    }
}
