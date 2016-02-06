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
package com.amitinside.tooling.chart.axis;

import com.amitinside.tooling.chart.SpiderChartComponent;
import com.amitinside.tooling.chart.gc.AbstractGraphicsSupplier;
import com.amitinside.tooling.chart.gc.AbstractChartColor;
import com.amitinside.tooling.chart.gc.AbstractChartFont;
import com.amitinside.tooling.chart.gc.AbstractChartGraphics;

public final class SpiderChartAxisLabel extends SpiderChartComponent {

	/** */
	public final AbstractChartColor color;
	/** */
	public AbstractChartFont font = AbstractGraphicsSupplier.getFont("Verdana", AbstractChartFont.PLAIN, 14);
	/** */
	public final String title;
	/** */
	public boolean vertical = false;

	/** */
	public SpiderChartAxisLabel(final String t, final AbstractChartColor c, final AbstractChartFont f) {
		this.color = c;
		this.title = t;
		this.font = f;
	}

	/** */
	protected void draw(final AbstractChartGraphics g) {
	}
}
