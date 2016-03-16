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

import static com.amitinside.tooling.chart.gc.AbstractChartFont.BOLD;
import static com.amitinside.tooling.chart.gc.AbstractGraphicsSupplier.getColor;
import static com.amitinside.tooling.chart.gc.AbstractGraphicsSupplier.getFont;
import static com.amitinside.tooling.chart.gc.Fonts.VERDANA;
import static com.amitinside.tooling.chart.style.LineStyle.NORMAL_LINE;

import com.amitinside.tooling.chart.sequence.LineDataSeq;
import com.amitinside.tooling.chart.style.FillStyle;
import com.amitinside.tooling.chart.style.LineStyle;

/**
 * Axis Pojo
 *
 * @author AMIT KUMAR MONDAL
 *
 */
public final class AxisData {

	/** Line Sequence to be used for the axis */
	private LineDataSeq data;

	/** getter for the axis sequence */
	public LineDataSeq getData() {
		return this.data;
	}

	/** setter for axis sequence values and color */
	public void setData(final double[] dataValues, final String color) {
		this.data = new LineDataSeq(dataValues, new LineStyle(2, getColor(color), NORMAL_LINE));
		this.data.setDrawPoint(true);
		this.data.setValueFont(getFont(VERDANA, BOLD, 12));
		this.data.setFillStyle(new FillStyle(getColor(color), 0.5f));
	}

}
