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

public class Rectangle {

	/** */
	public int h;
	/** */
	public int w;
	/** */
	public int x;
	/** */
	public int y;

	/** Constructor */
	public Rectangle(final int x, final int y, final int w, final int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	/** */
	public boolean contains(final int x1, final int y1) {
		if ((x1 >= this.x) && (x1 <= (this.x + this.w)) && (y1 >= this.y) && (y1 <= (this.y + this.h))) {
			return true;
		}
		return false;
	}

	/** */
	public boolean intersects(final Rectangle r) {
		return ((r.x + r.w) > this.x) && ((r.y + r.h) > this.y) && (r.x < (this.x + this.w))
				&& (r.y < (this.y + this.h));
	}
}
