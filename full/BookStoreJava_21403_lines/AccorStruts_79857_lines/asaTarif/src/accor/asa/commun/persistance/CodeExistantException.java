package com.accor.asa.commun.persistance;

public class CodeExistantException extends PersistanceException
{
  public CodeExistantException(String message)
  {
    super(message);
  }
}