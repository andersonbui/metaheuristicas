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
import main.mochila.cuadratica.utilidades.EstadisticasResultados;
import main.mochila.cuadratica.utilidades.LecturaParametrosCuadratica;
import main.mochila.cuadratica.utilidades.InstanciaAlgoritmo;
import metaheuristicas.AlgoritmoMetaheuristico;
import metaheuristicas.funcion.FuncionGen;

/**
 *
 * @author debian
 */
public class MainMochilaCuadratica {

    public static String tsalida = "v";
    public static String algoritmo = null;
    static String parametrosAlgoritmo;
   
    public static void main(String[] args) throws FileNotFoundException, Exception {
        List<String[]> listaVectArgumentas = new ArrayList();
        // comentar todo el if para produccion
//        System.out.println("max calue: "+Double.MAX_VALUE);
//        System.out.println("min calue: "+Double.MIN_VALUE);
//        double flotante =  1797693.13486231575554542;
//        flotante = Math.floor(flotante*1000000000)/1000000000;
//        
//        System.out.println("flotante: "+flotante);
//        
//        System.out.println("POSITIVE_INFINITY calue: "+flotante);
        if (args.length == 0) {
//            listaVectArgumentas.add(new String[]{"-e", "-r", "IHEA_M3", "L=2,mt=15,mt=5"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "IHEA_M2", "L=2,mt=15,mt=5"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "SGVNS","-g", "i_interc=5, i_encontrarM=5"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "SGVNS","-g", "i_interc=15, i_encontrarM=5"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "JSGVNS","-g", "i_interc=15, i_encontrarM=5"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "SGVNS_M1","-g", "i_interc=5, i_encontrarM=5"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "SGVNS_M2","-g", "i_interc=5, i_encontrarM=5"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "SGVNS_M3","-g", "i_interc=5, i_encontrarM=5"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "IHEA_GAR", "L=20,mt=10,ms=5"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "IHEA_M1", "L=20,mt=10,ms=5"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "IHEA_M1", "L=2,mt=15,mt=5"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "IHEA_M4", "L=2,mt=15,mt=5"});
//            listaVectArgumentas.add(new String[]{"-e", "-r", "IHEA_VA", "L=2,mt=15,mt=5"});
//            args = new String[]{"--archivo", "/home/debian/Documentos/Proyecto_grado/frameworks/framework-java-metaheuristicas/framework-metaheuristicas/mochilaCuadratica/grupo1/jeu_100_25_1.txt", "jeu_100_25_1_salida.txt"};
//            args = new String[]{"--estandar"};
//            args = new String[]{"--estandar", " < /home/debian/Documentos/Proyecto_grado/frameworks/framework-java-metaheuristicas/framework-metaheuristicas/mochilaCuadratica/grupo1/jeu_100_25_1.txt"};
//            args = new String[]{"-I","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework-java-metaheuristicas/framework-metaheuristicas/mochilaCuadratica/r_10_100_13.txt"};
//            listaVectArgumentas.add(new String[]{"-v","-r","IHEA_GAR","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework-java-metaheuristicas/framework-metaheuristicas/mochilaCuadratica/r_10_100_13.txt"});
//              listaVectArgumentas.add(new String[]{"-b","-r","IHEA_GAR","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework-java-metaheuristicas/framework-metaheuristicas/mochilaCuadratica/grupo1/jeu_100_100_2.txt"});
//              listaVectArgumentas.add(new String[]{"-I","-r","IHEA_GAR","-g","L=30,rcl=30,mt=50,ms=20","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework_GAR/Resultados/Generico/instancia_D0609I_0.txt"});
              listaVectArgumentas.add(new String[]{"-I","-r","IHEA_GAR","-g","L=30,rcl=30,mt=5,ms=2,CONS=0.0:0.5:0.5,DESC=0.0:0.5:0.5,PERT=0.0:0.5:0.5,TABU=0.0:0.5:0.5","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework_GAR/Resultados/Generico/instancia_D0626H_0.txt"}); //se detiene
//              listaVectArgumentas.add(new String[]{"-I","-r","IHEA_GAR","-g","L=20,mt=10,ms=5","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework-java-metaheuristicas/framework-metaheuristicas/mochilaCuadratica/resumenes/instancia_D0601A_0.txt"});
//            listaVectArgumentas.add(new String[]{"-I","-r","IHEA2","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework-java-metaheuristicas/framework-metaheuristicas/mochilaCuadratica/resumenes/instancia_D632i_0.txt"});
//            listaVectArgumentas.add(new String[]{"-I","-r","IHEA","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework-java-metaheuristicas/framework-metaheuristicas/mochilaCuadratica/resumenes/instancia_D632i_0.txt"});
//            listaVectArgumentas.add(new String[]{"-I","-r","SGVNS", "rcl=30,L=20,mi=65,ms=4,mt=10","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework_GAR/Resultados/Generico/instancia_D0615F_0.txt"});
//            listaVectArgumentas.add(new String[]{"-I","-r","IHEA_M1", "rcl=30,L=20,mi=65,ms=4,mt=10","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework_GAR/Resultados/Generico/instancia_D0615F_0.txt"});
//            listaVectArgumentas.add(new String[]{"-v","-a","/home/debian/Documentos/Proyecto_grado/frameworks/framework-java-metaheuristicas/framework-metaheuristicas/mochilaCuadratica/resumenes/instancia_D0626H_0.txt"});
        } else {
            listaVectArgumentas.add(args);
        }
        for (String[] vectArgumentos : listaVectArgumentas) {
//            System.out.println("entradas: "+Arrays.toString(vectArgumentos));
            ejecucion(vectArgumentos);
        }
    }

