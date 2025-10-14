// gamer-profile-backend/server.js
const express = require('express');
const cors = require('cors');
const app = express();
const PORT = process.env.PORT || 3001; // Puedes usar el puerto que prefieras
// Middlewares
app.use(cors()); // Habilita CORS para todas las rutas
app.use(express.json()); // Para parsear application/json
// Endpoint para recibir datos del perfil
app.post('/api/profile', (req, res) => {
    console.log("Datos del perfil recibidos en el backend:");
    console.log(req.body);
    // Aquí podrías guardar los datos en una base de datos, etc.
    // Por ahora, solo confirmamos la recepción.
    // Validaciones del lado del servidor (opcional, pero recomendado en producción)
    const { gamerTag, password_gamer, favoriteGame, platform, acceptTerms } = req.body;
    if (!gamerTag || !password_gamer || !favoriteGame || !platform || !acceptTerms) {
        return res.status(400).json({ message: "Faltan campos obligatorios en la solicitud." });
    }
    res.status(200).json({
        message: "Perfil recibido exitosamente en el servidor!", data: req.body
    });
});
// Iniciar el servidor
app.listen(PORT, () => {
    console.log(`Servidor corriendo en http://localhost:${PORT}`);
});
