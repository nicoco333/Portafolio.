// Importaciones
const sequelize = require('./sequelize');
const Producto = require('./models/producto');
const prompt = require('prompt-sync')();
const { Op } = require('sequelize');

// Función principal
async function main() {
    try {
        await sequelize.authenticate();
        console.log('Conexión a la base de datos establecida.\n');

        // Opcional: Sincronizar tabla y cargar datos de ejemplo
        // await Producto.sync({ force: true }); // Descomentar para recrear la tabla en cada ejecución (solo desarrollo)
        // console.log('Tabla "Productos" creada o sincronizada.\n');
        // await inicializarProductos(); // Función para insertar productos de ejemplo

        let continuar = true;
        while (continuar) {
            mostrarMenu();
            const opcion = prompt('Seleccione una opción: ');

            switch (opcion) {
                case '1':
                    await listarProductos();
                    break;
                case '2':
                    await buscarProducto();
                    break;
                case '3':
                    await actualizarPrecios();
                    break;
                case '4':
                    continuar = false;
                    console.log('¡Hasta luego!');
                    break;
                default:
                    console.log('Opción no válida. Por favor, intente de nuevo.\n');
            }
        }
    } catch (error) {
        console.error('Ocurrió un error:', error);
    } finally {
        await sequelize.close();
    }
}

// Funciones auxiliares
function mostrarMenu() {
    console.log('\n--- Menú de Gestión de Productos ---');
    console.log('1. Listar todos los productos');
    console.log('2. Buscar producto');
    console.log('3. Actualizar precios (10%)');
    console.log('4. Salir');
    console.log('-----------------------------------\n');
}

function mostrarDetallesProducto(producto) {
    console.log(`ID: ${producto.id}`);
    console.log(`Nombre: ${producto.nombre}`);
    console.log(`Descripción: ${producto.descripcion || 'No disponible'}`);
    console.log(`Categoría: ${producto.categoria || 'No especificada'}`);
    console.log(`Precio: $${producto.precio}`);
    console.log('---');
}

// Funciones principales
async function listarProductos() {
    try {
        const productos = await Producto.findAll();
        if (productos.length > 0) {
            console.log('\n--- Lista de Productos ---');
            productos.forEach(producto => {
                mostrarDetallesProducto(producto);
            });
        } else {
            console.log('No hay productos registrados.\n');
        }
    } catch (error) {
        console.error('Error al listar productos:', error);
    }
}

async function buscarProducto() {
    const searchTerm = prompt('Ingrese término de búsqueda: ');
    if (!searchTerm.trim()) {
        console.log('Por favor, ingrese un término de búsqueda válido.\n');
        return;
    }

    try {
        const productosEncontrados = await Producto.findAll({
            where: {
                [Op.or]: [
                    { nombre: { [Op.like]: `%${searchTerm}%` } },
                    { descripcion: { [Op.like]: `%${searchTerm}%` } }
                ]
            }
        });

        if (productosEncontrados.length > 0) {
            console.log('\n--- Productos Encontrados ---');
            productosEncontrados.forEach(producto => {
                mostrarDetallesProducto(producto);
            });
        } else {
            console.log('No se encontraron productos que coincidan con su búsqueda.\n');
        }
    } catch (error) {
        console.error('Error al buscar productos:', error);
    }
}

async function actualizarPrecios() {
    try {
        const productos = await Producto.findAll();
        if (productos.length > 0) {
            console.log('\nActualizando precios de todos los productos en un 10%...\n');
            for (const producto of productos) {
                producto.precio *= 1.10; // Incrementa el precio en un 10%
                await producto.save(); // Guarda el producto actualizado en la base de datos
            }
            console.log('¡Precios actualizados exitosamente!\n');
        } else {
            console.log('No hay productos para actualizar.\n');
        }
    } catch (error) {
        console.error('Error al actualizar precios:', error);
    }
}

// Función para inicializar productos de ejemplo
async function inicializarProductos() {
    await Producto.bulkCreate([
        { nombre: 'Laptop Ultrabook', descripcion: 'Laptop delgada y ligera.', categoria: 'Electrónicos', precio: 1200.00 },
        { nombre: 'Monitor Curvo 32"', descripcion: 'Monitor de alta resolución.', categoria: 'Electrónicos', precio: 450.00 },
        { nombre: 'Teclado Mecánico RGB', descripcion: 'Teclado mecánico retroiluminado.', categoria: 'Electrónicos', precio: 100.00 },
        { nombre: 'Silla Ergonómica', descripcion: 'Silla de oficina para largas jornadas.', categoria: 'Mobiliario', precio: 250.00 },
        { nombre: 'Escritorio de Madera', descripcion: 'Escritorio de madera maciza.', categoria: 'Mobiliario', precio: 300.00 },
    ]);
    console.log('Productos de ejemplo inicializados.\n');
}

// Ejecutar la aplicación
main();