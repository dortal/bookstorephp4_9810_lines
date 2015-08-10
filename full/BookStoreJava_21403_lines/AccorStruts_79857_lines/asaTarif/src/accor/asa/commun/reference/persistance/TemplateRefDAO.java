package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.DuplicateKeyException;
import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.ForeignKeyException;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.RefBean;
import com.accor.asa.commun.reference.metier.TemplateRefBean;

public class TemplateRefDAO extends RefDAO {

	private static final String SELECT_PROC_NAME = null;
	private static final String ADMIN_SELECT_PROC_NAME = "vente_Dref_Select_Gedtemplate";
	private static final String INSERT_PROC_NAME = "vente_DRef_insert_gedTemplate";
	private static final String UPDATE_PROC_NAME = "vente_DRef_insert_gedTemplate";
	private static final String DELETE_PROC_NAME = "vente_DRef_delete_gedTemplate";

	protected String getProcName (int type) {
		switch (type) {
			case SELECT :
				return SELECT_PROC_NAME;
			case ADMIN_SELECT :
				return ADMIN_SELECT_PROC_NAME;
			case INSERT :
				return INSERT_PROC_NAME;
			case UPDATE :
				return UPDATE_PROC_NAME;
			case DELETE :
				return DELETE_PROC_NAME;
			default :
				return null;
		}
	}

	protected SQLParamDescriptorSet getProcParameters (int type, RefBean bean, String codeLangue) {
		TemplateRefBean temp = (TemplateRefBean) bean;
		switch (type) {
			case SELECT :
				return null;
			case ADMIN_SELECT :
				// Pas utilisé, méthode redéfinie
				return null;
			case INSERT :
				// Pas utilisé, méthode redéfinie
				return null;
			case UPDATE :
				// Pas utilisé, méthode redéfinie
				return null;
			case DELETE :
				SQLParamDescriptor [] deleteParams = {
					new SQLParamDescriptor(Integer.valueOf(temp.getId())),
				};
				return new SQLParamDescriptorSet(deleteParams);
			default :
				return null;
		}
	}

	protected SQLResultSetReader getProcReader (int type) {
		switch (type) {
			case SELECT :
				return null;
			case ADMIN_SELECT :
				// Pas utilisé, méthode redéfinie
				return null;
			default :
				return null;
		}
	}

	protected String getCacheClassName () {
		return null;
	}

