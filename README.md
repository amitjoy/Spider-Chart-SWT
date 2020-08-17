# Spider Chart SWT

[![Build Status](https://travis-ci.org/amitjoy/Spider-Chart-SWT.svg?branch=master)](https://travis-ci.org/amitjoy/Spider-Chart-SWT)

SWT utility library to generate Spider Chart Diagrams. The library uses all new Java 8 fluent API to generate Spider Diagram.

### How To

*In OSGi Environment (Eclipse RCP):*

Build the library using maven. You will get an OSGi bundle. You just have to install it in your Equinox runtime by just adding it to your RCP target platform.

*In Java SE:*

Build the library using maven and put the recently built jar file to your application classpath.

### Sample Usage Snippet

Check out the sample application in the project for the detailed information.

``` java
@SpiderChartPlot(name = "iPhone 6", areaColor = DARKORCHID)
public final class IPhone {

	@DataPoints
	public double[] dataPoints() {
		final double[] data = { 4, 3.5, 4, 4.6, 5 };
		return data;
	}

}
```

``` java
@SpiderChartPlot(name = "Nexus 6", areaColor = OLIVE)
public final class Nexus {

	@DataPoints
	public double[] dataPoints() {
		final double[] data = { 4, 3, 3, 4.1, 3 };
		return data;
	}

}
```

``` java
public final class Sample {

	enum Brand {
		COMMUNAL, INTERNATIONAL, LOCAL, OUT_OF_MARKET, STANDARD
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

		// Updating the chart with new parameters
		Display.getDefault().asyncExec(() -> {
			// changing values in runtime
			final LineDataSeq iPhoneDataSequence = LineDataSeq.of(iPhoneData.get(), 2.0, 4.2, 4.1, 42.8, 3.7,
					Brand.INTERNATIONAL);
			// Set the first sequence
			viewer.getChart().getSpiderPlotter().setSeq(0, iPhoneDataSequence);

			// changing axes in runtime
			final AxesConfigurer configuration = new AxesConfigurer.Builder().addAxis("Battery", 5, 0)
					.addAxis("Screen", 5, 0).addAxis("Display", 5, 0).addAxis("Memory", 50, 0).addAxis("Sound", 5, 0)
					.addAxis("Brand", Brand.class).build();

			final LineDataSeq nexusDataSequence = LineDataSeq.of(nexusData.get(), 2.4, 3.2, 2.1, 23.8, 1.7,
					Brand.LOCAL);

			// Set the second sequence
			viewer.getChart().getSpiderPlotter().setSeq(1, nexusDataSequence);
			viewer.getChart().getSpiderPlotter().use(configuration);
			viewer.getChart().getSpiderPlotter().setMarkScalesOnEveryAxis(true);
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
```

### Maven Central

```xml
<dependency>
    <groupId>com.amitinside</groupId>
    <artifactId>com.amitinside.tooling.chart.spider</artifactId>
    <version>1.1.0</version>
</dependency>
```
