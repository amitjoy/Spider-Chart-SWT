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
package com.amitinside.tooling.chart.example;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.amitinside.tooling.chart.FillStyle;
import com.amitinside.tooling.chart.LineStyle;
import com.amitinside.tooling.chart.builder.ChartBuilder;
import com.amitinside.tooling.chart.gc.ChartColor;
import com.amitinside.tooling.chart.gc.ChartFont;
import com.amitinside.tooling.chart.gc.GraphicsProvider;

public class Sample {

	public static void main(final String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display, SWT.DIALOG_TRIM);

		ChartBuilder.config(shell, settingsBuilder -> {

			settingsBuilder.legend(legendBuilder -> {
				legendBuilder.background = new FillStyle(GraphicsProvider.getColor(ChartColor.WHITE));
				legendBuilder.border = new LineStyle(1, GraphicsProvider.getColor(ChartColor.BLACK),
						LineStyle.LINE_NORMAL);
				legendBuilder.addItem("iPhone 6",
						new LineStyle(1, GraphicsProvider.getColor(ChartColor.BLUE), LineStyle.LINE_NORMAL));
				legendBuilder.addItem("Nexus 6",
						new LineStyle(1, GraphicsProvider.getColor(ChartColor.GREEN), LineStyle.LINE_NORMAL));
			});

			settingsBuilder.title(titleBuilder -> titleBuilder.setText("Mobile Phone Comparison"));

			settingsBuilder.plotter(plotterBuilder -> {
				final double[] fMaxs = { 5, 5, 5, 5, 5 };
				final double[] fMins = { 0, 0, 0, 0, 0 };
				final String[] factors = { "Battery", "Camera", "Display", "Memory", "Brand", "Processor" };

				plotterBuilder.factorMaxs = fMaxs;
				plotterBuilder.factorMins = fMins;
				plotterBuilder.factorNames = factors;
				plotterBuilder.backStyle = new FillStyle(GraphicsProvider.getColor(ChartColor.YELLOW));
				plotterBuilder.radiusModifier = 0.8;

				plotterBuilder.gridStyle = new LineStyle(1, GraphicsProvider.getColor(ChartColor.BLACK),
						LineStyle.LINE_DASHED);
				plotterBuilder.gridFont = GraphicsProvider.getFont("Arial", ChartFont.PLAIN, 10);
			});

		}).viewer(chartBuilder -> {
			chartBuilder.chart(dataBuilder1 -> {
				// First Data
				final double[] data1 = { 1, 2, 3, 4, 5 };
				dataBuilder1.inject(data1, "BLUE");
				dataBuilder1.done();
			});
			chartBuilder.chart(dataBuilder2 -> {
				// Second Data
				final double[] data2 = { 2, 3, 4, 4.2, 3 };
				dataBuilder2.inject(data2, "GREEN");
				dataBuilder2.done();
			});
		});

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

}
