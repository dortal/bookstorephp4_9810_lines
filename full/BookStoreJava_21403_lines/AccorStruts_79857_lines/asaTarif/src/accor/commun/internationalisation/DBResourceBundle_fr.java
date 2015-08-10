package com.accor.commun.internationalisation;

public class DBResourceBundle_fr extends DBResourceBundle
{
    public Object handleGetObject(String key) {
        if (getHashFR() == null)
            loadResourceBundle(DBResourceBundle.FR);
        Object returnValue = getHashFR().get(key);
        if(returnValue == null || returnValue.toString().trim().equals("") || returnValue.toString().equalsIgnoreCase("null"))
            returnValue = super.handleGetObject(key);
        return returnValue;
     }

}