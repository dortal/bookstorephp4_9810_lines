package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 31 oct. 2007
 * Time: 10:02:16
 */
@SuppressWarnings("serial")
public class FamilleSegmentCachList  extends AbstractCachable implements CachableInterface {

	private List<FamilleSegmentRefBean> familly;
	
	public FamilleSegmentCachList( List<FamilleSegmentRefBean> list, String codeLangue ) {
		this.familly = list;
		this.params = new String[1];
		this.params[0] = codeLangue;
	}
	
	public List<FamilleSegmentRefBean> getElements() {
		return familly;
	}
}
