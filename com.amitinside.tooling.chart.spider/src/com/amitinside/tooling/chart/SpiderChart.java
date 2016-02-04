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

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;

import com.amitinside.tooling.chart.gc.Polygon;
import com.amitinside.tooling.chart.gc.SWTGraphicsSupplier;
import com.amitinside.tooling.chart.gc.SpiderChartColor;
import com.amitinside.tooling.chart.gc.SpiderChartFont;
import com.amitinside.tooling.chart.gc.SpiderChartGraphics;
import com.amitinside.tooling.chart.gc.SpiderChartImage;

/**
 * Actual Spider Chart
 *
 * @author AMIT KUMAR MONDAL
 *
 */
public class SpiderChart {

	private class SpiderChartWorker implements Runnable {

		/** */
		public SpiderChart chart = null;

		/** */
		public boolean stop = false;

		/** Constructor */
		private SpiderChartWorker() {
		}

		/** Constructor */
		SpiderChartWorker(final Object object) {
			this();
		}

		/** {@inheritDoc} */
		@Override
		public void run() {
			while (!this.stop) {
				try {
					Thread.sleep(SpiderChart.this.msecs);
				} catch (final Exception e) {
					e.printStackTrace();
				}
				if (this.stop) {
					break;
				}
			}
		}
	}

	/** */
	public static final int dnum = 10;
	/** */
	public static final int LAYOUT_LEGEND_BOTTOM = 2;
	/** */
	public static final int LAYOUT_LEGEND_RIGHT = 0;
	/** */
	public static final int LAYOUT_LEGEND_TOP = 1;
	/** */
	protected static final int MAX_SERIES = 50;
	/** */
	public static String numberLocale;

	/** */
	protected static int d() {
		return 0;
	}

	/** */
	public boolean activateSelection = false;

	/** Used to trigger thread automatically to build the Spider Chart */
	public boolean autoRebuild = true;

	/** Auto Sizeable Property */
	public boolean autoSize = true;

	/** */
	public double axisMargin = 0.0625D;

	/** */
	public FillStyle back = null;

	/** Spider Chart Back Image */
	public SpiderChartImage backImage;

	/** */
	private SpiderChartImage backTmpImage = null;

	/**  */
	public LineStyle border = null;

	/** */
	public double bottomMargin = 0.125D;

	/** */
	protected Vector<Object> chartHotAreas = new Vector<>(0, 5);

	/** Spider Chart Image */
	private SpiderChartImage chartImage = null;

	/** */
	private final Vector<ISpiderChartListener> chartListeners = new Vector<>();

	/** */
	public double currentValueX;

	/** */
	public double currentValueY;

	/** */
	public double currentValueY2;

	/** */
	public int currentX;

	/** */
	public int currentY;

	/** */
	private int cursorLastX = 0;

	/** */
	private int cursorLastY = 0;

	/** */
	private SpiderChartWorker deamon = null;

	/** */
	public boolean doubleBuffering = true;

	/** */
	private SpiderChartImage finalImage = null;

	/** */
	protected Vector<IFloatingObject> floatingObjects = new Vector<>(0, 5);

	/** */
	public boolean fullXAxis = false;

	/** */
	private int height = 0;

	/** */
	private int lastHeight = -1;

	/** */
	private int lastWidth = -1;

	/** */
	public int layout = 0;

	/** Chart Left Margin */
	public double leftMargin = 0.125D;

	/** Spider Chart Legend */
	public SpiderChartLegend legend;

	/** Spider Chart Legend Margin */
	public double legendMargin = 0.2D;

	/** */
	private int minimumHeight = 0;

	/** */
	private int minimumWidth = 0;

	/** */
	public long msecs = 2000L;

	/** */
	protected Vector<String> notes = new Vector<>();

	/** */
	public int offsetX = 0;

	/** */
	public int offsetY = 0;

	/** */
	private int originalVirtualHeight = -1;

	/** */
	private int originalVirtualWidth = -1;

	/** */
	public SpiderChartPlotter[] plotters = new SpiderChartPlotter[10];

	/** */
	private int plottersCount = 0;

	/** */
	public String reloadFrom = "";

	/** */
	public boolean repaintAll = true;

	/** */
	public boolean repaintAlways = true;

	/** */
	public double rightMargin = 0.125D;

	/** */
	public double secondYAxisMargin = 0.0D;

	/** */
	public SpiderChartLabel selectedLabel = null;

	/** */
	public DataSeq selectedSerie = null;

	/** */
	public int selectedSeriePoint = -1;

	/** */
	private boolean showingTip = false;

	/** */
	public boolean showPosition = false;

	/** Show Tips on the Spider Chart Points */
	public boolean showTips = false;

	/** */
	private boolean stopped = false;

	/** */
	protected Vector<TargetZone> targetZones = new Vector<>();

	/** Spider Chart Tip Background Color */
	SpiderChartColor tipColor = SWTGraphicsSupplier.getColor(SpiderChartColor.YELLOW);

	/** Spider Chart Tip Font */
	SpiderChartFont tipFont = SWTGraphicsSupplier.getFont("Serif", SpiderChartFont.PLAIN, 10);

	/** Spider Chart Tip Font Color */
	SpiderChartColor tipFontColor = SWTGraphicsSupplier.getColor(SpiderChartColor.BLACK);

	/** Spider Chart Title */
	public SpiderChartTitle title;

	/** */
	public double topMargin = 0.125D;

	/** */
	public int virtualHeight = 0;

	/** */
	public int virtualWidth = 0;

	/** */
	private int width = 0;

	/** Scrollable Property */
	public boolean withScroll = false;

	/** */
	public SpiderChartAxis XAxis;

	/** */
	public HAxisLabel XLabel;

	/** */
	public SpiderChartAxis Y2Axis;

	/** */
	public SpiderChartAxis YAxis;

	/**
	 * Constructor
	 */
	protected SpiderChart() {
	}

