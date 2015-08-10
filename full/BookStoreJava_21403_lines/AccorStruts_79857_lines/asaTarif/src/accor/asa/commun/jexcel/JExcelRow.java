package com.accor.asa.commun.jexcel;

import java.util.ArrayList;
import java.util.List;

public class JExcelRow {

	protected List<JExcelCell> rows;
	
	public JExcelRow() {
		rows = new ArrayList<JExcelCell>();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "[rows=" );
		if( rows != null ) {
			for( JExcelCell d : rows ) {
				sb.append( d.toString() );
			}
		}
		sb.append( "], " );
		return sb.toString();
	}
	
	public void add( JExcelCell cell ) {
		if( rows == null )
			rows = new ArrayList<JExcelCell>();
		rows.add( cell );
	}
	
	public JExcelCell get( int index ) {
		if( rows != null ) 
			return rows.get( index );
		return null;
	}
	
	public int size() {
		if( rows != null )
			return rows.size();
		return 0;
	}
}
