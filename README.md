# Spider Chart SWT

SWT Utility Library to generate Spider Chart Diagram. The library uses all new Java 8 Fluent API to generate Spider Diagram.

### How-To

*In OSGi Environment:*

Build the library using maven. You will get an OSGi bundle. You just have to install it in your Equinox runtime by just adding it to your RCP target platform.

*In Java SE:*

Build the library using maven and put the recently built jar to your application classpath.

### Sample Code

Check out the Sample Application in the project for detailed information.

``` java
final Supplier<ISpiderChartPlottable> iPhoneData = IPhone::new;
		final Supplier<ISpiderChartPlottable> nexusData = Nexus::new;

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
```

### Sampe Output

<img src="http://s8.postimg.org/708bj2jhh/Screen_Shot_2016_02_06_at_11_34_58_AM.png">
