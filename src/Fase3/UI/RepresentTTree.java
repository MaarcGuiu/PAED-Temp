package Fase3.UI;

import Fase3.RTree.Jugador;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RepresentTTree extends JFrame {

    public static void representGraficament(List<Jugador> jugadors) {
        XYSeries series = new XYSeries("Jugadors");

        for (Jugador jugador : jugadors) {
            series.add(jugador.getBattlesDone(), jugador.getBattlesWon());
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart scatterPlot = ChartFactory.createScatterPlot(
                "Representació Gràfica R-Tree",
                "Batalles fetes",
                "Batalles guanyades",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        XYPlot plot = scatterPlot.getXYPlot();
        XYItemRenderer renderer = new XYLineAndShapeRenderer(false, true);
        plot.setRenderer(renderer);

        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);

        ChartPanel panel = new ChartPanel(scatterPlot);
        panel.setPreferredSize(new Dimension(800, 600));

        RepresentTTree frame = new RepresentTTree();
        frame.setContentPane(panel);
        frame.setTitle("Representació gràfica R-Tree");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
