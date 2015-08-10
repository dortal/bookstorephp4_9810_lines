package com.accor.asa.rate.decorator;

import java.util.Date;

import com.accor.asa.rate.model.BlackOutDatesBean;
import com.accor.asa.rate.model.Rate;
import com.opensymphony.xwork2.conversion.annotations.ConversionType;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class BlackOutDatesDecorator  implements Rate {

    private BlackOutDatesBean bean;

    public BlackOutDatesDecorator(BlackOutDatesBean bean) {
		if (bean == null)
			throw new RuntimeException("The rate bean for a RateBeanDecorator can not be null");
		this.bean = bean;
	}

    public String getKey() {
        return bean.getKey();
    }

    public Long getIdGrille() {
        return bean.getIdGrille();
    }

    @TypeConversion(converter="com.accor.asa.rate.util.AsaDateConverter",type= ConversionType.CLASS )
    public Date getDateDebut() {
        return bean.getDateDebut();
    }

    @TypeConversion(converter="com.accor.asa.rate.util.AsaDateConverter",type=ConversionType.CLASS )
    public Date getDateFin() {
        return bean.getDateFin();
    }

}