	/** Constructor */
	public SpiderChart(final SpiderChartTitle t, final SpiderChartPlotter p) {
		this.resetChart(t, p, null, null);
	}

	/**
	 * Constructor
	 */
	public SpiderChart(final SpiderChartTitle t, final SpiderChartPlotter p, final SpiderChartAxis X, final SpiderChartAxis Y) {
		this.resetChart(t, p, X, Y);
	}

	/** */
	public void addChartListener(final ISpiderChartListener cl) {
		this.chartListeners.addElement(cl);
	}

	/** */
	protected void addFloationgObject(final IFloatingObject obj) {
		this.floatingObjects.addElement(obj);
	}

	/** */
	public void addNote(final String note) {
		this.notes.addElement(note);
	}

	/** */
	public void addPlotter(final SpiderChartPlotter p) {
		this.plotters[this.plottersCount] = p;
		this.plotters[this.plottersCount].XScale = this.plotters[0].XScale;
		this.plotters[this.plottersCount].YScale = this.plotters[0].YScale;
		this.plotters[this.plottersCount].Y2Scale = this.plotters[0].Y2Scale;
		this.plottersCount += 1;
	}

	/** */
	public void addSeq(final DataSeq s) {
		this.plotters[0].addSeq(s);
	}

	/** */
	public void addTargetZone(final TargetZone zone) {
		this.targetZones.addElement(zone);
	}

	/** */
	private void autoSize() {
		if (this.layout == 0) {
			this.autoSize_LayoutRight();
		}
		if (this.layout == 1) {
			this.autoSize_LayoutTop();
		}
		if (this.layout == 2) {
			this.autoSize_LayoutBottom();
		}
	}

	/** */
	private void autoSize_LayoutBottom() {
		final int myHeight = this.getHeight();
		final int myWidth = this.getWidth();
		if (this.virtualWidth < myWidth) {
			this.virtualWidth = myWidth;
		}
		if (this.virtualHeight < myHeight) {
			this.virtualHeight = myHeight;
		}
		this.plotters[0].visibleWidth = (int) (myWidth * (1.0D - (this.leftMargin + this.rightMargin)));
		this.plotters[0].visibleHeight = (int) (myHeight
				* (1.0D - (this.topMargin + this.bottomMargin + this.legendMargin)));

		this.plotters[0].x = (int) (myWidth * this.leftMargin);
		this.plotters[0].y = (int) (myHeight * this.topMargin);
		this.plotters[0].width = this.virtualWidth - (myWidth - this.plotters[0].visibleWidth);
		this.plotters[0].height = this.virtualHeight - (myHeight - this.plotters[0].visibleHeight);

		this.title.x = 0;
		this.title.y = 0;
		if ((this.XAxis != null) && this.XAxis.xscaleOnTop) {
			this.title.y = this.virtualHeight - (int) (myHeight * (this.bottomMargin + this.legendMargin));
		}
		this.title.height = (int) (myHeight * this.topMargin);
		this.title.width = myWidth;
		if (this.XAxis != null) {
			this.XAxis.x = (int) (myWidth * this.leftMargin);
			this.XAxis.y = this.virtualHeight - (int) (myHeight * (this.bottomMargin + this.legendMargin));
			this.XAxis.realPosition = (int) (myHeight * (1.0D - this.bottomMargin - this.legendMargin));
			this.XAxis.height = (int) (myHeight * (this.bottomMargin / (2 + this.getCountParallelAxis(this.XAxis))));
			this.XAxis.visibleSize = (int) (myWidth * (1.0D - (this.leftMargin + this.rightMargin)));

			this.XAxis.width = this.virtualWidth - (myWidth - this.XAxis.visibleSize);
		}
		if (this.YAxis != null) {
			this.YAxis.width = (int) (myWidth * (this.leftMargin / (2 + this.getCountParallelAxis(this.YAxis))));
			this.YAxis.x = (int) (myWidth * this.leftMargin) - this.YAxis.width;
			this.YAxis.realPosition = this.YAxis.x;
			this.YAxis.y = (int) (myHeight * this.topMargin);
			this.YAxis.visibleSize = (int) (myHeight
					* (1.0D - (this.topMargin + this.bottomMargin + this.legendMargin)));
			this.YAxis.height = this.virtualHeight - (myHeight - this.YAxis.visibleSize);
		}
		if (this.XLabel != null) {
			final int tmp = 2 + this.getCountParallelAxis(this.XAxis);
			this.XLabel.x = (int) (myWidth * this.leftMargin);
			this.XLabel.y = (int) (myHeight * (1.0D - this.legendMargin))
					- (int) (myHeight * (this.bottomMargin / tmp));
			if ((this.XAxis != null) && this.XAxis.xscaleOnTop) {
				this.XLabel.y = 0;
			}
			this.XLabel.height = (int) (myHeight * (this.bottomMargin / (2 + this.getCountParallelAxis(this.XAxis))));
			this.XLabel.width = (int) (myWidth * (1.0D - (this.leftMargin + this.leftMargin)));
		}
		if (this.Y2Axis != null) {
			this.plotters[0].width = (int) (this.plotters[0].width - ((myWidth * this.secondYAxisMargin) / 2.0D));
		}
		if (this.Y2Axis != null) {
			this.Y2Axis.x = this.plotters[0].x + this.plotters[0].width;
			this.Y2Axis.realPosition = this.plotters[0].x + this.plotters[0].visibleWidth;
			this.Y2Axis.y = (int) (myHeight * this.topMargin);
			this.Y2Axis.visibleSize = (int) (myHeight
					* (1.0D - (this.topMargin + this.bottomMargin + this.legendMargin)));
			this.Y2Axis.width = (int) ((myWidth * this.rightMargin) / (2 + this.getCountParallelAxis(this.Y2Axis)));
			this.Y2Axis.height = this.virtualHeight - (myHeight - this.Y2Axis.visibleSize);
		}
		if (this.legend != null) {
			this.legend.x = (int) (myWidth * this.leftMargin);
			this.legend.width = (int) (myWidth * (1.0D - (this.leftMargin + this.leftMargin)));
			this.legend.y = (int) (myHeight * (1.0D - this.legendMargin));
			this.legend.height = (int) (myHeight * this.legendMargin);
		}
		this.setPlotterSize();
	}

