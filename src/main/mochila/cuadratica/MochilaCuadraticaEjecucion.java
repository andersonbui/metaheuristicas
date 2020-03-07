/*
 * Copyright (C) 2017 debian
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package main.mochila.cuadratica;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import main.EjecutarGrupo;
import main.ResultadoAlgoritmo;
import main.ResultadoGrupo;
import main.mochila.cuadratica.ConjuntoInstancias.*;
import main.mochila.cuadratica.utilidades.ImprimirResultados;
import main.mochila.cuadratica.ConjuntoInstancias.Instancia;
import main.mochila.cuadratica.utilidades.LecturaParametrosCuadratica;
import main.mochila.cuadratica.utilidades.InstanciaAlgoritmo;
import main.mochila.cuadratica.utilidades.UtilCuadratica;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.funcion.FuncionGen;

/**
 *
 * @author debian
 */

public class MochilaCuadraticaEjecucion {

    public String tsalida = "v";
    public String algoritmo = null;
    public boolean depuracion = false;
    public boolean evresumen = false;
    String parametrosAlgoritmo;
    public String nombreExperimento = "";
   
    public ConjuntoInstancias escogerConjunto(String opcion){
        ConjuntoInstancias datos = null;
        switch(opcion) {
            case "1000":
                datos = new ConjuntoInstancias1000();
                return datos;
                
            case "100":
                datos = new ConjuntoInstancias100();
                return datos;
                
            case "200":
                datos = new ConjuntoInstancias200();
                return datos;
                
            case "300":
                datos = new ConjuntoInstancias300();
                return datos;
                
            case "resumen":
                datos = new ConjuntoInstanciasResumenes();
                return datos;
                
//              ConjuntoInstancias datos = new ConjuntoInstanciasPruebas();

        }
        return datos;
    }

    public void ejecucion(String[] args) throws FileNotFoundException, Exception {
        int numIntentos = 10;

        int indice = 0;
        nombreExperimento = "";
        boolean ayuda = true;
        ConjuntoInstancias datos = null;
        List<Instancia> listaInstanc = null;
        String nombreArchivoResultado = "";
        while (args.length > indice) {
            String opcion = args[indice++];
            switch (opcion) {
                case "--intentos":
                    numIntentos = Integer.parseInt(args[indice++]);
                    break;
                case "-e":
                case "--examples": 
                    datos = escogerConjunto(args[indice++]);
                    listaInstanc = datos.getConjuntoInstancias();
                    ayuda = false;
                    break;
                case "-a":
                case "--archivo":
                    listaInstanc = new ArrayList();
                    if (args.length > indice) {
                        String nombreInstancia = args[indice++];
                        String[] cad = nombreInstancia.split("/");
                        String nom = cad[cad.length - 1];
                        listaInstanc.add(new Instancia(nom, nombreInstancia, ""));
                        if (args.length > indice) {
                            nombreArchivoResultado = args[indice++];
                        }
                        ayuda = false;
                    }
                    break;
                case "-E":
                case "--estandar":
                    listaInstanc = new ArrayList();
                    nombreArchivoResultado = "--estandar";
                    if (args.length > indice) {
                        nombreArchivoResultado = args[indice++];
                    }
                    listaInstanc.add(new Instancia("--estandar", "--estandar", "--estandar"));
                    ayuda = false;
                    break;
                case "-i":
                case "--individuo":
                    tsalida = "i";
                    break;
                case "-I":
                case "--indices":
                    tsalida = "I";
                    break;
                case "-v":
                case "--evaluacion":
                    tsalida = "v";
                    break;
                case "--depuracion":
                    depuracion = true;
                    break;
                case "--evresumen":
                    evresumen = true;
                    break;
                case "-b":
                case "--ambos":
                    tsalida = "b";
                    break;
                case "-r":
                case "--algoritmo":
                    //algoritmos
                    if (args.length > indice) {
                        algoritmo = args[indice++];
                    }
                    break;
                case "-g":
                case "--arg-algoritmo":
                    //parametros para algoritmo
                    if (args.length > indice) {
                        parametrosAlgoritmo = args[indice++];
                    }
                    ayuda = false;
                    break;
            }
        }
        nombreExperimento+=algoritmo+"|";
        nombreExperimento+=parametrosAlgoritmo+"|";
        nombreExperimento+=numIntentos+"|";
        if(datos != null ){
            nombreExperimento+= "GRP:" + datos.getNombre()+"|";
        }

        if (!ayuda) {
            ejecutar(numIntentos, listaInstanc, nombreArchivoResultado);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Ayuda\n");
            sb.append("Comando <tipo salida> <opcion>\n");

            sb.append("\t, --evresumen:\n"
                    + "\t\tImprimir resumen de evaluacion.\n");
            
            sb.append("Tipos salida:\n");
            sb.append("\t-v, --evaluacion: (por defecto)\n"
                    + "\t\tImprimir Evaluacion.\n");
            
            sb.append("\t-i, --individuo:\n"
                    + "\t\tImprimir individuo.\n");
            
            sb.append("\t-I, --indices:\n"
                    + "\t\tImprimir indices del mejor individuo, ordenados de mayor a menor densidad (calidad/peso).\n");
            
            sb.append("\t-b, --ambos:\n"
                    + "\t\tImprimir ambos, individuo y estadistica.\n");
            
            sb.append("Opciones:\n");
            sb.append("\t-e --ejemplos:\n\t\tEjecutar todos los ejemplos.\n");
            sb.append("\t-a, --archivo <archivo-entrada> [<archivo-salida>]:\n"
                    + "\t\tEjecutar una instancia desde un archivo.\n");
            sb.append("\t-E, --estandar [<archivo-salida>]:\n"
                    + "\t\tLee desde la entrada estandar.\n");
            System.out.println(sb.toString());
        }
    }

