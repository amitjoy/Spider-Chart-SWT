# Spider Chart SWT

[![Build Status](https://travis-ci.org/amitjoy/Spider-Chart-SWT.svg?branch=master)](https://travis-ci.org/amitjoy/Spider-Chart-SWT)

SWT Utility Library to generate Spider Chart Diagram. The library uses all new Java 8 Fluent API to generate Spider Diagram.

### How-To

*In OSGi Environment (Eclipse RCP):*

Build the library using maven. You will get an OSGi bundle. You just have to install it in your Equinox runtime by just adding it to your RCP target platform.

*In Java SE:*

Build the library using maven and put the recently built jar to your application classpath.

### Sample Usage Snippet

Check out the Sample Application in the project for detailed information.
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
// Create the objects of the annotated classes
final Supplier<Object> iPhoneData = IPhone::new;
final Supplier<Object> nexusData = Nexus::new;

final SpiderChartViewer viewer = SpiderChartBuilder.config(shell, settings -> {
    // Add title and legends to the Spider Chart
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

<img src="http://s10.postimg.org/h5j8mqb61/Screen_Shot_2016_02_07_at_12_19_09_AM.png">