	/** */
	private void autoSize_LayoutRight() {
		final int myHeight = this.getHeight();
		final int myWidth = this.getWidth();
		if (this.virtualWidth < myWidth) {
			this.virtualWidth = myWidth;
		}
		if (this.virtualHeight < myHeight) {
			this.virtualHeight = myHeight;
		}
		this.plotters[0].visibleWidth = (int) (myWidth * (1.0D - (this.legendMargin + this.leftMargin)));
		if (this.Y2Axis != null) {
			this.plotters[0].visibleWidth = (int) (myWidth
					* (1.0D - (this.legendMargin + this.rightMargin + this.leftMargin)));
		}
		this.plotters[0].visibleHeight = (int) (myHeight * (1.0D - (this.topMargin + this.bottomMargin)));

		this.plotters[0].x = (int) (myWidth * this.leftMargin);
		this.plotters[0].y = (int) (myHeight * this.topMargin);
		this.plotters[0].width = this.virtualWidth - (myWidth - this.plotters[0].visibleWidth);
		this.plotters[0].height = this.virtualHeight - (myHeight - this.plotters[0].visibleHeight);

		this.title.x = 0;
		this.title.y = 0;
		if ((this.XAxis != null) && this.XAxis.xscaleOnTop) {
			this.title.y = this.virtualHeight - (int) (myHeight * this.bottomMargin);
		}
		this.title.height = (int) (myHeight * this.topMargin);
		this.title.width = myWidth;
		if (this.XAxis != null) {
			this.XAxis.x = (int) (myWidth * this.leftMargin);
			this.XAxis.y = this.virtualHeight - (int) (myHeight * this.bottomMargin);
			this.XAxis.realPosition = (int) (myHeight * (1.0D - this.bottomMargin));
			this.XAxis.height = (int) (myHeight * (this.bottomMargin / (2 + this.getCountParallelAxis(this.XAxis))));
			this.XAxis.visibleSize = this.plotters[0].visibleWidth;

			this.XAxis.width = this.virtualWidth - (myWidth - this.XAxis.visibleSize);
		}
		if (this.YAxis != null) {
			this.YAxis.width = (int) (myWidth * (this.leftMargin / (2 + this.getCountParallelAxis(this.YAxis))));
			this.YAxis.x = (int) (myWidth * this.leftMargin) - this.YAxis.width;
			this.YAxis.realPosition = this.YAxis.x;
			this.YAxis.y = (int) (myHeight * this.topMargin);
			this.YAxis.visibleSize = (int) (myHeight * (1.0D - (this.topMargin + this.bottomMargin)));
			this.YAxis.height = this.virtualHeight - (myHeight - this.YAxis.visibleSize);
		}
		if (this.XLabel != null) {
			this.XLabel.height = (int) (myHeight * (this.bottomMargin / (2 + this.getCountParallelAxis(this.XAxis))));
			this.XLabel.x = (int) (myWidth * this.leftMargin);
			this.XLabel.y = myHeight - this.XLabel.height;
			if ((this.XAxis != null) && this.XAxis.xscaleOnTop) {
				this.XLabel.y = 0;
			}
			this.XLabel.width = (int) (myWidth * (1.0D - (this.legendMargin + this.leftMargin)));
		}
		if (this.Y2Axis != null) {
			this.plotters[0].width = (int) (this.plotters[0].width - ((myWidth * this.secondYAxisMargin) / 2.0D));
		}
		if (this.Y2Axis != null) {
			this.Y2Axis.x = this.plotters[0].x + this.plotters[0].width;
			this.Y2Axis.realPosition = this.plotters[0].x + this.plotters[0].visibleWidth;
			this.Y2Axis.y = (int) (myHeight * this.topMargin);
			this.Y2Axis.visibleSize = (int) (myHeight * (1.0D - (this.topMargin + this.bottomMargin)));
			this.Y2Axis.width = (int) ((myWidth * this.rightMargin) / (2 + this.getCountParallelAxis(this.Y2Axis)));
			this.Y2Axis.height = this.virtualHeight - (myHeight - this.Y2Axis.visibleSize);
		}
		if (this.legend != null) {
			this.legend.x = (int) (myWidth * (1.0D - this.legendMargin));
			this.legend.width = (int) (myWidth * this.legendMargin);
			this.legend.y = (int) (myHeight * this.topMargin);
			this.legend.height = (int) (myHeight * 0.5D);
		}
		this.setPlotterSize();
	}

