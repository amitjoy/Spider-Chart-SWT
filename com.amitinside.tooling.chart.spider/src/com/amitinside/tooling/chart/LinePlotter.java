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
import com.amitinside.tooling.chart.gc.SpiderChartGraphics;

public class LinePlotter extends SpiderChartPlotter {

	/** */
	public static final int TYPE_LINE = 0;
	/** */
	protected int[][] bottomCorners = new int[2][2];
	/** */
	public boolean fixedLimits = false;
	/** */
	public boolean hideCeros = false;
	/** */
	public int MaxMinType = 0;
	/** */
	public int pointSize = 6;

	/** */
	protected double[][] clipLines(final double[][] Points, final double MaxY, final double MinY) {
		int position = 0;
		int lastPointPosition = 0;
		final double[][] result = new double[2][Points[0].length * 2];
		int resultCount = 0;
		if (Points[0].length == 0) {
			return Points;
		}
		if (Points[1][0] < MinY) {
			lastPointPosition = 1;
		}
		if (Points[1][0] > MaxY) {
			lastPointPosition = 2;
		}
		if (lastPointPosition == 0) {
			result[0][0] = Points[0][0];
			result[1][0] = Points[1][0];
			resultCount++;
		}
		for (int i = 1; i < Points[0].length; i++) {
			position = 0;
			if (Points[1][i] < MinY) {
				position = 1;
			}
			if (Points[1][i] > MaxY) {
				position = 2;
			}
			if ((position == 0) && (lastPointPosition == 0)) {
				result[0][resultCount] = Points[0][i];
				result[1][resultCount] = Points[1][i];
				resultCount++;
			}
			if ((position != lastPointPosition) && ((position == 0) || (lastPointPosition == 0))) {
				double limit = MinY;
				if ((position == 2) || (lastPointPosition == 2)) {
					limit = MaxY;
				}
				final double slope = (Points[0][i] - Points[0][i - 1]) / (Points[1][i] - Points[1][i - 1]);

				final double newX = Points[0][i - 1] + (slope * (limit - Points[1][i - 1]));

				final double newX1 = Points[0][i];
				if ((position == 0) && (newX1 < newX)) {
					result[0][resultCount] = newX1;
					result[1][resultCount] = Points[1][i];
					resultCount++;
				}
				result[0][resultCount] = newX;
				result[1][resultCount] = limit;
				resultCount++;
				if ((position == 0) && (newX1 >= newX)) {
					result[0][resultCount] = newX1;
					result[1][resultCount] = Points[1][i];
					resultCount++;
				}
			}
			if ((position != lastPointPosition) && (position != 0) && (lastPointPosition != 0)) {
				final double slope = (Points[0][i] - Points[0][i - 1]) / (Points[1][i] - Points[1][i - 1]);

				final double newX1 = Points[0][i - 1] + (slope * (MaxY - Points[1][i - 1]));
				final double newX2 = Points[0][i - 1] + (slope * (MinY - Points[1][i - 1]));
				if (position == 2) {
					result[0][resultCount] = newX2;
					result[1][resultCount] = MinY;
					resultCount++;
					result[0][resultCount] = newX1;
					result[1][resultCount] = MaxY;
					resultCount++;
				} else {
					result[0][resultCount] = newX1;
					result[1][resultCount] = MaxY;
					resultCount++;
					result[0][resultCount] = newX2;
					result[1][resultCount] = MinY;
					resultCount++;
				}
			}
			lastPointPosition = position;
		}
		final double[][] finalP = new double[2][resultCount];
		for (int i = 0; i < resultCount; i++) {
			finalP[0][i] = result[0][i];
			finalP[1][i] = result[1][i];
		}
		return finalP;
	}

	/** */
	protected void plotCurve(final SpiderChartGraphics g, final int[][] linePointsSC, final LineDataSeq l) {
	}

