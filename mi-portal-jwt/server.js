// server.js
require('dotenv').config(); // Para variables de entorno
const express = require('express');
const jwt = require('jsonwebtoken');
const helmet = require('helmet'); // Seguridad HTTP
const app = express();
const PORT = process.env.PORT || 3001;

// Configuración básica de seguridad y middleware
app.use(helmet());
app.use(express.json());

// Clave JWT desde variables de entorno (crea un archivo .env)
const JWT_SECRET = process.env.JWT_SECRET || 'clave-super-secreta-para-desarrollo';

// Base de datos simulada (en producción usa bcrypt para contraseñas)
const users = [
  { id: 1, username: 'ana.perez', password: 'password123', role: 'alumno', nombre: 'Ana Pérez' },
  { id: 2, username: 'juan.lopez', password: 'password456', role: 'alumno', nombre: 'Juan López' },
  { id: 3, username: 'carlos.gomez', password: 'passdocente', role: 'docente', nombre: 'Carlos Gómez' },
  { id: 4, username: 'laura.diaz', password: 'passadmin', role: 'admin', nombre: 'Laura Díaz' }
];

// Log de usuarios (solo para desarrollo)
console.log("Usuarios de prueba:");
users.forEach(u => console.log(`- ${u.username} / ${u.password} (Rol: ${u.role})`));
console.log("-------------------------------------------");

// --- ENDPOINTS ---

// 1. Login
app.post('/auth/login', (req, res) => {
  const { username, password } = req.body;

  if (!username || !password) {
    return res.status(400).json({ 
      success: false,
      message: 'Usuario y contraseña son requeridos.' 
    });
  }

  const user = users.find(u => u.username === username && u.password === password);

  if (!user) {
    return res.status(401).json({ 
      success: false,
      message: 'Credenciales incorrectas.' 
    });
  }

  const token = jwt.sign(
    { 
      userId: user.id, 
      username: user.username, 
      role: user.role, 
      nombre: user.nombre 
    },
    JWT_SECRET,
    { expiresIn: '1h' }
  );

  res.json({
    success: true,
    message: `¡Bienvenido ${user.nombre}!`,
    token,
    user: {
      id: user.id,
      username: user.username,
      role: user.role,
      nombre: user.nombre
    }
  });
});

// --- MIDDLEWARES ---

// 2. Autenticación JWT
function authenticateToken(req, res, next) {
  const authHeader = req.headers['authorization'];
  const token = authHeader && authHeader.split(' ')[1];

  if (!token) {
    return res.status(401).json({ 
      success: false,
      message: 'Token no proporcionado. Acceso denegado.' 
    });
  }

  jwt.verify(token, JWT_SECRET, (err, user) => {
    if (err) {
      return res.status(403).json({ 
        success: false,
        message: err.name === 'TokenExpiredError' ? 'Token expirado.' : 'Token inválido.' 
      });
    }
    req.user = user;
    next();
  });
}

// 3. Autorización por roles
function authorizeRoles(rolesPermitidos) {
  return (req, res, next) => {
    if (!rolesPermitidos.includes(req.user.role)) {
      return res.status(403).json({
        success: false,
        message: `Acceso denegado. Rol requerido: ${rolesPermitidos.join(', ')}` 
      });
    }
    next();
  };
}

// --- RUTAS PROTEGIDAS ---

// Pública
app.get('/api/public/noticias', (req, res) => {
  res.json({
    success: true,
    noticias: [
      { id: 1, titulo: 'Bienvenidos al ciclo lectivo 2024' },
      { id: 2, titulo: 'Nuevo laboratorio de informática disponible' }
    ]
  });
});

// Perfil del usuario logueado (cualquier rol)
app.get('/api/me/perfil', authenticateToken, (req, res) => {
  res.json({ 
    success: true,
    perfil: req.user 
  });
});

// Solo alumnos
app.get('/api/alumnos/mis-notas', authenticateToken, authorizeRoles(['alumno']), (req, res) => {
  res.json({
    success: true,
    alumno: req.user.nombre,
    notas: [
      { materia: 'Matemática', nota: 9 },
      { materia: 'Programación', nota: 8 }
    ]
  });
});

// Solo docentes (con validación de nota)
app.post('/api/docentes/cargar-nota', authenticateToken, authorizeRoles(['docente']), (req, res) => {
  const { alumnoId, materia, nota } = req.body;

  if (!alumnoId || !materia || nota === undefined || nota < 0 || nota > 10) {
    return res.status(400).json({
      success: false,
      message: 'Datos inválidos. La nota debe ser entre 0 y 10.'
    });
  }

  res.json({
    success: true,
    message: 'Nota registrada exitosamente',
    data: { alumnoId, materia, nota }
  });
});

// Solo administradores
app.get('/api/admin/usuarios', authenticateToken, authorizeRoles(['admin']), (req, res) => {
  const usuarios = users.map(u => ({ 
    id: u.id, 
    username: u.username, 
    role: u.role 
  }));

  res.json({ 
    success: true,
    usuarios 
  });
});

// Docentes y administradores
app.get('/api/cursos/gestion', authenticateToken, authorizeRoles(['docente', 'admin']), (req, res) => {
  res.json({
    success: true,
    message: `Acceso concedido a ${req.user.nombre} (${req.user.role})`,
    cursos: ['Diseño de Software', 'Base de Datos']
  });
});

// --- MANEJO DE ERRORES ---

// Ruta no encontrada (404)
app.use((req, res) => {
  res.status(404).json({ 
    success: false,
    message: `Ruta no encontrada: ${req.method} ${req.path}` 
  });
});

// Error general (500)
app.use((err, req, res, next) => {
  console.error('Error:', err.stack);
  res.status(500).json({ 
    success: false,
    message: 'Error interno del servidor.' 
  });
});

// Iniciar servidor
app.listen(PORT, () => {
  console.log(`Servidor escuchando en http://localhost:${PORT}`);
  console.log('Modo:', process.env.NODE_ENV || 'desarrollo');
});