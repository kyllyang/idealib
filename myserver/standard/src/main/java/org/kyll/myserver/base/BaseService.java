package org.kyll.myserver.base;

/**
 * User: Kyll
 * Date: 2014-11-08 13:01
 */
public interface BaseService<T, P> {
	T get(P p);
}
