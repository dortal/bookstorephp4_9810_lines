package com.accor.asa.rate.form;

import java.util.Date;

import com.accor.asa.rate.model.BlackOutDatesBean;
import com.opensymphony.xwork2.conversion.annotations.ConversionType;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class BlackOutDatesFormBean extends RateFormBean {

    private BlackOutDatesBean bean;


    public BlackOutDatesFormBean(BlackOutDatesBean bean) {
        this.bean=bean;
    }

    public BlackOutDatesBean getBean() {
        return bean;
    }

    public void setBean(BlackOutDatesBean bean) {
        this.bean = bean;
    }

    public String getKey() {
        return bean.getKey();
    }

    public void setKey(String key) {
        bean.setKey(key);
    }

    public Long getIdGrille() {
        return bean.getIdGrille();
    }

    public void setIdGrille(Long idGrille) {
        bean.setIdGrille(idGrille);
    }

    @TypeConversion(converter="com.accor.asa.rate.util.AsaDateConverter",type=ConversionType.CLASS )
    public Date getDateDebut() {
        return bean.getDateDebut();
    }

    @TypeConversion(converter = "com.accor.asa.rate.util.AsaDateConverter", type = ConversionType.CLASS)
    @RequiredFieldValidator(fieldName = "dateDebut", type = ValidatorType.SIMPLE, key = "BOD_MSG_ENTRERBEGINDATE", message = "", shortCircuit = true)
    public void setDateDebut(Date dateDebut) {
        bean.setDateDebut(dateDebut);
    }

    @TypeConversion(converter="com.accor.asa.rate.util.AsaDateConverter",type=ConversionType.CLASS )
    public Date getDateFin() {
        return bean.getDateFin();
    }

    @TypeConversion(converter = "com.accor.asa.rate.util.AsaDateConverter", type = ConversionType.CLASS)
    @RequiredFieldValidator(fieldName = "dateFin", type = ValidatorType.SIMPLE, key = "BOD_MSG_ENTRERENDDATE", message = "", shortCircuit = true)
    public void setDateFin(Date dateFin) {
        bean.setDateFin(dateFin);
    }

}
