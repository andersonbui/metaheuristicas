
Si se desea utilizarla herramienta con graficacion 3D(recorrido del algoritmo) es necesario que los puntos tengan dimension 2 (punto = 2 dimensiones + calidad, para un total de 3 dimensiones), ademas,se necesita la aplicacion gnuplot instalada y accesible desde consola(linea de comandos).

Si se desea utilizarla herramienta con graficacion 2D(grafica de dispersion) sirve para cualquier dimension de los puntos(ya que se utiliza tiempo vs calidad, para un total de 2 dimensiones), ademas, se necesita la aplicacion gnuplot instalada y accesible desde consola(linea de comandos).

Para modificar el numero de desimales mostrados en los resultados(por defecto 1), modificar el valor del atributo metaheuristicas.General.NUM_DECIMALES

Si se desea utilizar para minimizacion se necesita metaheusristicas.Punto.ORDEN = -1
Si se desea utilizar para maximizacion se necesita metaheusristicas.Punto.ORDEN = 1