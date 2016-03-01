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
package com.amitinside.tooling.chart.util;

import java.util.Arrays;
import java.util.List;

public final class SpiderUtil {

	/**
	 * Returns the array of string represented constants of any Enum
	 */
	public static <E extends Enum<E>> List<String> enumConstants(final Class<E> value) {
		final String[] constants = new String[value.getEnumConstants().length];
		int i = 0;
		for (final Enum<E> enumVal : value.getEnumConstants()) {
			constants[i++] = enumVal.name();
		}
		return Arrays.asList(constants);
	}

	/** */
	public static double[] toDoublePrimitiveArray(final Double[] wrappedArray) {
		final double[] array = new double[wrappedArray.length];
		for (int i = 0; i < wrappedArray.length; i++) {
			array[i] = wrappedArray[i].intValue();
		}
		return array;
	}

	/** */
	public static double[] wrapValues(final Object[] values) {
		final double[] wrappedValues = new double[values.length];

		int i = 0;
		for (final Object value : values) {
			if (value instanceof Double) {
				wrappedValues[i++] = (double) value;
			}
			if (value instanceof Enum<?>) {
				final Enum<?> enumConst = (Enum<?>) value;
				wrappedValues[i++] = enumConst.ordinal() + 1;
			}
		}
		return wrappedValues;
	}

	/**
	 * Constructor
	 */
	private SpiderUtil() {

	}

}
