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

import static com.amitinside.tooling.chart.LineStyle.LINE_NORMAL;
import static com.amitinside.tooling.chart.gc.ChartColor.BLUE;
import static com.amitinside.tooling.chart.gc.ChartColor.GREEN;
import static com.amitinside.tooling.chart.gc.SWTGraphicsSupplier.getColor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.amitinside.tooling.chart.LineStyle;
import com.amitinside.tooling.chart.api.ISpiderDrawable;
import com.amitinside.tooling.chart.builder.ChartBuilder;

public class Sample {

	private static void buildSpiderChart(final Shell shell) {

		final ISpiderDrawable iphoneData = new IPhone();
		final ISpiderDrawable nexusData = new Nexus();

		ChartBuilder.config(shell, settingsBuilder -> {

			settingsBuilder.title(titleBuilder -> titleBuilder.setText("Mobile Phone Comparison"));

			settingsBuilder.legend(legendBuilder -> {
				legendBuilder.addItem("iPhone 6", new LineStyle(1, getColor(BLUE), LINE_NORMAL));
				legendBuilder.addItem("Nexus 6", new LineStyle(1, getColor(GREEN), LINE_NORMAL));
			});

			settingsBuilder.plotter(plotterBuilder -> {
				final double[] maxScale = { 5, 5, 5, 5, 5 };
				final double[] minScale = { 0, 0, 0, 0, 0 };
				final String[] axes = { "Battery", "Camera", "Display", "Memory", "Brand" };

				plotterBuilder.factorMaxs = maxScale;
				plotterBuilder.factorMins = minScale;
				plotterBuilder.factorNames = axes;
			});

		}).viewer(chartBuilder -> {
			chartBuilder.data(firstData -> {
				firstData.inject(iphoneData);
			});
			chartBuilder.data(secondData -> {
				secondData.inject(nexusData);
			});
		});
	}

	public static void main(final String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display, SWT.DIALOG_TRIM);
		shell.setSize(800, 750);

		buildSpiderChart(shell);

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

}
