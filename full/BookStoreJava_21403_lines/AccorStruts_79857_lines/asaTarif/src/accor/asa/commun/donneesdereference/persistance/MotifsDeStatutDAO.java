package com.accor.asa.commun.donneesdereference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.donneesdereference.metier.DonneeReference;
import com.accor.asa.commun.donneesdereference.metier.ListMotifsStatut;
import com.accor.asa.commun.donneesdereference.metier.MapMotifsDeStatut;
import com.accor.asa.commun.donneesdereference.metier.MotifsDeStatut;
import com.accor.asa.commun.persistance.ContexteAppelDAO;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;

/**
 * DAO pour les motifs de changement de statut. Ces motfs sont des données de ref gérées dans translate.
 * table Dref : VENTE_MOTIF_STATUT_OFFRE_HOTEL
 * table mapping Dref, GroupeOffre, Statut offre : VENTE_MOTIF_STATUT_AUTORISE
 */
public class MotifsDeStatutDAO implements DonneeRefDAO {
	private static final String SELECT_ADMIN_PROC_NAME = "vente_DRef_sel_motif_statut";
	private static final String SELECT_PROC_NAME = "vente_sel_motif_statut";
	private static final String INSERT_ADMIN_PROC_NAME = "vente_DRef_ins_motif_statut";
	private static final String UPDATE_ADMIN_PROC_NAME = "vente_DRef_upd_motif_statut";
	private static final String DELETE_ADMIN_PROC_NAME = "vente_DRef_del_motif_statut";

