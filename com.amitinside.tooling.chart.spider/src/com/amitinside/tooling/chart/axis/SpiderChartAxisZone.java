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
package com.amitinside.tooling.chart.axis;

import com.amitinside.tooling.chart.SpiderChart;
import com.amitinside.tooling.chart.gc.AbstractChartGraphics;
import com.amitinside.tooling.chart.style.FillStyle;
import com.amitinside.tooling.chart.style.LineStyle;

public class SpiderChartAxisZone {

	/** */
	protected static int DISABLED = -1;

	/** */
	protected SpiderChart chart = null;
	/** */
	public FillStyle fillStyle;
	/** */
	public String label = "";
	/** */
	public double positionEnd = DISABLED;
	/** */
	public double positionStart = DISABLED;
	/** */
	public LineStyle style = null;
	/** */
	public int unitEnd = 0;

	/** */
	public int unitStart = 0;

	/** Constructor */
	public SpiderChartAxisZone(final double start, final double end, final int uStart, final int uEnd) {
		this.unitStart = uStart;
		this.unitEnd = uEnd;
		this.positionStart = start;
		this.positionEnd = end;
	}

	/** */
	protected void paint(final AbstractChartGraphics g, final SpiderChartAxis axis, final SpiderChartAxis peerAxis, final int axisPosition) {
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
