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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import com.amitinside.tooling.chart.gc.SWTGraphicsSupplier;
import com.amitinside.tooling.chart.gc.SpiderChartColor;
import com.amitinside.tooling.chart.gc.SpiderChartFont;
import com.amitinside.tooling.chart.gc.SpiderChartGraphics;

/**
 * Spider Chart Axis
 *
 * @author AMIT KUMAR MONDAL
 */
public class SpiderChartAxis extends SpiderChartComponent {

	/** */
	public static final int CEROAXIS_LINE = 0;

	/** */
	public static final int CEROAXIS_NO = 1;

	/** */
	public static final int CEROAXIS_SCALE = 2;

	/** */
	public static final int HORIZONTAL = 0;

	/** */
	public static final int VERTICAL = 1;

	/** */
	protected Vector<SpiderChartAxis> additionalAxis = new Vector<>();

	/** */
	public int autoNumberOfTicks = 0;

	/** */
	public boolean axisFrame = false;

	/** */
	public FillStyle barFilling = null;

	/** */
	public int bigTickInterval = 1;

	/** */
	public int[] bigTickIntervalList;

	/** */
	public int bigTickPixels = 9;

	/** */
	public boolean bigTicksGrid = false;

	/** */
	public int ceroAxis = 0;

	/** */
	public LineStyle ceroAxisStyle = new LineStyle(0.2F, SWTGraphicsSupplier.getColor(SpiderChartColor.DARKGRAY), 1);

	/** */
	public String dateLabelFormat = "dd-MMM-yyyy";

	/** */
	public String dateStep = "d";

	/** */
	public boolean dateStepPerUnit = false;

	/** */
	public SpiderChartColor DescColor = SWTGraphicsSupplier.getColor(SpiderChartColor.BLACK);

	/** */
	public SpiderChartFont DescFont = SWTGraphicsSupplier.getFont("Arial", SpiderChartFont.PLAIN, 10);

	/** */
	public FillStyle gridFillStyle = null;

	/** */
	public LineStyle gridStyle = null;

	/** */
	public Date initialDate = null;

	/** */
	public boolean IntegerScale = false;

	/** */
	protected boolean isMainAxis = true;

	/** Axis Label */
	public String label = "";

	/** */
	public String labelTemplate = "";

	/** */
	public boolean logarithmicIntervals = false;

	/** */
	protected SpiderChartAxis mainAxis = null;

	/** */
	private int maxTickLabelLength = 0;

	/** */
	protected int offset;

	/** */
	public int orientation;

	/** */
	public SpiderChartAxis peerAxis;

	/** */
	public SpiderChartPlotter plot;

	/** */
	protected int realPosition;

	/** */
	protected boolean rightAxis = false;

	/** */
	public int rotateLabels = 0;

	/** */
	public Scale scale;

	/** */
	public String scaleLabelFormat = "";

	/** */
	public double scaleTickInterval = 1.0D;

	/** */
	public boolean stackAdditionalAxis = false;

	/** */
	public boolean startWithBigTick = false;

	/** */
	public LineStyle style = new LineStyle(2.0F, SWTGraphicsSupplier.getColor(SpiderChartColor.BLACK), 1);

	/** */
	protected Vector<SpiderChartAxisZone> targetZones = new Vector<>();

	/** */
	public boolean tickAtBase = false;

	/** */
	public int tickLabelLength = 1000;

	/** */
	public String[] tickLabels = null;

	/** */
	public int tickPixels = 4;

	/** */
	public double[] ticks_preferred_Interval = { 0.1D, 0.5D, 1.0D, 5.0D, 10.0D, 25.0D, 50.0D, 100.0D, 250.0D, 500.0D,
			1000.0D, 5000.0D, 10000.0D, 50000.0D, 100000.0D, 500000.0D, 1000000.0D };

	/** */
	protected int visibleSize = 0;

	/** */
	public boolean xscaleOnTop = false;

