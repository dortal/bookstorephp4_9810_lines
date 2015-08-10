package com.accor.asa.commun.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.accor.asa.commun.TechnicalException;

/**
 * Interface à implémenter pour specifier comment un résultSet doit être interpreté
 * (une implémentation de SQLResultSetReader correspond a un select particulier et est dépendant des colonnes retournées)
 */
public interface SQLResultSetReader 
{
    /**
     * Crée un objet métier représentant une ligne du résultset.
     * Typiquement :
     * 
     * MaClasseMetier instance = new MaClasseMetier();
     * instance.setAttribut1(rs.getString("ATTRIBUT1");
     * ...
     * return instance;
     * 
     * 
     * ATTENTION : N'utiliser que des méthodes getXXX() dans l'implémentation de cette méthode 
     */
    public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException; 
}
