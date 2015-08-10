package com.accor.asa.rate.action;

import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletDispatcherResult;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
@Results({
    @Result(name = Action.SUCCESS, type = ServletDispatcherResult.class, value = "login.jsp"),
    @Result(name = Action.INPUT, type = ServletDispatcherResult.class, value = "changePassword.jsp"),
    @Result(name = Action.ERROR, type = ServletDispatcherResult.class, value = "error.jsp")
})
public class ChangePassword extends LoginAction {

    protected String newPassword;
    protected String confirmPassword;


    /**
     * Got to change password
     *
     * @return
     */
    public String execute() {
        Contexte contexte = new Contexte();
        contexte.setCodeUtilisateur(login);
        try {
            if (!StringUtils.equals(newPassword, confirmPassword)) {
                addActionError(getText("COM_LOGIN_MSG_COMPAREPASSWORD"));
                return Action.INPUT;
            } else {
                //Changement de password et retour à la page de login
                PoolCommunFactory.getInstance().getUserFacade().
                        setPassword(login, newPassword, contexte);
                return Action.SUCCESS;
            }
        } catch (Exception e) {
            StringBuffer sb = new StringBuffer();
            sb.append("Action : doChangePassword");
            sb.append(" , Login : ");
            sb.append(login);
            sb.append(" , Password : ");
            sb.append(password);
            sb.append(" , Langue : ");
            sb.append(codeLangue);
            addActionError("Error : " + e.getMessage());
            return Action.ERROR;
        }
    }

    @RequiredStringValidator(fieldName = "newPassword", type = ValidatorType.FIELD, message = "", key = "COM_LOGIN_MSG_NEWPASSWORDREQUIRED", shortCircuit = true)
    @StringLengthFieldValidator(fieldName = "newPassword", type = ValidatorType.FIELD, message = "", key = "COM_LOGIN_MSG_NEWPASSWORDSIZE", trim = true, minLength = "1", maxLength = "15", shortCircuit = true)
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @RequiredStringValidator(fieldName = "confirmPassword", type = ValidatorType.FIELD, message = "", key = "COM_LOGIN_MSG_CONFIRMPASSWORDREQUIRED", shortCircuit = true)
    @StringLengthFieldValidator(fieldName = "confirmPassword", type = ValidatorType.FIELD, message = "", key = "COM_LOGIN_MSG_CONFIRPASSWORDSIZE", trim = true, minLength = "1", maxLength = "15", shortCircuit = true)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
