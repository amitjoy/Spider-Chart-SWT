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
package com.amitinside.tooling.chart.listener;

import com.amitinside.tooling.chart.SpiderChart;
import com.amitinside.tooling.chart.gc.SpiderChartGraphics;

public interface ISpiderChartListener {

	/** */
	public static final int EVENT_AFTER_UPDATE = 1;
	/** */
	public static final int EVENT_BEFORE_UPDATE = 0;
	/** */
	public static final int EVENT_CHART_CLICKED = 6;
	/** */
	public static final int EVENT_ENTER_POINT = 2;
	/** */
	public static final int EVENT_LEAVE_POINT = 3;
	/** */
	public static final int EVENT_POINT_CLICKED = 5;
	/** */
	public static final int EVENT_TIP_UPDATE = 4;

	/** */
	public abstract void chartEvent(SpiderChart paramChart, int paramInt);

	/** */
	public abstract void paintUserExit(SpiderChart paramChart, SpiderChartGraphics paramChartGraphics);
}
