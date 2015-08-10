package com.accor.asa.commun.utiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * placer ici des méthodes statiques d'outils simples de conversion, de vérification, etc.
 * 
 */
public class Outils {

    public static float convertTCValue( float tcValue ) {
        if ( tcValue == Float.MIN_VALUE )
            return -1;
        else
            return tcValue;
    }
    public static int convertTCValue( int tcValue ) {
        if ( tcValue == Integer.MIN_VALUE )
            return -1;
        else
            return tcValue;
    }
    public static String convertTCValue( String tcValue ) {
        if ( tcValue == null )
            return "";
        else
            return tcValue;
    }
    
    public static Map getIndexedMap(List elements, KeyBuilder keyBuilder) {
		Iterator elementsIter = elements.iterator();
		Map result = new HashMap();
		while (elementsIter.hasNext()) {
			Object element = elementsIter.next();
			Object key = keyBuilder.getKey(element);
			List listForKey = (List)result.get(key);
			if (listForKey == null) {
				listForKey = new ArrayList();
				result.put(key, listForKey); 
			}
			listForKey.add(element);
		}
		return result;
    }
}
