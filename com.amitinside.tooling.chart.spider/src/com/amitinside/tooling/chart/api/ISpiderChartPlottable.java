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
package com.amitinside.tooling.chart.api;

import com.amitinside.tooling.chart.gc.AbstractChartColor;

/**
 * Every class object that needs to be plotted in Spider Chart must implement
 * this interface
 * 
 * @author AMIT KUMAR MONDAL
 *
 */
public interface ISpiderChartPlottable {

	/**
	 * Spider Chart Area Color
	 *
	 * @return color the name of the color
	 * @see AbstractChartColor
	 * @throws NullPointerException
	 *             if null is returned
	 */
	public abstract String areaColor();

	/**
	 * Spider Chart Legend Text
	 *
	 * @return name name of the legend to be used
	 * @throws NullPointerException
	 *             if null is returned
	 */
	public abstract String legend();

	/**
	 * Spider Chart Axis Values in Order
	 *
	 * @return values data points to be used in order of axis configuration
	 * @throws NullPointerException
	 *             if null is returned
	 */
	public abstract double[] values();

}
