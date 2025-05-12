const Product = require('../models/Product');

// Obtener todos los productos
const getProducts = async (req, res) => {
    const products = await Product.findAll();
    res.json(products);
};

// Obtener un producto por ID
const getProductById = async (req, res) => {
    const product = await Product.findByPk(req.params.id);
    if (product) {
        res.json(product);
    } else {
        res.status(404).json({ error: 'Producto no encontrado' });
    }
};

// Crear un nuevo producto
const createProduct = async (req, res) => {
    const product = await Product.create(req.body);
    res.status(201).json(product);
};

// Actualizar un producto
const updateProduct = async (req, res) => {
    const product = await Product.findByPk(req.params.id);
    if (product) {
        await product.update(req.body);
        res.json(product);
    } else {
        res.status(404).json({ error: 'Producto no encontrado' });
    }
};

// Eliminar un producto
const deleteProduct = async (req, res) => {
    const product = await Product.findByPk(req.params.id);
    if (product) {
        await product.destroy();
        res.json({ message: 'Producto eliminado' });
    } else {
        res.status(404).json({ error: 'Producto no encontrado' });
    }
};

module.exports = {
    getProducts,
    getProductById,
    createProduct,
    updateProduct,
    deleteProduct
};