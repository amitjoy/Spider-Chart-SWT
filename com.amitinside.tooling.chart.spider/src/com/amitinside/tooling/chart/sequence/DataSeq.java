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

import static java.text.NumberFormat.getNumberInstance;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.amitinside.tooling.chart.SpiderChart;

/**
 * Class representing sequence of points to be used for plotting
 *
 * @author AMIT KUMAR MONDAL
 *
 */
public abstract class DataSeq {

	/** Data Point Labels */
	private String[] dataLabels;

	/** Areas to be used for finding the points in the chart */
	private List<Object> hotAreas = new ArrayList<>();

	/** Label Template */
	private String labelTemplate = "";

	/** Descriptive Name for the Data Sequence */
	private String name = "";

	/** Tips to be used on the Data Sequence */
	private String[] tips = new String[0];

	/** Values to be formatted for the Data Sequence */
	private String valueFormat = "######.##";

	/** x-axis data points */
	private List<Double> xData = new ArrayList<>();

	/** y-axis data points */
	private List<Double> yData = new ArrayList<>();

	/**
	 * Constructor
	 */
	public DataSeq() {
	}

	/** Constructor */
	public DataSeq(final double[] y) {
		if (y == null) {
			return;
		}
		for (int i = 0; i < y.length; i++) {
			this.addData(new Double(i), new Double(y[i]));
		}
	}

	/** Constructor */
	public DataSeq(final double[] x, final double[] y) {
		for (int i = 0; i < x.length; i++) {
			this.addData(new Double(x[i]), new Double(y[i]));
		}
	}

	/** Constructor */
	public DataSeq(final double[] y, final int startingXValue) {
		for (int i = 0; i < y.length; i++) {
			this.addData(new Double(startingXValue + i), new Double(y[i]));
		}
	}

	/** Constructor */
	public DataSeq(final Double[] y) {
		if (y == null) {
			return;
		}
		for (int i = 0; i < y.length; i++) {
			this.addData(new Double(i), y[i]);
		}
	}

	/** Constructor */
	public DataSeq(final Double[] x, final Double[] y) {
		for (int i = 0; i < x.length; i++) {
			this.addData(x[i], y[i]);
		}
	}

	/** Constructor */
	public DataSeq(final Double[] y, final int startingXValue) {
		for (int i = 0; i < y.length; i++) {
			this.addData(new Double(startingXValue + i), y[i]);
		}
	}

	/** Constructor */
	public void addData(final Object x, final Object y) {
		this.xData.add((Double) x);
		this.yData.add((Double) y);
	}

	/** Converts double values to string conforming to the provided template */
	public String doubleToString(final Double d) {
		if (this.valueFormat.compareTo("") == 0) {
			return d.toString();
		}
		DecimalFormat df = null;
		if (SpiderChart.getNumberLocale() == null) {
			df = new DecimalFormat(this.valueFormat);
		} else {
			final NumberFormat nf = getNumberInstance(new Locale(SpiderChart.getNumberLocale(), ""));
			df = (DecimalFormat) nf;

			df.applyPattern(this.valueFormat);
		}
		df = new DecimalFormat(this.valueFormat);
		return df.format(d.doubleValue());
	}

	/** Returns the Data Point labels */
	public String[] getDataLabels() {
		return this.dataLabels;
	}

	/** Get element at X as provided */
	public Object getElementX(final int i) {
		return this.xData.get(i);
	}

	/** Get element at Y as provided */
	public Object getElementY(final int i) {
		return this.yData.get(i);
	}

	/** Getter for the hot areas */
	public List<Object> getHotAreas() {
		return this.hotAreas;
	}

	/** Getter for the Label Template */
	public String getLabelTemplate() {
		return this.labelTemplate;
	}

	/** Getter for the descriptive name */
	public String getName() {
		return this.name;
	}

	/** Getter for the size */
	public int getSize() {
		return this.xData.size();
	}

	/** Getter for the tips */
	public String[] getTips() {
		return this.tips;
	}

	/** Getter for the value label format */
	public String getValueFormat() {
		return this.valueFormat;
	}

	/** Getter for the x data */
	public List<Double> getxData() {
		return this.xData;
	}

	/** Getter for the y Data */
	public List<Double> getyData() {
		return this.yData;
	}

	/** Setter for data labels */
	public void setDataLabels(final String[] dataLabels) {
		this.dataLabels = dataLabels;
	}

	/** Setter for hot areas */
	public void setHotAreas(final List<Object> hotAreas) {
		this.hotAreas = hotAreas;
	}

	/** Setter for label template */
	public void setLabelTemplate(final String labelTemplate) {
		this.labelTemplate = labelTemplate;
	}

	/** Setter for the descriptive name */
	public void setName(final String name) {
		this.name = name;
	}

	/** Setter for the tips */
	public void setTips(final String[] tips) {
		this.tips = tips;
	}

	/** Setter for the value label format */
	public void setValueFormat(final String valueFormat) {
		this.valueFormat = valueFormat;
	}

	/** Setter for the x data */
	public void setxData(final List<Double> xData) {
		this.xData = xData;
	}

	/** Setter for the y Data */
	public void setyData(final List<Double> yData) {
		this.yData = yData;
	}

}
