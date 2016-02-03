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
import com.amitinside.tooling.chart.gc.ChartGraphics;
import com.amitinside.tooling.chart.gc.ChartImage;
import com.amitinside.tooling.chart.gc.SWTGraphicsSupplier;

public class FillStyle {

	public static int GRADIENT_HORIZONTAL = 1;
	public static int GRADIENT_VERTICAL = 2;
	public static int NO_GRADIENT = 0;

	public static FillStyle createFromString(final String f) {

		int p = f.indexOf(";");
		String separator = ";";
		if (p == -1) {
			p = f.indexOf(":");
			separator = ":";
		}
		if (p > -1) {
			final String[] items = ChartLoader.convertList(f, separator);

			final FillStyle fillFrom = new FillStyle(SWTGraphicsSupplier.getColor(ChartColor.BLACK));

			fillFrom.colorFrom = ChartLoader.convertColor(items[0]);
			fillFrom.color = fillFrom.colorFrom;
			fillFrom.colorUntil = ChartLoader.convertColor(items[1]);
			fillFrom.gradientType = GRADIENT_VERTICAL;
			if ((items.length > 2) && (items[2].toUpperCase().compareTo("HORIZONTAL") == 0)) {
				fillFrom.gradientType = GRADIENT_HORIZONTAL;
			}
			return fillFrom;
		}
		if (f.compareTo("") == 0) {
			return null;
		}
		final String[] items = ChartLoader.convertList(f);
		if (items == null) {
			return null;
		}
		if (items.length < 1) {
			return null;
		}
		final ChartColor c = ChartLoader.convertColor(items[0]);
		try {
			if (items.length == 1) {
				return new FillStyle(c);
			}
			return new FillStyle(c, new Float(items[1]).floatValue());
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public float alphaValue = 1.0F;
	ChartColor color;
	public ChartColor colorFrom = SWTGraphicsSupplier.getColor(ChartColor.RED);
	public ChartColor colorUntil = SWTGraphicsSupplier.getColor(ChartColor.WHITE);
	private Object composite = null;
	public Object fillPatern = null;
	public boolean gradientCyclic = false;

	public int gradientType = NO_GRADIENT;

	public ChartImage textureImage = null;

	public FillStyle(final ChartColor c) {
		this.color = c;
	}

	public FillStyle(final ChartColor c, final float f) {
		this.color = c;
		this.alphaValue = f;
	}

	public FillStyle(final ChartImage i) {
		this.textureImage = i;
		this.color = SWTGraphicsSupplier.getColor(ChartColor.WHITE);
	}

	protected void draw(final ChartGraphics g, int x1, int y1, int x2, int y2) {
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

	protected void drawArc(final ChartGraphics g, final int x, final int y, final int w, final int h, final int a1,
			final int a2) {
		g.setTexture(this.textureImage);
		g.setColor(this.color);
		this.setAlpha(g);
		g.fillArc(x, y, w, h, a1, a2);
		this.resetAlpha(g);
	}

	protected void drawPolygon(final ChartGraphics g, final int[] x1, final int[] y1, final int num) {
		g.setTexture(this.textureImage);
		g.setColor(this.color);
		this.setAlpha(g);
		g.fillPolygon(x1, y1, num);
		this.resetAlpha(g);
	}

	public void drawRoundRect(final ChartGraphics g, final int x1, final int y1, final int x2, final int y2) {
		g.setTexture(this.textureImage);
		g.setColor(this.color);
		this.setAlpha(g);
		g.fillRoundRect(x1, y1, x2 - x1, y2 - y1);
		this.resetAlpha(g);
	}

	public ChartColor getColor() {
		return this.color;
	}

	private void resetAlpha(final ChartGraphics g) {
		if (this.composite != null) {
			g.setAlphaComposite(this.composite);
		}
		this.composite = null;
	}

	private void setAlpha(final ChartGraphics g) {
		if (this.alphaValue != 1.0F) {
			this.composite = g.getAlphaComposite();
			g.setAlpha(this.alphaValue);
		}
	}

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
