package com.accor.asa.commun.hotel.model;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public interface SearchCriteria  {

    public String getCode();

    public String getNom();

    public String getVille();

    public String getCodeMarque();

    public String getCodeChaine();

    public String getCodePays();

    public String getCodePlace();

    public String getCodeDirOper();

    public String getCodeGroupe();

    public String getCodeLocOp();

    // ---  FOr SELECT ------------
    public void setUrl(String url);
    public String getIdHotel();
    
}
