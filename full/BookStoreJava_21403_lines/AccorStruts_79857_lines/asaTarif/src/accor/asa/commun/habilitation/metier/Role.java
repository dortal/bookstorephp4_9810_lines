package com.accor.asa.commun.habilitation.metier;

import com.accor.asa.commun.metier.Element;

/**
 * Title:        Projet Asa
 * Classe représentant un role (CodeRole, NomRole)
 * Copyright:    Copyright (c) 2001
 * Company:      Valtech
 * @author David Dreistadt
 * @version 1.0
 */

@SuppressWarnings("serial")
public class Role extends Element {

	public static final String CODE_ROLE_ADMIN_PAYS = "6";
	
	private boolean responsableDossier;
	private boolean reattributionDossier;
	private boolean roleVente;
	private boolean isRoleDbm;
	private boolean isRoleHotel; 
	private boolean isRoleValidateurPremierNiveau;
	private boolean isRoleValidateurDeuxiemeNiveau;
	private boolean isRoleValidateurTroisiemeNiveau;
	
	public Role() {
	}

	public Role(String code, String libelle,boolean isRoleVente) {
		super(code, libelle);
		this.roleVente = isRoleVente;
	}

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append( super.toString() );
        sb.append("[responsableDossier=").append(responsableDossier).append("],");
        sb.append("[reattributionDossier=").append(reattributionDossier).append("],");
        sb.append("[roleVente=").append(roleVente).append("],");
        sb.append("[isRoleDbm=").append(isRoleDbm).append("],");
        sb.append("[isRoleHotel=").append(isRoleHotel).append("],");
        sb.append("[isRoleValidateurPremierNiveau=").append(isRoleValidateurPremierNiveau).append("],");
        sb.append("[isRoleValidateurDeuxiemeNiveau=").append(isRoleValidateurDeuxiemeNiveau).append("],");
        sb.append("[isRoleValidateurTroisiemeNiveau=").append(isRoleValidateurTroisiemeNiveau).append("],");
        return sb.toString();
    }
	
	public boolean isReattributionDossier() {
		return reattributionDossier;
	}

	public boolean isResponsableDossier() {
		return responsableDossier;
	}

	public void setReattributionDossier(boolean b) {
		reattributionDossier = b;
	}

	public void setResponsableDossier(boolean b) {
		responsableDossier = b;
	}

	public boolean isRoleVente() {
		return roleVente;
	}

	public void setRoleVente(boolean roleVente) {
		this.roleVente = roleVente;
	}

	public boolean isRoleDbm() {
		return isRoleDbm;
	}

	public void setRoleDbm(boolean isRoleDbm) {
		this.isRoleDbm = isRoleDbm;
	}

	public boolean isRoleHotel() {
		return isRoleHotel;
	}

	public void setRoleHotel(boolean isRoleHotel) {
		this.isRoleHotel = isRoleHotel;
	}

	public boolean isRoleValidateurPremierNiveau() {
		return isRoleValidateurPremierNiveau;
	}

	public void setRoleValidateurPremierNiveau(boolean isRoleValidateurPremierNiveau) {
		this.isRoleValidateurPremierNiveau = isRoleValidateurPremierNiveau;
	}

	public boolean isRoleValidateurDeuxiemeNiveau() {
		return isRoleValidateurDeuxiemeNiveau;
	}

	public void setRoleValidateurDeuxiemeNiveau(
			boolean isRoleValidateurDeuxiemeNiveau) {
		this.isRoleValidateurDeuxiemeNiveau = isRoleValidateurDeuxiemeNiveau;
	}

	public boolean isRoleValidateurTroisiemeNiveau() {
		return isRoleValidateurTroisiemeNiveau;
	}

	public void setRoleValidateurTroisiemeNiveau(
			boolean isRoleValidateurTroisiemeNiveau) {
		this.isRoleValidateurTroisiemeNiveau = isRoleValidateurTroisiemeNiveau;
	}
}