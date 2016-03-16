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

import org.eclipse.swt.graphics.Color;

import com.amitinside.tooling.chart.gc.AbstractChartColor;

/**
 * Represents a color to be used in SWT
 * 
 * @author AMIT KUMAR MONDAL
 *
 */
public final class SpiderChartSwtColor extends AbstractChartColor {

	/** */
	private int blue = 0;
	/** */
	private int green = 0;
	/** */
	private int red = 0;

	/** Constructor */
	public SpiderChartSwtColor(final int iRed, final int iGreen, final int iBlue) {
		this.setRGB(iRed, iGreen, iBlue);
	}

	/** Constructor */
	public SpiderChartSwtColor(final Object c) {
		if (c instanceof String) {
			this.setFromString((String) c);
		} else {
			this.red = ((Color) c).getRed();
			this.green = ((Color) c).getGreen();
			this.blue = ((Color) c).getBlue();
		}
	}

	/** {@inheritDoc}} */
	@Override
	public AbstractChartColor brighter() {
		final int percent = 10;
		final int rr = this.red;
		final int gg = this.green;
		final int bb = this.blue;
		final int r = rr + (percent * (rr / 100));
		final int g = gg + (percent * (gg / 100));
		final int b = bb + (percent * (bb / 100));
		return new SpiderChartSwtColor(Math.min(r, 255), Math.min(g, 255), Math.min(b, 255));
	}

	/** {@inheritDoc}} */
	@Override
	public AbstractChartColor darker() {
		final int percent = 10;
		final int rr = this.red;
		final int gg = this.green;
		final int bb = this.blue;
		final int r = rr - (percent * (rr / 100));
		final int g = gg - (percent * (gg / 100));
		final int b = bb - (percent * (bb / 100));

		return new SpiderChartSwtColor(Math.min(r, 255), Math.min(g, 255), Math.min(b, 255));
	}

	/** {@inheritDoc}} */
	@Override
	public int getBlue() {
		return this.blue;
	}

	/** {@inheritDoc}} */
	public Color getColor() {
		return new Color(SwtGraphicsProvider.getDisplay(), this.red, this.green, this.blue);
	}

	/** {@inheritDoc}} */
	@Override
	public int getGreen() {
		return this.green;
	}

	/** {@inheritDoc}} */
	@Override
	public int getRed() {
		return this.red;
	}

