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
package com.amitinside.tooling.chart.plotter.spider;

import static com.amitinside.tooling.chart.gc.AbstractChartColor.BLACK;
import static com.amitinside.tooling.chart.gc.AbstractChartFont.PLAIN;
import static com.amitinside.tooling.chart.gc.AbstractGraphicsSupplier.getColor;
import static com.amitinside.tooling.chart.gc.AbstractGraphicsSupplier.getFont;
import static com.amitinside.tooling.chart.gc.Fonts.VERDANA;
import static com.amitinside.tooling.chart.util.ChartUtil.enumConstants;
import static java.util.Objects.requireNonNull;

import java.text.DecimalFormat;
import java.util.Arrays;

import com.amitinside.tooling.chart.builder.AxesConfigurer;
import com.amitinside.tooling.chart.gc.AbstractChartColor;
import com.amitinside.tooling.chart.gc.AbstractChartFont;
import com.amitinside.tooling.chart.gc.AbstractChartGraphics;
import com.amitinside.tooling.chart.gc.Polygon;
import com.amitinside.tooling.chart.plotter.AbstractPlotter;
import com.amitinside.tooling.chart.sequence.DataSeq;
import com.amitinside.tooling.chart.sequence.LineDataSeq;
import com.amitinside.tooling.chart.style.FillStyle;
import com.amitinside.tooling.chart.style.LineStyle;

/**
 * Used to plot spider chart
 *
 * @author AMIT KUMAR MONDAL
 */
public final class SpiderChartPlotter extends AbstractPlotter {

	/**
	 * Spider Chart axes names
	 */
	private String[] axesFactors;

	/**
	 * Spider Chart Axes Factor Color
	 */
	private AbstractChartColor axisFactorColor = getColor(BLACK);

	/**
	 * Spider Chart Axis Factor Colors (in case you need to set different colors
	 * for different axes)
	 */
	private AbstractChartColor[] axisFactorColors;

	/**
	 * Spider Chart Axes Factor Text Font
	 */
	private AbstractChartFont axisFactorFont = getFont(VERDANA, PLAIN, 11);

	/**
	 * Spider Chart Polygon Area Background Style
	 */
	private FillStyle backStyle;

	/**
	 * Spider Chart Border Style
	 */
	private LineStyle border = new LineStyle(0.2F, getColor(BLACK), 1);

	/**
	 * Spider Chart Radius
	 */
	private double chartRadius = 0.9D;

	/**
	 * Spider Chart would be surrounded by an enclosing circle
	 */
	private boolean drawCircle = false;

	/**
	 * Spider Chart Font for Grid Label
	 */
	private AbstractChartFont gridFont;

	/**
	 * Spider Chart Font Color for Grid Label
	 */
	private AbstractChartColor gridFontColor = getColor(BLACK);

	/**
	 * Spider Chart Grid Style
	 */
	private LineStyle gridStyle;

	/** Mark Scales on Every Axis */
	private boolean markScalesOnEveryAxis = false;

	/**
	 * Spider Chart Scaling Factors (Maximum Values)
	 */
	private double[] maxScaleFactors;

	/**
	 * Spider Chart Scaling Factors (Minimum Values)
	 */
	private double[] minScaleFactors;

	/** */
	private AbstractChartColor[] pointColors = null;

	/** */
	private double[] pointColorScale = null;

	/**
	 * Spider Chart Scaling Divisions
	 */
	private int scalingDivisions = 5;

	/**
	 * Spider Chart Scaling Label Format
	 */
	private Object[] scalingLabelFormat;

	/**
	 * Constructor
	 */
	public SpiderChartPlotter() {
	}

	/**
	 * @return the axesFactors
	 */
	public String[] getAxesFactors() {
		return this.axesFactors;
	}

	/**
	 * @return the axisFactorColor
	 */
	public AbstractChartColor getAxisFactorColor() {
		return this.axisFactorColor;
	}

