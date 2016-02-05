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

import java.text.DecimalFormat;

import com.amitinside.tooling.chart.builder.AxesConfigurer;
import com.amitinside.tooling.chart.gc.Polygon;
import com.amitinside.tooling.chart.gc.SWTGraphicsSupplier;
import com.amitinside.tooling.chart.gc.SpiderChartColor;
import com.amitinside.tooling.chart.gc.SpiderChartFont;
import com.amitinside.tooling.chart.gc.SpiderChartGraphics;

/**
 * Used to plot spider chart
 *
 * @author AMIT KUMAR MONDAL
 */
public class SpiderPlotter extends SpiderChartPlotter {

	/**
	 * Spider Chart axes names
	 */
	public String[] axesFactors;

	/**
	 * Spider Chart Axes Factor Color
	 */
	public SpiderChartColor axisFactorColor = SWTGraphicsSupplier.getColor(SpiderChartColor.BLACK);

	/**
	 * Spider Chart Axis Factor Colors (in case you need to set different colors
	 * for different axes)
	 */
	public SpiderChartColor[] axisFactorColors;

	/**
	 * Spider Chart Axes Factor Text Font
	 */
	public SpiderChartFont axisFactorFont = SWTGraphicsSupplier.getFont("Verdana", SpiderChartFont.PLAIN, 10);

	/**
	 * Spider Chart Polygon Area Background Style
	 */
	public FillStyle backStyle;

	/**
	 * Spider Chart Border Style
	 */
	LineStyle border = new LineStyle(0.2F, SWTGraphicsSupplier.getColor(SpiderChartColor.BLACK), 1);

	/**
	 * Spider Chart Radius
	 */
	public double chartRadius = 0.9D;

	/**
	 * Spider Chart would be surrounded by an enclosing circle
	 */
	public boolean drawCircle = false;

	/**
	 * Spider Chart Font for Grid Label
	 */
	public SpiderChartFont gridFont;

	/**
	 * Spider Chart Font Color for Grid Label
	 */
	public SpiderChartColor gridFontColor = SWTGraphicsSupplier.getColor(SpiderChartColor.BLACK);

	/**
	 * Spider Chart Grid Style
	 */
	public LineStyle gridStyle;

	/** Mark Scales on Every Axis */
	public boolean markScalesOnEveryAxis = false;

	/**
	 * Spider Chart Scaling Factors (Maximum Values)
	 */
	public double[] maxScaleFactors;

	/**
	 * Spider Chart Scaling Factors (Minimum Values)
	 */
	public double[] minScaleFactors;

	/** */
	public SpiderChartColor[] pointColors = null;

	/** */
	public double[] pointColorScale = null;

	/**
	 * Spider Chart Scaling Divisions
	 */
	public int scalingDivisions = 5;

	/**
	 * Spider Chart Scaling Label Format
	 */
	public String scalingLabelFormat = "#.#";

	/**
	 * Constructor
	 */
	public SpiderPlotter() {
		this.combinable = false;
		this.needsAxis = 0;
	}

	/** */
	public void inject(final AxesConfigurer configurer) {
		this.maxScaleFactors = configurer.maxScales();
		this.minScaleFactors = configurer.minScales();
		this.axesFactors = configurer.axesNames();
	}

