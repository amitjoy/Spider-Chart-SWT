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
package com.amitinside.tooling.chart;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.amitinside.tooling.chart.gc.SpiderChartColor;
import com.amitinside.tooling.chart.gc.SpiderChartFont;
import com.amitinside.tooling.chart.gc.SpiderChartImage;
import com.amitinside.tooling.chart.gc.SWTGraphicsSupplier;

public class SpiderChartLoader {

	protected static boolean convertBooleanParam(final String s, final boolean def) {
		try {
			if (s.compareTo("") == 0) {
				return def;
			}
			if (s.toUpperCase().compareTo("N") == 0) {
				return false;
			}
			if (s.toUpperCase().compareTo("NO") == 0) {
				return false;
			}
			if (s.toUpperCase().compareTo("0") == 0) {
				return false;
			}
			if (s.toUpperCase().compareTo("FALSE") == 0) {
				return false;
			}
			if (s.toUpperCase().compareTo("Y") == 0) {
				return true;
			}
			if (s.toUpperCase().compareTo("YES") == 0) {
				return true;
			}
			if (s.toUpperCase().compareTo("TRUE") == 0) {
				return true;
			}
			if (s.toUpperCase().compareTo("1") == 0) {
				return true;
			}
			return Boolean.valueOf(s).booleanValue();
		} catch (final Exception e) {
		}
		return def;
	}

	public static SpiderChartColor convertColor(final String s) {
		return SWTGraphicsSupplier.getColor(s);
	}

	protected static FillStyle convertFillStyle(final String f) {
		return FillStyle.createFromString(f);
	}

	protected static SpiderChartFont convertFont(final String f) {
		final String[] items = convertList(f, "|");
		if (items == null) {
			return null;
		}
		if (items.length < 3) {
			return null;
		}
		int s = SpiderChartFont.PLAIN;
		if (items[1].compareTo("BOLD") == 0) {
			s = SpiderChartFont.BOLD;
		}
		if (items[1].compareTo("ITALIC") == 0) {
			s = SpiderChartFont.ITALIC;
		}
		try {
			return SWTGraphicsSupplier.getFont(items[0], s, new Integer(items[2]).intValue());
		} catch (final Exception e) {
			System.out.println("Error converting font " + f + " " + e.getMessage());
		}
		return null;
	}

	protected static LineStyle convertLineStyle(final String f) {
		return LineStyle.createFromString(f);
	}

	protected static String[] convertList(final String items) {
		return convertList(items, "|");
	}

	protected static String[] convertList(String items, final String separator) {
		int elements = 1;

		int p = items.indexOf(separator);
		while (p >= 0) {
			elements++;
			p = items.indexOf(separator, p + 1);
		}
		final String[] itema = new String[elements];
		int itemCount = 0;

		p = items.indexOf(separator);
		while (p >= 0) {
			itema[itemCount++] = items.substring(0, p);
			items = items.substring(p + separator.length(), items.length());
			p = items.indexOf(separator);
		}
		if (items.compareTo("") != 0) {
			itema[itemCount++] = items;
		}
		if (itemCount == 0) {
			return null;
		}
		final String[] result = new String[itemCount];
		for (int i = 0; i < itemCount; i++) {
			result[i] = itema[i];
		}
		return result;
	}

	private SpiderChartColor c;
	private String dataFile = "";
	public String fileEncoding = "";
	public SpiderChart gChart = null;
	String[] loadedParameters = new String['ל'];
	int loadedParametersCount = 0;
	String[] loadedValues = new String['ל'];
	public boolean paintDirect = true;
	private final Object parentComponent = null;
	double pchartBottomMargin;
	double pchartLeftMargin;
	double pchartLegendMargin;
	double pchartSecondYAxisMargin;
	double pchartTopMargin;
	boolean plegend;
	String plegendPosition;
	public int pnumSeries = 0;
	public boolean promptForParameters = false;
	String pSerie;
	public DataSeq[] pSeries = new DataSeq[50];
	String[] pSeriesCloseData = new String[50];
	String[] pSeriesData = new String[50];
	String[] pSeriesMaxData = new String[50];
	String[] pSeriesMinData = new String[50];
	public String[] pSeriesNames = new String[50];
	String ptitle;
	String ptitleColor;
	String ptitleFont;
	String Xlabel;
	String XlabelColor;
	String XlabelFont;

	String Y2label;

	String Y2labelColor;

	String Y2labelFont;

	String Ylabel;

	String YlabelColor;
	String YlabelFont;

	public SpiderChartLoader() {
		this.loadedParametersCount = 0;
	}

	public SpiderChart build(final boolean clear, final boolean reReadFile) {
		return this.build(null, clear, reReadFile);
	}

	public SpiderChart build(final SpiderChart currentChart, final boolean clear, final boolean reReadFile) {
		if (clear) {
			this.loadedParametersCount = 0;
		}
		if (this.getStringParam("PAINT_DIRECT", "").compareTo("NO") == 0) {
			this.paintDirect = false;
		}
		return this.buildChart(currentChart);
	}

