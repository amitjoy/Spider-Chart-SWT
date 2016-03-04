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
package com.amitinside.tooling.chart.title;

import static com.amitinside.tooling.chart.gc.AbstractChartColor.BLUE;
import static com.amitinside.tooling.chart.gc.AbstractChartFont.PLAIN;
import static com.amitinside.tooling.chart.gc.AbstractGraphicsSupplier.getColor;
import static com.amitinside.tooling.chart.gc.AbstractGraphicsSupplier.getFont;
import static com.amitinside.tooling.chart.gc.Fonts.VERDANA;

import com.amitinside.tooling.chart.SpiderChartComponent;
import com.amitinside.tooling.chart.gc.AbstractChartColor;
import com.amitinside.tooling.chart.gc.AbstractChartFont;
import com.amitinside.tooling.chart.gc.AbstractChartGraphics;

/**
 * Used to represent Spider Chart Title
 *
 * @author AMIT KUMAR MONDAL
 *
 */
public final class SpiderChartTitle extends SpiderChartComponent {

	/** Title Color */
	private AbstractChartColor color = getColor(BLUE);

	/** Title Font */
	private AbstractChartFont font = getFont(VERDANA, PLAIN, 14);

	/** Title Text */
	private String text;

	/** Constructor */
	public SpiderChartTitle() {
	}

	/** Used to draw the title */
	public void draw(final AbstractChartGraphics g) {
		g.setColor(this.color);
		g.setFont(this.font);
		final String[] txt = new String[3];

		txt[0] = this.text;
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
				g.drawText(txt[i], this.x + toCenterX, this.y + toCenterY + (g.getFontHeight() * (i + 1)));
			}
		}
	}

	/** Getter for color */
	public AbstractChartColor getChartColor() {
		return this.color;
	}

	/** Getter for chart font */
	public AbstractChartFont getChartFont() {
		return this.font;
	}

	/** Getter for chart text */
	public String getText() {
		return this.text;
	}

	/** Setter for color */
	public void setColor(final AbstractChartColor color) {
		this.color = color;
	}

	/** Setter for chart font */
	public void setFont(final AbstractChartFont font) {
		this.font = font;
	}

	/** Setter for chart text */
	public void setText(final String text) {
		this.text = text;
	}
}
