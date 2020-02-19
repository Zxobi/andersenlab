package com.andersenlab.cachedProxy;

import java.lang.reflect.Method;
import java.util.*;

public class InmemoryMethodResultCache implements MethodResultCache {

    private Map<Method, Map<List<Object>, Object>> internalCache;

    public InmemoryMethodResultCache() {
        internalCache = new HashMap<>();
    }

    @Override
    public void set(Cached cached, Method method, Object[] args, Object result) {
        Map<List<Object>, Object> methodCache = internalCache.computeIfAbsent(method, k -> new HashMap<>());
        methodCache.put(toKey(args), result);
    }

    @Override
    public Object get(Cached cached, Method method, Object[] args) {
        Map<List<Object>, Object> methodCache = internalCache.get(method);
        return methodCache == null ? null : methodCache.get(toKey(args));
    }

    private List<Object> toKey(Object[] arr) {
        return Collections.unmodifiableList(Arrays.asList(arr));
    }
}
