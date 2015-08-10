package com.accor.asa.rate.action;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.habilitation.metier.Droit;
import com.accor.asa.commun.habilitation.metier.HabilitationException;
import com.accor.asa.commun.habilitation.metier.Profil;
import com.accor.asa.commun.habilitation.metier.Role;
import com.accor.asa.commun.habilitation.metier.acces.Acces;
import com.accor.asa.commun.habilitation.metier.visibilite.AxeVisibilite;
import com.accor.asa.commun.habilitation.persistance.VisibiliteDAO;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.rate.RatesException;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.service.process.persistance.RateHabilitationDAO;
import com.accor.commun.internationalisation.TranslatorFactory;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import java.text.MessageFormat;
import java.util.*;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
@ParentPackage(value = "rateDefaultPackage")
public class MainAction extends ActionSupport implements RequestAware, SessionAware {
    /**
     * Results
     */
    public static final String WELCOME      = "no_hotel";
    public static final String CHANGE_PWD   = "changePassword";
    public static final String CONTACT      = "contact";
    public static final String NO_HOTEL     = "no_hotel";
    public static final String EXPORT       = "export";
    public static final String DISPLAY      = "display";
    public static final String RELOAD       = "reload";
    public static final String REDIRECT     = "redirect";

    /**
     * Constantes
     */
    public static final String HTTP_LISTENER    = "HTTPLISTENER";
    public static final String CONTEXTE         = "contexte";
    public static final String HOTEL            = "hotel";
    public static final String HOTEL_CONTEXT    = "hotel_ctx";
    public static final String MENU_OPTIONS     = "menuOptions";
    public static final String LANGUES          = "langues";
    public static final String REQUEST_LOCALE   = "request_locale";
    public static final String FILE_PROPERTIES  = "asaRate";

    private static final Object[] EMPTY_ARGS = new Object[0];

    protected Map<String,Object> session;
    protected Map<String,Object> request;

    /**
     * Put le Request
     *
     * @param request
     */
    public void setRequest(Map<String,Object> request) {
        this.request = request;
    }

    /**
     * Put un objet dans Request
     *
     * @param request
     */
    public void setToRequest(String key, Object obj) {
        request.put(key, obj);
    }

    /**
     * Retourne le Request
     *
     * @return
     */
    public Map getRequest() {
        return this.request;
    }

    /**
     * Retourne un objet du Request
     *
     * @return
     */
    public Object getFromRequest(String key) {
        return this.request.get(key);
    }

    /**
     * Put la Session
     *
     * @param map
     */
    public void setSession(Map<String,Object> session) {
        this.session = session;
    }

    /**
     * Put un objet dans la Session
     *
     * @param map
     */
    public void setToSession(String key, Object obj) {
        this.session.put(key, obj);
    }

    /**
     * remove an object  bound to the specified key from session
     *
     * @param key
     */
    public void removeFromSession(String key) {
        this.session.remove(key);
    }

    /**
     * Retourne la Session
     *
     * @return
     */
    public Map getSession() {
        return this.session;
    }

    /**
     * Retourne un objet de la Session
     *
     * @return
     */
    public Object getFromSession(String key) {
        return this.session.get(key);
    }

    /**
     * Netoyer la session
     */
    protected void nettoieSession() {
        this.session.clear();
    }

    /**
     * Afficher le contenu de la Session
     *
     * @param session
     */
    protected void afficherSession() {
        Set set = this.session.keySet();
        if (set != null) {
            Iterator iter = set.iterator();
            String cle;
            while (iter.hasNext()) {
                cle = (String) iter.next();
                LogCommun.debug("MainAction ", "afficherSession", cle + "==>" + getFromSession(cle));
            }
        }
    }

    /**
     * Retourne le contexte utilisateurs
     *
     * @return
     */
    protected Contexte getAsaContexte() {
        Contexte contexte = (Contexte) getFromSession(CONTEXTE);
        if (contexte == null) contexte = new Contexte("gb");
        return contexte;
    }

    /**
     * Retourne la local utilisateur
     *
     * @return
     */
    protected Locale getAsaLocale() {
        return new Locale(getAsaContexte().getCodeLangue());
    }

    public String getText(String key) {
        return TranslatorFactory.getTranslator(getAsaContexte().getCodeLangue(), true).
                getLibelle(key);
    }

