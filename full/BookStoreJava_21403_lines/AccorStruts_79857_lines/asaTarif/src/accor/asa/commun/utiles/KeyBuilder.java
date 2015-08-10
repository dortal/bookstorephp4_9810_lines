package com.accor.asa.commun.utiles;

/**
 * Interface utilisée par la méthode Outils.getIndexedMap, 
 * permettant au développeur de spécifier quelle clé utiliser pour indexer une liste 
 */
public interface KeyBuilder {
	/**
	 * Cree une cle d'index pour un element donné.
	 * Attention, si la clé est un objet métier, ne pas oublier de définir ses méthodes equals() et hashCode()
	 */
	public Object getKey(Object element);
}
