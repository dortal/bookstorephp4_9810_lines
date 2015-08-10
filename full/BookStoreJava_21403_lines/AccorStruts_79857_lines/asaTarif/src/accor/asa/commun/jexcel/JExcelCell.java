package com.accor.asa.commun.jexcel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JExcelCell implements Serializable {

	protected Object 	data;
	protected boolean	deleted;
	
	public JExcelCell( Object data ) {
		this.data 		= data;
		this.deleted 	= false;
	}
	
	
	public JExcelCell( Object data, boolean deleted ) {
		this.data 		= data;
		this.deleted 	= deleted;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "[data=" ).append( data ).append( "], " );
		sb.append( "[deleted=" ).append( deleted ).append( "], " );
		return sb.toString();
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
}
