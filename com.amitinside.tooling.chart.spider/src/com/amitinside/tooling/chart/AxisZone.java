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

import com.amitinside.tooling.chart.gc.ChartGraphics;

public class AxisZone {

	protected static int DISABLED = -1;

	public static AxisZone createFromString(final String s) {
		if (s.length() == 0) {
			return null;
		}
		final String[] items = ChartLoader.convertList(s, ";");
		double start = DISABLED;
		double end = DISABLED;
		int uStart = 0;
		int uEnd = 0;
		try {
			if (items.length > 0) {
				if (items[0].endsWith("%")) {
					start = new Double(items[0].substring(0, items[0].length() - 1)).doubleValue();
					uStart = 1;
				} else {
					start = new Double(items[0]).doubleValue();
				}
			}
			if (items.length > 1) {
				if (items[1].endsWith("%")) {
					end = new Double(items[1].substring(0, items[1].length() - 1)).doubleValue();
					uEnd = 1;
				} else {
					end = new Double(items[1]).doubleValue();
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
		final AxisZone zone = new AxisZone(start, end, uStart, uEnd);
		if (items.length > 2) {
			zone.style = ChartLoader.convertLineStyle(items[2]);
		}
		if (items.length > 3) {
			zone.fillStyle = ChartLoader.convertFillStyle(items[3]);
		}
		if (items.length > 4) {
			zone.label = items[4];
		}
		return zone;
	}

	protected Chart chart = null;
	public FillStyle fillStyle;
	public String label = "";
	public double positionEnd = DISABLED;
	public double positionStart = DISABLED;
	public LineStyle style = null;
	public int unitEnd = 0;

	public int unitStart = 0;

	public AxisZone(final double start, final double end, final int uStart, final int uEnd) {
		this.unitStart = uStart;
		this.unitEnd = uEnd;
		this.positionStart = start;
		this.positionEnd = end;
	}

	protected void paint(final ChartGraphics g, final Axis axis, final Axis peerAxis, final int axisPosition) {
		if (this.positionStart != DISABLED) {
			if (this.unitStart == 1) {
				final double tmp = ((axis.scale.max - axis.scale.min) * this.positionStart) / 100.0D;
				axis.scale.getScreenCoord(tmp);
			} else {
				if (this.positionStart < axis.scale.min) {
					this.positionStart = axis.scale.min;
				}
				if (this.positionStart > axis.scale.max) {
					this.positionStart = axis.scale.max;
				}
				axis.scale.getScreenCoord(this.positionStart);
			}
		}
		if (this.positionEnd != DISABLED) {
			if (this.unitEnd == 1) {
				final double tmp = ((axis.scale.max - axis.scale.min) * this.positionEnd) / 100.0D;
				axis.scale.getScreenCoord(tmp);
				if ((this.positionEnd >= 100.0D) && (axis.orientation == 0)) {
				}
				if ((this.positionEnd >= 100.0D) && (axis.orientation == 1)) {
				}
			} else {
				if (this.positionEnd < axis.scale.min) {
					this.positionEnd = axis.scale.min;
				}
				if (this.positionEnd > axis.scale.max) {
					this.positionEnd = axis.scale.max;
				}
				axis.scale.getScreenCoord(this.positionEnd);
			}
		}
	}
}
