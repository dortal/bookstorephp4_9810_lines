package com.accor.commun.internationalisation;

public class DBResourceBundle_es extends DBResourceBundle
{
    
    public Object handleGetObject(String key) {
        if (getHashES() == null)
            loadResourceBundle(DBResourceBundle.ES);
        Object returnValue = getHashES().get(key);
        if(returnValue == null || returnValue.toString().trim().equals("") || returnValue.toString().equalsIgnoreCase("null"))
            returnValue = super.handleGetObject(key);
        return returnValue;
     }
     
}