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
package com.amitinside.tooling.chart.example;

import com.amitinside.tooling.chart.api.ISpiderChartPlottable;
import com.amitinside.tooling.chart.gc.SpiderChartColor;

public final class IPhone implements ISpiderChartPlottable {

	@Override
	public String areaColor() {
		return SpiderChartColor.DARKORCHID;
	}

	@Override
	public String legend() {
		return "iPhone";
	}

	@Override
	public double[] values() {
		final double[] data = { 4, 3.5, 4, 4.6, 5 };
		return data;
	}

}
