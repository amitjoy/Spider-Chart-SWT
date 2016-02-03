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
package com.amitinside.tooling.chart;

import com.amitinside.tooling.chart.gc.ChartColor;
import com.amitinside.tooling.chart.gc.ChartFont;
import com.amitinside.tooling.chart.gc.ChartImage;
import com.amitinside.tooling.chart.gc.SWTGraphicsSupplier;

public class LineDataSeq extends DataSeq {

	public static int startingXValue = 0;
	public boolean drawPoint = false;
	public FillStyle fillStyle = null;
	public ChartImage icon = null;
	public int lineType = 0;
	public ChartColor pointColor = SWTGraphicsSupplier.getColor(ChartColor.BLACK);
	public LineStyle style = null;
	public ChartColor valueColor = SWTGraphicsSupplier.getColor(ChartColor.BLACK);
	public ChartFont valueFont = null;
	public LineStyle vstyle = null;

	public LineDataSeq(final double[] x, final double[] y, final LineStyle s) {
		super(x, y);
		this.style = s;
	}

	public LineDataSeq(final double[] y, final LineStyle s) {
		super(y, startingXValue);
		this.style = s;
	}

	public LineDataSeq(final Double[] x, final Double[] y, final LineStyle s) {
		super(x, y);
		this.style = s;
	}

	public LineDataSeq(final Double[] y, final LineStyle s) {
		super(y, startingXValue);
		this.style = s;
	}

	public LineDataSeq(final LineStyle s) {
		this.style = s;
	}
}
