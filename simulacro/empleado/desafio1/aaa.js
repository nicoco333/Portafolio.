const seedrandom = require('seedrandom');
const seed = 1763519;

//funcion para gnerar numero aleatorios enteros usando seedrandom
function generarEnterosAleatorios(cantidad){
    const num = seedrandom(seed);
    const numeros = [];
    for (let i = 0; i < cantidad; i++){
        numeros.push(num.int32())
    }
    return numeros;   
}   

// generar los numeros aleatorios
const numeros = generarEnterosAleatorios(1000000);

//punto 1 
const positivos = numeros.filter(num => num > 0).length;
const negativos = numeros.filter(num  => num < 0).length;
console.log('Cantidad de numeros positivos:', positivos);
console.log('Cantidad de numeros negativos:', negativos);

//punto 2
const restos = numeros.filter(numero => [0, 3 , 5, 6].includes(Math.abs(numero % 7))).length;
console.log('Cantidad de numeros cuyo resto al dividirlos en 7 sea 0,3,5 o 6:', restos);


//punto 3
const contadores = new Array(10).fill(0);
for (const numero of numeros ) {
    const decena = Math.floor(Math.abs(numero) / 10) % 10;
    contadores[decena]++;
}
console.log('Contadores por anteultimo digito:', contadores);


//punto 4
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


//punto 5
let signoAnterior = null;
let cantidadSignosIguales = 0;
for (const numero of numeros) {
    const signo = numero >= 0 ? 1 : -1;
    if (signo === signoAnterior) {
        cantidadSignosIguales++;
    }
    signoAnterior = signo;

}
console.log('Cantidad de numeros cuyo signo sea igual al del anterior:', cantidadSignosIguales);


//punto 6
const numerosSeisDigitos = numeros.filter(numero => Math.abs(numero).toString().length === 6);
const sumaSeisDigitos = numerosSeisDigitos.reduce((acumulador, numero) => acumulador + numero, 0) / numerosSeisDigitos.length;
console.log('Promedio de numeros con exactamente 6 digitos:', Math.round(sumaSeisDigitos));


