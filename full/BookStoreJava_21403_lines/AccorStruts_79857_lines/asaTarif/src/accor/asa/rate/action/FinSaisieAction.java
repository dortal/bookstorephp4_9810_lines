package com.accor.asa.rate.action;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.habilitation.metier.HabilitationException;
import com.accor.asa.commun.habilitation.metier.Profil;
import com.accor.asa.commun.habilitation.metier.Role;
import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.*;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.model.ControleFinSaisieBean;
import com.accor.asa.rate.model.GrilleBean;
import com.accor.asa.rate.service.PoolRateFactory;
import com.accor.asa.rate.util.RateScrean;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletDispatcherResult;

import java.util.ArrayList;
import java.util.List;

@Results({
	@Result(name = Action.INPUT,        type = ServletDispatcherResult.class, value = "validation/finSaisie.jsp"),
	@Result(name = Action.SUCCESS,      type = ServletDispatcherResult.class, value = "validation/finSaisie.jsp"),
	@Result(name = MainAction.NO_HOTEL, type = ServletDispatcherResult.class, value = "/hotel/noHotel.jsp"),
	@Result(name = Action.ERROR,        type = ServletDispatcherResult.class, value = "error.jsp")
})
public class FinSaisieAction extends MainAction {

	private List<GroupeTarifRefBean> groupesTarif = null;
	private int idGroupeTarif = 0;
	private List<PeriodeValiditeRefBean> periodesValidite = null;
	private int idPeriodeValidite = 0;
	private List<GrilleBean> grilles = null;
	private List<ControleFinSaisieBean> controlesBloquantsEnEchec = null;
	private List<ControleFinSaisieBean> controlesNonBloquantsEnEchec = null;
	private boolean displayBoutonControle = false;
	private boolean displayBoutonFinSaisie = false;
    private boolean screenReadOnly;

    public List<GroupeTarifRefBean> getGroupesTarif () {
		return this.groupesTarif;
	}

	public int getIdGroupeTarif () {
		return this.idGroupeTarif;
	}

	public List<PeriodeValiditeRefBean> getPeriodesValidite () {
		return this.periodesValidite;
	}

	public int getIdPeriodeValidite () {
		return this.idPeriodeValidite;
	}

	public List<GrilleBean> getGrilles () {
		return this.grilles;
	}

	public List<ControleFinSaisieBean> getControlesBloquantsEnEchec () {
		return this.controlesBloquantsEnEchec;
	}

	public List<ControleFinSaisieBean> getControlesNonBloquantsEnEchec () {
		return this.controlesNonBloquantsEnEchec;
	}

	public boolean getDisplayBoutonControle () {
		return this.displayBoutonControle;
	}

	public boolean getDisplayBoutonFinSaisie () {
		return this.displayBoutonFinSaisie;
	}

	public void setGroupesTarif (List<GroupeTarifRefBean> groupesTarif) {
		this.groupesTarif = groupesTarif;
	}

	public void setIdGroupeTarif (int idGroupeTarif) {
		this.idGroupeTarif = idGroupeTarif;
	}

	public void setPeriodesValidite (List<PeriodeValiditeRefBean> periodesValidite) {
		this.periodesValidite = periodesValidite;
	}

	public void setIdPeriodeValidite (int idPeriodeValidite) {
		this.idPeriodeValidite = idPeriodeValidite;
	}

	public void setGrilles (List<GrilleBean> grilles) {
		this.grilles = grilles;
	}

	public void setControlesBloquantsEnEchec (List<ControleFinSaisieBean> controlesBloquantsEnEchec) {
		this.controlesBloquantsEnEchec = controlesBloquantsEnEchec;
	}

	public void setControlesNonBloquantsEnEchec (List<ControleFinSaisieBean> controlesNonBloquantsEnEchec) {
		this.controlesNonBloquantsEnEchec = controlesNonBloquantsEnEchec;
	}

	public void setDisplayBoutonControle (boolean displayBoutonControle) {
		this.displayBoutonControle = displayBoutonControle;
	}

	public void setDisplayBoutonFinSaisie (boolean displayBoutonFinSaisie) {
		this.displayBoutonFinSaisie = displayBoutonFinSaisie;
	}

