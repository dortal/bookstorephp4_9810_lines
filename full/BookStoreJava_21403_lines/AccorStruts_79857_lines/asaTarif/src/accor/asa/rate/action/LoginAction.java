package com.accor.asa.rate.action;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.habilitation.metier.AsaModule;
import com.accor.asa.commun.habilitation.metier.Profil;
import com.accor.asa.commun.habilitation.metier.optionmenu.GroupeOptionMenu;
import com.accor.asa.commun.habilitation.metier.optionmenu.OptionMenu;
import com.accor.asa.commun.habilitation.process.HabilitationFacade;
import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.commun.metier.Address;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.Element;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.user.metier.User;
import com.accor.asa.commun.user.process.UserFacade;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.common.SessionListener;
import com.accor.asa.rate.util.HotelContextManager;
import com.accor.asa.rate.util.RateScrean.HotelContext;
import com.accor.commun.internationalisation.Translator;
import com.accor.commun.internationalisation.TranslatorFactory;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;
import org.apache.struts2.dispatcher.ServletDispatcherResult;
import org.apache.struts2.interceptor.validation.SkipValidation;

import java.util.*;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
@Results({
    @Result(name = Action.SUCCESS,          type = ServletActionRedirectResult.class, value = "aide!displayRecommandation.action"),
    @Result(name = MainAction.REDIRECT,     type = ServletDispatcherResult.class, value = "/redirect.jsp"),
    @Result(name = Action.INPUT,            type = ServletDispatcherResult.class, value = "/login.jsp"),
    @Result(name = MainAction.CHANGE_PWD,   type = ServletDispatcherResult.class, value = "/changePassword.jsp"),
    @Result(name = MainAction.CONTACT,      type = ServletDispatcherResult.class, value = "/contacts.jsp"),
    @Result(name = Action.ERROR,            type = ServletDispatcherResult.class, value = "/error.jsp")
})
public class LoginAction extends MainAction implements Preparable {


    protected String login;
    protected String password;
    protected String codeLangue;
    protected String codeHotel;
    protected String url;
    protected List<Element> langues;

    /**
     * Préparer l'action
     *
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void prepare() throws Exception {
        langues = (List<Element>) getFromSession(LANGUES);
        if (langues == null) {
            langues = PoolCommunFactory.getInstance().getCommunUtilsFacade().getLanguages(new Contexte(""));
            setToSession(LANGUES, langues);
        }
    }

    /**
     * Acces to the login form
     *
     * @return
     */
    @SkipValidation
    public String execute() {
        return Action.INPUT;
    }

    /**
     * Construire la liste des option a afficher et les placer en session
     *
     * @param contexte
     * @throws Exception
     */
    private void setMenuOptions(Contexte contexte) throws Exception {
        Map<GroupeOptionMenu, List<OptionMenu>> allOptions = PoolCommunFactory.getInstance().getHabilitationFacade().
                getAllOptionMenuTarif(contexte);
        Set<String> authorizedOptions = PoolCommunFactory.getInstance().getHabilitationFacade().
                getOptionsMenuRole(contexte.getCodeRole(), contexte);
        Map<GroupeOptionMenu, List<OptionMenu>> mesOptions = new HashMap<GroupeOptionMenu, List<OptionMenu>>();
        if (allOptions != null && authorizedOptions != null) {
            List<OptionMenu> optionMenus;
            OptionMenu optionMenu;
            for (Map.Entry<GroupeOptionMenu, List<OptionMenu>> entry : allOptions.entrySet()) {
                optionMenus = entry.getValue();
                for (int i = 0, size = optionMenus.size(); i < size; i++) {
                    optionMenu = optionMenus.get(i);
                    if (authorizedOptions.contains(optionMenu.getCodeOption().toString())) {
                        if (!mesOptions.keySet().contains(entry.getKey()))
                            mesOptions.put(entry.getKey(), new ArrayList<OptionMenu>());
                        mesOptions.get(entry.getKey()).add(optionMenu);
                    }
                }
            }

        }
        setToSession(MENU_OPTIONS, mesOptions);
    }

