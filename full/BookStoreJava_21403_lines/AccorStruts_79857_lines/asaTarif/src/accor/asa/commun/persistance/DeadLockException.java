package com.accor.asa.commun.persistance;

/**
 * Levee en cas de deadLock
 */
public class DeadLockException extends PersistanceException
{
    public DeadLockException(){
      super();
    }

    public DeadLockException(String message){
      super(message);
    }

    public DeadLockException(Exception e){
      super(e);
    }
}