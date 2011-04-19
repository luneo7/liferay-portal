/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.cache.transactional;

import com.liferay.portal.kernel.cache.BasePortalCache;
import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Shuyang Zhou
 * @author Edward Han
 */
public class TransactionalPortalCache extends BasePortalCache {

	public TransactionalPortalCache(PortalCache portalCache) {
		_portalCache = portalCache;
	}

	public Collection<Object> get(Collection<String> keys) {
		List<Object> values = new ArrayList<Object>(keys.size());

		for (String key : keys) {
			values.add(get(key));
		}

		return values;
	}

	public Object get(String key) {
		Object result = null;

		if (TransactionalPortalCacheHelper.isEnabled()) {
			result = TransactionalPortalCacheHelper.get(_portalCache, key);

			if (result == _nullHolder) {
				return null;
			}
		}

		if (result == null) {
			result = _portalCache.get(key);
		}

		return result;
	}

	public void put(String key, Object value) {
		if (TransactionalPortalCacheHelper.isEnabled()) {
			if (value == null) {
				value = _nullHolder;
			}

			TransactionalPortalCacheHelper.put(_portalCache, key, value);
		}
		else {
			_portalCache.put(key, value);
		}
	}

	public void put(String key, Object value, int timeToLive) {
		if (TransactionalPortalCacheHelper.isEnabled()) {
			if (value == null) {
				value = _nullHolder;
			}

			TransactionalPortalCacheHelper.put(_portalCache, key, value);
		}
		else {
			_portalCache.put(key, value, timeToLive);
		}
	}

	public void put(String key, Serializable value) {
		if (TransactionalPortalCacheHelper.isEnabled()) {
			if (value == null) {
				value = _nullHolder;
			}

			TransactionalPortalCacheHelper.put(_portalCache, key, value);
		}
		else {
			_portalCache.put(key, value);
		}
	}

	public void put(String key, Serializable value, int timeToLive) {
		if (TransactionalPortalCacheHelper.isEnabled()) {
			if (value == null) {
				value = _nullHolder;
			}

			TransactionalPortalCacheHelper.put(_portalCache, key, value);
		}
		else {
			_portalCache.put(key, value, timeToLive);
		}
	}

	public void registerCacheListener(CacheListener cacheListener)
		throws PortalCacheException {

		_portalCache.registerCacheListener(cacheListener);
	}

	public void registerCacheListener(
			CacheListener cacheListener, CacheListenerScope cacheListenerScope)
		throws PortalCacheException {

		_portalCache.registerCacheListener(cacheListener, cacheListenerScope);
	}

	public void remove(String key) {
		if (TransactionalPortalCacheHelper.isEnabled()) {
			TransactionalPortalCacheHelper.remove(_portalCache, key);
		}

		_portalCache.remove(key);
	}

	public void removeAll() {
		if (TransactionalPortalCacheHelper.isEnabled()) {
			TransactionalPortalCacheHelper.removeAll(_portalCache);
		}

		_portalCache.removeAll();
	}

	public void unregisterCacheListener(CacheListener cacheListener) {
		_portalCache.unregisterCacheListener(cacheListener);
	}

	public void unregisterCacheListeners() {
		_portalCache.unregisterCacheListeners();
	}

	private static Serializable _nullHolder = new String();

	private PortalCache _portalCache;

}