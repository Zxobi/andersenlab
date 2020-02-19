package com.andersenlab.cachedProxy;

import java.lang.reflect.Method;

public interface MethodResultCache {

    Object get(Cached cached, Method method, Object[] args);
    void set(Cached cached, Method method, Object[] args, Object result);

}
