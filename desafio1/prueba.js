const seedrandom = require('seedrandom');
const seed = 1763519;

// funcion para generar numeros aleatorios enteros 
function generarEnterosAleatorios(cantidad){
    const num = seedrandom(seed);
    const numeros = [];
    for (let i = 0; i < cantidad; i++){
        numeros.push(num.int32())
    }
    return numeros;   
}

const numeros = generarEnterosAleatorios(1000000);

// 1) cantidad de numeros positivos y negativos 
const positivos = numeros.filter(num => num > 0).length;
const negativos = numeros.filter(num  => num < 0).length;
console.log('Cantidad de numeros positivos:', positivos);
console.log('Cantidad de numeros negativos:', negativos);

// 2) cantidad de numeros cuyo resto al dividirlos en 7 sea 0,3,5 o 6

const resto = numeros.filter(num => num % 7 === 0 || num % 7 === 3 || num % 7 === 5 || num % 7 === 6).length;
console.log('Cantidad de numeros cuyo resto al dividirlos en 7 sea 0,3,5 o 6:', resto);

// 3) Un arreglo de contadores que indique la cantidad de números según su anteúltimo dígito (el de las decenas) coincida con el índice.
const contadores = new Array(10).fill(0);
numeros.forEach(num => {
    const anteUltimoDigito = Math.floor(Math.abs(num) / 10) % 10;
    contadores[anteUltimoDigito]++;
    }
)
console.log('Contadores:', contadores);

// 4) Valor y posición del menor de todos.

let menor = numeros[0];
let posicionMenor = 1;
for (let i = 1; i < numeros.length; i++) {
    if (numeros[i] < menor) {
        menor = numeros[i];
        posicionMenor = i + 1; 
    }
}
console.log('Menor:', menor);
console.log('Posicion del menor:', posicionMenor);


// 5) Cantidad de números cuyo signo sea igual al del anterior


let cantidadIguales = 0;
for (let i = 1; i < numeros.length; i++) {
    if ((numeros[i] > 0 && numeros[i - 1] > 0) || (numeros[i] < 0 && numeros[i - 1] < 0)) {
        cantidadIguales++;
    }
}

console.log('Cantidad de numeros cuyo signo sea igual al del anterior:', cantidadIguales);



// 6) Promedio entero (redondeado con Math.round) de todos los números que contengan exactamente 6 dígitos.

const numeros6Digitos = numeros.filter(num => Math.abs(num) >= 100000 && Math.abs(num) < 1000000);
const suma6Digitos = numeros6Digitos.reduce((acc, num) => acc + num, 0);
const promedio6Digitos = Math.round(suma6Digitos / numeros6Digitos.length);
console.log('Promedio de numeros con 6 digitos:', promedio6Digitos);

