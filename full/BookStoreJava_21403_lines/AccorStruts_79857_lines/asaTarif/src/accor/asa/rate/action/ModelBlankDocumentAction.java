package com.accor.asa.rate.action;

import com.accor.asa.commun.metier.Element;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.model.ModelBlankDocument;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletDispatcherResult;

import java.util.List;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
@Results({
    @Result(name = Action.SUCCESS, type = ServletDispatcherResult.class, value = "/modeles/modeles.jsp"),
    @Result(name = Action.ERROR,   type = ServletDispatcherResult.class, value = "/error.jsp")
})
public class ModelBlankDocumentAction  extends MainAction {

    private String codeLangue;
    private String fileUrlSelected;
    private List<Element> langues;
    private List<ModelBlankDocument> blankDocuments;



    /**
     * La methode execute par defaut de l'action
     *
     * @return
     */
    public String execute() {
        try {
            if (StringUtils.isBlank(getCodeLangue()))
                setCodeLangue("GB");
            setBlankDocuments(ModelBlankDocument.getModelBlankDocuments(getCodeLangue()));
            List<Element> myLangues = (List<Element>)getFromSession(LANGUES);
            if (myLangues == null)
                myLangues = PoolCommunFactory.getInstance().getCommunUtilsFacade().getLanguages(getAsaContexte());
            setLangues(myLangues);
            return Action.SUCCESS;
        } catch (Exception e) {
            Log.critical(getAsaContexte().getCodeUtilisateur(), "ModelBlankDocumentAction", "execute", e.getMessage());
            addActionError(e.getMessage());
            return Action.ERROR;
        }
    }
    
    //====================  GETTER AND SETTER ===================================

    public String getCodeLangue() {
        return codeLangue;
    }

    public void setCodeLangue(String codeLangue) {
        this.codeLangue = codeLangue;
    }

    public String getFileUrlSelected() {
        return fileUrlSelected;
    }

    public void setFileUrlSelected(String fileUrlSelected) {
        this.fileUrlSelected = fileUrlSelected;
    }

    public List<Element> getLangues() {
        return langues;
    }

    public void setLangues(List<Element> langues) {
        this.langues = langues;
    }

    public List<ModelBlankDocument> getBlankDocuments() {
        return blankDocuments;
    }

    public void setBlankDocuments(List<ModelBlankDocument> blankDocuments) {
        this.blankDocuments = blankDocuments;
    }
}
