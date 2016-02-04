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

import com.amitinside.tooling.chart.gc.Polygon;
import com.amitinside.tooling.chart.gc.SWTGraphicsSupplier;
import com.amitinside.tooling.chart.gc.SpiderChartColor;
import com.amitinside.tooling.chart.gc.SpiderChartGraphics;
import com.amitinside.tooling.chart.gc.SpiderChartImage;

public class SpiderChartLabel implements IFloatingObject {

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
	protected Polygon clickableArea = null;
	/** */
	protected String clickInfo = "";
	/** */
	protected boolean ignorePosition = false;
	/** */
	int lineCount = 0;
	/** */
	int[] lineHeights = new int[100];
	/** */
	protected LineStyle lineToAnchor = null;
	/** */
	int[] lineWidths = new int[100];
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
	int requiredHeight = 0;
	/** */
	int requiredWidth = 0;
	/** */
	protected int rotation = 0;
	/** */
	protected int rotationAlign = SpiderChartGraphics.ROTATE_CENTER;
	/** */
	private String sFormat = "";
	/** */
	protected String tip = "";

	/** Constructor */
	public SpiderChartLabel(String pformat, final String pvalue, final boolean pvertical, final boolean pCenter) {
		if (pformat.length() == 0) {
			pformat = pvalue;
		}
		if (pvertical) {
			pformat = "@rotation value='90'@" + pformat;
		}
		if (pCenter) {
			pformat = "@align value='CENTER'@" + pformat;
		}
		final int p = pformat.indexOf("#value#");
		if (p >= 0) {
			pformat = pformat.substring(0, p - 1) + pvalue + pformat.substring(p + 7);
		}
		this.sFormat = pformat;
	}

	/** */
	protected int calcRelativeX(final String s, final int x) {
		if (s.length() == 0) {
			return x;
		}
		if (s.indexOf("+") == 0) {
			return x + this.calcX(s.substring(1));
		}
		if (s.indexOf("-") == 0) {
			return x - this.calcX(s.substring(1));
		}
		return this.calcX(s);
	}

	/** */
	protected int calcRelativeY(final String s, final int y) {
		if (s.length() == 0) {
			return y;
		}
		if (s.indexOf("+") == 0) {
			return y + this.calcY(s.substring(1));
		}
		if (s.indexOf("-") == 0) {
			return y - this.calcY(s.substring(1));
		}
		return this.calcY(s);
	}

	/** */
	protected int calcX(final String s) {
		if (s.indexOf("px") > 0) {
			return this.parseInt(s.substring(0, s.indexOf("px")));
		}
		if (s.indexOf("%") > 0) {
			return (this.chart.getWidth() * Integer.parseInt(s.substring(0, s.indexOf("%")))) / 100;
		}
		if (this.chart.XAxis == null) {
			return 0;
		}
		return this.chart.XAxis.scale.getScreenCoord(new Double(s).doubleValue());
	}

	/** */
	protected int calcY(final String s) {
		if (s.indexOf("px") > 0) {
			return this.parseInt(s.substring(0, s.indexOf("px")));
		}
		if (s.indexOf("%") > 0) {
			return (this.chart.getHeight() * Integer.parseInt(s.substring(0, s.indexOf("%")))) / 100;
		}
		if (this.chart.YAxis == null) {
			return 0;
		}
		return this.chart.YAxis.scale.getScreenCoord(new Double(s).doubleValue());
	}

	/** */
	public String getClickInfo() {
		return this.clickInfo;
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
	protected int getRotatedHeight() {
		if ((this.rotation == 90) || (this.rotation == -90) || (this.rotation == 270)) {
			return this.requiredWidth;
		}
		return this.requiredHeight;
	}

	/** */
	protected int getRotatedWidth() {
		if ((this.rotation == 90) || (this.rotation == -90) || (this.rotation == 270)) {
			return this.requiredHeight;
		}
		return this.requiredWidth;
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
	public void initialize(final SpiderChartGraphics g, final SpiderChart c) {
		this.chart = c;
	}

	/** */
	public void paint(final SpiderChartGraphics g, final int x, final int y, final int width, final int height) {
		if (this.chart != null) {
			this.chart.placeFloatingObject(this);
		}
		if (this.chart != null) {
			this.chart.addFloationgObject(this);
		}
		this.render(g);
	}

	/** */
	private int parseInt(final String s) {
		try {
			return Integer.parseInt(s);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/** */
	protected void render(final SpiderChartGraphics g2) {
		SpiderChartGraphics g = g2;
		SpiderChartImage rotatedImage = null;
		final int tmpPositionX = this.positionX;
		final int tmpPositionY = this.positionY;
		if ((this.lineToAnchor != null) && (this.rotation == 0)) {
			if (this.anchorY > this.positionY) {
				if (this.anchorX <= this.positionX) {
					this.lineToAnchor.draw(g2, this.anchorX, this.anchorY, this.positionX,
							(this.positionY + this.requiredHeight) - 1);
				} else {
					this.lineToAnchor.draw(g2, this.anchorX, this.anchorY, this.positionX + this.requiredWidth,
							(this.positionY + this.requiredHeight) - 1);
				}
			} else if (this.anchorX <= this.positionX) {
				this.lineToAnchor.draw(g2, this.anchorX, this.anchorY, this.positionX, this.positionY);
			} else {
				this.lineToAnchor.draw(g2, this.anchorX, this.anchorY, this.positionX + this.requiredWidth,
						this.positionY);
			}
		}
		if (this.rotation != 0) {
			final SpiderChartColor transparentColor = SWTGraphicsSupplier.getColor(1, 1, 1);
			rotatedImage = SWTGraphicsSupplier.createTransparentImage(this.requiredWidth, this.requiredHeight,
					transparentColor);
			g = rotatedImage.getGraphics();
			g.setFont(g2.getFont());
			g.setColor(g2.getColor());
			this.positionX = 0;
			this.positionY = 0;
		}
		int x = this.positionX;
		int lineStart = 0;
		if (this.background != null) {
			final SpiderChartColor c = g.getColor();
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
		this.clickableArea = null;
		if (this.rotation != 0) {
			this.positionX = tmpPositionX;
			this.positionY = tmpPositionY;
			g2.paintRotatedImage(rotatedImage, this.rotation, this.positionX, this.positionY, this.rotationAlign);
		} else {
			this.clickableArea = new Polygon();
			this.clickableArea.addPoint(this.positionX, this.positionY);
			this.clickableArea.addPoint(this.positionX, (this.positionY + this.requiredHeight) - 1);
			this.clickableArea.addPoint((this.positionX + this.requiredWidth) - 1,
					(this.positionY + this.requiredHeight) - 1);
			this.clickableArea.addPoint((this.positionX + this.requiredWidth) - 1, this.positionY);
			if (this.chart != null) {
				this.chart.chartHotAreas.addElement(this);
			}
		}
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
