package com.accor.asa.rate.util;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class DoubleConverter extends StrutsTypeConverter {

    @Override
    public Object convertFromString(Map arg0, String[] arg1, Class arg2) {
        String sValue = arg1[0];
        if(sValue==null || sValue.equals(""))
        	return null;
        try {
            return Double.valueOf(sValue);
        }  catch (Exception ex) {
            throw new com.opensymphony.xwork2.conversion.TypeConversionException("Invalid daouble: "+ sValue);
        }
    }

    @Override
    public String convertToString(Map arg0, Object arg1) {
        return String.valueOf(arg1);
    }


}
