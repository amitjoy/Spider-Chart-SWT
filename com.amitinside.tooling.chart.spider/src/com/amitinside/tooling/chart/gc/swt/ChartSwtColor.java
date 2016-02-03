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

import com.amitinside.tooling.chart.gc.ChartColor;

public class ChartSwtColor extends ChartColor {

	private int blue = 0;
	private int green = 0;
	private int red = 0;

	public ChartSwtColor(final int iRed, final int iGreen, final int iBlue) {
		this.setRGB(iRed, iGreen, iBlue);
	}

	public ChartSwtColor(final Object c) {
		if (c instanceof String) {
			this.setFromString((String) c);
		} else {
			this.red = ((Color) c).getRed();
			this.green = ((Color) c).getGreen();
			this.blue = ((Color) c).getBlue();
		}
	}

	@Override
	public ChartColor brighter() {
		final int percent = 10;
		final int rr = this.red;
		final int gg = this.green;
		final int bb = this.blue;
		final int r = rr + (percent * (rr / 100));
		final int g = gg + (percent * (gg / 100));
		final int b = bb + (percent * (bb / 100));
		return new ChartSwtColor(Math.min(r, 255), Math.min(g, 255), Math.min(b, 255));
	}

	@Override
	public ChartColor darker() {
		final int percent = 10;
		final int rr = this.red;
		final int gg = this.green;
		final int bb = this.blue;
		final int r = rr - (percent * (rr / 100));
		final int g = gg - (percent * (gg / 100));
		final int b = bb - (percent * (bb / 100));

		return new ChartSwtColor(Math.min(r, 255), Math.min(g, 255), Math.min(b, 255));
	}

	@Override
	public int getBlue() {
		return this.blue;
	}

	public Color getColor() {
		return new Color(SwtGraphicsProvider.getDefaultDisplay(), this.red, this.green, this.blue);
	}

	@Override
	public int getGreen() {
		return this.green;
	}

	@Override
	public int getRed() {
		return this.red;
	}

