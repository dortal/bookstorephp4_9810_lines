package com.accor.asa.commun.persistance;


/**
 * Contient les informations à passer jusqu'au DAO
 */
public class ContexteAppelDAO implements java.io.Serializable
{
    private String login = null;

    public ContexteAppelDAO(String login){
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
    
}