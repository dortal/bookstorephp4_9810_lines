package com.accor.asa.commun.persistance;

/**
 * Levee en cas d'erreur generee par une contrainte de cle etrangere
 */
public class ForeignKeyException extends PersistanceException
{
    public ForeignKeyException(){
      super();
    }

    public ForeignKeyException(String message){
      super(message);
    }

    public ForeignKeyException(Exception e){
      super(e);
    }
}