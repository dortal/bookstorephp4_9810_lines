package com.accor.asa.commun.donneesdereference.metier;

import com.accor.asa.commun.process.IncoherenceException;

/**
 * Cette classe, mal nommée (!), contient les statuts des dossiers et des offres hotels utilisees sur Admin
 * pour le paramétrage des motifs de changement de statut.
 * Les clefs utilisées dans cette classe font reference aux objets com.accor.asa.vente.dossier.metier.Statut (pour les dossiers)
 * et com.accor.asa.vente.offrehotelannuelle.metier.Statut pour les Offres Hotel.
 */
@SuppressWarnings("serial")
public class StatutOffre implements java.io.Serializable {
	// variables d'instances
	private int code;
	private String label;
	// codes des differents statuts
	public static final int _PROJET = 1;
	public static final int _ANNULE = 8;
	public static final int _CONTRAT = 4;
	public static final int _DEMANDE = 2;
	public static final int _PROPOSITION = 3;
	public static final int _REFUS_CLIENT = 7;
	public static final int _REFUS_HOTEL = 6;
	public static final int _SIGNE = 5;
	public static final int _REFUS_CLIENT_OH = 9;
	public static final int _REFUS_HOTEL_OH = 10;
	public static final int _PROJET_OH = 11;
	public static final int _CONTRAT_OH = 12;

	// objets "statut" instanciés
	public static final StatutOffre PROJET = new StatutOffre(_PROJET, "VENTE_STATUT_DOSSIER_PROJET");
	public static final StatutOffre ANNULE = new StatutOffre(_ANNULE, "VENTE_STATUT_DOSSIER_ANNULE");
	public static final StatutOffre CONTRAT = new StatutOffre(_CONTRAT, "VENTE_STATUT_DOSSIER_CONTRAT");
	public static final StatutOffre DEMANDE = new StatutOffre(_DEMANDE, "VENTE_STATUT_DOSSIER_DEMANDE");
	public static final StatutOffre PROPOSITION = new StatutOffre(_PROPOSITION, "VENTE_STATUT_DOSSIER_PROPOSITION");
	public static final StatutOffre REFUS_CLIENT = new StatutOffre(_REFUS_CLIENT, "VENTE_STATUT_DOSSIER_REFUS_CLIENT");
	public static final StatutOffre REFUS_HOTEL = new StatutOffre(_REFUS_HOTEL, "VENTE_STATUT_DOSSIER_REFUS_HOTEL");
	public static final StatutOffre SIGNE = new StatutOffre(_SIGNE, "VENTE_STATUT_DOSSIER_SIGNE");
	public static final StatutOffre REFUS_CLIENT_OH = new StatutOffre(_REFUS_CLIENT_OH, "VENTE_STATUT_OFFREHOTEL_REFUS_CLIENT");
	public static final StatutOffre REFUS_HOTEL_OH = new StatutOffre(_REFUS_HOTEL_OH, "VENTE_STATUT_OFFREHOTEL_REFUS_HOTEL");
	public static final StatutOffre PROJET_OH = new StatutOffre(_PROJET_OH, "VENTE_STATUT_OFFREHOTEL_PROJET");
	public static final StatutOffre CONTRAT_OH = new StatutOffre(_CONTRAT_OH, "VENTE_STATUT_OFFREHOTEL_CONTRAT");

	private StatutOffre(int unCode, String unLabel) {
		code = unCode;
		label = unLabel;
	}

	public String toString() {
		return getLabel();
	}

	public int getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	public boolean equals(Object obj) {
		if (obj instanceof StatutOffre) {
			if (this.getLabel().equals(((StatutOffre) obj).getLabel()))
				return true;
		}
		return false;
	}

	public static StatutOffre getInstance(int unCode) throws IncoherenceException {
		StatutOffre gp = null;
		switch (unCode) {
			case StatutOffre._PROJET :
				gp = StatutOffre.PROJET;
				break;
			case StatutOffre._ANNULE :
				gp = StatutOffre.ANNULE;
				break;
			case StatutOffre._CONTRAT :
				gp = StatutOffre.CONTRAT;
				break;
			case StatutOffre._DEMANDE :
				gp = StatutOffre.DEMANDE;
				break;
			case StatutOffre._PROPOSITION :
				gp = StatutOffre.PROPOSITION;
				break;
			case StatutOffre._REFUS_CLIENT :
				gp = StatutOffre.REFUS_CLIENT;
				break;
			case StatutOffre._REFUS_HOTEL :
				gp = StatutOffre.REFUS_HOTEL;
				break;
			case StatutOffre._SIGNE :
				gp = StatutOffre.SIGNE;
				break;
			case StatutOffre._REFUS_CLIENT_OH :
				gp = StatutOffre.REFUS_CLIENT_OH;
				break;
			case StatutOffre._REFUS_HOTEL_OH :
				gp = StatutOffre.REFUS_HOTEL_OH;
				break;
			case StatutOffre._PROJET_OH :
				gp = StatutOffre.PROJET_OH;
				break;
			case StatutOffre._CONTRAT_OH :
				gp = StatutOffre.CONTRAT_OH;
				break;
			default :
				throw new IncoherenceException("Type de StatutOffre de dossier inconnu / code : " + unCode);
		}
		return gp;
	}

