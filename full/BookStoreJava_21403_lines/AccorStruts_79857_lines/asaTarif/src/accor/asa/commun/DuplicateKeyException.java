package com.accor.asa.commun;

@SuppressWarnings("serial")
public class DuplicateKeyException extends SafeMessageException {

	public DuplicateKeyException (String message) {
		super(message);
	}

	public DuplicateKeyException (Exception ex) {
		super(ex.getMessage());
	}

}