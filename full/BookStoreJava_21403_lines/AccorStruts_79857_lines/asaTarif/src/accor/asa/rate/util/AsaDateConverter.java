package com.accor.asa.rate.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class AsaDateConverter extends StrutsTypeConverter {

    private static SimpleDateFormat formatter       = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat smallFormatter  = new SimpleDateFormat("dd/MM/yy");
    private static SimpleDateFormat smallFormatterWithoutSeparator = new SimpleDateFormat("ddMMyy");

    @Override
    public Object convertFromString(Map arg0, String[] arg1, Class arg2) {
        if (arg1 == null)
        	return null;
        String s_date = arg1[0].trim().replaceAll(" ", "/");
        if(s_date.length()==0)
        	return null;
        if (s_date.indexOf("-")!=-1) 
            s_date = arg1[0].trim().replaceAll("-", "/");
        Date d;
        try {
            if (s_date.length() == 6)
                d = smallFormatterWithoutSeparator.parse(s_date);
            else if (s_date.length() == 8)
                d = smallFormatter.parse(s_date);
            else
                d = formatter.parse(s_date);
        } catch (Exception ex) {
            throw new com.opensymphony.xwork2.conversion.TypeConversionException("Invalid date: "+arg1[0].trim());
        }
        return d;
    }

    @Override
    public String convertToString(Map arg0, Object arg1) {
        if (arg1 == null)
            return "";
        return formatter.format((java.util.Date) arg1);
    }


}