	protected CachableInterface getObjectInCache (String codeLangue) {
		return null;
	}

	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return null;
	}

	/**
	 * Redéfinitions des méthodes mères
	 */
	public List<TemplateRefBean> getAdminRefList( Contexte contexte, RefBean bean ) throws TechnicalException {
		try {
			final TreeMap<String, TemplateRefBean> temp = new TreeMap<String, TemplateRefBean>();

			TemplateRefBean templateRefBean = (TemplateRefBean) bean;
			SQLParamDescriptor [] params = {
				new SQLParamDescriptor(templateRefBean.getCodeLangue()),
				new SQLParamDescriptor(templateRefBean.getIdGroupeOffre()),
				new SQLParamDescriptor(templateRefBean.getTypeDocument())
			};

			SQLCallExecuter.getInstance().executeSelectProcSansLimite(
				ADMIN_SELECT_PROC_NAME,
				new SQLParamDescriptorSet(params),
				"TemplateRefDAO",
				"getAdminRefList",
				contexte.getContexteAppelDAO(),
				new SQLResultSetReader() {
					public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
						String id = rs.getString("ID_TEMPLATE");
						String libelle = rs.getString("TEMPLATE_NAME");

						// Pour trier par libellé
						TemplateRefBean bean = temp.get(libelle.toLowerCase() + id);

						if (bean == null) {
							bean = new TemplateRefBean();
							bean.setId(id);
							bean.setLibelle(libelle);
							bean.setCodeLangue(rs.getString("LANGUE"));
							bean.setNomDocument(rs.getString("DOCUMENT_FILE_NAME"));
							bean.setTypeDocument(rs.getString("TYPE_DOCUMENT"));
							bean.setVersion(rs.getString("VERSION"));
							bean.setDateVersion(rs.getString("DATE_VERSION"));
							bean.setIdPays(rs.getString("PAYS"));
							bean.setOhActive(rs.getBoolean("SELECT_OH_ACTIVE"));
							bean.setIdGroupeOffre(rs.getString("CODE_GROUPE_OFFRE"));
							bean.setTypesOffre(rs.getString("ID_TYPE_OFFRE"));
							bean.setStatuts(rs.getString("CODE_LISTE_STATUT"));
							bean.setIdStatutCible(rs.getString("STATUT_CIBLE"));
							bean.setStatutOhCible(rs.getString("STATUT_OH"));
							String ratesDisplayType = rs.getString("RATES_DISPLAY_TYPE");
							if (StringUtils.isNotBlank(ratesDisplayType))
								bean.setTypeAffichageTarif(ratesDisplayType);
							else
								bean.setTypeAffichageTarif("0");
						}
						else {
							bean.setTypesOffre(bean.getTypesOffre() + "|" + rs.getString("ID_TYPE_OFFRE"));
						}

						temp.put(bean.getLibelle().toLowerCase() + bean.getId(), bean);

						// Et parce qu'il faut bien retourner quelque chose...
						return null;
					}
				});
			List<TemplateRefBean> refDataList = new ArrayList<TemplateRefBean>(temp.values());

			LogCommun.traceFonctionnelle( contexte.getCodeUtilisateur(), ADMIN_SELECT_PROC_NAME, "SELECT", "" );
			LogCommun.debug("TemplateRefDAO", "getAdminRefList", "Taille resultat = " + refDataList.size());
			return refDataList;
		}
		catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}

	public int insertRef( Contexte contexte, RefBean bean ) throws TechnicalException, IncoherenceException, DuplicateKeyException {

		TemplateRefBean temp = (TemplateRefBean) bean;
		try {
			Integer typeTarif = new Integer( Integer.MIN_VALUE );
			if( StringUtils.isNotBlank( temp.getTypeAffichageTarif() ) )
				typeTarif = new Integer( temp.getTypeAffichageTarif() );
			
			SQLParamDescriptor[] params = new SQLParamDescriptor[] {
				new SQLParamDescriptor( temp.getLibelle(), false, Types.VARCHAR ),
				new SQLParamDescriptor( temp.getIdGroupeOffre(), false, Types.VARCHAR ),
				new SQLParamDescriptor( temp.getStatuts(), false, Types.INTEGER ),
				new SQLParamDescriptor( temp.getIdStatutCible(), false, Types.VARCHAR ),
				new SQLParamDescriptor( temp.getCodeLangue(), false, Types.CHAR ),
				new SQLParamDescriptor( new Integer(1), false, Types.INTEGER ),
				new SQLParamDescriptor( temp.getNomDocument(), false, Types.VARCHAR ),
				new SQLParamDescriptor( new Integer(0), false, Types.INTEGER ),
				new SQLParamDescriptor( temp.getTypesOffre(), false, Types.VARCHAR ),
				new SQLParamDescriptor( temp.getStatutOhCible(), false, Types.VARCHAR ),
				new SQLParamDescriptor( typeTarif, false, Types.INTEGER ),
				new SQLParamDescriptor( temp.getIdPays(), false, Types.VARCHAR ),
				new SQLParamDescriptor( new Boolean( temp.getOhActive() ), false, Types.BIT ),
				new SQLParamDescriptor( IS_NEW, false, Types.CHAR ),
				new SQLParamDescriptor( temp.getTypeDocument(), false, Types.VARCHAR )
			};
			
			SQLCallExecuter.getInstance().executeUpdate( INSERT_PROC_NAME, new SQLParamDescriptorSet( params ), 
					"TemplateRefDAO", "insertRef", contexte.getContexteAppelDAO() );
			
			LogCommun.traceFonctionnelle( contexte.getCodeUtilisateur(), INSERT_PROC_NAME, "INSERT", bean.toString() );

			return 0;

		} catch (SQLException e) {
			if (e.getErrorCode() == SYBASE_PRIMARY_KEY_ERROR_CODE) {
				throw new DuplicateKeyException(e);
			}
			else {
				throw new TechnicalException(e);
			}
		} catch (ForeignKeyException e) {
			throw e;
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

	/**
	 * Redéfinition de la méthode mère pour appeler execute() au lieu de executeUpdate() car la procédure stockée renvoie une liste (qui ne sert à rien)
	 */
	public int updateRef( Contexte contexte, RefBean bean ) throws TechnicalException, IncoherenceException, DuplicateKeyException {

		TemplateRefBean temp = (TemplateRefBean) bean;
		try {
			
			Integer typeTarif = new Integer( Integer.MIN_VALUE );
			if( StringUtils.isNotBlank( temp.getTypeAffichageTarif() ) )
				typeTarif = new Integer( temp.getTypeAffichageTarif() );
			
			SQLParamDescriptor[] params = new SQLParamDescriptor[] {
				new SQLParamDescriptor( temp.getLibelle(), false, Types.VARCHAR ),
				new SQLParamDescriptor( temp.getIdGroupeOffre(), false, Types.VARCHAR ),
				new SQLParamDescriptor( temp.getStatuts(), false, Types.INTEGER ),
				new SQLParamDescriptor( temp.getIdStatutCible(), false, Types.VARCHAR ),
				new SQLParamDescriptor( temp.getCodeLangue(), false, Types.CHAR ),
				new SQLParamDescriptor( temp.getVersion(), false, Types.INTEGER ),
				new SQLParamDescriptor( temp.getNomDocument(), false, Types.VARCHAR ),
				new SQLParamDescriptor( temp.getId(), false, Types.INTEGER ),
				new SQLParamDescriptor( temp.getTypesOffre(), false, Types.VARCHAR ),
				new SQLParamDescriptor( temp.getStatutOhCible(), false, Types.VARCHAR ),
				new SQLParamDescriptor( typeTarif, false, Types.INTEGER ),
				new SQLParamDescriptor( temp.getIdPays(), false, Types.VARCHAR ),
				new SQLParamDescriptor( new Boolean( temp.getOhActive() ), false, Types.BIT ),
				new SQLParamDescriptor( IS_NOT_NEW, false, Types.CHAR ),
				new SQLParamDescriptor( temp.getTypeDocument(), false, Types.VARCHAR )
			};
			
			SQLCallExecuter.getInstance().executeUpdate( UPDATE_PROC_NAME, new SQLParamDescriptorSet( params ), 
					"TemplateRefDAO", "insertRef", contexte.getContexteAppelDAO() );
			
			LogCommun.traceFonctionnelle( contexte.getCodeUtilisateur(), UPDATE_PROC_NAME, "UPDATE", bean.toString() );

			return 0;

		} catch (SQLException e) {
			if (e.getErrorCode() == SYBASE_PRIMARY_KEY_ERROR_CODE) {
				throw new DuplicateKeyException(e);
			}
			else {
				throw new TechnicalException(e);
			}
		} catch (ForeignKeyException e) {
			throw e;
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

}