package com.amitinside.tooling.chart.builder.model;

import com.amitinside.tooling.chart.Legend;
import com.amitinside.tooling.chart.SpiderPlotter;
import com.amitinside.tooling.chart.Title;

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

	public ChartConfigurationBuilder setLegend(final Legend legend) {
		this.legend = legend;
		return this;
	}

	public ChartConfigurationBuilder setPlotter(final SpiderPlotter plotter) {
		this.plotter = plotter;
		return this;
	}

	public ChartConfigurationBuilder setTitle(final Title title) {
		this.title = title;
		return this;
	}

}
