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
package com.amitinside.tooling.chart.sequence;

import static com.amitinside.tooling.chart.api.annotations.processor.SpiderChartAnnotationProcessor.getAreaColor;
import static com.amitinside.tooling.chart.gc.AbstractChartColor.BLACK;
import static com.amitinside.tooling.chart.gc.AbstractChartFont.BOLD;
import static com.amitinside.tooling.chart.gc.AbstractGraphicsSupplier.getColor;
import static com.amitinside.tooling.chart.gc.AbstractGraphicsSupplier.getFont;
import static com.amitinside.tooling.chart.gc.Fonts.VERDANA;
import static com.amitinside.tooling.chart.style.LineStyle.NORMAL_LINE;

import com.amitinside.tooling.chart.gc.AbstractChartColor;
import com.amitinside.tooling.chart.gc.AbstractChartFont;
import com.amitinside.tooling.chart.gc.AbstractChartImage;
import com.amitinside.tooling.chart.style.FillStyle;
import com.amitinside.tooling.chart.style.LineStyle;

public final class LineDataSeq extends DataSeq {

	/** */
	private static final int STARTING_X_VALUE = 0;

	/**
	 * Static Factory to create instance of {@link LineDataSeq}
	 */
	public static LineDataSeq of(final LineStyle style, final double... values) {
		return new LineDataSeq(values, style);
	}

	/**
	 * Static Factory to create instance of {@link LineDataSeq}
	 */
	public static LineDataSeq of(final Object oldObjectToCopyData, final double... values) {
		final LineDataSeq seq = new LineDataSeq(values,
				new LineStyle(2, getColor(getAreaColor(oldObjectToCopyData)), NORMAL_LINE));
		seq.valueFont = getFont(VERDANA, BOLD, 12);
		seq.fillStyle = new FillStyle(getColor(getAreaColor(oldObjectToCopyData)), 0.5f);
		seq.drawPoint = true;
		return seq;
	}

	/** */
	private boolean drawPoint = false;
	/** */
	private FillStyle fillStyle = null;
	/** */
	private AbstractChartImage icon = null;
	/** */
	private int lineType = 0;
	/** */
	private AbstractChartColor pointColor = getColor(BLACK);
	/** */
	private LineStyle style = null;
	/** */
	private AbstractChartColor valueColor = getColor(BLACK);
	/** */
	private AbstractChartFont valueFont = null;

	/** */
	private LineStyle vstyle = null;

	/** Constructor */
	public LineDataSeq(final double[] x, final double[] y, final LineStyle s) {
		super(x, y);
		this.style = s;
	}

	/** Constructor */
	public LineDataSeq(final double[] y, final LineStyle s) {
		super(y, STARTING_X_VALUE);
		this.style = s;
	}

	/** Constructor */
	public LineDataSeq(final Double[] x, final Double[] y, final LineStyle s) {
		super(x, y);
		this.style = s;
	}

	/** Constructor */
	public LineDataSeq(final Double[] y, final LineStyle s) {
		super(y, STARTING_X_VALUE);
		this.style = s;
	}

	/** Constructor */
	public LineDataSeq(final LineStyle s) {
		this.style = s;
	}

	/**
	 * @return the fillStyle
	 */
	public FillStyle getFillStyle() {
		return this.fillStyle;
	}

	/**
	 * @return the icon
	 */
	public AbstractChartImage getIcon() {
		return this.icon;
	}

	/**
	 * @return the lineType
	 */
	public int getLineType() {
		return this.lineType;
	}

	/**
	 * @return the pointColor
	 */
	public AbstractChartColor getPointColor() {
		return this.pointColor;
	}

	/**
	 * @return the style
	 */
	public LineStyle getStyle() {
		return this.style;
	}

	/**
	 * @return the valueColor
	 */
	public AbstractChartColor getValueColor() {
		return this.valueColor;
	}

	/**
	 * @return the valueFont
	 */
	public AbstractChartFont getValueFont() {
		return this.valueFont;
	}

	/**
	 * @return the vstyle
	 */
	public LineStyle getVstyle() {
		return this.vstyle;
	}

	/**
	 * @return the drawPoint
	 */
	public boolean isDrawPoint() {
		return this.drawPoint;
	}

	/**
	 * @param drawPoint
	 *            the drawPoint to set
	 */
	public void setDrawPoint(final boolean drawPoint) {
		this.drawPoint = drawPoint;
	}

	/**
	 * @param fillStyle
	 *            the fillStyle to set
	 */
	public void setFillStyle(final FillStyle fillStyle) {
		this.fillStyle = fillStyle;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(final AbstractChartImage icon) {
		this.icon = icon;
	}

	/**
	 * @param lineType
	 *            the lineType to set
	 */
	public void setLineType(final int lineType) {
		this.lineType = lineType;
	}

	/**
	 * @param pointColor
	 *            the pointColor to set
	 */
	public void setPointColor(final AbstractChartColor pointColor) {
		this.pointColor = pointColor;
	}

	/**
	 * @param style
	 *            the style to set
	 */
	public void setStyle(final LineStyle style) {
		this.style = style;
	}

	/**
	 * @param valueColor
	 *            the valueColor to set
	 */
	public void setValueColor(final AbstractChartColor valueColor) {
		this.valueColor = valueColor;
	}

	/**
	 * @param valueFont
	 *            the valueFont to set
	 */
	public void setValueFont(final AbstractChartFont valueFont) {
		this.valueFont = valueFont;
	}

	/**
	 * @param vstyle
	 *            the vstyle to set
	 */
	public void setVstyle(final LineStyle vstyle) {
		this.vstyle = vstyle;
	}
}
