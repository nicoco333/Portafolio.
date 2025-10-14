const { DataTypes } = require('sequelize');
const sequelize = require('../sequelize');
const Producto = sequelize.define('Producto', {
    id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
    nombre: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    descripcion: {
        type: DataTypes.TEXT,
    },
    categoria: {
        type: DataTypes.STRING,
    },
    precio: {
        type: DataTypes.FLOAT,
    },
    }, {
    timestamps: false,
    });
    module.exports = Producto;