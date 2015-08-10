package com.accor.asa.commun.metier.reporting;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.accor.asa.commun.donneesdereference.metier.DonneeReference;

@SuppressWarnings("serial")
public class TypeReport extends DonneeReference implements Serializable {

	private String	code;
	
	private int 		idReport;
	private String	libelle;

	private int			idGroup;
	private	String	cleTranslateGroup;
	private String 	libGroup;
	
	private String 	xslDetaille;
	private String 	xslSynthetique;
	private String 	xslSemiDetaille;
	private boolean	clientIndirect;
	private boolean	hotelReport; 
	private String 	dao;
	private boolean isDefault;
	private boolean isActive;
	
	public DonneeReference getInstance(HttpServletRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isClientIndirect() {
		return clientIndirect;
	}

	public void setClientIndirect(boolean clientIndirect) {
		this.clientIndirect = clientIndirect;
	}

	public boolean isHotelReport() {
		return hotelReport;
	}

	public void setHotelReport(boolean hotelReport) {
		this.hotelReport = hotelReport;
	}

	public int getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(int idGroup) {
		this.idGroup = idGroup;
	}

	public int getIdReport() {
		return idReport;
	}

	public void setIdReport(int idReport) {
		this.idReport = idReport;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getLibReport() {
		return getLibelle();
	}

	public void setLibReport(String libReport) {
		setLibelle( libReport );
	}

	public String getXslDetaille() {
		return xslDetaille;
	}

	public void setXslDetaille(String xslDetaille) {
		this.xslDetaille = xslDetaille;
	}

	public String getXslSemiDetaille() {
		return xslSemiDetaille;
	}

	public void setXslSemiDetaille(String xslSemiDetaille) {
		this.xslSemiDetaille = xslSemiDetaille;
	}

	public String getXslSynthetique() {
		return xslSynthetique;
	}

	public void setXslSynthetique(String xslSynthetique) {
		this.xslSynthetique = xslSynthetique;
	}

	public String getCode() {
		return code;
	}

	public String getCleTranslateReport() {
		return getCode();
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCleTranslateReport( String cleTranslateReport ) {
		setCode(cleTranslateReport);
	}

	public String getXsl( int detailLevel ) {
		String xsl;

		if( detailLevel == NiveauDetail.CODE_SEMI_DETAILLE )
			xsl = getXslSemiDetaille();
		else 
			if( detailLevel == NiveauDetail.CODE_SYNTHETIQUE )
				xsl = getXslSynthetique();
			else
				xsl = getXslDetaille();
		return xsl;
	}

	public String getDao() {
		return dao;
	}

	public void setDao(String dao) {
		this.dao = dao;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getCleTranslateGroup() {
		return cleTranslateGroup;
	}

	public void setCleTranslateGroup(String cleTranslateGroup) {
		this.cleTranslateGroup = cleTranslateGroup;
	}

	public String getLibGroup() {
		return libGroup;
	}

	public void setLibGroup(String libGroup) {
		this.libGroup = libGroup;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String toString() {
  	StringBuffer sb = new StringBuffer();
  	sb.append( "{" );
  	sb.append( code ).append( " - " );
  	sb.append( idReport ).append( " - " );
  	sb.append( libelle ).append( " - " );
  	sb.append( idGroup ).append( " - " );
  	sb.append( cleTranslateGroup ).append( " - " );
  	sb.append( libGroup ).append( " - " );
  	sb.append( xslDetaille ).append( " - " );
  	sb.append( xslSynthetique ).append( " - " );
  	sb.append( xslSemiDetaille ).append( " - " );
  	sb.append( clientIndirect ).append( " - " );
  	sb.append( hotelReport ).append( " - " );
  	sb.append( dao ).append( " - " );
  	sb.append( isDefault ).append( " - " );
  	sb.append( isActive ).append( " - " );
  	sb.append( "}" );
  	return sb.toString();
  }
}
