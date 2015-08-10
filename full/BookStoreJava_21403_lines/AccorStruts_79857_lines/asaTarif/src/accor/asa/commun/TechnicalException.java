package com.accor.asa.commun;

@SuppressWarnings("serial")
public class TechnicalException extends SafeMessageException {

  public TechnicalException(String message) {
    super(message);
  }

  public TechnicalException(Exception ex) {
    super(ex.getMessage());
  }
}