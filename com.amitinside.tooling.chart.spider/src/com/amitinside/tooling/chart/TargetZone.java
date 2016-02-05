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

import com.amitinside.tooling.chart.axis.SpiderChartAxis;
import com.amitinside.tooling.chart.gc.SpiderChartGraphics;
import com.amitinside.tooling.chart.style.FillStyle;
import com.amitinside.tooling.chart.style.LineStyle;

public class TargetZone {

	/** */
	public static int DISABLED = -1;
	/** */
	public boolean background = true;
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
	/** */
	public boolean vertical = false;

	/** Constructor */
	public TargetZone(final double start, final double end, final int uStart, final int uEnd) {
		this.unitStart = uStart;
		this.unitEnd = uEnd;
		this.positionStart = start;
		this.positionEnd = end;
	}

	/** */
	protected void paint(final SpiderChartGraphics g, final SpiderChartAxis xaxis, final SpiderChartAxis yaxis) {
		int pixelStart = 0;
		SpiderChartAxis axis = yaxis;
		if (this.vertical) {
			axis = xaxis;
		}
		if (this.positionStart != DISABLED) {
			if (this.unitStart == 1) {
				final double tmp = ((axis.scale.max - axis.scale.min) * this.positionStart) / 100.0D;
				pixelStart = axis.scale.getScreenCoord(tmp);
			} else {
				if (this.positionStart < axis.scale.min) {
					this.positionStart = axis.scale.min;
				}
				if (this.positionStart > axis.scale.max) {
					this.positionStart = axis.scale.max;
				}
				pixelStart = axis.scale.getScreenCoord(this.positionStart);
			}
		}
		int pixelEnd = 0;
		if (this.positionEnd != DISABLED) {
			if (this.unitEnd == 1) {
				final double tmp = ((axis.scale.max - axis.scale.min) * this.positionEnd) / 100.0D;
				pixelEnd = axis.scale.getScreenCoord(tmp);
			} else {
				if (this.positionEnd < axis.scale.min) {
					this.positionEnd = axis.scale.min;
				}
				if (this.positionEnd > axis.scale.max) {
					this.positionEnd = axis.scale.max;
				}
				pixelEnd = axis.scale.getScreenCoord(this.positionEnd);
			}
		}
		if (this.vertical) {
			final int hStart = yaxis.y;
			final int hEnd = yaxis.y + yaxis.height;
			if ((this.fillStyle != null) && (pixelEnd >= 0)) {
				int y1 = hStart;
				int x1 = pixelStart;
				int y2 = hEnd;
				int x2 = pixelEnd;
				if (y2 < y1) {
					y2 = hStart;
					y1 = hEnd;
				}
				if (x2 < x1) {
					x1 = pixelEnd;
					x2 = pixelStart;
				}
			}
		} else {
			final int wStart = xaxis.x;
			final int wEnd = xaxis.x + xaxis.width;
			if ((this.fillStyle != null) && (pixelEnd >= 0)) {
				int x1 = wStart;
				int y1 = pixelStart;
				int x2 = wEnd;
				int y2 = pixelEnd;
				if (x2 < x1) {
					x2 = wStart;
					x1 = wEnd;
				}
				if (y2 < y1) {
					y1 = pixelEnd;
					y2 = pixelStart;
				}
			}
		}
	}
}
