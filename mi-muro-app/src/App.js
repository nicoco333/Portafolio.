import React, { useState, useEffect } from 'react';
import Publicacion from './components/Publicacion';
import FormularioNuevaPublicacion from './components/FormularioNuevaPublicacion';

function App() {
  const [publicaciones, setPublicaciones] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    setTimeout(() => {
      setPublicaciones([
        { id: 1, autor: 'Ana Coder', titulo: 'Mi primer día con React', contenido: '¡Hoy empecé a aprender React!' },
        { id: 2, autor: 'Luis Dev', titulo: 'useEffect al rescate', contenido: 'Descubriendo cómo usar efectos secundarios.' }
      ]);
      setIsLoading(false);
    }, 1500);
  }, []);

  const handleNuevaPublicacion = (datos) => {
    const nueva = { ...datos, id: Date.now() };
    setPublicaciones(prev => [nueva, ...prev]);
  };

  return (
    <div>
      <h1>Muro de Novedades Interactivo</h1>
      <FormularioNuevaPublicacion onNuevaPublicacion={handleNuevaPublicacion} />
      {isLoading ? (
        <p>Cargando publicaciones...</p>
      ) : (
        publicaciones.length === 0 ? (
          <p>No hay publicaciones aún.</p>
        ) : (
          publicaciones.map(pub => (
            <Publicacion
              key={pub.id}
              autor={pub.autor}
              titulo={pub.titulo}
              contenido={pub.contenido}
            />
          ))
        )
      )}
    </div>
  );
}

export default App;
