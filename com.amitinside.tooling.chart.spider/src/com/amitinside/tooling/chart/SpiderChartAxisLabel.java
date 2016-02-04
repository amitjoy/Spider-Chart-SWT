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

import com.amitinside.tooling.chart.gc.SWTGraphicsSupplier;
import com.amitinside.tooling.chart.gc.SpiderChartColor;
import com.amitinside.tooling.chart.gc.SpiderChartFont;
import com.amitinside.tooling.chart.gc.SpiderChartGraphics;

public class SpiderChartAxisLabel extends SpiderChartComponent {

	/** */
	public SpiderChartColor color;
	/** */
	public SpiderChartFont font = SWTGraphicsSupplier.getFont("Arial", SpiderChartFont.PLAIN, 14);
	/** */
	public String title;
	/** */
	public boolean vertical = false;

	/** */
	public SpiderChartAxisLabel(final String t, final SpiderChartColor c, final SpiderChartFont f) {
		this.color = c;
		this.title = t;
		this.font = f;
	}

	/** */
	protected void draw(final SpiderChartGraphics g) {
	}
}
