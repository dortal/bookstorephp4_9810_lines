package com.accor.asa.rate.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.accor.asa.commun.metier.AsaDate;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class ChildRateBean extends RateBean implements ChildRate{

	private String codeRateLevel;
	private String[] codesProduit;
	private Date dateDebut;
	private Date dateFin;
	private Integer maxChild;
	private Integer maxAdult;
    private String codeDevise;
	private boolean chambreSepare;
    List<ChildRateServiceData> services = new ArrayList<ChildRateServiceData>();

	public String getCodeRateLevel() {
		return codeRateLevel;
	}
	public void setCodeRateLevel(String codeRateLevel) {
		this.codeRateLevel = codeRateLevel;
	}

    public String[] getCodesProduit() {
        return codesProduit;
    }

    public void setCodesProduit(String[] codesProduit) {
        this.codesProduit = codesProduit;
    }

    public String getCodeProduit() {
		if (codesProduit != null && codesProduit.length > 0)
			return codesProduit[0];
		else
			return null;
	}

	public void setCodeProduit(String unCodeProduit) {
		if (codesProduit == null)
			codesProduit = new String[1];
		codesProduit[0] = unCodeProduit;
	}

    public int getNbreProduits() {
        return (codesProduit!=null)?codesProduit.length:0;
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

    public Integer getMaxChild() {
        return maxChild;
    }

    public void setMaxChild(Integer maxChild) {
        this.maxChild = maxChild;
    }

    public Integer getMaxAdult() {
        return maxAdult;
    }

    public void setMaxAdult(Integer maxAdult) {
        this.maxAdult = maxAdult;
    }

    public String getCodeDevise() {
        return codeDevise;
    }

    public void setCodeDevise(String codeDevise) {
        this.codeDevise = codeDevise;
    }

    public boolean isChambreSepare() {
        return chambreSepare;
    }

    public void setChambreSepare(boolean chambreSepare) {
        this.chambreSepare = chambreSepare;
    }

    public List<ChildRateServiceData> getServices() {
        return services;
    }

    public void setServices(List<ChildRateServiceData> services) {
        this.services = services;
    }

    public void addService(ChildRateServiceData service) {
        services.add(service);
    }

    public ChildRateServiceData getService(int index) {
        ChildRateServiceData service = new ChildRateServiceData(null, null, null, null, null, null, null, null);
        service.setAgeActiv(false);
        if (services!=null && services.size()>index) {
            service = services.get(index);
            service.setAgeActiv(true);
        }
        return service;
    }

    public void clearServices() {
        services.clear();
    }

    public static ChildRateBean createForRow(ChildRateRowBean row) {
		ChildRateBean bean = new ChildRateBean();
        bean.setIdGrille(row.getIdGrille());
        bean.setCodeProduit(row.getCodeProduit());
		bean.setCodeRateLevel(row.getCodeRateLevel());
		bean.setDateDebut(row.getDateDebut());
		bean.setDateFin(row.getDateFin());
		bean.setMaxAdult(row.getMaxAdult());
		bean.setMaxChild(row.getMaxChild());
        bean.setChambreSepare(row.getChambreSepare());
        bean.setCodeDevise(row.getCodeDevise());
        bean.setKey(row.generateBeanKey());
		return bean;
	}

    //=========================================================================================
    //=============                       OTHER                  ==============================
    //=========================================================================================

    @Override
	public String generateBeanKey() {
		StringBuffer sb = new StringBuffer(String.valueOf(getIdGrille()));
		sb.append("_").append(getCodeRateLevel())
		.append("_").append(getCodeProduit()).append("_")
		.append(new AsaDate(getDateDebut())).append("_")
		.append(isChambreSepare());
		return sb.toString();
	}


    /**
     * To String
     *
     * @return
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("(").append(key).append(")");
        sb.append("_").append(idGrille).append("_").append(codeRateLevel);
        if (codesProduit != null) {
            sb.append("_[");
            for (String chambre : codesProduit)
                sb.append((sb.toString().endsWith("[")) ? chambre : "," + chambre);
            sb.append("]");
        } else {
            sb.append("_").append("null");
        }
        sb.append("_").append(new AsaDate(dateDebut)).
                append("_").append(new AsaDate(dateFin)).
                append("_").append(maxChild).
                append("_").append(maxAdult).
                append("_").append(codeDevise).
                append("_").append(chambreSepare);
        if (services != null) {
            sb.append("_{");
            for (ChildRateServiceData service : services)
                sb.append("_[").append(service).append("]");
            sb.append("}");
        } else {
            sb.append("_").append("null");
        }
        return sb.toString();
    }

}
