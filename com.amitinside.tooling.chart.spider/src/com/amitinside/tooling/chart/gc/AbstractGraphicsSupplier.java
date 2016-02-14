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
package com.amitinside.tooling.chart.gc;

import static com.amitinside.tooling.chart.gc.swt.SwtGraphicsProvider.startUIThread;

import com.amitinside.tooling.chart.gc.swt.SpiderChartSwtColor;
import com.amitinside.tooling.chart.gc.swt.SpiderChartSwtFont;
import com.amitinside.tooling.chart.gc.swt.SpiderChartSwtGraphics;
import com.amitinside.tooling.chart.gc.swt.SpiderChartSwtImage;

public final class AbstractGraphicsSupplier {

	/** */
	public static AbstractChartImage createImage(final int w, final int h) {
		return new SpiderChartSwtImage(w, h);
	}

	/** */
	public static AbstractChartImage createTransparentImage(final int w, final int h,
			final AbstractChartColor transparent) {
		return new SpiderChartSwtImage(w, h, transparent);
	}

	/** */
	public static AbstractChartColor getColor(final int red, final int green, final int blue) {
		return new SpiderChartSwtColor(red, green, blue);
	}

	/** */
	public static AbstractChartColor getColor(final String c) {
		return new SpiderChartSwtColor(c);
	}

	/** */
	public static AbstractChartColor getColorFromObject(final Object o) {
		return new SpiderChartSwtColor(o);
	}

	/** */
	public static AbstractChartFont getFont(final String c, final int style, final int size) {
		return new SpiderChartSwtFont(c, style, size);
	}

	/** */
	public static AbstractChartFont getFontFromObject(final Object o) {
		return new SpiderChartSwtFont(o);
	}

	/** */
	public static AbstractChartGraphics getGraphics(final Object o) {
		return new SpiderChartSwtGraphics(o);
	}

	/** */
	public static AbstractChartImage getImage(final Object o) {
		try {
			return new SpiderChartSwtImage(o);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** */
	public static void startUiThread(final Runnable r) {
		startUIThread(r);
	}

	/**
	 * Constructor
	 */
	private AbstractGraphicsSupplier() {
	}
}
