package com.accor.asa.commun.habilitation.metier.ecran;

import com.accor.asa.commun.metier.Element;

@SuppressWarnings("serial")
public class GroupeEcran extends Element {

	protected boolean groupeEcranVente;
	protected boolean groupeEcranTarif;
	protected boolean groupeEcranAdmin;
	protected boolean groupeEcranTranslate;

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( super.toString() );
		sb.append( "[groupeEcranVente=" ).append( groupeEcranVente ).append( "]," );
		sb.append( "[groupeEcranTarif=" ).append( groupeEcranTarif ).append( "]," );
		sb.append( "[groupeEcranAdmin=" ).append( groupeEcranAdmin ).append( "]," );
		sb.append( "[groupeEcranTranslate=" ).append( groupeEcranTranslate ).append( "]," );
		return sb.toString();
	}

	public boolean isGroupeEcranAdmin() {
		return groupeEcranAdmin;
	}

	public void setGroupeEcranAdmin(boolean groupeEcranAdmin) {
		this.groupeEcranAdmin = groupeEcranAdmin;
	}

	public boolean isGroupeEcranTarif() {
		return groupeEcranTarif;
	}

	public void setGroupeEcranTarif(boolean groupeEcranTarif) {
		this.groupeEcranTarif = groupeEcranTarif;
	}

	public boolean isGroupeEcranTranslate() {
		return groupeEcranTranslate;
	}

	public void setGroupeEcranTranslate(boolean groupeEcranTranslate) {
		this.groupeEcranTranslate = groupeEcranTranslate;
	}

	public boolean isGroupeEcranVente() {
		return groupeEcranVente;
	}

	public void setGroupeEcranVente(boolean groupeEcranVente) {
		this.groupeEcranVente = groupeEcranVente;
	}
}
