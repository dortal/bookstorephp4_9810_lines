package com.accor.asa.rate.util;

import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.rate.util.RateScrean.HotelContext;

public class HotelContextManager {

	public static HotelContext getHotelContext(Hotel hotel) {

		if(hotel==null)
			return HotelContext.DEFAULT_CONTEXT;
		if(hotel.getChainCode()!= null && hotel.getChainCode().equals("ADG"))
			return HotelContext.ADAGIO_CONTEXT;
        if("1".equals(hotel.getFlagAsaTarifLoisir()))
            return HotelContext.LEISURE_CONTEXT;
        return HotelContext.DEFAULT_CONTEXT;
	}
}
