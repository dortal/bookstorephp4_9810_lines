package com.accor.commun.internationalisation;

public class DBResourceBundle_pt extends DBResourceBundle
{
    
    public Object handleGetObject(String key) {
        if (getHashPT() == null)
            loadResourceBundle(DBResourceBundle.PT);
        Object returnValue = getHashPT().get(key);
        if(returnValue == null || returnValue.toString().trim().equals("") || returnValue.toString().equalsIgnoreCase("null"))
            returnValue = super.handleGetObject(key);
        return returnValue;
     }
     
}