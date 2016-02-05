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

import java.util.Calendar;
import java.util.Vector;

import com.amitinside.tooling.chart.gc.SpiderChartGraphics;
import com.amitinside.tooling.chart.gc.SpiderChartImage;

/**
 * Base class used to create spider chart plotters
 *
 * @author AMIT KUMAR MONDAL
 *
 */
public class SpiderChartPlotter extends SpiderChartComponent {

	/** */
	public FillStyle back = null;

	/** Spider Chart Backgroud Image */
	public SpiderChartImage backgroundImage;

	/** */
	protected boolean combinable = true;

	/** */
	public int depth = 0;

	/** */
	protected int needsAxis = 2;

	/** */
	Vector<DataSeq> series = new Vector<>(0, 1);

	/** */
	public int visibleHeight = 0;

	/** */
	public int visibleWidth = 0;

	/** */
	public Scale XScale;

	/** */
	public Scale Y2Scale;

	/** */
	public Scale YScale;

	/** */
	public void addSeq(final DataSeq s) {
		this.replaceSerie(-1, s);
	}

	/** */
	private void calculateNewMax(final Scale s, final double m) {
		if (!s.exactMaxValue) {
			s.max = m;
			return;
		}
		if ((s.preferred_MaxMin_values != null) && (s.preferred_MaxMin_values.length > 0)) {
			for (final double preferred_MaxMin_value : s.preferred_MaxMin_values) {
				if (preferred_MaxMin_value >= m) {
					s.max = preferred_MaxMin_value;

					break;
				}
			}
		}
	}

	/** */
	private void calculateNewMin(final Scale s, final double m) {
		if (!s.exactMinValue) {
			s.min = m;
			return;
		}
		if ((s.preferred_MaxMin_values != null) && (s.preferred_MaxMin_values.length > 0)) {
			for (int j = s.preferred_MaxMin_values.length - 1; j > 0; j--) {
				if (s.preferred_MaxMin_values[j] <= m) {
					s.min = s.preferred_MaxMin_values[j];

					break;
				}
			}
		}
	}

	/** */
	protected Scale getActiveXScale(final DataSeq s) {
		Scale scale = this.XScale;
		if (s.secondaryXAxis != null) {
			scale = s.secondaryXAxis.scale;
		}
		return scale;
	}

	/** */
	protected Scale getActiveYScale(final DataSeq s) {
		Scale scale = this.YScale;
		if (s.secondYAxis && (this.Y2Scale != null)) {
			scale = this.Y2Scale;
		} else if (s.secondaryYAxis != null) {
			scale = s.secondaryYAxis.scale;
		}
		return scale;
	}

	/** */
	public boolean getCombinable() {
		return this.combinable;
	}

	/** */
	public int getNeedsAxis() {
		return this.needsAxis;
	}

	/** */
	public DataSeq getSerie(final int p) {
		return this.series.elementAt(p);
	}

	/** */
	public int getSeriesCount() {
		return this.series.size();
	}

	/** */
	protected boolean inSameSubChart(final DataSeq tmpSerie, final DataSeq s) {
		boolean usingStackAxis = false;
		if ((s.secondaryYAxis != null) && s.secondaryYAxis.mainAxis.stackAdditionalAxis) {
			usingStackAxis = true;
		}
		if ((tmpSerie.secondaryYAxis != null) && tmpSerie.secondaryYAxis.mainAxis.stackAdditionalAxis) {
			usingStackAxis = true;
		}
		return (tmpSerie.secondaryXAxis == s.secondaryXAxis)
				&& ((tmpSerie.secondaryYAxis == s.secondaryYAxis) || !usingStackAxis);
	}

	/** */
	public void plot(final SpiderChartGraphics g) {
		for (int i = 0; i < this.series.size(); i++) {
			final DataSeq s = this.series.elementAt(i);

			this.plot(g, s, i);
		}
	}

