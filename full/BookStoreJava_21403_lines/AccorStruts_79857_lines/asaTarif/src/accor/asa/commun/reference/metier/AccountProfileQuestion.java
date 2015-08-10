package com.accor.asa.commun.reference.metier;

/***
 * Objet metier définissant une Question RFP
 * @author FCHIVAUX
 *
 */
@SuppressWarnings("serial")
public class AccountProfileQuestion extends RefBean {

	protected Integer page;
	protected Integer section;
	protected Integer ordre;
	protected boolean obligatoire;
	protected boolean defaultValue;
	protected boolean value;
	
	public boolean isObligatoire() {
		return obligatoire;
	}
	public void setObligatoire(boolean obligatoire) {
		this.obligatoire = obligatoire;
	}
	public Integer getOrdre() {
		return ordre;
	}
	public void setOrdre(Integer ordre) {
		this.ordre = ordre;
	}
	public void setOrdre(int ordre) {
		this.ordre = new Integer(ordre);
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public void setPage(int page) {
		this.page = new Integer(page);
	}
	public Integer getSection() {
		return section;
	}
	public void setSection(Integer section) {
		this.section = section;
	}
	public void setSection(int section) {
		this.section = new Integer(section);
	}
	public boolean isValue() {
		return value;
	}
	public void setValue(boolean value) {
		this.value = value;
	}
	public boolean isDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[idseq=").append(code).append("], ");
		sb.append("[libelle=").append(libelle).append("], ");
		sb.append("[page=").append(page).append("], ");
		sb.append("[section=").append(section).append("], ");
		sb.append("[ordre=").append(ordre).append("], ");
		sb.append("[obligatoire=").append(obligatoire).append("], ");
		sb.append("[defaultValue=").append(defaultValue).append("], ");
		sb.append("[value=").append(value).append("], ");
		sb.append("[supprimer=").append(!isActif()).append("], ");
		return sb.toString();
	}
}
