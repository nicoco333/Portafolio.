const express = require('express');
const router = express.Router();
const categorias = require('../models/categoriasModel');


// Obtener todas las categorías
router.get('/api/categorias', async (req, res) => {
  try {
    const categoriass = await categorias.findAll();
    res.json(categoriass);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Error al obtener las categorías' });
  }
});

// Ejercicio: Implementar GET por ID
router.get('/api/categorias/:id', async (req, res) => {
  try {
    const categoria = await categorias.findByPk(req.params.id); // Busca por clave primaria
    if (categoria) {
      res.json(categoria);
    } else {
      res.status(404).json({ message: 'Categoría no encontrada' }); // Error 404 si no existe
    }
  } catch (error) {
    res.status(500).json({ error: 'Error al obtener la categoría' }); // Error 500 si falla la consulta
  }
});

module.exports = router;