	private SpiderChart buildChart(final SpiderChart currentChart) {
		SpiderChartFont font = null;

		Title cTitle = null;
		HAxisLabel cXLabel = null;
		VAxisLabel cYLabel = null;
		VAxisLabel cY2Label = null;

		LinePlotter linePlot = null;

		SpiderPlotter radarPlot = null;

		Axis cXAxis = null;
		Axis cYAxis = null;

		Axis cY2Axis = null;

		final Legend clegend = new Legend();

		LineDataSeq.startingXValue = this.getIntParam("LINECHART_START_VALUE_X", new Integer(0)).intValue();

		this.ptitle = this.getStringParam("TITLECHART", "");
		if (this.ptitle.compareTo("") == 0) {
			this.ptitle = this.getStringParam("TITLE", "");
		}
		if (this.ptitle.compareTo("") != 0) {
			cTitle = new Title();
			cTitle.setText(this.ptitle);

			this.ptitleFont = this.getStringParam("TITLE_FONT", "");
			font = convertFont(this.ptitleFont);
			if (font != null) {
				cTitle.font = font;
			}
			this.ptitleColor = this.getStringParam("TITLE_COLOR", "");
			if (this.ptitleColor != null) {
				cTitle.color = convertColor(this.ptitleColor);
			}
		}
		this.Xlabel = this.getStringParam("XLABEL", "");
		if (this.Xlabel.compareTo("") != 0) {
			this.XlabelFont = this.getStringParam("XLABEL_FONT", "");
			font = convertFont(this.XlabelFont);
			if (font == null) {
				font = SWTGraphicsSupplier.getFont("Arial", SpiderChartFont.BOLD, 12);
			}
			this.XlabelColor = this.getStringParam("XLABEL_COLOR", "");
			if (this.XlabelColor != null) {
				this.c = convertColor(this.XlabelColor);
			} else {
				this.c = SWTGraphicsSupplier.getColor(SpiderChartColor.BLACK);
			}
			cXLabel = new HAxisLabel(this.Xlabel, this.c, font);
			cXLabel.vertical = this.getBooleanParam("XLABEL_VERTICAL", false);
		}
		this.Ylabel = this.getStringParam("YLABEL", "");
		if (this.Ylabel.compareTo("") != 0) {
			this.YlabelFont = this.getStringParam("YLABEL_FONT", "");
			font = convertFont(this.YlabelFont);
			if (font == null) {
				font = SWTGraphicsSupplier.getFont("Arial", SpiderChartFont.BOLD, 12);
			}
			this.YlabelColor = this.getStringParam("YLABEL_COLOR", "");
			if (this.YlabelColor != null) {
				this.c = convertColor(this.YlabelColor);
			} else {
				this.c = SWTGraphicsSupplier.getColor(SpiderChartColor.BLACK);
			}
			cYLabel = new VAxisLabel(this.Ylabel, this.c, font);
			cYLabel.vertical = this.getBooleanParam("YLABEL_VERTICAL", false);
		}
		this.Y2label = this.getStringParam("Y2LABEL", "");
		if (this.Y2label.compareTo("") != 0) {
			this.Y2labelFont = this.getStringParam("Y2LABEL_FONT", "");
			font = convertFont(this.Y2labelFont);
			if (font == null) {
				font = SWTGraphicsSupplier.getFont("Arial", SpiderChartFont.BOLD, 12);
			}
			this.Y2labelColor = this.getStringParam("Y2LABEL_COLOR", "");
			if (this.Y2labelColor != null) {
				this.c = convertColor(this.Y2labelColor);
			} else {
				this.c = SWTGraphicsSupplier.getColor(SpiderChartColor.BLACK);
			}
			cY2Label = new VAxisLabel(this.Y2label, this.c, font);
			cY2Label.vertical = this.getBooleanParam("Y2LABEL_VERTICAL", false);
		}
		final boolean axisX = this.getBooleanParam("XAXIS", true);
		final boolean axisY = this.getBooleanParam("YAXIS", true);
		final boolean axisY2 = this.getBooleanParam("Y2AXIS", false);
		if (axisX) {
			cXAxis = this.loadAxisAndScale("X", 0);
		}
		if (axisY) {
			cYAxis = this.loadAxisAndScale("Y", 1);
		}
		if (axisY2) {
			cY2Axis = this.loadAxisAndScale("Y2", 1);
		}
		FillStyle fstyle = convertFillStyle(this.getStringParam("LEGEND_FILL", ""));
		if (fstyle != null) {
			clegend.background = fstyle;
		}
		LineStyle lstyle = convertLineStyle(this.getStringParam("LEGEND_BORDER", ""));
		if (lstyle != null) {
			clegend.border = lstyle;
		}
		SpiderChartColor c = convertColor(this.getStringParam("LEGEND_COLOR", ""));
		clegend.color = c;
		font = convertFont(this.getStringParam("LEGEND_FONT", ""));
		if (font != null) {
			clegend.font = font;
		}
		clegend.verticalLayout = this.getBooleanParam("LEGEND_VERTICAL", true);

		clegend.legendLabel = this.getStringParam("LEGEND_LABEL", "");

		this.pnumSeries = 0;
		for (int i = 1; i < 50; i++) {
			this.pSerie = this.getStringParam("SERIE_" + i, "");
			if (this.pSerie.compareTo("") == 0) {
				break;
			}
			this.pSeriesNames[i] = this.pSerie;

			this.pSeriesData[i] = this.getStringParam("SERIE_DATA_" + i, "");
			if (this.pSeriesData[i].indexOf("/") > 0) {
				this.pSeriesData[i] = this.convertFromDates(this.pSeriesData[i], cYAxis.dateStep, cYAxis.initialDate);
			}
			final String Typ = this.getStringParam("SERIE_TYPE_" + i, "LINE");

			this.pSeries[i] = null;
			if ((Typ.compareTo("LINE") == 0) || (Typ.compareTo("B-SPLINES") == 0) || (Typ.compareTo("CURVE") == 0)
					|| (Typ.compareTo("LINE_LEAST_SQUARES") == 0)) {
				if (linePlot == null) {
					if (Typ.compareTo("LINE") == 0) {
						linePlot = new LinePlotter();
					}
					linePlot.XScale = cXAxis.scale;
					linePlot.YScale = cYAxis.scale;
					if (cY2Axis != null) {
						linePlot.Y2Scale = cY2Axis.scale;
					}
					linePlot.fixedLimits = this.getBooleanParam("LINECHART_FIXED_LIMITS", false);

					linePlot.pointSize = this.getIntParam("LINECHART_POINT_SIZE", new Integer(6)).intValue();

					final FillStyle backstyle = convertFillStyle(this.getStringParam("LINECHART_BACK", ""));
					if (backstyle != null) {
						linePlot.back = backstyle;
					}
				}
				lstyle = convertLineStyle(this.getStringParam("SERIE_STYLE_" + i, ""));
				if (lstyle == null) {
					lstyle = new LineStyle(0.2F, SWTGraphicsSupplier.getColor(SpiderChartColor.BLACK), 1);
				}
				final LineStyle vstyle = convertLineStyle(this.getStringParam("SERIE_V_STYLE_" + i, ""));
				if (!this.getBooleanParam("SERIE_DRAW_LINE_" + i, true)) {
					lstyle = null;
				}
				this.pSeriesMaxData[i] = this.getStringParam("SERIE_MAX_DATA_" + i, "");
				this.pSeriesMaxData[i] = this.getStringParam("SERIE_MAX_DATA_" + i, "");
				this.pSeriesMinData[i] = this.getStringParam("SERIE_MIN_DATA_" + i, "");
				this.pSeriesCloseData[i] = this.getStringParam("SERIE_CLOSE_DATA_" + i, "");

				this.pSeries[i] = null;
				if (this.pSeriesMaxData[i].length() == 0) {
					this.pSeries[i] = new LineDataSeq(this.convertDoubleListWithNulls(this.pSeriesData[i]), lstyle);
					((LineDataSeq) this.pSeries[i]).vstyle = vstyle;
				}

				if (Typ.compareTo("LINE") == 0) {
					((LineDataSeq) this.pSeries[i]).lineType = 0;
				}
				if (Typ.compareTo("B-SPLINES") == 0) {
					((LineDataSeq) this.pSeries[i]).lineType = 2;
				}
				if (Typ.compareTo("CURVE") == 0) {
					((LineDataSeq) this.pSeries[i]).lineType = 1;
				}
				if (Typ.compareTo("LINE_LEAST_SQUARES") == 0) {
					((LineDataSeq) this.pSeries[i]).lineType = 3;
				}
				String Xs = this.getStringParam("SERIE_DATAX_" + i, "");
				if (Xs.length() > 0) {
					if (Xs.indexOf("/") > 0) {
						Xs = this.convertFromDates(Xs, cXAxis.dateStep, cXAxis.initialDate);
					}
					this.pSeries[i].setDatax(this.convertDoubleList(Xs));
				}
				this.pSeries[i].secondYAxis = this.getBooleanParam("SERIE_SECONDYAXIS_" + i, false);

				this.setAxisForSerie(i, cXAxis, cYAxis, this.pSeries[i]);

				fstyle = convertFillStyle(this.getStringParam("SERIE_FILL_" + i, ""));
				if (fstyle != null) {
					((LineDataSeq) this.pSeries[i]).fillStyle = fstyle;
				}
				font = convertFont(this.getStringParam("SERIE_FONT_" + i, ""));
				if (font != null) {
					((LineDataSeq) this.pSeries[i]).valueFont = font;
				}
				c = convertColor(this.getStringParam("SERIE_COLOR_" + i, ""));
				((LineDataSeq) this.pSeries[i]).valueColor = c;

				c = convertColor(this.getStringParam("SERIE_POINT_COLOR_" + i, ""));
				((LineDataSeq) this.pSeries[i]).pointColor = c;
				((LineDataSeq) this.pSeries[i]).drawPoint = this.getBooleanParam("SERIE_POINT_" + i, false);

				SpiderChartImage im2 = null;
				final String pointImageStr = this.getStringParam("SERIE_POINT_IMAGE_" + i, "");
				if (pointImageStr.compareTo("") != 0) {
					im2 = SWTGraphicsSupplier.getImage(pointImageStr);
					((LineDataSeq) this.pSeries[i]).icon = im2;
				}
				if (!this.pSeriesNames[i].startsWith("HIDDEN")) {
					if (im2 != null) {
						clegend.addItem(this.pSeriesNames[i], im2);
					} else if (fstyle == null) {
						if (lstyle != null) {
							clegend.addItem(this.pSeriesNames[i], lstyle);
						} else {
							clegend.addItem(this.pSeriesNames[i],
									new FillStyle(((LineDataSeq) this.pSeries[i]).valueColor));
						}
					} else {
						clegend.addItem(this.pSeriesNames[i], fstyle);
					}
				}
				this.pSeries[i].name = this.pSeriesNames[i];
				linePlot.addSerie(this.pSeries[i]);
			}
			if (Typ.compareTo("RADAR") == 0) {
				if (radarPlot == null) {
					radarPlot = new SpiderPlotter();
				}
				lstyle = convertLineStyle(this.getStringParam("SERIE_STYLE_" + i, ""));
				if (lstyle == null) {
					lstyle = new LineStyle(0.2F, SWTGraphicsSupplier.getColor(SpiderChartColor.BLACK), 1);
				}
				this.pSeries[i] = null;
				this.pSeries[i] = new LineDataSeq(this.convertDoubleList(this.pSeriesData[i]), lstyle);

				fstyle = convertFillStyle(this.getStringParam("SERIE_FILL_" + i, ""));
				if (fstyle != null) {
					((LineDataSeq) this.pSeries[i]).fillStyle = fstyle;
				}
				if (fstyle == null) {
					clegend.addItem(this.pSeriesNames[i], lstyle);
				} else {
					clegend.addItem(this.pSeriesNames[i], fstyle);
				}
				font = convertFont(this.getStringParam("SERIE_FONT_" + i, ""));
				if (font != null) {
					((LineDataSeq) this.pSeries[i]).valueFont = font;
				}
				c = convertColor(this.getStringParam("SERIE_COLOR_" + i, ""));
				((LineDataSeq) this.pSeries[i]).valueColor = c;

				c = convertColor(this.getStringParam("SERIE_POINT_COLOR_" + i, ""));
				((LineDataSeq) this.pSeries[i]).pointColor = c;
				((LineDataSeq) this.pSeries[i]).drawPoint = this.getBooleanParam("SERIE_POINT_" + i, false);

				this.pSeries[i].name = this.pSeriesNames[i];
				radarPlot.addSerie(this.pSeries[i]);
			}
			if (this.pSeries[i] != null) {
				this.pSeries[i].valueFormat = this.getStringParam("SERIE_FORMAT_" + i, "#####.##");

				this.pSeries[i].labelTemplate = this.getStringParam("SERIE_LABEL_TEMPLATE_" + i, "");

				this.pSeries[i].tips = convertList(this.getStringParam("SERIE_TIPS_" + i, ""));

				this.pSeries[i].htmlLinks = convertList(this.getStringParam("SERIE_LINKS_" + i, ""));
				if (this.getStringParam("SERIE_DATA_LABELS_" + i, "").length() > 0) {
					this.pSeries[i].dataLabels = convertList(this.getStringParam("SERIE_DATA_LABELS_" + i, ""));
				}
			}
		}
		if (radarPlot != null) {
			final Double d = this.getDoubleParam("RADARCHART_RADIUS", null);
			if (d != null) {
				radarPlot.radiusModifier = d.doubleValue();
			}
			radarPlot.drawCircle = this.getBooleanParam("RADARCHART_CIRCLE", false);

			fstyle = convertFillStyle(this.getStringParam("RADARCHART_BACK", ""));
			if (fstyle != null) {
				radarPlot.backStyle = fstyle;
			}
			if (this.getStringParam("RADARCHART_POINT_COLORS_SCALE", "").length() > 0) {
				radarPlot.pointColorScale = this
						.convertDoubleList(this.getStringParam("RADARCHART_POINT_COLORS_SCALE", ""));
			}
			if (this.getStringParam("RADARCHART_POINT_COLORS", "").length() > 0) {
				final String[] listTMP = convertList(this.getStringParam("RADARCHART_POINT_COLORS", ""));

				radarPlot.pointColors = new SpiderChartColor[listTMP.length];
				for (int j = 0; j < listTMP.length; j++) {
					radarPlot.pointColors[j] = convertColor(listTMP[j]);
				}
			}
			if (this.getStringParam("RADARCHART_FACTOR_COLORS", "").length() > 0) {
				final String[] listTMP = convertList(this.getStringParam("RADARCHART_FACTOR_COLORS", ""));

				radarPlot.axisFactorColors = new SpiderChartColor[listTMP.length];
				for (int j = 0; j < listTMP.length; j++) {
					radarPlot.axisFactorColors[j] = convertColor(listTMP[j]);
				}
			}
			lstyle = convertLineStyle(this.getStringParam("RADARCHART_BORDER", ""));
			if (lstyle != null) {
				radarPlot.border = lstyle;
			}
			lstyle = convertLineStyle(this.getStringParam("RADARCHART_GRID", ""));
			if (lstyle != null) {
				radarPlot.gridStyle = lstyle;
			}
			font = convertFont(this.getStringParam("RADARCHART_GRID_FONT", ""));
			if (font != null) {
				radarPlot.gridFont = font;
			}
			radarPlot.gridFontColor = convertColor(this.getStringParam("RADARCHART_GRID_FONT_COLOR", ""));

			font = convertFont(this.getStringParam("RADARCHART_FACTOR_FONT", ""));
			if (font != null) {
				radarPlot.axisFactorFont = font;
			}
			radarPlot.axisFactorColor = convertColor(this.getStringParam("RADARCHART_FACTOR_COLOR", ""));

			String tmpList = this.getStringParam("RADARCHART_FACTOR_MAX", "");
			if (tmpList.length() > 0) {
				radarPlot.maxScaleFactors = this.convertDoubleList(tmpList);
			}
			tmpList = this.getStringParam("RADARCHART_FACTOR_MIN", "");
			if (tmpList.length() > 0) {
				radarPlot.minScaleFactors = this.convertDoubleList(tmpList);
			}
			tmpList = this.getStringParam("RADARCHART_FACTOR_NAMES", "");
			if (tmpList.length() > 0) {
				radarPlot.axesFactors = convertList(tmpList);
			}
			tmpList = this.getStringParam("RADARCHART_TICKS", "");
			if (tmpList.length() > 0) {
				radarPlot.ticks = new Integer(tmpList).intValue();
			}
			radarPlot.tickLabelFormat = this.getStringParam("RADARCHART_TICK_FORMAT", "");
		}
		Plotter plot = null;
		if (plot == null) {
			plot = linePlot;
		}
		if (plot == null) {
			plot = radarPlot;
		}
		SpiderChart chart = currentChart;
		if (chart == null) {
			chart = new SpiderChart();
		}
		chart.resetChart(cTitle, plot, cXAxis, cYAxis);
		if (plot == radarPlot) {
			chart.resetChart(cTitle, plot, null, null);
		}
		chart.showTips = this.getBooleanParam("CHART_SHOW_TIPS", false);
		chart.showPosition = this.getBooleanParam("CHART_SHOW_POSITION", false);
		chart.activateSelection = this.getBooleanParam("CHART_POINT_SELECTION", false);
		if (chart.showTips) {
			chart.activateSelection = true;
		}
		if (chart.showPosition) {
			chart.activateSelection = true;
		}
		int noteCount = 1;
		while (this.getStringParam("CHART_NOTE" + noteCount, "").length() > 0) {
			chart.addNote(this.getStringParam("CHART_NOTE" + noteCount, ""));
			noteCount++;
		}
		chart.tipColor = convertColor(this.getStringParam("CHART_TIPS_COLOR", "YELLOW"));
		chart.tipFontColor = convertColor(this.getStringParam("CHART_TIPS_FONT_COLOR", "BLACK"));
		chart.tipFont = convertFont(this.getStringParam("CHART_TIPS_FONT", "SERIF|PLAIN|10"));
		chart.htmlLinkTarget = this.getStringParam("CHART_LINKS_TARGET", "_new");

		chart.repaintAll = true;

		chart.XLabel = cXLabel;
		chart.YLabel = cYLabel;

		chart.Y2Label = cY2Label;
		if (cY2Axis != null) {
			chart.setY2Scale(cY2Axis);
		}
		if ((linePlot != null) && (linePlot != plot)) {
			chart.addPlotter(linePlot);
		}
		if (this.getBooleanParam("LEGEND", true)) {
			chart.legend = clegend;
		}
		this.plegendPosition = this.getStringParam("LEGEND_POSITION", "RIGHT");
		if (this.plegendPosition.compareTo("RIGHT") == 0) {
			chart.layout = 0;
		}
		if (this.plegendPosition.compareTo("BOTTOM") == 0) {
			chart.layout = 2;
		}
		if (this.plegendPosition.compareTo("TOP") == 0) {
			chart.layout = 1;
		}
		Double d = this.getDoubleParam("LEFT_MARGIN", null);
		if (d != null) {
			chart.leftMargin = d.doubleValue();
		}
		d = this.getDoubleParam("RIGHT_MARGIN", null);
		if (d != null) {
			chart.rightMargin = d.doubleValue();
		}
		d = this.getDoubleParam("TOP_MARGIN", null);
		if (d != null) {
			chart.topMargin = d.doubleValue();
		}
		d = this.getDoubleParam("LEGEND_MARGIN", null);
		if (d != null) {
			chart.legendMargin = d.doubleValue();
		}
		d = this.getDoubleParam("BOTTOM_MARGIN", null);
		if (d != null) {
			chart.bottomMargin = d.doubleValue();
		}
		lstyle = convertLineStyle(this.getStringParam("CHART_BORDER", ""));
		if (lstyle != null) {
			chart.border = lstyle;
		}
		fstyle = convertFillStyle(this.getStringParam("CHART_FILL", ""));
		if (fstyle != null) {
			chart.back = fstyle;
		}
		if (this.getBooleanParam("CHART_FULL_XAXIS", false)) {
			chart.fullXAxis = true;
		}
		final double tmpDouble = this.getDoubleParam("CHART_SECOND_AXIS_MARGIN", new Double(0.0D)).doubleValue();
		chart.secondYAxisMargin = tmpDouble;
		if (this.getStringParam("CHART_AXIS_MARGIN", "").length() > 0) {
			chart.axisMargin = this.getDoubleParam("CHART_AXIS_MARGIN", new Double(0.0D)).doubleValue();
		}
		final String backImageStr = this.getStringParam("BACK_IMAGE", "");
		if (backImageStr.compareTo("") != 0) {
			SpiderChartImage im2 = null;

			im2 = SWTGraphicsSupplier.getImage(backImageStr);
			chart.backImage = im2;
		}
		chart.repaintAll = true;
		chart.repaintAlways = true;

		this.gChart = chart;
		if (this.getStringParam("DOUBLE_BUFFERING", "").toUpperCase().compareTo("NO") == 0) {
			chart.doubleBuffering = false;
		}
		chart.virtualWidth = (int) this.getDoubleParam("CHART_WIDTH", new Double(0.0D)).doubleValue();
		chart.virtualHeight = (int) this.getDoubleParam("CHART_HEIGHT", new Double(0.0D)).doubleValue();

		int zoneNr = 1;
		while (this.getParameter("CHART_TARGET_ZONE_" + zoneNr, "").length() > 0) {
			chart.addTargetZone(TargetZone.createFromString(this.getParameter("CHART_TARGET_ZONE_" + zoneNr, "")));
			zoneNr++;
		}
		chart.msecs = this.getIntParam("REALTIME_MSECS", new Integer(2000)).intValue();
		chart.reloadFrom = this.getParameter("REALTIME_DATAFILE", "");
		chart.loader = this;

		return chart;
	}

