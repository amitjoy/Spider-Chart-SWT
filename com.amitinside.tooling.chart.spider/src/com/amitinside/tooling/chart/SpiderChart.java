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

import static com.amitinside.tooling.chart.gc.AbstractChartColor.AQUA;
import static com.amitinside.tooling.chart.gc.AbstractChartColor.BLACK;
import static com.amitinside.tooling.chart.gc.AbstractChartColor.RED;
import static com.amitinside.tooling.chart.gc.AbstractChartColor.YELLOW;
import static com.amitinside.tooling.chart.gc.AbstractChartFont.PLAIN;
import static com.amitinside.tooling.chart.gc.AbstractGraphicsSupplier.createImage;
import static com.amitinside.tooling.chart.gc.AbstractGraphicsSupplier.getColor;
import static com.amitinside.tooling.chart.gc.AbstractGraphicsSupplier.getFont;
import static com.amitinside.tooling.chart.gc.Fonts.SERIF;
import static com.amitinside.tooling.chart.listener.ISpiderChartListener.EVENT_CHART_CLICKED;
import static com.amitinside.tooling.chart.listener.ISpiderChartListener.EVENT_ENTER_POINT;
import static com.amitinside.tooling.chart.listener.ISpiderChartListener.EVENT_LEAVE_POINT;
import static com.amitinside.tooling.chart.listener.ISpiderChartListener.EVENT_POINT_CLICKED;
import static com.amitinside.tooling.chart.listener.ISpiderChartListener.EVENT_TIP_UPDATE;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.amitinside.tooling.chart.gc.AbstractChartColor;
import com.amitinside.tooling.chart.gc.AbstractChartFont;
import com.amitinside.tooling.chart.gc.AbstractChartGraphics;
import com.amitinside.tooling.chart.gc.AbstractChartImage;
import com.amitinside.tooling.chart.gc.Polygon;
import com.amitinside.tooling.chart.label.SpiderChartLabel;
import com.amitinside.tooling.chart.label.api.IFloatingObject;
import com.amitinside.tooling.chart.legend.SpiderChartLegend;
import com.amitinside.tooling.chart.listener.ISpiderChartListener;
import com.amitinside.tooling.chart.plotter.AbstractPlotter;
import com.amitinside.tooling.chart.plotter.spider.SpiderChartPlotter;
import com.amitinside.tooling.chart.sequence.DataSeq;
import com.amitinside.tooling.chart.sequence.LineDataSeq;
import com.amitinside.tooling.chart.style.FillStyle;
import com.amitinside.tooling.chart.style.LineStyle;
import com.amitinside.tooling.chart.title.SpiderChartTitle;

/**
 * Actual Spider Chart Diagram
 *
 * @author AMIT KUMAR MONDAL
 *
 */
public final class SpiderChart {

	/**
	 * Background Worker Thread to refresh chart
	 */
	private final class SpiderChartWorker implements Runnable {

		/** The Spider Chart Diagram to be updated */
		@SuppressWarnings("unused")
		private SpiderChart chart = null;

		/** the flag */
		private boolean stop = false;

