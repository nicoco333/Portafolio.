// Base de datos inicial de autos (Array de Objetos)
let autosAgencia = [
    { marca: 'Renault', modelo: 'Sandero', anio: 2022, precio: 19000000 },
    { marca: 'Chevrolet', modelo: 'Cruze', anio: 2019, precio: 23000000 },
    { marca: 'Citroen', modelo: 'C3', anio: 2021, precio: 17000000 },
    { marca: 'Fiat', modelo: 'Cronos', anio: 2023, precio: 21500000 } //Agregamos uno más para variedad
];
// Función para mostrar los autos en la tabla HTML
function mostrarAutos() {
    const tablaBody = document.getElementById('tablaAutosBody');
    // Limpiar tabla anterior (si existe)
    tablaBody.innerHTML = '';
    // Recorrer el array y generar las filas de la tabla
    autosAgencia.forEach(auto => {
    const fila = `
        <tr>
            <td>${auto.marca}</td>
            <td>${auto.modelo}</td>
            <td>${auto.anio}</td>
            <td>$ ${auto.precio.toLocaleString('es-AR')}</td>
        </tr>
    `;
    // Agregar la fila al cuerpo de la tabla
    tablaBody.innerHTML += fila; // Usamos += para añadir, no reemplazar
    });
}
// Función para agregar un auto desde el formulario
function agregarAuto() {
    // 1. Obtener referencias a los inputs
    const marcaInput = document.getElementById('inputMarca');
    const modeloInput = document.getElementById('inputModelo');
    const anioInput = document.getElementById('inputAnio');
    const precioInput = document.getElementById('inputPrecio');
    // 2. Obtener los valores de los inputs
    const marca = marcaInput.value.trim(); // trim() quita espacios al inicio/final
    const modelo = modeloInput.value.trim();
    const anioStr = anioInput.value;
    const precioStr = precioInput.value;
    // 3. Validación básica (campos no vacíos)
    if (!marca || !modelo || !anioStr || !precioStr) {
        alert('Por favor, complete todos los campos.');
        return; // Detiene la ejecución si falta algún dato
    }
    // 4. Convertir año y precio a números
    const anio = parseInt(anioStr);
    const precio = parseFloat(precioStr);
    // 5. Validación numérica
    if (isNaN(anio) || isNaN(precio) || anio <= 1900 || precio < 0) {
        alert('Por favor, ingrese un año y precio válidos.');
        return; // Detiene si la conversión falló o valores no razonables
    }
    // 6. Crear el nuevo objeto auto
    const nuevoAuto = {
        marca: marca,modelo: modelo,
        anio: anio,
        precio: precio
    };
    // 7. Agregar el auto al array
    autosAgencia.push(nuevoAuto);
    // 8. Actualizar la tabla en el HTML
    mostrarAutos();
    // 9. Limpiar el formulario
    marcaInput.value = '';
    modeloInput.value = '';
    anioInput.value = '';
    precioInput.value = '';
    // Opcional: Mover el foco al primer campo para el siguiente ingreso
    marcaInput.focus();
    }
// Función para eliminar el último auto del array
function eliminarUltimoAuto() {
    if (autosAgencia.length > 0) {
        autosAgencia.pop(); // Elimina el último elemento
        mostrarAutos(); // Actualiza la tabla
        console.log('Se eliminó el último auto. Array actual:', autosAgencia); //Log para verificar
    } else {
            alert('No hay autos para eliminar.');
        }
    }
// Event Listener para que se ejecute mostrarAutos cuando el DOM esté cargado
document.addEventListener('DOMContentLoaded', mostrarAutos);
// Event Listener para el botón Agregar
const btnAgregar = document.getElementById('btnAgregar');
btnAgregar.addEventListener('click', agregarAuto);
// Event Listener para el botón Eliminar Último
const btnEliminarUltimo = document.getElementById('btnEliminarUltimo');
btnEliminarUltimo.addEventListener('click', eliminarUltimoAuto);