    public String getText(String key, String defaultValue) {
        String text = getText(key);
        if (text == null || text.startsWith(TranslatorFactory.LIBELLE_NON_PRESENT)) {
            return defaultValue;
        }
        return text;
    }

    public String getText(String key, List args) {
        Object[] params;
        if (args != null) {
            params = args.toArray();
        } else {
            params = EMPTY_ARGS;
        }
        return MessageFormat.format(getText(key), params);
    }

    public String getText(String key, String[] args) {
        Object[] params;
        if (args != null) {
            params = args;
        } else {
            params = EMPTY_ARGS;
        }
        return MessageFormat.format(getText(key), params);
    }

    public String getText(String key, String defaultValue, List args) {
        String text = getText(key, args);
        if (text == null || text.startsWith(TranslatorFactory.LIBELLE_NON_PRESENT) && defaultValue != null) {
            MessageFormat format = new MessageFormat(defaultValue);
            format.applyPattern(defaultValue);
            Object[] params;
            if (args != null) {
                params = args.toArray();
            } else {
                params = EMPTY_ARGS;
            }
            return format.format(params);
        }
        return text;
    }

    public String getText(String key, String defaultValue, String[] args) {
        String text = getText(key, args);
        if (text == null || text.startsWith(TranslatorFactory.LIBELLE_NON_PRESENT) && defaultValue != null) {
            MessageFormat format = new MessageFormat(defaultValue);
            format.applyPattern(defaultValue);
            if (args == null) {
                return format.format(EMPTY_ARGS);
            }
            return format.format(args);
        }
        return text;
    }

    public String getText(String key, String defaultValue, String obj) {
        List<Object> args = new ArrayList<Object>(1);
        args.add(obj);
        return getText(key, defaultValue, args);
    }

    public String getText(String key, String defaultValue, List args, ValueStack stack) {
        return getText(key, defaultValue, args);
    }

    public String getText(String key, String defaultValue, String[] args, ValueStack stack) {
        return getText(key, defaultValue, Arrays.asList(args));
    }

    public ResourceBundle getTexts(String bundleName) {
        return TranslatorFactory.getTranslator(getAsaContexte().getCodeLangue(), true).
                getBundle();
    }

    public ResourceBundle getTexts() {
        return TranslatorFactory.getTranslator(getAsaContexte().getCodeLangue(), true).
                getBundle();
    }

    public String translateRateException(RatesException ex) {
        String msg = null;
        String key = ex.getKey();
        if (StringUtils.isNotBlank(key))
            msg = getText(key);
        if (msg == null || msg.startsWith(TranslatorFactory.LIBELLE_NON_PRESENT)) {
            if (ex.getMessage() != null)
                msg = ex.getMessage();
            else {
                if (ex.getCause() != null)
                    msg = ex.getCause().getMessage();
            }
        }
        return msg;
    }

    //===========================================================================================
    //=============                  HABILITATION DES ECRANS         ============================
    //===========================================================================================
    /**
     * Retourne le droit d'acces à un écran
     * @param codeEcran
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     */
    public Droit getDroit ( final String codeEcran,
                            final Contexte contexte )
            throws RatesTechnicalException {
        try {
            Droit droit = PoolCommunFactory.getInstance().getHabilitationFacade().getDroit( codeEcran, contexte);
            if(Log.isDebug)
                Log.debug( "MainAction", "getDroit", " >> droit : " + droit );
            if(droit.equals(Droit.INTERDIT)) {
                StringBuffer sb = new StringBuffer();
                sb.append("Ecran ");
                sb.append(codeEcran);
                sb.append(" interdit pour le profil ");
                sb.append(contexte.getCodeProfil());
                throw new HabilitationException(sb.toString());
            }
            return droit;
        } catch(IncoherenceException e) {
            throw new RatesTechnicalException(e);
        } catch(TechnicalException e) {
            throw new RatesTechnicalException(e);
        } catch(HabilitationException e) {
            throw new RatesTechnicalException(e.getMessage());
        }
    }

    /**
     * Vérifier si l'ecran est en mode lecture seule
     * @param codeEcran
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     */
    protected boolean isReadOnly(final String codeEcran,
                                 final Contexte contexte)
    		throws RatesTechnicalException {
        Droit droit = getDroit( codeEcran, contexte );
        return droit.equals(Droit.LECTURE);
    }

