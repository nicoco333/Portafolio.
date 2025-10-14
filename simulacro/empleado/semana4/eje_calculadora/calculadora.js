function sumar() {
    const num1 = parseFloat(document.getElementById("num1").value);
    const num2 = parseFloat(document.getElementById("num2").value);
    const resultado = num1 + num2;
    document.getElementById("resultado").textContent = "Resultado: " + resultado;
   }
   function restar() {
    const num1 = parseFloat(document.getElementById("num1").value);
    const num2 = parseFloat(document.getElementById("num2").value);
    const resultado = num1 - num2;
    document.getElementById("resultado").textContent = "Resultado: " + resultado;
   }
   function multiplicar() {
    const num1 = parseFloat(document.getElementById("num1").value);
    const num2 = parseFloat(document.getElementById("num2").value);
    const resultado = num1 * num2;
    document.getElementById("resultado").textContent = "Resultado: " + resultado;
   }
   function dividir() {
    const num1 = parseFloat(document.getElementById("num1").value);
    const num2 = parseFloat(document.getElementById("num2").value);
    if (num2 !== 0) {
    const resultado = num1 / num2;
    document.getElementById("resultado").textContent = "Resultado: " + resultado;
    } else {
    document.getElementById("resultado").textContent = "Error: División por cero";
    }
   }