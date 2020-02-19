package com.andersenlab.cachedProxy;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

public class ExternalMethodResultCache implements MethodResultCache {

    private Map<Method, Map<List<Object>, Object>> internalCache;

    private String baseDir;

    public ExternalMethodResultCache(String baseDir) {
        this.baseDir = baseDir;
        internalCache = new HashMap<>();
    }

    @Override
    public Object get(Cached cached, Method method, Object[] args) {
        Map<List<Object>, Object> methodCache = getMethodCacheOrLoad(buildFilename(cached, method), method);
        return methodCache == null ? null : methodCache.get(toKey(args));
    }

    @Override
    public void set(Cached cached, Method method, Object[] args, Object result) {
        Map<List<Object>, Object> methodCache = getMethodCacheOrLoad(buildFilename(cached, method), method);
        if (methodCache == null) methodCache = new HashMap<>();
        methodCache.put(toKey(args), result);
        saveMethodCache(buildFilename(cached, method), methodCache);
    }

    private String buildFilename(Cached cached, Method method) {
        return Objects.toString(baseDir, ".") + '/' +
                (cached.storageName().isEmpty() ?
                        String.format("%s$%d", method.getName(), method.hashCode()) :
                        cached.storageName());
    }

    private Map<List<Object>, Object> loadMethodCache(String filename) {
        File file = new File(filename);
        Map<List<Object>, Object> result = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            result =(Map<List<Object>, Object>) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
        } catch (Exception ex) {}

        return result;
    }

    private void saveMethodCache(String filename, Map<List<Object>, Object> methodCache) {
        File file = new File(filename);
        try {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(methodCache);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException ex) {
            throw new RuntimeException("Unable to create or write to file " + filename, ex);
        }
    }

    private Map<List<Object>, Object> getMethodCacheOrLoad(String filename, Method method) {
        Map<List<Object>, Object> methodCache = internalCache.get(method);
        if (methodCache == null) {
            methodCache = loadMethodCache(filename);
            if (methodCache == null) return null;
            else internalCache.put(method, methodCache);
        }

        return methodCache;
    }

    private List<Object> toKey(Object[] arr) {
        return Collections.unmodifiableList(Arrays.asList(arr));
    }
}