	/** String to RGB */
	private void setFromString(String c) {
		c = c.toUpperCase();
		if (c.compareTo("") == 0) {
			this.setRGB(0, 0, 0);
			return;
		}
		if (c.compareTo(RED) == 0) {
			this.setRGB(255, 0, 0);
		} else if (c.compareTo(BLACK) == 0) {
			this.setRGB(0, 0, 0);
		} else if (c.compareTo(BLUE) == 0) {
			this.setRGB(0, 0, 255);
		} else if (c.compareTo(CYAN) == 0) {
			this.setRGB(0, 255, 255);
		} else if (c.compareTo(DARKGRAY) == 0) {
			this.setRGB(70, 70, 70);
		} else if (c.compareTo(GRAY) == 0) {
			this.setRGB(128, 128, 128);
		} else if (c.compareTo(GREEN) == 0) {
			this.setRGB(0, 255, 0);
		} else if (c.compareTo(LIGHTGRAY) == 0) {
			this.setRGB(192, 192, 192);
		} else if (c.compareTo(MAGENTA) == 0) {
			this.setRGB(255, 0, 128);
		} else if (c.compareTo(ORANGE) == 0) {
			this.setRGB(255, 128, 0);
		} else if (c.compareTo(PINK) == 0) {
			this.setRGB(255, 0, 255);
		} else if (c.compareTo(WHITE) == 0) {
			this.setRGB(255, 255, 255);
		} else if (c.compareTo(YELLOW) == 0) {
			this.setRGB(255, 255, 0);
		} else if (c.compareTo(LIME) == 0) {
			this.setRGB(65280);
		} else if (c.compareTo(OLIVE) == 0) {
			this.setRGB(8421376);
		} else if (c.compareTo(MAROON) == 0) {
			this.setRGB(8388608);
		} else if (c.compareTo(NAVY) == 0) {
			this.setRGB(128);
		} else if (c.compareTo(PURPLE) == 0) {
			this.setRGB(8388736);
		} else if (c.compareTo(TELA) == 0) {
			this.setRGB(32896);
		} else if (c.compareTo(FUCHSIA) == 0) {
			this.setRGB(16711935);
		} else if (c.compareTo(AQUA) == 0) {
			this.setRGB(65535);
		} else if (c.compareTo(ALICEBLUE) == 0) {
			this.setRGB(15792383);
		} else if (c.compareTo(ANTIQUEWHITE) == 0) {
			this.setRGB(16444375);
		} else if (c.compareTo(AQUAMARINE) == 0) {
			this.setRGB(8388564);
		} else if (c.compareTo(AZURE) == 0) {
			this.setRGB(15794175);
		} else if (c.compareTo(BEIGE) == 0) {
			this.setRGB(16119260);
		} else if (c.compareTo(BLUEVIOLET) == 0) {
			this.setRGB(9055202);
		} else if (c.compareTo(BROWN) == 0) {
			this.setRGB(10824234);
		} else if (c.compareTo(BORLYWOOD) == 0) {
			this.setRGB(14596231);
		} else if (c.compareTo(CORAL) == 0) {
			this.setRGB(16744272);
		} else if (c.compareTo(CYAN) == 0) {
			this.setRGB(65535);
		} else if (c.compareTo(DARKGOLGENROD) == 0) {
			this.setRGB(12092939);
		} else if (c.compareTo(DARKGREEN) == 0) {
			this.setRGB(25600);
		} else if (c.compareTo(DARKOLIVEGREEN) == 0) {
			this.setRGB(5597999);
		} else if (c.compareTo(DARKORANGE) == 0) {
			this.setRGB(16747520);
		} else if (c.compareTo(DARKORCHID) == 0) {
			this.setRGB(10040012);
		} else if (c.compareTo(DARKSALMON) == 0) {
			this.setRGB(15308410);
		} else if (c.compareTo(DARKTURQUOISE) == 0) {
			this.setRGB(52945);
		} else if (c.compareTo(DARKVIOLET) == 0) {
			this.setRGB(9699539);
		} else if (c.compareTo(DEEPPINK) == 0) {
			this.setRGB(16716947);
		} else if (c.compareTo(DEEPSKYBLUE) == 0) {
			this.setRGB(49151);
		} else if (c.compareTo(FORESTGREEN) == 0) {
			this.setRGB(2263842);
		} else if (c.compareTo(GOLD) == 0) {
			this.setRGB(16766720);
		} else if (c.compareTo(GOLDENROD) == 0) {
			this.setRGB(14329120);
		} else if (c.compareTo(GREENYELLOW) == 0) {
			this.setRGB(11403055);
		} else if (c.compareTo(HOTPINK) == 0) {
			this.setRGB(16738740);
		} else if (c.compareTo(INDIANRED) == 0) {
			this.setRGB(13458524);
		} else if (c.compareTo(IVORY) == 0) {
			this.setRGB(16777200);
		} else if (c.compareTo(KHALI) == 0) {
			this.setRGB(15787660);
		} else if (c.compareTo(LAVENDER) == 0) {
			this.setRGB(15132410);
		} else if (c.compareTo(LAWNGREEN) == 0) {
			this.setRGB(8190976);
		} else if (c.compareTo(LIGHTBLUE) == 0) {
			this.setRGB(11393254);
		} else if (c.compareTo(LIGHTCORAL) == 0) {
			this.setRGB(15761536);
		} else if (c.compareTo(LIGHTCYAN) == 0) {
			this.setRGB(14745599);
		} else if (c.compareTo(LIGHTGRAY) == 0) {
			this.setRGB(13882323);
		} else if (c.compareTo(LIGHTPINK) == 0) {
			this.setRGB(16758465);
		} else if (c.compareTo(LIGHTSALMON) == 0) {
			this.setRGB(16752762);
		} else if (c.compareTo(LIGHTSKYBLUE) == 0) {
			this.setRGB(8900346);
		} else if (c.compareTo(LIGHTYELLOW) == 0) {
			this.setRGB(16777184);
		} else if (c.compareTo(LIMEGREEN) == 0) {
			this.setRGB(3329330);
		} else if (c.compareTo(MAGENTA) == 0) {
			this.setRGB(16711935);
		} else if (c.compareTo(MEDIUMBLUE) == 0) {
			this.setRGB(205);
		} else if (c.compareTo(MEDIUMPURPLE) == 0) {
			this.setRGB(9662683);
		} else if (c.compareTo(MIDNIGHTBLUE) == 0) {
			this.setRGB(1644912);
		} else if (c.compareTo(ORANGE) == 0) {
			this.setRGB(16753920);
		} else if (c.compareTo(ORANGERED) == 0) {
			this.setRGB(16729344);
		} else if (c.compareTo(ORCHID) == 0) {
			this.setRGB(14315734);
		} else if (c.compareTo(PALEGREEN) == 0) {
			this.setRGB(10025880);
		} else if (c.compareTo(PALETURQUOISE) == 0) {
			this.setRGB(11529966);
		} else if (c.compareTo(PALEVIOLETRED) == 0) {
			this.setRGB(14381203);
		} else if (c.compareTo(PINK) == 0) {
			this.setRGB(16761035);
		} else if (c.compareTo(PLUM) == 0) {
			this.setRGB(14524637);
		} else if (c.compareTo(PURPLE) == 0) {
			this.setRGB(10494192);
		} else if (c.compareTo(SALMON) == 0) {
			this.setRGB(16416882);
		} else if (c.compareTo(SEAGREEN) == 0) {
			this.setRGB(3050327);
		} else if (c.compareTo(SIENNA) == 0) {
			this.setRGB(10506797);
		} else if (c.compareTo(SKYBLUE) == 0) {
			this.setRGB(8900331);
		} else if (c.compareTo(SPRINGGREEN) == 0) {
			this.setRGB(65407);
		} else if (c.compareTo(TURQUOISE) == 0) {
			this.setRGB(4251856);
		} else if (c.compareTo(VIOLET) == 0) {
			this.setRGB(15631086);
		} else if (c.compareTo(YELLOWGREEN) == 0) {
			this.setRGB(10145074);
		} else {
			try {
				int rgb = 0;
				c = c.toUpperCase();
				if (c.startsWith("0X")) {
					rgb = Integer.parseInt(c.substring(2), 16);
				} else if (c.startsWith("X")) {
					rgb = Integer.parseInt(c.substring(1), 16);
				} else {
					rgb = Integer.parseInt(c);
				}
				final int r = (rgb >> 16) & 0xFF;
				final int g = (rgb >> 8) & 0xFF;
				final int b = rgb & 0xFF;
				this.setRGB(r, g, b);
			} catch (final Exception e) {
				e.printStackTrace();
				this.setRGB(0, 0, 0);
			}
		}
	}

	/** setter for RGB */
	private void setRGB(final int rgb) {
		this.red = (rgb >> 16) & 0xFF;
		this.green = (rgb >> 8) & 0xFF;
		this.blue = rgb & 0xFF;
	}

	/** setter for RGB */
	private void setRGB(final int iRed, final int iGreen, final int iBlue) {
		this.red = iRed;
		this.green = iGreen;
		this.blue = iBlue;
	}
}
