// public/script.js
document.addEventListener('DOMContentLoaded', () => {
    const pilotosList = document.getElementById('pilotos-list');
    const addPilotoForm = document.getElementById('add-piloto-form');
    // Función para cargar y mostrar los pilotos
    const fetchPilotos = async () => {
        try {
            const response = await fetch('/api/pilotos'); // Llama a nuestra API
            if (!response.ok) {
                throw new Error(`Error HTTP: ${response.status}`);
            }
            const pilotos = await response.json(); // Parsear la respuesta JSON
            // Limpiar la lista actual
            pilotosList.innerHTML = '';
            if (pilotos.length === 0) {
                pilotosList.innerHTML = '<li class="list-group-item text-center text-muted">No hay pilotos cargados todavía.</li > ';
            } else {
                // Mostrar cada piloto en la lista
                pilotos.forEach(piloto => {
                    const li = document.createElement('li');
                    li.classList.add('list-group-item');
                    li.innerHTML = `
    <strong>${piloto.nombre}</strong> (${piloto.escuderia}) - ${piloto.pais ||
                        'N/A'} | #${piloto.numeroCoche || 'N/A'} | ${piloto.campeonatos} Campeonatos
    <!-- Opcional: Agregar botones de editar/eliminar aquí -->
    `;
                    pilotosList.appendChild(li);
                });
            }
        } catch (error) {
            console.error('Error al cargar los pilotos:', error);
            pilotosList.innerHTML = `<li class="list-group-item text-danger">Error al cargar
    pilotos: ${error.message}</li>`;
        }
    };
    // Manejar el envío del formulario
    addPilotoForm.addEventListener('submit', async (event) => {
        event.preventDefault(); // Evitar que la página se recargue
        // Obtener datos del formulario
        const nombre = document.getElementById('nombre').value;
        const escuderia = document.getElementById('escuderia').value;
        const pais = document.getElementById('pais').value;
        const numeroCoche = document.getElementById('numeroCoche').value;
        const campeonatos = document.getElementById('campeonatos').value;
        const nuevoPiloto = {
            nombre,
            escuderia,
            pais: pais || null, // Enviar null si está vacío
            numeroCoche: numeroCoche ? parseInt(numeroCoche, 10) : null, // Convertir a número o null 
            campeonatos: campeonatos? parseInt(campeonatos, 10): null
        };
        try {
            const response = await fetch('/api/pilotos', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(nuevoPiloto), // Enviar los datos como JSON
            });
            const result = await response.json();
            if (!response.ok) {
                // Si la respuesta no es OK, lanzar un error con el mensaje del backend
                const errorMessage = result.error || 'Error al agregar piloto';
                throw new Error(`Error al agregar piloto: ${response.status} -
    ${errorMessage}`);
            }
            console.log('Piloto agregado:', result);
            // Limpiar el formulario
            addPilotoForm.reset();
            // Recargar la lista de pilotos para mostrar el nuevo
            fetchPilotos();
        } catch (error) {
            console.error('Error al agregar el piloto:', error);
            // Mostrar un mensaje de error al usuario (simple alerta por ahora)
            alert(`No se pudo agregar el piloto: ${error.message}`);
        }
    });
    // Cargar los pilotos cuando la página se cargue
    fetchPilotos();
});
