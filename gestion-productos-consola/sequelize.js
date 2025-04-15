const { Sequelize } = require('sequelize');
const sequelize = new Sequelize({
    dialect: 'sqlite',
    storage: 'productos_app.sqlite',
    logging: false,
});
module.exports = sequelize;