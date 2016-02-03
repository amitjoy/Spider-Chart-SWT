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

import com.amitinside.tooling.chart.gc.ChartColor;
import com.amitinside.tooling.chart.gc.ChartFont;
import com.amitinside.tooling.chart.gc.ChartGraphics;
import com.amitinside.tooling.chart.gc.GraphicsProvider;
import com.amitinside.tooling.chart.gc.Polygon;

public class SpiderPlotter extends Plotter {

	public FillStyle backStyle;
	LineStyle border = new LineStyle(0.2F, GraphicsProvider.getColor(ChartColor.BLACK), 1);
	public boolean drawCircle = false;
	public ChartColor factorColor = GraphicsProvider.getColor(ChartColor.BLACK);
	public ChartColor[] factorColors;
	public ChartFont factorFont = GraphicsProvider.getFont("Arial", ChartFont.PLAIN, 10);
	public double[] factorMaxs;
	public double[] factorMins;
	public String[] factorNames;
	public ChartFont gridFont;
	public ChartColor gridFontColor = GraphicsProvider.getColor(ChartColor.BLACK);
	public LineStyle gridStyle;
	public ChartColor[] pointColors = null;
	public double[] pointColorScale = null;
	public double radiusModifier = 0.9D;
	public String tickLabelFormat = "#.#";
	public int ticks = 5;

	public SpiderPlotter() {
		this.combinable = false;
		this.needsAxis = 0;
	}

	@Override
	protected void plotSerie(final ChartGraphics g, final DataSeq s, final int serieSec) {
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
		radi = (int) (radi * this.radiusModifier);

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
			if ((serieSec == 0) && (this.factorFont != null)) {
				g.setFont(this.factorFont);
				for (int i = 0; i < count; i++) {
					g.setColor(this.factorColor);
					if ((this.factorColors != null) && (this.factorColors.length > i)) {
						g.setColor(this.factorColors[i]);
					}
					angle = (360.0D / count) * i;

					angle += 90.0D;
					if (angle > 360.0D) {
						angle -= 360.0D;
					}
					final int tmpradi = (int) ((radi * 1.1D) / 2.0D);
					int correction = 0;
					if ((angle > 120.0D) && (angle < 240.0D)) {
						correction = g.getFontWidth(this.factorNames[i]);
					}
					final double radian = 0.01745277777777778D * angle;
					final double Sin = Math.sin(radian);
					final double Cos = Math.cos(radian);
					int relativeY = (int) (Sin * tmpradi);
					final int relativeX = (int) (Cos * tmpradi);
					relativeY *= -1;
					if (this.factorNames.length > i) {
						if (this.factorNames[i].indexOf("@") >= 0) {
							final ChartLabel label = new ChartLabel(this.factorNames[i], "", false, false);
							label.initialize(g, this.chart);
							label.paint(g, (PieCenterX + relativeX) - correction, PieCenterY + relativeY, -1, -1);
						} else {
							g.drawString(this.factorNames[i], (PieCenterX + relativeX) - correction,
									PieCenterY + relativeY);
						}
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
			if (this.factorMins.length >= (i + 1)) {
				min = this.factorMins[i];
			}
			if (this.factorMaxs.length >= (i + 1)) {
				max = this.factorMaxs[i];
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
				ChartColor c = p.pointColor;
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
				final String txtValue = txt;
				if (p.labelTemplate.length() > 0) {
					txt = p.labelTemplate;
				}
				if ((p.dataLabels != null) && (p.dataLabels.length > i)) {
					txt = p.dataLabels[i];
				}
				if (txt.indexOf("@") >= 0) {
					final ChartLabel label = new ChartLabel(txt, txtValue, false, false);
					label.initialize(g, this.chart);
					label.paint(g, xs[i] + 7, ys[i], -1, -1);
				} else {
					g.drawString(txt, xs[i] + 7, ys[i]);
				}
			}
		}
		if (this.gridStyle != null) {
			double min = 0.0D;
			double max = 100.0D;
			if (this.factorMins.length >= 1) {
				min = this.factorMins[0];
			}
			if (this.factorMaxs.length >= 1) {
				max = this.factorMaxs[0];
			}
			final int tickInterval = 100 / this.ticks;
			final double tickIntervalAbs = (max - min) / this.ticks;
			int tickAt = 0;
			double tickAtAbs = 0.0D;
			for (int j = 0; j < this.ticks; j++) {
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
					if (this.tickLabelFormat.length() > 0) {
						DecimalFormat df = null;
						df = new DecimalFormat(this.tickLabelFormat);
						v = df.format(new Double(tickValue));
					}
					g.drawString("" + v, xs[0] - 3 - g.getFontWidth("" + v), ys[0]);
				}
			}
		}
	}
}
