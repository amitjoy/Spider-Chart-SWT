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

public class LogScale extends Scale {

	public double base = 2.0D;

	public LogScale() {
	}

	public LogScale(final int ma, final int mi) {
		this.max = ma;
		this.min = mi;
	}

	@Override
	public int getScreenCoord(final double v) {
		final double logMax = Math.log(this.max) / Math.log(this.base);
		double logMin = 0.0D;
		if (this.min > 0.0D) {
			logMin = Math.log(this.min) / Math.log(this.base);
		}
		double l;
		if (v > 0.0D) {
			l = Math.log(v) / Math.log(this.base);
		} else {
			l = 0.0D;
		}
		l -= logMin;
		if (l <= 0.0D) {
			l = 0.0D;
		}
		int i = this.screenMaxMargin - this.screenMin;

		i = (int) ((l * i) / (logMax - logMin));
		if (!this.reverse) {
			i += this.screenMin;
		} else {
			i = (this.screenMax - this.screenMin - i) + this.screenMin;
		}
		return i;
	}

	@Override
	public double getValue(int c) {
		if (!this.reverse) {
			c -= this.screenMin;
		} else {
			c = this.screenMax - c;
		}
		final double logMax = Math.log(this.max) / Math.log(this.base);
		double logMin = 0.0D;
		if (this.min > 0.0D) {
			logMin = Math.log(this.min) / Math.log(this.base);
		}
		final double i = this.screenMaxMargin - this.screenMin;

		double l = (c * (logMax - logMin)) / i;

		l += logMin;

		l = Math.pow(this.base, l);

		return l;
	}
}