    public void ejecutar(int numIntentos, List<Instancia> listaInstanc, String nombreArchivoResultado) throws InterruptedException {

        boolean graficaRecorrido3D = false; //true solo para SO con gnuplot y para (2 dimensiones + calidad) osea 3D
        boolean graficaDispercion2D = false; // true para graficas de dispersion con gnuplot
//        graficaRecorrido3D = true;
//        graficaDispercion2D = true;
        // numero de veces que se ejecuta un mismo algoritmo con una misma funcion
        String nombreArchivoCompleto;
        IndividuoCuadratico mejorGlobal = null;
        String mensaje = "";
        List<ResultadoGrupo> listResultadosGrupos = new ArrayList();

        ImprimirResultados imprimir = new ImprimirResultados(nombreArchivoResultado);
        List<HiloEjecucion> hilos = new ArrayList<>();
        // no imprimir cabeceras para resumen
        if((!evresumen) && ("v".equals(tsalida) || "b".equals(tsalida))){
            StringBuilder sbCabecera = new StringBuilder();
            sbCabecera.append("----------------------------------------------.\n");
            sbCabecera.append("Numero Intentos:").append(numIntentos).append("\n");
            sbCabecera.append("NPI: Numero iteraciones promedio.\n");
            sbCabecera.append("TP: Tiempo Promedio.\n");
            sbCabecera.append("TE: Tasa de exito.\n");
            sbCabecera.append("DPR: Desviación porcentual relativa.\n");
            sbCabecera.append("----------------------------------------------.\n");
            String campos = formatearCabecera("NOMBRE", "FUNCION", "ALGORITMO", "DIMENSION", "NPI", "TE", "MEJOR OPTIMO",
                    "PROM OPTIMOS", "DPR", "TP", "EVALUACIONES");
            sbCabecera.append(campos);
            imprimir.imprimir(sbCabecera.toString());
        }
        
        
        String nombreInst;
        for (Instancia instancia : listaInstanc) {
            nombreInst = instancia.getNombre();
            nombreArchivoCompleto = instancia.getNombreCompleto();
            mensaje = instancia.getFamilia();
            // dimension de los puntos;
            LecturaParametrosCuadratica lpc = new LecturaParametrosCuadratica();
            InstanciaAlgoritmo instanciasAlgot = lpc.obtenerParametrosInstancias(instancia);
            if (instanciasAlgot == null) {
                if((!evresumen) && ("v".equals(tsalida) || "b".equals(tsalida))) {
                    imprimir.imprimir("#========== No se encontro el archivo (" + nombreArchivoCompleto + ")\n");
                }
                continue;
            }
            GrupoAlgoritmosMochilaCuadratica grupoAlgoritmos = new GrupoAlgoritmosMochilaCuadratica(instanciasAlgot);
            GrupoAlgoritmosMochilaCuadratica.AlgoritmoOpion algot = null;
            if (algoritmo != null) {
                try {
                    algot = GrupoAlgoritmosMochilaCuadratica.AlgoritmoOpion.valueOf(algoritmo);
//                    System.out.println("algoritmo: " + algoritmo);
                    grupoAlgoritmos.setOpcion(algot);
                    grupoAlgoritmos.setParametrosAlgoritmo(parametrosAlgoritmo);
                } catch (java.lang.IllegalArgumentException e) {
                    System.out.println("Algoritmo <" + algoritmo + "> no existe");
                    return;
                }
            } else {
                System.out.println("Falta indicar el algoritmo");
                return;
            }
            grupoAlgoritmos.setInstancias(instanciasAlgot);
            grupoAlgoritmos.inicializar();
            EjecutarGrupo ejecutor = new EjecutarGrupo();
            // EJECUTAR ANALISIS
            ejecutor.setParametros(grupoAlgoritmos, graficaRecorrido3D, graficaDispercion2D, numIntentos, nombreInst);
            // Multi-hilo
            hilos.add(new HiloEjecucion(instanciasAlgot, ejecutor, mensaje));

        }
        // esperar que todas la ejecuciones terminen hasta aqui
        for (HiloEjecucion hilo : hilos) {
            hilo.start();
        }
        String nombreIns = "#";
        InstanciaAlgoritmo parametros = null;
        ResultadoGrupo resultadoGrupo;
        // Imprimir resultados y estadisticas
        for (HiloEjecucion hilo : hilos) {
            hilo.join();

            resultadoGrupo = hilo.getResultadoGrupo();

            if (resultadoGrupo != null) {
                parametros = resultadoGrupo.getParametros();

                if (!nombreIns.equals(resultadoGrupo.getInstancia())) {
                    nombreIns = resultadoGrupo.getInstancia();
                    if((!evresumen) && ("v".equals(tsalida) || "b".equals(tsalida))) {
//                        imprimir.imprimir("#====================================================================================================(" + parametros.getMaxGlobal() + ")\n");
                    }
                }
                listResultadosGrupos.add(resultadoGrupo);
                LecturaParametrosCuadratica lpc = new LecturaParametrosCuadratica();

                String stringResult = armarResultados(resultadoGrupo);
                if((!evresumen) && ("v".equals(tsalida) || "b".equals(tsalida))) {
                    imprimir.imprimir(stringResult);
                }
                IndividuoCuadratico individuo = (IndividuoCuadratico) resultadoGrupo.getMejorIndividuo();
                // almacenar mejor global
                if (mejorGlobal == null || mejorGlobal.compareTo(individuo) < 0) {
                    mejorGlobal = individuo;
                }
                // comprobar calidad de la actua instancia y actualizar los archivos de InstanciaAlgoritmo
                {
                    if (parametros.getMaxGlobal().isNaN() || parametros.getMaxGlobal().compareTo(individuo.getCalidad()) < 0) {
                        parametros.setMaxGlobal(individuo.getCalidad());
                        parametros.setVectorIdeal(vDouble_vInt(individuo.getValores()));

                        try {
                            lpc.actualizar(parametros.getNombreArchivo(), parametros);
                        } catch (Exception e) {
                            System.err.println("---fallo la actualizacion del maximo global:\n " + e.getLocalizedMessage());
                            System.err.println("---mensaje de error: \n " + e.getMessage());
                        }
                    } else if (parametros.getMaxGlobal().compareTo(individuo.getCalidad()) > 0) {
//                        System.out.println("No se encontro mejor");
                    }
                }
            } else {
                System.out.println("pailas");
            }
        }

        //EstadisticasResultados estadResult = new EstadisticasResultados();

        // imprimir mejor y resumen
        if ("i".equals(tsalida) || "b".equals(tsalida)) {
            if (mejorGlobal != null) {
                imprimir.imprimir(mejorGlobal.toStringInt());
            }
        }

        // imprimir mejor
        if ("I".equals(tsalida)) {
            if (mejorGlobal != null) {
                imprimir.imprimir(mejorGlobal.toStringIndicesOrdenados());
                imprimir.imprimir("" + mejorGlobal.getCalidad());
            }
        }

        if (!listResultadosGrupos.isEmpty()) {
//            estadResult.estadisticas(listResultadosGrupos);
            String resumen = "-----RESUMEN----\n" + armarResumen(listResultadosGrupos);
            if ("v".equals(tsalida) || "b".equals(tsalida)) {
                imprimir.imprimir(resumen);
            }
        }
    }

