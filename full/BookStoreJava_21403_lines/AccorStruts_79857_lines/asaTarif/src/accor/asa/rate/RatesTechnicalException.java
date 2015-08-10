package com.accor.asa.rate;

public class RatesTechnicalException extends RatesException{
	
	private static String DEFALT_MESSAGE_KEY="COM_PRM_MSG_DEFAULTERROR";
	private static String DEFALT_MESSAGE    ="A technical error occurs";
	public RatesTechnicalException() {
		super(DEFALT_MESSAGE,DEFALT_MESSAGE_KEY);
	}
	public RatesTechnicalException(String defaultMessage, String key) {
		super(defaultMessage, key);
	}
	public RatesTechnicalException(String defaultMessage) {
		super(defaultMessage);
	}
	public RatesTechnicalException(Throwable throwable, String defaultMessage, String key) {
		super(throwable, defaultMessage, key);
	}
	public RatesTechnicalException(Throwable throwable, String key) {
		super(throwable, key);
	}
	public RatesTechnicalException(Throwable throwable) {
		super(throwable, DEFALT_MESSAGE, DEFALT_MESSAGE_KEY);
	}

	

	
}
