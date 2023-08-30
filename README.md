# Web Server

Este proyecto busca solucionar el ejercicio dado de escribir un servidor web concurrente que soporte múltiples solicitudes. El servidor web deberá retornar los archivos solicitados, incluyendo páginas HTML, imágenes y archivos .js.

## Diseño

### Diagrama de clases 

![class diagram](https://github.com/sebasporras14/AREP-lab02/blob/master/imagenes/clases.png)


## Corriendo el proyecto

Una vez clonado el proyecto y generado el jar con los siguientes comandos:

~~~
git clone https://github.com/sebasporras14/AREP-lab02.git
~~~
~~~
mvn package
~~~

se puede ejecutar con:

~~~
java -cp "./target/classes" edu.escuelaing.arep.app.HttpServer
~~~
una vez aparezca "Listo para recibir", en su navegador ingrese:

~~~
Http://LocalHost:35000/"el archivo html, imagen o js"
~~~

### prueba HTML,JPG o PNG, .js

Al colocar los siguientes comandos se obtiene:
~~~
Http://LocalHost:35000/index.html"
~~~
~~~
Http://LocalHost:35000/img.jpg
~~~
~~~
Http://LocalHost:35000/img2.png
~~~
~~~
Http://LocalHost:35000/prueba.js"
~~~
![test](https://github.com/sebasporras14/AREP-lab02/blob/master/imagenes/pruebas.png)

### prueba con APACHE jmeter

Se realizó la prueba donde se configuraron 200 solicitudes

![configuración](https://github.com/sebasporras14/AREP-lab02/blob/master/imagenesconfiguracion.png)

donde se observa que las solicitudes fueron exitosas:

![test](https://github.com/sebasporras14/AREP-lab02/blob/master/imagenes/prueba1.png)

----

![test](https://github.com/sebasporras14/AREP-lab02/blob/master/imagenes/prueba2.png)

## Construido con 
* [Maven](https://maven.apache.org/) - Dependency Management
* [java](https://rometools.github.io/rome/) - Used to generate RSS Feeds


## Autor

* **Sebastian Porras**

### Fecha

08/30/2023 

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