	/** */
	private void autoSize_LayoutTop() {
		final int myHeight = this.getHeight();
		final int myWidth = this.getWidth();
		if (this.virtualWidth < myWidth) {
			this.virtualWidth = myWidth;
		}
		if (this.virtualHeight < myHeight) {
			this.virtualHeight = myHeight;
		}
		this.plotters[0].visibleWidth = (int) (myWidth * (1.0D - (this.leftMargin + this.rightMargin)));
		this.plotters[0].visibleHeight = (int) (myHeight
				* (1.0D - (this.topMargin + this.legendMargin + this.bottomMargin)));

		this.plotters[0].x = (int) (myWidth * this.leftMargin);
		this.plotters[0].y = (int) (myHeight * (this.topMargin + this.legendMargin));
		this.plotters[0].width = this.virtualWidth - (myWidth - this.plotters[0].visibleWidth);
		this.plotters[0].height = this.virtualHeight - (myHeight - this.plotters[0].visibleHeight);

		this.title.x = 0;
		this.title.y = 0;
		if ((this.XAxis != null) && this.XAxis.xscaleOnTop) {
			this.title.y = this.virtualHeight - (int) (myHeight * this.bottomMargin);
		}
		this.title.height = (int) (myHeight * this.topMargin);
		this.title.width = myWidth;
		if (this.XAxis != null) {
			this.XAxis.x = (int) (myWidth * this.leftMargin);
			this.XAxis.y = this.virtualHeight - (int) (myHeight * this.bottomMargin);
			this.XAxis.realPosition = (int) (myHeight * (1.0D - this.bottomMargin));
			this.XAxis.height = (int) (myHeight * (this.bottomMargin / (2 + this.getCountParallelAxis(this.XAxis))));
			this.XAxis.visibleSize = (int) (myWidth * (1.0D - (this.leftMargin + this.rightMargin)));

			this.XAxis.width = this.virtualWidth - (myWidth - this.XAxis.visibleSize);
		}
		if (this.YAxis != null) {
			this.YAxis.width = (int) (myWidth * (this.leftMargin / (2 + this.getCountParallelAxis(this.YAxis))));
			this.YAxis.x = (int) (myWidth * this.leftMargin) - this.YAxis.width;
			this.YAxis.realPosition = this.YAxis.x;
			this.YAxis.y = (int) (myHeight * (this.topMargin + this.legendMargin));
			this.YAxis.visibleSize = (int) (myHeight
					* (1.0D - (this.topMargin + this.bottomMargin + this.legendMargin)));
			this.YAxis.height = this.virtualHeight - (myHeight - this.YAxis.visibleSize);
		}
		if (this.XLabel != null) {
			this.XLabel.height = (int) (myHeight * (this.bottomMargin / (2 + this.getCountParallelAxis(this.XAxis))));
			this.XLabel.x = (int) (myWidth * this.leftMargin);
			this.XLabel.y = myHeight - this.XLabel.height;
			if ((this.XAxis != null) && this.XAxis.xscaleOnTop) {
				this.XLabel.y = (int) (myHeight * this.legendMargin);
			}
			this.XLabel.width = (int) (myWidth * (1.0D - (this.leftMargin + this.leftMargin)));
		}
		if (this.Y2Axis != null) {
			this.plotters[0].width = (int) (this.plotters[0].width - ((myWidth * this.secondYAxisMargin) / 2.0D));
		}
		if (this.Y2Axis != null) {
			this.Y2Axis.x = this.plotters[0].x + this.plotters[0].width;
			this.Y2Axis.realPosition = this.plotters[0].x + this.plotters[0].visibleWidth;
			this.Y2Axis.y = (int) (myHeight * (this.topMargin + this.legendMargin));
			this.Y2Axis.visibleSize = (int) (myHeight
					* (1.0D - (this.topMargin + this.bottomMargin + this.legendMargin)));
			this.Y2Axis.width = (int) ((myWidth * this.rightMargin) / (2 + this.getCountParallelAxis(this.Y2Axis)));
			this.Y2Axis.height = this.virtualHeight - (myHeight - this.Y2Axis.visibleSize);
		}
		if (this.legend != null) {
			this.legend.x = (int) (myWidth * this.leftMargin);
			this.legend.width = (int) (myWidth * (1.0D - (this.leftMargin + this.rightMargin)));
			this.legend.y = (int) (myHeight * this.topMargin);
			if ((this.XAxis != null) && this.XAxis.xscaleOnTop) {
				this.legend.y = 0;
			}
			this.legend.height = (int) (myHeight * this.legendMargin);
		}
		this.setPlotterSize();
	}

	/** */
	public void dispose() {
		for (int i = 0; i < this.plottersCount; i++) {
			if (this.plotters[i] != null) {
				for (int j = 0; j < this.plotters[i].getSeriesCount(); j++) {
					if (this.plotters[i].getSerie(j) instanceof LineDataSeq) {
						final LineDataSeq lSerie = (LineDataSeq) this.plotters[i].getSerie(j);
						if (lSerie.icon != null) {
							lSerie.icon.dispose();
						}
					}
				}
			}
		}
		if (this.chartImage != null) {
			this.chartImage.dispose();
		}
		if (this.finalImage != null) {
			this.finalImage.dispose();
		}
		if (this.backTmpImage != null) {
			this.backTmpImage.dispose();
		}
		if (this.backImage != null) {
			this.backImage.dispose();
		}
		this.stopWorker();
	}

	/** */
	private void drawBackImage(final SpiderChartGraphics g) {
		final int ImageW = this.backImage.getWidth();
		final int ImageH = this.backImage.getHeight();
		if ((ImageW == -1) || (ImageH == -1)) {
			return;
		}
		for (int j = 0; j <= this.virtualWidth; j += ImageW) {
			for (int i = 0; i <= this.virtualHeight; i += ImageH) {
				g.drawImage(this.backImage, j, i);
			}
		}
	}

	/** */
	private int getCountParallelAxis(final SpiderChartAxis axis) {
		if (axis.stackAdditionalAxis) {
			return 0;
		}
		return axis.getAdditionalAxisCount();
	}

	/** */
	public int getHeight() {
		return this.height;
	}

	/** */
	public TargetZone[] getTargetZones() {
		final TargetZone[] a = new TargetZone[this.targetZones.size()];
		for (int i = 0; i < a.length; i++) {
			a[i] = this.targetZones.elementAt(i);
		}
		return a;
	}

	/** */
	public int getWidth() {
		return this.width;
	}

	/** */
	public void mouseClick() {
		if (((this.selectedSerie != null) && (this.selectedSeriePoint >= 0)) || (this.selectedLabel != null)) {
			this.triggerEvent(5);
			return;
		}
		this.triggerEvent(6);
	}

