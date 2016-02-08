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

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.amitinside.tooling.chart.builder.AxesConfigurer;
import com.amitinside.tooling.chart.builder.SpiderChartBuilder;
import com.amitinside.tooling.chart.sequence.LineDataSeq;
import com.amitinside.tooling.chart.swt.SpiderChartViewer;

public final class Sample {

	private static SpiderChartViewer viewer;

	private static void buildSpiderChart(final Shell shell) {
		final Supplier<Object> iPhoneData = IPhone::new;
		final Supplier<Object> nexusData = Nexus::new;

		viewer = SpiderChartBuilder.config(shell, settings -> {
			settings.title(title -> title.text = "Smartphone Comparison Scale").legend(legend -> {
				legend.addItem(iPhoneData);
				legend.addItem(nexusData);
			}).plotter(plotter -> {
				final AxesConfigurer configuration = new AxesConfigurer.Builder().addAxis("Battery", 5, 0)
						.addAxis("Camera", 5, 0).addAxis("Display", 5, 0).addAxis("Memory", 5, 0).addAxis("Brand", 5, 0)
						.build();
				plotter.use(configuration);
			});
		}).viewer(chart -> {
			chart.data(firstData -> firstData.inject(iPhoneData)).data(secondData -> secondData.inject(nexusData));
		});

		Display.getDefault().asyncExec(() -> {
			// Wait for 2 second and change the data points
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (final Exception e) {
				e.printStackTrace();
			}
			// changing values in runtime
			final LineDataSeq seq = LineDataSeq.of(new double[] { 2.0, 2, 4, 2, 3 }, iPhoneData.get());
			viewer.getChart().getSpiderPlotter().setSeq(0, seq);

			// changing axes in runtime
			final AxesConfigurer configuration = new AxesConfigurer.Builder().addAxis("Battery", 5, 0)
					.addAxis("c", 5, 0).addAxis("Display", 5, 0).addAxis("Memory", 5, 0).addAxis("Brand", 5, 0).build();

			final LineDataSeq seq2 = LineDataSeq.of(new double[] { 2.0, 1, 4, 2, 3 }, nexusData.get());
			viewer.getChart().getSpiderPlotter().setSeq(1, seq2);

			viewer.getChart().getSpiderPlotter().use(configuration);
			viewer.redraw();
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
		viewer.getChart().stopWorker();
		viewer.getChart().dispose();
		display.dispose();
	}

}
