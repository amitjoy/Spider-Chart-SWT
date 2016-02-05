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

public final class Scale {

	/** */
	public boolean exactMaxValue = false;
	/** */
	public boolean exactMinValue = false;
	/** */
	public double max = -99999.0D;
	/** */
	public double min = 99999.0D;
	/** */
	public double[] preferred_MaxMin_values = { -1000000.0D, -500000.0D, -100000.0D, -50000.0D, -10000.0D, -5000.0D,
			-1000.0D, -500.0D, -250.0D, -100.0D, -50.0D, -35.0D, -5.0D, -1.0D, -0.5D, -0.1D, 0.0D, 0.1D, 0.5D, 1.0D,
			5.0D, 10.0D, 25.0D, 50.0D, 100.0D, 250.0D, 500.0D, 1000.0D, 5000.0D, 10000.0D, 50000.0D, 100000.0D,
			500000.0D, 1000000.0D };
	/** */
	public boolean reverse = false;
	/** */
	public int screenMax;
	/** */
	public int screenMaxMargin;
	/** */
	public int screenMin;

	/** Constructor */
	public Scale() {
	}

	/** Constructor */
	public Scale(final int ma, final int mi) {
		this.max = ma;
		this.min = mi;
	}

	/** */
	public int getScreenCoord(final double v) {
		double range = this.max - this.min;
		if ((this.min < 0.0D) && (this.max < 0.0D)) {
			range = (this.min - this.max) * -1.0D;
		}
		int i = this.screenMaxMargin - this.screenMin;

		i = (int) (((v - this.min) * i) / range);
		if (!this.reverse) {
			i += this.screenMin;
		} else {
			i = (this.screenMax - this.screenMin - i) + this.screenMin;
		}
		return i;
	}

	/** */
	public double getValue(int c) {
		if (!this.reverse) {
			c -= this.screenMin;
		} else {
			c = this.screenMax - c;
		}
		double range = this.max - this.min;
		if ((this.min < 0.0D) && (this.max < 0.0D)) {
			range = (this.min - this.max) * -1.0D;
		}
		double i = this.screenMaxMargin - this.screenMin;

		i = (c * range) / i;

		i += this.min;

		return i;
	}
}