	public void clearParams() {
		this.loadedParametersCount = 0;
	}

	private boolean[] convertBooleanList(final String s) {
		final String[] items = convertList(s);
		final boolean[] d = new boolean[items.length];
		for (int i = 0; i < items.length; i++) {
			try {
				d[i] = (boolean) ((items[i].compareTo("0") != 0) && (items[i].toUpperCase().compareTo("FALSE") != 0) ? 1
						: false);
			} catch (final Exception e) {
				d[i] = false;
			}
		}
		return d;
	}

	private double[] convertDoubleList(final String s) {
		final String[] items = convertList(s);
		if (items == null) {
			return null;
		}
		final double[] d = new double[items.length];
		for (int i = 0; i < items.length; i++) {
			try {
				d[i] = new Double(items[i]).doubleValue();
			} catch (final Exception e) {
				d[i] = 0.0D;
			}
		}
		return d;
	}

	private Double[] convertDoubleListWithNulls(final String s) {
		final String[] items = convertList(s);
		if (items == null) {
			return new Double[0];
		}
		final Double[] d = new Double[items.length];
		for (int i = 0; i < items.length; i++) {
			try {
				if (items[i].toUpperCase().compareTo("NULL") == 0) {
					d[i] = null;
				} else {
					d[i] = new Double(items[i]);
				}
			} catch (final Exception e) {
				d[i] = new Double(0.0D);
			}
		}
		return d;
	}

