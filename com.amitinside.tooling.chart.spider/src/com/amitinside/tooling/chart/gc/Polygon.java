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
package com.amitinside.tooling.chart.gc;

import java.util.ArrayList;
import java.util.List;

public final class Polygon {

	/** list of points to draw the polygon */
	private final List<Point> points;

	/**
	 * Constructor
	 */
	public Polygon() {
		this.points = new ArrayList<>();
	}

	/** adds the point for the polygon to be drawn */
	public void addPoint(final int x, final int y) {
		this.points.add(new Point(x, y));
	}

	/** checks for the containment of a coordinate in the polygon */
	public boolean contains(final int x, final int y) {
		int i = 0;
		int j = 0;
		boolean c = false;

		i = 0;
		for (j = this.points.size() - 1; i < this.points.size(); j = i++) {
			if (((this.getY(i) <= y) && (y < this.getY(j))) || ((this.getY(j) <= y) && (y < this.getY(i))
					&& (x < ((((this.getX(j) - this.getX(i)) * (y - this.getY(i))) / (this.getY(j) - this.getY(i)))
							+ this.getX(i))))) {
				c = !c;
			}
		}
		return c;
	}

	/** getter for the x coordinate */
	public int getX(final int i) {
		return this.points.get(i).getX();
	}

	/** getter for the y coordinate */
	public int getY(final int i) {
		return this.points.get(i).getY();
	}
}
