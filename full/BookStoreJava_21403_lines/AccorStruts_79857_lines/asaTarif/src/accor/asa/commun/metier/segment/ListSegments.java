package com.accor.asa.commun.metier.segment;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ListSegments extends AbstractCachable implements CachableInterface {

	protected List<Segment> segments;
	
	public ListSegments( List<Segment> segments, String codeLangue ) {
		this.segments = segments;
		this.params = new String[1];
		this.params[0] = codeLangue;
	}
	
	public List<Segment> getElements() {
		return segments;
	}
}