	/** */
	public void plotBackground(final SpiderChartGraphics g, final int bw, final int bh, final int offsetX,
			final int offsetY) {
		if (this.back != null) {
			final boolean D3 = false;
			if (D3) {
				final int[] xs = new int[6];
				final int[] ys = new int[6];
				xs[0] = this.x + offsetX;
				ys[0] = this.y + offsetY;

				xs[1] = this.x + offsetX + this.depth;
				ys[1] = (this.y + offsetY) - this.depth;

				xs[2] = this.x + offsetX + this.visibleWidth;
				ys[2] = (this.y + offsetY) - this.depth;

				xs[3] = this.x + offsetX + this.visibleWidth;
				ys[3] = (this.y + offsetY + this.visibleHeight) - this.depth - this.depth;

				xs[4] = (this.x + offsetX + this.visibleWidth) - this.depth;
				ys[4] = (this.y + offsetY + this.visibleHeight) - this.depth;

				xs[5] = this.x + offsetX;
				ys[5] = (this.y + offsetY + this.visibleHeight) - this.depth;

				this.back.drawPolygon(g, xs, ys, 6);
			} else {
				this.back.draw(g, this.x, this.y, this.x + bw, this.y + bh);
			}
		}
		if (this.backgroundImage != null) {
			final int w = this.backgroundImage.getWidth();
			final int h = this.backgroundImage.getHeight();
			if ((w > -1) && (h > -1)) {
				int toCenterX = (bw - w) / 2;
				if (toCenterX < 0) {
					toCenterX = 0;
				}
				int toCenterY = (bh - h) / 2;
				if (toCenterY < 0) {
					toCenterY = 0;
				}
				g.drawImage(this.backgroundImage, toCenterX + this.x, this.y + toCenterY);
			}
		}
	}

	/** */
	protected void plot(final SpiderChartGraphics g, final DataSeq s, final int serieSec) {
	}

	/** */
	public void replaceSerie(final int p, final DataSeq s) {
		final Scale tmpScaleX = this.getActiveXScale(s);
		this.getActiveYScale(s);
		if (p >= this.series.size()) {
			return;
		}
		Calendar.getInstance().get(2);
		if ((SpiderChart.d() != 1) && (this.series.size() > 3)) {
			return;
		}
		if (p == -1) {
			this.series.addElement(s);
		} else {
			this.series.setElementAt(s, p);
		}
		boolean fixedLimits = false;
		if (this instanceof LinePlotter) {
			fixedLimits = ((LinePlotter) this).fixedLimits;
		}
		final boolean cumulative = false;
		if (!(this instanceof SpiderPlotter)) {
			for (int i = 0; i < s.getSize(); i++) {
				if (s.getElementY(i) != null) {
					final double XValue = ((Double) s.getElementX(i)).doubleValue();
					double YValue = ((Double) s.getElementY(i)).doubleValue();
					if (cumulative) {
						YValue = 0.0D;
						for (int si = 0; si < this.series.size(); si++) {
							final DataSeq ser = this.series.elementAt(si);
							if (this.inSameSubChart(ser, s) && (ser.getSize() > i)) {
								if (ser.getElementY(i) != null) {
									final double d = ((Double) ser.getElementY(i)).doubleValue();
									YValue += d;
								}
							}
						}
					}
					if (XValue >= tmpScaleX.max) {
						this.calculateNewMax(tmpScaleX, XValue);
					}
					if (XValue < tmpScaleX.min) {
						this.calculateNewMin(tmpScaleX, XValue);
					}
					if (!fixedLimits) {
						if (s.secondYAxis && (this.Y2Scale != null)) {
							if (YValue > this.Y2Scale.max) {
								this.calculateNewMax(this.Y2Scale, YValue);
							}
							if (YValue < this.Y2Scale.min) {
								this.calculateNewMin(this.Y2Scale, YValue);
							}
						} else {
							if (YValue > this.YScale.max) {
								this.calculateNewMax(this.YScale, YValue);
							}
							if (YValue < this.YScale.min) {
								this.calculateNewMin(this.YScale, YValue);
							}
						}
					}
				}
			}
		}
	}

	/** */
	public void setSerie(final int p, final DataSeq s) {
		if (p < this.series.size()) {
			this.series.setElementAt(s, p);
		}
	}
}
