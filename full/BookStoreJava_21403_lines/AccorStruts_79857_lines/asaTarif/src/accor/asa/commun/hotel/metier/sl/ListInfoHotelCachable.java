/*
 * Created on 21 oct. 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.accor.asa.commun.hotel.metier.sl;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.services.framework.enterprise.common.basictype.BasicType;


/**
 * @author FCHIVAUX
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
public final class ListInfoHotelCachable extends AbstractCachable implements CachableInterface {
  
	public final static String INFO_TYPE_LOCALISATION	= "localisation";
	public final static String INFO_TYPE_CI 			= "ci";
	public final static String INFO_TYPE_SECURITE		= "securite";
	public final static String INFO_TYPE_RESTAURANT	= "restaurant";
	public final static String INFO_TYPE_SPORT			= "sport";
	public final static String INFO_TYPE_SERVICE		= "service";
	  
	protected List<BasicType> infos;
	
	public ListInfoHotelCachable( final List<BasicType> listeInfo, final String[] params ) {
		this.params = params;
		this.infos = listeInfo;
	}

	public List<BasicType> getElements() {
		return infos;
	}
}
