// routes/pilotos.js
const express = require('express');
const router = express.Router();
const { Piloto } = require('../db/db'); // Importamos el modelo Piloto
// Ruta para obtener todos los pilotos
// GET /api/pilotos
router.get('/', async (req, res) => {
    try {
        const pilotos = await Piloto.findAll(); // Busca todos los pilotos en la DB
        res.json(pilotos); // Devuelve la lista como JSON
    } catch (error) {
        console.error("Error al obtener pilotos:", error);
        // Importante: Propagamos el error al middleware de manejo de errores
        res.status(500).json({ error: 'Error al obtener pilotos' });
    }
});
// Ruta para crear un nuevo piloto
// POST /api/pilotos
router.post('/', async (req, res) => {
    try {
        const nuevoPiloto = await Piloto.create(req.body); // Crea un nuevo piloto con los datos del body
        res.status(201).json(nuevoPiloto); // Devuelve el piloto creado con status 201
        (Created)
    } catch (error) {
        console.error("Error al crear piloto:", error);
        // Importante: Propagamos el error al middleware de manejo de errores
        res.status(400).json({ error: error.message }); // Devuelve un error 400 si hay problemas con los datos
    }
});
// TODO: Aquí podríamos agregar rutas para GET individual, PUT, DELETE
module.exports = router; // Exportamos el router