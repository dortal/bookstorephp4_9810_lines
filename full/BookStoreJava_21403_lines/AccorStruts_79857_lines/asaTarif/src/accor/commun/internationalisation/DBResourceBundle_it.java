package com.accor.commun.internationalisation;

public class DBResourceBundle_it extends DBResourceBundle
{
    
    public Object handleGetObject(String key) {
        if (getHashIT() == null)
            loadResourceBundle(DBResourceBundle.IT);
        Object returnValue = getHashIT().get(key);
        if(returnValue == null || returnValue.toString().trim().equals("") || returnValue.toString().equalsIgnoreCase("null"))
            returnValue = super.handleGetObject(key);
        return returnValue;
     }
     
}