package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.FamilleSegmentCachList;
import com.accor.asa.commun.reference.metier.FamilleSegmentRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 31 oct. 2007
 * Time: 10:03:06
 */
public class FamilleSegmentRefDAO extends RefDAO {

    private static final String SELECT_PROC_NAME        = "ref_selFamilleSegment";
	private static final String ADMIN_SELECT_PROC_NAME  = "ref_selFamilleSegment";
	private static final String INSERT_PROC_NAME        = null;
	private static final String UPDATE_PROC_NAME        = null;
	private static final String DELETE_PROC_NAME        = null;

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
        switch (type) {
            case SELECT :
                SQLParamDescriptor[] selectParams = {
                    new SQLParamDescriptor(Boolean.FALSE),
                    new SQLParamDescriptor(codeLangue)
                };
                return new SQLParamDescriptorSet(selectParams);
            case ADMIN_SELECT :
                SQLParamDescriptor[] adminSelectParams = {
                    new SQLParamDescriptor(Boolean.TRUE),
                    new SQLParamDescriptor(codeLangue)
                };
                return new SQLParamDescriptorSet(adminSelectParams);
            case INSERT :
            case UPDATE :
            case DELETE :
            default :
                return null;
        }
    }

    protected SQLResultSetReader getProcReader (int type) {
        switch (type) {
            case SELECT :
            case ADMIN_SELECT :
                return new SQLResultSetReader() {
                    public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
                        FamilleSegmentRefBean bean = new FamilleSegmentRefBean();
                        bean.setId(rs.getString("codeFamSegment"));
                        bean.setLibelle(rs.getString("nomFamSegment"));
                        bean.setActif(rs.getString("supprime").equals("0"));
                        return bean;
                    }
                };
            default :
                return null;
        }
    }

    protected String getCacheClassName () {
        return FamilleSegmentCachList.class.getName();
    }

    protected CachableInterface getObjectInCache (String codeLangue) {
        return (CachableInterface) CacheManager.getInstance().getObjectInCache(FamilleSegmentCachList.class, codeLangue);
    }


	@SuppressWarnings("unchecked")
	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new FamilleSegmentCachList((List<FamilleSegmentRefBean>) data, contexte.getCodeLangue());
	}
}