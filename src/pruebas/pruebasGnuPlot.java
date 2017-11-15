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
package pruebas;

import org.jgnuplot.*;

/**
 *
 * @author debian
 */
public class pruebasGnuPlot {

    public static void main(String[] args) {
        gnuplot();
//        ControlsDem.gnuplot();
//        ElectronDem.gnuplot();
//        UsingDem.gnuplot();
//        FillStyleDem.gnuplot();
//        // mgr.dem
//        // fit.dem
//        ParamDem.gnuplot();
//        PolarDem.gnuplot();
    }


    public static void gnuplot() {
        Plot.setGnuplotExecutable("gnuplot  -persist");
        Plot.setPlotDirectory("/tmp");
        
        String salida = Terminal.NOT_SPECIFIED;
        
        Plot aPlot = new Plot();
        aPlot.setKey("left box");
        aPlot.setSamples("50");
        aPlot.setRanges("[-10:10]");
        aPlot.pushGraph(new Graph("sin(x)"));
        aPlot.pushGraph(new Graph("atan(x)"));
        aPlot.pushGraph(new Graph("cos(atan(x))"));
        aPlot.setOutput(salida, "test/out/simple-01.png");
        Graph g;
        try {
            aPlot.plot();
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
//
//        aPlot.clear();
//        aPlot.setKey("right nobox");
//        aPlot.setSamples("100");
//        aPlot.setRanges("[-pi/2:pi]");
//        aPlot.pushGraph(new Graph("cos(x)"));
//        aPlot.pushGraph(new Graph("-(sin(x) > sin(x+1) ? sin(x) : sin(x+1))"));
//        aPlot.setOutput(salida, "test/out/simple-02.png");
//        try {
//            aPlot.plot();
//        } catch (Exception e) {
//            System.err.println(e);
//            System.exit(1);
//        }
//
//        aPlot.clear();
//        aPlot.setKey("left box");
//        aPlot.setSamples("200");
//        aPlot.setRanges("[-3:5]");
//        aPlot.pushGraph(new Graph("asin(x)"));
//        aPlot.pushGraph(new Graph("acos(x)"));
//        aPlot.setOutput(salida, "test/out/simple-03.png");
//        try {
//            aPlot.plot();
//        } catch (Exception e) {
//            System.err.println(e);
//            System.exit(1);
//        }
//
//        aPlot.clear();
//        aPlot.setRanges("[-30:20]");
//        aPlot.pushGraph(new Graph("besj0(x)*0.12e1", Axes.NOT_SPECIFIED, null,
//                Style.IMPULSES));
//        aPlot.pushGraph(new Graph("(x**besj0(x))-2.5", Axes.NOT_SPECIFIED, null,
//                Style.POINTS));
//        aPlot.setOutput(salida, "test/out/simple-04.png");
//        try {
//            aPlot.plot();
//        } catch (Exception e) {
//            System.err.println(e);
//            System.exit(1);
//        }
//
//        aPlot.clear();
//        aPlot.setSamples("400");
//        aPlot.setRanges("[-10:10]");
//        aPlot.pushGraph(new Graph("real(sin(x)**besj0(x))"));
//        aPlot.setOutput(salida, "test/out/simple-05.png");
//        try {
//            aPlot.plot();
//        } catch (Exception e) {
//            System.err.println(e);
//            System.exit(1);
//        }
//
//        aPlot.clear();
//        aPlot.setKey("outside below");
//        aPlot.setRanges("[-5*pi:5*pi] [-5:5]");
//        aPlot.pushGraph(new Graph("real(tan(x)/atan(x))"));
//        aPlot.pushGraph(new Graph("1/x"));
//        aPlot.setOutput(salida, "test/out/simple-06.png");
//        try {
//            aPlot.plot();
//        } catch (Exception e) {
//            System.err.println(e);
//            System.exit(1);
//        }
//
//        aPlot.clear();
//        aPlot.setKey("left box");
//        aPlot.setAutoscale();
//        aPlot.setSamples("800");
//        aPlot.setRanges("[-30:20]");
//        aPlot.pushGraph(new Graph("sin(x*20)*atan(x)"));
//        aPlot.setOutput(salida, "test/out/simple-07.png");
//        try {
//            aPlot.plot();
//        } catch (Exception e) {
//            System.err.println(e);
//            System.exit(1);
//        }
//
//        aPlot.clear();
//        aPlot.setRanges("[-19:19]");
//        aPlot.pushGraph(new Graph("1.dat", null, Axes.NOT_SPECIFIED, null,
//                Style.IMPULSES, LineType.NOT_SPECIFIED, PointType.NOT_SPECIFIED));
//        aPlot.pushGraph(new Graph("2.dat", null, Axes.NOT_SPECIFIED, null,
//                Style.NOT_SPECIFIED, LineType.NOT_SPECIFIED,
//                PointType.NOT_SPECIFIED));
//        aPlot.pushGraph(new Graph("3.dat", null, Axes.NOT_SPECIFIED, null,
//                Style.LINES, LineType.NOT_SPECIFIED, PointType.NOT_SPECIFIED));
//        aPlot.setOutput(salida, "test/out/simple-08.png");
//        try {
//            aPlot.plot();
//        } catch (Exception e) {
//            System.err.println(e);
//            System.exit(1);
//        }
//
//        aPlot.clear();
//        aPlot.setRanges("[-19:19]");
//        aPlot.addExtra("f(x) = x/100");
//        aPlot.pushGraph(new Graph("1.dat", null, Axes.NOT_SPECIFIED, null,
//                Style.IMPULSES, LineType.NOT_SPECIFIED, PointType.NOT_SPECIFIED));
//        aPlot.pushGraph(new Graph("2.dat", null, "thru f(x)", Axes.NOT_SPECIFIED,
//                null, Style.NOT_SPECIFIED, LineType.NOT_SPECIFIED,
//                PointType.NOT_SPECIFIED));
//        aPlot.pushGraph(new Graph("3.dat", null, Axes.NOT_SPECIFIED, null,
//                Style.LINES, LineType.NOT_SPECIFIED, PointType.NOT_SPECIFIED));
//        aPlot.setOutput(salida, "test/out/simple-09.png");
//        try {
//            aPlot.plot();
//        } catch (Exception e) {
//            System.err.println(e);
//            System.exit(1);
//        }
    }
}
