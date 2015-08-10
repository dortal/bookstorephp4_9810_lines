package com.accor.commun.internationalisation;

public class DBResourceBundle_de extends DBResourceBundle
{
    
    public Object handleGetObject(String key) {
        if (getHashDE() == null)
            loadResourceBundle(DBResourceBundle.DE);
        Object returnValue = getHashDE().get(key);
        if(returnValue == null || returnValue.toString().trim().equals("") || returnValue.toString().equalsIgnoreCase("null"))
            returnValue = super.handleGetObject(key);
        return returnValue;
     }
     
}