	/**
	 * Constructor
	 */
	public SpiderChartAxis(final int o, final Scale s) {
		this.orientation = o;
		this.scale = s;
		if (o == 1) {
			this.scale.reverse = true;
		}
	}

	/** */
	public void addAdditionalAxis(final SpiderChartAxis axis) {
		axis.isMainAxis = false;
		axis.mainAxis = this;
		this.additionalAxis.addElement(axis);
	}

	/** */
	public void addTargetZone(final SpiderChartAxisZone zone) {
		if (zone != null) {
			this.targetZones.addElement(zone);
		}
	}

	/** */
	public void draw(final SpiderChartGraphics g, final SpiderChartAxis peerAxis, final boolean drawForeGround,
			final boolean drawGridBackground) {
		double range = this.scale.max - this.scale.min;

		int numberBigTicks = 0;
		int numberTicks = 0;
		if ((this.scale.min < 0.0D) && (this.scale.max < 0.0D)) {
			range = (this.scale.min - this.scale.max) * -1.0D;
		}
		int bigTickCount = 0;
		int axisPosition = 0;
		int axisCeroPosition = 0;
		boolean drawTick = true;
		int effect3D = this.plot.depth;
		if (!this.isMainAxis && !this.mainAxis.stackAdditionalAxis) {
			effect3D = 0;
		}
		int lastGridPosition = 0;
		boolean fillGrid = false;

		double usedTickInterval = this.scaleTickInterval;
		if (this.autoNumberOfTicks > 0) {
			usedTickInterval = Math.abs(range / this.autoNumberOfTicks);

			int proposedNumberOfTicks = 0;
			double bestTickInterval = usedTickInterval;
			if ((this.ticks_preferred_Interval != null) && (this.ticks_preferred_Interval.length > 0)) {
				for (final double element : this.ticks_preferred_Interval) {
					if (proposedNumberOfTicks == 0) {
						proposedNumberOfTicks = (int) Math.abs(range / element);
					}
					final int newProposedNumberOfTicks = (int) Math.abs(range / element);
					if (Math.abs(newProposedNumberOfTicks - this.autoNumberOfTicks) < Math
							.abs(proposedNumberOfTicks - this.autoNumberOfTicks)) {
						proposedNumberOfTicks = newProposedNumberOfTicks;
						bestTickInterval = element;
						usedTickInterval = bestTickInterval;
					}
				}
			}
		}
		if ((peerAxis.scale.min < 0.0D) && (peerAxis.scale.max > 0.0D)) {
			axisCeroPosition = peerAxis.scale.getScreenCoord(0.0D);
		}
		if (this.orientation == 0) {
			if ((this.ceroAxis == 2) && (axisCeroPosition != 0)) {
				axisPosition = axisCeroPosition;
			} else {
				axisPosition = this.realPosition;
			}
			if (drawForeGround) {
				if (!this.xscaleOnTop || this.axisFrame) {
					this.style.draw(g, this.x, axisPosition, this.x + this.visibleSize, axisPosition);
					if (!this.xscaleOnTop) {
						if (this.label.length() > 0) {
							final SpiderChartLabel clabel = new SpiderChartLabel(this.label, "", false, false);
							g.setColor(this.DescColor);
							g.setFont(this.DescFont);
							clabel.initialize(g, this.chart);
							if (this.mainAxis.stackAdditionalAxis) {
								clabel.paint(g, this.x, (this.y + this.height) - clabel.requiredHeight, 0, 0);
							} else {
								clabel.paint(g, this.x - clabel.requiredWidth - 4, axisPosition, 0, 0);
							}
						}
					}
				}
				if ((this.axisFrame || this.xscaleOnTop) && (peerAxis != null) && (effect3D == 0)) {
					this.style.draw(g, this.x, axisPosition - peerAxis.visibleSize, this.x + this.visibleSize,
							axisPosition - peerAxis.visibleSize);
					if (this.xscaleOnTop) {
						if (this.label.length() > 0) {
							final SpiderChartLabel clabel = new SpiderChartLabel(this.label, "", false, false);
							g.setColor(this.DescColor);
							g.setFont(this.DescFont);
							clabel.initialize(g, this.chart);
							if (this.mainAxis.stackAdditionalAxis) {
								clabel.paint(g, this.x, this.y, 0, 0);
							}
						}
					}
				}
			}
			if (!drawForeGround && !drawGridBackground) {
				final int[] xs = new int[4];
				final int[] ys = new int[4];
				peerAxis.scale.getScreenCoord(peerAxis.scale.min);
				peerAxis.scale.getScreenCoord(peerAxis.scale.max);

				xs[0] = this.x + effect3D + this.offset;
				ys[0] = (axisPosition + peerAxis.offset) - effect3D;
				xs[1] = this.x + this.visibleSize + effect3D + this.offset;
				ys[1] = (axisPosition + peerAxis.offset) - effect3D;
				xs[2] = this.x + this.visibleSize + this.offset;
				ys[2] = axisPosition + peerAxis.offset;
				xs[3] = this.x + this.offset;
				ys[3] = axisPosition + peerAxis.offset;
				if (!this.rightAxis) {
					if (effect3D != 0) {
						this.style.draw(g, xs[0], ys[0], xs[1], ys[1]);
					}
					if (effect3D != 0) {
						this.style.draw(g, xs[2], ys[2], xs[1], ys[1]);
					}
					if (effect3D != 0) {
						this.style.draw(g, xs[3], ys[3], xs[0], ys[0]);
					}
					if (effect3D != 0) {
						this.style.draw(g, xs[0], (this.plot.y - effect3D) + peerAxis.offset, xs[1],
								(this.plot.y - effect3D) + peerAxis.offset);
					}
				}
			}
			if ((axisCeroPosition != 0) && (this.ceroAxis == 0) && !drawForeGround) {
				this.ceroAxisStyle.draw(g, this.x, axisCeroPosition, this.x + this.width, axisCeroPosition);
				if (!this.rightAxis) {
					if (effect3D != 0) {
						this.ceroAxisStyle.draw(g, this.x + effect3D, axisCeroPosition - effect3D,
								this.x + this.width + effect3D, axisCeroPosition - effect3D);
					}
					if (effect3D != 0) {
						this.ceroAxisStyle.draw(g, this.x, axisCeroPosition, this.x + effect3D,
								axisCeroPosition - effect3D);
					}
					if (effect3D != 0) {
						this.ceroAxisStyle.draw(g, this.x + this.width, axisCeroPosition,
								this.x + this.width + effect3D, axisCeroPosition - effect3D);
					}
				}
			}
		} else {
			if ((this.ceroAxis == 2) && (axisCeroPosition != 0)) {
				axisPosition = axisCeroPosition;
			} else if (!this.isMainAxis && this.rightAxis) {
				axisPosition = this.x;
			} else if (this.rightAxis) {
				axisPosition = this.realPosition;
			} else {
				axisPosition = this.x + this.width;
			}
			if (drawForeGround) {
				this.style.draw(g, axisPosition, this.y, axisPosition, this.y + this.visibleSize);
				if (this.axisFrame && (peerAxis != null) && (effect3D == 0) && !this.rightAxis) {
					this.style.draw(g, axisPosition + peerAxis.visibleSize, this.y, axisPosition + peerAxis.visibleSize,
							this.y + this.visibleSize);
				}
				if (!this.rightAxis) {
					if (this.label.length() > 0) {
						final SpiderChartLabel clabel = new SpiderChartLabel(
								"@rotation value='90' center='LEFTTOP'@" + this.label, "", false, false);
						g.setColor(this.DescColor);
						g.setFont(this.DescFont);
						clabel.initialize(g, this.chart);
						if (this.mainAxis.stackAdditionalAxis) {
							clabel.paint(g, this.x, this.y, 0, 0);
						}
					}
				}
				if (this.rightAxis) {
					if (this.label.length() > 0) {
						final SpiderChartLabel clabel = new SpiderChartLabel(
								"@rotation value='90'  center='LEFTTOP'@" + this.label, "", false, false);
						g.setColor(this.DescColor);
						g.setFont(this.DescFont);
						clabel.initialize(g, this.chart);
						if (this.mainAxis.stackAdditionalAxis) {
							clabel.paint(g, (axisPosition + this.width) - clabel.requiredHeight, this.y, 0, 0);
						} else {
							clabel.paint(g, axisPosition, this.y + this.visibleSize + 4, 0, 0);
						}
					}
				}
			}
			if (!drawForeGround && !drawGridBackground) {
				final int[] xs = new int[4];
				final int[] ys = new int[4];
				final int[] xs2 = new int[4];
				final int[] ys2 = new int[4];
				peerAxis.scale.getScreenCoord(peerAxis.scale.min);
				peerAxis.scale.getScreenCoord(peerAxis.scale.max);

				xs[0] = axisPosition + peerAxis.offset + effect3D;
				ys[0] = (this.y - effect3D) + this.offset;
				xs[1] = axisPosition + peerAxis.offset + effect3D;
				ys[1] = ((this.y + this.visibleSize) - effect3D) + this.offset;
				xs[2] = axisPosition + peerAxis.offset;
				ys[2] = this.y + this.visibleSize + this.offset;
				xs[3] = axisPosition + peerAxis.offset;
				ys[3] = this.y + this.offset;

				xs2[0] = axisPosition + peerAxis.visibleSize + effect3D + peerAxis.offset;
				ys2[0] = ys[0];
				xs2[1] = axisPosition + peerAxis.visibleSize + effect3D + peerAxis.offset;
				ys2[1] = ys[1];
				xs2[2] = xs[1];
				ys2[2] = ys[1];
				xs2[3] = xs[0];
				ys2[3] = ys[0];
				if (!this.rightAxis) {
					if (effect3D != 0) {
						this.style.draw(g, xs[0], ys[0], xs[1], ys[1]);
					}
					if (effect3D != 0) {
						this.style.draw(g, xs[2], ys[2], xs[1], ys[1]);
					}
					if (effect3D != 0) {
						this.style.draw(g, xs[3], ys[3], xs[0], ys[0]);
					}
					if (effect3D != 0) {
						this.style.draw(g, xs2[0], ys2[0], xs2[1], ys2[1]);
					}
				}
			}
			if ((axisCeroPosition != 0) && (this.ceroAxis == 0) && !drawForeGround) {
				this.ceroAxisStyle.draw(g, axisCeroPosition, this.y, axisCeroPosition, this.y + this.height);
				if (!this.rightAxis) {
					if (effect3D != 0) {
						this.ceroAxisStyle.draw(g, axisCeroPosition + effect3D, this.y - effect3D,
								axisCeroPosition + effect3D, (this.y + this.height) - effect3D);
					}
					if (effect3D != 0) {
						this.ceroAxisStyle.draw(g, axisCeroPosition, this.y + this.height, axisCeroPosition + effect3D,
								(this.y + this.height) - effect3D);
					}
					if (effect3D != 0) {
						this.ceroAxisStyle.draw(g, axisCeroPosition, this.y, axisCeroPosition + effect3D,
								this.y - effect3D);
					}
				}
			}
		}
		final double tickBase = this.scale.min;

		int iteration = 0;
		if (this.logarithmicIntervals) {
		}
		if (this.startWithBigTick) {
			bigTickCount = this.bigTickInterval - 1;
		}
		Date tickDate = null;
		if (this.initialDate != null) {
			tickDate = this.initialDate;
		}
		double startValue = tickBase;
		if (startValue == 0.0D) {
			startValue = 1.0D;
		}
		for (double i = tickBase; i <= this.scale.max;) {
			if (iteration > 1000) {
				break;
			}
			iteration++;
			boolean nowBigTick = false;
			if ((iteration > 1) || this.tickAtBase) {
				numberTicks++;
				bigTickCount++;

				final int v = this.scale.getScreenCoord(i);
				if ((this.bigTickIntervalList == null) && (bigTickCount == this.bigTickInterval)) {
					nowBigTick = true;
				}
				if (this.bigTickIntervalList != null) {
					for (final int element : this.bigTickIntervalList) {
						if (numberTicks == element) {
							nowBigTick = true;
						}
					}
				}
				int tickLength;
				if (nowBigTick) {
					bigTickCount = 0;

					tickLength = this.bigTickPixels;
				} else {
					tickLength = this.tickPixels;
				}
				drawTick = true;
				if (drawTick) {
					if ((i != this.scale.min) && !drawForeGround && drawGridBackground) {
						if ((this.gridFillStyle != null) && ((bigTickCount == 0) || !this.bigTicksGrid)) {
							if (this.orientation == 0) {
								if (fillGrid) {
									this.gridFillStyle.draw(g, lastGridPosition + 1, this.plot.y - effect3D,
											v + effect3D, (this.plot.y + this.plot.height) - effect3D);
								}
								lastGridPosition = v + effect3D;
							} else {
								if (fillGrid) {
									this.gridFillStyle.draw(g, this.plot.x + effect3D, (v - effect3D) + 1,
											this.plot.x + this.plot.width + effect3D, lastGridPosition);
								}
								lastGridPosition = v - effect3D;
							}
							fillGrid = !fillGrid;
						}
					}
					if ((i != this.scale.min) && !drawForeGround && !drawGridBackground) {
						if ((this.gridStyle != null) && ((bigTickCount == 0) || !this.bigTicksGrid)) {
							if ((this.ceroAxis != 0) || (i != 0.0D)) {
								if (this.orientation == 0) {
									if (effect3D != 0) {
										this.gridStyle.draw(g, v, this.plot.y + this.plot.height, v + effect3D,
												(this.plot.y + this.plot.height) - effect3D);
									}
									this.gridStyle.draw(g, v + effect3D, this.plot.y - effect3D, v + effect3D,
											(this.plot.y + this.plot.height) - effect3D);
								} else {
									this.gridStyle.draw(g, this.plot.x + effect3D, v - effect3D,
											this.plot.x + this.plot.width + effect3D, v - effect3D);
									if (effect3D != 0) {
										this.gridStyle.draw(g, this.plot.x, v, this.plot.x + effect3D, v - effect3D);
									}
								}
							}
						}
					}
				}
				if (drawForeGround) {
					if (drawTick) {
						if (this.orientation == 0) {
							if (((v - this.offset) >= this.x) && ((v - this.offset) <= (this.visibleSize + this.x))) {
								if (!this.xscaleOnTop) {
									this.style.draw(g, v - this.offset, axisPosition, v - this.offset,
											axisPosition + tickLength);
								} else {
									this.style.draw(g, v - this.offset, axisPosition - peerAxis.visibleSize,
											v - this.offset, axisPosition - peerAxis.visibleSize - tickLength);
								}
							}
						} else if (this.rightAxis) {
							if (((v - this.offset) >= this.y) && ((v - this.offset) <= (this.visibleSize + this.y))) {
								this.style.draw(g, axisPosition, v - this.offset, axisPosition + tickLength,
										v - this.offset);
							}
						} else if (((v - this.offset) >= this.y)
								&& ((v - this.offset) <= (this.visibleSize + this.y))) {
							this.style.draw(g, axisPosition - tickLength, v - this.offset, axisPosition,
									v - this.offset);
						}
					}
					if (bigTickCount == 0) {
						numberBigTicks++;
						g.setColor(this.DescColor);
						g.setFont(this.DescFont);

						String txt = new Double(i).toString();
						if (this.scaleLabelFormat.length() > 0) {
							DecimalFormat df = null;
							if (SpiderChart.numberLocale == null) {
								df = new DecimalFormat(this.scaleLabelFormat);
							} else {
								final NumberFormat nf = NumberFormat
										.getNumberInstance(new Locale(SpiderChart.numberLocale, ""));
								df = (DecimalFormat) nf;
								df.applyPattern(this.scaleLabelFormat);
							}
							txt = df.format(new Double(i));
						}
						if (this.IntegerScale) {
							txt = new Integer((int) i).toString();
						}
						if (tickDate != null) {
							txt = new SimpleDateFormat(this.dateLabelFormat).format(tickDate);
						}
						SpiderChartLabel formattedlabel = null;
						if (this.tickLabels != null) {
							if (this.tickLabels.length >= numberBigTicks) {
								if (this.tickLabels[numberBigTicks - 1] != null) {
									formattedlabel = new SpiderChartLabel(this.tickLabels[numberBigTicks - 1], txt,
											false, true);
									txt = this.tickLabels[numberBigTicks - 1];
								} else {
									txt = " ";
								}
							} else {
								txt = " ";
							}
						}
						if ((formattedlabel == null) && (this.labelTemplate.length() > 0)) {
							formattedlabel = new SpiderChartLabel(this.labelTemplate, txt, false, false);
						}
						boolean labelPainted = false;
						if ((this.rotateLabels != 0) && (this.orientation == 0) && ((v - this.offset) >= this.x)
								&& ((v - this.offset) <= (this.visibleSize + this.x))) {
							final int textWidth = g.getFontWidth(this.DescFont, txt);
							if (!this.xscaleOnTop) {
								labelPainted = g.drawRotatedText(this.DescFont, this.DescColor, txt, this.rotateLabels,
										v - this.offset, this.realPosition + tickLength, false);
							} else {
								labelPainted = g.drawRotatedText(this.DescFont, this.DescColor, txt, this.rotateLabels,
										v - this.offset,
										this.realPosition - this.bigTickPixels - peerAxis.visibleSize - textWidth - 2,
										false);
							}
							if (this.maxTickLabelLength < textWidth) {
								this.maxTickLabelLength = textWidth;
							}
						}
						if ((this.rotateLabels != 0) && (this.orientation == 1) && ((v - this.offset) >= this.y)
								&& ((v - this.offset) <= (this.visibleSize + this.y))) {
							int lblx = 0;
							int lbly = 0;

							final int textHeight = g.getFontHeight(this.DescFont);
							if (this.rightAxis) {
								lblx = this.realPosition + tickLength + 1;
							} else {
								lblx = (this.x + this.width) - tickLength - g.getFontHeight(null);
							}
							lbly = v - (g.getFontWidth(null, txt) / 2);

							labelPainted = g.drawRotatedText(this.DescFont, this.DescColor, txt, this.rotateLabels,
									lblx, lbly - this.offset, false);
							if (this.maxTickLabelLength < textHeight) {
								this.maxTickLabelLength = textHeight;
							}
						}
						if (!labelPainted && drawTick && (formattedlabel != null)) {
							formattedlabel.initialize(g, this.chart);
							labelPainted = true;

							final int lblw = formattedlabel.requiredWidth;
							int lblx = 0;
							int lbly = 0;
							if (this.orientation == 1) {
								lbly = v - formattedlabel.requiredHeight;
								if (((lbly - this.offset) >= this.y)
										&& ((lbly - this.offset) <= (this.visibleSize + this.y))) {
									formattedlabel.paint(g, lblx, lbly - this.offset, formattedlabel.requiredWidth,
											formattedlabel.requiredHeight);
								}
								if (this.maxTickLabelLength < formattedlabel.getRotatedWidth()) {
									this.maxTickLabelLength = formattedlabel.getRotatedWidth();
								}
							} else {
								lblx = v - (lblw / 2);
								lbly = this.realPosition + tickLength;
								if (((v - this.offset) >= this.x)
										&& ((v - this.offset) <= (this.visibleSize + this.x))) {
									formattedlabel.paint(g, lblx - this.offset, lbly, formattedlabel.requiredWidth,
											formattedlabel.requiredHeight);
								}
								if (this.maxTickLabelLength < formattedlabel.getRotatedHeight()) {
									this.maxTickLabelLength = formattedlabel.getRotatedHeight();
								}
							}
						}
						if (!labelPainted && drawTick) {
							final String[] txts = this.splitText(txt);

							final int lblw = g.getFontWidth(null, txts[0] + " ");
							final int lblh = g.getFontHeight(null);
							int lblx = 0;
							int lbly = 0;
							if (this.orientation == 1) {
								lbly = v;
								if (((lbly - this.offset) >= this.y)
										&& ((lbly - this.offset) <= (this.visibleSize + this.y))) {
									g.drawString(txts[0], lblx, lbly - this.offset);
								}
								for (int h = 1; h < txts.length; h++) {
									final int lblw1 = g.getFontWidth(null, txts[h]);
									if (this.rightAxis) {
										lblx = this.realPosition + tickLength + 1;
									} else {
										lblx = (this.x + this.width) - tickLength - lblw1;
									}
									if (((lbly - this.offset) >= this.y)
											&& ((lbly - this.offset) <= (this.visibleSize + this.y))) {
										g.drawString(txts[h], lblx, (lbly - this.offset) + (lblh * h));
									}
								}
							} else {
								lblx = v - (lblw / 2);
								lbly = this.realPosition + tickLength + lblh;
								if (((v - this.offset) >= this.x)
										&& ((v - this.offset) <= (this.visibleSize + this.x))) {
									g.drawString(txts[0], lblx - this.offset, lbly);
								}
								for (int h = 1; h < txts.length; h++) {
									final int lblw1 = g.getFontWidth(null, txts[h]);
									lblx = v - (lblw1 / 2);
									if (((v - this.offset) >= this.x)
											&& ((v - this.offset) <= (this.visibleSize + this.x))) {
										g.drawString(txts[h], lblx - this.offset, lbly + (lblh * h));
									}
								}
							}
						}
					}
				}
			}
			{
				i += usedTickInterval;

				i = Math.rint(i * 100000.0D) / 100000.0D;
				if (tickDate != null) {
					int step = 1;
					if (this.dateStep.length() > 1) {
						step = new Integer(this.dateStep.substring(1, this.dateStep.length())).intValue();
					}
					if (this.dateStepPerUnit) {
						step = (int) (step * usedTickInterval);
					}
					final Calendar c = Calendar.getInstance();
					c.setTime(tickDate);
					if (this.dateStep.toLowerCase().indexOf("d") == 0) {
						c.add(5, step);
					}
					if (this.dateStep.toLowerCase().indexOf("m") == 0) {
						c.add(2, step);
					}
					if (this.dateStep.toLowerCase().indexOf("w") == 0) {
						c.add(5, step * 7);
					}
					if (this.dateStep.toLowerCase().indexOf("y") == 0) {
						c.add(1, step);
					}
					if (this.dateStep.toLowerCase().indexOf("h") == 0) {
						c.add(10, step);
					}
					if (this.dateStep.toLowerCase().indexOf("s") == 0) {
						c.add(13, step);
					}
					if (this.dateStep.toLowerCase().indexOf("n") == 0) {
						c.add(12, step);
					}
					tickDate = c.getTime();
				}
			}
		}
	}

