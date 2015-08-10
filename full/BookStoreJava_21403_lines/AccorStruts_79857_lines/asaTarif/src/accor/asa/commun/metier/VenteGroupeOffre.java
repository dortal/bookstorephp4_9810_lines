package com.accor.asa.commun.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.accor.asa.commun.process.IncoherenceException;

/**
 * Classe enum répertoriant les divers groupes d'offres possible dans ASA
 * Utilisée par AsaAdmin et AsaVente
 */
@SuppressWarnings("serial")
public class VenteGroupeOffre implements java.io.Serializable {

    /**
     * groupes d'offres par code groupe offre
     * cle : Integer
     * value : VenteGroupeOffre
     */
    private static Map<Integer, VenteGroupeOffre> m_codeMap = new HashMap<Integer, VenteGroupeOffre>();
    
    /**
     * groupes d'offres par libellé
     * cle : String
     * value : VenteGroupeOffre
     */
    private static Map<String, VenteGroupeOffre> m_labelMap = new HashMap<String, VenteGroupeOffre>();

	/* différents codes groupe offres
	 	utilisé pour référencer les groupes en Java
		En base : on utilise les libelles et parfois le code (et c'est pas joli joli...)
	*/ 

    /** offres annuelles société */
    public static final int CODEGROUPEOFFRE_ANNUELLE_SOCIETE = 1;
    /** offres annuelles loisir */
    public static final int CODEGROUPEOFFRE_ANNUELLE_LOISIR = 7;

    /** offres ponctuelles société */
    public static final int CODEGROUPEOFFRE_PONCTUELLE_SOCIETE = 3;
    /** offres ponctuelles loisir */
    public static final int CODEGROUPEOFFRE_PONCTUELLE_LOISIR = 8;

    /** offres loisir IT 
     * (agences de voyage, tour operator, autocaristes, etc...) 
     */
    public static final int CODEGROUPEOFFRE_ANNUELLE_IT = 4;

    /** offres packagées business */
    public static final int CODEGROUPEOFFRE_PACKAGEE_BTP = 2;
    /** offres packagées loisir 
     * (Go As You Please, sorte de carte prépayée) 
     */
    public static final int CODEGROUPEOFFRE_PACKAGEE_GAYP = 5;

    /** offres cartes (à détailler...) */
    public static final int CODEGROUPEOFFRE_CARTES = 6;

    private static final List<VenteGroupeOffre> s_groupesOffreAvecHotel = new ArrayList<VenteGroupeOffre>(3);

    /* (attention, même table que loisir!!) */
    public static final VenteGroupeOffre GROUPEOFFRE_ANNUELLE_SOCIETE =
        new VenteGroupeOffre( CODEGROUPEOFFRE_ANNUELLE_SOCIETE, "VENTE_GROUPE_OFFRE_DOSSIER_CONTRAT_HEBERGEMENT_SOCIETE", true, TableOffre.ANNUELLE, true);
    /* (attention, même table que business!!) */
    public static final VenteGroupeOffre GROUPEOFFRE_ANNUELLE_LOISIR =
        new VenteGroupeOffre( CODEGROUPEOFFRE_ANNUELLE_LOISIR, "VENTE_GROUPE_OFFRE_ANNUELLE_LOISIR", true, TableOffre.ANNUELLE, true);

    /* (attention, même table que loisir!!) */
    public static final VenteGroupeOffre GROUPEOFFRE_PONCTUELLE_SOCIETE =
        new VenteGroupeOffre(CODEGROUPEOFFRE_PONCTUELLE_SOCIETE, "VENTE_GROUPE_OFFRE_DOSSIER_CONTRAT_PONCTUEL", true, TableOffre.PONCTUELLE, true);
    /* (attention, même table que business!!) */
    public static final VenteGroupeOffre GROUPEOFFRE_PONCTUELLE_LOISIR =
        new VenteGroupeOffre(CODEGROUPEOFFRE_PONCTUELLE_LOISIR, "VENTE_GROUPE_OFFRE_PONCTUELLE_LOISIR", true, TableOffre.PONCTUELLE, true);

    public static final VenteGroupeOffre GROUPEOFFRE_ANNUELLE_IT =
        new VenteGroupeOffre(CODEGROUPEOFFRE_ANNUELLE_IT, "VENTE_GROUPE_OFFRE_DOSSIER_CONTRAT_IT", true, TableOffre.ANNUELLE, true);

    public static final VenteGroupeOffre GROUPEOFFRE_PACKAGEE_BTP =
        new VenteGroupeOffre(CODEGROUPEOFFRE_PACKAGEE_BTP, "VENTE_GROUPE_OFFRE_DOSSIER_CONTRAT_BTP", TableOffre.BTP, false);
    public static final VenteGroupeOffre GROUPEOFFRE_PACKAGEE_GAYP =
        new VenteGroupeOffre(CODEGROUPEOFFRE_PACKAGEE_GAYP, "VENTE_GROUPE_OFFRE_DOSSIER_CONTRAT_GAYP", TableOffre.GAYP, false );

    public static final VenteGroupeOffre GROUPEOFFRE_CARTES =
        new VenteGroupeOffre(CODEGROUPEOFFRE_CARTES, "VENTE_GROUPE_OFFRE_DOSSIER_CONTRAT_CARTES", TableOffre.CARTE, false);

    /* variables membres */
    /**
     * le code sous forme de string, utilisé comme clef en base (eh oui... génial, non?)
     */
    private String m_label;
    /**
     * le code entier également utilisé comme clef en base (mais moins souvent...)
     */
    private int m_code;
    
