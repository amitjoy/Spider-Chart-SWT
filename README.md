# Spider Chart SWT

SWT Utility Library to generate Spider Chart Diagram. The library uses all new Java 8 Fluent API to generate Spider Diagram.

### How-To

*In OSGi Environment (Eclipse RCP):*

Build the library using maven. You will get an OSGi bundle. You just have to install it in your Equinox runtime by just adding it to your RCP target platform.

*In Java SE:*

Build the library using maven and put the recently built jar to your application classpath.

### Sample Code

Check out the Sample Application in the project for detailed information.

``` java
		// Create the objects of the classes implementing ISpiderChartPlottable
		// You have to implement 3 methods to provide area color, data points to be plotted 
		// and legend name to be displayed
		final Supplier<ISpiderChartPlottable> iPhoneData = IPhone::new;
		final Supplier<ISpiderChartPlottable> nexusData = Nexus::new;

		final SpiderChartViewer viewer = SpiderChartBuilder.config(shell, settings -> {
			// Add title to the Spider Chart
			settings.title(title -> title.text = "Smartphone Comparison Scale").legend(legend -> {
				legend.addItem(iPhoneData);
				legend.addItem(nexusData);
			}).plotter(plotter -> {
				// Add the Axes (name, maximum scale, minimum scale)
				final AxesConfigurer configuration = new AxesConfigurer.Builder().addAxis("Battery", 5, 0)
						.addAxis("Camera", 5, 0).addAxis("Display", 5, 0).addAxis("Memory", 5, 0).addAxis("Brand", 5, 0).build();
				plotter.use(configuration);
			});
		}).viewer(chart -> {
			// Add the different data objects
			chart.data(firstData -> firstData.inject(iPhoneData)).data(secondData -> secondData.inject(nexusData));
		});
```

### Sample Output

<img src="http://s8.postimg.org/708bj2jhh/Screen_Shot_2016_02_06_at_11_34_58_AM.png">
