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

import java.util.ArrayList;
import java.util.List;

public final class AxesConfigurer {

	public static class Builder {

		/** */
		private final List<Double> maxScales;
		/** */
		private final List<Double> minScales;
		/** */
		private final List<String> scalesNames;

		/** Constructor */
		public Builder() {
			this.maxScales = new ArrayList<>();
			this.minScales = new ArrayList<>();
			this.scalesNames = new ArrayList<>();
		}

		/** */
		public Builder addAxis(final String name, final double maxScale, final double minScale) {
			this.maxScales.add(maxScale);
			this.minScales.add(minScale);
			this.scalesNames.add(name);
			return this;
		}

		/** */
		public AxesConfigurer build() {
			return new AxesConfigurer(this.maxScales, this.minScales, this.scalesNames);
		}

	}

	/** */
	private static double[] toDoublePrimitiveArray(final Double[] wrappedArray) {
		final double[] array = new double[wrappedArray.length];
		for (int i = 0; i < wrappedArray.length; i++) {
			array[i] = wrappedArray[i].intValue();
		}
		return array;
	}

	/** */
	private final List<Double> maxScales;
	/** */
	private final List<Double> minScales;

	/** */
	private final List<String> scalesNames;

	/** Constructor */
	private AxesConfigurer(final List<Double> maxScales, final List<Double> minScales, final List<String> scalesNames) {
		super();
		this.maxScales = maxScales;
		this.minScales = minScales;
		this.scalesNames = scalesNames;
	}

	/** */
	public String[] axesNames() {
		final String[] axesNames = new String[this.scalesNames.size()];
		return this.scalesNames.toArray(axesNames);
	}

	/** */
	public double[] maxScales() {
		final Double[] maxScales = new Double[this.maxScales.size()];
		return toDoublePrimitiveArray(this.maxScales.toArray(maxScales));
	}

	/** */
	public double[] minScales() {
		final Double[] minScales = new Double[this.minScales.size()];
		return toDoublePrimitiveArray(this.minScales.toArray(minScales));
	}

}