	private String convertFromDates(final String v, final String dateStep, final Date initialDate) {
		final String[] vs = convertList(v);
		String result = "";
		for (int i = 0; i < vs.length; i++) {
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			if (vs[i].length() > 10) {
				df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			}
			Date d = initialDate;

			vs[i] = this.replaceStr(vs[i], "/", "-");
			try {
				d = df.parse(vs[i]);
			} catch (final Exception e) {
				e.printStackTrace();
			}
			double diff2 = Axis.convertFromDate(d, dateStep, initialDate);
			if (i > 0) {
				result = result + "|";
			}
			diff2 = Math.floor(diff2 * 100.0D) / 100.0D;

			result = result + diff2;
		}
		return result;
	}

	private int[] convertIntList(final String s) {
		final String[] items = convertList(s);
		final int[] d = new int[items.length];
		for (int i = 0; i < items.length; i++) {
			try {
				d[i] = new Integer(items[i]).intValue();
			} catch (final Exception e) {
				d[i] = 0;
			}
		}
		return d;
	}

	public boolean getBooleanParam(final String Param, final boolean def) {
		final String s = this.getParameter(Param, "");
		return convertBooleanParam(s, def);
	}

	protected Date getDateParam(final String Param, final String def) {
		try {
			String s = this.getParameter(Param, "");
			if (s.compareTo("") == 0) {
				s = def;
			}
			if (s.compareTo("") == 0) {
				return null;
			}
			s = this.replaceStr(s, "/", "-");

			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			if (s.length() > 10) {
				df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			}
			return df.parse(s);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Double getDoubleParam(final String Param, final Double def) {
		try {
			final String s = this.getParameter(Param, "");
			if (s.compareTo("") == 0) {
				return def;
			}
			return new Double(s);
		} catch (final Exception e) {
		}
		return def;
	}

	protected Integer getIntParam(final String Param, final Integer def) {
		try {
			final String s = this.getParameter(Param, "");
			if (s.compareTo("") == 0) {
				return def;
			}
			return new Integer(s);
		} catch (final Exception e) {
		}
		return def;
	}

	private String[] getItemsParameter(final String key) {
		final String items = this.getStringParam(key, "");
		return convertList(items);
	}

	public String getLoadedParameter(final int i) {
		return this.loadedParameters[i];
	}

	public int getLoadedParametersCount() {
		return this.loadedParametersCount;
	}

	public String getLoadedValue(final int i) {
		return this.loadedValues[i];
	}

	public String getParameter(final String key, final String def) {
		String v = "";
		for (int i = 0; i < this.loadedParametersCount; i++) {
			if (this.loadedParameters[i].compareTo(key) == 0) {
				v = this.loadedValues[i];
				if (v.length() == 0) {
					return def;
				}
				return this.getParsedValue(v);
			}
		}
		if (v == null) {
			return def;
		}
		if (v.length() == 0) {
			return def;
		}
		return v;
	}

	private String getParsedValue(final String s) {
		return s;
	}

	private String getStringParam(final String Param, final String def) {
		return this.getParameter(Param, def);
	}

	private void loadAxis(final Axis axis, final String name) {
		axis.autoNumberOfTicks = this.getIntParam(name + "AXIS_AUTO_TICKS", new Integer(0)).intValue();
		if (this.getStringParam(name + "AXIS_PREF_TICK_INTERVAL", "").length() > 0) {
			axis.ticks_preferred_Interval = this
					.convertDoubleList(this.getStringParam(name + "AXIS_PREF_TICK_INTERVAL", ""));
		}
		if (this.getBooleanParam(name + "AXIS_START_WITH_BIG_TICK", false)) {
			axis.startWithBigTick = true;
		}
		axis.logarithmicIntervals = this.getBooleanParam("TICK_LOG_INTERVAL" + name, false);

		axis.axisFrame = this.getBooleanParam(name + "AXIS_CLOSED", false);
		axis.xscaleOnTop = this.getBooleanParam(name + "AXIS_ON_TOP", false);

		axis.stackAdditionalAxis = this.getBooleanParam(name + "AXIS_STACK_ADDITIONAL", false);

		axis.labelTemplate = this.getStringParam(name + "AXIS_TEMPLATE", "");

		axis.label = this.getStringParam(name + "AXIS_LABEL", "");

		axis.dateLabelFormat = this.getStringParam(name + "AXIS_DATE_FORMAT", "dd-MMM-yyyy");
		axis.dateStep = this.getStringParam(name + "AXIS_DATE_STEP", "d");
		axis.initialDate = this.getDateParam(name + "AXIS_INITIAL_DATE", "");

		final Date d = this.getDateParam(name + "AXIS_FINAL_DATE", "");
		if (d != null) {
			axis.scale.max = Axis.convertFromDate(d, axis.dateStep, axis.initialDate);
			axis.dateStepPerUnit = true;
		}
		axis.tickLabelLength = this.getIntParam(name + "AXIS_TICK_TEXT_LINE", new Integer(1000)).intValue();

		axis.tickPixels = (int) this.getDoubleParam(name + "AXIS_TICKPIXELS", new Double(axis.tickPixels))
				.doubleValue();
		axis.bigTickPixels = (int) this.getDoubleParam(name + "AXIS_BIGTICKPIXELS", new Double(axis.bigTickPixels))
				.doubleValue();
		final String ceroAxis = this.getStringParam("CERO_XAXIS", "");
		if (ceroAxis.compareTo("LINE") == 0) {
			axis.ceroAxis = 0;
		}
		if (ceroAxis.compareTo("NO") == 0) {
			axis.ceroAxis = 1;
		}
		if (ceroAxis.compareTo("SCALE") == 0) {
			axis.ceroAxis = 2;
		}
		LineStyle lstyle = convertLineStyle(this.getStringParam("CERO_XAXIS_STYLE", ""));
		if (lstyle != null) {
			axis.ceroAxisStyle = lstyle;
		}
		lstyle = convertLineStyle(this.getStringParam(name + "AXIS_STYLE", ""));
		if (lstyle != null) {
			axis.style = lstyle;
		}
		lstyle = convertLineStyle(this.getStringParam(name + "AXIS_GRID", ""));
		if (lstyle != null) {
			axis.gridStyle = lstyle;
		}
		FillStyle fstyle = convertFillStyle(this.getStringParam(name + "AXIS_FILL_GRID", ""));
		if (fstyle != null) {
			axis.gridFillStyle = fstyle;
		}
		final SpiderChartColor c = convertColor(this.getStringParam(name + "AXIS_FONT_COLOR", ""));
		axis.DescColor = c;

		final SpiderChartFont font = convertFont(this.getStringParam(name + "AXIS_FONT", ""));
		if (font != null) {
			axis.DescFont = font;
		}
		axis.IntegerScale = this.getBooleanParam(name + "AXIS_INTEGER", true);
		axis.scaleLabelFormat = this.getStringParam(name + "AXIS_LABEL_FORMAT", "");

		axis.tickAtBase = this.getBooleanParam(name + "AXIS_TICKATBASE", false);

		axis.rotateLabels = this.getIntParam(name + "AXIS_ROTATE_LABELS", new Integer(0)).intValue();
		if (this.getBooleanParam(name + "AXIS_VERTICAL_LABELS", false)) {
			axis.rotateLabels = 90;
		}
		fstyle = convertFillStyle(this.getStringParam(name + "AXIS_FILLING", ""));
		if (fstyle != null) {
			axis.barFilling = fstyle;
		}
		this.getStringParam(name + "AXIS_BAR_STYLE", "NONE");

		int zoneNr = 1;
		while (this.getParameter(name + "AXIS_TARGET_ZONE_" + zoneNr, "").length() > 0) {
			axis.addTargetZone(AxisZone.createFromString(this.getParameter(name + "AXIS_TARGET_ZONE_" + zoneNr, "")));
			zoneNr++;
		}
	}

	private Axis loadAxisAndScale(final String name, final int type) {
		Scale sc = new Scale();
		if (this.getBooleanParam(name + "SCALE_LOG", false)) {
			sc = new LogScale();
		}
		final int iTmp = this.getIntParam(name + "SCALE_LOG_BASE", new Integer(2)).intValue();
		if ((iTmp != 2) && (sc instanceof LogScale)) {
			((LogScale) sc).base = iTmp;
		}
		final Double scaleMin = this.getDoubleParam(name + "SCALE_MIN", null);
		final Double scaleMax = this.getDoubleParam(name + "SCALE_MAX", null);
		if (scaleMin != null) {
			sc.min = scaleMin.doubleValue();
		}
		if (scaleMax != null) {
			sc.max = scaleMax.doubleValue();
		}
		final boolean bigTicksGrid = this.getBooleanParam("GRID" + name, false);

		final Double scaleTickInterval = this.getDoubleParam("TICK_INTERVAL" + name, new Double(1.0D));

		final Integer bigTickInterval = this.getIntParam("BIG_TICK_INTERVAL" + name, new Integer(5));
		int[] bigTickIntervalList = null;
		if (this.getStringParam("BIG_TICK_INTERVAL_LIST" + name, "").length() > 0) {
			bigTickIntervalList = this.convertIntList(this.getStringParam("BIG_TICK_INTERVAL_LIST" + name, ""));
		}
		final String axisLabels = this.getStringParam(name + "AXIS_LABELS", "");

		sc.exactMaxValue = this.getBooleanParam(name + "SCALE_EXACT_MAX", false);
		sc.exactMinValue = this.getBooleanParam(name + "SCALE_EXACT_MIN", false);
		if (this.getStringParam(name + "SCALE_PREF_MAXMIN", "").length() > 0) {
			sc.preferred_MaxMin_values = this.convertDoubleList(this.getStringParam(name + "SCALE_PREF_MAXMIN", ""));
		}
		final Axis axis = new Axis(type, sc);

		axis.bigTickInterval = bigTickInterval.intValue();
		axis.bigTickIntervalList = bigTickIntervalList;
		axis.scaleTickInterval = scaleTickInterval.doubleValue();
		axis.bigTicksGrid = bigTicksGrid;
		if (axisLabels.compareTo("") != 0) {
			axis.tickLabels = convertList(axisLabels);
		}
		this.loadAxis(axis, name);

		int subAxis = 1;
		while (this.getBooleanParam(name + "_" + subAxis + "AXIS", false)) {
			axis.addAdditionalAxis(this.loadAxisAndScale(name + "_" + subAxis, type));
			subAxis++;
		}
		return axis;
	}

	private String replaceStr(String s, final String sub1, final String sub2) {
		int p = s.indexOf(sub1);
		while (p >= 0) {
			s = s.substring(0, p) + sub2 + s.substring(p + sub1.length(), s.length());
			p = s.indexOf(sub1);
		}
		return s;
	}

	private void setAxisForSerie(final int i, final Axis cXAxis, final Axis cYAxis, final DataSeq s) {
		int secAxis = this.getIntParam("SERIE_SECONDARY_XAXIS_" + i, new Integer(0)).intValue();
		if ((secAxis > 0) && (cXAxis.getAdditionalAxisCount() >= secAxis)) {
			s.secondaryXAxis = cXAxis.getAdditionalAxis(secAxis - 1);
		}
		secAxis = this.getIntParam("SERIE_SECONDARY_YAXIS_" + i, new Integer(0)).intValue();
		if ((secAxis > 0) && (cYAxis.getAdditionalAxisCount() >= secAxis)) {
			s.secondaryYAxis = cYAxis.getAdditionalAxis(secAxis - 1);
		}
	}

	public void setDataFile(final String df) {
		this.dataFile = df;
	}

	public void setParameter(final String param, final String value) {
		boolean alreadyFound = false;
		for (int h = 0; h < this.loadedParametersCount; h++) {
			if (this.loadedParameters[h].compareTo(param) == 0) {
				this.loadedValues[h] = value;
				alreadyFound = true;
				return;
			}
		}
		if (!alreadyFound) {
			this.loadedParameters[this.loadedParametersCount] = param;
			this.loadedValues[this.loadedParametersCount] = value;
			this.loadedParametersCount += 1;
		}
	}

}
