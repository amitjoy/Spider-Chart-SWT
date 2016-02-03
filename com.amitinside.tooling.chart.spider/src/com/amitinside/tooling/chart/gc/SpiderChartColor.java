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

public abstract class SpiderChartColor {

	public static String ALICEBLUE = "ALICEBLUE";
	public static String ANTIQUEWHITE = "ANTIQUEWHITE";
	public static String AQUA = "AQUA";
	public static String AQUAMARINE = "AQUAMARINE";
	public static String AZURE = "AZURE";
	public static String BEIGE = "BEIGE";
	public static String BLACK = "BLACK";
	public static String BLUE = "BLUE";
	public static String BLUEVIOLET = "BLUEVIOLET";
	public static String BORLYWOOD = "BORLYWOOD";
	public static String BROWN = "BROWN";
	public static String CORAL = "CORAL";
	public static String CYAN = "CYAN";
	public static String DARKGOLGENROD = "DARKGOLGENROD";
	public static String DARKGRAY = "DARKGRAY";
	public static String DARKGREEN = "DARKGREEN";
	public static String DARKOLIVEGREEN = "DARKOLIVEGREEN";
	public static String DARKORANGE = "DARKORANGE";
	public static String DARKORCHID = "DARKORCHID";
	public static String DARKSALMON = "DARKSALMON";
	public static String DARKTURQUOISE = "DARKTURQUOISE";
	public static String DARKVIOLET = "DARKVIOLET";
	public static String DEEPPINK = "DEEPPINK";
	public static String DEEPSKYBLUE = "DEEPSKYBLUE";
	public static String FORESTGREEN = "FORESTGREEN";
	public static String FUCHSIA = "FUCHSIA";
	public static String GOLD = "GOLD";
	public static String GOLDENROD = "GOLDENROD";
	public static String GRAY = "GRAY";
	public static String GREEN = "GREEN";
	public static String GREENYELLOW = "GREENYELLOW";
	public static String HOTPINK = "HOTPINK";
	public static String INDIANRED = "INDIANRED";
	public static String IVORY = "IVORY";
	public static String KHALI = "KHALI";
	public static String LAVENDER = "LAVENDER";
	public static String LAWNGREEN = "LAWNGREEN";
	public static String LIGHTBLUE = "LIGHTBLUE";
	public static String LIGHTCORAL = "LIGHTCORAL";
	public static String LIGHTCYAN = "LIGHTCYAN";
	public static String LIGHTGRAY = "LIGHTGRAY";
	public static String LIGHTPINK = "LIGHTPINK";
	public static String LIGHTSALMON = "LIGHTSALMON";
	public static String LIGHTSKYBLUE = "LIGHTSKYBLUE";
	public static String LIGHTYELLOW = "LIGHTYELLOW";
	public static String LIME = "LIME";
	public static String LIMEGREEN = "LIMEGREEN";
	public static String MAGENTA = "MAGENTA";
	public static String MAROON = "MAROON";
	public static String MEDIUMBLUE = "MEDIUMBLUE";
	public static String MEDIUMPURPLE = "MEDIUMPURPLE";
	public static String MIDNIGHTBLUE = "MIDNIGHTBLUE";
	public static String NAVY = "NAVY";
	public static String OLIVE = "OLIVE";
	public static String ORANGE = "ORANGE";
	public static String ORANGERED = "ORANGERED";
	public static String ORCHID = "ORCHID";
	public static String PALEGREEN = "PALEGREEN";
	public static String PALETURQUOISE = "PALETURQUOISE";
	public static String PALEVIOLETRED = "PALEVIOLETRED";
	public static String PINK = "PINK";
	public static String PLUM = "PLUM";
	public static String PURPLE = "PURPLE";
	public static String RED = "RED";
	public static String SALMON = "SALMON";
	public static String SEAGREEN = "SEAGREEN";
	public static String SIENNA = "SIENNA";
	public static String SKYBLUE = "SKYBLUE";
	public static String SPRINGGREEN = "SPRINGGREEN";
	public static String TELA = "TELA";
	public static String TURQUOISE = "TURQUOISE";
	public static String VIOLET = "VIOLET";
	public static String WHITE = "WHITE";
	public static String YELLOW = "YELLOW";
	public static String YELLOWGREEN = "YELLOWGREEN";

	public SpiderChartColor brighter() {
		return null;
	}

	public SpiderChartColor darker() {
		return null;
	}

	public int getBlue() {
		return 0;
	}

	public int getGreen() {
		return 0;
	}

	public int getRed() {
		return 0;
	}

	public int getRGB() {
		return (this.getRed() * 256 * 256) + (this.getGreen() * 256) + this.getBlue();
	}

	public String getRGBString() {
		return Integer.toHexString(this.getRGB());
	}
}