	// cache avec rafraichissement JMS pour les codeGroupeOffre et statut des motifs de changement de statut
	
	
	public List getDonneesRefAdmin(ContexteAppelDAO context) throws TechnicalException {
		SQLParamDescriptorSet result = new SQLParamDescriptorSet(new SQLParamDescriptor[0]);
		List resultsetListeMotifs;
		try {
			resultsetListeMotifs =
				SQLCallExecuter
					.getInstance()
					.executeSelectProc(SELECT_ADMIN_PROC_NAME, result, "MotifsDeStatutDAO", "getDonneesRef", context, new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					MotifsDeStatut motif = new MotifsDeStatut();
					motif.setCode(rs.getString("CLE"));
					motif.setLibelle(rs.getString("MOTIF"));
					motif.setCodeLangue(rs.getString("CODE_LANGUE"));
					return motif;
				}
			});
			setInformationsMotifs(context, resultsetListeMotifs);
		}
		catch (TechnicalException e) {
			throw new TechnicalException(e);
		}
		catch (SQLException e) {
			throw new TechnicalException(e);
		}
		return resultsetListeMotifs;

	}

    /**
     * Retourne la liste des motifs status
     * @param codeLangue
     * @param context
     * @return
     * @throws TechnicalException
     */
    public List getDonneesRef(String codeLangue, ContexteAppelDAO context)
            throws TechnicalException {
        ListMotifsStatut motifsStatut = (ListMotifsStatut)
                CacheManager.getInstance().getObjectInCache(ListMotifsStatut.class, codeLangue);
        try {
            if (motifsStatut == null) {
                List res = SQLCallExecuter.getInstance().
                        executeSelectProc(SELECT_PROC_NAME, new SQLParamDescriptorSet(new SQLParamDescriptor(codeLangue, Types.VARCHAR)),
                                "MotifsDeStatutDAO", "getDonneesRef",context,
                                new SQLResultSetReader() {
                    public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
                        MotifsDeStatut motif = new MotifsDeStatut();
                        motif.setCode(rs.getString("CLE"));
                        motif.setLibelle(rs.getString("MOTIF"));
                        motif.setCodeLangue(rs.getString("CODE_LANGUE"));
                        motif.setHasInfo(rs.getBoolean("has_info"));
                        return motif;
                    }
                });
                motifsStatut = new ListMotifsStatut(res, codeLangue);
                CacheManager.getInstance().setObjectInCache(motifsStatut);
            }
            setInformationsMotifs(context, motifsStatut.getElements());
        } catch (TechnicalException e) {
            throw new TechnicalException(e);
        }  catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return motifsStatut.getElements();
	}

	/*
	 * Pour chaque motif passé en paramètre, recupère les listes de groupes d'offre et de statuts d'offre rattachés
	 * et met à jour l'objet Motif.
	 */
	private void setInformationsMotifs(ContexteAppelDAO context, List<MotifsDeStatut> resultsetListeMotifs) throws TechnicalException, SQLException {
	    MapMotifsDeStatut motifs = (MapMotifsDeStatut) 
		CacheManager.getInstance().getObjectInCache( MapMotifsDeStatut.class );

	    if( motifs == null ) motifs = updateMotifCache(context);
	
		for (Iterator<MotifsDeStatut> iter = resultsetListeMotifs.iterator(); iter.hasNext();) {
			final MotifsDeStatut motif = iter.next();
						
			MotifsDeStatut motifInCache = (MotifsDeStatut) motifs.getElements().get(motif.getCode());
			if( motifInCache != null ) {
				List<String> listeGroupeOffre = motifInCache.getListe_offres();
				if (listeGroupeOffre == null) listeGroupeOffre = new ArrayList<String>();
				motif.setListe_offres(listeGroupeOffre);
				List<String> listeStatut = motifInCache.getListe_statuts();
				if (listeStatut == null) listeStatut = new ArrayList<String>();
				motif.setListe_statuts(listeStatut);
			}
		}
	}
	
	private static MapMotifsDeStatut updateMotifCache(ContexteAppelDAO contexte) throws TechnicalException, SQLException {
		SQLParamDescriptorSet result = new SQLParamDescriptorSet();
		final Map<String, MotifsDeStatut> motifs = new HashMap<String, MotifsDeStatut>();
		
		
		//proc de select des motifs autorisés en fonction du groupe d'offre et du statut de l'offre.
		SQLCallExecuter
			.getInstance()
			.executeSelectProcSansLimite("vente_sel_motif_statutAutorise", result, "MotifsDeStatutDAO", "getDonneesRef", contexte, new SQLResultSetReader() {
			public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
				String codeMotif = rs.getString("CODE_MOTIF_STATUT");
				MotifsDeStatut motif = (MotifsDeStatut) motifs.get(codeMotif);
				
				if( motif == null ) {
					motif = new MotifsDeStatut();
					motif.setListe_offres( new ArrayList<String>() );
					motif.setListe_statuts( new ArrayList<String>() );
				}
				
				List<String> listCodeGroupeOffre = motif.getListe_offres();
				List<String> listStatut = motif.getListe_statuts();
				listCodeGroupeOffre.add(rs.getString("CODE_GROUPEOFFRE"));
				listStatut.add(rs.getString("STATUT_OFFRE"));
				
				motif.setListe_offres(listCodeGroupeOffre);
				motif.setListe_statuts(listStatut);
				motifs.put(codeMotif, motif);
				return codeMotif; // obligatoire a cause de l'API SQLCallExecuter ; pas utilisé ici.
			}
		});
		MapMotifsDeStatut motifsInCache = new MapMotifsDeStatut( motifs );
		CacheManager.getInstance().setObjectInCache( motifsInCache );
		return motifsInCache;
	}

	public void insertDonneeRef(DonneeReference donnee, ContexteAppelDAO contexte) throws TechnicalException, IncoherenceException {
		MotifsDeStatut motif = (MotifsDeStatut) donnee;
		try {
			SQLCallExecuter.getInstance().executeUpdate(
				INSERT_ADMIN_PROC_NAME,
				getInsertParamDescriptor(motif),
				"MotifsDeStatutDAO",
				"insertDonneeRef",
				contexte);

			//si la liste des Motifs autorisés été modifiée.
			if ("oui".equals(motif.getListesModifiee())) {
				updateMotifsAutorises(contexte, motif);
			}
			
			// on met a jour le cache !!!!!!!
			updateMotifCache(contexte);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new TechnicalException(e);
		}
	}

	private void updateMotifsAutorises(ContexteAppelDAO contexte, MotifsDeStatut motif) throws TechnicalException, SQLException {
		//on supprime les entrees de ce motif dans la table jointure
		deleteMotifsAutorises(contexte, motif.getCode());

		//on insère les nouvelles entrées
		List<String> listeOffres = motif.getListe_offres();
		for (String codeGroupeOffre : listeOffres) {
			List<String> listeStatuts = motif.getListe_statuts();
			for (String codeStatut : listeStatuts) {
				SQLCallExecuter.getInstance().executeUpdate(
					"vente_ins_motif_statutAutorise",
					getMotifsAutorisesParamsDescriptionSet(motif.getCode(), codeGroupeOffre, codeStatut),
					"MotifsDeStatutDAO",
					"insertDonneeRef",
					contexte);
			}
		}
	}

	private void deleteMotifsAutorises(ContexteAppelDAO contexte, String code) throws TechnicalException, SQLException {
		//on supprime les entrees de ce motif dans la table jointure
		SQLParamDescriptor[] params = new SQLParamDescriptor[1];
		SQLParamDescriptorSet result = new SQLParamDescriptorSet(params);
		params[0] = new SQLParamDescriptor(Integer.valueOf(code), Types.INTEGER);
		SQLCallExecuter.getInstance().executeUpdate("vente_del_motif_statutAutorise", result, "MotifsDeStatutDAO", "deleteMotifsAutorises", contexte);
	}

	private SQLParamDescriptorSet getMotifsAutorisesParamsDescriptionSet(String code, String codeGroupeOffre, String codeStatut) {
		SQLParamDescriptor[] params = new SQLParamDescriptor[3];
		SQLParamDescriptorSet result = new SQLParamDescriptorSet(params);
		params[0] = new SQLParamDescriptor(Integer.valueOf(code), Types.INTEGER);
		params[1] = new SQLParamDescriptor(codeGroupeOffre, Types.VARCHAR);
		params[2] = new SQLParamDescriptor(codeStatut, Types.VARCHAR);
		return result;
	}

	private SQLParamDescriptorSet getInsertParamDescriptor(MotifsDeStatut motif) {
		SQLParamDescriptor[] params = new SQLParamDescriptor[3];
		SQLParamDescriptorSet result = new SQLParamDescriptorSet(params);
		params[0] = new SQLParamDescriptor(Integer.valueOf(motif.getCode()));
		params[1] = new SQLParamDescriptor(motif.getLibelle(), Types.VARCHAR);
		params[2] = new SQLParamDescriptor(motif.getCodeLangue(), Types.VARCHAR);
		return result;
	}

	public void updateDonneeRef(DonneeReference donnee, ContexteAppelDAO context) throws TechnicalException, IncoherenceException {
		MotifsDeStatut motif = (MotifsDeStatut) donnee;
		try {
			SQLCallExecuter.getInstance().executeUpdate(UPDATE_ADMIN_PROC_NAME, getUpdateParamDescriptor(motif), "MotifsDeStatutDAO", "updateDonneeRef", context);
			//si la liste des Motifs autorisés été modifiée.
			if ("oui".equals(motif.getListesModifiee())) {
				updateMotifsAutorises(context, motif);
			}
		}
		catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}

	private SQLParamDescriptorSet getUpdateParamDescriptor(MotifsDeStatut motif) {
		SQLParamDescriptor[] params = new SQLParamDescriptor[2];
		SQLParamDescriptorSet result = new SQLParamDescriptorSet(params);
		params[0] = new SQLParamDescriptor(Integer.valueOf(motif.getCode()), Types.INTEGER);
		params[1] = new SQLParamDescriptor(motif.getLibelle(), Types.VARCHAR);
		return result;
	}

	public void deleteDonneeRef(String codeDonneeRef, ContexteAppelDAO context) throws TechnicalException {
		SQLParamDescriptor[] params = new SQLParamDescriptor[1];
		SQLParamDescriptorSet result = new SQLParamDescriptorSet(params);
		params[0] = new SQLParamDescriptor(Integer.valueOf(codeDonneeRef), Types.INTEGER);

		try {
			SQLCallExecuter.getInstance().executeUpdate(DELETE_ADMIN_PROC_NAME, result, "MotifsDeStatutDAO", "deleteDonneeRef", context);
			deleteMotifsAutorises(context, codeDonneeRef);
		}

		catch (SQLException e) {
			e.printStackTrace();
			throw new TechnicalException(e);
		}
	}

  public Map getDonneesRefIntoMap( String codeLangue, ContexteAppelDAO context ) throws TechnicalException {
    Map map = new HashMap();
    Object obj;
    List info = getDonneesRef( codeLangue, context );
    for( Iterator it = info.iterator(); it.hasNext(); ) {
    	obj = it.next();
      if( obj instanceof DonneeReference ) {
      	map.put( ( ( DonneeReference ) obj ).getCode(), obj ); 
      }
    }
    return map;
  }
}
