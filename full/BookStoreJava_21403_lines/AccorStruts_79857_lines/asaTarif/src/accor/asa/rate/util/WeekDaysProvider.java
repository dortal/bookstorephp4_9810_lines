package com.accor.asa.rate.util;

import java.util.HashMap;

import com.accor.asa.commun.metier.Contexte;
import com.accor.commun.internationalisation.Translator;
import com.accor.commun.internationalisation.TranslatorFactory;

public class WeekDaysProvider {
	
	private static HashMap<String, String[]> weekDaysMap= new HashMap<String, String[]>();
	
	public static  String[] getWeekDays(Contexte contexte) {
		String langCode =contexte.getCodeLangue();
		String[] weekdays = weekDaysMap.get(langCode);
		if(weekdays==null) {
			weekdays=loadWeekDays(contexte);
			weekDaysMap.put(langCode, weekdays);
		}
		return weekdays;
	}

	private  static String[] loadWeekDays(Contexte contexte) {
        Translator trans = TranslatorFactory.getTranslator(contexte.getCodeLangue(), true);
        return new String[] {   trans.getLibelle("ALL_ALL_DAY_ABR2_LUNDI"),
                                trans.getLibelle("ALL_ALL_DAY_ABR2_MARDI"),
                                trans.getLibelle("ALL_ALL_DAY_ABR2_MERCREDI"),
                                trans.getLibelle("ALL_ALL_DAY_ABR2_JEUDI"),
                                trans.getLibelle("ALL_ALL_DAY_ABR2_VENDREDI"),
                                trans.getLibelle("ALL_ALL_DAY_ABR2_SAMEDI"),
                                trans.getLibelle("ALL_ALL_DAY_ABR2_DIMANCHE")   };
	}
}