	/** */
	public void mouseMoved(final int eX, final int eY) {
		if (this.plotters[0] == null) {
			return;
		}
		this.currentValueX = 0.0D;
		this.currentValueY = 0.0D;
		this.currentValueY2 = 0.0D;

		this.currentX = eX;
		this.currentY = eY;

		Object previousSelectedObject = this.selectedSerie;
		final int previousPoint = this.selectedSeriePoint;
		if ((this.selectedSerie == null) && (this.selectedLabel != null)) {
			previousSelectedObject = this.selectedLabel;
		}
		this.selectedSerie = null;
		this.selectedLabel = null;
		this.selectedSeriePoint = -1;
		if (this.XAxis != null) {
			this.currentValueX = this.XAxis.scale.getValue(this.currentX + this.offsetX);
		}
		if (this.YAxis != null) {
			this.currentValueY = this.YAxis.scale.getValue(this.currentY + this.offsetY);
			if (this.Y2Axis != null) {
				this.currentValueY2 = this.Y2Axis.scale.getValue(this.currentY + this.offsetY);
			}
		}
		if (this.activateSelection) {
			for (final SpiderChartPlotter plotter : this.plotters) {
				if (plotter == null) {
					break;
				}
				for (int k = 0; k < plotter.getSeriesCount(); k++) {
					final DataSeq d = plotter.getSerie(k);
					for (int i = 0; i < d.hotAreas.size(); i++) {
						if (((Polygon) d.hotAreas.elementAt(i)).contains(this.currentX + this.offsetX,
								this.currentY + this.offsetY)) {
							boolean triggerEnter = false;
							if (previousSelectedObject == null) {
								triggerEnter = true;
							} else if ((previousSelectedObject != d) || (previousPoint != i)) {
								this.triggerEvent(3);
								triggerEnter = true;
							}
							this.selectedSerie = d;
							this.selectedSeriePoint = i;
							if (!triggerEnter) {
								break;
							}
							this.triggerEvent(2);
							break;
						}
					}
				}
			}
			if (this.selectedSerie == null) {
				for (int i = 0; i < this.chartHotAreas.size(); i++) {
					final SpiderChartLabel label = (SpiderChartLabel) this.chartHotAreas.elementAt(i);
					if (label.clickableArea.contains(this.currentX + this.offsetX, this.currentY + this.offsetY)) {
						this.selectedLabel = label;
						break;
					}
				}
			}
			if ((Math.abs(this.currentX - this.cursorLastX) > 2) || (Math.abs(this.currentY - this.cursorLastY) > 2)) {
				this.cursorLastX = this.currentX;
				this.cursorLastY = this.currentY;
				this.triggerEvent(4);
			}
		}
		if ((previousSelectedObject != null) && (this.selectedSerie == null) && (this.selectedLabel == null)) {
			this.triggerEvent(3);
		}
	}