		/** Constructor */
		private SpiderChartWorker() {
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

	/** the localized string for numbers */
	private static String numberLocale;

	/** Getter for number locale */
	public static String getNumberLocale() {
		return numberLocale;
	}

	/** Setter for number locale */
	public static void setNumberLocale(final String numberLocale) {
		SpiderChart.numberLocale = numberLocale;
	}

	/** The flag to be used to enable selection of data points */
	private boolean activateSelection = false;

	/** Used to trigger thread automatically to build the Spider Chart */
	private boolean autoRebuild = true;

	/** Auto Sizeable Property */
	private boolean autoResize = true;

	/** Axis Margin */
	private double axisMargin = 0.0625D;

	/** Background Canvas Color */
	private String backgroundCanvasColor = AQUA;

	/** Spider Chart Back Image */
	private AbstractChartImage backImage;

	/** Background Styling Theme */
	private FillStyle backStyle = new FillStyle(getColor(AQUA));

	/** Temporary Background Image */
	private AbstractChartImage backTmpImage = null;

	/** Border Style */
	private LineStyle border = null;

	/** Margin from the bottom */
	private final double bottomMargin = 0.125D;

	/** Spider Chart Image */
	private AbstractChartImage chartImage = null;

	/** List of action listeners */
	private final List<ISpiderChartListener> chartListeners = new CopyOnWriteArrayList<>();

	/** Current X Coordinate of the cursor */
	private int currentX;

	/** Current Y Coordinate of the cursor */
	private int currentY;

	/** Previous X Coordinate of the cursor */
	private int cursorLastX = 0;

	/** Previous Y Coordinate of the cursor */
	private int cursorLastY = 0;

	/** Thread to draw spider chart */
	private SpiderChartWorker deamon = null;

	/** Needed to use buffering of values (WIP) */
	private final boolean doubleBuffering = true;

	/** the rendered spider chart image */
	private AbstractChartImage finalImage = null;

	/** Container for list of Spider Chart Elements such as Labels */
	private final List<IFloatingObject> floatingObjects = new ArrayList<>();

	/** configuration if X-Axis Screen Margin will be set */
	private final boolean fullXAxis = false;

	/** Height of the spider chart */
	private int height = 0;

	/** last height before the zoom */
	private int lastHeight = -1;

	/** last height before the width */
	private int lastWidth = -1;

	/** Chart Left Margin */
	private final double leftMargin = 0.125D;

	/** Spider Chart Legend */
	private SpiderChartLegend legend;

	/** Spider Chart Legend Margin */
	private final double legendMargin = 0.2D;

	/** minimum height */
	private int minimumHeight = 0;

	/** minimum width */
	private int minimumWidth = 0;

	/** microseconds to wait before a chart refresh */
	private long msecs = 2000L;

	/** X-offset for data points */
	private int offsetX = 0;

	/** Y-offset for data points */
	private int offsetY = 0;

	/** */
	private int originalVirtualHeight = -1;

	/** */
	private int originalVirtualWidth = -1;

	/** All the plotters to be used for spider chart plotting */
	private AbstractPlotter[] plotters = new SpiderChartPlotter[10];

	/** no of plotters */
	private int plottersCount = 0;

	/** configuration for repainting the chart */
	private boolean repaintAll = true;

	/** configuration for repainting always */
	private boolean repaintAlways = true;

	/** Right MArgin */
	private final double rightMargin = 0.125D;

	/** Spider Chart Label */
	private SpiderChartLabel selectedLabel = null;

	/** The data sequence to be used */
	private DataSeq selectedSeq = null;

	/** the data point as selected */
	private int selectedSeqPoint = -1;

	/** Show Tips on the Spider Chart Points */
	private boolean showTips = false;

	/** */
	private boolean stopped = false;

	/** Spider Chart Tip Background Color */
	private final AbstractChartColor tipColor = getColor(YELLOW);

	/** Spider Chart Tip Font */
	private final AbstractChartFont tipFont = getFont(SERIF, PLAIN, 10);

	/** Spider Chart Tip Font Color */
	private final AbstractChartColor tipFontColor = getColor(BLACK);

	/** Spider Chart Title */
	private SpiderChartTitle title;

	/** Top Margin */
	private final double topMargin = 0.125D;

	/** */
	private int virtualHeight = 0;

	/** */
	private int virtualWidth = 0;

	/** */
	private int width = 0;

	/** Scrollable Property */
	private boolean withScroll = false;

	/** Constructor */
	public SpiderChart(final SpiderChartTitle t, final SpiderChartPlotter p) {
		this.resetChart(t, p);
	}

	/** Registers the selected chart listener */
	public void addChartListener(final ISpiderChartListener cl) {
		this.chartListeners.add(cl);
	}

	/** Adds the selected floating object */
	public void addFloationgObject(final IFloatingObject obj) {
		this.floatingObjects.add(obj);
	}

	/** Adds the plotter */
	public void addPlotter(final SpiderChartPlotter p) {
		this.plotters[this.plottersCount] = p;
		this.plotters[this.plottersCount].setxScale(this.plotters[0].getxScale());
		this.plotters[this.plottersCount].setyScale(this.plotters[0].getyScale());
		this.plottersCount += 1;
	}

	/** Adds the sequence */
	public void addSeq(final DataSeq s) {
		this.plotters[0].addSeq(s);
	}

	/** Disposes the spider chart */
	public void dispose() {
		for (int i = 0; i < this.plottersCount; i++) {
			if (this.plotters[i] != null) {
				for (int j = 0; j < this.plotters[i].getSeqCount(); j++) {
					if (this.plotters[i].getSeq(j) instanceof LineDataSeq) {
						final LineDataSeq lseq = (LineDataSeq) this.plotters[i].getSeq(j);
						if (lseq.getIcon() != null) {
							lseq.getIcon().dispose();
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

	/** Draws the background image */
	private void drawBackImage(final AbstractChartGraphics g) {
		final int imageW = this.backImage.getWidth();
		final int imageH = this.backImage.getHeight();
		if ((imageW == -1) || (imageH == -1)) {
			return;
		}
		for (int j = 0; j <= this.virtualWidth; j += imageW) {
			for (int i = 0; i <= this.virtualHeight; i += imageH) {
				g.drawImage(this.backImage, j, i);
			}
		}
	}

	/** Getter for Axis Margin */
	public double getAxisMargin() {
		return this.axisMargin;
	}

	/** Getter for Background Canvas Color */
	public String getBackgroundCanvasColor() {
		return this.backgroundCanvasColor;
	}

	/** Getter for Background Image */
	public AbstractChartImage getBackImage() {
		return this.backImage;
	}

	/** Getter for Background Fill Style */
	public FillStyle getBackStyle() {
		return this.backStyle;
	}

	/** Getter for Background Temporary Image */
	public AbstractChartImage getBackTmpImage() {
		return this.backTmpImage;
	}

	/** Getter for Border */
	public LineStyle getBorder() {
		return this.border;
	}

	/** Getter for Bottom Margin */
	public double getBottomMargin() {
		return this.bottomMargin;
	}

	/** Getter for Chart Image */
	public AbstractChartImage getChartImage() {
		return this.chartImage;
	}

	/** Getter for all registered chart listeners */
	public List<ISpiderChartListener> getChartListeners() {
		return this.chartListeners;
	}

	/** Getter for current X coordinate of the cursor */
	public int getCurrentX() {
		return this.currentX;
	}

	/** Getter for current Y coordinate of the cursor */
	public int getCurrentY() {
		return this.currentY;
	}

	/** Getter for last cursor X coordinate of the cursor */
	public int getCursorLastX() {
		return this.cursorLastX;
	}

	/** Getter for last cursor Y coordinate of the cursor */
	public int getCursorLastY() {
		return this.cursorLastY;
	}

	/** Getter for the deamon to refresh the chart */
	public SpiderChartWorker getDeamon() {
		return this.deamon;
	}

	/** Getter for the last image to be shown as a background to the chart */
	public AbstractChartImage getFinalImage() {
		return this.finalImage;
	}

	/** Getter for all floating objects to be used in the chart */
	public List<IFloatingObject> getFloatingObjects() {
		return this.floatingObjects;
	}

	/** Getter for the height */
	public int getHeight() {
		return this.height;
	}

	/** Getter for last height of the chart before zoom */
	public int getLastHeight() {
		return this.lastHeight;
	}

	/** Getter for the last width of the chart before zoom */
	public int getLastWidth() {
		return this.lastWidth;
	}

	/** Getter for left margin */
	public double getLeftMargin() {
		return this.leftMargin;
	}

	/** Getter for the spider chart legend */
	public SpiderChartLegend getLegend() {
		return this.legend;
	}

	/** Getter for legend margin */
	public double getLegendMargin() {
		return this.legendMargin;
	}

	/** Getter for minimum height */
	public int getMinimumHeight() {
		return this.minimumHeight;
	}

	/** Getter for minimum width */
	public int getMinimumWidth() {
		return this.minimumWidth;
	}

	/** Getter for the microseconds to wait before the chart to be refreshed */
	public long getMsecs() {
		return this.msecs;
	}

	/** Getter for X-coordinate offset */
	public int getOffsetX() {
		return this.offsetX;
	}

	/** Getter for Y-coordinate offset */
	public int getOffsetY() {
		return this.offsetY;
	}

	/** Getter for virtual height */
	public int getOriginalVirtualHeight() {
		return this.originalVirtualHeight;
	}

	/** Getter for virtual width */
	public int getOriginalVirtualWidth() {
		return this.originalVirtualWidth;
	}

	/** Getter for all the plotters */
	public AbstractPlotter[] getPlotters() {
		return this.plotters;
	}

	/** Getter for the count of the plotters */
	public int getPlottersCount() {
		return this.plottersCount;
	}

	/** Getter for margin from right */
	public double getRightMargin() {
		return this.rightMargin;
	}

	/** Getter for spider chart label */
	public SpiderChartLabel getSelectedLabel() {
		return this.selectedLabel;
	}

	/** Getter for the sequence to be used */
	public DataSeq getSelectedSeq() {
		return this.selectedSeq;
	}

	/** Getter for selected sequence point */
	public int getSelectedSeqPoint() {
		return this.selectedSeqPoint;
	}

	/** Getter for the first plotter to be used */
	public SpiderChartPlotter getSpiderPlotter() {
		return (SpiderChartPlotter) this.plotters[0];
	}

	/** Getter for the tip color */
	public AbstractChartColor getTipColor() {
		return this.tipColor;
	}

	/** Getter for tip font */
	public AbstractChartFont getTipFont() {
		return this.tipFont;
	}

	/** Getter for tip font color */
	public AbstractChartColor getTipFontColor() {
		return this.tipFontColor;
	}

	/** Getter for the spider chart title */
	public SpiderChartTitle getTitle() {
		return this.title;
	}

	/** Getter for the margin from top */
	public double getTopMargin() {
		return this.topMargin;
	}

	/** Getter for virtual height */
	public int getVirtualHeight() {
		return this.virtualHeight;
	}

	/** Getter for virtual width */
	public int getVirtualWidth() {
		return this.virtualWidth;
	}

	/** Getter for the width of the chart */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Getter for the configuration to check if it is a active sequence
	 * selection
	 */
	public boolean isActivateSelection() {
		return this.activateSelection;
	}

	/**
	 * Getter for the configuration to check if chart auto rebuild is enabled
	 */
	public boolean isAutoRebuild() {
		return this.autoRebuild;
	}

	/** Getter for the configuration to check if auto resize if enabled */
	public boolean isAutoResize() {
		return this.autoResize;
	}

	/** Getter for the configuration to check if double buffering is enabled */
	public boolean isDoubleBuffering() {
		return this.doubleBuffering;
	}

	/**
	 * Getter for the configuration to check if X-axis screen margin will be set
	 */
	public boolean isFullXAxis() {
		return this.fullXAxis;
	}

	/**
	 * Getter for the configuration to check if repainting all floating objects
	 * is enabled
	 */
	public boolean isRepaintAll() {
		return this.repaintAll;
	}

	/**
	 * Getter for the configuration to check if repaint the chart always is
	 * enabled
	 */
	public boolean isRepaintAlways() {
		return this.repaintAlways;
	}

	/** Getter for the configuration to check if tips to be shown */
	public boolean isShowTips() {
		return this.showTips;
	}

	/**
	 * Getter to check if the thread responsible to refresh the chart is stopped
	 */
	public boolean isStopped() {
		return this.stopped;
	}

	/** Getter for the configuration if the chart has scrolling functionality */
	public boolean isWithScroll() {
		return this.withScroll;
	}

	/** Mouse Click Functionality */
	public void mouseClick() {
		if (((this.selectedSeq != null) && (this.selectedSeqPoint >= 0)) || (this.selectedLabel != null)) {
			this.triggerChartEvent(EVENT_POINT_CLICKED);
			return;
		}
		this.triggerChartEvent(EVENT_CHART_CLICKED);
	}

	/** Mouse Move Functionality */
	public void mouseMoved(final int eX, final int eY) {
		if (this.plotters[0] == null) {
			return;
		}

		this.currentX = eX;
		this.currentY = eY;

		Object previousSelectedObject = this.selectedSeq;
		final int previousPoint = this.selectedSeqPoint;

		if ((this.selectedSeq == null) && (this.selectedLabel != null)) {
			previousSelectedObject = this.selectedLabel;
		}
		this.selectedSeq = null;
		this.selectedLabel = null;
		this.selectedSeqPoint = -1;
		if (this.activateSelection) {
			for (final AbstractPlotter plotter : this.plotters) {
				if (plotter == null) {
					break;
				}
				for (int k = 0; k < plotter.getSeqCount(); k++) {
					final DataSeq d = plotter.getSeq(k);
					for (int i = 0; i < d.getHotAreas().size(); i++) {
						if (((Polygon) d.getHotAreas().get(i)).contains(this.currentX + this.offsetX,
								this.currentY + this.offsetY)) {
							boolean triggerEnter = false;
							if (previousSelectedObject == null) {
								triggerEnter = true;
							} else if ((previousSelectedObject != d) || (previousPoint != i)) {
								this.triggerChartEvent(EVENT_LEAVE_POINT);
								triggerEnter = true;
							}
							this.selectedSeq = d;
							this.selectedSeqPoint = i;
							if (!triggerEnter) {
								break;
							}
							this.triggerChartEvent(EVENT_ENTER_POINT);
							break;
						}
					}
				}
			}
			if ((Math.abs(this.currentX - this.cursorLastX) > 2) || (Math.abs(this.currentY - this.cursorLastY) > 2)) {
				this.cursorLastX = this.currentX;
				this.cursorLastY = this.currentY;
				this.triggerChartEvent(EVENT_TIP_UPDATE);
			}
		}
		if ((previousSelectedObject != null) && (this.selectedSeq == null) && (this.selectedLabel == null)) {
			this.triggerChartEvent(EVENT_LEAVE_POINT);
		}
	}

	/** Paints the chart */
	public void paint(final AbstractChartGraphics pg) {
		this.floatingObjects.clear();

		if ((this.plotters[0] == null) || (this.plottersCount <= 0)) {
			pg.setColor(getColor(RED));
			pg.drawText("No plotters have been found", 30, 30);
			return;
		}
		AbstractChartGraphics gScroll = pg;
		AbstractChartGraphics gBack = pg;
		AbstractChartGraphics g = pg;
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
		if (this.autoResize) {
			if (!this.withScroll) {
				this.virtualHeight = this.originalVirtualHeight;
				this.virtualWidth = this.originalVirtualWidth;
			}
			this.resize();
		}
		try {
			if (this.doubleBuffering && (this.repaintAll || (this.finalImage == null))) {
				if (this.finalImage != null) {
					this.finalImage.dispose();
				}
				this.finalImage = createImage(this.getWidth(), this.getHeight());
			}
		} catch (final Exception e) {
			e.printStackTrace();
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
				this.chartImage = createImage(this.virtualWidth, this.virtualHeight);
			}
			gScroll = this.chartImage.getGraphics();
			if (this.repaintAll || (this.backTmpImage == null)) {
				if (this.backTmpImage != null) {
					this.backTmpImage.dispose();
				}
				this.backTmpImage = createImage(this.virtualWidth, this.virtualHeight);
			}
			gBack = this.backTmpImage.getGraphics();
		}
		if (this.repaintAll) {
			if ((this.backStyle != null) && (this.backgroundCanvasColor != null)) {
				this.backStyle.draw(gBack, this.backgroundCanvasColor, 0, 0, this.virtualWidth, this.virtualHeight);
			}
			if (this.backImage != null) {
				this.drawBackImage(gBack);
			}
		}
		if (this.withScroll && ((this.backImage != null) || (this.backStyle != null))) {
			if (this.repaintAll) {
				gScroll.drawImage(this.backTmpImage, 0, 0, this.virtualWidth, this.virtualHeight, 0, 0,
						this.virtualWidth, this.virtualHeight);
			}
			g.drawImage(this.backTmpImage, 0, 0, this.getWidth(), this.getHeight(), this.offsetX, this.offsetY,
					this.getWidth() + this.offsetX, this.getHeight() + this.offsetY);
		}
		if (this.plotters[0].getxScale() != null) {
			this.plotters[0].getxScale().setScreenMax(this.plotters[0].x + this.plotters[0].width);
			this.plotters[0].getxScale()
					.setScreenMaxMargin((int) (this.plotters[0].getxScale().getScreenMax() * (1.0D - this.axisMargin)));
			if (this.fullXAxis) {
				this.plotters[0].getxScale().setScreenMaxMargin(this.plotters[0].getxScale().getScreenMax());
			}
			this.plotters[0].getxScale().setScreenMin(this.plotters[0].x);
		}
		if (this.plotters[0].getyScale() != null) {
			this.plotters[0].getyScale().setScreenMax(this.plotters[0].y + this.plotters[0].height);
			this.plotters[0].getyScale()
					.setScreenMaxMargin((int) (this.plotters[0].getyScale().getScreenMax() * (1.0D - this.axisMargin)));
			this.plotters[0].getyScale().setScreenMin(this.plotters[0].y);
		}
		if (this.repaintAll) {
			final int plotterBackWidth = this.plotters[0].width;
			final int plotterBackHeight = this.plotters[0].height;
			this.plotters[0].plotBackground(gScroll, plotterBackWidth, plotterBackHeight, this.offsetX, this.offsetY);
		}
		this.title.chart = this;
		this.title.draw(g);
		if ((this.legend == null)) {
			this.legend = new SpiderChartLegend();
		}
		if (this.legend != null) {
			this.legend.chart = this;
			this.legend.draw(g);
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

			final int x2 = this.plotters[0].x + this.plotters[0].getVisibleWidth();

			final int y1 = this.plotters[0].y - this.plotters[0].getDepth();

			final int y2 = (this.plotters[0].y - this.plotters[0].getDepth()) + this.plotters[0].getVisibleHeight();

			g.drawImage(this.chartImage, x1, y1, x2, y2, x1 + this.offsetX, y1 + this.offsetY, x2 + this.offsetX,
					y2 + this.offsetY);
		}
		if (this.chartListeners != null) {
			for (int i = 0; i < this.chartListeners.size(); i++) {
				this.chartListeners.get(i).onPaintUserExit(this, g);
			}
		}

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

	/** Paints the tips */
	private void paintTips(final AbstractChartGraphics g) {
		// TODO (AKM) To be implemented properly: Tips Functionality
		if (this.showTips && (this.selectedLabel != null)) {
			this.selectedLabel.getTip();
		}
	}

	/** Places the defined floating object */
	public void placeFloatingObject(final IFloatingObject obj) {
	}

	/** Removes all registered listeners */
	public void removeAllChartListener() {
		this.chartListeners.clear();
	}

	/** Removes the specified chart listener */
	public void removeChartListener(final ISpiderChartListener cl) {
		this.chartListeners.remove(cl);
	}

	/** Removes all the chart plotters */
	public void removeChartPlotters() {
		for (int i = 0; i < this.plottersCount; i++) {
			this.plotters[i] = null;
		}
		this.plottersCount = 0;
	}

	/** Release the used resources */
	protected void resetChart(final SpiderChartTitle t, final SpiderChartPlotter p) {
		this.plottersCount = 0;
		this.plotters = new SpiderChartPlotter[10];
		this.legend = null;
		this.title = null;
		this.border = null;
		this.backStyle = null;
		this.selectedSeq = null;
		this.selectedSeqPoint = -1;
		this.repaintAll = true;
		this.floatingObjects.clear();
		this.plotters[0] = p;
		this.title = t;
		if (this.title == null) {
			this.title = new SpiderChartTitle();
			this.title.setText("");
		}
		this.plottersCount = 1;
	}

	/** Resizes the chart */
	private void resize() {
		final int myHeight = this.getHeight();
		final int myWidth = this.getWidth();
		if (this.virtualWidth < myWidth) {
			this.virtualWidth = myWidth;
		}
		if (this.virtualHeight < myHeight) {
			this.virtualHeight = myHeight;
		}
		this.plotters[0].setVisibleWidth((int) (myWidth * (1.0D - (this.legendMargin + this.leftMargin))));
		this.plotters[0].setVisibleHeight((int) (myHeight * (1.0D - (this.topMargin + this.bottomMargin))));

		this.plotters[0].x = (int) (myWidth * this.leftMargin);
		this.plotters[0].y = (int) (myHeight * this.topMargin);
		this.plotters[0].width = this.virtualWidth - (myWidth - this.plotters[0].getVisibleWidth());
		this.plotters[0].height = this.virtualHeight - (myHeight - this.plotters[0].getVisibleHeight());

		this.title.x = 0;
		this.title.y = 0;
		this.title.height = (int) (myHeight * this.topMargin);
		this.title.width = myWidth;
		if (this.legend != null) {
			this.legend.x = (int) (myWidth * (1.0D - this.legendMargin));
			this.legend.width = (int) (myWidth * this.legendMargin);
			this.legend.y = (int) (myHeight * this.topMargin);
			this.legend.height = (int) (myHeight * 0.5D);
		}
		this.setPlotterSize();
	}

	/** Setter for the active sequence selection */
	public void setActivateSelection(final boolean activateSelection) {
		this.activateSelection = activateSelection;
	}

	/** Setter for the auto rebuild configuration */
	public void setAutoRebuild(final boolean autoRebuild) {
		this.autoRebuild = autoRebuild;
	}

	/** Setter for the auto resize configuration */
	public void setAutoResize(final boolean autoResize) {
		this.autoResize = autoResize;
	}

	/** Setter for the axis margin */
	public void setAxisMargin(final double axisMargin) {
		this.axisMargin = axisMargin;
	}

	/** Setter for the background canvas color */
	public void setBackgroundCanvasColor(final String backgroundCanvasColor) {
		this.backgroundCanvasColor = backgroundCanvasColor;
	}

	/** Setter for the background image */
	public void setBackImage(final AbstractChartImage backImage) {
		this.backImage = backImage;
	}

	/** Setter for the background fill style */
	public void setBackStyle(final FillStyle backStyle) {
		this.backStyle = backStyle;
	}

	/** Setter for background temporary image */
	public void setBackTmpImage(final AbstractChartImage backTmpImage) {
		this.backTmpImage = backTmpImage;
	}

	/** Setter for the border */
	public void setBorder(final LineStyle border) {
		this.border = border;
	}

	/** Setter for the chart image */
	public void setChartImage(final AbstractChartImage chartImage) {
		this.chartImage = chartImage;
	}

	/** Setter for the current X Coordinate of the cursor */
	public void setCurrentX(final int currentX) {
		this.currentX = currentX;
	}

	/** Setter for the current Y Coordinate of the cursor */
	public void setCurrentY(final int currentY) {
		this.currentY = currentY;
	}

	/** Setter for previous X Coordinate of the cursor */
	public void setCursorLastX(final int cursorLastX) {
		this.cursorLastX = cursorLastX;
	}

	/** Setter for previous Y Coordinate of the cursor */
	public void setCursorLastY(final int cursorLastY) {
		this.cursorLastY = cursorLastY;
	}

	/** Setter for the spider chart refresh deamon */
	public void setDeamon(final SpiderChartWorker deamon) {
		this.deamon = deamon;
	}

	/** Setter for final image to be used on the chart */
	public void setFinalImage(final AbstractChartImage finalImage) {
		this.finalImage = finalImage;
	}

	/** Setter for the height of the chart */
	public void setHeight(final int h) {
		if (h > this.minimumHeight) {
			this.height = h;
		}
	}

	/** Setter for the last height before zoom */
	public void setLastHeight(final int lastHeight) {
		this.lastHeight = lastHeight;
	}

	/** Setter for the last width before zoom */
	public void setLastWidth(final int lastWidth) {
		this.lastWidth = lastWidth;
	}

	/** Setter for the legend */
	public void setLegend(final SpiderChartLegend legend) {
		this.legend = legend;
	}

	/** Setter for the minimum height */
	public void setMinimumHeight(final int minimumHeight) {
		this.minimumHeight = minimumHeight;
	}

	/** Setter for the minimum size */
	public void setMinimumSize(final int w, final int h) {
		this.minimumWidth = w;
		this.minimumHeight = h;
	}

	/** Setter for the minimum width */
	public void setMinimumWidth(final int minimumWidth) {
		this.minimumWidth = minimumWidth;
	}

	/** Setter for the miliseconds to wait before the chart gets refreshed */
	public void setMsecs(final long msecs) {
		this.msecs = msecs;
	}

	/** Setter for the offset to use in X-axis */
	public void setOffsetX(final int offsetX) {
		this.offsetX = offsetX;
	}

	/** Setter for the offset to use in Y-axis */
	public void setOffsetY(final int offsetY) {
		this.offsetY = offsetY;
	}

	/** Setter for the virtual height */
	public void setOriginalVirtualHeight(final int originalVirtualHeight) {
		this.originalVirtualHeight = originalVirtualHeight;
	}

	/** Setter for the virtual width */
	public void setOriginalVirtualWidth(final int originalVirtualWidth) {
		this.originalVirtualWidth = originalVirtualWidth;
	}

	/** Setter for the plotters */
	public void setPlotters(final AbstractPlotter[] plotters) {
		this.plotters = plotters;
	}

	/** Setter for the plotters counts */
	public void setPlottersCount(final int plottersCount) {
		this.plottersCount = plottersCount;
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

	/** Setter for the configuration to repaint all floating objects */
	public void setRepaintAll(final boolean repaintAll) {
		this.repaintAll = repaintAll;
	}

	/** Setter for the configuration to repaint always */
	public void setRepaintAlways(final boolean repaintAlways) {
		this.repaintAlways = repaintAlways;
	}

	/** Setter for the spider chart label */
	public void setSelectedLabel(final SpiderChartLabel selectedLabel) {
		this.selectedLabel = selectedLabel;
	}

	/** Setter for the selected data points sequence */
	public void setSelectedSeq(final DataSeq selectedSeq) {
		this.selectedSeq = selectedSeq;
	}

	/** */
	public void setSelectedSeqPoint(final int selectedSeqPoint) {
		this.selectedSeqPoint = selectedSeqPoint;
	}

	/** Setter for the configuration to show tips */
	public void setShowTips(final boolean showTips) {
		this.showTips = showTips;
	}

	/** Setter to set the size */
	public void setSize(final int w, final int h) {
		this.setWidth(w);
		this.setHeight(h);
	}

	/** Setter for the configuration to mark the stop as stopped */
	public void setStopped(final boolean stopped) {
		this.stopped = stopped;
	}

	/** Setter for the spider chart title */
	public void setTitle(final SpiderChartTitle title) {
		this.title = title;
	}

	/** Setter for the virtual height */
	public void setVirtualHeight(final int virtualHeight) {
		this.virtualHeight = virtualHeight;
	}

	/** Setter for the virtual width */
	public void setVirtualWidth(final int virtualWidth) {
		this.virtualWidth = virtualWidth;
	}

	/** Setter for the width of the chart */
	public void setWidth(final int w) {
		if (w > this.minimumWidth) {
			this.width = w;
		}
	}

	/**
	 * Setter for the configuration to enable scrolling funtionality on the
	 * chart
	 */
	public void setWithScroll(final boolean withScroll) {
		this.withScroll = withScroll;
	}

	/** Executes the chart refresh deamon */
	public void startWorker() {
		this.stopped = false;
		this.deamon = new SpiderChartWorker();
		this.deamon.chart = this;
		new Thread(this.deamon).start();
	}

	/** Stops the execution of the chart refresh deamon */
	public void stopWorker() {
		this.stopped = true;
		if (this.deamon != null) {
			this.deamon.stop = true;
		}
		this.deamon = null;
	}

	/** Triggers the chart event */
	private void triggerChartEvent(final int event) {
		for (int i = 0; i < this.chartListeners.size(); i++) {
			this.chartListeners.get(i).onChartEvent(this, event);
		}
	}
}
