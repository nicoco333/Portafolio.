package com.frc.isi.museo.app.menu;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    private ApplicationContext() {
    }

    private static class Holder {
        private final static ApplicationContext INSTANCE = new ApplicationContext();
    }

    public static ApplicationContext getInstance() {
        return Holder.INSTANCE;
    }

    private final Map<String, Object> store = new ConcurrentHashMap<>();

    public void put(String key, Object value) {
        store.put(key, value);
    }

    public <T> T get(String key, Class<T> type) {
        return type.cast(store.get(key));
    }

    public Object remove(String key) {
        return store.remove(key);
    }

    public boolean contains(String key) {
        return store.containsKey(key);
    }

    public <S> void registerService(Class<S> entityClass, S service) {
        store.put(entityClass.getName(), service);
    }

    @SuppressWarnings("unchecked")
    public <S> S getService(Class<S> entityClass) {
        return (S) store.get(entityClass.getName());
    }

    public boolean set(String key, Object value) {
        return store.replace(key, value) != null;
    }

    // (Opcional) variante estricta que lanza excepción si la clave NO existe
    public void setOrThrow(String key, Object value) {
        if (!store.containsKey(key)) {
            throw new NoSuchElementException("No existe la clave: " + key);
        }
        store.put(key, value);
    }

    
}
