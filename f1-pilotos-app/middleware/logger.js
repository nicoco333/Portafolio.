// middleware/logger.js
const logger = (req, res, next) => {
    const start = Date.now();
    console.log(`[${new Date().toISOString()}] ${req.method} ${req.url}`);
    // Aquí podríamos añadir lógica para cuando la respuesta termina
    res.on('finish', () => {
        const duration = Date.now() - start;
        console.log(`[${new Date().toISOString()}] ${req.method} ${req.url} - Status:
    ${res.statusCode} - Time: ${duration}ms`);
    });
    next(); // ¡Importante! Llama al siguiente middleware o ruta
};
module.exports = logger;