    public boolean isScreenReadOnly() {
        return screenReadOnly;
    }

    public void setScreenReadOnly(boolean screenReadOnly) {
        this.screenReadOnly = screenReadOnly;
    }

    /**
	 * Affichage des grilles et de leur statut
	 */
	public String execute () {
		try {
			Hotel hotel = (Hotel) getFromSession(HOTEL);
			if (hotel == null) {
				setToRequest("url", ServletActionContext.getRequest().getRequestURI());
				return NO_HOTEL;
			}
			else {
                findGrilles();
                setScreenReadOnly();
				// Affichage du bouton de lancement des contrôles si période de validité en cours et si un statut en saisie au moins
                if(grilles!=null && !grilles.isEmpty() && !isScreenReadOnly()) {
                    for (GrilleBean grille : grilles) {
                        if (grille.getIdStatut() == StatutGrilleRefBean.ID_STATUT_NOW_SEIZED) {
                            this.displayBoutonControle = true;
                            break;
                        }
                    }
                }
				return Action.INPUT;
			}
		}
		catch (Exception e) {
            Log.critical(getAsaContexte().getCodeUtilisateur(),"FinSaisieAction","execute",e.getMessage(),e);
			addActionError(getText("COM_PRM_MSG_DEFAULTERROR"));
			return Action.ERROR;
		}
	}

	/**
	 * Affichage des grilles et de leur statut
	 * Lancement des controles avant la fin de saisie
	 */
	public String controle () {
		try {
			Hotel hotel = (Hotel) getFromSession(HOTEL);
			if (hotel == null) {
				setToRequest("url", ServletActionContext.getRequest().getRequestURI());
				return NO_HOTEL;
			} else {
				findGrilles();
				if (grilles.size() != 0) {
					List<ControleFinSaisieBean> controlesEnEchec = PoolRateFactory.getInstance().getRateFacade().getControlesEnEchec(hotel.getCode(), idGroupeTarif, idPeriodeValidite, getAsaContexte());
					controlesBloquantsEnEchec = new ArrayList<ControleFinSaisieBean>();
					controlesNonBloquantsEnEchec = new ArrayList<ControleFinSaisieBean>();
					this.displayBoutonFinSaisie = true;
					// Affichage du bouton pour lancer la fin de saisie si aucun controle en échec
					for (ControleFinSaisieBean controleEnEchec : controlesEnEchec) {
						if (controleEnEchec.isBloquant()) {
							controlesBloquantsEnEchec.add(controleEnEchec);
							this.displayBoutonFinSaisie = false;
						}
						else {
							controlesNonBloquantsEnEchec.add(controleEnEchec);
						}
					}
				}
				return Action.SUCCESS;
			}
		}
		catch (Exception e) {
            Log.critical(getAsaContexte().getCodeUtilisateur(),"FinSaisieAction","controle",e.getMessage(),e);
			addActionError(getText("COM_PRM_MSG_DEFAULTERROR"));
			return Action.ERROR;
		}
	}

	/**
	 * Affichage des grilles et de leur statut
	 * Fin de saisie
	 */
	public String finSaisie () {
		try {
			Hotel hotel = (Hotel) getFromSession(HOTEL);
			if (hotel == null) {
				setToRequest("url", ServletActionContext.getRequest().getRequestURI());
				return NO_HOTEL;
			} else {
				PoolRateFactory.getInstance().getRateFacade().updateStatutGrille(hotel.getCode(), idGroupeTarif, idPeriodeValidite, StatutGrilleRefBean.ID_STATUT_SEIZED, getAsaContexte());
				findGrilles();
				return Action.SUCCESS;
			}
		}
		catch (Exception e) {
            Log.critical(getAsaContexte().getCodeUtilisateur(),"FinSaisieAction","finSaisie",e.getMessage(),e);
			addActionError(getText("COM_PRM_MSG_DEFAULTERROR"));
			return Action.ERROR;
		}
	}

