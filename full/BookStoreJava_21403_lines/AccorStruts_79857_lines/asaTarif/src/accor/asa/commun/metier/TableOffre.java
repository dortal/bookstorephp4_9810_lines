package com.accor.asa.commun.metier;

/**
 * classe technique, liée à la table VENTE_GROUPE_OFFRE_TABLE
 * permet de savoir dans quelle table est stockée une offre : "annuelle", "ponctuelle", etc.
 * 
 * @see com.accor.asa.commun.metier.VenteGroupeOffre
 */
public final class TableOffre {
    
    public static final TableOffre ANNUELLE = new TableOffre(1, "offres annuelles","VENTE_OFFRE_SOCIETE","VENTE_OFFRE_HOTEL_SOCIETE");
    public static final TableOffre PONCTUELLE = new TableOffre(2, "offres ponctuelles","VENTE_OFFRE_PONCTUELLE","VENTE_OFFRE_HOTEL_PONCTUELLE");
    public static final TableOffre CARTE = new TableOffre(3, "offres cartes","VENTE_OFFRE_CARTES");
    public static final TableOffre BTP = new TableOffre(4, "offres packagées société","VENTE_OFFRE_BTP");
    public static final TableOffre GAYP = new TableOffre(5, "offres packagées loisir","VENTE_OFFRE_GAYP");
    
    private int m_id;
    private String m_desc;

    private String m_table_offre;
    private String m_table_offre_hotel;

    public TableOffre(int id, String description, String offre) {
        this(id,description,offre,null);
    }
    public TableOffre(int id, String description, String offre, String hotel) {
        m_id = id;
        m_desc = description;
        m_table_offre = offre;
        m_table_offre_hotel = hotel;
    }
        
    public int getId(){
        return m_id;
    }
    
    public String getNomTableOffre(){
        return m_table_offre;
    }
    
    public String getNomTableOffreHotel(){
        return m_table_offre_hotel;
    }
    
    public int hashCode() {
        return m_id;
    }

    public boolean equals(TableOffre autre) {
        return autre.m_id == m_id;
    }

    
    public String toString() {
        return "["+m_id+"] (" + m_desc + ")";
    }

}
