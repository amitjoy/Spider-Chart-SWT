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
package com.amitinside.tooling.chart.label;

import com.amitinside.tooling.chart.SpiderChart;
import com.amitinside.tooling.chart.gc.AbstractChartColor;
import com.amitinside.tooling.chart.gc.AbstractChartGraphics;
import com.amitinside.tooling.chart.gc.Polygon;
import com.amitinside.tooling.chart.label.api.IFloatingObject;
import com.amitinside.tooling.chart.style.FillStyle;
import com.amitinside.tooling.chart.style.LineStyle;

public final class SpiderChartLabel implements IFloatingObject {

	/** */
	protected static int ALIGN_CENTER = 1;
	/** */
	protected static int ALIGN_LEFT = 0;
	/** */
	protected static int ALIGN_RIGHT = 2;
	/** */
	protected static int BORDER_OVAL = 2;
	/** */
	protected static int BORDER_RECT = 0;
	/** */
	protected static int BORDER_ROUNDRECT = 1;
	/** */
	private int align = ALIGN_LEFT;
	/** */
	private int anchorX = 0;

	/** */
	private int anchorY = 0;

	/** */
	private FillStyle background = null;

	/** */
	private LineStyle border = null;

	/** */
	private int borderShape = BORDER_RECT;

	/** */
	private SpiderChart chart = null;

	/** */
	private Polygon clickableArea = null;

	/** */
	private boolean ignorePosition = false;

	/** */
	private LineStyle lineToAnchor = null;

	/** */
	private final int[] lineWidths = new int[100];

	/** */
	private int marginX = 2;

	/** */
	private int marginY = 2;

	/** */
	private String name = "";

	/** */
	protected int positionX = 0;

	/** */
	protected int positionY = 0;

	/** */
	private int requiredHeight = 0;

	/** */
	private int requiredWidth = 0;

	/** */
	private String sFormat = "";

	/** */
	private String tip = "";

	/** Constructor */
	public SpiderChartLabel(String pformat, final String pvalue, final boolean pvertical, final boolean pCenter) {
		if (pformat.length() == 0) {
			pformat = pvalue;
		}
		this.sFormat = pformat;
	}

	/** */
	public int getAlign() {
		return this.align;
	}

	/** */
	public int getAnchorX() {
		return this.anchorX;
	}

	/** */
	public int getAnchorY() {
		return this.anchorY;
	}

	/** */
	public FillStyle getBackground() {
		return this.background;
	}

	/** */
	public LineStyle getBorder() {
		return this.border;
	}

	/** */
	public int getBorderShape() {
		return this.borderShape;
	}

	/** */
	public SpiderChart getChart() {
		return this.chart;
	}

	/** */
	public Polygon getClickableArea() {
		return this.clickableArea;
	}

	/** */
	public LineStyle getLineToAnchor() {
		return this.lineToAnchor;
	}

	/** */
	public int[] getLineWidths() {
		return this.lineWidths;
	}

	/** */
	public int getMarginX() {
		return this.marginX;
	}

	/** */
	public int getMarginY() {
		return this.marginY;
	}

	/** */
	public String getName() {
		return this.name;
	}

	/** {@inheritDoc}} */
	@Override
	public Polygon getObjectBounds() {
		final Polygon pol = new Polygon();
		pol.addPoint(this.positionX, this.positionY);
		pol.addPoint(this.positionX + this.requiredWidth, this.positionY);
		pol.addPoint(this.positionX + this.requiredWidth, this.positionY + this.requiredHeight);
		pol.addPoint(this.positionX, this.positionY + this.requiredHeight);
		return pol;
	}

	/** */
	public int getPositionX() {
		return this.positionX;
	}

	/** */
	public int getPositionY() {
		return this.positionY;
	}

	/** */
	public int getRequiredHeight() {
		return this.requiredHeight;
	}

	/** */
	public int getRequiredWidth() {
		return this.requiredWidth;
	}

	/** */
	public String getsFormat() {
		return this.sFormat;
	}

	/** */
	public String getTip() {
		return this.tip;
	}

	/** {@inheritDoc}} */
	@Override
	public int getX() {
		return this.positionX;
	}

	/** {@inheritDoc}} */
	@Override
	public int getY() {
		return this.positionY;
	}

	/** */
	public void initialize(final AbstractChartGraphics g, final SpiderChart c) {
		this.chart = c;
	}

	/** */
	public boolean isIgnorePosition() {
		return this.ignorePosition;
	}

	/** */
	public void paint(final AbstractChartGraphics g, final int x, final int y, final int width, final int height) {
		if (this.chart != null) {
			this.chart.placeFloatingObject(this);
			this.chart.addFloationgObject(this);
		}
		this.render(g);
	}

