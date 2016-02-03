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

import com.amitinside.tooling.chart.gc.SpiderChartColor;
import com.amitinside.tooling.chart.gc.SpiderChartFont;
import com.amitinside.tooling.chart.gc.SpiderChartGraphics;

public class HAxisLabel extends AxisLabel {

	public HAxisLabel(final String t, final SpiderChartColor c, final SpiderChartFont f) {
		super(t, c, f);
	}

	@Override
	protected void draw(final SpiderChartGraphics g) {
		g.setColor(this.color);
		g.setFont(this.font);
		if (this.title.indexOf("@") >= 0) {
			final SpiderChartLabel formattedLabel = new SpiderChartLabel(this.title, "", this.vertical, true);
			formattedLabel.initialize(g, this.chart);
			formattedLabel.paint(g, this.x, this.y, this.width, this.height);
			return;
		}
		final int w = g.getFontWidth(this.title);
		int toCenterX = 0;
		if (w < this.width) {
			toCenterX = (this.width - w) / 2;
		}
		final int h = g.getFontHeight();
		int toCenterY = this.height;
		if (h < this.height) {
			toCenterY = (this.height - h) / 2;
		}
		g.drawString(this.title, this.x + toCenterX, this.y + toCenterY + h);
	}
}
