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
package com.amitinside.tooling.chart.builder.model;

import static com.amitinside.tooling.chart.LineStyle.NORMAL_LINE;

import com.amitinside.tooling.chart.FillStyle;
import com.amitinside.tooling.chart.LineDataSeq;
import com.amitinside.tooling.chart.LineStyle;
import com.amitinside.tooling.chart.gc.SWTGraphicsSupplier;
import com.amitinside.tooling.chart.gc.SpiderChartFont;

public class AxisData {

	private LineDataSeq data;

	public LineDataSeq getData() {
		return this.data;
	}

	public void setData(final double[] dataValues, final String color) {
		this.data = new LineDataSeq(dataValues, new LineStyle(2, SWTGraphicsSupplier.getColor(color), NORMAL_LINE));
		this.data.drawPoint = true;
		this.data.valueFont = SWTGraphicsSupplier.getFont("Arial", SpiderChartFont.PLAIN, 10);
		this.data.fillStyle = new FillStyle(SWTGraphicsSupplier.getColor(color), 0.5f);
	}

}
