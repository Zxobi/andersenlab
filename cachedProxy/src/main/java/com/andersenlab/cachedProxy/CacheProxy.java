package com.andersenlab.cachedProxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CacheProxy {

    private MethodResultCache localCache;
    private MethodResultCache externalCache;

    public CacheProxy(MethodResultCache localCache, MethodResultCache externalCache) {
        this.localCache = localCache;
        this.externalCache = externalCache;
    }

    public <T> T cache (T service) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(service.getClass());
        enhancer.setCallback(new CacheMethodInterceptor());
        return (T) enhancer.create();
    }

    private MethodResultCache getMethodResultCache(Cached cachedAnnotation) {
        return cachedAnnotation.isExternal() ? externalCache : localCache;
    }

    private class CacheMethodInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            Cached cachedAnnotation = method.getAnnotation(Cached.class);
            if (cachedAnnotation == null) return methodProxy.invokeSuper(o, args);
            MethodResultCache cache = getMethodResultCache(cachedAnnotation);
            Object result = cache.get(cachedAnnotation, method, args);
            if (result == null) {
                result = methodProxy.invokeSuper(o, args);
                cache.set(cachedAnnotation, method, args, result);
            }

            return result;
        }
    }
}
