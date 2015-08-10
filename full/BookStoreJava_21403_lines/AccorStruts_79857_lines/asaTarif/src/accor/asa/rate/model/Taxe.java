package com.accor.asa.rate.model;

import java.util.Date;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public interface Taxe {
    static public Integer STATUT_NONTRANSFERE   = 1;
    static public Integer STATUT_TRANSFERE      = 6;
    static public Integer TYPEPRIX_NONE         = 0;
    static public Integer TYPEPRIX_MONTANT      = 1;
    static public Integer TYPEPRIX_POURCENTAGE  = 2;
    static public Integer UNITETAXE_NONE        = 0;

    public String getCode();

    public Date getDateDebut();

    public Date getDateFin();

    public boolean getInclus();

    public Double getMontant();

    public Integer getIdTypePrix();

    public Integer getIdUniteTaxe();

    public String getCodeDevise();

    public Integer getIdStatut();

    public boolean getSupprime();

    public String [] getRateLevels();
}
