package com.accor.asa.commun.persistance;

/**
 * Classe d exception pouvant etre levees dans les DAO.
 * Le fait de lever une exception de type UserDatabaseException
 * indique qu'un problème grave de base est survenu (deadLock, PK ou FK non respectee)
 */
public class PersistanceException extends RuntimeException
{
    public PersistanceException(){
      super();
    }

    public PersistanceException(String message){
      super(message);
    }

    public PersistanceException(Exception e){
      super(e.getMessage());
    }
}