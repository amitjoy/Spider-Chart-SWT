package com.amitinside.tooling.chart.builder;

import org.eclipse.swt.widgets.Composite;

public class Sample {

	public void main(final String[] args, final Composite parent) {
		ChartBuilder.viewer(parent, builder -> {
			builder.chart(dataBuilder1 -> {
				// First Data
				final double[] d1 = { 1, 2, 3, 4, 5 };
				dataBuilder1.setValues(d1, "BLUE");
				dataBuilder1.done();
			});
			builder.chart(dataBuilder2 -> {
				// Second Data
				final double[] d2 = { 2, 3, 4, 4.2, 3 };
				dataBuilder2.setValues(d2, "GREEN");
				dataBuilder2.done();
			});
		});

	}

}
