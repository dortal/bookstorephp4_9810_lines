package com.accor.asa.commun.cache.metier;

/**
 * Classe de référence pour tous les caches gérés par le cacheManager 
 */
public class CacheNameReferences {
    
    /** Cache de la liste des pays (procSelPays et venteSelectListePays)*/      
    public static final String LISTE_PAYS = "LISTE_PAYS";

    /** Cache de la liste des groupes de prestations (??)*/      
    public static final String LISTE_GROUPES_PRESTATIONS = "LISTE_GROUPES_PRESTATIONS";
    
    /** Cache de la liste des profiles (procSelProfile) */
    public static final String LISTE_PROFILS = "LISTE_PROFILS";

    /** Cache des données de références Cible Commerciale */
    public static final String CIBLE_COMMERCIALE = "CIBLE_COMMERCIALE";

    /** Cache des données de références MAilling */
    public static final String MAILLING = "MAILLING";
        
    /** Cache des données de références MArche */
     public static final String MARCHE = "MARCHE";
        
    /** Cache des données de références Prestation */
     public static final String PRESTATION = "PRESTATION";
        
     /** Cache des fichiers Template */
     public static final String TEMPLATE = "TEMPLATE";
        
    /** Cache des données de références Type Offre */
     public static final String TYPE_OFFRE = "TYPE_OFFRE";                                    

    /** Caches de la table internationalisation */
    public static final String INTERNATIONALISATION = "INTERNATIONALISATION";

    /** cache des pondérations de calcul d'habilitation par axe et par groupe */
    public static final String HABIL_PONDERATION_AXES = "HABIL_PONDERATION_AXES";

    /** cache des droits d'acces d'habilitation par axe et par groupe */
    public static final String HABIL_DROIT_AXES = "HABIL_DROIT_AXES";
    
    /** cache des statuts pour les motifs de changements de statut*/
    public static final String VENTE_MOTIF_STATUT = "VENTE_MOTIF_STATUT";

	/** cache groupe d'offre pour les motifs de changement de statut*/
	public static final String VENTE_MOTIF_CODE_GROUPE_OFFRE= "VENTE_MOTIF_CODE_GROUPE_OFFRE";
}