	/** {@inheritDoc}} */
	@Override
	protected void plotSerie(final SpiderChartGraphics g, final DataSeq s, final int serieSec) {
		s.hotAreas.removeAllElements();
		LineDataSeq l;
		if (s instanceof LineDataSeq) {
			l = (LineDataSeq) s;
		} else {
			return;
		}
		final int count = l.getSize();

		int scX = 0;
		int scY = 0;
		boolean isNull = false;

		final Scale tmpScaleY = super.getActiveYScale(s);

		final Scale tmpScaleX = super.getActiveXScale(s);
		double XValue;
		int i;
		double YValue;
		if ((l.fillStyle != null) && (count > 1)) {
			double[][] Points = new double[2][count + 2];

			XValue = 0.0D;
			for (i = 0; i < count; i++) {
				XValue = ((Double) l.getElementX(i)).doubleValue();
				if (l.getElementY(i) == null) {
					YValue = 0.0D;
				} else {
					YValue = ((Double) l.getElementY(i)).doubleValue();
				}
				if (i == 0) {
					Points[0][0] = XValue;
					Points[1][0] = tmpScaleY.min;
				}
				Points[0][i + 1] = XValue;
				Points[1][i + 1] = YValue;
			}
			Points[0][count + 1] = XValue;
			Points[1][count + 1] = tmpScaleY.min;
			if (this.fixedLimits) {
				Points = this.clipLines(Points, tmpScaleY.max, tmpScaleY.min);
			}
			final int[][] PointsSC = new int[2][Points[0].length];
			for (i = 0; i < Points[0].length; i++) {
				PointsSC[0][i] = tmpScaleX.getScreenCoord(Points[0][i]);
				PointsSC[1][i] = tmpScaleY.getScreenCoord(Points[1][i]);
			}
			this.bottomCorners[0][0] = PointsSC[0][0];
			this.bottomCorners[1][0] = PointsSC[1][0];
			this.bottomCorners[0][1] = PointsSC[0][PointsSC[0].length - 1];
			this.bottomCorners[1][1] = PointsSC[1][PointsSC[0].length - 1];
			if (l.lineType == 0) {
				l.fillStyle.drawPolygon(g, PointsSC[0], PointsSC[1], PointsSC[0].length);
			}
		}
		double[][] linePoints = new double[2][count];
		final boolean[] nullPoints = new boolean[count];
		for (int iteration = 0; iteration < 2; iteration++) {
			for (i = 0; i < count; i++) {
				XValue = ((Double) l.getElementX(i)).doubleValue();

				isNull = false;
				YValue = 0.0D;
				if (l.getElementY(i) == null) {
					isNull = true;
				} else {
					YValue = ((Double) l.getElementY(i)).doubleValue();
				}
				if (l.fillStyle != null) {
					isNull = false;
				}
				scX = tmpScaleX.getScreenCoord(XValue);
				scY = tmpScaleY.getScreenCoord(YValue);

				nullPoints[i] = isNull;
				if ((l.style != null) && (iteration == 0)) {
					linePoints[0][i] = XValue;
					linePoints[1][i] = YValue;
				}
				if ((l.vstyle != null) && (iteration == 1)) {
					l.vstyle.draw(g, scX, tmpScaleY.getScreenCoord(tmpScaleY.min), scX, scY);
				}
				if (l.drawPoint && (iteration == 1) && !isNull && (YValue <= tmpScaleY.max)
						&& (YValue >= tmpScaleY.min)) {
					g.setColor(l.pointColor);

					final Polygon p = new Polygon();
					if (l.icon == null) {
						final int halfPoint = this.pointSize / 2;
						g.fillRect(scX - halfPoint, scY - halfPoint, this.pointSize, this.pointSize);

						p.addPoint(scX - halfPoint, scY - halfPoint);
						p.addPoint(scX - halfPoint, scY + halfPoint);
						p.addPoint(scX + halfPoint, scY + halfPoint);
						p.addPoint(scX + halfPoint, scY - halfPoint);
					} else {
						final int iX = scX - (l.icon.getWidth() / 2);
						final int iY = scY - (l.icon.getHeight() / 2);
						g.drawImage(l.icon, iX, iY);

						p.addPoint(iX, iY);
						p.addPoint(iX, iY + l.icon.getHeight());
						p.addPoint(iX + l.icon.getWidth(), iY + l.icon.getHeight());
						p.addPoint(iX + l.icon.getWidth(), iY);
					}
					l.hotAreas.addElement(p);
				}
				if ((l.valueFont != null) && (iteration == 1) && !isNull && (YValue <= tmpScaleY.max)
						&& (YValue >= tmpScaleY.min)) {
					g.setColor(l.valueColor);
					g.setFont(l.valueFont);
					String txt = l.doubleToString(new Double(YValue));
					if (this.hideCeros && (txt.compareTo("0") == 0)) {
						txt = "";
					}
					final String txtValue = txt;
					if (s.labelTemplate.length() > 0) {
						txt = s.labelTemplate;
					}
					if ((s.dataLabels != null) && (s.dataLabels.length > i)) {
						txt = s.dataLabels[i];
					}
					final SpiderChartLabel label = new SpiderChartLabel(txt, txtValue, false, false);

					label.initialize(g, this.chart);
					label.paint(g, scX + 4, scY - 4 - label.requiredHeight, -1, -1);
				}
			}
			if ((iteration == 0) && (l.style != null)) {
				if (this.fixedLimits) {
					linePoints = this.clipLines(linePoints, tmpScaleY.max, tmpScaleY.min);
				}
				final int[][] linePointsSC = new int[2][linePoints[0].length];
				for (int j = 0; j < linePoints[0].length; j++) {
					isNull = false;
					if (!this.fixedLimits && (nullPoints[j] != false)) {
						isNull = true;
					}
					if (!isNull) {
						linePointsSC[0][j] = tmpScaleX.getScreenCoord(linePoints[0][j]);
						linePointsSC[1][j] = tmpScaleY.getScreenCoord(linePoints[1][j]);
					}
				}
				if (l.lineType != 0) {
					this.plotCurve(g, linePointsSC, l);
				} else {
					for (int j = 1; j < linePoints[0].length; j++) {
						isNull = false;
						if (!this.fixedLimits && ((nullPoints[j] != false) || (nullPoints[j - 1] != false))) {
							isNull = true;
						}
						if (!isNull) {
							boolean draw = true;
							if (this.fixedLimits && (linePoints[1][j - 1] == tmpScaleY.max)
									&& (linePoints[1][j] == tmpScaleY.max)) {
								draw = false;
							}
							if (this.fixedLimits && (linePoints[1][j - 1] == tmpScaleY.min)
									&& (linePoints[1][j] == tmpScaleY.min)) {
								draw = false;
							}
							if (draw) {
								l.style.draw(g, linePointsSC[0][j - 1], linePointsSC[1][j - 1], linePointsSC[0][j],
										linePointsSC[1][j]);
							}
						}
					}
				}
			}
		}
	}
}
