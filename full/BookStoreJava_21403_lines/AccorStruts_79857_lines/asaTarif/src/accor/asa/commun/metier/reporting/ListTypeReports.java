package com.accor.asa.commun.metier.reporting;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ListTypeReports extends AbstractCachable implements CachableInterface {

	private List<TypeReport> types;
	
	public ListTypeReports( List<TypeReport> typeReports, int codeRole, String codeLangue ) {
		this.types = typeReports;
		this.params = new String[2];
		this.params[0] = codeLangue;
		this.params[1] = String.valueOf( codeRole );
	}
	
	public TypeReport getTypeReport( int idReport ){
		if( types != null )
			for( TypeReport t : types )
				if( t.getIdReport() == idReport )
					return t;
		
		return null;
	}
	
	public List<TypeReport> getElements() {
		return types;
	}
}
