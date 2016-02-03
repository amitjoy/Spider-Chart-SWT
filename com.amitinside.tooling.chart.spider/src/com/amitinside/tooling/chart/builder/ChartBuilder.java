package com.amitinside.tooling.chart.builder;

import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.amitinside.tooling.chart.Chart;
import com.amitinside.tooling.chart.FillStyle;
import com.amitinside.tooling.chart.LineStyle;
import com.amitinside.tooling.chart.SpiderPlotter;
import com.amitinside.tooling.chart.Title;
import com.amitinside.tooling.chart.gc.ChartColor;
import com.amitinside.tooling.chart.gc.ChartFont;
import com.amitinside.tooling.chart.gc.GraphicsProvider;
import com.amitinside.tooling.chart.gc.swt.SwtGraphicsProvider;
import com.amitinside.tooling.chart.swt.ChartViewer;

public class ChartBuilder {

	public static ChartViewer viewer(final Composite parent, final Consumer<ChartBuilder> chartBuilderConsumer) {
		final ChartBuilder chartViewerBuilder = new ChartBuilder(parent);
		chartBuilderConsumer.accept(chartViewerBuilder);
		return chartViewerBuilder.chartViewer;
	}

	private final Chart chart;
	private final ChartViewer chartViewer;

	public ChartBuilder(final Composite parent) {
		SwtGraphicsProvider.setDefaultDisplay(parent.getShell().getDisplay());
		this.chartViewer = new ChartViewer(parent, SWT.NONE);
		this.prepareChartViewer(parent);

		// TODO Needs to changed
		final Title title = new Title("Sales (thousands $)");

		final SpiderPlotter plot = new SpiderPlotter();
		plot.width = 700;
		plot.height = 700;

		final double[] fMaxs = { 6, 6, 6, 6, 6 };
		final double[] fMins = { 0, 0, 0, 0, 0 };
		final String[] factors = { "factor1", "factor2", "factor3", "factor4", "factor5", "factor6" };

		plot.factorMaxs = fMaxs;
		plot.factorMins = fMins;
		plot.factorNames = factors;
		plot.backStyle = new FillStyle(GraphicsProvider.getColor(ChartColor.YELLOW));
		plot.radiusModifier = 0.8;

		plot.gridStyle = new LineStyle(1, GraphicsProvider.getColor(ChartColor.BLACK), LineStyle.LINE_DASHED);
		plot.gridFont = GraphicsProvider.getFont("Arial", ChartFont.PLAIN, 10);

		this.chart = new Chart(title, plot, null, null);

	}

	public void chart(final Consumer<DataBuilder> dataBuilderConsumer) {
		final DataBuilder dataBuilder = new DataBuilder(this.chart);
		dataBuilderConsumer.accept(dataBuilder);
		this.chartViewer.setChart(this.chart);
	}

	private void prepareChartViewer(final Composite parent) {
		this.chartViewer.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		this.chartViewer.setBounds(parent.getShell().getClientArea().x, parent.getShell().getClientArea().y,
				parent.getShell().getClientArea().width, parent.getShell().getClientArea().height - 20);
		this.chartViewer.changePointer = true;
		this.chartViewer.changePointer = true;
		this.chartViewer.allowZoom = true;
	}

}