    public static void ejecucion(String[] args) throws FileNotFoundException, Exception {
        int numIntentos = 1;
        int indice = 0;
//        boolean repetir = true;
        boolean ayuda = true;
        List<Instancia> listaInstanc = null;
        String nombreArchivoResultado = "";
        while (args.length > indice) {
            String opcion = args[indice++];
            switch (opcion) {
                case "-e":
                case "--examples":
//                    ConjuntoInstancias datos = new ConjuntoInstancias1000();
//                    ConjuntoInstancias datos = new ConjuntoInstancias300();
//                    ConjuntoInstancias datos = new ConjuntoInstancias100();
                        ConjuntoInstancias datos = new ConjuntoInstanciasResumenes();
//                        ConjuntoInstancias datos = new ConjuntoInstanciasPruebas();
                    nombreArchivoResultado = "";
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

        if (!ayuda) {
            ejecutar(numIntentos, listaInstanc, nombreArchivoResultado);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Ayuda\n");
            sb.append("Comando <tipo salida> <opcion>\n");

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

    public static void ejecutar(int numIntentos, List<Instancia> listaInstanc, String nombreArchivoResultado) throws InterruptedException {

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
        StringBuilder sbCabecera = new StringBuilder();
        sbCabecera.append("----------------------------------------------------------.\n");
        sbCabecera.append("Numero Intentos:").append(numIntentos).append("\n");
        sbCabecera.append("NPI: Numero iteraciones promedio.\n");
        sbCabecera.append("TP: Tiempo Promedio.\n");
        sbCabecera.append("TE: Tasa de exito.\n");
        sbCabecera.append("DPR: Desviación porcentual relativa.\n");
        sbCabecera.append("----------------------------------------------------------.\n");

        String campos = formatearCabecera("NOMBRE", "FUNCION", "ALGORITMO", "DIMENSION", "NPI", "TE", "MEJOR OPTIMO",
                "PROM OPTIMOS", "DPR", "TP", "EVALUACIONES");
        sbCabecera.append(campos);
        if ("v".equals(tsalida) || "b".equals(tsalida)) {
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
                if ("v".equals(tsalida) || "b".equals(tsalida)) {
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
//                grupoAlgoritmos.setOpcion(GrupoAlgoritmosMochilaCuadratica.AlgoritmoOpion.OPCION_JSGVNS);
//                grupoAlgoritmos.setOpcion(GrupoAlgoritmosMochilaCuadratica.AlgoritmoOpion.OPCION_IHEA_VA);
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
                    if ("v".equals(tsalida) || "b".equals(tsalida)) {
//                        imprimir.imprimir("#====================================================================================================(" + parametros.getMaxGlobal() + ")\n");
                    }
                }
                listResultadosGrupos.add(resultadoGrupo);
                LecturaParametrosCuadratica lpc = new LecturaParametrosCuadratica();

                String stringResult = armarResultados(resultadoGrupo);
                if ("v".equals(tsalida) || "b".equals(tsalida)) {
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

        EstadisticasResultados estadResult = new EstadisticasResultados();

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
            String resumen = armarResumen(listResultadosGrupos);
            if ("v".equals(tsalida) || "b".equals(tsalida)) {
                imprimir.imprimir(resumen);
            }
        }
    }

    static class HiloEjecucion extends Thread {

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

    public static int[] vDouble_vInt(double[] vector) {
        int[] vectorInt = new int[vector.length];
        for (int i = 0; i < vector.length; i++) {
            vectorInt[i] = (int) vector[i];
        }
        return vectorInt;
    }

    public static String armarResultados(ResultadoGrupo resultados) {
        StringBuilder sb = new StringBuilder();
        for (ResultadoAlgoritmo resultado : resultados) {

            AlgoritmoMetaheuristico algot = resultado.algoritmo;
            FuncionGen funcion = algot.getFuncion();
            String cad = formatearCabecera(
                    resultados.getInstancia(),
                    funcion.getNombre(),
                    algot.getNombre(),
                    formatear(funcion.getDimension()),
                    formatear(resultado.promedioIteraciones),
                    formatear((double) resultado.exitos),
                    formatear(resultado.mejorRecorrido.getMejorIndividuo().getCalidad()),
                    formatear(resultado.promedioCalidadOptimos),
                    formatear(resultado.desviacionCalidadOptimos),
                    formatear(resultado.tiempoTotal),
                    formatear(resultado.promedionumEvaluaciones)
            );
            sb.append(cad);
        }
        return sb.toString();
    }

    public static String armarResumen(List<ResultadoGrupo> resultadosGrupos) {
        String resumen;
        StringBuilder sb = new StringBuilder("--------------------RESUMEN-----------------");
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
            cadena.append("Algoritmo[").append(grupoAux.get(i).algoritmo.getNombre()).append("]\n");
            cadena.append("aciertos:").append(formatear(aciertos[i] * 100 / (double) resultadosGrupos.size())).append("%\n");
            cadena.append("desviacion:").append(formatear(desviacion[i] / (double) resultadosGrupos.size())).append("\n");
            cadena.append("tiempo promedio:").append(formatear(tiempos[i] / (double) resultadosGrupos.size())).append("ms\n");
            cadena.append("-----------\n");
            sb.append(cadena.toString());
        }
        resumen = sb.toString();
//        System.out.println(resumen);
        return resumen;
    }

    public static String formatearCabecera(String nombre, String funcion, String algoritmo, String dimension, String promIteraciones,
            String mejorOptimo, String peorOptimo, String promedioOptimos, String desviacionOpti, String tiempoPromedio, String numEvaluaciones) {
        String cadena = String.format("%-20s|%-20s|%-30s|%-10s|%-8s|%-8s|%-14s|%-12s|%-8s|%-10s|%-15s\n", nombre, funcion, algoritmo, dimension, promIteraciones,
                mejorOptimo, peorOptimo, promedioOptimos, desviacionOpti, tiempoPromedio, numEvaluaciones);
        return cadena;
    }

    public static String formatear(double valor) {
        if (valor > 100000) {
            return String.format("%-2.2f", valor);
        }
        if (valor < 10) {
            return String.format("%-1.6f", valor);
        } else {
            return String.format("%.2f", valor);
        }
    }

    public static String formatear(int valor) {
        if (valor > 100000) {
            return String.format("%-2E", 1.0 * valor);
        } else {
            return String.format("%4d" + "", valor);
        }
    }
}
