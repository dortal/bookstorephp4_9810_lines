package com.accor.asa.rate.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.metier.taxe.TaxeTarsBean;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class TaxeBean extends TaxeTarsBean implements Taxe {

    protected String    codeHotel;

    protected Long      idTaxe;
    protected Date      dateDebut;
    protected Date      dateFin;
    protected boolean   inclus;
    protected Double    montant;
    protected Integer   idTypePrix;
    protected Integer   idUniteTaxe;
    protected String    codeDevise;
    protected Integer   idStatut;
    protected boolean   supprime;

    protected String[] rateLevels;


    public String getCodeHotel() {
        return codeHotel;
    }

    public void setCodeHotel(String codeHotel) {
        this.codeHotel = codeHotel;
    }

    public Long getIdTaxe() {
        return idTaxe;
    }

    public void setIdTaxe(Long idTaxe) {
        this.idTaxe = idTaxe;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public boolean getInclus() {
        return inclus;
    }

    public void setInclus(boolean inclus) {
        this.inclus = inclus;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Integer getIdTypePrix() {
        return idTypePrix;
    }

    public void setIdTypePrix(Integer idTypePrix) {
        this.idTypePrix = idTypePrix;
    }

    public Integer getIdUniteTaxe() {
        return idUniteTaxe;
    }

    public void setIdUniteTaxe(Integer idUniteTaxe) {
        this.idUniteTaxe = idUniteTaxe;
    }

    public String getCodeDevise() {
        return codeDevise;
    }

    public void setCodeDevise(String codeDevise) {
        this.codeDevise = codeDevise;
    }


    public Integer getIdStatut() {
        return idStatut;
    }

    public void setIdStatut(Integer idStatut) {
        this.idStatut = idStatut;
    }


    public boolean getSupprime() {
        return supprime;
    }

    public void setSupprime(boolean supprime) {
        this.supprime = supprime;
    }

    public String[] getRateLevels() {
        return rateLevels;
    }

    public void setRateLevels(String[] rateLevels) {
        this.rateLevels = rateLevels;
    }

    public int getNbRateLevels() {
        return (rateLevels==null)?0:rateLevels.length;
    }

    public List<String> getListRateLevels() {
        return Arrays.asList(rateLevels);
    }

    public void setListRateLevels(List<String> rateLevels) {
        if (rateLevels!=null && rateLevels.size()>0)
            setRateLevels(rateLevels.toArray(new String[0]));
    }

    public String getRateLevel(int index) {
        int nbRL = getNbRateLevels();
        if (rateLevels==null || index<0 || index>=nbRL)
            return null;
        else
            return rateLevels[index];
    }

    public String getRateLevelsOnText() {
        StringBuffer sb = new StringBuffer();
        if (getNbRateLevels()>0) {
            for (String rl:getRateLevels())
                if (StringUtils.isBlank(sb.toString()))
                    sb.append(rl);
                else
                    sb.append(",").append(rl);
        }
        return sb.toString();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("codeHotel=").append(codeHotel).append(",  ");
        sb.append("code=").append(getCode()).append(",  ");
        sb.append("nom=").append(getNom()).append(",  ");
        sb.append("type=").append(getType()).append(",  ");
        sb.append("ttc=").append(getTtc()).append(",  ");
        sb.append("idTaxe=").append(idTaxe).append(",  ");
        sb.append("dateDebut=").append(dateDebut).append(",  ");
        sb.append("dateFin=").append(dateFin).append(",  ");
        sb.append("inclus=").append(inclus).append(",  ");
        sb.append("montant=").append(montant).append(",  ");
        sb.append("idTypePrix=").append(idTypePrix).append(",  ");
        sb.append("idUniteTaxe=").append(idUniteTaxe).append(",  ");
        sb.append("codeDevise=").append(codeDevise).append(",  ");
        sb.append("idStatut=").append(idStatut).append(",  ");
        sb.append("supprime=").append(supprime).append(",  ");
        if (getNbRateLevels()>0) {
            sb.append("[");
            for (String rl:getRateLevels())
                if (sb.toString().endsWith("["))
                    sb.append(rl);
                else
                    sb.append(",").append(rl);
            sb.append("]");
        }
        return sb.toString();
    }

}
