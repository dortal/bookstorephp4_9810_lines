package com.accor.asa.commun.habilitation.metier.visibilite;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.metier.Element;

@SuppressWarnings("serial")
public class TypeVisibilite extends Element {

	public TypeVisibilite() {}

	public TypeVisibilite( String code, String valeur ) {
		super(code, valeur);
	}

	public static String getLibelleForCode( final String code, final List<TypeVisibilite> types ) {
		TypeVisibilite v = getTypeVisibiliteForCode( code, types );
		if( v != null )
			return v.getLibelle();
		return null;
	}

	public static TypeVisibilite getTypeVisibiliteForCode( final String code, 
			final List<TypeVisibilite> types ) {
		TypeVisibilite v;
		for( int i=0, size=types.size(); i<size; i++ ) {
			v = (TypeVisibilite) types.get(i);
			if( StringUtils.equals( code, v.getCode() ) ) 
				return v;
		}
		return null;
	}
}
