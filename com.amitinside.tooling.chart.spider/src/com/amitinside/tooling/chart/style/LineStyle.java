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

import static com.amitinside.tooling.chart.gc.AbstractChartGraphics.STROKE_DASHED;
import static com.amitinside.tooling.chart.gc.AbstractChartGraphics.STROKE_DOTTED;
import static com.amitinside.tooling.chart.gc.AbstractChartGraphics.STROKE_NORMAL;

import com.amitinside.tooling.chart.gc.AbstractChartColor;
import com.amitinside.tooling.chart.gc.AbstractChartGraphics;

public final class LineStyle {

	/** */
	public static final int DASHED_LINE = 2;
	/** */
	public static final int DOTS_LINE = 3;
	/** */
	public static final int NORMAL_LINE = 1;

	/** Factory method to create line */
	public static LineStyle of(final float w, final AbstractChartColor c, final int t) {
		return new LineStyle(w, c, t);
	}

	/** */
	private float alphaValue = 1.0F;
	/** color instance for the line */
	private AbstractChartColor color;

	/** type of the line */
	private int lineType;

	/** */
	private float lineWidth;

	/** Constructor */
	public LineStyle(final float w, final AbstractChartColor c, final int t) {
		this.lineType = t;
		this.lineWidth = w;
		this.color = c;
	}

	/** Constructor */
	public LineStyle(final float w, final AbstractChartColor c, final int t, final float alpha) {
		this(w, c, t);
	}

	/** Draws the line */
	public void draw(final AbstractChartGraphics g, final int x1, final int y1, final int x2, final int y2) {
		this.setGraphicsProperties(g);
		g.drawLineWithStyle(x1, y1, x2, y2);
	}

	/** Draws arc */
	public void drawArc(final AbstractChartGraphics g, final int x, final int y, final int w, final int h, final int a1,
			final int a2) {
		this.setGraphicsProperties(g);

		g.drawArc(x, y, w, h, a1, a2);
	}

	/** Draws open ended polygon */
	public void drawOpenPolygon(final AbstractChartGraphics g, final int[] x, final int[] y, final int c) {
		for (int i = 1; i < c; i++) {
			this.draw(g, x[i - 1], y[i - 1], x[i], y[i]);
		}
	}

	/** Draws polygon */
	public void drawPolygon(final AbstractChartGraphics g, final int[] x, final int[] y, final int c) {
		for (int i = 1; i < c; i++) {
			this.draw(g, x[i - 1], y[i - 1], x[i], y[i]);
		}
		this.draw(g, x[0], y[0], x[c - 1], y[c - 1]);
	}

	/** Draws Rectangle */
	public void drawRect(final AbstractChartGraphics g, final int x1, final int y1, final int x2, final int y2) {
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

	/** Draws Round Rectangle */
	public void drawRoundRect(final AbstractChartGraphics g, final int x1, final int y1, final int x2, final int y2) {
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

	/**
	 * @return the alphaValue
	 */
	public float getAlphaValue() {
		return this.alphaValue;
	}

	/** Getter for line color instance */
	public AbstractChartColor getColor() {
		return this.color;
	}

	/**
	 * @return the lineType
	 */
	public int getLineType() {
		return this.lineType;
	}

	/**
	 * @return the lWidth
	 */
	public float getlWidth() {
		return this.lineWidth;
	}

	/** Getter for the type of the line */
	public int getType() {
		return this.lineType;
	}

	/** Getter for the width of the line */
	public float getWidth() {
		return this.lineWidth;
	}

	/**
	 * @param alphaValue
	 *            the alphaValue to set
	 */
	public void setAlphaValue(final float alphaValue) {
		this.alphaValue = alphaValue;
	}

	/** Setter for the color instance */
	public void setColor(final AbstractChartColor c) {
		this.color = c;
	}

	/** Setter for the graphics properties */
	protected void setGraphicsProperties(final AbstractChartGraphics g) {
		g.setColor(this.color);
		int tmp = (int) this.lineWidth;
		if ((tmp == 0) && (this.lineWidth > 0.0F)) {
			tmp = 1;
		}
		g.setLineWidth(tmp);
		g.setLineStyle(STROKE_NORMAL);
		if (this.lineType == 2) {
			g.setLineStyle(STROKE_DASHED);
		}
		if (this.lineType == 3) {
			g.setLineStyle(STROKE_DOTTED);
		}
	}

	/**
	 * @param lineType
	 *            the lineType to set
	 */
	public void setLineType(final int lineType) {
		this.lineType = lineType;
	}

	/**
	 * @param lWidth
	 *            the lWidth to set
	 */
	public void setlWidth(final float lWidth) {
		this.lineWidth = lWidth;
	}

	/** Setter for the type of the line */
	public void setType(final int t) {
		this.lineType = t;
	}

	/** Setter for the width of the line */
	public void setWidth(final float f) {
		this.lineWidth = f;
	}
}