	/** */
	public void paint(final SpiderChartGraphics pg) {
		this.floatingObjects.removeAllElements();
		this.chartHotAreas.removeAllElements();

		System.currentTimeMillis();
		if ((this.plotters[0] == null) || (this.plottersCount <= 0)) {
			pg.setColor(SWTGraphicsSupplier.getColor(SpiderChartColor.RED));
			pg.drawString("Error: No plotters/series have been defined", 30, 30);
			return;
		}
		for (int j = 0; j < this.plottersCount; j++) {
			if ((this.plotters[j].getNeedsAxis() > 0) && (this.XAxis == null)) {
				pg.setColor(SWTGraphicsSupplier.getColor(SpiderChartColor.RED));
				pg.drawString("Error: No X axis have been defined", 30, 30);
				return;
			}
			if ((this.plotters[j].getNeedsAxis() > 1) && (this.YAxis == null)) {
				pg.setColor(SWTGraphicsSupplier.getColor(SpiderChartColor.RED));
				pg.drawString("Error: No Y axis have been defined", 30, 30);
				return;
			}
			if ((this.plottersCount > 1) && !this.plotters[j].getCombinable()) {
				pg.setColor(SWTGraphicsSupplier.getColor(SpiderChartColor.RED));
				pg.drawString("Error: These plotters cannot be combined", 30, 30);
				return;
			}
		}
		SpiderChartGraphics gScroll = pg;
		SpiderChartGraphics gBack = pg;
		SpiderChartGraphics g = pg;
		if ((this.lastWidth != this.width) || (this.lastHeight != this.height)) {
			this.repaintAll = true;
			this.lastWidth = this.width;
			this.lastHeight = this.height;
		}
		if (this.originalVirtualHeight == -1) {
			this.originalVirtualHeight = this.virtualHeight;
		}
		if (this.originalVirtualWidth == -1) {
			this.originalVirtualWidth = this.virtualWidth;
		}
		if (!this.withScroll) {
			this.repaintAlways = true;
		}
		if (this.repaintAlways) {
			this.repaintAll = true;
		}
		if (this.autoSize) {
			if (!this.withScroll) {
				this.virtualHeight = this.originalVirtualHeight;
				this.virtualWidth = this.originalVirtualWidth;
			}
			this.autoSize();
		}
		try {
			if (this.doubleBuffering && (this.repaintAll || (this.finalImage == null))) {
				if (this.finalImage != null) {
					this.finalImage.dispose();
				}
				this.finalImage = SWTGraphicsSupplier.createImage(this.getWidth(), this.getHeight());
			}
		} catch (final Exception e) {
		}
		if (this.finalImage != null) {
			g = this.finalImage.getGraphics();
			gScroll = g;
			gBack = g;
		}
		if (this.withScroll) {
			if (this.repaintAll || (this.chartImage == null)) {
				if (this.chartImage != null) {
					this.chartImage.dispose();
				}
				this.chartImage = SWTGraphicsSupplier.createImage(this.virtualWidth, this.virtualHeight);
			}
			gScroll = this.chartImage.getGraphics();
			if (this.repaintAll || (this.backTmpImage == null)) {
				if (this.backTmpImage != null) {
					this.backTmpImage.dispose();
				}
				this.backTmpImage = SWTGraphicsSupplier.createImage(this.virtualWidth, this.virtualHeight);
			}
			gBack = this.backTmpImage.getGraphics();
		}
		if ((this.virtualWidth > this.width) && this.XAxis.stackAdditionalAxis) {
			this.XAxis.stackAdditionalAxis = false;
			System.err.println("Warning: additional axis cannot be stacked if using scroll.");
			if (this.XAxis.getTargetZones().length > 0) {
				this.XAxis.removeTargetZones();
				System.err.println("Warning: axis target zones not compatible scroll.");
			}
		}
		if ((this.virtualHeight > this.height) && this.YAxis.stackAdditionalAxis) {
			this.YAxis.stackAdditionalAxis = false;
			System.err.println("Warning: additional axis cannot be stacked if using scroll.");
			if (this.YAxis.getTargetZones().length > 0) {
				this.YAxis.removeTargetZones();
				System.err.println("Warning: axis target zones not compatible scroll.");
			}
		}
		if (this.repaintAll) {
			if (this.back != null) {
				this.back.draw(gBack, 0, 0, this.virtualWidth, this.virtualHeight);
			}
			if (this.backImage != null) {
				this.drawBackImage(gBack);
			}
		}
		if (this.withScroll && ((this.backImage != null) || (this.back != null))) {
			if (this.repaintAll) {
				gScroll.drawImage(this.backTmpImage, 0, 0, this.virtualWidth, this.virtualHeight, 0, 0,
						this.virtualWidth, this.virtualHeight);
			}
			g.drawImage(this.backTmpImage, 0, 0, this.getWidth(), this.getHeight(), this.offsetX, this.offsetY,
					this.getWidth() + this.offsetX, this.getHeight() + this.offsetY);
		}
		if (this.plotters[0].XScale != null) {
			this.plotters[0].XScale.screenMax = this.plotters[0].x + this.plotters[0].width;
			this.plotters[0].XScale.screenMaxMargin = (int) (this.plotters[0].XScale.screenMax
					* (1.0D - this.axisMargin));
			if (this.fullXAxis) {
				this.plotters[0].XScale.screenMaxMargin = this.plotters[0].XScale.screenMax;
			}
			this.plotters[0].XScale.screenMin = this.plotters[0].x;
		}
		if (this.plotters[0].YScale != null) {
			this.plotters[0].YScale.screenMax = this.plotters[0].y + this.plotters[0].height;
			this.plotters[0].YScale.screenMaxMargin = (int) (this.plotters[0].YScale.screenMax
					* (1.0D - this.axisMargin));
			this.plotters[0].YScale.screenMin = this.plotters[0].y;
		}
		if (this.plotters[0].Y2Scale != null) {
			this.plotters[0].Y2Scale.screenMax = (this.plotters[0].y + this.plotters[0].height)
					- this.plotters[0].depth;
			this.plotters[0].Y2Scale.screenMaxMargin = (int) (this.plotters[0].Y2Scale.screenMax
					* (1.0D - this.axisMargin));
			this.plotters[0].Y2Scale.screenMin = this.plotters[0].y - this.plotters[0].depth;
		}
		if (this.repaintAll) {
			int plotterBackWidth = this.plotters[0].width;
			int plotterBackHeight = this.plotters[0].height;
			if (this.XAxis != null) {
				plotterBackWidth = this.XAxis.width;
			}
			if (this.YAxis != null) {
				plotterBackHeight = this.YAxis.height;
			}
			this.plotters[0].plotBackground(gScroll, plotterBackWidth, plotterBackHeight, this.offsetX, this.offsetY);
		}
		this.title.chart = this;
		this.title.draw(g);
		if (this.repaintAll) {
			if (this.XAxis != null) {
				this.XAxis.chart = this;
			}
			if (this.YAxis != null) {
				this.YAxis.chart = this;
			}
			if (this.Y2Axis != null) {
				this.Y2Axis.chart = this;
			}
			this.setPositionStackedAxis(this.XAxis);
			this.setPositionStackedAxis(this.YAxis);
			this.setPositionStackedAxis(this.Y2Axis);
			if (this.XAxis != null) {
				this.XAxis.offset = this.offsetX;
			}
			if (this.YAxis != null) {
				this.YAxis.offset = this.offsetY;
			}
			if (this.Y2Axis != null) {
				this.Y2Axis.offset = this.offsetY;
			}
			if (this.XAxis != null) {
				this.XAxis.drawGridBackground(gScroll, this.YAxis);
			}
			if (this.YAxis != null) {
				this.YAxis.drawGridBackground(gScroll, this.XAxis);
			}
			if (this.Y2Axis != null) {
				this.Y2Axis.drawGridBackground(gScroll, this.XAxis);
			}
			if (this.XAxis != null) {
				this.XAxis.drawBackground(gScroll, this.YAxis);
			}
			if (this.YAxis != null) {
				this.YAxis.drawBackground(gScroll, this.XAxis);
			}
			if (this.Y2Axis != null) {
				this.Y2Axis.drawBackground(gScroll, this.XAxis);
			}
		}
		this.paintTargetZones(g, true);
		if ((d() != 1) && (this.legend == null)) {
			this.legend = new SpiderChartLegend();
		}
		if (this.legend != null) {
			this.legend.chart = this;
			this.legend.draw(g);
		}
		if (this.XLabel != null) {
			this.XLabel.chart = this;
			this.XLabel.draw(g);
		}
		if (this.repaintAll) {
			for (int i = 0; i < this.plottersCount; i++) {
				this.plotters[i].chart = this;
				this.plotters[i].plot(gScroll);
			}
		}
		if (this.border != null) {
			this.border.drawRect(g, 0, 0, this.getWidth() - 1, this.getHeight() - 1);
		}
		if (this.chartImage != null) {
			final int x1 = this.plotters[0].x;

			final int x2 = this.plotters[0].x + this.plotters[0].visibleWidth;

			final int y1 = this.plotters[0].y - this.plotters[0].depth;

			final int y2 = (this.plotters[0].y - this.plotters[0].depth) + this.plotters[0].visibleHeight;

			g.drawImage(this.chartImage, x1, y1, x2, y2, x1 + this.offsetX, y1 + this.offsetY, x2 + this.offsetX,
					y2 + this.offsetY);
		}
		if (this.chartListeners != null) {
			for (int i = 0; i < this.chartListeners.size(); i++) {
				this.chartListeners.elementAt(i).paintUserExit(this, g);
			}
		}
		if (this.XAxis != null) {
			this.XAxis.offset = this.offsetX;
			this.XAxis.drawForeground(g, this.YAxis);
		}
		if (this.YAxis != null) {
			this.YAxis.offset = this.offsetY;
			this.YAxis.drawForeground(g, this.XAxis);
		}
		if (this.Y2Axis != null) {
			this.Y2Axis.offset = this.offsetY;
			this.Y2Axis.drawForeground(g, this.XAxis);
		}
		this.paintTargetZones(g, false);

		this.paintNotes(g);

		this.paintTips(g);
		if (this.finalImage != null) {
			pg.drawImage(this.finalImage, 0, 0, this.getWidth(), this.getHeight(), 0, 0, this.getWidth(),
					this.getHeight());
		}
		this.repaintAll = false;
		if (gScroll != pg) {
			gScroll.dispose();
		}
		if (gBack != pg) {
			gBack.dispose();
		}
		if (g != pg) {
			g.dispose();
		}
	}

