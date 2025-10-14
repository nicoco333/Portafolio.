const seedrandom = require('seedrandom');
const seed = 1763519;

// Generamos 1.000.000 de números enteros usando int32
const cantidad = 1000000;
let numeros = [];
for (let i = 0; i < cantidad; i++) {
    numeros.push(rng.int32());
}

// 1) Cantidad de números positivos y negativos
let positivos = 0, negativos = 0;
for (let num of numeros) {
    if (num > 0) positivos++;
    else if (num < 0) negativos++;
}

// 2) Resto al dividir por 7 igual a 0, 3, 5 o 6
let countResto = 0;
for (let num of numeros) {
    let resto = Math.abs(num % 7);
    if ([0, 3, 5, 6].includes(resto)) {
        countResto++;
    }
}

// 3) Arreglo de contadores por anteúltimo dígito (decenas)
let decenas = Array(10).fill(0);
for (let num of numeros) {
    let decena = Math.floor(Math.abs(num) / 10) % 10;
    decenas[decena]++;
}

// 4) Valor y posición del menor
let menor = numeros[0];
let posicion = 1;
for (let i = 1; i < numeros.length; i++) {
    if (numeros[i] < menor) {
        menor = numeros[i];
        posicion = i + 1; // posición comienza en 1
    }
}

// 5) Números con mismo signo que el anterior
let mismoSigno = 0;
for (let i = 1; i < numeros.length; i++) {
    if ((numeros[i] >= 0 && numeros[i - 1] >= 0) || (numeros[i] < 0 && numeros[i - 1] < 0)) {
        mismoSigno++;
    }
}

// 6) Promedio de números con exactamente 6 dígitos
let suma6digitos = 0;
let cuenta6digitos = 0;
for (let num of numeros) {
    let absNum = Math.abs(num);
    if (absNum >= 100000 && absNum <= 999999) {
        suma6digitos += num;
        cuenta6digitos++;
    }
}
let promedio6digitos = cuenta6digitos > 0 ? Math.round(suma6digitos / cuenta6digitos) : 0;

// Mostrar resultados
console.log("1) Positivos:", positivos, "Negativos:", negativos);
console.log("2) Múltiplos con resto 0,3,5,6:", countResto);
console.log("3) Decenas:", decenas);
console.log("4) Menor valor:", menor, "en posición:", posicion);
console.log("5) Mismo signo que anterior:", mismoSigno);
console.log("6) Promedio de 6 dígitos:", promedio6digitos);

