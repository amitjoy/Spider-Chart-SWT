package com.amitinside.tooling.chart.builder;

import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.amitinside.tooling.chart.Chart;
import com.amitinside.tooling.chart.FillStyle;
import com.amitinside.tooling.chart.builder.model.ChartConfigurationBuilder;
import com.amitinside.tooling.chart.gc.ChartColor;
import com.amitinside.tooling.chart.gc.GraphicsProvider;
import com.amitinside.tooling.chart.gc.swt.SwtGraphicsProvider;
import com.amitinside.tooling.chart.swt.ChartViewer;

public class ChartBuilder {

	private static ChartConfigurationBuilder chartConfiguration;
	private static ChartBuilder chartViewerBuilder;

	public static ChartBuilder config(final Composite parent, final Consumer<ChartConfigurationBuilder> settings) {
		chartConfiguration = new ChartConfigurationBuilder();
		settings.accept(chartConfiguration);
		chartViewerBuilder = new ChartBuilder(parent);
		return chartViewerBuilder;
	}

	private Chart chart;
	private final ChartViewer chartViewer;

	public ChartBuilder(final Composite parent) {
		SwtGraphicsProvider.setDefaultDisplay(parent.getShell().getDisplay());
		this.chartViewer = new ChartViewer(parent, SWT.NONE);
		this.prepareChartViewer(parent);
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
		this.chart = new Chart(chartConfiguration.getTitle(), chartConfiguration.getPlotter(), null, null);
		this.chart.back = new FillStyle(GraphicsProvider.getColor(ChartColor.INDIANRED));
		this.chart.back.gradientType = FillStyle.GRADIENT_HORIZONTAL;
		this.chart.legend = chartConfiguration.getLegend();
	}

	public ChartViewer viewer(final Consumer<ChartBuilder> chartBuilderConsumer) {
		chartBuilderConsumer.accept(chartViewerBuilder);
		return chartViewerBuilder.chartViewer;
	}

}