	/**
	 * @return the axisFactorColors
	 */
	public AbstractChartColor[] getAxisFactorColors() {
		return this.axisFactorColors;
	}

	/**
	 * @return the axisFactorFont
	 */
	public AbstractChartFont getAxisFactorFont() {
		return this.axisFactorFont;
	}

	/**
	 * @return the backStyle
	 */
	public FillStyle getBackStyle() {
		return this.backStyle;
	}

	/**
	 * @return the border
	 */
	public LineStyle getBorder() {
		return this.border;
	}

	/**
	 * @return the chartRadius
	 */
	public double getChartRadius() {
		return this.chartRadius;
	}

	/**
	 * @return the gridFont
	 */
	public AbstractChartFont getGridFont() {
		return this.gridFont;
	}

	/**
	 * @return the gridFontColor
	 */
	public AbstractChartColor getGridFontColor() {
		return this.gridFontColor;
	}

	/**
	 * @return the gridStyle
	 */
	public LineStyle getGridStyle() {
		return this.gridStyle;
	}

	/**
	 * @return the maxScaleFactors
	 */
	public double[] getMaxScaleFactors() {
		return this.maxScaleFactors;
	}

	/**
	 * @return the minScaleFactors
	 */
	public double[] getMinScaleFactors() {
		return this.minScaleFactors;
	}

	/**
	 * @return the pointColors
	 */
	public AbstractChartColor[] getPointColors() {
		return this.pointColors;
	}

	/**
	 * @return the pointColorScale
	 */
	public double[] getPointColorScale() {
		return this.pointColorScale;
	}

	/**
	 * @return the scalingDivisions
	 */
	public int getScalingDivisions() {
		return this.scalingDivisions;
	}

	/**
	 * @return the scalingLabelFormat
	 */
	public Object[] getScalingLabelFormat() {
		return this.scalingLabelFormat;
	}

	/**
	 * @return the drawCircle
	 */
	public boolean isDrawCircle() {
		return this.drawCircle;
	}

	/**
	 * @return the markScalesOnEveryAxis
	 */
	public boolean isMarkScalesOnEveryAxis() {
		return this.markScalesOnEveryAxis;
	}

