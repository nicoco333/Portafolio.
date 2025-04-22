// backend/index.js
const express = require('express');
const cors = require('cors'); // Importamos cors
const { Sequelize, DataTypes } = require('sequelize'); // Importamos Sequelize y DataTypes
const app = express();
const port = 3000; // Puerto donde correrá nuestro backend

// Middlewares
app.use(cors()); // Habilita CORS para todas las rutas
app.use(express.json()); // Permite que Express lea JSON en las peticiones

// --- Configuración de Sequelize ---
// Creamos una instancia de Sequelize conectando a un archivo SQLite
const sequelize = new Sequelize({
    dialect: 'sqlite',
    storage: './database.sqlite' // El archivo de la base de datos se llamará database.sqlite
});
// Definimos el modelo Country (representa la tabla 'Countries' en la base de datos)
const Country = sequelize.define('Country', {
    name: {
        type: DataTypes.STRING,
        allowNull: false,
        unique: true // Aseguramos que el nombre del país sea único
    },
    flag: {
        type: DataTypes.STRING, // Guardaremos la URL de la bandera
        allowNull: true // Podría ser que no tengamos la URL para todos
    },
    population: {
        type: DataTypes.INTEGER,
        allowNull: true
    },
    currency: {
        type: DataTypes.STRING, // Podríamos guardar el código o el nombre de la moneda
        allowNull: true
    }
});
// Sincronizar el modelo con la base de datos (crea la tabla si no existe)
// { force: true } borra la tabla si existe y la vuelve a crear. Útil en desarrollo.
// ¡Cuidado con esto en producción!
sequelize.sync({ force: true })
    .then(() => {
        console.log('Base de datos sincronizada. Tablas creadas/actualizadas.');
        // Aquí podríamos agregar la lógica para cargar datos iniciales (seeding)
        seedDatabase(); // Llamamos a la función de seeding
    })
    .catch(err => {
        console.error('Error al sincronizar la base de datos:', err);
    });
// --- Función para cargar datos iniciales (Seeding) ---
async function seedDatabase() {
    try {
        const count = await Country.count();
        if (count === 0) {
            console.log('Base de datos vacía. Insertando datos iniciales...');
            const latinAmericanCountries = [
                {
                    name: 'Argentina', flag: 'https://flagcdn.com/ar.svg', population: 45376763,
                    currency: 'ARS'
                },
                {
                    name: 'Bolivia', flag: 'https://flagcdn.com/bo.svg', population: 11693337,
                    currency: 'BOB'
                },
                {
                    name: 'Brasil', flag: 'https://flagcdn.com/br.svg', population: 212559417,
                    currency: 'BRL'
                },
                {
                    name: 'Chile', flag: 'https://flagcdn.com/cl.svg', population: 19116209,
                    currency: 'CLP'
                },
                {
                    name: 'Colombia', flag: 'https://flagcdn.com/co.svg', population: 50882884,
                    currency: 'COP'
                },
                {
                    name: 'Ecuador', flag: 'https://flagcdn.com/ec.svg', population: 17643060,
                    currency: 'USD'
                }, // Usan USD
                {
                    name: 'México', flag: 'https://flagcdn.com/mx.svg', population: 128932753,
                    currency: 'MXN'
                },
                {
                    name: 'Paraguay', flag: 'https://flagcdn.com/py.svg', population: 7132530,
                    currency: 'PYG'
                },
                {
                    name: 'Perú', flag: 'https://flagcdn.com/pe.svg', population: 32971846,
                    currency: 'PEN'
                },
                {
                    name: 'Uruguay', flag: 'https://flagcdn.com/uy.svg', population: 3473727,
                    currency: 'UYU'
                }
            ];
            await Country.bulkCreate(latinAmericanCountries);
            console.log('Datos iniciales insertados correctamente.');
        } else {
            console.log('La base de datos ya contiene datos. Saltando inserción inicial.');
        }
    } catch (error) {
        console.error('Error al insertar datos iniciales:', error);
    }
}

// --- Rutas ---

// Ruta de prueba
app.get('/', (req, res) => {
    res.send('¡Backend de Países Funcionando!');
});
// Ruta para obtener todos los países
app.get('/api/countries', async (req, res) => {
    try {
        const countries = await Country.findAll(); // Busca todos los registros en la tabla
        Country
        res.json(countries); // Envía la lista de países como JSON
    } catch (error) {
        console.error('Error al obtener países:', error);
        res.status(500).json({ error: 'Error interno del servidor al obtener países.' });
    }
});

// Iniciar el servidor (asegurarse de que esto esté al final)
// ¡Importante! Iniciar el servidor solo después de intentar sincronizar la DB
// Aunque con force:true y async/await en seedDatabase, la sincronización y el seeding
// deberían terminar antes de que `sequelize.sync` termine, es una buena práctica
// asegurarse de que la DB esté lista antes de aceptar peticiones.
// En este caso simple, como sync está al inicio y seedDatabase es llamada dentro de su then,
// el server se inicia independientemente del seed, lo cual está bien para esta ejercitación
// Iniciar el servidor
app.listen(port, () => {
    console.log(`Servidor escuchando en http://localhost:${port}`);
});