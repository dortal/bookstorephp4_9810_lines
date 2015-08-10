package com.accor.asa.commun.process;

public class IncoherenceException extends Exception {

    private String message;
    private Exception exceptionEncapsulee;

    public IncoherenceException(String message) {
        super(message);
        this.message = message;
    }

    public IncoherenceException(Exception e) {
        setExceptionEncapsulee(e);
    }

    public String getMessage() {
        if (this.message == null)
            return super.getMessage();
        else
            return this.message;
    }

    public java.lang.Exception getExceptionEncapsulee() {
        return exceptionEncapsulee;
    }

    private void setExceptionEncapsulee(Exception newExceptionEncapsulee) {
        exceptionEncapsulee = newExceptionEncapsulee;
    }

}