// middleware/errorHandler.js
const errorHandler = (err, req, res, next) => {
    console.error("----- ERROR CAPTURADO POR MIDDLEWARE -----");
    console.error(err.stack); // Registra el stack trace del error en consola
    // Determina el status y mensaje del error
    const statusCode = err.statusCode || 500;
    const message = err.message || 'Ocurrió un error interno en el servidor';
    // Envía la respuesta de error al cliente
    res.status(statusCode).json({
        error: message,
        // Opcional: No enviar detalles sensibles del error en producción
        // stack: process.env.NODE_ENV === 'development' ? err.stack : undefined
    });
};
module.exports = errorHandler;