	/** {@inheritDoc}} **/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void plot(final AbstractChartGraphics g, final DataSeq s, final int seqSec) {
		LineDataSeq p = null;
		if (s instanceof LineDataSeq) {
			p = (LineDataSeq) s;
		}
		s.getHotAreas().clear();

		final double count = p.getyData().size();

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
		if ((seqSec == 0) || (seqSec >= (this.getSeq().size() - 1))) {
			if ((seqSec == 0) && (this.backStyle != null) && this.drawCircle) {
				this.backStyle.drawArc(g, toCenterX + this.x, toCenterY + this.y, radi, radi, 0, 360);
			}
			if ((seqSec == 0) && (this.backStyle != null) && !this.drawCircle) {
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
			if ((seqSec == (this.getSeq().size() - 1)) && (this.border != null)) {
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
			if ((seqSec == 0) && (this.axisFactorFont != null)) {
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
					final double sin = Math.sin(radian);
					final double cos = Math.cos(radian);
					int relativeY = (int) (sin * tmpradi);
					final int relativeX = (int) (cos * tmpradi);
					relativeY *= -1;
					if (this.axesFactors.length > i) {
						g.drawText(this.axesFactors[i], (PieCenterX + relativeX) - correction, PieCenterY + relativeY);
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
		if (p.getStyle() != null) {
			p.getStyle().drawPolygon(g, xs, ys, (int) count);
		}
		if (p.getFillStyle() != null) {
			p.getFillStyle().drawPolygon(g, xs, ys, (int) count);
		}
		int kl = 0;
		for (int i = 0; i < count; i++) {
			final Polygon po = new Polygon();
			po.addPoint(xs[i] - 3, ys[i] - 3);
			po.addPoint(xs[i] - 3, ys[i] + 3);
			po.addPoint(xs[i] + 3, ys[i] + 3);
			po.addPoint(xs[i] + 3, ys[i] - 3);
			s.getHotAreas().add(po);
			double YValue;
			if (p.isDrawPoint()) {
				AbstractChartColor c = p.getPointColor();
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
				if (p.getIcon() == null) {
					g.fillRect(xs[i] - 3, ys[i] - 3, 6, 6);
				} else {
					g.drawImage(p.getIcon(), xs[i] - 4, ys[i] - 4);
				}
			}
			if (p.getValueFont() != null) {
				g.setColor(p.getValueColor());
				g.setFont(p.getValueFont());

				YValue = ((Double) p.getElementY(i)).doubleValue();

				String txt = p.doubleToString(new Double(YValue));

				if (YValue == (int) YValue) {
					txt = new Integer((int) YValue).toString();
				}
				if (p.getLabelTemplate().length() > 0) {
					txt = p.getLabelTemplate();
				}
				if ((p.getDataLabels() != null) && (p.getDataLabels().length > i)) {
					txt = p.getDataLabels()[i];
				}
				g.drawText(txt, xs[i] + 7, ys[i]);
			}
		}
		if (this.gridStyle != null) {
			final double maxValues[] = new double[xs.length];
			final double minValues[] = new double[20];
			if (this.minScaleFactors.length >= 1) {
				for (int j = 0; j < xs.length; j++) {
					minValues[j] = this.minScaleFactors[j];
				}
			}
			if (this.maxScaleFactors.length >= 1) {
				for (int j = 0; j < xs.length; j++) {
					maxValues[j] = this.maxScaleFactors[j];
				}
			}
			final int tickInterval = 100 / this.scalingDivisions;
			final double[] tickIntervalAbsValues = new double[xs.length];

			for (int j = 0; j < xs.length; j++) {
				tickIntervalAbsValues[j] = (maxValues[j] - minValues[j]) / this.scalingDivisions;
			}

			int tickAt = 0;
			final double[] tickAtAbsValues = new double[xs.length];
			for (int j = 0; j < this.scalingDivisions; j++) {
				tickAt += tickInterval;
				for (int k = 0; k < xs.length; k++) {
					tickAtAbsValues[k] += tickIntervalAbsValues[k];
				}
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
				if (seqSec >= (this.getSeq().size() - 1)) {
					this.gridStyle.drawPolygon(g, xs, ys, (int) count);
				}
				if ((seqSec >= (this.getSeq().size() - 1)) && (this.gridFont != null)) {
					g.setColor(this.gridFontColor);
					g.setFont(this.gridFont);

					final double[] tickValues = new double[xs.length];
					final String[] values = new String[xs.length];

					for (int i = 0; i < tickValues.length; i++) {
						tickValues[i] = tickAtAbsValues[i];
						values[i] = "" + tickValues[i];

						if (this.scalingLabelFormat.length > 0) {
							final Object scalingLabel = this.scalingLabelFormat[i];
							DecimalFormat df = null;
							if (scalingLabel instanceof String) {
								df = new DecimalFormat((String) this.scalingLabelFormat[i]);
								values[i] = df.format(new Double(tickValues[i]));
							}
							if (scalingLabel instanceof Class<?>) {
								if (((Class<?>) scalingLabel).isEnum()) {
									try {
										values[i] = (String) enumConstants((Class<Enum>) scalingLabel).get(kl++);
									} catch (final Exception e) {
									}
								}
							}
						}
					}

					if (this.markScalesOnEveryAxis) {
						for (int k = 0; k < xs.length; k++) {
							g.drawText("" + values[k], xs[k] - 3 - g.getFontWidth("" + values[k]), ys[k]);
						}
					} else {
						g.drawText("" + values[0], xs[0] - 3 - g.getFontWidth("" + values[0]), ys[0]);
					}

				}
			}
		}
	}

	/**
	 * @param axesFactors
	 *            the axesFactors to set
	 */
	public void setAxesFactors(final String[] axesFactors) {
		this.axesFactors = axesFactors;
	}

	/**
	 * @param axisFactorColor
	 *            the axisFactorColor to set
	 */
	public void setAxisFactorColor(final AbstractChartColor axisFactorColor) {
		this.axisFactorColor = axisFactorColor;
	}

	/**
	 * @param axisFactorColors
	 *            the axisFactorColors to set
	 */
	public void setAxisFactorColors(final AbstractChartColor[] axisFactorColors) {
		this.axisFactorColors = axisFactorColors;
	}

	/**
	 * @param axisFactorFont
	 *            the axisFactorFont to set
	 */
	public void setAxisFactorFont(final AbstractChartFont axisFactorFont) {
		this.axisFactorFont = axisFactorFont;
	}

	/**
	 * @param backStyle
	 *            the backStyle to set
	 */
	public void setBackStyle(final FillStyle backStyle) {
		this.backStyle = backStyle;
	}

	/**
	 * @param border
	 *            the border to set
	 */
	public void setBorder(final LineStyle border) {
		this.border = border;
	}

	/**
	 * @param chartRadius
	 *            the chartRadius to set
	 */
	public void setChartRadius(final double chartRadius) {
		this.chartRadius = chartRadius;
	}

	/**
	 * @param drawCircle
	 *            the drawCircle to set
	 */
	public void setDrawCircle(final boolean drawCircle) {
		this.drawCircle = drawCircle;
	}

	/**
	 * @param gridFont
	 *            the gridFont to set
	 */
	public void setGridFont(final AbstractChartFont gridFont) {
		this.gridFont = gridFont;
	}

	/**
	 * @param gridFontColor
	 *            the gridFontColor to set
	 */
	public void setGridFontColor(final AbstractChartColor gridFontColor) {
		this.gridFontColor = gridFontColor;
	}

	/**
	 * @param gridStyle
	 *            the gridStyle to set
	 */
	public void setGridStyle(final LineStyle gridStyle) {
		this.gridStyle = gridStyle;
	}

	/**
	 * @param markScalesOnEveryAxis
	 *            the markScalesOnEveryAxis to set
	 */
	public void setMarkScalesOnEveryAxis(final boolean markScalesOnEveryAxis) {
		this.markScalesOnEveryAxis = markScalesOnEveryAxis;
	}

	/**
	 * @param maxScaleFactors
	 *            the maxScaleFactors to set
	 */
	public void setMaxScaleFactors(final double[] maxScaleFactors) {
		this.maxScaleFactors = maxScaleFactors;
	}

	/**
	 * @param minScaleFactors
	 *            the minScaleFactors to set
	 */
	public void setMinScaleFactors(final double[] minScaleFactors) {
		this.minScaleFactors = minScaleFactors;
	}

	/**
	 * @param pointColors
	 *            the pointColors to set
	 */
	public void setPointColors(final AbstractChartColor[] pointColors) {
		this.pointColors = pointColors;
	}

	/**
	 * @param pointColorScale
	 *            the pointColorScale to set
	 */
	public void setPointColorScale(final double[] pointColorScale) {
		this.pointColorScale = pointColorScale;
	}

	/**
	 * @param scalingDivisions
	 *            the scalingDivisions to set
	 */
	public void setScalingDivisions(final int scalingDivisions) {
		this.scalingDivisions = scalingDivisions;
	}

	/**
	 * @param scalingLabelFormat
	 *            the scalingLabelFormat to set
	 */
	public void setScalingLabelFormat(final Object... scalingLabelFormat) {
		this.scalingLabelFormat = Arrays.asList(scalingLabelFormat).toArray();
	}

	/** */
	public void use(final AxesConfigurer configurer) {
		requireNonNull(configurer);
		this.maxScaleFactors = configurer.maxScales();
		this.minScaleFactors = configurer.minScales();
		this.axesFactors = configurer.axesNames();
	}
}
