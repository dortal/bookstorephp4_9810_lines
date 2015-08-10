package com.accor.asa.commun.metier.ratelevel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RateLevel implements Serializable {
	
	protected String code;
	protected String libelle;
	protected Integer idFamilleTarif;
	protected String  libFamilleTarif;
	protected String  codeLangue;
	private String codeTypeTarif;

  /**
   * To string
   * @return  String
   */
  public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("[code=").append(code).append("]  ");
      sb.append("[libelle=").append(libelle).append("]  ");
      sb.append("[idFamilleTarif=").append(idFamilleTarif).append("]  ");
      sb.append("[libFamilleTarif=").append(libFamilleTarif).append("]  ");
      sb.append("[codeLangue=").append(codeLangue).append("]  ");
      return sb.toString();
  }


  public RateLevel() {}

  public RateLevel(String code, String libelle, Integer idFamilleTarif, String libFamilleTarif, 
  		String codeLangue ) {
    this.code = code;
    this.libelle = libelle;
    this.idFamilleTarif = idFamilleTarif;
    this.libFamilleTarif = libFamilleTarif;
    this.codeLangue = codeLangue;
}

  public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
  /**
   * @return Returns the idFamilleTarif.
   */
  public Integer getIdFamilleTarif() {
    return idFamilleTarif;
  }
  /**
   * @param idFamilleTarif The idFamilleTarif to set.
   */
  public void setIdFamilleTarif(Integer idFamilleTarif) {
    this.idFamilleTarif = idFamilleTarif;
  }
  /**
   * @return Returns the libFamilleTarif.
   */
  public String getLibFamilleTarif() {
    return libFamilleTarif;
  }
  /**
   * @param libFamilleTarif The libFamilleTarif to set.
   */
  public void setLibFamilleTarif(String libFamilleTarif) {
    this.libFamilleTarif = libFamilleTarif;
  }
  /**
   * @return Returns the codeLangue.
   */
  public String getCodeLangue() {
    return codeLangue;
  }
  /**
   * @param codeLangue The codeLangue to set.
   */
  public void setCodeLangue(String codeLangue) {
    this.codeLangue = codeLangue;
  }


public String getCodeTypeTarif() {
	return codeTypeTarif;
}


public void setCodeTypeTarif(String codeTypeTarif) {
	this.codeTypeTarif = codeTypeTarif;
}
  
  

}
