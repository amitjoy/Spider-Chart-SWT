package com.amitinside.tooling.chart.builder;

import org.eclipse.swt.widgets.Composite;

import com.amitinside.tooling.chart.FillStyle;
import com.amitinside.tooling.chart.Legend;
import com.amitinside.tooling.chart.LineStyle;
import com.amitinside.tooling.chart.SpiderPlotter;
import com.amitinside.tooling.chart.Title;
import com.amitinside.tooling.chart.gc.ChartColor;
import com.amitinside.tooling.chart.gc.ChartFont;
import com.amitinside.tooling.chart.gc.GraphicsProvider;

public class Sample {

	public void main(final String[] args, final Composite parent) {
		ChartBuilder.config(parent, settingsBuilder -> {
			// Chart Related Settings
			// Legend
			final Legend legend = new Legend();
			legend.background = new FillStyle(GraphicsProvider.getColor(ChartColor.WHITE));
			legend.border = new LineStyle(1, GraphicsProvider.getColor(ChartColor.BLACK), LineStyle.LINE_NORMAL);
			legend.addItem("Products",
					new LineStyle(1, GraphicsProvider.getColor(ChartColor.BLUE), LineStyle.LINE_NORMAL));
			legend.addItem("Services",
					new LineStyle(1, GraphicsProvider.getColor(ChartColor.GREEN), LineStyle.LINE_NORMAL));

			settingsBuilder.setLegend(legend);

			// Title
			final Title title = new Title("Sales (thousands $)");

			settingsBuilder.setTitle(title);

			// Plotter
			final SpiderPlotter plotter = new SpiderPlotter();
			plotter.width = 700;
			plotter.height = 700;

			final double[] fMaxs = { 6, 6, 6, 6, 6 };
			final double[] fMins = { 0, 0, 0, 0, 0 };
			final String[] factors = { "factor1", "factor2", "factor3", "factor4", "factor5", "factor6" };

			plotter.factorMaxs = fMaxs;
			plotter.factorMins = fMins;
			plotter.factorNames = factors;
			plotter.backStyle = new FillStyle(GraphicsProvider.getColor(ChartColor.YELLOW));
			plotter.radiusModifier = 0.8;

			plotter.gridStyle = new LineStyle(1, GraphicsProvider.getColor(ChartColor.BLACK), LineStyle.LINE_DASHED);
			plotter.gridFont = GraphicsProvider.getFont("Arial", ChartFont.PLAIN, 10);

			settingsBuilder.setPlotter(plotter);

		}).viewer(chartBuilder -> {
			chartBuilder.chart(dataBuilder1 -> {
				// First Data
				final double[] d1 = { 1, 2, 3, 4, 5 };
				dataBuilder1.setValues(d1, "BLUE");
				dataBuilder1.done();
			});
			chartBuilder.chart(dataBuilder2 -> {
				// Second Data
				final double[] d2 = { 2, 3, 4, 4.2, 3 };
				dataBuilder2.setValues(d2, "GREEN");
				dataBuilder2.done();
			});
		});

	}

}
