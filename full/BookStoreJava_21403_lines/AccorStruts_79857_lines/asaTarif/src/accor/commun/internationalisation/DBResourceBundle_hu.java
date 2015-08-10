package com.accor.commun.internationalisation;

public class DBResourceBundle_hu extends DBResourceBundle {

	public Object handleGetObject(String key) {
		if (getHashHU() == null)
			loadResourceBundle(DBResourceBundle.HU);
		
		Object returnValue = getHashHU().get(key);
		
		if (returnValue == null || returnValue.toString().trim().equals("") || returnValue.toString().equalsIgnoreCase("null"))
			returnValue = super.handleGetObject(key);
		
		return returnValue;
	}

}