	/** */
	private void paintNotes(final SpiderChartGraphics g) {
		if (g == null) {
			return;
		}
		for (int i = 0; i < this.notes.size(); i++) {
			final SpiderChartLabel label = new SpiderChartLabel(this.notes.elementAt(i), "", false, false);
			label.initialize(g, this);
			label.paint(g, 0, 0, 0, 0);
		}
	}

	/** */
	protected void paintTargetZones(final SpiderChartGraphics g, final boolean back) {
		g.setFont(SWTGraphicsSupplier.getFont("Arial", SpiderChartFont.BOLD, 10));
		for (int i = 0; i < this.targetZones.size(); i++) {
			final TargetZone z = this.targetZones.elementAt(i);
			z.chart = this;
			z.effect3D = this.plotters[0].depth;
			if (back && z.background) {
				z.paint(g, this.XAxis, this.YAxis);
			}
			if (!back && !z.background) {
				z.paint(g, this.XAxis, this.YAxis);
			}
		}
	}

	/** */
	private void paintTips(final SpiderChartGraphics g) {
		// TODO (AKM) To be implemented properly: Tips Functionality
		this.showingTip = false;

		if (this.showTips && (this.selectedSerie != null) && (this.selectedSeriePoint >= 0)) {
			if ((this.selectedSerie.tips != null) && (this.selectedSerie.tips.length > this.selectedSeriePoint)) {
			}
		}
		if (this.showTips && (this.selectedLabel != null)) {
			this.selectedLabel.getTip();
		}
		if (this.showPosition) {
			if ((this.currentX > 0) && (this.currentY > 0) && (this.currentX < this.XAxis.scale.screenMax)
					&& (this.currentX > this.XAxis.scale.screenMin) && (this.currentY < this.YAxis.scale.screenMax)
					&& (this.currentY > this.YAxis.scale.screenMin)) {
				String txt = "" + this.currentValueY;
				String sFormat = this.YAxis.scaleLabelFormat;
				if (sFormat.length() == 0) {
					sFormat = "#";
				}
				if ((this.YAxis != null) && (sFormat.length() > 0)) {
					DecimalFormat df = null;
					if (numberLocale == null) {
						df = new DecimalFormat(sFormat);
					} else {
						final NumberFormat nf = NumberFormat.getNumberInstance(new Locale(numberLocale, ""));
						df = (DecimalFormat) nf;
						df.applyPattern(sFormat);
					}
					txt = df.format(new Double(this.currentValueY));
				}
				g.setFont(this.tipFont);
				final int he = g.getFontHeight() + 4;
				final int wi = g.getFontWidth(txt + "  ");
				g.setColor(this.tipColor);
				g.fillRect(this.currentX, this.currentY - he, wi, he);
				g.setColor(this.tipFontColor);
				g.drawRect(this.currentX, this.currentY - he, wi, he);

				this.showingTip = true;
				g.drawString(txt, this.currentX + 2, this.currentY - 2);
			}
		}
	}

	/** */
	protected void placeFloatingObject(final IFloatingObject obj) {
	}

	/** */
	public void removeAllChartListener() {
		this.chartListeners.removeAllElements();
	}

	/** */
	public void removeChartListener(final ISpiderChartListener cl) {
		this.chartListeners.removeElement(cl);
	}

	/** */
	public void removeNotes() {
		this.notes.removeAllElements();
	}

	/** */
	public void removePlotters() {
		for (int i = 0; i < this.plottersCount; i++) {
			this.plotters[i] = null;
		}
		this.plottersCount = 0;
	}

	/** */
	public void removeTargetZones() {
		this.targetZones.removeAllElements();
	}

