package com.frc.isi.museo.app.servicios.interfaces;

public interface ILookUpOrPersistService<T> {
    T getOrCreateAutor(String descripcion);
}
