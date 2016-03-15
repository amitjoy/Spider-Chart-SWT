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
package com.amitinside.tooling.chart.api.annotations.processor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import com.amitinside.tooling.chart.api.annotations.DataPoints;
import com.amitinside.tooling.chart.api.annotations.SpiderChartPlot;

/**
 * Spider Chart Data Point Provider POJO Annotation Processor
 * 
 * @author AMIT KUMAR MONDAL
 *
 */
public final class SpiderChartAnnotationProcessor {

	/**
	 * Checks if {@link DataPoints} annotation is present on the given object's
	 * method
	 */
	private static boolean checkMethodAnnotation(final Object object) {
		for (final Method m : object.getClass().getMethods()) {
			if (m.getAnnotation(DataPoints.class) != null) {
				return true;
			}

		}
		return false;
	}

	/**
	 * Checks if {@link SpiderChartPlot} annotation is present on the given
	 * objects class
	 */
	private static boolean checkTypeAnnotation(final Object object) {
		return object.getClass().isAnnotationPresent(SpiderChartPlot.class);
	}

	/**
	 * Returns area color of the provided Spider Chart Object
	 */
	public static String getAreaColor(final Object object) {
		reportOnNull(object);
		if (!(checkTypeAnnotation(object) && checkMethodAnnotation(object))) {
			throw new RuntimeException(
					"The provided Spider Chart object's class is not annotated with both the SpiderChartPlot and DataPoints annotations");
		} else {
			final String areaColor = object.getClass().getAnnotation(SpiderChartPlot.class).areaColor();
			return areaColor;
		}
	}

	/**
	 * Returns data points of the provided Spider Chart Object
	 */
	public static double[] getDataPoints(final Object object) {
		reportOnNull(object);
		if (!(checkTypeAnnotation(object) && checkMethodAnnotation(object))) {
			throw new RuntimeException(
					"The provided Spider Chart object's class is not annotated with both the SpiderChartPlot and DataPoints annotations");
		} else {
			for (final Method m : object.getClass().getMethods()) {
				final DataPoints dataPoints = m.getAnnotation(DataPoints.class);
				try {
					if (dataPoints != null) {
						final Object invokedResult = m.invoke(object, (Object[]) null);
						if (invokedResult instanceof double[]) {
							return ((double[]) invokedResult);
						} else {
							throw new RuntimeException(
									"Spider Chart annotated method doesn't return primitive double array");
						}
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * Returns legend of the provided Spider Chart Object
	 */
	public static String getLegend(final Object object) {
		reportOnNull(object);
		if (!(checkTypeAnnotation(object) && checkMethodAnnotation(object))) {
			throw new RuntimeException(
					"The provided Spider Chart object's class is not annotated with both the SpiderChartPlot and DataPoints annotations");
		} else {
			final String legend = object.getClass().getAnnotation(SpiderChartPlot.class).name();
			return legend;
		}
	}

	/**
	 * Throws exception in case object provided is null
	 */
	private static void reportOnNull(final Object object) {
		Optional.ofNullable(object).orElseThrow(() -> new IllegalArgumentException("Provided Object is null"));
	}

	/**
	 * Constructor
	 */
	private SpiderChartAnnotationProcessor() {
	}

}