	/** */
	protected void resetChart(final SpiderChartTitle t, final SpiderChartPlotter p, final SpiderChartAxis X, final SpiderChartAxis Y) {
		this.plottersCount = 0;
		this.plotters = new SpiderChartPlotter[10];
		this.XAxis = null;
		this.YAxis = null;
		this.Y2Axis = null;
		this.XLabel = null;
		this.legend = null;
		this.title = null;
		this.border = null;
		this.back = null;
		this.selectedSerie = null;
		this.selectedSeriePoint = -1;
		this.repaintAll = true;
		this.removeTargetZones();
		this.removeNotes();
		this.chartHotAreas.removeAllElements();
		this.floatingObjects.removeAllElements();

		this.plotters[0] = p;
		this.XAxis = X;
		this.YAxis = Y;
		if ((X != null) && (this.plotters[0] != null)) {
			this.plotters[0].XScale = X.scale;
			X.plot = this.plotters[0];
		}
		if ((Y != null) && (this.plotters[0] != null)) {
			this.plotters[0].YScale = Y.scale;
			Y.plot = this.plotters[0];
		}
		this.title = t;
		if (this.title == null) {
			this.title = new SpiderChartTitle();
			this.title.text = "";
		}
		this.plottersCount = 1;
	}

	/** */
	public void resetSize() {
		if (this.originalVirtualHeight > -1) {
			this.virtualHeight = this.originalVirtualHeight;
		}
		if (this.originalVirtualWidth > -1) {
			this.virtualWidth = this.originalVirtualWidth;
		}
	}

	/** */
	public void saveToFile(final OutputStream os, final String psFormat) throws Exception {
		final SpiderChartImage image = SWTGraphicsSupplier.createImage(this.width, this.height);
		SpiderChartGraphics g = null;
		try {
			g = image.getGraphics();
			this.paint(g);
			image.saveToStream(psFormat, os);
		} finally {
			if (g != null) {
				g.dispose();
			}
			image.dispose();
		}
	}

	/** */
	public void saveToFile(final String psFile, final String psFormat) throws Exception {
		this.saveToFile(new FileOutputStream(psFile), psFormat);
	}

	/** */
	public void setHeight(final int h) {
		if (h > this.minimumHeight) {
			this.height = h;
		}
	}

	/** */
	public void setMinimumSize(final int w, final int h) {
		this.minimumWidth = w;
		this.minimumHeight = h;
	}

	/** */
	private void setPlotterSize() {
		for (int i = 1; i < this.plottersCount; i++) {
			this.plotters[i].x = this.plotters[0].x;
			this.plotters[i].y = this.plotters[0].y;
			this.plotters[i].width = this.plotters[0].width;
			this.plotters[i].height = this.plotters[0].height;
		}
	}

	/** */
	private void setPositionStackedAxis(final SpiderChartAxis axis) {
		if (axis == null) {
			return;
		}
		final int aCount = axis.getAdditionalAxisCount() + 1;
		if (axis.stackAdditionalAxis && (aCount > 1)) {
			if (axis.orientation == 1) {
				int tmpY = axis.y;
				final int h = axis.height / aCount;
				int screenMin = axis.scale.screenMin;
				final int screenH = (axis.scale.screenMax - axis.scale.screenMin) / aCount;
				final int screenH2 = (axis.scale.screenMaxMargin - axis.scale.screenMin) / aCount;
				final int visibleH = axis.visibleSize / aCount;
				tmpY += h;
				screenMin += screenH;

				axis.height = h;
				axis.scale.screenMax = axis.scale.screenMin + screenH;
				axis.scale.screenMaxMargin = axis.scale.screenMin + screenH2;
				axis.visibleSize = visibleH;
				for (int i = 0; i < axis.getAdditionalAxisCount(); i++) {
					final SpiderChartAxis a = axis.getAdditionalAxis(i);

					a.y = tmpY;
					a.height = h;
					a.scale.screenMin = screenMin;
					a.scale.screenMax = a.scale.screenMin + screenH;
					a.scale.screenMaxMargin = a.scale.screenMin + screenH2;
					a.visibleSize = visibleH;
					a.realPosition = axis.realPosition;
					a.width = axis.width;
					tmpY += h;
					screenMin += screenH;
				}
			} else {
				int tmpX = axis.x;
				final int w = axis.width / aCount;
				int screenMin = axis.scale.screenMin;
				final int screenW = (axis.scale.screenMax - axis.scale.screenMin) / aCount;
				final int screenW2 = (axis.scale.screenMaxMargin - axis.scale.screenMin) / aCount;
				final int visibleW = axis.visibleSize / aCount;
				tmpX += w;
				screenMin += screenW;

				axis.width = w;
				axis.scale.screenMax = axis.scale.screenMin + screenW;
				axis.scale.screenMaxMargin = axis.scale.screenMin + screenW2;
				axis.visibleSize = visibleW;
				for (int i = 0; i < axis.getAdditionalAxisCount(); i++) {
					final SpiderChartAxis a = axis.getAdditionalAxis(i);

					a.x = tmpX;
					a.width = w;
					a.scale.screenMin = screenMin;
					a.scale.screenMax = a.scale.screenMin + screenW;
					a.scale.screenMaxMargin = a.scale.screenMin + screenW2;
					a.visibleSize = visibleW;
					a.realPosition = axis.realPosition;
					a.height = axis.height;
					tmpX += w;
					screenMin += screenW;
				}
			}
		}
	}

	/** */
	public void setSize(final int w, final int h) {
		this.setWidth(w);
		this.setHeight(h);
	}

	/** */
	public void setWidth(final int w) {
		if (w > this.minimumWidth) {
			this.width = w;
		}
	}

	/** */
	public void setY2Scale(final SpiderChartAxis a) {
		this.plotters[0].Y2Scale = a.scale;
		this.Y2Axis = a;
		a.rightAxis = true;
		a.plot = this.plotters[0];
	}

	/** */
	public void startWorker() {
		this.stopped = false;

		this.deamon = new SpiderChartWorker(null);
		this.deamon.chart = this;
		new Thread(this.deamon).start();
	}

	/** */
	public void stopWorker() {
		this.stopped = true;
		if (this.deamon != null) {
			this.deamon.stop = true;
		}
		this.deamon = null;
	}

	/** */
	private void triggerEvent(final int event) {
		for (int i = 0; i < this.chartListeners.size(); i++) {
			this.chartListeners.elementAt(i).chartEvent(this, event);
		}
	}
}
