// frontend/script.js
document.addEventListener('DOMContentLoaded', () => {
    const countriesContainer = document.getElementById('countries-container');
    const loadingMessage = document.getElementById('loading-message');
    // URL de nuestra API del backend
    const API_URL = 'http://localhost:3000/api/countries';
    // Función para obtener los datos de los países
    async function fetchCountries() {
        try {
            // Ocultar mensaje de carga
            if (loadingMessage) {
                loadingMessage.style.display = 'none';
            }
            // Realizar la petición GET a la API
            const response = await fetch(API_URL);
            // Verificar si la respuesta es exitosa
            if (!response.ok) {
                // Si no es exitosa, lanzar un error
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            // Convertir la respuesta a JSON
            const countries = await response.json();
            // Limpiar el contenedor antes de renderizar (si no está vacío)
            countriesContainer.innerHTML = ''; // Limpiamos cualquier contenido previo (como el mensaje de carga si no lo ocultamos antes)
            // Renderizar los países en el HTML
            renderCountries(countries);
        } catch (error) {
            console.error('Error al obtener los países:', error);
            // Mostrar un mensaje de error al usuario
            countriesContainer.innerHTML = `<p class="text-danger">Error al cargar los
    países: ${error.message}</p>`;
        }
    }
    // Función para renderizar los países en el DOM
    function renderCountries(countries) {
        if (!countries || countries.length === 0) {
            countriesContainer.innerHTML = '<p>No se encontraron países.</p>';
            return;
        }
        countries.forEach(country => {
            // Crear un div para la columna (Bootstrap Grid)
            const colDiv = document.createElement('div');
            colDiv.classList.add('col'); // Clase para columna en Bootstrap Grid
            // Crear un div para la tarjeta de Bootstrap
            const cardDiv = document.createElement('div');
            cardDiv.classList.add('card', 'h-100', 'country-card'); // h-100 para que todas tengan la misma altura
            // Crear el cuerpo de la tarjeta
            const cardBodyDiv = document.createElement('div');
            cardBodyDiv.classList.add('card-body');
            // Agregar contenido a la tarjeta
            const flagImg = document.createElement('img');
            flagImg.src = country.flag || 'placeholder.svg'; // Usar una imagen placeholder si no hay bandera
            flagImg.alt = `Bandera de ${country.name}`;
            flagImg.classList.add('country-flag'); // Clase CSS personalizada para la bandera
            const nameElement = document.createElement('h5');
            nameElement.classList.add('card-title');
            nameElement.textContent = country.name;
            const populationElement = document.createElement('p');
            populationElement.classList.add('card-text');
            populationElement.textContent = `Población: ${country.population ?
                country.population.toLocaleString() : 'N/D'}`; // Formato de número
            const currencyElement = document.createElement('p');
            currencyElement.classList.add('card-text');
            currencyElement.textContent = `Moneda: ${country.currency || 'N/D'}`;
            // Ensamblar la tarjeta
            cardBodyDiv.appendChild(flagImg);
            cardBodyDiv.appendChild(nameElement);
            cardBodyDiv.appendChild(populationElement);
            cardBodyDiv.appendChild(currencyElement);
            cardDiv.appendChild(cardBodyDiv);
            colDiv.appendChild(cardDiv); // Añadir la tarjeta a la columna
            // Añadir la columna al contenedor principal
            countriesContainer.appendChild(colDiv);
        });
    }
    // Llamar a la función para obtener y mostrar los países cuando el DOM esté listo
    fetchCountries();
});
