package com.accor.asa.commun.cache.metier;

import java.util.List;

/***
 * Abstract Class fournissant tous les services 
 * obligatoires a l'enregistrement d'une List dans le cache 
 * @author FCHIVAUX
 *
 */
public abstract class AbstractListCachable extends AbstractCachable {

	protected List elements;
	
	public int size() {
		return elements.size();
	}
	
	public List get() {
		return elements;
	}
	
	public List getElements() {
		return elements;
	}
	
	public void set( List elements ) {
		this.elements = elements;
	}
	
	public void add( Object element ) {
		this.elements.add( element );
	}
	
	public Object get( int index ) {
		if( elements != null && index<size() ) 
			return elements.get( index );
		
		return null;
	}
	
	public List subList( int fromIndex, int toIndex ) {
		if( elements != null && fromIndex >= 0 && toIndex <= elements.size() ) { 
			return elements.subList(fromIndex, toIndex);
		}
		return null;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if( this.params != null )
			for( int i=0, size=params.length; i<size; i++ )
				sb.append( params[i] ).append( " - " );
		
		if( elements != null ) {
			sb.append( "{" );
			for( int i=0, size=this.elements.size(); i<size; i++ )
				sb.append( (this.elements.get(i) ).toString() ).append( "|" );
			sb.append( "}" );
		}
		return sb.toString();
	}
	
}
