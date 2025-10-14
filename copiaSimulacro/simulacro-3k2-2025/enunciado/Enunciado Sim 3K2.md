# Backend de Aplicaciones 2025

## Enunciado Simulacro 3K2

Lea atentamente hasta el final del enunciado, a no ser que no le preocupe programar demás, cosas que no se solicitan.

### Datos

Usted dispone del archivo games_data.csv, (el archivo tiene encoding UTF-8), con valores separados por `;`. El archivo tiene una fila de cabecera con los nombres de cada una de las columnas.

El archivo contiene los datos de 16473 juegos que pueden o no estar repetidos y eso no será tenido en cuenta para la resolución.

La estructura del archivo se describe a continuación, **preste atención a las particularidades de cada columna puesto que esto le servirá para evitar idas y vueltas al procesar**:

- **`Title`**: El nombre del videojuego.
- **`Release_Date`**: La fecha de lanzamiento del videojuego, especificada en formato de año-mes-día.
  - Está expresada como una cadena de caracteres con el formato `MMM dd, yyyy`.
  
    Se espera  que usted almacene en la columna de la base de datos el número que se obtiene al invocar el método `getTime()` de las instancias de `java.util.Date`

    ```java
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);

        try {
            // Convertir la cadena a un objeto Date
            Date date = formatter.parse(dateString);

            // Obtener el valor en milisegundos usando getTime()
            long timeInMillis = date.getTime();

            // Imprimir el resultado
            System.out.println("Fecha original: " + dateString);
            System.out.println("Fecha en milisegundos: " + timeInMillis);
        } catch (ParseException e) {
            System.out.println("Error al parsear la fecha: " + e.getMessage());
        }

    ```

  - También puede contener la cadena vacía o el valor "TBD", en estos casos simplemente no se cargará este dato.
- **`Developers`**: El nombre del desarrollador del videojuego.
  - La cadena con el nombre estará contenida entre `[]` y `''`, se debe extraer el valor puro para la carga.
  - La lista puede estar vacía y en tal caso contendrá la cadena vacía o la cadena "[]". En el caso de vacío se inserta null en la base de datos.
- **`Summary`**: Una descripción general o resumen del videojuego, que puede incluir detalles sobre la trama, características clave y otros aspectos importantes.
  - Es un texto más largo de que una cadena normal.
- **`Platforms`**: Las plataformas en las que está disponible el videojuego, como PC, PlayStation, Xbox, etc.
  - La cadena con el nombre estará contenida entre `[]` y `''`, se debe extraer el valor puro para la carga.
  - La lista puede estar vacía y en tal caso contendrá la cadena vacía o la cadena "[]". En el caso de vacío se inserta null en la base de datos.
- **`Genres`**: Los géneros del videojuego, como aventura, acción, rol, entre otros.
  - La cadena con el nombre estará contenida entre `[]` y `''`, se debe extraer el valor puro para la carga.
  - La lista puede estar vacía y en tal caso contendrá la cadena vacía o la cadena "[]". En el caso de vacío se inserta null en la base de datos.
- **`Rating`**: La calificación del videojuego, basada en la opinión de los usuarios o críticos.
  - Es un número decimal que está entre 0 y 5 y que expresa el nivel de aceptación del juego.
  - También puede contener la cadena "N/A" y en tal caso simplemente lo vamos a manejar como un valor nulo.
- **`Plays`**: El número de veces que el videojuego ha sido jugado.
  - Es un número entero pero cargado como cadena de caracteres con la letra K indicando miles.
  - Deberá ser procesado inicialmente como un número decimal para luego en caso de existir la K multiplicarlo por 1000 y volverlo un número entero
- **`esrb_rating`**: La clasificación ESRB, que indica la edad recomendada para jugar el videojuego, según el sistema de clasificación de la ESRB.
  - La Clasificación que es una cadena de caracteres, requerirá un tratamiento especial de acuerdo con lo que sigue:
  
#### Clasificación ESRB

La clasificación ESRB de los juegos es una clasificación internacional que indica la edad esperada de los jugadores o la edad para la que el juego fue pensado sin tener en cuenta los menores de dicha edad.

En la siguiente tabla se muestran todos los valores posibles con sus códigos:

| Código | Clasificación ESRB | Descripción                                              |
|--------|--------------------|----------------------------------------------------------|
| E      | Everyone            | Apto para todas las edades. Sin contenido objetable.     |
| E10+   | Everyone 10+        | Apto para mayores de 10 años. Contiene algo más de fantasía o violencia leve. |
| T      | Teen                | Apto para mayores de 13 años. Puede incluir violencia moderada, lenguaje fuerte o temas sugerentes. |
| M      | Mature              | Apto para mayores de 17 años. Contiene violencia intensa, sangre, temas sexuales o lenguaje fuerte. |
| AO     | Adults Only         | Apto solo para adultos mayores de 18 años. Contiene temas sexuales explícitos o violencia extrema. |
| RP     | Rating Pending      | Clasificación aún pendiente de revisión.                 |
| UR     | Unrated      | Clasificación Indeterminada.                 |

Se solicita que esta información se maneje como un enum en la aplicación asociada a la cadena del código que se almacena en la base de datos.

