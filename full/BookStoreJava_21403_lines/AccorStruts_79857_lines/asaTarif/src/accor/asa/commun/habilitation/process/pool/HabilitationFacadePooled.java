package com.accor.asa.commun.habilitation.process.pool;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.habilitation.metier.AsaModule;
import com.accor.asa.commun.habilitation.metier.Droit;
import com.accor.asa.commun.habilitation.metier.HabilitationException;
import com.accor.asa.commun.habilitation.metier.Profil;
import com.accor.asa.commun.habilitation.metier.Role;
import com.accor.asa.commun.habilitation.metier.acces.Acces;
import com.accor.asa.commun.habilitation.metier.acces.AccesRoleMap;
import com.accor.asa.commun.habilitation.metier.ecran.GroupeEcran;
import com.accor.asa.commun.habilitation.metier.optionmenu.GroupeOptionMenu;
import com.accor.asa.commun.habilitation.metier.optionmenu.OptionMenu;
import com.accor.asa.commun.habilitation.metier.optionmenu.OptionMenuRole;
import com.accor.asa.commun.habilitation.metier.optionmenu.OptionsMenuRoleMap;
import com.accor.asa.commun.habilitation.metier.visibilite.AxeVisibilite;
import com.accor.asa.commun.habilitation.metier.visibilite.TypeVisibilite;
import com.accor.asa.commun.habilitation.metier.visibilite.Visibilite;
import com.accor.asa.commun.habilitation.persistance.EcranDAO;
import com.accor.asa.commun.habilitation.persistance.HabilitationDAO;
import com.accor.asa.commun.habilitation.persistance.HabilitationQBEDAO;
import com.accor.asa.commun.habilitation.persistance.ProfilDAO;
import com.accor.asa.commun.habilitation.persistance.RoleDAO;
import com.accor.asa.commun.habilitation.persistance.VisibiliteDAO;
import com.accor.asa.commun.habilitation.process.HabilitationFacade;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.TimestampException;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.user.metier.UserLight;
import com.accor.asa.vente.commun.habilitation.metier.Habilitation;
import com.accor.asa.vente.commun.habilitation.metier.ObjetMaitreHabilitation;
import com.accor.asa.vente.commun.habilitation.metier.TypeObjetMaitreHabilitation;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 16 juin 2005
 * Time: 16:19:09
 * To change this template use File | Settings | File Templates.
 */
public class HabilitationFacadePooled implements HabilitationFacade {

    /**
     * Constructeur
     */
    public HabilitationFacadePooled() {
    }

    /**
     * récupère la pondération pour un couple (axe, groupe ecran),
     * @param contexte
     * @param codeAxe
     * @param codeGroupeEcran
     * @return la pondération pour un coupe (axe, groupe ecran)
     * @throws TechnicalException
     */
    public Integer getPonderationPourAxe( final String codeAxe, final String codeGroupeEcran, 
    		final Contexte contexte ) throws TechnicalException {
        HabilitationDAO hDao = HabilitationDAO.getInstance();
        return hDao.getPonderationPourAxe( codeAxe, codeGroupeEcran, contexte );
    }

    /***
     * Renvoie tous les roles de vente
     * @param contexte
     * @return List<Role>
     * @throws TechnicalException
     */
    public List<Role> getRolesVente( final Contexte contexte ) throws TechnicalException {
    	return RoleDAO.getInstance().getRolesVente( contexte );
    }
    
    /***
     * Renvoie tous les roles qui n'appartiennent pas a Vente
     * @param contexte
     * @return List<Role>
     * @throws TechnicalException
     */
    public List<Role> getOthersRoles( final Contexte contexte ) throws TechnicalException {
    	return RoleDAO.getInstance().getOthersRoles( contexte );
    }

    /***
     * Renvoie l'ensemble des roles du systeme 
     * @param contexte
     * @return List<Role>
     * @throws TechnicalException
     */
    public List<Role> getRolesAdmin( final Contexte contexte ) throws TechnicalException {
    	return RoleDAO.getInstance().getRolesAdmin(contexte);
    }
    