	/** */
	protected void render(final AbstractChartGraphics graphics) {
		final AbstractChartGraphics g = graphics;
		if ((this.lineToAnchor != null)) {
			if (this.anchorY > this.positionY) {
				if (this.anchorX <= this.positionX) {
					this.lineToAnchor.draw(graphics, this.anchorX, this.anchorY, this.positionX,
							(this.positionY + this.requiredHeight) - 1);
				} else {
					this.lineToAnchor.draw(graphics, this.anchorX, this.anchorY, this.positionX + this.requiredWidth,
							(this.positionY + this.requiredHeight) - 1);
				}
			} else if (this.anchorX <= this.positionX) {
				this.lineToAnchor.draw(graphics, this.anchorX, this.anchorY, this.positionX, this.positionY);
			} else {
				this.lineToAnchor.draw(graphics, this.anchorX, this.anchorY, this.positionX + this.requiredWidth,
						this.positionY);
			}
		}
		int x = this.positionX;
		int lineStart = 0;
		if (this.background != null) {
			final AbstractChartColor c = g.getColor();
			if (this.borderShape == BORDER_RECT) {
				this.background.draw(g, this.positionX, this.positionY, (this.positionX + this.requiredWidth) - 1,
						(this.positionY + this.requiredHeight) - 1);
			}
			if (this.borderShape == BORDER_ROUNDRECT) {
				this.background.drawRoundRect(g, this.positionX, this.positionY,
						(this.positionX + this.requiredWidth) - 1, (this.positionY + this.requiredHeight) - 1);
			}
			if (this.borderShape == BORDER_OVAL) {
				this.background.drawArc(g, (int) (this.positionX - (this.requiredWidth * 0.1D)),
						(int) (this.positionY - (this.requiredHeight * 0.1D)),
						(int) ((this.requiredWidth + (this.requiredWidth * 0.2D)) - 1.0D),
						(int) ((this.requiredHeight + (this.requiredHeight * 0.3D)) - 1.0D), 0, 360);
			}
			g.setColor(c);
		}
		if (this.align == ALIGN_CENTER) {
			x += (this.requiredWidth - (this.marginX * 2) - this.lineWidths[0]) / 2;
		}
		if (this.align == ALIGN_RIGHT) {
			x += this.requiredWidth - (this.marginX * 2) - this.lineWidths[0];
		}
		lineStart = x + this.marginX;

		x = lineStart;

		if (this.border != null) {
			if (this.borderShape == BORDER_RECT) {
				this.border.drawRect(g, this.positionX, this.positionY, (this.positionX + this.requiredWidth) - 1,
						(this.positionY + this.requiredHeight) - 1);
			}
			if (this.borderShape == BORDER_ROUNDRECT) {
				this.border.drawRoundRect(g, this.positionX, this.positionY, (this.positionX + this.requiredWidth) - 1,
						(this.positionY + this.requiredHeight) - 1);
			}
			if (this.borderShape == BORDER_OVAL) {
				this.border.drawArc(g, (int) (this.positionX - (this.requiredWidth * 0.1D)),
						(int) (this.positionY - (this.requiredHeight * 0.1D)),
						(int) ((this.requiredWidth + (this.requiredWidth * 0.2D)) - 1.0D),
						(int) ((this.requiredHeight + (this.requiredHeight * 0.3D)) - 1.0D), 0, 360);
			}
		}
		this.clickableArea = new Polygon();
		this.clickableArea.addPoint(this.positionX, this.positionY);
		this.clickableArea.addPoint(this.positionX, (this.positionY + this.requiredHeight) - 1);
		this.clickableArea.addPoint((this.positionX + this.requiredWidth) - 1,
				(this.positionY + this.requiredHeight) - 1);
		this.clickableArea.addPoint((this.positionX + this.requiredWidth) - 1, this.positionY);
	}

	/** */
	public void setAlign(final int align) {
		this.align = align;
	}

	/** */
	public void setAnchorX(final int anchorX) {
		this.anchorX = anchorX;
	}

	/** */
	public void setAnchorY(final int anchorY) {
		this.anchorY = anchorY;
	}

	/** */
	public void setBackground(final FillStyle background) {
		this.background = background;
	}

	/** */
	public void setBorder(final LineStyle border) {
		this.border = border;
	}

	/** */
	public void setBorderShape(final int borderShape) {
		this.borderShape = borderShape;
	}

	/** */
	public void setChart(final SpiderChart chart) {
		this.chart = chart;
	}

	/** */
	public void setClickableArea(final Polygon clickableArea) {
		this.clickableArea = clickableArea;
	}

	/** */
	public void setIgnorePosition(final boolean ignorePosition) {
		this.ignorePosition = ignorePosition;
	}

	/** */
	public void setLineToAnchor(final LineStyle lineToAnchor) {
		this.lineToAnchor = lineToAnchor;
	}

	/** */
	public void setMarginX(final int marginX) {
		this.marginX = marginX;
	}

	/** */
	public void setMarginY(final int marginY) {
		this.marginY = marginY;
	}

	/** */
	public void setName(final String name) {
		this.name = name;
	}

	/** */
	public void setPositionX(final int positionX) {
		this.positionX = positionX;
	}

	/** */
	public void setPositionY(final int positionY) {
		this.positionY = positionY;
	}

	/** */
	public void setRequiredHeight(final int requiredHeight) {
		this.requiredHeight = requiredHeight;
	}

	/** */
	public void setRequiredWidth(final int requiredWidth) {
		this.requiredWidth = requiredWidth;
	}

	/** */
	public void setsFormat(final String sFormat) {
		this.sFormat = sFormat;
	}

	/** */
	public void setTip(final String tip) {
		this.tip = tip;
	}

	/** {@inheritDoc}} */
	@Override
	public void setX(final int x) {
		this.positionX = x;
	}

	/** {@inheritDoc}} */
	@Override
	public void setY(final int y) {
		this.positionY = y;
	}
}
