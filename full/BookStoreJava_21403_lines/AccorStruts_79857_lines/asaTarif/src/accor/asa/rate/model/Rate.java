package com.accor.asa.rate.model;

import java.io.Serializable;
import java.util.Date;

import com.opensymphony.xwork2.conversion.annotations.ConversionType;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public interface Rate extends Serializable {

	public String getKey();

	
	public Long getIdGrille();

    @TypeConversion(converter="com.accor.asa.rate.util.AsaDateConverter",type= ConversionType.CLASS )
	public Date getDateDebut();
    @TypeConversion(converter="com.accor.asa.rate.util.AsaDateConverter",type=ConversionType.CLASS )
	public Date getDateFin();
    
}