	public static StatutOffre getInstance(String unLabel) throws IncoherenceException {
		StatutOffre gp = null;
		if (unLabel.equals("VENTE_STATUT_DOSSIER_PROJET")) {
			gp = StatutOffre.PROJET;
			return gp;
		}
		if (unLabel.equals("VENTE_STATUT_DOSSIER_ANNULE")) {
			gp = StatutOffre.ANNULE;
			return gp;
		}
		if (unLabel.equals("VENTE_STATUT_DOSSIER_CONTRAT")) {
			gp = StatutOffre.CONTRAT;
			return gp;
		}
		if (unLabel.equals("VENTE_STATUT_DOSSIER_DEMANDE")) {
			gp = StatutOffre.DEMANDE;
			return gp;
		}
		if (unLabel.equals("VENTE_STATUT_DOSSIER_PROPOSITION")) {
			gp = StatutOffre.PROPOSITION;
			return gp;
		}
		if (unLabel.equals("VENTE_STATUT_DOSSIER_REFUS_CLIENT")) {
			gp = StatutOffre.REFUS_CLIENT;
			return gp;
		}
		if (unLabel.equals("VENTE_STATUT_DOSSIER_REFUS_HOTEL")) {
			gp = StatutOffre.REFUS_HOTEL;
			return gp;
		}
		if (unLabel.equals("VENTE_STATUT_DOSSIER_SIGNE")) {
			gp = StatutOffre.SIGNE;
			return gp;
		}
		if (unLabel.equals("VENTE_STATUT_OFFREHOTEL_REFUS_CLIENT")) {
			gp = StatutOffre.REFUS_CLIENT_OH;
			return gp;
		}
		if (unLabel.equals("VENTE_STATUT_OFFREHOTEL_REFUS_HOTEL")) {
			gp = StatutOffre.REFUS_HOTEL_OH;
			return gp;
		}
		if (unLabel.equals("VENTE_STATUT_OFFREHOTEL_PROJET")) {
			gp = StatutOffre.PROJET_OH;
			return gp;
		}
		if (unLabel.equals("VENTE_STATUT_OFFREHOTEL_CONTRAT")) {
			gp = StatutOffre.CONTRAT_OH;
			return gp;
		}
		throw new IncoherenceException("Type de StatutOffre de dossier inconnu / label : " + unLabel);
	}

	public static java.util.List<StatutOffre> getStatutOffreAll() throws IncoherenceException {
		java.util.List<StatutOffre> StatutOffreListe = new java.util.ArrayList<StatutOffre>();
		StatutOffreListe.add(StatutOffre.getInstance(StatutOffre._PROJET));
		StatutOffreListe.add(StatutOffre.getInstance(StatutOffre._DEMANDE));
		StatutOffreListe.add(StatutOffre.getInstance(StatutOffre._PROPOSITION));
		StatutOffreListe.add(StatutOffre.getInstance(StatutOffre._CONTRAT));
		StatutOffreListe.add(StatutOffre.getInstance(StatutOffre._SIGNE));
		StatutOffreListe.add(StatutOffre.getInstance(StatutOffre._REFUS_HOTEL));
		StatutOffreListe.add(StatutOffre.getInstance(StatutOffre._REFUS_CLIENT));
		StatutOffreListe.add(StatutOffre.getInstance(StatutOffre._ANNULE));
		StatutOffreListe.add(StatutOffre.getInstance(StatutOffre._PROJET_OH));
		StatutOffreListe.add(StatutOffre.getInstance(StatutOffre._CONTRAT_OH));
		StatutOffreListe.add(StatutOffre.getInstance(StatutOffre._REFUS_HOTEL_OH));
		StatutOffreListe.add(StatutOffre.getInstance(StatutOffre._REFUS_CLIENT_OH));

		return StatutOffreListe;
	}
}