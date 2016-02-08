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

import static com.amitinside.tooling.chart.api.annotations.processor.SpiderChartAnnotationProcessor.getAreaColor;
import static com.amitinside.tooling.chart.api.annotations.processor.SpiderChartAnnotationProcessor.getDataPoints;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.amitinside.tooling.chart.SpiderChart;
import com.amitinside.tooling.chart.builder.model.AxisData;

public final class AxisDataBuilder {

	/** */
	private final List<AxisData> axesData = new ArrayList<>();

	/** */
	private final AxisData axisData;

	/** */
	private final SpiderChart chart;

	/** Constructor */
	public AxisDataBuilder(final SpiderChart chart) {
		requireNonNull(chart);

		this.axisData = new AxisData();
		this.chart = chart;
	}

	/** */
	private void done() {
		this.chart.addSeq(this.axisData.getData());
	}

	/** */
	public void inject(final Supplier<Object> drawableData) {
		requireNonNull(drawableData);
		final Object data = drawableData.get();
		final Optional<double[]> values = Optional.of(getDataPoints(data));
		final Optional<String> areaColor = Optional.of(getAreaColor(data));

		this.axisData.setData(values.orElseGet(() -> new double[] { 0 }), areaColor.orElse("RED"));
		this.axesData.add(this.axisData);
		this.done();
	}

}
