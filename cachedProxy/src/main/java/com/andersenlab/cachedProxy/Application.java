package com.andersenlab.cachedProxy;

import java.io.Serializable;

public class Application {

    static class CachedService {

        @Cached
        public int[] localCachedWork() {
            return new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        }

        @Cached(isExternal = true)
        public String hardWork(String arg) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {}
            return arg;
        }

        @Cached(isExternal = true)
        public int externalCachedWork(int i) {
            return i;
        }

        @Cached(isExternal = true, storageName = "externalStorage")
        public CacheEntry externalCachedWork2(int i) {
            return new CacheEntry(i);
        }

        private static class CacheEntry implements Serializable {
            private int i;

            public CacheEntry(int i) {
                this.i = i;
            }
        }

    }

    public static void main(String[] args) {
        CacheProxy proxy = new CacheProxy(new InmemoryMethodResultCache(), new ExternalMethodResultCache("./data"));
        CachedService proxied = proxy.cache(new CachedService());

        proxied.hardWork("message");
        proxied.externalCachedWork(1);
        proxied.hardWork("second message");
        proxied.externalCachedWork(2);
        proxied.hardWork("message");
        proxied.externalCachedWork2(3);
        proxied.hardWork("third message");
        proxied.externalCachedWork2(4);
        proxied.externalCachedWork2(5);
        proxied.localCachedWork();
        proxied.localCachedWork();
    }

}
