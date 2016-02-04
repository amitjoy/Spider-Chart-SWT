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
import com.amitinside.tooling.chart.gc.SpiderChartGraphics;
import com.amitinside.tooling.chart.gc.SpiderChartImage;

public class FillStyle {

	public static int GRADIENT_HORIZONTAL = 1;
	public static int GRADIENT_VERTICAL = 2;
	public static int NO_GRADIENT = 0;

	public float alphaValue = 1.0F;
	SpiderChartColor color;
	public SpiderChartColor colorFrom = SWTGraphicsSupplier.getColor(SpiderChartColor.RED);
	public SpiderChartColor colorUntil = SWTGraphicsSupplier.getColor(SpiderChartColor.WHITE);
	private Object composite = null;
	public Object fillPatern = null;
	public boolean gradientCyclic = false;

	public int gradientType = NO_GRADIENT;

	public SpiderChartImage textureImage = null;

	public FillStyle(final SpiderChartColor c) {
		this.color = c;
	}

	public FillStyle(final SpiderChartColor c, final float f) {
		this.color = c;
		this.alphaValue = f;
	}

	public FillStyle(final SpiderChartImage i) {
		this.textureImage = i;
		this.color = SWTGraphicsSupplier.getColor(SpiderChartColor.WHITE);
	}

	protected void draw(final SpiderChartGraphics g, int x1, int y1, int x2, int y2) {
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

	protected void drawArc(final SpiderChartGraphics g, final int x, final int y, final int w, final int h,
			final int a1, final int a2) {
		g.setTexture(this.textureImage);
		g.setColor(this.color);
		this.setAlpha(g);
		g.fillArc(x, y, w, h, a1, a2);
		this.resetAlpha(g);
	}

	protected void drawPolygon(final SpiderChartGraphics g, final int[] x1, final int[] y1, final int num) {
		g.setTexture(this.textureImage);
		g.setColor(this.color);
		this.setAlpha(g);
		g.fillPolygon(x1, y1, num);
		this.resetAlpha(g);
	}

	public void drawRoundRect(final SpiderChartGraphics g, final int x1, final int y1, final int x2, final int y2) {
		g.setTexture(this.textureImage);
		g.setColor(this.color);
		this.setAlpha(g);
		g.fillRoundRect(x1, y1, x2 - x1, y2 - y1);
		this.resetAlpha(g);
	}

	public SpiderChartColor getColor() {
		return this.color;
	}

	private void resetAlpha(final SpiderChartGraphics g) {
		if (this.composite != null) {
			g.setAlphaComposite(this.composite);
		}
		this.composite = null;
	}

	private void setAlpha(final SpiderChartGraphics g) {
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
