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

import java.util.List;
import java.util.Vector;
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
 * Actual Spider Chart
 *
 * @author AMIT KUMAR MONDAL
 *
 */
public final class SpiderChart {

	/**
	 * Background Worker Thread to refresh chart
	 *
	 * @author AMIT KUMAR MONDAL
	 *
	 */
	private final class SpiderChartWorker implements Runnable {

		/** */
		@SuppressWarnings("unused")
		private SpiderChart chart = null;

		/** */
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

	/** */
	private static String numberLocale;

	public static String getNumberLocale() {
		return numberLocale;
	}

	public static void setNumberLocale(final String numberLocale) {
		SpiderChart.numberLocale = numberLocale;
	}

	/** */
	private boolean activateSelection = false;

	/** Used to trigger thread automatically to build the Spider Chart */
	private boolean autoRebuild = true;

	/** Auto Sizeable Property */
	private boolean autoResize = true;

	/** */
	private double axisMargin = 0.0625D;

	/** */
	private String backgroundCanvasColor = AQUA;

	/** Spider Chart Back Image */
	private AbstractChartImage backImage;

	/** */
	private FillStyle backStyle = new FillStyle(getColor(AQUA));

	/** */
	private AbstractChartImage backTmpImage = null;

	/**  */
	private LineStyle border = null;

	/** */
	private final double bottomMargin = 0.125D;

	/** Spider Chart Image */
	private AbstractChartImage chartImage = null;

	/** */
	private final List<ISpiderChartListener> chartListeners = new CopyOnWriteArrayList<>();

	/** */
	private int currentX;

	/** */
	private int currentY;

	/** */
	private int cursorLastX = 0;

	/** */
	private int cursorLastY = 0;

	/** */
	private SpiderChartWorker deamon = null;

	/** */
	private final boolean doubleBuffering = true;

	/** */
	private AbstractChartImage finalImage = null;

	/** */
	private final Vector<IFloatingObject> floatingObjects = new Vector<>(0, 5);

	/** */
	private final boolean fullXAxis = false;

	/** */
	private int height = 0;

	/** */
	private int lastHeight = -1;

	/** */
	private int lastWidth = -1;

	/** Chart Left Margin */
	private final double leftMargin = 0.125D;

	/** Spider Chart Legend */
	private SpiderChartLegend legend;

	/** Spider Chart Legend Margin */
	private final double legendMargin = 0.2D;

	/** */
	private int minimumHeight = 0;

	/** */
	private int minimumWidth = 0;

	/** */
	private long msecs = 2000L;

	/** */
	private int offsetX = 0;

	/** */
	private int offsetY = 0;

	/** */
	private int originalVirtualHeight = -1;

	/** */
	private int originalVirtualWidth = -1;

	/** */
	private AbstractPlotter[] plotters = new SpiderChartPlotter[10];

	/** */
	private int plottersCount = 0;

	/** */
	private boolean repaintAll = true;

	/** */
	private boolean repaintAlways = true;

	/** */
	private final double rightMargin = 0.125D;

	/** */
	private SpiderChartLabel selectedLabel = null;

	/** */
	private DataSeq selectedSeq = null;

	/** */
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

	/** */
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

	/** */
	public void addChartListener(final ISpiderChartListener cl) {
		this.chartListeners.add(cl);
	}

	/** */
	public void addFloationgObject(final IFloatingObject obj) {
		this.floatingObjects.addElement(obj);
	}

	/** */
	public void addPlotter(final SpiderChartPlotter p) {
		this.plotters[this.plottersCount] = p;
		this.plotters[this.plottersCount].xScale = this.plotters[0].xScale;
		this.plotters[this.plottersCount].yScale = this.plotters[0].yScale;
		this.plottersCount += 1;
	}

	/** */
	public void addSeq(final DataSeq s) {
		this.plotters[0].addSeq(s);
	}