	/** {@inheritDoc}} **/
	@Override
	protected void plot(final SpiderChartGraphics g, final DataSeq s, final int serieSec) {
		LineDataSeq p;
		if (s instanceof LineDataSeq) {
			p = (LineDataSeq) s;
		} else {
			return;
		}
		s.hotAreas.removeAllElements();

		final double count = p.yData.size();

		final int[] xs = new int[(int) count];
		final int[] ys = new int[(int) count];

		int radi = 0;
		radi = this.width;
		if (this.height < radi) {
			radi = this.height;
		}
		radi = (int) (radi * this.chartRadius);

		final int toCenterX = (this.width - radi) / 2;
		final int toCenterY = (this.height - radi) / 2;

		final int PieCenterX = toCenterX + this.x + (radi / 2);
		final int PieCenterY = toCenterY + this.y + (radi / 2);
		double angle;
		if ((serieSec == 0) || (serieSec >= (this.series.size() - 1))) {
			if ((serieSec == 0) && (this.backStyle != null) && this.drawCircle) {
				this.backStyle.drawArc(g, toCenterX + this.x, toCenterY + this.y, radi, radi, 0, 360);
			}
			if ((serieSec == 0) && (this.backStyle != null) && !this.drawCircle) {
				for (int i = 0; i < count; i++) {
					angle = (360.0D / count) * i;

					angle += 90.0D;
					if (angle > 360.0D) {
						angle -= 360.0D;
					}
					final double radian = 0.01745277777777778D * angle;
					final double Sin = Math.sin(radian);
					final double Cos = Math.cos(radian);
					int relativeY = (int) (Sin * (radi / 2));
					final int relativeX = (int) (Cos * (radi / 2));
					relativeY *= -1;

					xs[i] = PieCenterX + relativeX;
					ys[i] = PieCenterY + relativeY;
				}
				this.backStyle.drawPolygon(g, xs, ys, (int) count);
			}
			if ((serieSec == (this.series.size() - 1)) && (this.border != null)) {
				if (this.drawCircle) {
					this.border.drawArc(g, toCenterX + this.x, toCenterY + this.y, radi, radi, 0, 360);
				}
				for (int i = 0; i < count; i++) {
					angle = (360.0D / count) * i;

					angle += 90.0D;
					if (angle > 360.0D) {
						angle -= 360.0D;
					}
					final double radian = 0.01745277777777778D * angle;
					final double Sin = Math.sin(radian);
					final double Cos = Math.cos(radian);
					int relativeY = (int) (Sin * (radi / 2));
					final int relativeX = (int) (Cos * (radi / 2));
					relativeY *= -1;

					this.border.draw(g, PieCenterX, PieCenterY, PieCenterX + relativeX, PieCenterY + relativeY);
				}
			}
			if ((serieSec == 0) && (this.axisFactorFont != null)) {
				g.setFont(this.axisFactorFont);
				for (int i = 0; i < count; i++) {
					g.setColor(this.axisFactorColor);
					if ((this.axisFactorColors != null) && (this.axisFactorColors.length > i)) {
						g.setColor(this.axisFactorColors[i]);
					}
					angle = (360.0D / count) * i;

					angle += 90.0D;
					if (angle > 360.0D) {
						angle -= 360.0D;
					}
					final int tmpradi = (int) ((radi * 1.1D) / 2.0D);
					int correction = 0;
					if ((angle > 120.0D) && (angle < 240.0D)) {
						correction = g.getFontWidth(this.axesFactors[i]);
					}
					final double radian = 0.01745277777777778D * angle;
					final double Sin = Math.sin(radian);
					final double Cos = Math.cos(radian);
					int relativeY = (int) (Sin * tmpradi);
					final int relativeX = (int) (Cos * tmpradi);
					relativeY *= -1;
					if (this.axesFactors.length > i) {
						g.drawString(this.axesFactors[i], (PieCenterX + relativeX) - correction,
								PieCenterY + relativeY);
					}
				}
			}
		}
		for (int i = 0; i < count; i++) {
			angle = (360.0D / count) * i;

			angle += 90.0D;
			if (angle > 360.0D) {
				angle -= 360.0D;
			}
			int tmpradi = 0;

			double min = 0.0D;
			double max = 100.0D;
			if (this.minScaleFactors.length >= (i + 1)) {
				min = this.minScaleFactors[i];
			}
			if (this.maxScaleFactors.length >= (i + 1)) {
				max = this.maxScaleFactors[i];
			}
			tmpradi = (int) (((((Double) p.getElementY(i)).doubleValue() - min) * 100.0D) / (max - min));
			tmpradi = (tmpradi * radi) / 100;

			final double radian = 0.01745277777777778D * angle;
			final double Sin = Math.sin(radian);
			final double Cos = Math.cos(radian);
			int relativeY = (int) (Sin * (tmpradi / 2));
			final int relativeX = (int) (Cos * (tmpradi / 2));
			relativeY *= -1;
			xs[i] = PieCenterX + relativeX;
			ys[i] = PieCenterY + relativeY;
		}
		if (p.style != null) {
			p.style.drawPolygon(g, xs, ys, (int) count);
		}
		if (p.fillStyle != null) {
			p.fillStyle.drawPolygon(g, xs, ys, (int) count);
		}
		for (int i = 0; i < count; i++) {
			final Polygon po = new Polygon();
			po.addPoint(xs[i] - 3, ys[i] - 3);
			po.addPoint(xs[i] - 3, ys[i] + 3);
			po.addPoint(xs[i] + 3, ys[i] + 3);
			po.addPoint(xs[i] + 3, ys[i] - 3);

			s.hotAreas.addElement(po);
			double YValue;
			if (p.drawPoint) {
				SpiderChartColor c = p.pointColor;
				YValue = ((Double) p.getElementY(i)).doubleValue();
				if ((this.pointColors != null) && (this.pointColorScale != null)) {
					if (this.pointColors.length > 0) {
						c = this.pointColors[0];
					}
					for (int j = 1; j < this.pointColors.length; j++) {
						if (this.pointColorScale.length >= j) {
							if (this.pointColorScale[j - 1] > YValue) {
								break;
							}
							c = this.pointColors[j];
						}
					}
				}
				g.setColor(c);
				if (p.icon == null) {
					g.fillRect(xs[i] - 3, ys[i] - 3, 6, 6);
				} else {
					g.drawImage(p.icon, xs[i] - 4, ys[i] - 4);
				}
			}
			if (p.valueFont != null) {
				g.setColor(p.valueColor);
				g.setFont(p.valueFont);

				YValue = ((Double) p.getElementY(i)).doubleValue();

				String txt = p.doubleToString(new Double(YValue));

				if (YValue == (int) YValue) {
					txt = new Integer((int) YValue).toString();
				}
				if (p.labelTemplate.length() > 0) {
					txt = p.labelTemplate;
				}
				if ((p.dataLabels != null) && (p.dataLabels.length > i)) {
					txt = p.dataLabels[i];
				}
				g.drawString(txt, xs[i] + 7, ys[i]);
			}
		}
		if (this.gridStyle != null) {
			double min = 0.0D;
			double max = 100.0D;
			if (this.minScaleFactors.length >= 1) {
				min = this.minScaleFactors[0];
			}
			if (this.maxScaleFactors.length >= 1) {
				max = this.maxScaleFactors[0];
			}
			final int tickInterval = 100 / this.scalingDivisions;
			final double tickIntervalAbs = (max - min) / this.scalingDivisions;
			int tickAt = 0;
			double tickAtAbs = 0.0D;
			for (int j = 0; j < this.scalingDivisions; j++) {
				tickAt += tickInterval;
				tickAtAbs += tickIntervalAbs;
				for (int i = 0; i < count; i++) {
					angle = (360.0D / count) * i;

					angle += 90.0D;
					if (angle > 360.0D) {
						angle -= 360.0D;
					}
					final int tmpradi = (radi * tickAt) / 100;
					final double radian = 0.01745277777777778D * angle;
					final double Sin = Math.sin(radian);
					final double Cos = Math.cos(radian);
					int relativeY = (int) (Sin * (tmpradi / 2));
					final int relativeX = (int) (Cos * (tmpradi / 2));
					relativeY *= -1;

					xs[i] = PieCenterX + relativeX;
					ys[i] = PieCenterY + relativeY;
				}
				if (serieSec >= (this.series.size() - 1)) {
					this.gridStyle.drawPolygon(g, xs, ys, (int) count);
				}
				if ((serieSec >= (this.series.size() - 1)) && (this.gridFont != null)) {
					g.setColor(this.gridFontColor);
					g.setFont(this.gridFont);

					final double tickValue = tickAtAbs;

					String v = "";
					v = "" + tickValue;

					if (tickValue == (int) tickValue) {
						v = "" + (int) tickValue;
					}
					if (this.scalingLabelFormat.length() > 0) {
						DecimalFormat df = null;
						df = new DecimalFormat(this.scalingLabelFormat);
						v = df.format(new Double(tickValue));
					}
					if (this.markScalesOnEveryAxis) {
						for (int i = 0; i < xs.length; i++) {
							// TODO (AKM) Implement Each Axes Scaling Interval
							// Display
							g.drawString("" + v, xs[i] - 3 - g.getFontWidth("" + v), ys[i]);
						}
					} else {
						g.drawString("" + v, xs[0] - 3 - g.getFontWidth("" + v), ys[0]);
					}
				}
			}
		}
	}
}
