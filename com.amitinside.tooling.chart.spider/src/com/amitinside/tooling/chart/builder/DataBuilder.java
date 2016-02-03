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

import com.amitinside.tooling.chart.Chart;
import com.amitinside.tooling.chart.api.ISpiderDrawable;
import com.amitinside.tooling.chart.builder.model.Data;

public class DataBuilder {

	private final Chart chart;
	private final Data data;

	public DataBuilder(final Chart chart) {
		requireNonNull(chart);

		this.data = new Data();
		this.chart = chart;
	}

	private void done() {
		this.chart.addSeq(this.data.getData());
	}

	public void inject(final ISpiderDrawable drawableData) {
		requireNonNull(drawableData);

		this.data.setData(drawableData.values(), drawableData.areaColor());
		this.done();
	}

}
