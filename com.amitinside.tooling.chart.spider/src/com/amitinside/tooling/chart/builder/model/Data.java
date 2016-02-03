package com.amitinside.tooling.chart.builder.model;

import com.amitinside.tooling.chart.FillStyle;
import com.amitinside.tooling.chart.LineDataSeq;
import com.amitinside.tooling.chart.LineStyle;
import com.amitinside.tooling.chart.gc.ChartFont;
import com.amitinside.tooling.chart.gc.GraphicsProvider;

public class Data {

	private LineDataSeq data;

	public void setData(final double[] dataValues, final String color) {
		this.data = new LineDataSeq(dataValues,
				new LineStyle(2, GraphicsProvider.getColor(color), LineStyle.LINE_NORMAL));
		this.data.drawPoint = true;
		this.data.valueFont = GraphicsProvider.getFont("Arial", ChartFont.PLAIN, 10);
		this.data.fillStyle = new FillStyle(GraphicsProvider.getColor(color), 0.5f);
	}

}
