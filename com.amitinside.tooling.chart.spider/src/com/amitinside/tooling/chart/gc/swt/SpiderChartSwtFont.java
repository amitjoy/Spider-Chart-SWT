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
package com.amitinside.tooling.chart.gc.swt;

import org.eclipse.swt.graphics.Font;

import com.amitinside.tooling.chart.gc.SpiderChartFont;

public final class SpiderChartSwtFont extends SpiderChartFont {

	/** */
	private String fontName = "";
	/** */
	private int fontSize = 10;
	/** */
	private int fontStyle = SpiderChartFont.PLAIN;

	/** Constructor */
	public SpiderChartSwtFont(final Object f) {
		this.fontName = ((Font) f).getFontData()[0].getName();
		final int s = ((Font) f).getFontData()[0].getStyle();
		this.fontStyle = SpiderChartFont.PLAIN;
		if ((s & 0x1) == 1) {
			this.fontStyle = SpiderChartFont.BOLD;
		}
		if ((s & 0x2) == 2) {
			this.fontStyle = SpiderChartFont.ITALIC;
		}
		if ((s & 0x3) == 3) {
			this.fontStyle = SpiderChartFont.BOLD_ITALIC;
		}
		this.fontSize = ((Font) f).getFontData()[0].getHeight();
	}

	/** Constructor */
	public SpiderChartSwtFont(final String name, final int style, final int size) {
		this.fontName = name;
		this.fontSize = size;
		this.fontStyle = style;
	}

	/** */
	protected Font getFont() {
		int s = 0;
		if (this.fontStyle == SpiderChartFont.BOLD) {
			s = 1;
		}
		if (this.fontStyle == SpiderChartFont.ITALIC) {
			s = 2;
		}
		if (this.fontStyle == SpiderChartFont.BOLD_ITALIC) {
			s = 3;
		}
		return new Font(SwtGraphicsProvider.getDefaultDisplay(), this.fontName, this.fontSize, s);
	}
}
