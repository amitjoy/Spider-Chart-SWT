/*******************************************************************************
 * Copyright 2016 Amit Kumar Mondal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.amitinside.tooling.chart.builder;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.amitinside.tooling.chart.Chart;
import com.amitinside.tooling.chart.FillStyle;
import com.amitinside.tooling.chart.gc.ChartColor;
import com.amitinside.tooling.chart.gc.SWTGraphicsSupplier;
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
		requireNonNull(parent);

		SwtGraphicsProvider.setDefaultDisplay(parent.getShell().getDisplay());
		this.chartViewer = new ChartViewer(parent, SWT.NONE);
		this.prepareChartViewer(parent);
	}

	public void data(final Consumer<DataBuilder> dataBuilderConsumer) {
		final DataBuilder dataBuilder = new DataBuilder(this.chart);
		dataBuilderConsumer.accept(dataBuilder);
		this.chartViewer.setChart(this.chart);
	}

	private void prepareChartViewer(final Composite parent) {
		this.chartViewer.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		this.chartViewer.setBounds(parent.getShell().getClientArea().x, parent.getShell().getClientArea().y,
				parent.getShell().getClientArea().width, parent.getShell().getClientArea().height - 10);
		this.chartViewer.changePointer = true;
		this.chartViewer.allowZoom = true;

		this.chart = new Chart(chartConfiguration.getTitle(), chartConfiguration.getPlotter());
		this.chart.back = new FillStyle(SWTGraphicsSupplier.getColor(ChartColor.YELLOW));
		this.chart.back.gradientType = FillStyle.GRADIENT_VERTICAL;
		this.chart.legend = chartConfiguration.getLegend();
	}

	public ChartViewer viewer(final Consumer<ChartBuilder> chartBuilderConsumer) {
		chartBuilderConsumer.accept(chartViewerBuilder);
		return chartViewerBuilder.chartViewer;
	}

}
