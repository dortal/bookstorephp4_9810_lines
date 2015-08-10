package com.accor.asa.rate.model;


public interface SpecialRate extends Rate {

	public Integer getCodeOffreSpeciale();


	public boolean isObligatoire();

	public Double getPrix();

	public Integer getIdUnitePrix();

	public Integer getIdTypePrix();

	public String getCodeDevise();

	public boolean isTousMarches();

	public String getCodePays1();

	public String getCodePays2();

	public String getCodePays3();

	public String getCodePays4();

	public String getCodePays5();

	public String getCodeContinent1();

	public String getCodeContinent2();

	public String getCodeContinent3();

	public String getCommentaire();

}
