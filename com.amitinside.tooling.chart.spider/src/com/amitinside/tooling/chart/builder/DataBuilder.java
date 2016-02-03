package com.amitinside.tooling.chart.builder;

import com.amitinside.tooling.chart.Chart;
import com.amitinside.tooling.chart.builder.model.Data;

public class DataBuilder {

	private final Chart chart;
	private final Data data;

	public DataBuilder(final Chart chart) {
		this.data = new Data();
		this.chart = chart;
	}

	public void done() {
		this.chart.addSeq(this.data.getData());
	}

	public void setValues(final double[] data, final String color) {
		this.data.setData(data, color);
	}

}
