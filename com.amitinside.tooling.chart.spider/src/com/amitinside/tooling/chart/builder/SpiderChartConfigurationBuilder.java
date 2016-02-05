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

import static com.amitinside.tooling.chart.style.LineStyle.NORMAL_LINE;

import java.util.function.Consumer;

import com.amitinside.tooling.chart.gc.SWTGraphicsSupplier;
import com.amitinside.tooling.chart.gc.SpiderChartColor;
import com.amitinside.tooling.chart.gc.SpiderChartFont;
import com.amitinside.tooling.chart.legend.SpiderChartLegend;
import com.amitinside.tooling.chart.plotter.spider.SpiderChartPlotter;
import com.amitinside.tooling.chart.style.FillStyle;
import com.amitinside.tooling.chart.style.LineStyle;
import com.amitinside.tooling.chart.title.SpiderChartTitle;

public final class SpiderChartConfigurationBuilder {

	/** */
	private SpiderChartLegend legend;
	/** */
	private SpiderChartPlotter plotter;
	/** */
	private SpiderChartTitle title;

	/** */
	public SpiderChartLegend getLegend() {
		return this.legend;
	}

	/** */
	public SpiderChartPlotter getPlotter() {
		return this.plotter;
	}

	/** */
	public SpiderChartTitle getTitle() {
		return this.title;
	}

	/** */
	public SpiderChartConfigurationBuilder legend(final Consumer<SpiderChartLegend> legendBuilder) {
		this.legend = new SpiderChartLegend();
		this.legend.background = new FillStyle(SWTGraphicsSupplier.getColor(SpiderChartColor.WHITE));
		this.legend.border = new LineStyle(1, SWTGraphicsSupplier.getColor(SpiderChartColor.BLACK), NORMAL_LINE);
		legendBuilder.accept(this.legend);
		return this;
	}

	/** */
	public SpiderChartConfigurationBuilder plotter(final Consumer<SpiderChartPlotter> plotter) {
		this.plotter = new SpiderChartPlotter();
		this.plotter.backStyle = new FillStyle(SWTGraphicsSupplier.getColor(SpiderChartColor.PALEGREEN));

		this.plotter.gridStyle = new LineStyle(1, SWTGraphicsSupplier.getColor(SpiderChartColor.TELA),
				LineStyle.DASHED_LINE);
		this.plotter.gridFont = SWTGraphicsSupplier.getFont("Arial", SpiderChartFont.PLAIN, 10);
		this.plotter.gridFontColor = SWTGraphicsSupplier.getColor(SpiderChartColor.BLUE);
		plotter.accept(this.plotter);
		return this;
	}

	/** */
	public SpiderChartConfigurationBuilder title(final Consumer<SpiderChartTitle> titleBuilder) {
		this.title = new SpiderChartTitle();
		titleBuilder.accept(this.title);
		return this;
	}

}