	/** */
	public void dispose() {
		for (int i = 0; i < this.plottersCount; i++) {
			if (this.plotters[i] != null) {
				for (int j = 0; j < this.plotters[i].getSeqCount(); j++) {
					if (this.plotters[i].getSeq(j) instanceof LineDataSeq) {
						final LineDataSeq lseq = (LineDataSeq) this.plotters[i].getSeq(j);
						if (lseq.icon != null) {
							lseq.icon.dispose();
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

	public double getAxisMargin() {
		return this.axisMargin;
	}

	public String getBackgroundCanvasColor() {
		return this.backgroundCanvasColor;
	}

	public AbstractChartImage getBackImage() {
		return this.backImage;
	}

	public FillStyle getBackStyle() {
		return this.backStyle;
	}

	public AbstractChartImage getBackTmpImage() {
		return this.backTmpImage;
	}

	public LineStyle getBorder() {
		return this.border;
	}

	public double getBottomMargin() {
		return this.bottomMargin;
	}

	public AbstractChartImage getChartImage() {
		return this.chartImage;
	}

	public List<ISpiderChartListener> getChartListeners() {
		return this.chartListeners;
	}

	public int getCurrentX() {
		return this.currentX;
	}

	public int getCurrentY() {
		return this.currentY;
	}

	public int getCursorLastX() {
		return this.cursorLastX;
	}

	public int getCursorLastY() {
		return this.cursorLastY;
	}

	public SpiderChartWorker getDeamon() {
		return this.deamon;
	}

	public AbstractChartImage getFinalImage() {
		return this.finalImage;
	}

	public Vector<IFloatingObject> getFloatingObjects() {
		return this.floatingObjects;
	}

	/** */
	public int getHeight() {
		return this.height;
	}

	public int getLastHeight() {
		return this.lastHeight;
	}

	public int getLastWidth() {
		return this.lastWidth;
	}

	public double getLeftMargin() {
		return this.leftMargin;
	}

	public SpiderChartLegend getLegend() {
		return this.legend;
	}

	public double getLegendMargin() {
		return this.legendMargin;
	}

	public int getMinimumHeight() {
		return this.minimumHeight;
	}

	public int getMinimumWidth() {
		return this.minimumWidth;
	}

	public long getMsecs() {
		return this.msecs;
	}

	public int getOffsetX() {
		return this.offsetX;
	}

	public int getOffsetY() {
		return this.offsetY;
	}

	public int getOriginalVirtualHeight() {
		return this.originalVirtualHeight;
	}

	public int getOriginalVirtualWidth() {
		return this.originalVirtualWidth;
	}

	public AbstractPlotter[] getPlotters() {
		return this.plotters;
	}

	public int getPlottersCount() {
		return this.plottersCount;
	}

	public double getRightMargin() {
		return this.rightMargin;
	}

	public SpiderChartLabel getSelectedLabel() {
		return this.selectedLabel;
	}

	public DataSeq getSelectedSeq() {
		return this.selectedSeq;
	}

	public int getSelectedSeqPoint() {
		return this.selectedSeqPoint;
	}

	/** */
	public SpiderChartPlotter getSpiderPlotter() {
		return (SpiderChartPlotter) this.plotters[0];
	}

	public AbstractChartColor getTipColor() {
		return this.tipColor;
	}

	public AbstractChartFont getTipFont() {
		return this.tipFont;
	}

	public AbstractChartColor getTipFontColor() {
		return this.tipFontColor;
	}

	public SpiderChartTitle getTitle() {
		return this.title;
	}

	public double getTopMargin() {
		return this.topMargin;
	}

	public int getVirtualHeight() {
		return this.virtualHeight;
	}

	public int getVirtualWidth() {
		return this.virtualWidth;
	}

	/** */
	public int getWidth() {
		return this.width;
	}

	public boolean isActivateSelection() {
		return this.activateSelection;
	}

	public boolean isAutoRebuild() {
		return this.autoRebuild;
	}

	public boolean isAutoResize() {
		return this.autoResize;
	}

	public boolean isDoubleBuffering() {
		return this.doubleBuffering;
	}

	public boolean isFullXAxis() {
		return this.fullXAxis;
	}

	public boolean isRepaintAll() {
		return this.repaintAll;
	}

	public boolean isRepaintAlways() {
		return this.repaintAlways;
	}

	public boolean isShowTips() {
		return this.showTips;
	}

	public boolean isStopped() {
		return this.stopped;
	}

	public boolean isWithScroll() {
		return this.withScroll;
	}

	/** */
	public void mouseClick() {
		if (((this.selectedSeq != null) && (this.selectedSeqPoint >= 0)) || (this.selectedLabel != null)) {
			this.triggerChartEvent(EVENT_POINT_CLICKED);
			return;
		}
		this.triggerChartEvent(EVENT_CHART_CLICKED);
	}

	/** */
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
						if (((Polygon) d.getHotAreas().elementAt(i)).contains(this.currentX + this.offsetX,
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

	/** */
	public void paint(final AbstractChartGraphics pg) {
		this.floatingObjects.removeAllElements();

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
		if (this.plotters[0].xScale != null) {
			this.plotters[0].xScale.screenMax = this.plotters[0].x + this.plotters[0].width;
			this.plotters[0].xScale.screenMaxMargin = (int) (this.plotters[0].xScale.screenMax
					* (1.0D - this.axisMargin));
			if (this.fullXAxis) {
				this.plotters[0].xScale.screenMaxMargin = this.plotters[0].xScale.screenMax;
			}
			this.plotters[0].xScale.screenMin = this.plotters[0].x;
		}
		if (this.plotters[0].yScale != null) {
			this.plotters[0].yScale.screenMax = this.plotters[0].y + this.plotters[0].height;
			this.plotters[0].yScale.screenMaxMargin = (int) (this.plotters[0].yScale.screenMax
					* (1.0D - this.axisMargin));
			this.plotters[0].yScale.screenMin = this.plotters[0].y;
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

			final int x2 = this.plotters[0].x + this.plotters[0].visibleWidth;

			final int y1 = this.plotters[0].y - this.plotters[0].depth;

			final int y2 = (this.plotters[0].y - this.plotters[0].depth) + this.plotters[0].visibleHeight;

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

	/** */
	private void paintTips(final AbstractChartGraphics g) {
		// TODO (AKM) To be implemented properly: Tips Functionality
		if (this.showTips && (this.selectedLabel != null)) {
			this.selectedLabel.getTip();
		}
	}

	/** */
	public void placeFloatingObject(final IFloatingObject obj) {
	}

	/** */
	public void removeAllChartListener() {
		this.chartListeners.clear();
	}

	/** */
	public void removeChartListener(final ISpiderChartListener cl) {
		this.chartListeners.remove(cl);
	}

	/** */
	public void removeChartPlotters() {
		for (int i = 0; i < this.plottersCount; i++) {
			this.plotters[i] = null;
		}
		this.plottersCount = 0;
	}

	/** */
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
		this.floatingObjects.removeAllElements();
		this.plotters[0] = p;
		this.title = t;
		if (this.title == null) {
			this.title = new SpiderChartTitle();
			this.title.text = "";
		}
		this.plottersCount = 1;
	}

	/** */
	private void resize() {
		final int myHeight = this.getHeight();
		final int myWidth = this.getWidth();
		if (this.virtualWidth < myWidth) {
			this.virtualWidth = myWidth;
		}
		if (this.virtualHeight < myHeight) {
			this.virtualHeight = myHeight;
		}
		this.plotters[0].visibleWidth = (int) (myWidth * (1.0D - (this.legendMargin + this.leftMargin)));
		this.plotters[0].visibleHeight = (int) (myHeight * (1.0D - (this.topMargin + this.bottomMargin)));

		this.plotters[0].x = (int) (myWidth * this.leftMargin);
		this.plotters[0].y = (int) (myHeight * this.topMargin);
		this.plotters[0].width = this.virtualWidth - (myWidth - this.plotters[0].visibleWidth);
		this.plotters[0].height = this.virtualHeight - (myHeight - this.plotters[0].visibleHeight);

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

	public void setActivateSelection(final boolean activateSelection) {
		this.activateSelection = activateSelection;
	}

	public void setAutoRebuild(final boolean autoRebuild) {
		this.autoRebuild = autoRebuild;
	}

	public void setAutoResize(final boolean autoResize) {
		this.autoResize = autoResize;
	}

	public void setAxisMargin(final double axisMargin) {
		this.axisMargin = axisMargin;
	}

	public void setBackgroundCanvasColor(final String backgroundCanvasColor) {
		this.backgroundCanvasColor = backgroundCanvasColor;
	}

	public void setBackImage(final AbstractChartImage backImage) {
		this.backImage = backImage;
	}

	public void setBackStyle(final FillStyle backStyle) {
		this.backStyle = backStyle;
	}

	public void setBackTmpImage(final AbstractChartImage backTmpImage) {
		this.backTmpImage = backTmpImage;
	}

	public void setBorder(final LineStyle border) {
		this.border = border;
	}

	public void setChartImage(final AbstractChartImage chartImage) {
		this.chartImage = chartImage;
	}

	public void setCurrentX(final int currentX) {
		this.currentX = currentX;
	}

	public void setCurrentY(final int currentY) {
		this.currentY = currentY;
	}

	public void setCursorLastX(final int cursorLastX) {
		this.cursorLastX = cursorLastX;
	}

	public void setCursorLastY(final int cursorLastY) {
		this.cursorLastY = cursorLastY;
	}

	public void setDeamon(final SpiderChartWorker deamon) {
		this.deamon = deamon;
	}

	public void setFinalImage(final AbstractChartImage finalImage) {
		this.finalImage = finalImage;
	}

	/** */
	public void setHeight(final int h) {
		if (h > this.minimumHeight) {
			this.height = h;
		}
	}

	public void setLastHeight(final int lastHeight) {
		this.lastHeight = lastHeight;
	}

	public void setLastWidth(final int lastWidth) {
		this.lastWidth = lastWidth;
	}

	public void setLegend(final SpiderChartLegend legend) {
		this.legend = legend;
	}

	public void setMinimumHeight(final int minimumHeight) {
		this.minimumHeight = minimumHeight;
	}

	/** */
	public void setMinimumSize(final int w, final int h) {
		this.minimumWidth = w;
		this.minimumHeight = h;
	}

	public void setMinimumWidth(final int minimumWidth) {
		this.minimumWidth = minimumWidth;
	}

	public void setMsecs(final long msecs) {
		this.msecs = msecs;
	}

	public void setOffsetX(final int offsetX) {
		this.offsetX = offsetX;
	}

	public void setOffsetY(final int offsetY) {
		this.offsetY = offsetY;
	}

	public void setOriginalVirtualHeight(final int originalVirtualHeight) {
		this.originalVirtualHeight = originalVirtualHeight;
	}

	public void setOriginalVirtualWidth(final int originalVirtualWidth) {
		this.originalVirtualWidth = originalVirtualWidth;
	}

	public void setPlotters(final AbstractPlotter[] plotters) {
		this.plotters = plotters;
	}

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

	public void setRepaintAll(final boolean repaintAll) {
		this.repaintAll = repaintAll;
	}

	public void setRepaintAlways(final boolean repaintAlways) {
		this.repaintAlways = repaintAlways;
	}

	public void setSelectedLabel(final SpiderChartLabel selectedLabel) {
		this.selectedLabel = selectedLabel;
	}

	public void setSelectedSeq(final DataSeq selectedSeq) {
		this.selectedSeq = selectedSeq;
	}

	public void setSelectedSeqPoint(final int selectedSeqPoint) {
		this.selectedSeqPoint = selectedSeqPoint;
	}

	public void setShowTips(final boolean showTips) {
		this.showTips = showTips;
	}

	/** */
	public void setSize(final int w, final int h) {
		this.setWidth(w);
		this.setHeight(h);
	}

	public void setStopped(final boolean stopped) {
		this.stopped = stopped;
	}

	public void setTitle(final SpiderChartTitle title) {
		this.title = title;
	}

	public void setVirtualHeight(final int virtualHeight) {
		this.virtualHeight = virtualHeight;
	}

	public void setVirtualWidth(final int virtualWidth) {
		this.virtualWidth = virtualWidth;
	}

	/** */
	public void setWidth(final int w) {
		if (w > this.minimumWidth) {
			this.width = w;
		}
	}

	public void setWithScroll(final boolean withScroll) {
		this.withScroll = withScroll;
	}

	/** */
	public void startWorker() {
		this.stopped = false;
		this.deamon = new SpiderChartWorker();
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
	private void triggerChartEvent(final int event) {
		for (int i = 0; i < this.chartListeners.size(); i++) {
			this.chartListeners.get(i).onChartEvent(this, event);
		}
	}
}