	/**
	 * Récupération des grilles et de leur statut
	 */
	private void findGrilles () throws RatesTechnicalException {
		try {
			Contexte contexte = getAsaContexte();
			Hotel hotel = (Hotel) getFromSession(HOTEL);
			
			groupesTarif = GroupeTarifRefBean.getCacheList(contexte).getElements();
			periodesValidite = PeriodeValiditeRefBean.getCacheList(contexte).getPeriodesForGroupeTarif(idGroupeTarif);
			
			boolean idPeriodeOk = false;
			if (periodesValidite != null && periodesValidite.size() > 0) {
				for (PeriodeValiditeRefBean periode : periodesValidite) {
					if (Integer.parseInt(periode.getId()) == idPeriodeValidite) {
						idPeriodeOk = true;
						break;
					}
				}
				if (!idPeriodeOk) {
					idPeriodeValidite = Integer.parseInt(periodesValidite.get(0).getCode());
				}
				grilles = PoolRateFactory.getInstance().getRateFacade().getGrilles(hotel.getCode(), idGroupeTarif, idPeriodeValidite, contexte);
			}
		} catch (TechnicalException ex) {
			throw new RatesTechnicalException(ex);
		} catch (IncoherenceException ex) {
			throw new RatesTechnicalException(ex);
		}
	}

    /**
     * Habilitation de l'ecran
     * @throws RatesTechnicalException
     */
    public void setScreenReadOnly()
            throws RatesTechnicalException {
        try {
            Contexte contexte               = getAsaContexte();
            Hotel hotel                     = (Hotel) getFromSession(HOTEL);
            int idPeriodeValidite           = getIdPeriodeValidite();
            ParamPeriodeValiditeRefBean ppv = ParamPeriodeValiditeRefBean.getCacheList(contexte).getParamPeriodeValidite(hotel.getCodeAsaCategory(), idGroupeTarif);
            PeriodeValiditeRefBean pv = (ppv!=null)?
                    PeriodeValiditeRefBean.getCacheList(contexte).getPeriodeValiditeById(ppv.getIdPeriodeValidite()):null;
            int idPeriodeValidditeOuverte   = (pv!=null)?Integer.parseInt(pv.getCode()):-1;
            int idFamilleTarif              = getIdGroupeTarif()==GroupeTarifRefBean.ID_GROUPETARIF_BUSINESS?
                                                FamilleTarifRefBean.ID_FAMILLE_PUBLISHED:FamilleTarifRefBean.ID_FAMILLE_IT;
            int idStatut                    = StatutGrilleRefBean.ID_STATUT_NOW_SEIZED;
            if(  contexte.getProfil() == null )
                throw new HabilitationException( "Aucun profil associe a cet utilisateur : " + contexte.getCodeUtilisateur() );
            Profil profil = contexte.getProfil();
            if(  profil.getRole() == null )
                throw new HabilitationException( "Aucun role associe a ce profil : " + profil.getCode() );
            Role role = profil.getRole();
            this.screenReadOnly = (!(role.isRoleValidateurTroisiemeNiveau() || role.isRoleDbm()) && (idPeriodeValidite!=idPeriodeValidditeOuverte)) ||
                    isReadOnly(RateScrean.RATES_ECRAN_OPTION_ENDSEIZURE,
                    hotel.getCode(),
                    idPeriodeValidite,
                    idFamilleTarif,
                    idStatut,
                    contexte);
            if(Log.isDebug) {
                StringBuffer sb = new StringBuffer(" >> Verifier l'habilitation ");
                sb.append(" ecran=").append(RateScrean.RATES_ECRAN_OPTION_ENDSEIZURE).
                        append(" Hotel=").append(hotel.getCode()).
                        append(" idPeriodeValidite=").append(idPeriodeValidite).
                        append(" idPeriodeValiditeOuverte=").append(idPeriodeValidditeOuverte).
                        append(" idFamilleTarif=").append(idFamilleTarif).
                        append(" idStatut=").append(idStatut).
                        append(" profil=").append(contexte.getCodeProfil()).
                        append(" >> readOnly=").append(screenReadOnly);
                Log.debug("MainRateAction","isReadOnly",sb.toString());
            }
        } catch(HabilitationException e) {
            throw new RatesTechnicalException(e);
        } catch(IncoherenceException e) {
            throw new RatesTechnicalException(e);
        } catch(TechnicalException e) {
            throw new RatesTechnicalException(e);
        }
    }


}