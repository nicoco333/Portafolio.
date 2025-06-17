const express = require("express");

// crear servidor
const app = express();
app.use(express.json()); // para poder leer json en el body

// controlar ruta
app.get("/", (req, res) => {
    res.send("Backend inicial dds-backend!");
});

const categoriasmockRouter = require("./routes/categoriasmock");
app.use(categoriasmockRouter);


// levantar servidor
const port = 3000;
app.locals.fechaInicio = new Date();  // fecha y hora inicio de aplicacion
app.listen(port, () => {
    console.log(`sitio escuchando en el puerto ${port}`);
});
