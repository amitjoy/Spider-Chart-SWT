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
import java.util.Locale;
import java.util.Vector;

import com.amitinside.tooling.chart.SpiderChart;

public class DataSeq {

	/** */
	public String[] dataLabels;
	/** */
	public Vector<Object> hotAreas = new Vector<>(0, 5);
	/** */
	public String labelTemplate = "";
	/** */
	public String name = "";
	/** */
	public String[] tips = new String[0];
	/** */
	public String valueFormat = "######.##";
	/** */
	public Vector<Double> xData = new Vector<>(0, 5);
	/** */
	public Vector<Double> yData = new Vector<>(0, 5);

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
		this.xData.addElement((Double) x);
		this.yData.addElement((Double) y);
	}

	/** */
	public String doubleToString(final Double d) {
		if (this.valueFormat.compareTo("") == 0) {
			return d.toString();
		}
		DecimalFormat df = null;
		if (SpiderChart.numberLocale == null) {
			df = new DecimalFormat(this.valueFormat);
		} else {
			final NumberFormat nf = getNumberInstance(new Locale(SpiderChart.numberLocale, ""));
			df = (DecimalFormat) nf;

			df.applyPattern(this.valueFormat);
		}
		df = new DecimalFormat(this.valueFormat);
		return df.format(d.doubleValue());
	}

	/** */
	public Object getElementX(final int i) {
		return this.xData.elementAt(i);
	}

	/** */
	public Object getElementY(final int i) {
		return this.yData.elementAt(i);
	}

	/** */
	public int getSize() {
		return this.xData.size();
	}

	/** */
	public void setDatax(final double[] x) {
		for (int i = 0; i < x.length; i++) {
			if (i < this.xData.size()) {
				this.xData.setElementAt(new Double(x[i]), i);
			}
		}
	}
}