    /***
     * Renvoie l objet Role associe au codeRole
     * @param codeRole
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public Role getRole( final String codeRole, final Contexte contexte ) 
    		throws HabilitationException, TechnicalException {
    	
    	if( codeRole == null )
    		return null;

    	List<Role> rolesVente = getRolesVente(contexte);
    	if( rolesVente != null ) {
    		for( int i=0, size=rolesVente.size(); i<size; i++ ) {
    			if( StringUtils.equalsIgnoreCase( codeRole , ( (Role) rolesVente.get(i) ).getCode() ) )
    				return (Role) rolesVente.get(i);
    		}
    	}
    	
    	List<Role> otherRoles = getOthersRoles(contexte);
    	if( otherRoles != null ) {
    		for( int i=0, size=otherRoles.size(); i<size; i++ ) {
    			if( StringUtils.equalsIgnoreCase( codeRole , ( (Role) otherRoles.get(i) ).getCode() ) )
    				return (Role) otherRoles.get(i);
    		}
    	}

        throw new HabilitationException("Code role inconnu : " + codeRole );
    }
    
    
    /***
     * Methode d'enregistrement de la connexion Utilisateur
     * @param login
     * @param module
     * @param contexte
     * @throws TechnicalException
     */
    public void connexion( final String login, final String module, final Contexte contexte ) 
    		throws TechnicalException {
    	HabilitationDAO.getInstance().connexion( login, module, contexte );
    }

    /***
     * Methode de suppression de la log de la connexion Utilisateur
     * @param login
     * @param module
     * @param contexte
     * @throws TechnicalException
     */
    public void deconnexion( final String login, final String module, final Contexte contexte ) 
    		throws TechnicalException {
    	HabilitationDAO.getInstance().deconnexion( login, module, contexte );
    }

    /**
     * Vérifie si l'estampille n'a pas changé pour le compte passé en paramètre (Verrouillage optimiste)
     *
     * @param codeCompte
     * @param timestamp
     * @return nouveau timestamp
     * @throws TimestampException si le timestamp a changé
     * @throws TechnicalException
     */
    public void checkVerrouOptimisteCompte( final Integer codeCompte, final String timestamp, 
    		final Contexte contexte ) throws TechnicalException, TimestampException {
    	HabilitationDAO.getInstance().checkVerrouOptimisteCompte( codeCompte, timestamp, contexte );
    }

    /**
     * Vérifie si l'estampille n'a pas changé pour le compte passé en paramètre (Verrouillage optimiste)
     *
     * @param codeContact
     * @param timestamp
     * @return nouveau timestamp
     * @throws TimestampException si le timestamp a changé
     * @throws TechnicalException
     */
    public void checkVerrouOptimisteContact( final Integer codeContact, final String timestamp,
    		final Contexte contexte ) throws TechnicalException, TimestampException {
    	HabilitationDAO.getInstance().checkVerrouOptimisteContact( codeContact, timestamp, contexte );    	
    }
    
    /**
     * Vérifie si l'estampille n'a pas changé pour le compte passé en paramètre (Verrouillage optimiste)
     *
     * @param codeDossier
     * @param timestamp
     * @return nouveau timestamp
     * @throws TimestampException si le timestamp a changé
     * @throws TechnicalException
     */
    public void checkVerrouOptimisteDossier( final Long codeDossier, final String timestamp,
    		final Contexte contexte ) throws TechnicalException, TimestampException {
    	HabilitationDAO.getInstance().checkVerrouOptimisteDossier( codeDossier, timestamp, contexte );
    }
    
    /**
     * Renvoie l'estampille du compte passé en paramètre (Verrouillage optimiste)
     * Doit être appelée avant la lecture en base
     *
     * @param codeCompte
     * @return
     * @throws TechnicalException
     */
    public String getVerrouOptimisteCompte( final Integer codeCompte, final Contexte contexte ) 
    		throws TechnicalException {
    	return HabilitationDAO.getInstance().getVerrouOptimisteCompte( codeCompte, contexte );
    }
    
    /**
     * Renvoie l'estampille du contact passé en paramètre (Verrouillage optimiste)
     * Doit être appelée avant la lecture en base
     *
     * @param codeContact
     * @return
     * @throws TechnicalException
     */
    public String getVerrouOptimisteContact( final Integer codeContact, final Contexte contexte ) 
    		throws TechnicalException {
    	return HabilitationDAO.getInstance().getVerrouOptimisteContact( codeContact, contexte );
    }
    
