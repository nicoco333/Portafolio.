// Variable global (¡Ojo con esto!)
mensajeGlobal = "¡Bienvenido a tu lista de tareas!";
function inicializarLista() {
    contadorTareasAgregadas = 0
    let nombreLista = "Tareas Pendientes";
    const mensajeInicial = mensajeGlobal;
    mostrarMensaje(mensajeInicial);
    mostrarTareasIniciales();
    configurarBotonAgregarTarea(); // Nueva función para el botón
}
function mostrarMensaje(texto) {
    const elementoMensaje = document.getElementById('mensaje');
    elementoMensaje.textContent = texto;
}
function mostrarTareasIniciales() {
    const tareas = ["Comprar víveres", "Estudiar JavaScript",
        "Hacer ejercicio", "Llamar a un amigo"];
    const listaTareasElemento = document.getElementById('lista-tareas');
    for (let i = 0; i < tareas.length; i++) {
        const tareaTexto = tareas[i];
        const elementoLista = document.createElement('li');
        elementoLista.textContent = tareaTexto;
        listaTareasElemento.appendChild(elementoLista);
        }
}

function agregarTareaALista(tarea){
    const listaTareasElemento = document.getElementById('lista-tareas');
    const elementoLista = document.createElement('li');
    elementoLista.textContent = tarea;
    listaTareasElemento.appendChild(elementoLista);
}
function configurarBotonAgregarTarea() {
    const botonAgregar = document.getElementById('boton-agregar-tarea');
    botonAgregar.addEventListener('click', function() {
    // Vamos a usar un switch para mostrar diferentes mensajes (ejemplo simplificado)
    contadorTareasAgregadas++; // Valor predefinido para el ejemplo del switch
    let opcion = contadorTareasAgregadas % 3
    switch (opcion) {
        case 0:
            mostrarMensaje("¡Excelente! Vamos a agregar una nueva tarea.");
            break;
        case 1:
            mostrarMensaje("¿Listo para ser productivo?");
            break;
        case 2: 
            mostrarMensaje("Tu lista de tareas sigue creciendo, sigue asi!");
            break;
        default:
            mostrarMensaje("Haz clic en 'Agregar Tarea' paraempezar.");
    }
    let nuevaTarea = prompt("¿Qué tarea deseas agregar?");
        if (nuevaTarea) {
            agregarTareaALista(nuevaTarea);
        }
    });
}
inicializarLista();
console.log("Variable global mensajeGlobal:", mensajeGlobal);