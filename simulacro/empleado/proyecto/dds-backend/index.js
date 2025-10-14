const express = require("express");

// crear servidor
const app = express();
app.use(express.json()); // para poder leer json en el body

const inicializarBase = require("./models/inicializarBase");  // inicializar base de datos

// controlar ruta
app.get("/", (req, res) => {
  res.send("Backend inicial dds-backend!");
});
app.get('/_isalive', (req, res) => {
  res.status(200).send('Ejecutandose desde: dds-backend');
});

const categoriasmockRouter = require("./routes/categoriasmock");
app.use(categoriasmockRouter);

const categoriasRouter = require("./routes/categorias");
app.use(categoriasRouter);

const articulosRouter = require("./routes/articulos");
app.use(articulosRouter);

// configurar servidor
const cors = require("cors");
app.use(
  cors({
    origin: "*", // origin: 'https://dds-frontend.azurewebsites.net'
  })
);

const seguridadRouter = require("./routes/seguridad");
app.use(seguridadRouter);
const usuariosRouter = require("./routes/usuarios");
app.use(usuariosRouter);

app.use((req, res) => {
  res.status(404).send('No encontrada!');
});

// levantar servidor
const port = 3000;
app.locals.fechaInicio = new Date();  // fecha y hora inicio de aplicacion
if (require.main === module) {   // si no es llamado por otro módulo, es decir, si es el módulo principal -> levantamos el servidor
  inicializarBase().then(() => {
   app.listen(port, () => {
    console.log(`sitio escuchando en el puerto ${port}`);
    });
  });
}
module.exports = app; // para testing


