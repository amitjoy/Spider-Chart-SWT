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

import com.amitinside.tooling.chart.api.ISpiderChartDrawable;
import com.amitinside.tooling.chart.gc.SpiderChartColor;

public class Nexus implements ISpiderChartDrawable {

	@Override
	public String areaColor() {
		return SpiderChartColor.GREEN;
	}

	@Override
	public String legend() {
		return "Nexus 6";
	}

	@Override
	public double[] values() {
		final double[] data = { 2, 3, 4, 4.2, 3 };
		return data;
	}

}
