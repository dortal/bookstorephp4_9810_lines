package com.accor.asa.commun.cache.metier;

import java.io.Serializable;

/***
 * Abstract Class fournissant tous les services 
 * obligatoires a l'enregistrement d'un Object dans le cache 
 * @author FCHIVAUX
 *
 */
public abstract class AbstractCachable implements Serializable {

  /** key separator */
  public static final char KEY_CACHE_SEPARATOR = '_';
  public String[] params;
	private long ttl = 0;
  
/*
	public static Object generateKey() {
		// TODO Auto-generated method stub
		return null;
	}
*/
	public Object getKey() {
		// TODO Auto-generated method stub
		return generateKey( params );
	}

	public long getTTL() {
		// TODO Auto-generated method stub
		return ttl;
	}

	public void setTTL(long arg0) {
		// TODO Auto-generated method stub
		this.ttl = arg0;
	}

	public static Object generateKey( Object[] params ) {
		StringBuffer sb = new StringBuffer("");
		
		if( params != null ) {
			for( int i=0, size=params.length; i<size; i++ )
				sb.append( KEY_CACHE_SEPARATOR ).append( String.valueOf( params[i] ) );
		}
		return sb.toString();
	}

	public abstract Object getElements();
}
