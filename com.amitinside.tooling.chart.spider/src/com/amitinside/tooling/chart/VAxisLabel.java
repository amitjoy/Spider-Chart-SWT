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
import com.amitinside.tooling.chart.gc.ChartGraphics;

public class VAxisLabel extends AxisLabel {

	public VAxisLabel(final String t, final ChartColor c, final ChartFont f) {
		super(t, c, f);
	}

	@Override
	public void draw(final ChartGraphics g) {
		g.setFont(this.font);
		g.setColor(this.color);
		if (this.title.indexOf("@") >= 0) {
			final ChartLabel formattedLabel = new ChartLabel(this.title, "", this.vertical, true);
			formattedLabel.initialize(g, this.chart);
			formattedLabel.paint(g, this.x + (this.width / 3), this.y + (this.height / 3), 10, 10);
			return;
		}
		if (this.vertical && g.drawRotatedText(this.font, this.color, this.title, 90,
				this.x + ((this.width - g.getFontHeight()) / 2),
				this.y + ((this.height - g.getFontWidth(this.title)) / 2), true)) {
			return;
		}
		final int w = g.getFontWidth(null, "X");
		int toCenterX = 0;
		if (w < this.width) {
			toCenterX = (this.width - w) / 2;
		}
		int charHeight = g.getFontHeight(null);
		charHeight = (int) (charHeight + (charHeight * 0.2D));
		final int h = charHeight * this.title.length();
		int toCenterY = 0;
		if (h < this.height) {
			toCenterY = (this.height - h) / 2;
		}
		int offset = toCenterY + charHeight;
		for (int i = 0; i < this.title.length(); i++) {
			g.drawString(this.title.substring(i, i + 1), this.x + toCenterX, this.y + offset);

			offset += charHeight;
		}
	}
}
