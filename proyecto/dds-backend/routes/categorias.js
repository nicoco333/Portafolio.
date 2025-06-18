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

// Crear una nueva categoría
router.post('/api/categorias', async (req, res) => {
  try {
    const { Nombre } = req.body; // Extrae el nombre de la categoría del cuerpo de la solicitud
    const nuevaCategoria = await categorias.create({ Nombre }); // Crea una nueva categoría
    res.status(201).json(nuevaCategoria); // Devuelve la nueva categoría con estado 201
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Error al crear la categoría' }); // Error 500 si falla la creación
  }
});

// Actualizar una categoría existente
router.put('/api/categorias/:id', async (req, res) => {
  try {
    const { Nombre } = req.body; // Extrae el nombre de la categoría del cuerpo de la solicitud
    const categoria = await categorias.findByPk(req.params.id); // Busca la categoría por ID
    if (categoria) {
      categoria.Nombre = Nombre; // Actualiza el nombre
      await categoria.save(); // Guarda los cambios
      res.json({ message: 'Categoría actualizada' }); // Devuelve un mensaje de éxito
    } else {
      res.status(404).json({ message: 'Categoría no encontrada' }); // Error 404 si no existe
    }
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Error al actualizar la categoría' }); // Error 500 si falla la actualización
  }
}
);

// Eliminar una categoría
router.delete('/api/categorias/:id', async (req, res) => {
  try {
    const categoria = await categorias.findByPk(req.params.id); // Busca la categoría por ID
    if (categoria) {
      await categoria.destroy(); // Elimina la categoría
      res.json({ message: 'Categoría eliminada' }); // Devuelve un mensaje de éxito
    } else {
      res.status(404).json({ message: 'Categoría no encontrada' }); // Error 404 si no existe
    }
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Error al eliminar la categoría' }); // Error 500 si falla la eliminación
  }
}
);

module.exports = router;
