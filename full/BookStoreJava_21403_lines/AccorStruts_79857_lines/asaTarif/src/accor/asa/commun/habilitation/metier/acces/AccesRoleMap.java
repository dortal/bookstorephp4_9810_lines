package com.accor.asa.commun.habilitation.metier.acces;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.accor.asa.commun.habilitation.metier.Droit;


public class AccesRoleMap {

	private Map<String,Droit> droits;
	private Map<String,Integer> regles;

	public AccesRoleMap() {
		droits = new HashMap<String,Droit>();
		regles = new HashMap<String,Integer>();
	}

	public int size() {
		return droits.size();
	}

    public void put( String index, Droit a, Integer regle ) {
    	droits.put( index, a );
    	regles.put( index, regle );
    }
    
    public Droit getDroit ( Object index ) {
        if( index != null )
            return (Droit) droits.get( index );

        return null;
    }
    
    public Integer getRegle (Object index) {
    	if (index != null)
    		return (Integer) regles.get(index);
    	return null;
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if( droits != null ) {
            sb.append( "{" );
             Iterator<Droit> itor = droits.values().iterator();
             while (itor.hasNext()) {
                 sb.append( itor.next().toString() ).append( "|" );
             }
        }
        return sb.toString();
    }
	
}
