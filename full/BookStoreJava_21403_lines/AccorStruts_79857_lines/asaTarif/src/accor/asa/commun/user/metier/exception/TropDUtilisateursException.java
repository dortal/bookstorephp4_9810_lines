package com.accor.asa.commun.user.metier.exception;

// import com.accor.commun.metier.BusinessException;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

@SuppressWarnings("serial")
public class TropDUtilisateursException extends Exception {  // extends BusinessException {

    public TropDUtilisateursException(String message) {
        super(message);
    }
}