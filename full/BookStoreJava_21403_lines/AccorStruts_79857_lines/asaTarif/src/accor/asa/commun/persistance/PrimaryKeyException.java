package com.accor.asa.commun.persistance;

/**
 * Levee en cas de duplicate key
 */
public class PrimaryKeyException extends PersistanceException
{
    public PrimaryKeyException(){
      super();
    }

    public PrimaryKeyException(String message){
      super(message);
    }

    public PrimaryKeyException(Exception e){
      super(e);
    }
}