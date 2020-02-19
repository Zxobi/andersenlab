package com.andersenlab.cachedProxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

public class CacheProxy {

    private String baseDir;

    private HashMap<Method, HashMap<List<Object>, Object>> internalCache = new HashMap<>();

    public CacheProxy() {

    }

    public CacheProxy(String baseDir) {
        this.baseDir = baseDir;
    }

    public <T> T cache (T service) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(service.getClass());
        enhancer.setCallback(new CacheMethodInterceptor());
        return (T) enhancer.create();
    }

    private class CacheMethodInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            Cached cachedAnnotation = method.getAnnotation(Cached.class);
            if (cachedAnnotation == null) return methodProxy.invokeSuper(o, args);

            List<Object> argsList = Arrays.asList(args);
            HashMap<List<Object>, Object> cache;

            if (cachedAnnotation.isExternal()) {
                String filepath = Objects.toString(baseDir, ".") + "/" +
                        (cachedAnnotation.storageName().isEmpty() ?
                                String.format("%s$%d", method.getName(), method.hashCode()) :
                                cachedAnnotation.storageName());

                cache = internalCache.get(method);
                if (cache == null) {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(filepath);
                        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                        cache = (HashMap<List<Object>, Object>) objectInputStream.readObject();
                        internalCache.put(method, cache);

                        objectInputStream.close();
                        fileInputStream.close();
                    } catch (FileNotFoundException ex) {
                        cache = new HashMap<>();
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException("Corrupted file " + filepath, ex);
                    } catch (IOException ex) {
                        throw new RuntimeException("Unable to read file " + filepath, ex);
                    }
                }

                Object result = cache.get(argsList);
                if (result == null) {
                    result = methodProxy.invokeSuper(o, args);
                    cache.put(Collections.unmodifiableList(argsList), result);
                }

                try {
                    File file = new File(filepath);
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file, false);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    objectOutputStream.writeObject(cache);
                    objectOutputStream.flush();
                    objectOutputStream.close();
                    fileOutputStream.close();
                } catch (IOException ex) {
                    throw new RuntimeException("Unable to create or write to file " + filepath, ex);
                }

                return result;
            } else {
                cache = internalCache.computeIfAbsent(method, k -> new HashMap<>());
                Object result = cache.get(argsList);
                if (result == null) {
                    result = methodProxy.invokeSuper(o, args);
                    cache.put(Collections.unmodifiableList(argsList), result);
                }

                return result;
            }
        }
    }
}
