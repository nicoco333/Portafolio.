// db/db.js
const { Sequelize, DataTypes } = require('sequelize');
// Configuración de Sequelize para usar SQLite
const sequelize = new Sequelize({
    dialect: 'sqlite',
    storage: 'database.sqlite' // El archivo de la base de datos se creará aquí
});
// Definimos el modelo Piloto
const Piloto = sequelize.define('Piloto', {
    nombre: {
        type: DataTypes.STRING,
        allowNull: false
    },
    escuderia: {
        type: DataTypes.STRING,
        allowNull: false
    },
    pais: {
        type: DataTypes.STRING,
        allowNull: true // Permitimos que el país sea opcional
    },
    numeroCoche: {
        type: DataTypes.INTEGER,
        allowNull: true, // Permitimos que el número sea opcional
        unique: true // Aseguramos que no haya dos pilotos con el mismo número (en teoría)
    },
    campeonatos: {
        type: DataTypes.INTEGER,
        defaultValue: 0 // Por defecto, 0 campeonatos
    }
});
// Función para conectar y sincronizar la base de datos
const connectDB = async () => {
    try {
        await sequelize.authenticate();
        console.log('Conexión a la base de datos SQLite establecida correctamente.');
        // Sincroniza los modelos con la base de datos (crea la tabla si no existe)
        await sequelize.sync({ alter: true }); // 'alter: true' intenta adaptar la tabla si el modelo cambia
        console.log('Modelos sincronizados con la base de datos.');
    } catch (error) {
        console.error('Error al conectar o sincronizar la base de datos:', error);
        // Aquí podrías decidir si quieres que la app termine si la DB falla
        process.exit(1); // Termina la aplicación con código de error
    }
};
module.exports = { sequelize, Piloto, connectDB };