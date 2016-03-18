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

import static com.amitinside.tooling.chart.gc.AbstractChartColor.ANTIQUEWHITE;
import static com.amitinside.tooling.chart.gc.AbstractChartColor.YELLOW;
import static com.amitinside.tooling.chart.gc.AbstractGraphicsSupplier.getColor;
import static com.amitinside.tooling.chart.style.FillStyle.GRADIENT_VERTICAL;
import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.amitinside.tooling.chart.SpiderChart;
import com.amitinside.tooling.chart.style.FillStyle;
import com.amitinside.tooling.chart.swt.SpiderChartViewer;

/**
 * Used to build the complete spider chart
 *
 * @author AMIT KUMAR MONDAL
 *
 */
public final class SpiderChartBuilder {

	/** Spider Chart Configuration Builder Ref */
	private static SpiderChartConfigurationBuilder chartConfiguration;

	/** Chart Viewer Builder */
	private static SpiderChartBuilder chartViewerBuilder;

	/** Configuration for the Spider Chart */
	public static SpiderChartBuilder config(final Composite parent,
			final Consumer<SpiderChartConfigurationBuilder> settings) {
		requireNonNull(parent);
		requireNonNull(settings);
		chartConfiguration = new SpiderChartConfigurationBuilder();
		settings.accept(chartConfiguration);
		chartViewerBuilder = new SpiderChartBuilder(parent);
		return chartViewerBuilder;
	}

	/** Actual Spider Chart to be used */
	private SpiderChart chart;

	/** Actual Spider Chart Viewer */
	private final SpiderChartViewer chartViewer;

	/** Constructor */
	public SpiderChartBuilder(final Composite parent) {
		requireNonNull(parent);
		this.chartViewer = new SpiderChartViewer(parent, SWT.NONE);
		this.prepareChartViewer(parent);
	}

	/** The datapoints provider */
	public SpiderChartBuilder data(final Consumer<AxisDataBuilder> dataBuilderConsumer) {
		requireNonNull(dataBuilderConsumer);
		final AxisDataBuilder dataBuilder = new AxisDataBuilder(this.chart);
		dataBuilderConsumer.accept(dataBuilder);
		this.chartViewer.setChart(this.chart);
		return this;
	}

	/** Configuration for the spider chart */
	private void prepareChartViewer(final Composite parent) {
		this.chartViewer.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		this.chartViewer.setBounds(parent.getShell().getClientArea().x, parent.getShell().getClientArea().y,
				parent.getShell().getClientArea().width, parent.getShell().getClientArea().height - 10);
		this.chartViewer.setChangePointer(true);
		this.chartViewer.setAllowZoom(true);

		// Some chart related default configurations
		this.chart = new SpiderChart(chartConfiguration.getTitle(), chartConfiguration.getPlotter());
		this.chart.setBackStyle(new FillStyle(getColor(YELLOW)));
		this.chart.setBackgroundCanvasColor(ANTIQUEWHITE);
		this.chart.getBackStyle().setGradientType(GRADIENT_VERTICAL);
		this.chart.setLegend(chartConfiguration.getLegend());
		this.chart.setRepaintAll(true);
		this.chart.setActivateSelection(true);
	}

	/**
	 * Builds the viewer if values are already provided to the plotter using
	 * line data sequence
	 */
	public SpiderChartViewer viewer() {
		return chartViewerBuilder.chartViewer;
	}

	/** Builds the viewer */
	public SpiderChartViewer viewer(final Consumer<SpiderChartBuilder> chartBuilderConsumer) {
		requireNonNull(chartBuilderConsumer);
		chartBuilderConsumer.accept(chartViewerBuilder);
		return chartViewerBuilder.chartViewer;
	}

}
