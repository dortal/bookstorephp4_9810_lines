package com.accor.asa.commun.persistance;


/**
 * jeu de paramètres pour un appel de procédure
 */
public class SQLParamDescriptorSet {
    /**
     * liste de paramètres vide
     */
    public SQLParamDescriptorSet() {
    }

    private SQLParamDescriptor[] jeuDeParametresSQL;
   
    public SQLParamDescriptorSet(SQLParamDescriptor sqlParamDesc) {
        this (new SQLParamDescriptor[]{ sqlParamDesc });
    }

    public SQLParamDescriptorSet(SQLParamDescriptor[] sqlParamDescTable) {
        this.jeuDeParametresSQL = sqlParamDescTable;
    }
    
    public int size() {
        if ( jeuDeParametresSQL != null )
            return jeuDeParametresSQL.length;
        else
            return 0;
    }
    
    public SQLParamDescriptor getParameter(int index)
    {
        return jeuDeParametresSQL[index];
    }
    
    public String toString()
    {
        if (jeuDeParametresSQL == null) return "[]";
        String result = "[";
        for (int i=0 ; i<jeuDeParametresSQL.length ; i++)
        {
        	if (jeuDeParametresSQL[i] == null) throw new RuntimeException("Le paramètre à l'index " + i + " n'a pas été initialisé dans le SQLParamDescriptor");
            result += jeuDeParametresSQL[i].toString();
            if (i<jeuDeParametresSQL.length-1) result += ", ";
        }
        result += "]";
        return result;
    }
}
