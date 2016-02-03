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
import com.amitinside.tooling.chart.gc.SpiderChartGraphics;

public class LineStyle {

	public static final int LINE_DASHED = 2;
	public static final int LINE_DOTS = 3;
	public static final int LINE_NORMAL = 1;

	public static LineStyle createFromString(final String s) {
		final String[] items = SpiderChartLoader.convertList(s);
		if (items == null) {
			return null;
		}
		if (items.length < 3) {
			return null;
		}
		float floa = 0.0F;
		try {
			floa = new Float(items[0]).floatValue();
		} catch (final Exception e) {
			floa = 0.2F;
		}
		int typ = 1;
		if (items[2].compareTo("DASHED") == 0) {
			typ = 2;
		}
		if (items[2].compareTo("DOTS") == 0) {
			typ = 3;
		}
		final SpiderChartColor c = SpiderChartLoader.convertColor(items[1]);
		try {
			if (items.length <= 3) {
				return new LineStyle(floa, c, typ);
			}
			return new LineStyle(floa, c, typ, new Float(items[3]).floatValue());
		} catch (final Exception e) {
		}
		return null;
	}

	public float alphaValue = 1.0F;
	SpiderChartColor color;
	int lineType;

	float lWidth;

	public LineStyle(final float w, final SpiderChartColor c, final int t) {
		this.lineType = t;
		this.lWidth = w;
		this.color = c;
	}

	public LineStyle(final float w, final SpiderChartColor c, final int t, final float alpha) {
		this(w, c, t);
	}

	public void draw(final SpiderChartGraphics g, final int x1, final int y1, final int x2, final int y2) {
		this.setGraphicsProperties(g);
		g.drawLineWithStyle(x1, y1, x2, y2);
	}

	public void drawArc(final SpiderChartGraphics g, final int x, final int y, final int w, final int h, final int a1,
			final int a2) {
		this.setGraphicsProperties(g);

		g.drawArc(x, y, w, h, a1, a2);
	}

	public void drawOpenPolygon(final SpiderChartGraphics g, final int[] x, final int[] y, final int c) {
		for (int i = 1; i < c; i++) {
			this.draw(g, x[i - 1], y[i - 1], x[i], y[i]);
		}
	}

	public void drawPolygon(final SpiderChartGraphics g, final int[] x, final int[] y, final int c) {
		for (int i = 1; i < c; i++) {
			this.draw(g, x[i - 1], y[i - 1], x[i], y[i]);
		}
		this.draw(g, x[0], y[0], x[c - 1], y[c - 1]);
	}

	public void drawRect(final SpiderChartGraphics g, final int x1, final int y1, final int x2, final int y2) {
		int iX = x1;
		int iY = y1;
		int w = x2 - x1;
		int h = y2 - y1;
		if (w < 0) {
			w *= -1;
			iX = x2;
		}
		if (h < 0) {
			h *= -1;
			iY = y2;
		}
		this.setGraphicsProperties(g);
		g.drawRect(iX, iY, w, h);
	}

	public void drawRoundRect(final SpiderChartGraphics g, final int x1, final int y1, final int x2, final int y2) {
		int iX = x1;
		int iY = y1;
		int w = x2 - x1;
		int h = y2 - y1;
		if (w < 0) {
			w *= -1;
			iX = x2;
		}
		if (h < 0) {
			h *= -1;
			iY = y2;
		}
		this.setGraphicsProperties(g);
		g.drawRoundedRect(iX, iY, w, h);
	}

	public SpiderChartColor getColor() {
		return this.color;
	}

	public int getType() {
		return this.lineType;
	}

	public float getWidth() {
		return this.lWidth;
	}

	public void setColor(final SpiderChartColor c) {
		this.color = c;
	}

	protected void setGraphicsProperties(final SpiderChartGraphics g) {
		g.setColor(this.color);
		int tmp = (int) this.lWidth;
		if ((tmp == 0) && (this.lWidth > 0.0F)) {
			tmp = 1;
		}
		g.setLineWidth(tmp);
		g.setLineStyle(SpiderChartGraphics.STROKE_NORMAL);
		if (this.lineType == 2) {
			g.setLineStyle(SpiderChartGraphics.STROKE_DASHED);
		}
		if (this.lineType == 3) {
			g.setLineStyle(SpiderChartGraphics.STROKE_DOTTED);
		}
	}

	public void setType(final int t) {
		this.lineType = t;
	}

	public void setWidth(final float f) {
		this.lWidth = f;
	}
}
