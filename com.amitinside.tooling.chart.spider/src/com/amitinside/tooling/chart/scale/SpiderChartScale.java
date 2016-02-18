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
package com.amitinside.tooling.chart.scale;

public final class SpiderChartScale {

	/** */
	private boolean exactMaxValue = false;
	/** */
	private boolean exactMinValue = false;
	/** */
	private double max = -99999.0D;
	/** */
	private double min = 99999.0D;
	/** */
	private double[] preferred_MaxMin_values = { -1000000.0D, -500000.0D, -100000.0D, -50000.0D, -10000.0D, -5000.0D,
			-1000.0D, -500.0D, -250.0D, -100.0D, -50.0D, -35.0D, -5.0D, -1.0D, -0.5D, -0.1D, 0.0D, 0.1D, 0.5D, 1.0D,
			5.0D, 10.0D, 25.0D, 50.0D, 100.0D, 250.0D, 500.0D, 1000.0D, 5000.0D, 10000.0D, 50000.0D, 100000.0D,
			500000.0D, 1000000.0D };
	/** */
	private int screenMax;
	/** */
	private int screenMaxMargin;
	/** */
	private int screenMin;

	/** Constructor */
	public SpiderChartScale(final int max, final int min) {
		this.max = max;
		this.min = min;
	}

	/**
	 * @return the max
	 */
	public double getMax() {
		return this.max;
	}

	/**
	 * @return the min
	 */
	public double getMin() {
		return this.min;
	}

	/**
	 * @return the preferred_MaxMin_values
	 */
	public double[] getPreferred_MaxMin_values() {
		return this.preferred_MaxMin_values;
	}

	/**
	 * @return the screenMax
	 */
	public int getScreenMax() {
		return this.screenMax;
	}

	/**
	 * @return the screenMaxMargin
	 */
	public int getScreenMaxMargin() {
		return this.screenMaxMargin;
	}

	/**
	 * @return the screenMin
	 */
	public int getScreenMin() {
		return this.screenMin;
	}

	/**
	 * @return the exactMaxValue
	 */
	public boolean isExactMaxValue() {
		return this.exactMaxValue;
	}

	/**
	 * @return the exactMinValue
	 */
	public boolean isExactMinValue() {
		return this.exactMinValue;
	}

	/**
	 * @param exactMaxValue
	 *            the exactMaxValue to set
	 */
	public void setExactMaxValue(final boolean exactMaxValue) {
		this.exactMaxValue = exactMaxValue;
	}

	/**
	 * @param exactMinValue
	 *            the exactMinValue to set
	 */
	public void setExactMinValue(final boolean exactMinValue) {
		this.exactMinValue = exactMinValue;
	}

	/**
	 * @param max
	 *            the max to set
	 */
	public void setMax(final double max) {
		this.max = max;
	}

	/**
	 * @param min
	 *            the min to set
	 */
	public void setMin(final double min) {
		this.min = min;
	}

	/**
	 * @param preferred_MaxMin_values
	 *            the preferred_MaxMin_values to set
	 */
	public void setPreferred_MaxMin_values(final double[] preferred_MaxMin_values) {
		this.preferred_MaxMin_values = preferred_MaxMin_values;
	}

	/**
	 * @param screenMax
	 *            the screenMax to set
	 */
	public void setScreenMax(final int screenMax) {
		this.screenMax = screenMax;
	}

	/**
	 * @param screenMaxMargin
	 *            the screenMaxMargin to set
	 */
	public void setScreenMaxMargin(final int screenMaxMargin) {
		this.screenMaxMargin = screenMaxMargin;
	}

	/**
	 * @param screenMin
	 *            the screenMin to set
	 */
	public void setScreenMin(final int screenMin) {
		this.screenMin = screenMin;
	}

}
