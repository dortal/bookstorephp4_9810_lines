package com.accor.asa.commun.jexcel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/***
 * Classe servant à construire un tableau Excel en JAVA
 * @author FCHIVAUX
 *
 */
@SuppressWarnings("serial")
public class JExcelArray implements Serializable {

		//Tableau de String contenant les titres des colonnes 
	private String[] header;
		// Liste contenant les differentes lignes a afficher dans le tableau, 
		//		elle-memes représentées par une liste d Objets représentant les celulles du tableau
	private	List<JExcelRow> body;
	
	public JExcelArray() {
		this.body = new ArrayList<JExcelRow>();
	}
	
	public JExcelArray( final String[] header, final List<JExcelRow> body ) {
		this.header = header;
		this.body = body;
	}

	public JExcelCell getCell( final int indexRow, final int indexCol ) {
		return body.get( indexRow ).get( indexCol );
	}
	
	public List<JExcelRow> getBody() {
		return body;
	}

	public void setBody( final List<JExcelRow> body ) {
		this.body = body;
	}

	public String[] getHeader() {
		return header;
	}

	public void setHeader( final String[] header ) {
		this.header = header;
	}
}