    /**
     * Renvoie l'estampille du dossier passé en paramètre (Verrouillage optimiste)
     * Doit être appelée avant la lecture en base
     *
     * @param codeDossier
     * @return
     * @throws TechnicalException
     */
    public String getVerrouOptimisteDossier( final Long codeDossier, final Contexte contexte ) 
    		throws TechnicalException {
    	return HabilitationDAO.getInstance().getVerrouOptimisteDossier( codeDossier, contexte );
    }
    
    /**
     * Renvoie l'estampille de l'hotel passé en paramètre (Verrouillage optimiste)
     * Doit être appelée avant la lecture en base
     * @param codeDossier
     * @return
     * @throws TechnicalException
     */
	public String getVerrouOptimisteHotel( final String codeHotel, final Contexte contexte ) 
			throws TechnicalException{
		return HabilitationDAO.getInstance().getVerrouOptimisteHotel( codeHotel, contexte );
	}
	
    /***
     * récupère le droit possible pour un trio (role, axe, groupe ecran)
     * <br> voir table acces
     * @param codeRole
     * @param codeAxe
     * @param codeGroupeEcran
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public Droit getDroit( final String codeRole, final String codeAxe, 
    		final String codeGroupeEcran, final Contexte contexte ) throws TechnicalException {
    	
    	Acces acces = VisibiliteDAO.getInstance().getAcces( codeRole, codeAxe, codeGroupeEcran, contexte );
    	if( acces != null )
    		return acces.getDroit();
    	
    	return null;
    }
    
    /***
     * Récupère de droit max pour un ecran et le profil/role contenu dans le contexte
     * @param codeEcran
     * @param contexte
     * @return
     * @throws HabilitationException
     * @throws IncoherenceException
     * @throws TechnicalException
     */
    public Droit getDroit( final String codeEcran, final Contexte contexte ) 
    		throws HabilitationException, IncoherenceException, TechnicalException {
	
    	List<AxeVisibilite> axes = getListeAxesProfilFromCache( contexte.getCodeProfil(), contexte );
    	String codeRole = contexte.getCodeRole();
    	String codeGroupeEcran = getCodeGroupeEcran( codeEcran );
    	Droit d = Droit.INTERDIT;
    	if( axes != null ) {
    		AxeVisibilite av;
    		for( int i=0, nbAxes=axes.size(); i<nbAxes; i++ ) {
    			av = (AxeVisibilite) axes.get(i);
    			if( ! av.isAxeVente() ) {
    				d = getDroit( codeRole, av.getCode(),codeGroupeEcran, contexte );
    				if( ( d == null ) || ( d.equals( Droit.ECRITURE ) ) )
    					return Droit.ECRITURE;
    			}
    		}
		}
	  return d;
    }

    /**
     * Renvoie la liste des axes pour le profil demandé en se basant sur le cache
     * si possible. Sinon, effectue la lecture en base et met à jour le cache.
     * @param contexte
     * @param codeProfil
     * @return List<AxeVisibilite>
     * @throws TechnicalException
     */
    public List<AxeVisibilite> getListeAxesProfilFromCache( final String codeProfil, final Contexte contexte )
            throws TechnicalException {
    	return VisibiliteDAO.getInstance().getListeAxesProfilFromCache( codeProfil, contexte );
    }
    
    /**
     * Renvoie la liste des axes pour le profil demandé depuis la base. ( table AXE_VISIBILITE )
     * @param contexte
     * @param codeProfil
     * @return List<AxeVisibilite>
     * @throws TechnicalException
     */
	public List<AxeVisibilite> getListeAxesProfilAdmin( final String codeProfil, final Contexte contexte )
            throws TechnicalException {
		return VisibiliteDAO.getInstance().getListeAxesProfil( codeProfil, contexte );
	}
    
    /**
    * Rnvoie le code du groupeEcran associe a l ecran
    * @param codeEcran
    * @return
    * @throws TechnicalException
    * @throws IncoherenceException
    */
   public String getCodeGroupeEcran( final String codeEcran ) throws TechnicalException,
           IncoherenceException {
       return  Habilitation.getInstance().getCodeGroupeEcran( codeEcran );
   }