	/** */
	protected void drawBackground(final SpiderChartGraphics g, final SpiderChartAxis peerAxis) {
		this.maxTickLabelLength = 0;

		int tmpX = this.x;
		int tmpY = this.y;
		int realPositionY = this.realPosition;
		if (this.isMainAxis && !this.xscaleOnTop) {
			for (int i = 0; i < this.additionalAxis.size(); i++) {
				final SpiderChartAxis a = this.additionalAxis.elementAt(i);
				a.mainAxis = this;
				if (this.rightAxis) {
					a.rightAxis = true;
				}
				if (this.xscaleOnTop) {
					a.xscaleOnTop = true;
				}
				if (!this.stackAdditionalAxis) {
					if ((this.orientation == 0) && !this.xscaleOnTop) {
						tmpY += this.height;
						realPositionY += this.height;
						a.realPosition = realPositionY;
					}
					if ((this.orientation == 0) && this.xscaleOnTop) {
						tmpY -= this.height;
						realPositionY -= this.height;
						a.realPosition = realPositionY;
					}
					if ((this.orientation == 1) && !this.rightAxis) {
						tmpX -= this.width;
					}
					if ((this.orientation == 1) && this.rightAxis) {
						tmpX += this.width;
					}
					a.x = tmpX;
					a.y = tmpY;
					a.width = this.width;
					a.height = this.height;
					a.scale.screenMax = this.scale.screenMax;
					a.scale.screenMaxMargin = this.scale.screenMaxMargin;
					a.scale.screenMin = this.scale.screenMin;
				} else if (this.orientation == 1) {
					a.x = this.x;
				} else {
					a.y = this.y;
				}
				a.chart = this.chart;
				a.plot = this.plot;
				a.isMainAxis = false;
				a.visibleSize = this.visibleSize;
				a.draw(g, peerAxis, false, false);
			}
		}
		this.mainAxis = this;
		this.draw(g, peerAxis, false, false);
	}

