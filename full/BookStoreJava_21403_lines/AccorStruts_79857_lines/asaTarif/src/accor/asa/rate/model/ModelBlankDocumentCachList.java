package com.accor.asa.rate.model;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

import java.util.List;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class ModelBlankDocumentCachList   extends AbstractCachable implements CachableInterface {

    private List<ModelBlankDocument> blankDocuments;

	public ModelBlankDocumentCachList( List<ModelBlankDocument> list, String codeLangue ) {
		this.blankDocuments = list;
		this.params = new String[1];
		this.params[0] = codeLangue;
	}

	public List<ModelBlankDocument> getElements() {
        return blankDocuments;
	}
}