	private void setFromString(String c) {
		c = c.toUpperCase();
		if (c.compareTo("") == 0) {
			this.setRGB(0, 0, 0);
			return;
		}
		if (c.compareTo(ChartColor.RED) == 0) {
			this.setRGB(255, 0, 0);
		} else if (c.compareTo(ChartColor.BLACK) == 0) {
			this.setRGB(0, 0, 0);
		} else if (c.compareTo(ChartColor.BLUE) == 0) {
			this.setRGB(0, 0, 255);
		} else if (c.compareTo(ChartColor.CYAN) == 0) {
			this.setRGB(0, 255, 255);
		} else if (c.compareTo(ChartColor.DARKGRAY) == 0) {
			this.setRGB(70, 70, 70);
		} else if (c.compareTo(ChartColor.GRAY) == 0) {
			this.setRGB(128, 128, 128);
		} else if (c.compareTo(ChartColor.GREEN) == 0) {
			this.setRGB(0, 255, 0);
		} else if (c.compareTo(ChartColor.LIGHTGRAY) == 0) {
			this.setRGB(192, 192, 192);
		} else if (c.compareTo(ChartColor.MAGENTA) == 0) {
			this.setRGB(255, 0, 128);
		} else if (c.compareTo(ChartColor.ORANGE) == 0) {
			this.setRGB(255, 128, 0);
		} else if (c.compareTo(ChartColor.PINK) == 0) {
			this.setRGB(255, 0, 255);
		} else if (c.compareTo(ChartColor.WHITE) == 0) {
			this.setRGB(255, 255, 255);
		} else if (c.compareTo(ChartColor.YELLOW) == 0) {
			this.setRGB(255, 255, 0);
		} else if (c.compareTo(ChartColor.LIME) == 0) {
			this.setRGB(65280);
		} else if (c.compareTo(ChartColor.OLIVE) == 0) {
			this.setRGB(8421376);
		} else if (c.compareTo(ChartColor.MAROON) == 0) {
			this.setRGB(8388608);
		} else if (c.compareTo(ChartColor.NAVY) == 0) {
			this.setRGB(128);
		} else if (c.compareTo(ChartColor.PURPLE) == 0) {
			this.setRGB(8388736);
		} else if (c.compareTo(ChartColor.TELA) == 0) {
			this.setRGB(32896);
		} else if (c.compareTo(ChartColor.FUCHSIA) == 0) {
			this.setRGB(16711935);
		} else if (c.compareTo(ChartColor.AQUA) == 0) {
			this.setRGB(65535);
		} else if (c.compareTo(ChartColor.ALICEBLUE) == 0) {
			this.setRGB(15792383);
		} else if (c.compareTo(ChartColor.ANTIQUEWHITE) == 0) {
			this.setRGB(16444375);
		} else if (c.compareTo(ChartColor.AQUAMARINE) == 0) {
			this.setRGB(8388564);
		} else if (c.compareTo(ChartColor.AZURE) == 0) {
			this.setRGB(15794175);
		} else if (c.compareTo(ChartColor.BEIGE) == 0) {
			this.setRGB(16119260);
		} else if (c.compareTo(ChartColor.BLUEVIOLET) == 0) {
			this.setRGB(9055202);
		} else if (c.compareTo(ChartColor.BROWN) == 0) {
			this.setRGB(10824234);
		} else if (c.compareTo(ChartColor.BORLYWOOD) == 0) {
			this.setRGB(14596231);
		} else if (c.compareTo(ChartColor.CORAL) == 0) {
			this.setRGB(16744272);
		} else if (c.compareTo(ChartColor.CYAN) == 0) {
			this.setRGB(65535);
		} else if (c.compareTo(ChartColor.DARKGOLGENROD) == 0) {
			this.setRGB(12092939);
		} else if (c.compareTo(ChartColor.DARKGREEN) == 0) {
			this.setRGB(25600);
		} else if (c.compareTo(ChartColor.DARKOLIVEGREEN) == 0) {
			this.setRGB(5597999);
		} else if (c.compareTo(ChartColor.DARKORANGE) == 0) {
			this.setRGB(16747520);
		} else if (c.compareTo(ChartColor.DARKORCHID) == 0) {
			this.setRGB(10040012);
		} else if (c.compareTo(ChartColor.DARKSALMON) == 0) {
			this.setRGB(15308410);
		} else if (c.compareTo(ChartColor.DARKTURQUOISE) == 0) {
			this.setRGB(52945);
		} else if (c.compareTo(ChartColor.DARKVIOLET) == 0) {
			this.setRGB(9699539);
		} else if (c.compareTo(ChartColor.DEEPPINK) == 0) {
			this.setRGB(16716947);
		} else if (c.compareTo(ChartColor.DEEPSKYBLUE) == 0) {
			this.setRGB(49151);
		} else if (c.compareTo(ChartColor.FORESTGREEN) == 0) {
			this.setRGB(2263842);
		} else if (c.compareTo(ChartColor.GOLD) == 0) {
			this.setRGB(16766720);
		} else if (c.compareTo(ChartColor.GOLDENROD) == 0) {
			this.setRGB(14329120);
		} else if (c.compareTo(ChartColor.GREENYELLOW) == 0) {
			this.setRGB(11403055);
		} else if (c.compareTo(ChartColor.HOTPINK) == 0) {
			this.setRGB(16738740);
		} else if (c.compareTo(ChartColor.INDIANRED) == 0) {
			this.setRGB(13458524);
		} else if (c.compareTo(ChartColor.IVORY) == 0) {
			this.setRGB(16777200);
		} else if (c.compareTo(ChartColor.KHALI) == 0) {
			this.setRGB(15787660);
		} else if (c.compareTo(ChartColor.LAVENDER) == 0) {
			this.setRGB(15132410);
		} else if (c.compareTo(ChartColor.LAWNGREEN) == 0) {
			this.setRGB(8190976);
		} else if (c.compareTo(ChartColor.LIGHTBLUE) == 0) {
			this.setRGB(11393254);
		} else if (c.compareTo(ChartColor.LIGHTCORAL) == 0) {
			this.setRGB(15761536);
		} else if (c.compareTo(ChartColor.LIGHTCYAN) == 0) {
			this.setRGB(14745599);
		} else if (c.compareTo(ChartColor.LIGHTGRAY) == 0) {
			this.setRGB(13882323);
		} else if (c.compareTo(ChartColor.LIGHTPINK) == 0) {
			this.setRGB(16758465);
		} else if (c.compareTo(ChartColor.LIGHTSALMON) == 0) {
			this.setRGB(16752762);
		} else if (c.compareTo(ChartColor.LIGHTSKYBLUE) == 0) {
			this.setRGB(8900346);
		} else if (c.compareTo(ChartColor.LIGHTYELLOW) == 0) {
			this.setRGB(16777184);
		} else if (c.compareTo(ChartColor.LIMEGREEN) == 0) {
			this.setRGB(3329330);
		} else if (c.compareTo(ChartColor.MAGENTA) == 0) {
			this.setRGB(16711935);
		} else if (c.compareTo(ChartColor.MEDIUMBLUE) == 0) {
			this.setRGB(205);
		} else if (c.compareTo(ChartColor.MEDIUMPURPLE) == 0) {
			this.setRGB(9662683);
		} else if (c.compareTo(ChartColor.MIDNIGHTBLUE) == 0) {
			this.setRGB(1644912);
		} else if (c.compareTo(ChartColor.ORANGE) == 0) {
			this.setRGB(16753920);
		} else if (c.compareTo(ChartColor.ORANGERED) == 0) {
			this.setRGB(16729344);
		} else if (c.compareTo(ChartColor.ORCHID) == 0) {
			this.setRGB(14315734);
		} else if (c.compareTo(ChartColor.PALEGREEN) == 0) {
			this.setRGB(10025880);
		} else if (c.compareTo(ChartColor.PALETURQUOISE) == 0) {
			this.setRGB(11529966);
		} else if (c.compareTo(ChartColor.PALEVIOLETRED) == 0) {
			this.setRGB(14381203);
		} else if (c.compareTo(ChartColor.PINK) == 0) {
			this.setRGB(16761035);
		} else if (c.compareTo(ChartColor.PLUM) == 0) {
			this.setRGB(14524637);
		} else if (c.compareTo(ChartColor.PURPLE) == 0) {
			this.setRGB(10494192);
		} else if (c.compareTo(ChartColor.SALMON) == 0) {
			this.setRGB(16416882);
		} else if (c.compareTo(ChartColor.SEAGREEN) == 0) {
			this.setRGB(3050327);
		} else if (c.compareTo(ChartColor.SIENNA) == 0) {
			this.setRGB(10506797);
		} else if (c.compareTo(ChartColor.SKYBLUE) == 0) {
			this.setRGB(8900331);
		} else if (c.compareTo(ChartColor.SPRINGGREEN) == 0) {
			this.setRGB(65407);
		} else if (c.compareTo(ChartColor.TURQUOISE) == 0) {
			this.setRGB(4251856);
		} else if (c.compareTo(ChartColor.VIOLET) == 0) {
			this.setRGB(15631086);
		} else if (c.compareTo(ChartColor.YELLOWGREEN) == 0) {
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

	private void setRGB(final int rgb) {
		this.red = (rgb >> 16) & 0xFF;
		this.green = (rgb >> 8) & 0xFF;
		this.blue = rgb & 0xFF;
	}

	private void setRGB(final int iRed, final int iGreen, final int iBlue) {
		this.red = iRed;
		this.green = iGreen;
		this.blue = iBlue;
	}
}
