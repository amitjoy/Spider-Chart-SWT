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
package com.amitinside.tooling.chart.plotter;

import java.util.ArrayList;
import java.util.List;

import com.amitinside.tooling.chart.SpiderChartComponent;
import com.amitinside.tooling.chart.gc.AbstractChartGraphics;
import com.amitinside.tooling.chart.gc.AbstractChartImage;
import com.amitinside.tooling.chart.scale.SpiderChartScale;
import com.amitinside.tooling.chart.sequence.DataSeq;
import com.amitinside.tooling.chart.style.FillStyle;

/**
 * Base class used to create spider chart plotters
 *
 * @author AMIT KUMAR MONDAL
 *
 */
public abstract class AbstractPlotter extends SpiderChartComponent {

	/** Spider Chart Background Image */
	private AbstractChartImage backgroundImage;

	/** background fill style */
	private FillStyle backgroundStyle = null;

	/** depth of the component */
	private int depth = 0;

	/** list of sequences to be used for plotting */
	private List<DataSeq> seq = new ArrayList<>();

	/** visible height on the canvas */
	private int visibleHeight = 0;

	/** visible width on the canvas */
	private int visibleWidth = 0;

	/** x-axis scaling */
	private SpiderChartScale xScale;

	/** y-axis scaling */
	private SpiderChartScale yScale;

	/** adds sequence to the plotter */
	public void addSeq(final DataSeq s) {
		this.replaceSeq(-1, s);
	}

	/** calculates the max values in the provided scale */
	private void calculateMax(final SpiderChartScale s, final double m) {
		if (!s.isExactMaxValue()) {
			s.setMax(m);
			return;
		}
		final double[] value = s.getPreferred_MaxMin_values();
		if ((value != null) && (value.length > 0)) {
			for (final double preferred_MaxMin_value : value) {
				if (preferred_MaxMin_value >= m) {
					s.setMax(preferred_MaxMin_value);

					break;
				}
			}
		}
	}

	/** calculates the min values in the provided scale */
	private void calculateMin(final SpiderChartScale s, final double m) {
		if (!s.isExactMinValue()) {
			s.setMin(m);
			return;
		}
		final double[] value = s.getPreferred_MaxMin_values();
		if ((value != null) && (value.length > 0)) {
			for (int j = value.length - 1; j > 0; j--) {
				if (value[j] <= m) {
					s.setMin(value[j]);

					break;
				}
			}
		}
	}

	/** returns the active x scaling */
	protected SpiderChartScale getActiveXScale(final DataSeq s) {
		final SpiderChartScale scale = this.xScale;
		return scale;
	}

	/** returns the active y scaling */
	protected SpiderChartScale getActiveYScale(final DataSeq s) {
		final SpiderChartScale scale = this.yScale;
		return scale;
	}

	/**
	 * @return the backgroundImage
	 */
	public AbstractChartImage getBackgroundImage() {
		return this.backgroundImage;
	}

	/**
	 * @return the backgroundStyle
	 */
	public FillStyle getBackgroundStyle() {
		return this.backgroundStyle;
	}

	/**
	 * @return the depth
	 */
	public int getDepth() {
		return this.depth;
	}

	/**
	 * @return the seq
	 */
	public List<DataSeq> getSeq() {
		return this.seq;
	}

	/** */
	public DataSeq getSeq(final int p) {
		return this.seq.get(p);
	}

	/** */
	public int getSeqCount() {
		return this.seq.size();
	}

	/**
	 * @return the visibleHeight
	 */
	public int getVisibleHeight() {
		return this.visibleHeight;
	}

	/**
	 * @return the visibleWidth
	 */
	public int getVisibleWidth() {
		return this.visibleWidth;
	}

	/**
	 * @return the xScale
	 */
	public SpiderChartScale getxScale() {
		return this.xScale;
	}

	/**
	 * @return the yScale
	 */
	public SpiderChartScale getyScale() {
		return this.yScale;
	}

	/** plots the values */
	public void plot(final AbstractChartGraphics g) {
		for (int i = 0; i < this.seq.size(); i++) {
			final DataSeq s = this.seq.get(i);
			this.plot(g, s, i);
		}
	}

	/** plots the values */
	protected void plot(final AbstractChartGraphics g, final DataSeq s, final int serieSec) {
	}

	/** plots the background of the plotting canvas */
	public void plotBackground(final AbstractChartGraphics g, final int bw, final int bh, final int offsetX,
			final int offsetY) {
		if (this.backgroundStyle != null) {
			this.backgroundStyle.draw(g, this.x, this.y, this.x + bw, this.y + bh);
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

	/** replaces a sequence with the provided one */
	public void replaceSeq(final int p, final DataSeq s) {
		final SpiderChartScale tmpScaleX = this.getActiveXScale(s);
		this.getActiveYScale(s);
		if (p >= this.seq.size()) {
			return;
		}
		if ((this.seq.size() > 3)) {
			return;
		}
		if (p == -1) {
			this.seq.add(s);
		} else {
			this.seq.set(p, s);
		}
		final boolean fixedLimits = false;
		final boolean cumulative = false;
		if (!(this instanceof AbstractPlotter)) {
			for (int i = 0; i < s.getSize(); i++) {
				if (s.getElementY(i) != null) {
					final double XValue = ((Double) s.getElementX(i)).doubleValue();
					double YValue = ((Double) s.getElementY(i)).doubleValue();
					if (cumulative) {
						YValue = 0.0D;
						for (int si = 0; si < this.seq.size(); si++) {
							final DataSeq ser = this.seq.get(si);
						}
					}
					if (XValue >= tmpScaleX.getMax()) {
						this.calculateMax(tmpScaleX, XValue);
					}
					if (XValue < tmpScaleX.getMin()) {
						this.calculateMin(tmpScaleX, XValue);
					}
					if (!fixedLimits) {
						if (YValue > this.yScale.getMax()) {
							this.calculateMax(this.yScale, YValue);
						}
						if (YValue < this.yScale.getMin()) {
							this.calculateMin(this.yScale, YValue);
						}
					}
				}
			}
		}
	}

	/**
	 * @param backgroundImage
	 *            the backgroundImage to set
	 */
	public void setBackgroundImage(final AbstractChartImage backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	/**
	 * @param backgroundStyle
	 *            the backgroundStyle to set
	 */
	public void setBackgroundStyle(final FillStyle backgroundStyle) {
		this.backgroundStyle = backgroundStyle;
	}

	/**
	 * @param depth
	 *            the depth to set
	 */
	public void setDepth(final int depth) {
		this.depth = depth;
	}

	/** */
	public void setSeq(final int p, final DataSeq s) {
		if (p < this.seq.size()) {
			this.seq.set(p, s);
		}
	}

	/**
	 * @param seq
	 *            the seq to set
	 */
	public void setSeq(final List<DataSeq> seq) {
		this.seq = seq;
	}

	/**
	 * @param visibleHeight
	 *            the visibleHeight to set
	 */
	public void setVisibleHeight(final int visibleHeight) {
		this.visibleHeight = visibleHeight;
	}

	/**
	 * @param visibleWidth
	 *            the visibleWidth to set
	 */
	public void setVisibleWidth(final int visibleWidth) {
		this.visibleWidth = visibleWidth;
	}

	/**
	 * @param xScale
	 *            the xScale to set
	 */
	public void setxScale(final SpiderChartScale xScale) {
		this.xScale = xScale;
	}

	/**
	 * @param yScale
	 *            the yScale to set
	 */
	public void setyScale(final SpiderChartScale yScale) {
		this.yScale = yScale;
	}
}