    /**
     * Connexion
     *
     * @return
     */
    public String connexion() {
        Contexte contexte = new Contexte();
        contexte.setCodeUtilisateur(login);
        try {
            UserFacade uf = PoolCommunFactory.getInstance().getUserFacade();
            HabilitationFacade hf = PoolCommunFactory.getInstance().getHabilitationFacade();
            if (!uf.checkPassword(login, password, contexte)) {
                //Login or password incorrect
                this.addActionError(getText("COM_LOGIN_MSG_CHECKPROFIL"));
                return Action.INPUT;
            } else {
                if (PoolCommunFactory.getInstance().getUserFacade().isDefaultPassword(login, password, contexte)) {
                    //Password default
                    User user = uf.getUser(login, contexte);
                    Translator translator = TranslatorFactory.getTranslator(user.getLanguageCode(), true);
                    this.addActionError(translator.getLibelle("LOGIN_CHANGE_DEFAULT_PASSWORD"));
                    return CHANGE_PWD;
                } else {
                    //Authentification réussie
                    User user = uf.getUser(login, contexte);
                    Profil p = hf.getProfil(user.getProfileId(), contexte);
                    if (p.getRole() != null) {
                        contexte = initialiserContexteUtilisateur(user, p);
                        if (StringUtils.isNotBlank(codeLangue))
                            contexte.setCodeLangue(codeLangue);
                        else
                            contexte.setCodeLangue(user.getLanguageCode());
                        String idHotel = StringUtils.isNotBlank(user.getHotelId())?user.getHotelId():codeHotel;
                        contexte.setCodeHotel(idHotel);
                        HotelContext hc = HotelContext.DEFAULT_CONTEXT;
                        if (StringUtils.isNotBlank(idHotel)) {
                            Hotel ho = PoolCommunFactory.getInstance().getHotelFacade().getHotel( idHotel, contexte );
                            if (ho != null) {
                                contexte.getUtilisateurValue().setHotelName(ho.getName());
                                contexte.setCodePays(ho.getCountryId());
                                contexte.setCodeZoneVente(ho.getSaleZoneCode());
                                setToSession(HOTEL, ho);
                                hc = HotelContextManager.getHotelContext(ho);

                            } else {
                                contexte.getUtilisateurValue().setHotelName("not found");
                                contexte.setCodeLangue("GB");
                                ho = new Hotel();
                                Address addr = ho.getAddress();
                                addr.setCity("not found");
                                addr.setCountryName("not found");
                                ho.setAddress( addr );
                                Element chain = ho.getChain();
                                chain.setLibelle("not found");
                                ho.setChain(chain);
                                setToSession(HOTEL, ho);
                            }
                        }
                        setToRequest(REQUEST_LOCALE, contexte.getCodeLangue().toLowerCase());
                        setToSession(CONTEXTE, contexte);
                        setToSession(HOTEL_CONTEXT, hc);
                        setMenuOptions(contexte);
                        return StringUtils.isBlank(url)?Action.SUCCESS:MainAction.REDIRECT;
                    } else {
                        addActionError(getText("COM_LOGIN_MSG_USERNOTAUTHORIZED"));
                        return Action.INPUT;
                    }
                }
            }
        } catch (Exception e) {
            StringBuffer sb = new StringBuffer();
            sb.append("Action : connexion");
            sb.append(" , Login : ");
            sb.append(login);
            sb.append(" , Password : ");
            sb.append(password);
            sb.append(" , Langue : ");
            sb.append(codeLangue);
            addActionError("Unknown hotel : " + e.getMessage());
            return Action.ERROR;
        }
    }