    class HiloEjecucion extends Thread {

        private final InstanciaAlgoritmo parametros;
        private final EjecutarGrupo ejecutor;
        private ResultadoGrupo resultadoGrupo;
        private final String mensaje;

        public HiloEjecucion(InstanciaAlgoritmo parametros, EjecutarGrupo ejecutor, String mensaje) {
            this.parametros = parametros;
            this.ejecutor = ejecutor;
            this.mensaje = mensaje;
        }

        @Override
        public void run() {
            resultadoGrupo = ejecutor.ejecutarGrupo();
            resultadoGrupo.setParametros(parametros);
        }

        public ResultadoGrupo getResultadoGrupo() {
            return resultadoGrupo;
        }

    }

    public int[] vDouble_vInt(double[] vector) {
        int[] vectorInt = new int[vector.length];
        for (int i = 0; i < vector.length; i++) {
            vectorInt[i] = (int) vector[i];
        }
        return vectorInt;
    }

    public String armarResultados(ResultadoGrupo resultados) {
        StringBuilder sb = new StringBuilder();
        for (ResultadoAlgoritmo resultado : resultados) {

            AlgoritmoMetaheuristico algot = resultado.algoritmo;
            FuncionGen funcion = algot.getFuncion();
            String cad = formatearCabecera(
                    resultados.getInstancia(),
                    funcion.getNombre(),
                    algot.getNombre(),
                    UtilCuadratica.formatear(funcion.getDimension()),
                    UtilCuadratica.formatear(resultado.promedioIteraciones),
                    UtilCuadratica.formatear((double) resultado.exitos),
                    UtilCuadratica.formatear(resultado.mejorRecorrido.getMejorIndividuo().getCalidad()),
                    UtilCuadratica.formatear(resultado.promedioCalidadOptimos),
                    UtilCuadratica.formatear(resultado.desviacionCalidadOptimos),
                    UtilCuadratica.formatear(resultado.tiempoTotal),
                    UtilCuadratica.formatear(resultado.promedionumEvaluaciones)
            );
            sb.append(cad);
        }
        return sb.toString();
    }

