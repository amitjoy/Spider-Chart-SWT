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

import java.util.function.Supplier;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.amitinside.tooling.chart.builder.AxesConfigurer;
import com.amitinside.tooling.chart.builder.SpiderChartBuilder;
import com.amitinside.tooling.chart.sequence.LineDataSeq;
import com.amitinside.tooling.chart.swt.SpiderChartViewer;

public final class Sample {

	private enum SampleEnum {
		BOLLO, BOLLO1, BOLLO2, HELLO, HELLO1, HELLO2;
	}

	private static SpiderChartViewer viewer;

	private static void buildSpiderChart(final Shell shell) {
		final Supplier<Object> iPhoneData = IPhone::new;
		final Supplier<Object> nexusData = Nexus::new;

		viewer = SpiderChartBuilder.config(shell, settings -> {
			settings.title(title -> title.setText("Smartphone Comparison Scale")).legend(legend -> {
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
		viewer.getChart().getSpiderPlotter().setScalingLabelFormat("#.#", "#.#", "#.#", "#.#", "#.#");

		Display.getDefault().asyncExec(() -> {
			// changing values in runtime
			final LineDataSeq iPhoneDataSequence = LineDataSeq.of(iPhoneData.get(), 2.0, 4.2, 4.1, 2.8, 3.7, 4.1);
			viewer.getChart().getSpiderPlotter().setSeq(0, iPhoneDataSequence);

			// changing axes in runtime
			final AxesConfigurer configuration = new AxesConfigurer.Builder().addAxis("Battery", 5, 0)
					.addAxis("Screen", 5, 0).addAxis("Display", 5, 0).addAxis("Memory", 50, 0).addAxis("Sound", 5, 0)
					.addAxis("Brand", SampleEnum.class).build();

			final LineDataSeq nexusDataSequence = LineDataSeq.of(nexusData.get(), 2.4, 3.2, 2.1, 3.8, 1.7, 1.1);
			viewer.getChart().getSpiderPlotter().setSeq(1, nexusDataSequence);
			viewer.getChart().setShowTips(true);
			viewer.getChart().getSpiderPlotter().use(configuration);
			viewer.getChart().getSpiderPlotter().setMarkScalesOnEveryAxis(true);
			viewer.getChart().getSpiderPlotter().setScalingLabelFormat("#.#", "#.#", "#.#", "#.#", "#.#",
					SampleEnum.class);
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
