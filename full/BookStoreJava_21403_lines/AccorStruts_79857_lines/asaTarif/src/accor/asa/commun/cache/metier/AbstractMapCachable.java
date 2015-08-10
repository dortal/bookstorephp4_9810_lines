package com.accor.asa.commun.cache.metier;

import java.util.Iterator;
import java.util.Map;

/***
 * Abstract Class fournissant tous les services 
 * obligatoires a l'enregistrement d'une Map dans le cache 
 * @author FCHIVAUX
 *
 */
public abstract class AbstractMapCachable extends AbstractCachable {

    protected Map elements;

    public int size() {
        return elements.size();
    }

    public Map get() {
        return elements;
    }

    public void set( Map elements ) {
        this.elements = elements;
    }

    public Object get( Object index ) {
        if( elements != null && index != null )
            return elements.get( index );

        return null;
    }
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if( this.params != null )
            for( int i=0, size=params.length; i<size; i++ )
                sb.append( params[i] ).append( " - " );
        if( elements != null ) {
            sb.append( "{" );
             Iterator itor = elements.values().iterator();
             while (itor.hasNext()) {
                 sb.append( itor.next().toString() ).append( "|" );
             }
        }
        return sb.toString();
    }

    public Map getElements() {
    	return elements;
    }
}
