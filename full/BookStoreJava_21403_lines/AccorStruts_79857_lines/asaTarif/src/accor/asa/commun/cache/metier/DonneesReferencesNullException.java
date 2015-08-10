/*
 * Created on 29 mars 04
 */
package com.accor.asa.commun.cache.metier;

import com.accor.asa.commun.process.IncoherenceException;

/**
 * Exception retourné en cas de nullité des données de référence.
 */
@SuppressWarnings("serial")
public class DonneesReferencesNullException extends IncoherenceException {

    /**
     * @param message
     */
    public DonneesReferencesNullException(String message) {
        super(message);
    }

}