   /**
   *
   * @param contexte
   * @param objMaitreHabil
   * @param codeAxe
   * @return
   * @throws TechnicalException
   * @throws IncoherenceException
   */
  public boolean isObjetVisiblePourAxe( final ObjetMaitreHabilitation objMaitreHabil, final String codeAxe, 
		  final Contexte contexte ) throws TechnicalException, IncoherenceException {
      
	  boolean visible = false;
      // procédure à exécuter
      String login = contexte.getCodeUtilisateur();
      String clefLogHabil = login + "-" + objMaitreHabil.getCodeObjetMaitre();
      try {
          visible = HabilitationQBEDAO.getInstance().appliquerVisibiliteHabil(contexte, objMaitreHabil, codeAxe);
      } catch (TechnicalException e) {
          LogCommun.major( login,"HabilitationFacadeBean", "isObjetVisiblePourAxe", "Erreur SQL",e );
          LogCommun.traceHabilitations(clefLogHabil + "|failed|" + e.getMessage());
          throw e;
      }
      return visible;
  }

   /**
   	*
  	* @param codeEcran
  	* @return
  	* @throws TechnicalException
  	* @throws IncoherenceException
  	*/
  	public TypeObjetMaitreHabilitation getTypeObjetHabilitePourEcran( final String codeEcran ) 
 			throws TechnicalException, IncoherenceException {
  		return  Habilitation.getInstance().getRegleHabilitation(codeEcran);
  	}

	/**
	 * Sauvegarde les droits d'acces pour un role
	 * @param droitAcces
	 * @param contexte
	 * @exception TechnicalException
	 */
	 public void saveAccesByRole( final List<Acces> droitAcces, final Contexte contexte ) 
	 		throws TechnicalException {
		 HabilitationDAO.getInstance().saveAccesByRole( droitAcces, contexte );
	 }

	/***
	 * Renvoie une Map contenant les droits associes a chaque Axe*groupeEcran pour un Role
	 * @param codeRole
	 * @param contexte
	 * @return
	 * @throws TechnicalException
	 */
	 public AccesRoleMap getAccesByRole( final String codeRole, final Contexte contexte ) 
	 		throws TechnicalException {
		 return HabilitationDAO.getInstance().getAccesByRole( codeRole, contexte );
	 }

    /**
     * Renvoie toutes les options du menu de vente
     * @params Contexte
     * @return Map<GroupeOptionMenu,List<OptionMenu>>
     * @throws TechnicalException
     */
    public Map<GroupeOptionMenu,List<OptionMenu>> getAllOptionMenuVente( final Contexte contexte ) throws TechnicalException {
    	return EcranDAO.getInstance().getAllOptionMenuVente( contexte );
    }
    	
    /**
     * Renvoie toutes les options du menu de tarif
     * @params Contexte
     * @return Map<GroupeOptionMenu,List<OptionMenu>>
     * @throws TechnicalException
     */
    public Map<GroupeOptionMenu,List<OptionMenu>> getAllOptionMenuTarif( final Contexte contexte ) throws TechnicalException {
    	return EcranDAO.getInstance().getAllOptionMenuTarif( contexte );
    }
    	
    /**
     * Renvoie toutes les options du menu d'admin
     * @params Contexte
     * @return Map<GroupeOptionMenu,List<OptionMenu>>
     * @throws TechnicalException
     */
    public Map<GroupeOptionMenu,List<OptionMenu>> getAllOptionMenuAdmin( final Contexte contexte ) throws TechnicalException {
    	return EcranDAO.getInstance().getAllOptionMenuAdmin( contexte );
    }
    	
    /**
     * Renvoie toutes les options du menu de translate
     * @params Contexte
     * @return Map<GroupeOptionMenu,List<OptionMenu>>
     * @throws TechnicalException
     */
    public Map<GroupeOptionMenu,List<OptionMenu>> getAllOptionMenuTranslate( final Contexte contexte ) throws TechnicalException {
    	return EcranDAO.getInstance().getAllOptionMenuTranslate( contexte );
    }
    	
    /**
     * Renvoie les toutes les options de menu pour tous les roles
     * @param contexte
     * @param codeRole
     * @return List<OptionMenuRole>
     * @throws TechnicalException
     */
    public List<OptionMenuRole> getAllOptionsMenuRole( final Contexte contexte ) 
    		throws TechnicalException {
    	return EcranDAO.getInstance().getAllOptionsMenuRole(contexte);
    }

    /**
     * Renvoie les options de menu pour le role donne en parametre
     * @param contexte
     * @param codeRole
     * @return Set<String>
     * @throws TechnicalException
     */
    public Set<String> getOptionsMenuRole( final String codeRole, final Contexte contexte ) throws TechnicalException {
        return  EcranDAO.getInstance().getOptionsMenuRole( codeRole, contexte );
    }
    
