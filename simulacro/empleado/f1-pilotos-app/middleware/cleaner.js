// middleware/cleaner.js
const cleaner = (req, res, next) => {
    if (req.body && typeof req.body === 'object') {
        for (const key in req.body) {
            if (typeof req.body[key] === 'string') {
                // Eliminar espacios en blanco al inicio y final
                req.body[key] = req.body[key].trim();
                // Opcional: Podríamos añadir más limpieza o validación aquí
            }
        }
    }
    next(); // ¡Importante! Llama al siguiente middleware o ruta
};
module.exports = cleaner;