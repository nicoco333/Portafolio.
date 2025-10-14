// src/components/Publicacion.js
import React, { useState } from 'react'; // 1. Importar useState
function Publicacion({ autor, titulo, contenido }) {
    // 2. Usar useState para manejar los likes
    // 'likes' es la variable de estado, 'setLikes' es la función para actualizarla.
    // Se inicializa 'likes' en 0.
    const [likes, setLikes] = useState(0);
    // 3. Función para manejar el clic en el botón de "Me Gusta"
    const handleLike = () => {
// Usamos la forma funcional de setLikes para asegurar que usamos el valor previo más reciente.
            setLikes(prevLikes => prevLikes + 1);
    };
    return (
        <div style={{
            border: '1px solid #ccc', margin: '10px', padding: '10px',
            borderRadius: '5px'
        }}>
            <h3>{titulo}</h3>
            <h4>Por: {autor}</h4>
            <p>{contenido}</p>
            {/* 4. Botón y muestra de likes */}
            <button onClick={handleLike}>Me Gusta 👍</button>
            <span style={{ marginLeft: '10px' }}>({likes} {likes === 1 ? 'like' :
                'likes'})</span>
        </div>
    );
}
export default Publicacion;