    /**
     * Got to change password
     *
     * @return
     */
    public String goChangePassword() {
        Contexte contexte = new Contexte();
        contexte.setCodeUtilisateur(login);
        try {
            UserFacade uf = PoolCommunFactory.getInstance().getUserFacade();
            if (!uf.checkPassword(login, password, contexte)) {
                //Login or password incorrect
                this.addActionError(getText("COM_LOGIN_MSG_CHECKPROFIL"));
                return Action.INPUT;
            } else {
                //Change password
                return CHANGE_PWD;
            }
        } catch (Exception e) {
            StringBuffer sb = new StringBuffer();
            sb.append("Action : goChangePassword");
            sb.append(" , Login : ");
            sb.append(login);
            sb.append(" , Password : ");
            sb.append(password);
            sb.append(" , Langue : ");
            sb.append(codeLangue);
            addActionError("Unknown hotel : " + e.getMessage());
            return Action.ERROR;
        }
    }

    /**
     * Got to change password
     *
     * @return
     */
    @SkipValidation
    public String goContacts() {
        return MainAction.CONTACT;
    }

    /**
     * Acces to the login form
     *
     * @return
     */
    @SkipValidation
    public String disconnect() {
        String login = "DECONNEXION";
        try {
            Contexte contexte = (Contexte) getFromSession(CONTEXTE);
            if (contexte != null) {
                login = contexte.getCodeUtilisateur();
                // getSession().remove(HTTP_LISTENER);
                getSession().remove(CONTEXTE);
                getSession().remove(HOTEL);
                getSession().remove(HOTEL_CONTEXT);
                // Invalidate la session
                // if (session instanceof org.apache.struts2.dispatcher.SessionMap)
                //    ((org.apache.struts2.dispatcher.SessionMap) session).invalidate();
            }
            return Action.INPUT;
        } catch (Exception e) {
            Log.major(login, "LoginAction", "disconnect", e.getMessage(), e);
            addActionError(e.getMessage());
            return Action.ERROR;
        }
    }

    /**
     * Initialiser le contexte utilisateur
     *
     * @param session
     * @param user
     * @param profil
     * @return
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    private Contexte initialiserContexteUtilisateur(User user, Profil profil)
            throws TechnicalException, IncoherenceException {

        HabilitationFacade hFacade = PoolCommunFactory.getInstance().getHabilitationFacade();
        Contexte contexte = new Contexte();
        contexte.setUtilisateurValue(user);
        contexte.setProfil(profil);
        hFacade.connexion(user.getLogin(), AsaModule.NAME_APP_TARIF, contexte);
        contexte.setTimestampHotel(hFacade.getVerrouOptimisteHotel(user.getHotelId(), contexte));
        setToSession(HTTP_LISTENER, new SessionListener(user.getLogin()));
        setToSession(CONTEXTE, contexte);
        return contexte;
    }

    @RequiredStringValidator(fieldName = "login", type = ValidatorType.FIELD, message = "", key = "COM_LOGIN_MSG_LOGINREQUIRED", shortCircuit = true)
    //@StringLengthFieldValidator(fieldName = "login", type = ValidatorType.FIELD, message = "", key = "COM_LOGIN_MSG_LOGINSIZE", trim = true, minLength = "1", maxLength = "15", shortCircuit = true)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @RequiredStringValidator(fieldName = "password", type = ValidatorType.SIMPLE, message = "", key = "COM_LOGIN_MSG_PASSWORDREQUIRED", shortCircuit = true)
    //@StringLengthFieldValidator(fieldName = "password", type = ValidatorType.SIMPLE, message = "", key = "COM_LOGIN_MSG_PASSWORDSIZE", trim = true, minLength = "6", maxLength = "12", shortCircuit = true)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCodeLangue() {
        return codeLangue;
    }

    public void setCodeLangue(String codeLangue) {
        this.codeLangue = codeLangue;
    }

    public String getCodeHotel() {
        return codeHotel;
    }

    public void setCodeHotel(String codeHotel) {
        this.codeHotel = codeHotel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Element> getLangues() {
        return langues;
    }

    public void setLangues(List<Element> langues) {
        this.langues = langues;
    }
}
