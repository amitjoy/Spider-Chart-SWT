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

import com.amitinside.tooling.chart.gc.swt.SpiderChartSwtColor;
import com.amitinside.tooling.chart.gc.swt.SpiderChartSwtFont;
import com.amitinside.tooling.chart.gc.swt.SpiderChartSwtGraphics;
import com.amitinside.tooling.chart.gc.swt.SpiderChartSwtImage;
import com.amitinside.tooling.chart.gc.swt.SwtGraphicsProvider;

public interface SWTGraphicsSupplier {

	/** SWT Mode */
	public static int DEFAULT_MODE = 1;

	/** */
	public static SpiderChartImage createImage(final int w, final int h) {
		return new SpiderChartSwtImage(w, h);
	}

	/** */
	public static SpiderChartImage createTransparentImage(final int w, final int h,
			final SpiderChartColor transparent) {
		return new SpiderChartSwtImage(w, h, transparent);
	}

	/** */
	public static SpiderChartColor getColor(final int red, final int green, final int blue) {
		return new SpiderChartSwtColor(red, green, blue);
	}

	/** */
	public static SpiderChartColor getColor(final String c) {
		return new SpiderChartSwtColor(c);
	}

	/** */
	public static SpiderChartColor getColorFromObject(final Object o) {
		return new SpiderChartSwtColor(o);
	}

	/** */
	public static SpiderChartFont getFont(final String c, final int style, final int size) {
		return new SpiderChartSwtFont(c, style, size);
	}

	/** */
	public static SpiderChartFont getFontFromObject(final Object o) {
		return new SpiderChartSwtFont(o);
	}

	/** */
	public static SpiderChartGraphics getGraphics(final Object o) {
		return new SpiderChartSwtGraphics(o);
	}

	/** */
	public static SpiderChartImage getImage(final Object o) {
		try {
			return new SpiderChartSwtImage(o);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** */
	public static SpiderChartImage getImageFromFile(final String file) {
		try {
			return new SpiderChartSwtImage(file);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** */
	public static int getMode() {
		return DEFAULT_MODE;
	}

	/** */
	public static void startUIThread(final Runnable r) {
		SwtGraphicsProvider.startUIThread(r);
	}
}
