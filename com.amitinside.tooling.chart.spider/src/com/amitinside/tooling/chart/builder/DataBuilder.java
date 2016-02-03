package com.amitinside.tooling.chart.builder;

import com.amitinside.tooling.chart.builder.model.Data;

public class DataBuilder {

	private final Data data;

	public DataBuilder() {
		this.data = new Data();
	}

	public void setValues(final double[] data, final String color) {
		this.data.setData(data, color);
	}

}
