const express = require('express');
const cors = require('cors');
const sequelize = require('./db');
const productRoutes = require('./routes/products');

const app = express();
const PORT = process.env.PORT || 3000;

app.use(cors());
app.use(express.json());
app.use(productRoutes);

// Sincronizar la base de datos y arrancar el servidor
sequelize.sync().then(() => {
    console.log('Base de datos conectada');
    app.listen(PORT, () => {
        console.log(`Servidor corriendo en http://localhost:${PORT}`);
    });
});