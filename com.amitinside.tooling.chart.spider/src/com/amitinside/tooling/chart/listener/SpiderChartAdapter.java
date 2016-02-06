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

public class SpiderChartAdapter implements ISpiderChartListener {

	/** {@inheritDoc}} */
	@Override
	public void chartEvent(final SpiderChart c, final int type) {
	}

	/** {@inheritDoc}} */
	@Override
	public void paintUserExit(final SpiderChart c, final AbstractChartGraphics g) {
	}
}
