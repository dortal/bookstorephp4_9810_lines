package com.accor.asa.rate;

public class RatesUserException extends RatesException{

	public static String KEY_DUPLICATE_KEY_MSG="";
	public static String KEY_FOREIGN_KEY_MSG  ="";

	public RatesUserException(String defaultMessage, String key) {
		super(defaultMessage, key);
	}

	public RatesUserException(String defaultMessage) {
		super(defaultMessage);
	}

	public RatesUserException(Throwable throwable, String defaultMessage, String key) {
		super(throwable, defaultMessage, key);
	}

	public RatesUserException(Throwable throwable, String key) {
		super(throwable, key);
	}

	
	
}
