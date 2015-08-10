package com.accor.asa.rate.form;

import com.accor.asa.rate.model.Rate;
import com.accor.asa.rate.model.RateBean;

public abstract class  RateFormBean implements Rate{
    private  String[] weekDays;
	public abstract RateBean getBean();

    public String[] getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(String[] weekDays) {
        this.weekDays = weekDays;
    }
}
