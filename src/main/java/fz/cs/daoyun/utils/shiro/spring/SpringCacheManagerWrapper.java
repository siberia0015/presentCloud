/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package fz.cs.daoyun.utils.shiro.spring;

import net.sf.ehcache.Ehcache;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.*;


public class SpringCacheManagerWrapper implements CacheManager {

    private org.springframework.cache.CacheManager cacheManager;

    /**
     * 设置spring cache manager
     *
     * @param cacheManager
     */
    public void setCacheManager(org.springframework.cache.CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        org.springframework.cache.Cache springCache = cacheManager.getCache(name);
        return new SpringCacheWrapper(springCache);
    }

    static class SpringCacheWrapper implements Cache {
        private org.springframework.cache.Cache springCache;

        SpringCacheWrapper(org.springframework.cache.Cache springCache) {

            this.springCache = springCache;
        }

        @Override
        public Object get(Object key) throws CacheException {
            if(springCache != null){
                Object value = springCache.get(key);
//                System.out.println("------------get1: " + key + " :   " + value);

                if (value instanceof SimpleValueWrapper) {

//                value = ((SimpleValueWrapper) value).get();
                    value = ((SimpleValueWrapper) value);
                    if(value != null){
                        value = ((SimpleValueWrapper) value).get();
                    }
//                    System.out.println("------------get2: " + key + " :   " + value);
                    return value;

                }

                return value;
            }else {
                return null;
            }


        }

        @Override
        public Object put(Object key, Object value) throws CacheException {

            if(springCache != null){
                springCache.put(key, value);
//                System.out.println("++++++++++++++put:  " + key +"  :  "+value);
            }

            return value;
        }

        @Override
        public Object remove(Object key) throws CacheException {
            if(springCache != null){
                springCache.evict(key);
            }

            return null;
        }

        @Override
        public void clear() throws CacheException {
            springCache.clear();
        }

        @Override
        public int size() {
            if (springCache.getNativeCache() instanceof Ehcache) {
                Ehcache ehcache = (Ehcache) springCache.getNativeCache();
                return ehcache.getSize();
            }
            throw new UnsupportedOperationException("invoke spring cache abstract size method not supported");
        }

        @Override
        public Set keys() {
            if (springCache.getNativeCache() instanceof Ehcache) {
                Ehcache ehcache = (Ehcache) springCache.getNativeCache();
                return new HashSet(ehcache.getKeys());
            }
            throw new UnsupportedOperationException("invoke spring cache abstract keys method not supported");
        }

        @Override
        public Collection values() {
            if (springCache.getNativeCache() instanceof Ehcache) {
                Ehcache ehcache = (Ehcache) springCache.getNativeCache();
                List keys = ehcache.getKeys();
                if (!CollectionUtils.isEmpty(keys)) {
                    List values = new ArrayList(keys.size());
                    for (Object key : keys) {
                        Object value = get(key);
                        if (value != null) {
                            values.add(value);
                        }
                    }
                    return Collections.unmodifiableList(values);
                } else {
                    return Collections.emptyList();
                }
            }
            throw new UnsupportedOperationException("invoke spring cache abstract values method not supported");
        }
    }
}
