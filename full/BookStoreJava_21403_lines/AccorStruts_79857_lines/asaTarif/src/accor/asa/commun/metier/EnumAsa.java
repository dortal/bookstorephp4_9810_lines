package com.accor.asa.commun.metier;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class EnumAsa implements Serializable {
    private String code;
    private String libelle;
    private static Map<Class<?>, Map<String, EnumAsa>> indexesByClass = new HashMap<Class<?>, Map<String, EnumAsa>>(); 

    public EnumAsa(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
        getClassIndex(this.getClass()).put(code, this);
    }

    public String getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }

    public static EnumAsa getEnumConstant(Class<?> enumClass, String code) {
        return (EnumAsa) getClassIndex(enumClass).get(code);
    }
    
    public static Collection<?> getEnumList(Class<?> enumClass) {
    	return getClassIndex(enumClass).values();
    }
    
    public static Map<String, EnumAsa> getClassIndex(Class<?> aClass) {
        Map<String, EnumAsa> classIndex = indexesByClass.get(aClass);
        if (classIndex == null) {
            classIndex = new HashMap<String, EnumAsa>();
            indexesByClass.put(aClass, classIndex);
        }
        return classIndex;
    }

	public String toString() {
		return "[" + code + ":" + libelle + "]";
	}
}
