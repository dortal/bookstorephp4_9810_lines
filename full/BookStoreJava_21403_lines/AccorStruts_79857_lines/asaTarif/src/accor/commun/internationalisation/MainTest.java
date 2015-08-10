package com.accor.commun.internationalisation;

public class MainTest
{
	public static void main(String[] args)
	{
		System.out.println("Test du FM d'internationalisation");
		Translator de = TranslatorFactory.getTranslator("de", true);

		System.out.println("En allemand : OK " + de.getLibelle("ALL_ALL_SAVE"));
		//System.out.println("En franais : Test " + fr.getLibelle("test"));
		//System.out.println("En allemand : Test " + de.getLibelle("test"));
	}

}