    public String armarResumen(List<ResultadoGrupo> resultadosGrupos) {
        String resumen;
        StringBuilder sb = new StringBuilder("");
        ResultadoGrupo grupoAux = resultadosGrupos.get(0);
        int tamGrupoAux = grupoAux.size();
        double[] aciertos = new double[tamGrupoAux];
        double[] desviacion = new double[tamGrupoAux];
        double[] tiempos = new double[tamGrupoAux];
        for (int i = 0; i < resultadosGrupos.size(); i++) {
            ResultadoGrupo grupo = resultadosGrupos.get(i);
            for (int j = 0; j < tamGrupoAux; j++) {
                ResultadoAlgoritmo resultado = grupo.get(j);
                aciertos[j] += resultado.exitos;
                desviacion[j] += resultado.desviacionCalidadOptimos;
                tiempos[j] += resultado.tiempoTotal;
            }
        }
        StringBuilder cadena;
        sb.append("\n");
        for (int i = 0; i < tamGrupoAux; i++) {
            cadena = new StringBuilder();
            cadena.append("Algoritmo,aciertos(%),desviacion,tiempo promedio(ms)").append("\n");
            cadena.append(grupoAux.get(i).algoritmo.getNombre()).append("|");
            cadena.append(UtilCuadratica.formatear(aciertos[i] * 100 / (double) resultadosGrupos.size())).append("|");
            cadena.append(UtilCuadratica.formatear(desviacion[i] / (double) resultadosGrupos.size())).append("|");
            cadena.append(UtilCuadratica.formatear(tiempos[i] / (double) resultadosGrupos.size())).append("|");
            cadena.append(nombreExperimento).append("|");
            sb.append(cadena.toString());
        }
        resumen = sb.toString();
        return resumen;
    }

    public String formatearCabecera(String nombre, String funcion, String algoritmo, String dimension, String promIteraciones,
            String mejorOptimo, String peorOptimo, String promedioOptimos, String desviacionOpti, String tiempoPromedio, String numEvaluaciones) {
        String cadena = String.format("%-20s|%-20s|%-30s|%-10s|%-8s|%-8s|%-14s|%-12s|%-8s|%-10s|%-15s\n", nombre, funcion, algoritmo, dimension, promIteraciones,
                mejorOptimo, peorOptimo, promedioOptimos, desviacionOpti, tiempoPromedio, numEvaluaciones);
        return cadena;
    }

}
