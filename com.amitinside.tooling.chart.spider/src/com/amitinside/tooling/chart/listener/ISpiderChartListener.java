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
import com.amitinside.tooling.chart.gc.AbstractChartGraphics;

public interface ISpiderChartListener {

	/** To be fired on clicking on chart canvas */
	public static final int EVENT_CHART_CLICKED = 6;

	/** To be fired on selecting data point */
	public static final int EVENT_ENTER_POINT = 2;

	/** To be fired on leaving data point */
	public static final int EVENT_LEAVE_POINT = 3;

	/** To be fired on clicking on data point */
	public static final int EVENT_POINT_CLICKED = 5;

	/** To be fired on tip shell update */
	public static final int EVENT_TIP_UPDATE = 4;

	/** */
	public abstract void onChartEvent(SpiderChart paramChart, int paramInt);

	/** */
	public abstract void onPaintUserExit(SpiderChart paramChart, AbstractChartGraphics paramChartGraphics);
}
