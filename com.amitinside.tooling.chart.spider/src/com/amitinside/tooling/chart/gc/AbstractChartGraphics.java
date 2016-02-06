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
package com.amitinside.tooling.chart.gc;

public abstract class AbstractChartGraphics {

	/** */
	public static int ROTATE_CENTER = 0;
	/** */
	public static int ROTATE_LEFTTOP = 1;
	/** */
	public static int STROKE_DASHED = 3;
	/** */
	public static int STROKE_DOTTED = 2;
	/** */
	public static int STROKE_NORMAL = 1;
	/** */
	protected int lineStyle = STROKE_NORMAL;
	/** */
	protected int lineWidth = 1;
	/** */
	protected AbstractChartImage textureImage = null;

	/** */
	public void createFadeArea(final AbstractChartColor colorFrom, final AbstractChartColor colorUntil, final int x,
			final int y, final int w, final int h, final boolean vertical, final boolean cyclic) {
	}

	/** */
	public void dispose() {
	}

	/** */
	public void drawArc(final int x, final int y, final int w, final int h, final int a1, final int a2) {
	}

	/** */
	public void drawImage(final AbstractChartImage image, final int x, final int y) {
	}

	/** */
	public void drawImage(final AbstractChartImage image, final int x1Dest, final int y1Dest, final int x2Dest,
			final int y2Dest, final int x1Source, final int y1Source, final int x2Source, final int y2Source) {
	}

	/** */
	public void drawLine(final int x1, final int y1, final int x2, final int y2) {
	}

	/** */
	public void drawLineWithStyle(final int x1, final int y1, final int x2, final int y2) {
		if (this.lineStyle == STROKE_NORMAL) {
			this.drawSimpleLine(x1, y1, x2, y2);
		} else {
			int segment = 10;
			int segmentspace = 10;
			if (this.lineStyle == STROKE_DOTTED) {
				segment = 1;
				segmentspace = 5;
			}
			final int h = Math.abs(y2 - y1);
			final int w = Math.abs(x2 - x1);
			if ((h == 0) && (w == 0)) {
				return;
			}
			final double hipo = Math.sqrt((h * h) + (w * w));

			final double Cos = w / hipo;
			final double Sin = h / hipo;

			int Xslope = 1;
			int Yslope = 1;
			if (x2 < x1) {
				Xslope = -1;
			}
			if (y2 < y1) {
				Yslope = -1;
			}
			int subx1 = 0;
			int suby1 = 0;
			int subx2 = 0;
			int suby2 = 0;
			int subsegment = 0;
			for (;;) {
				suby2 = (int) (Sin * (subsegment + segment)) * Yslope;
				subx2 = (int) (Cos * (subsegment + segment)) * Xslope;
				suby1 = (int) (Sin * subsegment) * Yslope;
				subx1 = (int) (Cos * subsegment) * Xslope;
				if ((w < Math.abs(subx1)) || (h < Math.abs(suby1))) {
					break;
				}
				if (w < Math.abs(subx2)) {
					subx2 = w * Xslope;
				}
				if (h < Math.abs(suby2)) {
					suby2 = h * Yslope;
				}
				this.drawSimpleLine(x1 + subx1, y1 + suby1, x1 + subx2, y1 + suby2);

				subsegment = subsegment + segment + segmentspace;
			}
		}
	}

	/** */
	public void drawPolygon(final int[] x1, final int[] y1, final int count) {
	}

	/** */
	public void drawRect(final int x1, final int y1, final int w, final int h) {
		this.drawLineWithStyle(x1, y1, x1, y1 + h);
		this.drawLineWithStyle(x1, y1, x1 + w, y1);
		this.drawLineWithStyle(x1, y1 + h, x1 + w, y1 + h);
		this.drawLineWithStyle(x1 + w, y1, x1 + w, y1 + h);
	}

	/** */
	public boolean drawRotatedText(final AbstractChartFont descFont, final AbstractChartColor descColor, final String txt,
			final int angle, final int x, final int y, final boolean b) {
		return false;
	}

	/** */
	public void drawRoundedRect(final int x1, final int y1, final int w, final int h) {
		this.drawRect(x1, y1, w, h);
	}

	/** */
	protected void drawSimpleLine(final int x1, final int y1, final int x2, final int y2) {
		int pixelsWidth = 1;

		pixelsWidth = this.lineWidth * 1;
		if (pixelsWidth < 1) {
			pixelsWidth = 1;
		}
		this.drawLine(x1, y1, x2, y2);
		if (pixelsWidth == 1) {
			return;
		}
		if (pixelsWidth > 1) {
			int xwidth = 0;
			int ywidth = 0;

			final int h = Math.abs(y2 - y1);
			final int w = Math.abs(x2 - x1);

			final double hipo = Math.sqrt((h * h) + (w * w));

			final double Cos = w / hipo;

			xwidth = 1;
			ywidth = 0;
			if (Cos > Math.cos(1.0471666666666668D)) {
				xwidth = 0;
				ywidth = 1;
			}
			if (Cos > Math.cos(0.5235833333333334D)) {
				xwidth = 0;
				ywidth = 1;
			}
			int side = 1;
			int distanceToCenter = 0;
			for (int i = 2; i <= pixelsWidth; i++) {
				if (side == 1) {
					distanceToCenter++;
				}
				this.drawLine(x1 + (side * xwidth * distanceToCenter), y1 + (side * ywidth * distanceToCenter),
						x2 + (side * xwidth * distanceToCenter), y2 + (side * ywidth * distanceToCenter));
				side *= -1;
			}
		}
	}

	/** */
	public void drawText(final String s, final int x, final int y) {
	}

	/** */
	public void fillArc(final int x, final int y, final int w, final int h, final int a1, final int a2) {
	}

	/** */
	public void fillPolygon(final int[] x1, final int[] y1, final int count) {
	}

	/** */
	public void fillRect(final int x1, final int y1, final int w, final int h) {
	}

	/** */
	public void fillRoundRect(final int x1, final int y1, final int w, final int h) {
		this.fillRect(x1, y1, w, h);
	}

	/** */
	public Object getAlphaComposite() {
		return null;
	}

	/** */
	public AbstractChartColor getColor() {
		return null;
	}

	/** */
	public AbstractChartFont getFont() {
		return null;
	}

	/** */
	public int getFontHeight() {
		return this.getFontHeight(null);
	}

	/** */
	public int getFontHeight(final AbstractChartFont font) {
		return 0;
	}

	/** */
	public int getFontWidth(final AbstractChartFont font, final String s) {
		return 0;
	}

	/** */
	public int getFontWidth(final String s) {
		return this.getFontWidth(null, s);
	}

	/** */
	public void paintRotatedImage(final AbstractChartImage srcImage, final int angle, final int x, final int y,
			final int alginment) {
	}

	/** */
	public void setAlpha(final float a) {
	}

	/** */
	public void setAlphaComposite(final Object a) {
	}

	/** */
	public void setColor(final AbstractChartColor color) {
	}

	/** */
	public void setFont(final AbstractChartFont font) {
	}

	/** */
	public void setLineStyle(final int style) {
		this.lineStyle = style;
	}

	/** */
	public void setLineWidth(final int w) {
		this.lineWidth = w;
	}

	/** */
	public void setTexture(final AbstractChartImage image) {
		this.textureImage = image;
	}
}
