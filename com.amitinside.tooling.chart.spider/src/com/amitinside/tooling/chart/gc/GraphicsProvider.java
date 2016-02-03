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

import com.amitinside.tooling.chart.gc.swt.ChartSwtColor;
import com.amitinside.tooling.chart.gc.swt.ChartSwtFont;
import com.amitinside.tooling.chart.gc.swt.ChartSwtGraphics;
import com.amitinside.tooling.chart.gc.swt.ChartSwtImage;
import com.amitinside.tooling.chart.gc.swt.SwtGraphicsProvider;

public class GraphicsProvider {

	public static int DEFAULT_MODE = 1;
	private static int mode = DEFAULT_MODE;

	public static ChartImage createImage(final int w, final int h) {
		return new ChartSwtImage(w, h);
	}

	public static ChartImage createTransparentImage(final int w, final int h, final ChartColor transparent) {
		return new ChartSwtImage(w, h, transparent);
	}

	public static ChartColor getColor(final int red, final int green, final int blue) {
		return new ChartSwtColor(red, green, blue);
	}

	public static ChartColor getColor(final String c) {
		return new ChartSwtColor(c);
	}

	public static ChartColor getColorFromObject(final Object o) {
		return new ChartSwtColor(o);
	}

	public static ChartFont getFont(final String c, final int style, final int size) {
		return new ChartSwtFont(c, style, size);
	}

	public static ChartFont getFontFromObject(final Object o) {
		return new ChartSwtFont(o);
	}

	public static ChartGraphics getGraphics(final Object o) {
		return new ChartSwtGraphics(o);
	}

	public static ChartImage getImage(final Object o) {
		try {
			return new ChartSwtImage(o);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ChartImage getImageFromFile(final String file) {
		try {
			return new ChartSwtImage(file);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int getMode() {
		return mode;
	}

	public static void setMode(final int m) {
		mode = m;
	}

	public static void startUIThread(final Runnable r) {
		SwtGraphicsProvider.startUIThread(r);
	}
}
