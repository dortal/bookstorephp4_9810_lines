package com.accor.commun.internationalisation;

public class DBResourceBundle_en extends DBResourceBundle
{
    
    public Object handleGetObject(String key) {
        if (getHashENG() == null)
            loadResourceBundle();
        Object returnValue = getHashENG().get(key);
        if(returnValue == null || returnValue.toString().trim().equals("") || returnValue.toString().equalsIgnoreCase("null"))
            returnValue = super.handleGetObject(key);
        return returnValue;
     }
     
}