// app.js
const express = require('express');
const app = express();
const PORT = process.env.PORT || 3000;
const cors = require('cors');
const { connectDB } = require('./db/db');
const pilotosRouter = require('./routes/pilotos');
const loggerMiddleware = require('./middleware/logger');
const cleanerMiddleware = require('./middleware/cleaner');
const errorHandlerMiddleware = require('./middleware/errorHandler');
// --- Middleware Generales (Orden Importa) ---
app.use(loggerMiddleware); // 1. Primero el logger
app.use(cors()); // 2. Luego CORS (permite peticiones cruzadas)
app.use(express.json()); // 3. Parsing de JSON
app.use(express.urlencoded({ extended: true })); // 4. Parsing de URL-encoded
app.use(cleanerMiddleware); // 5. Limpieza de datos de entrada
// -----------------------------------------
// --- Servir archivos estáticos ---
app.use(express.static('public')); // <--- Sirve los archivos de la carpeta 'public'
// ---------------------------------
// --- Rutas API ---
app.use('/api/pilotos', pilotosRouter); // Nuestras rutas específicas
// -----------------
// --- Middleware de Manejo de Errores (¡SIEMPRE AL FINAL!) ---
app.use(errorHandlerMiddleware);
// ----------------------------------------------------------
// Conectar a la base de datos ANTES de iniciar el servidor
connectDB().then(() => {
    // Iniciamos el servidor
    app.listen(PORT, () => {
        console.log(`Servidor corriendo en http://localhost:${PORT}`);
        console.log(`Frontend disponible en http://localhost:${PORT}`); // Mensaje útil
    });
}).catch(err => {
    console.error("Falló la conexión a la base de datos. Saliendo...", err);
    process.exit(1);
});