	/***
	 * Renvoie la liste des groupes d'ecrans declares avec leur appartenance ( vente, tarif ... )
	 * @param contexte
	 * @return List<GroupeEcran>
	 * @throws TechnicalException
	 */
    public List<GroupeEcran> getGroupeEcrans( AsaModule module, final Contexte contexte ) throws TechnicalException {
    	return EcranDAO.getInstance().getGroupeEcrans( module, contexte );
    }

    /***
	* Mise a jour des infos d'un role
	* @param codeRole
	* @param responsableDossier
	* @param reattributionDossier
	* @param contexte
	* @throws TechnicalException
	*/
	public void saveInfoRole( final String codeRole, final boolean responsableDossier, 
			final boolean reattributionDossier, final Contexte contexte ) throws TechnicalException {
		RoleDAO.getInstance().saveInfoRole( codeRole, responsableDossier, reattributionDossier, contexte );
	}

	 /***
	  * Enregistre les options du menu par role
	  * @param table de hashage (codeRole) -> codeOption
	  * @param module
	  * @param tableOptionsMenuRole
	  * @param contexte
	  * @throws TechnicalException
	  */
	 public void setOptionMenuByRole( final AsaModule module, final OptionsMenuRoleMap optionsMenuRole, 
			final Contexte contexte ) throws TechnicalException {
		 EcranDAO.getInstance().setOptionMenuByRole( module, optionsMenuRole, contexte );
	 }

	/***
	 * renvoie la liste des types de visibilites ( table TYPE_VALEUR_VISIBILITE )
	 * @param contexte
	 * @return List<TypeVisibilite>
	 * @throws TechnicalException
	 */
	public List<TypeVisibilite> getTypesVisibilite( final Contexte contexte ) throws TechnicalException {
		return VisibiliteDAO.getInstance().getTypesVisibilite( contexte );
	}

    /**
     * Renvoie tous les axes de visibilite du module Vente ( table AXE_VISIBILITE )
     * @param module 
     * @param contexte
     * @return List<AxeVisibilite>
     * @throws TechnicalException
     */
    public List<AxeVisibilite> getAxesVisibiliteVente( final Contexte contexte ) throws TechnicalException {
    	return VisibiliteDAO.getInstance().getAxesVisibiliteVente( contexte );
    }

    /**
     * Renvoie tous les axes de visibilite des modules tarif / admin / translate ( table AXE_VISIBILITE )
     * @param module 
     * @param contexte
     * @return List<AxeVisibilite>
     * @throws TechnicalException
     */
    public List<AxeVisibilite> getOtherAxesVisibilite( final Contexte contexte ) throws TechnicalException {
    	return VisibiliteDAO.getInstance().getOtherAxesVisibilite( contexte );
    }
    

    /***
     * Ajoute une valeur de visibilite a l'utilisateur.
     * @param v
     * @param codeLangue
     * @param contexte
     * @throws IncoherenceException
     * @throws TechnicalException
     */
    public void addVisibilite( final Visibilite v, final Contexte contexte ) 
    		throws IncoherenceException, TechnicalException {
    	
    	int codeRetour = VisibiliteDAO.getInstance().addVisibilite(v, contexte);
    	
    	if( codeRetour != Visibilite.NO_CONSTRAINT_ERROR ) {
    		throw new IncoherenceException( Visibilite.getErrorMessage( codeRetour, contexte ) );
    	}
    }

    /***
     * Renvoie les valeurs de visibilite d'un utilisateur pour un axe et un type de valeur 
     * @param login
     * @param codeAxe
     * @param codeType
     * @param contexte
     * @return List<<Visibilite>
     * @throws TechnicalException
     */
    public List<Visibilite> getVisibilites( final String login, final String codeAxe, final String codeType, 
    		final Contexte contexte ) throws TechnicalException {
    	return VisibiliteDAO.getInstance().getVisibilites(login, codeAxe, codeType, contexte);    	
    }

    /***
     * Renvoie la liste des axes de visibilite sur vente sur lesquels
     *   l'utilisateur a une visibilité complète (All)
     * @param login
     * @param contexte
     * @return List<String>
     * @throws TechnicalException
     */
    public List<String> getAxesWithVisibiliteAll( final String login, final Contexte contexte )
            throws TechnicalException {
    	return VisibiliteDAO.getInstance().getAxesWithVisibiliteAll(login, contexte);    	
    }

