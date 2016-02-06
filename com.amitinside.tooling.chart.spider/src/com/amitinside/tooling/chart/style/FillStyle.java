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
package com.amitinside.tooling.chart.style;

import static com.amitinside.tooling.chart.gc.AbstractChartColor.AZURE;
import static com.amitinside.tooling.chart.gc.AbstractChartColor.WHITE;
import static com.amitinside.tooling.chart.gc.AbstractGraphicsSupplier.getColor;

import com.amitinside.tooling.chart.gc.AbstractChartColor;
import com.amitinside.tooling.chart.gc.AbstractChartGraphics;
import com.amitinside.tooling.chart.gc.AbstractChartImage;

public final class FillStyle {

	/** */
	public static int GRADIENT_HORIZONTAL = 1;
	/** */
	public static int GRADIENT_VERTICAL = 2;
	/** */
	public static int NO_GRADIENT = 0;

	/** */
	public float alphaValue = 1.0F;
	/** */
	AbstractChartColor color;
	/** Spider Chart Canvas Background Color */
	public AbstractChartColor colorFrom = getColor(AZURE);
	/** */
	public AbstractChartColor colorUntil = getColor(WHITE);
	/** */
	private Object composite = null;
	/** */
	public Object fillPatern = null;
	/** */
	public boolean gradientCyclic = false;

	/** */
	public int gradientType = NO_GRADIENT;

	/** */
	public AbstractChartImage textureImage = null;

	/** Constructor */
	public FillStyle(final AbstractChartColor c) {
		this.color = c;
	}

	/** Constructor */
	public FillStyle(final AbstractChartColor c, final float f) {
		this.color = c;
		this.alphaValue = f;
	}

	/** Constructor */
	public FillStyle(final AbstractChartImage i) {
		this.textureImage = i;
		this.color = getColor(WHITE);
	}

	/** */
	public void draw(final AbstractChartGraphics g, int x1, int y1, int x2, int y2) {
		if (x1 > x2) {
			final int xtmp = x2;
			x2 = x1;
			x1 = xtmp;
		}
		if (y1 > y2) {
			final int ytmp = y2;
			y2 = y1;
			y1 = ytmp;
		}
		g.setTexture(this.textureImage);
		g.setColor(this.color);
		if (this.gradientType != NO_GRADIENT) {
			g.createFadeArea(this.colorFrom, this.colorUntil, x1, y1, x2 - x1, y2 - y1,
					this.gradientType == GRADIENT_VERTICAL, this.gradientCyclic);
		} else {
			this.setAlpha(g);
			g.fillRect(x1, y1, x2 - x1, y2 - y1);
			this.resetAlpha(g);
		}
	}

	/** */
	public void draw(final AbstractChartGraphics g, final String backgroundCanvasColor, int x1, int y1, int x2,
			int y2) {
		if (x1 > x2) {
			final int xtmp = x2;
			x2 = x1;
			x1 = xtmp;
		}
		if (y1 > y2) {
			final int ytmp = y2;
			y2 = y1;
			y1 = ytmp;
		}
		g.setTexture(this.textureImage);
		g.setColor(this.color);
		if (this.gradientType != NO_GRADIENT) {
			this.colorFrom = getColor(backgroundCanvasColor);
			g.createFadeArea(this.colorFrom, this.colorUntil, x1, y1, x2 - x1, y2 - y1,
					this.gradientType == GRADIENT_VERTICAL, this.gradientCyclic);
		} else {
			this.setAlpha(g);
			g.fillRect(x1, y1, x2 - x1, y2 - y1);
			this.resetAlpha(g);
		}
	}

	/** */
	public void drawArc(final AbstractChartGraphics g, final int x, final int y, final int w, final int h, final int a1,
			final int a2) {
		g.setTexture(this.textureImage);
		g.setColor(this.color);
		this.setAlpha(g);
		g.fillArc(x, y, w, h, a1, a2);
		this.resetAlpha(g);
	}

	/** */
	public void drawPolygon(final AbstractChartGraphics g, final int[] x1, final int[] y1, final int num) {
		g.setTexture(this.textureImage);
		g.setColor(this.color);
		this.setAlpha(g);
		g.fillPolygon(x1, y1, num);
		this.resetAlpha(g);
	}

	/** */
	public void drawRoundRect(final AbstractChartGraphics g, final int x1, final int y1, final int x2, final int y2) {
		g.setTexture(this.textureImage);
		g.setColor(this.color);
		this.setAlpha(g);
		g.fillRoundRect(x1, y1, x2 - x1, y2 - y1);
		this.resetAlpha(g);
	}

	/** */
	private void resetAlpha(final AbstractChartGraphics g) {
		if (this.composite != null) {
			g.setAlphaComposite(this.composite);
		}
		this.composite = null;
	}

	/** */
	private void setAlpha(final AbstractChartGraphics g) {
		if (this.alphaValue != 1.0F) {
			this.composite = g.getAlphaComposite();
			g.setAlpha(this.alphaValue);
		}
	}

	/** {@inheritDoc}} */
	@Override
	public String toString() {
		if (this.gradientType != NO_GRADIENT) {
			return this.colorFrom.getRGBString() + ";" + this.colorUntil.getRGBString();
		}
		if (this.alphaValue != 1.0F) {
			return this.color.getRGBString() + "|" + this.alphaValue;
		}
		return this.color.getRGBString();
	}
}