### Base de Datos

La base de datos es la que se suministró previamente con el pre-enunciado, no se valora la forma en la que la base de datos en memoria creada a nivel de estructura, sin embargo se debe respectar específicamente el ddl que se suministra en el pre-enunciado.

Algunas consideraciones al respecto de la tabla `Juegos` puesto que las acciones a tomar con las demás tablas se definen en el punto 5 de carga de la base de datos:

1. La `fecha_lanzamiento` está identificada como un entero y es porque se espera que se almacene el valor que se obtiene del método getTime de la clase Date en ese atributo de forma que si hiciera falta luego se puede volver a fecha de una manera simple.
2. Tanto `rating` como `plays` se definen como números, ya se mencionó la transformación que se espera para ellos.
3. En el caso de las tablas relacionadas note que siempre vamos a trabajar la relación a una fila de la tabla relacionada, y en el punto 2 de carga de base de datos se explicará como actuar en cada caso.
4. Finalmente, solo para notar la particularidad, las tablas Desarrolladores y Generos son similares por lo que aproveche esta particularidad para ganar tiempo.

### Requerimientos

1. **Cargar el archivo CSV a una estructura de datos en memoria**  
   A partir del modelo de clases con el mapeo a la base de datos que genero con el pre-enunciado, se espera que usted cree instancias de esas clases a partir de los datos del archivo CSV.

   Para esto dejamos algunas consideraciones a tener en cuenta:
   - Cargar la línea del archivo CSV si y solo sí tiene un valor para Desarrollador, Plataforma  y Género.
   - Cada linea del archivo CSV se va a transformar en una instancia de la clase `Juego` en memoria que deberá ser almacenada en alguna estructura de datos y en cada instancia de la clase Juego habrá que componerla con las instancias de las clases Desarrollador, Plataforma y Género.
   - Además también deberá relacionar el juego con la enumeración que corresponda de acuerdo con su clasificación esrb.
   - En el caso en que el juego tenga la clasificación vacía se asociará con el valor Unrated que corresponde con Clasificación Indeterminada o No definida.  
  
   A modo de control muestre al final la cantidad total de juegos cargados.
2. **Popular la base de datos**  
   con todos los objetos cargados en  memoria teniendo en cuenta las siguiente consideraciones:
   - Cada juego en memoria corresponderá con una fila de la tabla `Juegos`
   - Cada desarrollador corresponderá con una fila de la tabla `Desarrolladores` y deberán quedar relacionados mediante el id.
   - Cada género corresponderá con una fila de la tabla `Generos` y deberán quedar relacionadas mediante el id.
   - Cada plataforma corresponderá con una fila de la tabla `Plataformas` y deberán quedar relacionadas mediante el id.
   - Finalmente la cadena `Clasificaciones_ESRB` deberá ser administrada a traves de una enumeración en el código Java.
3. **Determinar el ranking de Géneros más jugados**  
   Determinar los 5 Géneros más jugados teniendo en cuenta la suma de juegos en curso `jugando` de todos los juegos del genero.  
   Deberá realizar, para cada género, la suma de de la cantidad de juegos en curso `jugando` de todos los juegos del género y en base a estas sumas mostrar los 5 mayores ordenados de mayor a menor.
   A modo de control muestre al final la cantidad total de Géneros importados.
4. **Determinar la Cantidad de Juegos cargados por cada Desarrollador**  
   Una vez que logró cargar todos los juegos en memoria se requiere que usted muestre un listado de todos los Desarrolladores y para cada una de ellos la cantidad de juegos asociados pero solo para los desarrolladores con más de 30 juegos asociados.  
   A modo de control muestre al final la cantidad total de Desarrolladores importados.
5. **Determinar cuál es la Plataforma mejor Rankeada**  
   Indique cuál es la plataforma con mayor rating promedio entre todos sus juegos.  
   Para determinar el rating promedio, calcular la suma de todos los `ratings` de cada uno de los juegos de cada plataforma dividido por la cantidad de juegos de la plataforma. Realizar este cálculo solo para los juegos que tengan más de 999 juegos finalizados.

### Consignas generales

- La aplicación debe ejecutar a partir de `mvn exec:java` como lo venimos trabajando en clase, importar el archivo CSV, y mostrar los resultados solicitados de forma clara por pantalla.
- El estudiante puede decidir cómo dividir las responsabilidades de lo solicitado en clases pero es importante que el método main cumpla los requisitos de los puntos del 1 al 5 mostrando en cada paso lo solicitado.

### Recomendaciones y aclaraciones

- El archivo CSV tiene un numero importante de filas, puede optar por generar un recorte del mismo para llevar a cabo las pruebas pero la ejecución final debe llevarse a cabo con el archivo original
- Se espera un trabajo individual y de producción propia, apoyado en el trabajo previo a partir del pre-enunciado y a su leal saber y entender
- Si bien la estructura de capas de la aplicación no deben seguir una estructura particular, las preguntas del cuestionario asumen que se implementó la solución de acuerdo con lo que se documentó en los materiales de la cátedra.

### La cátedra de Backend les desea todo el Éxito en esta semana de Parcial