    /**
     * Renvoie les valeurs de visibilite d'un utilisateur
     * @param Identifiant de l'utilisateur
     * @return List<Visibilite>
     */
    public List<Visibilite> getVisibilites( final String login, final Contexte contexte )
            throws TechnicalException {
    	return VisibiliteDAO.getInstance().getVisibilites(login, contexte);    	
    }

    /***
     * Supprime une valeur de visibilite d'un utilisateur
     * @param login
     * @param codeAxe
     * @param codeType
     * @param contexte
     * @throws TechnicalException
     */
    public void deleteVisibilites( final String login, final String codeAxe, final Contexte contexte ) 
    		throws TechnicalException {
    	VisibiliteDAO.getInstance().deleteVisibilites(login, codeAxe, contexte);    	
    }
    

    /**
     * Recherche les infos d un profil dont le code est passe en parametre
     *
     * @param code
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public Profil getProfil( String code, Contexte contexte ) throws TechnicalException, HabilitationException {
    	List<Profil> profils = getProfils(contexte);

        if (profils != null)
            for (int i = 0, size = profils.size(); i < size; i++)
                if (StringUtils.equals(code, ((Profil) profils.get(i)).getCode()))
                    return (Profil) profils.get(i);

        throw new HabilitationException("Code profil inconnu : " + code);
    }

    /**
     * Recherche de tous les profils
     * 20030227 : ajout de cache pour les perfs
     *
     * @param contexte utilisé pour le login seulement
     * @return List<Profil>
     */
    public List<Profil> getProfils( final Contexte contexte ) throws TechnicalException {
    	return ProfilDAO.getInstance().getProfils( contexte );
    }

    /**
     * Recherche de tous les profils ( supprimer inclus )
     * @param contexte 
     * @return List<Profil>
     */
    public List<Profil> getProfilsAdmin( final Contexte contexte ) throws TechnicalException {
    	return ProfilDAO.getInstance().getProfilsAdmin( contexte );
    }

    /***
     * methode de mise a jour d'un profil et de ses axes de visibilites
     * @param p
     * @param contexte
     * @param newProfil
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    public void updateProfil( Profil p, final Contexte contexte, final boolean newProfil ) 
    		throws TechnicalException, IncoherenceException  {
    	List<Profil> profils = getProfilsAdmin(contexte);
    	int codeRetour = Profil.NO_CONSTRAINT_ERROR;
    	if( newProfil ) {
    		if( p.notExist( profils ) ) {
    			codeRetour = ProfilDAO.getInstance().insertProfil( p, contexte );
    		} else {
    			throw new IncoherenceException( 
    					Profil.getErrorMessage( Profil.DUPLICATE_PROFIL_CONSTRAINT_ERROR , p.getCode(), contexte ) );
    		}
    	} else {
    		if( p.isUnique( profils ) ) {
    			codeRetour = ProfilDAO.getInstance().updateProfil( p, contexte );
    		} else {
    			throw new IncoherenceException( 
    					Profil.getErrorMessage( Profil.DUPLICATE_PROFIL_CONSTRAINT_ERROR , p.getCode(), contexte ) );
    		}
    	}
    	
    	if( codeRetour != Profil.NO_CONSTRAINT_ERROR ) {
    		throw new IncoherenceException( Profil.getErrorMessage( codeRetour, p.getCode(), contexte ) );
    	}
    }

    /***
     * Methode suppression logique d'un profil
     * @param p
     * @param contexte
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    public void deleteProfil( final Profil p, final Contexte contexte ) 
    		throws TechnicalException, IncoherenceException {
    	int codeRetour = ProfilDAO.getInstance().deleteProfil( p, contexte );
    	if( codeRetour != Profil.NO_CONSTRAINT_ERROR ) {
    		throw new IncoherenceException( Profil.getErrorMessage( codeRetour, p.getCode(), contexte ) );
    	}
    }
    
	/***
	 * Methode de recuperation des utilisateurs lie a ce profil
	 * @param idTerritory
	 * @param contexte
	 * @return List<UserLight>
	 * @throws TechnicalException
	 */
	public List<UserLight> getUsersForProfile( final String codeProfil, final Contexte contexte ) 
			throws TechnicalException {
		return ProfilDAO.getInstance().getUsers( codeProfil, contexte );
	}
}
