// src/components/FormularioNuevaPublicacion.js
import React, { useState } from 'react';
// Este componente recibe una función 'onNuevaPublicacion' como prop desde App.js
function FormularioNuevaPublicacion({ onNuevaPublicacion }) {
    // Estados para cada campo del formulario
    const [autor, setAutor] = useState('');
    const [titulo, setTitulo] = useState('');
    const [contenido, setContenido] = useState('');
    const handleSubmit = (event) => {
        event.preventDefault(); // Previene el comportamiento por defecto del formulario(recargar la página)
        if (!autor.trim() || !titulo.trim() || !contenido.trim()) {
            alert("Todos los campos son obligatorios.");
            return;
        }
        // Llamamos a la función pasada por props, enviando los datos del formulario
        onNuevaPublicacion({ autor, titulo, contenido });
        // Limpiamos los campos después de enviar
        setAutor('');
        setTitulo('');
        setContenido('');
    };
    return (
        <form onSubmit={handleSubmit} style={{
            marginBottom: '20px', padding:
                '15px', border: '1px solid green', borderRadius: '5px'
        }}>
            <h2>Crear Nueva Publicación</h2>
            <div>
                <label htmlFor="autor">Autor: </label>
                <input
                    type="text"
                    id="autor"
                    value={autor} // El valor del input está ligado al estado 'autor'
                    onChange={(e) => setAutor(e.target.value)} // Actualiza el estado 'autor'
                    cuando el input cambia
                    required
                />
            </div>
            <div style={{ marginTop: '10px' }}>
                <label htmlFor="titulo">Título: </label>
                <input
                    type="text"
                    id="titulo"
                    value={titulo}
                    onChange={(e) => setTitulo(e.target.value)}
                    required
                />
            </div>
            <div style={{ marginTop: '10px' }}>
                <label htmlFor="contenido">Contenido: </label>
                <textarea
                    id="contenido"
                    value={contenido}
                    onChange={(e) => setContenido(e.target.value)}
                    required
                    rows="4"
                    style={{ width: "90%" }}
                />
            </div>
            <button type="submit" style={{ marginTop: '10px' }}>Publicar</button>
        </form>
    );
}
export default FormularioNuevaPublicacion;