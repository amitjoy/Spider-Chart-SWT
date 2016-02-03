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
import com.amitinside.tooling.chart.gc.GraphicsProvider;

public class Title extends ChartComponent {

	public ChartColor color = GraphicsProvider.getColor(ChartColor.BLACK);
	public ChartFont font = GraphicsProvider.getFont("Arial", ChartFont.PLAIN, 14);
	private String text;

	public Title() {
	}

	public void draw(final ChartGraphics g) {
		g.setColor(this.color);
		g.setFont(this.font);
		if (this.text.indexOf("@") >= 0) {
			final ChartLabel formattedLabel = new ChartLabel(this.text, "", false, true);
			formattedLabel.initialize(g, this.chart);
			formattedLabel.paint(g, this.x, this.y, this.width, this.height);
			return;
		}
		final String[] txt = new String[3];

		txt[0] = this.text;
		if (txt[0].indexOf("\\n") > -1) {
			txt[1] = txt[0].substring(txt[0].indexOf("\\n") + 2, txt[0].length());
			txt[0] = txt[0].substring(0, txt[0].indexOf("\\n"));
			if (txt[1].indexOf("\\n") > -1) {
				txt[2] = txt[1].substring(txt[1].indexOf("\\n") + 2, txt[1].length());
				txt[1] = txt[1].substring(0, txt[1].indexOf("\\n"));
			}
		}
		for (int i = 0; i < 3; i++) {
			if (txt[i] != null) {
				int toCenterX = (this.width - g.getFontWidth(null, txt[i])) / 2;
				if (toCenterX < 0) {
					toCenterX = 0;
				}
				int toCenterY = (this.height - g.getFontHeight()) / 2;
				if (toCenterY < 0) {
					toCenterY = 0;
				}
				g.drawString(txt[i], this.x + toCenterX, this.y + toCenterY + (g.getFontHeight() * (i + 1)));
			}
		}
	}

	public void setText(final String text) {
		this.text = text;
	}
}