	/** */
	protected void drawForeground(final SpiderChartGraphics g, final SpiderChartAxis peerAxis) {
		if (this.isMainAxis && !this.xscaleOnTop) {
			for (int i = 0; i < this.additionalAxis.size(); i++) {
				final SpiderChartAxis a = this.additionalAxis.elementAt(i);
				a.draw(g, peerAxis, true, false);
			}
		}
		this.draw(g, peerAxis, true, false);
	}

	/** */
	protected void drawGridBackground(final SpiderChartGraphics g, final SpiderChartAxis peerAxis) {
		this.draw(g, peerAxis, false, true);
	}

	/** */
	protected SpiderChartAxis getAdditionalAxis(final int i) {
		return this.additionalAxis.elementAt(i);
	}

	/** */
	protected int getAdditionalAxisCount() {
		return this.additionalAxis.size();
	}

	/** */
	public SpiderChartAxisZone[] getTargetZones() {
		final SpiderChartAxisZone[] a = new SpiderChartAxisZone[this.targetZones.size()];
		for (int i = 0; i < a.length; i++) {
			a[i] = this.targetZones.elementAt(i);
		}
		return a;
	}

	/** */
	protected void paintTargetZones(final SpiderChartGraphics g, final SpiderChartAxis peerAxis, final int position) {
		for (int i = 0; i < this.targetZones.size(); i++) {
			final SpiderChartAxisZone z = this.targetZones.elementAt(i);
			z.chart = this.chart;
			z.paint(g, this, peerAxis, position);
		}
	}

	/** */
	public void removeTargetZones() {
		this.targetZones.removeAllElements();
	}

	/** */
	private String[] splitText(String s) {
		final String[] r = new String[100];
		int lines = 0;
		if (this.tickLabelLength == 0) {
			this.tickLabelLength = 1000;
		}
		while ((s.indexOf("\\n") > -1) || (s.length() > this.tickLabelLength)) {
			if ((s.indexOf("\\n") > -1) && (s.indexOf("\\n") < this.tickLabelLength)) {
				r[lines] = s.substring(0, s.indexOf("\\n"));
				s = s.substring(s.indexOf("\\n") + 2, s.length());
			} else {
				r[lines] = s.substring(0, this.tickLabelLength);
				s = s.substring(this.tickLabelLength, s.length());
			}
			lines++;
			if (lines >= r.length) {
				break;
			}
		}
		r[lines] = s;

		final String[] a = new String[lines + 1];
		for (int i = 0; i <= lines; i++) {
			a[i] = r[i];
		}
		return a;
	}
}
