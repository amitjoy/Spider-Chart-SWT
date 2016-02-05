package com.amitinside.tooling.chart.builder;

import java.util.ArrayList;
import java.util.List;

public class AxesConfigurer {

	public static class Builder {

		private final List<Double> maxScales;
		private final List<Double> minScales;
		private final List<String> scalesNames;

		public Builder() {
			this.maxScales = new ArrayList<>();
			this.minScales = new ArrayList<>();
			this.scalesNames = new ArrayList<>();
		}

		public Builder addAxis(final String name, final double maxScale, final double minScale) {
			this.maxScales.add(maxScale);
			this.minScales.add(minScale);
			this.scalesNames.add(name);
			return this;
		}

		public AxesConfigurer build() {
			return new AxesConfigurer(this.maxScales, this.minScales, this.scalesNames);
		}

	}

	private final List<Double> maxScales;
	private final List<Double> minScales;
	private final List<String> scalesNames;

	public AxesConfigurer(final List<Double> maxScales, final List<Double> minScales, final List<String> scalesNames) {
		super();
		this.maxScales = maxScales;
		this.minScales = minScales;
		this.scalesNames = scalesNames;
	}

	public String[] axesNames() {
		final String[] axesNames = new String[this.scalesNames.size()];
		return this.scalesNames.toArray(axesNames);
	}

	public double[] maxScales() {
		final Double[] maxScales = new Double[this.maxScales.size()];
		return this.toDoublePrimitiveArray(this.maxScales.toArray(maxScales));
	}

	public double[] minScales() {
		final Double[] minScales = new Double[this.minScales.size()];
		return this.toDoublePrimitiveArray(this.minScales.toArray(minScales));
	}

	private double[] toDoublePrimitiveArray(final Double[] wrappedArray) {
		final double[] array = new double[wrappedArray.length];
		for (int i = 0; i < wrappedArray.length; i++) {
			array[i] = wrappedArray[i].intValue();
		}
		return array;
	}

}
