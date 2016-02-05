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

import com.amitinside.tooling.chart.FillStyle;
import com.amitinside.tooling.chart.SpiderChart;
import com.amitinside.tooling.chart.gc.SWTGraphicsSupplier;
import com.amitinside.tooling.chart.gc.SpiderChartColor;
import com.amitinside.tooling.chart.gc.swt.SwtGraphicsProvider;
import com.amitinside.tooling.chart.swt.SpiderChartViewer;

public class SpiderChartBuilder {

	/** */
	private static SpiderChartConfigurationBuilder chartConfiguration;
	/** */
	private static SpiderChartBuilder chartViewerBuilder;

	/** */
	public static SpiderChartBuilder config(final Composite parent,
			final Consumer<SpiderChartConfigurationBuilder> settings) {
		requireNonNull(parent);

		chartConfiguration = new SpiderChartConfigurationBuilder();
		settings.accept(chartConfiguration);
		chartViewerBuilder = new SpiderChartBuilder(parent);
		return chartViewerBuilder;
	}

	/** */
	private SpiderChart chart;
	/** */
	private final SpiderChartViewer chartViewer;

	/** Constructor */
	public SpiderChartBuilder(final Composite parent) {
		requireNonNull(parent);

		SwtGraphicsProvider.setDefaultDisplay(parent.getShell().getDisplay());
		this.chartViewer = new SpiderChartViewer(parent, SWT.NONE);
		this.prepareChartViewer(parent);
	}

	/** */
	public SpiderChartBuilder data(final Consumer<AxisDataBuilder> dataBuilderConsumer) {
		final AxisDataBuilder dataBuilder = new AxisDataBuilder(this.chart);
		dataBuilderConsumer.accept(dataBuilder);
		this.chartViewer.setChart(this.chart);
		return this;
	}

	/** */
	private void prepareChartViewer(final Composite parent) {
		this.chartViewer.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		this.chartViewer.setBounds(parent.getShell().getClientArea().x, parent.getShell().getClientArea().y,
				parent.getShell().getClientArea().width, parent.getShell().getClientArea().height - 10);
		this.chartViewer.changePointer = true;
		this.chartViewer.allowZoom = true;

		// Some chart related default configurations
		this.chart = new SpiderChart(chartConfiguration.getTitle(), chartConfiguration.getPlotter());
		this.chart.back = new FillStyle(SWTGraphicsSupplier.getColor(SpiderChartColor.YELLOW));
		this.chart.backgroundCanvasColor = SpiderChartColor.ANTIQUEWHITE;
		this.chart.back.gradientType = FillStyle.GRADIENT_VERTICAL;
		this.chart.legend = chartConfiguration.getLegend();
		this.chart.repaintAll = true;
		this.chart.showTips = true;
		this.chart.activateSelection = true;
	}

	/** */
	public SpiderChartViewer viewer(final Consumer<SpiderChartBuilder> chartBuilderConsumer) {
		chartBuilderConsumer.accept(chartViewerBuilder);
		return chartViewerBuilder.chartViewer;
	}

}
