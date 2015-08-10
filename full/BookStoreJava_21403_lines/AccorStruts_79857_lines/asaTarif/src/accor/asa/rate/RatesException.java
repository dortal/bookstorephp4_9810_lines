package com.accor.asa.rate;

import com.accor.asa.commun.AsaException;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class RatesException  extends Exception implements AsaException{

    private String key;


    public RatesException() {
    }

    public RatesException(String defaultMessage) {
        super(defaultMessage);
    }

    public RatesException(Throwable throwable) {
        super(throwable);
    }

    public RatesException(String defaultMessage, String key) {
        super(defaultMessage);
        this.key = key;
    }

    public RatesException(Throwable throwable, String defaultMessage,  String key) {
        super(defaultMessage, throwable);
        this.key = key;
    }

    public RatesException(Throwable throwable, String key) {
        super(throwable);
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
