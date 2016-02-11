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

import static com.amitinside.tooling.chart.gc.AbstractChartGraphics.ROTATE_CENTER;

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
	protected int align = ALIGN_LEFT;
	/** */
	protected int anchorX = 0;
	/** */
	protected int anchorY = 0;
	/** */
	protected FillStyle background = null;
	/** */
	protected LineStyle border = null;
	/** */
	protected int borderShape = BORDER_RECT;
	/** */
	protected SpiderChart chart = null;
	/** */
	public Polygon clickableArea = null;
	/** */
	protected boolean ignorePosition = false;
	/** */
	protected LineStyle lineToAnchor = null;
	/** */
	private final int[] lineWidths = new int[100];
	/** */
	protected int marginX = 2;
	/** */
	protected int marginY = 2;
	/** */
	protected String name = "";
	/** */
	protected int positionX = 0;
	/** */
	protected int positionY = 0;
	/** */
	public int requiredHeight = 0;
	/** */
	public int requiredWidth = 0;
	/** */
	protected int rotationAlign = ROTATE_CENTER;
	/** */
	@SuppressWarnings("unused")
	private String sFormat = "";
	/** */
	protected String tip = "";

	/** Constructor */
	public SpiderChartLabel(String pformat, final String pvalue, final boolean pvertical, final boolean pCenter) {
		if (pformat.length() == 0) {
			pformat = pvalue;
		}
		this.sFormat = pformat;
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
	public void paint(final AbstractChartGraphics g, final int x, final int y, final int width, final int height) {
		if (this.chart != null) {
			this.chart.placeFloatingObject(this);
		}
		if (this.chart != null) {
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