    /**
     * Retourne le droit d'une regle
     * @param codeEcran
     * @param codeHotel
     * @param idPeriodeValidite
     * @param idFamilleTarif
     * @param idStatut
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     * @throws HabilitationException
     */
  protected Droit getDroitWorkflow(final String codeEcran,
                                   final String codeHotel,
                                   final int idPeriodeValidite,
                                   final int idFamilleTarif,
                                   final int idStatut,
                                   final Contexte contexte)
  			throws RatesTechnicalException, HabilitationException {
      try {
          Droit autorisation = null;
          String codeGroupeEcran =  PoolCommunFactory.getInstance().getHabilitationFacade().getCodeGroupeEcran( codeEcran );
          if(  contexte.getProfil() == null )
              throw new HabilitationException( "Aucun profil associe a cet utilisateur : " + contexte.getCodeUtilisateur() );
          Profil profil = contexte.getProfil();
          if(  profil.getRole() == null )
              throw new HabilitationException( "Aucun role associe a ce profil : " + profil.getCode() );
          Role role = profil.getRole();
          List<AxeVisibilite> axes = profil.getAxesTarif();
          if( axes != null ) {
              AxeVisibilite av;
              Acces acces;
              Integer codeRegle;
              Droit d;
              for( int i=0, nbAxes=axes.size(); i<nbAxes; i++ ) {
                  // Recuperer le code de la regle a appliquer
                  av = axes.get(i);
                  acces = VisibiliteDAO.getInstance().getAcces( role.getCode(), av.getCode(), codeGroupeEcran, contexte );
                  if (acces != null) {
                      codeRegle = acces.getRegle();
                      if( codeRegle != null ) {
                          if( Log.isDebug )
                              Log.debug( "WorkflowBean", "getDroitWorkflow", "coderegle : " + codeRegle );
                          d = RateHabilitationDAO.getInstance().
                                  verifierRegle(codeRegle,codeHotel,idPeriodeValidite,idFamilleTarif, idStatut, role.getCode(), contexte );
                          if( Droit.ECRITURE.equals( d ) )
                              return d;
                          else if( autorisation == null || autorisation.compareTo( d ) < 0 )
                                  autorisation = d;
                      }
                  }
              }
          }
          if(Log.isDebug)
              Log.debug( "MainAction", "getDroit", " >> droitWf : " + autorisation);
          return autorisation;
      } catch(IncoherenceException e) {
          throw new RatesTechnicalException(e);
      } catch(TechnicalException e) {
          throw new RatesTechnicalException(e);
      }
  }
    /**
     * Retourne le droit d'acces à un écran
     * @param codeEcran
     * @param codeHotel
     * @param idPeriodeValidite
     * @param idFamilleTarif
     * @param idStatut
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     */
    protected Droit getDroit (  final String codeEcran,
                                final String codeHotel,
                                final int idPeriodeValidite,
                                final int idFamilleTarif,
                                final int idStatut,
                                final Contexte contexte )
            throws RatesTechnicalException {
        try {
            Droit droit = getDroit(codeEcran, contexte);
            Droit droitWf = getDroitWorkflow( codeEcran, codeHotel, idPeriodeValidite, idFamilleTarif, idStatut, contexte );
            if(Log.isDebug)
                Log.debug( "MainAction", "getDroit", " >> droit : " + droit + " droitWf="+droitWf );
            if (droitWf == null) {
                return droit;
            } else {
                return droitWf;
            }
        } catch(HabilitationException e) {
            throw new RatesTechnicalException(e.getMessage());
        }
    }
    /**
     * Vérifier si l'ecran est en mode lecture seule
     * @param codeEcran
     * @param codeHotel
     * @param idPeriodeValidite
     * @param idFamilleTarif
     * @param idStatut
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     */
    protected boolean isReadOnly(final String codeEcran,
                                 final String codeHotel,
                                 final int idPeriodeValidite,
                                 final int idFamilleTarif,
                                 final int idStatut,
                                 final Contexte contexte)
    		throws RatesTechnicalException {
        Droit droit = getDroit( codeEcran, codeHotel, idPeriodeValidite, idFamilleTarif, idStatut, contexte );
        return droit.equals(Droit.LECTURE);
    }
}
