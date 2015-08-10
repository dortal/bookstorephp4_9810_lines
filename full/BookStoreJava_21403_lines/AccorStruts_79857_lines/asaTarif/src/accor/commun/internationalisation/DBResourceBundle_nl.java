package com.accor.commun.internationalisation;

public class DBResourceBundle_nl extends DBResourceBundle
{
    
    public Object handleGetObject(String key) {
        if (getHashNL() == null)
            loadResourceBundle(DBResourceBundle.NL);
        Object returnValue = getHashNL().get(key);
        if(returnValue == null || returnValue.toString().trim().equals("") || returnValue.toString().equalsIgnoreCase("null"))
            returnValue = super.handleGetObject(key);
        return returnValue;
     }
     
}