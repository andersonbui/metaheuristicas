set isosample 50
set style line 100 lc rgb "gray20" lw 0.5
set xrange [-10.0:10.0]
set yrange [-10.0:10.0]
set title "SUBIENDO LA COLINA(ACKLEY)"
set view 58,228
a(x,y) = -20*exp(-0.2*sqrt(0.5*(x**2+y**2))) - exp(0.5*(cos(2*pi*x)+cos(2*pi*y))) + 20 + exp(1)
splot a(x,y) w l lw 0.5 , ".dat" title "2" w lp linewidth 2 linetype 6