    private boolean m_avecHotel;

    private boolean hasCDC = false; // EVO 4297
    
    /**
     * référence du type de table où sont stockées les données (offre et hôtel)
     * voir table VENTE_GROUPE_OFFRE_TABLE
     */
    private TableOffre m_tableOffre;
    
    private VenteGroupeOffre(int code, String codeTexteEnBase, TableOffre tableOffre, boolean cdc) {
        this(code, codeTexteEnBase, false, tableOffre,cdc);
    }
    private VenteGroupeOffre(int code, String codeTexteEnBase, boolean estAvecHotel, TableOffre tableOffre, boolean cdc) {
        m_code = code;
        m_label = codeTexteEnBase;
        m_codeMap.put(new Integer(m_code), this);
        m_labelMap.put(m_label, this);
        if (estAvecHotel)
            s_groupesOffreAvecHotel.add(this);
        m_tableOffre = tableOffre;
        m_avecHotel = estAvecHotel;
        hasCDC = cdc;
    }
    
    public boolean isAvecHotel() {
        return m_avecHotel;
    }

    public int getCode() {
        return m_code;
    }

    public String getLabel() {
        return m_label;
    }
    
    
    public TableOffre getTableOffre() {
        return m_tableOffre;
    }

    public String toString() {
        return getLabel();
    }

    public boolean hasCDC() {
        return hasCDC;
    }
	/**
	 * @param unCode le code du groupe (corresponda au getCode() CODEGROUPEOFFRE_xxx)
	 * @return le groupe correspondant
	 */
    public static VenteGroupeOffre getInstance(int unCode) throws IncoherenceException {
        VenteGroupeOffre gp = (VenteGroupeOffre) m_codeMap.get(new Integer(unCode));
        if (gp == null)
            throw new IncoherenceException("Type de GroupeOffre de dossier inconnu / code : " + unCode);
        return gp;
    }

	/**
	 * @param unCode le code du groupe (corresponda au getCode() CODEGROUPEOFFRE_xxx)
	 * @return le groupe correspondant
	 */
    public static VenteGroupeOffre getInstance(Integer unCode) throws IncoherenceException {
        VenteGroupeOffre gp = (VenteGroupeOffre) m_codeMap.get( unCode );
        if (gp == null)
            throw new IncoherenceException("Type de GroupeOffre de dossier inconnu / code : " + unCode);
        return gp;
    }

    /**
	 * @param unLabel le libellé du groupe (correspond au getLabel())
	 * @return le groupe correspondant
	 */
    public static VenteGroupeOffre getInstance(String unLabel) throws IncoherenceException {
        VenteGroupeOffre gp = (VenteGroupeOffre) m_labelMap.get(unLabel);
        if (gp == null)
            throw new IncoherenceException("Type de GroupeOffre de dossier inconnu / label : " + unLabel);
        return gp;
    }

    /**
     * renvoie une liste d'objets VenteGroupeOffre correspondants aux groupes d'offre avec hotel
     */
    public static List<VenteGroupeOffre> getGroupesOffresAvecHotel() {
        return s_groupesOffreAvecHotel;
    }

	/**
	 * @return la liste de tous les VenteGroupeOffre répertoriés
	 */
    public static List<VenteGroupeOffre> getAll() {
        List<VenteGroupeOffre> l = new ArrayList<VenteGroupeOffre>();
        l.add(m_codeMap.get(new Integer(CODEGROUPEOFFRE_ANNUELLE_SOCIETE)));
		l.add(m_codeMap.get(new Integer(CODEGROUPEOFFRE_PACKAGEE_BTP)));
		l.add(m_codeMap.get(new Integer(CODEGROUPEOFFRE_PONCTUELLE_SOCIETE)));
		l.add(m_codeMap.get(new Integer(CODEGROUPEOFFRE_ANNUELLE_IT)));
		l.add(m_codeMap.get(new Integer(CODEGROUPEOFFRE_PONCTUELLE_LOISIR)));
		l.add(m_codeMap.get(new Integer(CODEGROUPEOFFRE_ANNUELLE_LOISIR)));
		l.add(m_codeMap.get(new Integer(CODEGROUPEOFFRE_PACKAGEE_GAYP)));
		l.add(m_codeMap.get(new Integer(CODEGROUPEOFFRE_CARTES)));
		return l;
    }

    public boolean isLoisir() {
    	return ( m_code == CODEGROUPEOFFRE_ANNUELLE_IT ||
    			m_code == CODEGROUPEOFFRE_PONCTUELLE_LOISIR ||
    			m_code == CODEGROUPEOFFRE_ANNUELLE_LOISIR ||
    			m_code == CODEGROUPEOFFRE_PACKAGEE_GAYP );
    }
    
    public boolean isRenouvelable() {
    	return ( m_code == CODEGROUPEOFFRE_ANNUELLE_IT 
    			 || m_code == CODEGROUPEOFFRE_ANNUELLE_LOISIR 
    			 || m_code == CODEGROUPEOFFRE_PACKAGEE_GAYP
    			 || m_code == CODEGROUPEOFFRE_PACKAGEE_BTP
    			 || m_code == CODEGROUPEOFFRE_ANNUELLE_SOCIETE );    	
    }
    
    public boolean isDuplicable() {
		return ( m_code == CODEGROUPEOFFRE_PONCTUELLE_LOISIR
				|| m_code == CODEGROUPEOFFRE_PONCTUELLE_SOCIETE );
    }
    
    public boolean equals( VenteGroupeOffre vo ) {
    	if( vo != null )
    		return vo.getCode() == m_code;
    	return false;
    }
}
