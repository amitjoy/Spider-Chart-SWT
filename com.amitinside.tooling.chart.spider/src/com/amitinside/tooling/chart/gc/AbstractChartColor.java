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

public abstract class AbstractChartColor {

	public static final String ALICEBLUE = "ALICEBLUE";
	public static final String ANTIQUEWHITE = "ANTIQUEWHITE";
	public static final String AQUA = "AQUA";
	public static final String AQUAMARINE = "AQUAMARINE";
	public static final String AZURE = "AZURE";
	public static final String BEIGE = "BEIGE";
	public static final String BLACK = "BLACK";
	public static final String BLUE = "BLUE";
	public static final String BLUEVIOLET = "BLUEVIOLET";
	public static final String BORLYWOOD = "BORLYWOOD";
	public static final String BROWN = "BROWN";
	public static final String CORAL = "CORAL";
	public static final String CYAN = "CYAN";
	public static final String DARKGOLGENROD = "DARKGOLGENROD";
	public static final String DARKGRAY = "DARKGRAY";
	public static final String DARKGREEN = "DARKGREEN";
	public static final String DARKOLIVEGREEN = "DARKOLIVEGREEN";
	public static final String DARKORANGE = "DARKORANGE";
	public static final String DARKORCHID = "DARKORCHID";
	public static final String DARKSALMON = "DARKSALMON";
	public static final String DARKTURQUOISE = "DARKTURQUOISE";
	public static final String DARKVIOLET = "DARKVIOLET";
	public static final String DEEPPINK = "DEEPPINK";
	public static final String DEEPSKYBLUE = "DEEPSKYBLUE";
	public static final String FORESTGREEN = "FORESTGREEN";
	public static final String FUCHSIA = "FUCHSIA";
	public static final String GOLD = "GOLD";
	public static final String GOLDENROD = "GOLDENROD";
	public static final String GRAY = "GRAY";
	public static final String GREEN = "GREEN";
	public static final String GREENYELLOW = "GREENYELLOW";
	public static final String HOTPINK = "HOTPINK";
	public static final String INDIANRED = "INDIANRED";
	public static final String IVORY = "IVORY";
	public static final String KHALI = "KHALI";
	public static final String LAVENDER = "LAVENDER";
	public static final String LAWNGREEN = "LAWNGREEN";
	public static final String LIGHTBLUE = "LIGHTBLUE";
	public static final String LIGHTCORAL = "LIGHTCORAL";
	public static final String LIGHTCYAN = "LIGHTCYAN";
	public static final String LIGHTGRAY = "LIGHTGRAY";
	public static final String LIGHTPINK = "LIGHTPINK";
	public static final String LIGHTSALMON = "LIGHTSALMON";
	public static final String LIGHTSKYBLUE = "LIGHTSKYBLUE";
	public static final String LIGHTYELLOW = "LIGHTYELLOW";
	public static final String LIME = "LIME";
	public static final String LIMEGREEN = "LIMEGREEN";
	public static final String MAGENTA = "MAGENTA";
	public static final String MAROON = "MAROON";
	public static final String MEDIUMBLUE = "MEDIUMBLUE";
	public static final String MEDIUMPURPLE = "MEDIUMPURPLE";
	public static final String MIDNIGHTBLUE = "MIDNIGHTBLUE";
	public static final String NAVY = "NAVY";
	public static final String OLIVE = "OLIVE";
	public static final String ORANGE = "ORANGE";
	public static final String ORANGERED = "ORANGERED";
	public static final String ORCHID = "ORCHID";
	public static final String PALEGREEN = "PALEGREEN";
	public static final String PALETURQUOISE = "PALETURQUOISE";
	public static final String PALEVIOLETRED = "PALEVIOLETRED";
	public static final String PINK = "PINK";
	public static final String PLUM = "PLUM";
	public static final String PURPLE = "PURPLE";
	public static final String RED = "RED";
	public static final String SALMON = "SALMON";
	public static final String SEAGREEN = "SEAGREEN";
	public static final String SIENNA = "SIENNA";
	public static final String SKYBLUE = "SKYBLUE";
	public static final String SPRINGGREEN = "SPRINGGREEN";
	public static final String TELA = "TELA";
	public static final String TURQUOISE = "TURQUOISE";
	public static final String VIOLET = "VIOLET";
	public static final String WHITE = "WHITE";
	public static final String YELLOW = "YELLOW";
	public static final String YELLOWGREEN = "YELLOWGREEN";

	/** */
	public AbstractChartColor brighter() {
		return null;
	}

	/** */
	public AbstractChartColor darker() {
		return null;
	}

	/** */
	public int getBlue() {
		return 0;
	}

	/** */
	public int getGreen() {
		return 0;
	}

	/** */
	public int getRed() {
		return 0;
	}

	/** */
	public int getRGB() {
		return (this.getRed() * 256 * 256) + (this.getGreen() * 256) + this.getBlue();
	}

	/** */
	public String getRGBString() {
		return Integer.toHexString(this.getRGB());
	}
}
