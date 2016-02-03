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

import java.util.function.Consumer;

import com.amitinside.tooling.chart.FillStyle;
import com.amitinside.tooling.chart.Legend;
import com.amitinside.tooling.chart.LineStyle;
import com.amitinside.tooling.chart.SpiderPlotter;
import com.amitinside.tooling.chart.Title;
import com.amitinside.tooling.chart.gc.ChartColor;
import com.amitinside.tooling.chart.gc.ChartFont;
import com.amitinside.tooling.chart.gc.SWTGraphicsSupplier;

public class ChartConfigurationBuilder {

	private Legend legend;
	private SpiderPlotter plotter;
	private Title title;

	public Legend getLegend() {
		return this.legend;
	}

	public SpiderPlotter getPlotter() {
		return this.plotter;
	}

	public Title getTitle() {
		return this.title;
	}

	public void legend(final Consumer<Legend> legendBuilder) {
		this.legend = new Legend();
		this.legend.background = new FillStyle(SWTGraphicsSupplier.getColor(ChartColor.WHITE));
		this.legend.border = new LineStyle(1, SWTGraphicsSupplier.getColor(ChartColor.BLACK), LineStyle.LINE_NORMAL);
		legendBuilder.accept(this.legend);
	}

	public void plotter(final Consumer<SpiderPlotter> plotter) {
		this.plotter = new SpiderPlotter();
		this.plotter.backStyle = new FillStyle(SWTGraphicsSupplier.getColor(ChartColor.YELLOW));
		this.plotter.radiusModifier = 0.8;

		this.plotter.gridStyle = new LineStyle(1, SWTGraphicsSupplier.getColor(ChartColor.BLACK),
				LineStyle.LINE_DASHED);
		this.plotter.gridFont = SWTGraphicsSupplier.getFont("Arial", ChartFont.PLAIN, 10);
		plotter.accept(this.plotter);
	}

	public void title(final Consumer<Title> titleBuilder) {
		this.title = new Title();
		titleBuilder.accept(this.title);
	}

}
