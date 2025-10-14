import React, { useState } from 'react';

function Encuestas({ encuestas }) {
  const [respuestas, setRespuestas] = useState({});

  const manejarCambio = (idEncuesta, opcionSeleccionada) => {
    setRespuestas({
      ...respuestas,
      [idEncuesta]: opcionSeleccionada,
    });
  };

  return (
    <div>
      {encuestas.map((encuesta) => (
        <div key={encuesta.id}>
          <h2>{encuesta.pregunta}</h2>
          {encuesta.opciones.map((opcion, index) => (
            <div key={index}>
              <label>
                <input
                  type="radio"
                  name={`encuesta-${encuesta.id}`}
                  value={opcion}
                  checked={respuestas[encuesta.id] === opcion}
                  onChange={() => manejarCambio(encuesta.id, opcion)}
                />
                {opcion}
              </label>
            </div>
          ))}
        </div>
      ))}
    </div>
  );
}

export default Encuestas;