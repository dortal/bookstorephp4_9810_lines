package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 9 août 2007
 * Time: 16:45:57
 */
@SuppressWarnings("serial")
public class RemunerationFrequenceCachList extends AbstractCachable implements CachableInterface {

	protected List<RemunerationFrequenceRefBean> frequencies;
	
	public RemunerationFrequenceCachList( List<RemunerationFrequenceRefBean> frequencies, 
			String codeLangue ) {
		this.frequencies = frequencies;
		this.params = new String[1];
		this.params[0] = codeLangue;
	}
	
	public List<RemunerationFrequenceRefBean> getElements() {
		return frequencies;
	}
}
