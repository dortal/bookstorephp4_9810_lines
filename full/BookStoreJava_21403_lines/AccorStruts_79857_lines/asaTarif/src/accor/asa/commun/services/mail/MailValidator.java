package com.accor.asa.commun.services.mail;

import org.apache.commons.validator.GenericValidator;

import com.accor.asa.commun.log.LogCommun;

/**
 * @author <a href="mailto:LEHYARIC_Gildas@accor-hotels.com">lehyaric</a>
 * @version $Id: MailValidator.java,v 1.3 2005/06/08 12:00:10 gle Exp $
 */
public class MailValidator implements EmailAddressValidator {

    public boolean validate(String email) {
        LogCommun.debug("MailValidator","validate", "validate "+email);
        return GenericValidator.isEmail(email);
    }

}
