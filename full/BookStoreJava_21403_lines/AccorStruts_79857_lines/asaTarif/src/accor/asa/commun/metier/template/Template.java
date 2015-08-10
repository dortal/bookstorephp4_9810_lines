package com.accor.asa.commun.metier.template;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 13 mars 2006
 * Time: 17:42:13
 * To change this template use File | Settings | File Templates.
 */
public class Template implements Serializable {
    protected String  idTemplate;
    protected String  libelle;
    protected String  documentFileName;
    protected String  langue;
    protected String  codePays;
    protected String  selectOHActive;
    protected String  statutOH;
    protected String  statutCible;
    protected String  version;
    protected String  dateVersion;
    protected String  ratesDisplayType;

    protected String  codeGroupeOffre;
    protected String  codeTypeOffre;
    protected String  codeListeStatut;
    protected String typeDocument;

    protected String  langueContact;
    /**
     * Constructeur
     */
    public Template() {
    }

    // ================ GETTER AND SETTER ======================

    public String getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(String idTemplate) {
        this.idTemplate = idTemplate;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDocumentFileName() {
        return documentFileName;
    }

    public void setDocumentFileName(String documentFileName) {
        this.documentFileName = documentFileName;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getCodePays() {
        return codePays;
    }

    public void setCodePays(String codePays) {
        this.codePays = codePays;
    }

    public String getSelectOHActive() {
        return selectOHActive;
    }

    public void setSelectOHActive(String selectOHActive) {
        this.selectOHActive = selectOHActive;
    }

    public String getStatutOH() {
        return statutOH;
    }

    public void setStatutOH(String statutOH) {
        this.statutOH = statutOH;
    }

    public String getStatutCible() {
        return statutCible;
    }

    public void setStatutCible(String statutCible) {
        this.statutCible = statutCible;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDateVersion() {
        return dateVersion;
    }

    public void setDateVersion(String dateVersion) {
        this.dateVersion = dateVersion;
    }

    public String getRatesDisplayType() {
        return ratesDisplayType;
    }

    public void setRatesDisplayType(String ratesDisplayType) {
        this.ratesDisplayType = ratesDisplayType;
    }

    public String getCodeGroupeOffre() {
        return codeGroupeOffre;
    }

    public void setCodeGroupeOffre(String codeGroupeOffre) {
        this.codeGroupeOffre = codeGroupeOffre;
    }

    public String getCodeTypeOffre() {
        return codeTypeOffre;
    }

    public void setCodeTypeOffre(String codeTypeOffre) {
        this.codeTypeOffre = codeTypeOffre;
    }

    public String getCodeListeStatut() {
        return codeListeStatut;
    }

    public void setCodeListeStatut(String codeListeStatut) {
        this.codeListeStatut = codeListeStatut;
    }
    
    public String getTypeDocument () {
    	return typeDocument;
    }

    public void setTypeDocument (String typeDocument) {
    	this.typeDocument = typeDocument;
    }
    
    public String getLangueContact() {
        return langueContact;
    }

    public void setLangueContact(String langueContact) {
        this.langueContact = langueContact;
    }


    /**
     * To string
     * @return  String
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[idTemplate=").append(idTemplate).append("]  ");
        sb.append("[libelle=").append(libelle).append("]  ");
        sb.append("[documentFileName=").append(documentFileName).append("]  ");
        sb.append("[langue=").append(langue).append("]  ");
        sb.append("[codePays=").append(codePays).append("]  ");
        sb.append("[selectOHActive=").append(selectOHActive).append("]  ");
        sb.append("[statutOH=").append(statutOH).append("]  ");
        sb.append("[statutCible=").append(statutCible).append("]  ");
        sb.append("[version=").append(version).append("]  ");
        sb.append("[dateVersion=").append(dateVersion).append("]  ");
        sb.append("[ratesDisplayType=").append(ratesDisplayType).append("]  ");

        sb.append("[codeGroupeOffre=").append(codeGroupeOffre).append("]  ");
        sb.append("[codeTypeOffre=").append(codeTypeOffre).append("]  ");
        sb.append("[codeListeStatut=").append(codeListeStatut).append("]  ");
        sb.append("[typeDocument=").append(typeDocument).append("]  ");

        sb.append("[langueContact=").append(langueContact).append("]  ");
        return sb.toString();
    }

}
