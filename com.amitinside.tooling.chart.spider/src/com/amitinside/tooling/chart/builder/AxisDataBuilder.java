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

import java.util.Optional;
import java.util.function.Supplier;

import com.amitinside.tooling.chart.SpiderChart;
import com.amitinside.tooling.chart.api.ISpiderChartDrawable;
import com.amitinside.tooling.chart.builder.model.AxisData;

public class AxisDataBuilder {

	/** */
	private final SpiderChart chart;
	/** */
	private final AxisData data;

	/** Constructor */
	public AxisDataBuilder(final SpiderChart chart) {
		requireNonNull(chart);

		this.data = new AxisData();
		this.chart = chart;
	}

	/** */
	private void done() {
		this.chart.addSeq(this.data.getData());
	}

	/** */
	public void inject(final Supplier<ISpiderChartDrawable> drawableData) {
		requireNonNull(drawableData);
		final Optional<double[]> values = Optional.of(drawableData.get().values());
		final Optional<String> areaColor = Optional.of(drawableData.get().areaColor());

		this.data.setData(values.orElseGet(() -> new double[] { 0 }), areaColor.orElse("RED"));
		this.done();
	}

}
