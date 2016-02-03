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
package com.amitinside.tooling.chart.tags;

import java.util.Vector;

public class TagParser {
	static final int T_BACKSPACE = 5;
	static final int T_CR = 4;
	static final int T_END = 6;
	static final int T_EQUALS = 2;
	static final int T_LF = 3;
	static final int T_STRING = 1;
	static final int T_TAG_DELIMITER = 0;
	int counter = 0;
	int currentItem = 0;
	int currentPos = 0;
	String currentValue;
	int[] items = new int[100];
	String[] values = new String[100];

	private void addItem(final String currentItem) {
		if (currentItem.length() > 0) {
			this.values[this.counter] = currentItem;
			this.items[this.counter++] = 1;
		}
	}

	private void nextToken() {
		if (this.currentPos >= this.counter) {
			this.currentValue = "";
			this.currentItem = 6;
			return;
		}
		this.currentValue = this.values[this.currentPos];
		this.currentItem = this.items[this.currentPos++];
	}

	private void parseAttribute(final TagObject object) {
		final String attName = this.currentValue.toUpperCase();

		this.nextToken();
		if (this.currentItem != 2) {
			this.reportError("Sign = expected after attribute name");
			return;
		}
		this.nextToken();
		if (this.currentItem != 1) {
			this.reportError("Value for attribute expected");
			return;
		}
		object.setAttribute(attName, this.currentValue);
	}

	private void parseObject(final TagObject object) {
		this.nextToken();
		if (this.currentItem != 1) {
			this.reportError("Object name expected");
			return;
		}
		object.setAttribute(TagObject.ATT_OBJECT_NAME, this.currentValue);

		this.nextToken();
		while (this.currentItem != 6) {
			switch (this.currentItem) {
			case 1:
				this.parseAttribute(object);
				this.nextToken();
				break;
			case 0:
				this.nextToken();
				return;
			}
		}
	}

	public TagObject[] parseTags(final String tags) {
		this.tokenize(tags);

		final Vector tagsVector = new Vector();

		this.currentPos = 0;

		this.nextToken();
		while (this.currentItem != 6) {
			switch (this.currentItem) {
			case 0:
				final TagObject object = new TagObject(TagObject.TAG_OBJECT, this.currentValue);
				tagsVector.addElement(object);
				this.parseObject(object);
				break;
			case 1:
				tagsVector.addElement(new TagObject(TagObject.TAG_STRING, this.currentValue));
				this.nextToken();
				break;
			case 3:
				tagsVector.addElement(new TagObject(TagObject.TAG_LF));
				this.nextToken();
				break;
			case 4:
				tagsVector.addElement(new TagObject(TagObject.TAG_CR));
				this.nextToken();
				break;
			case 5:
				tagsVector.addElement(new TagObject(TagObject.TAG_BACKSPACE));
				this.nextToken();
				break;
			case 2:
			default:
				this.nextToken();
			}
		}
		final TagObject[] result = new TagObject[tagsVector.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = (TagObject) tagsVector.elementAt(i);
		}
		return result;
	}

	private void reportError(final String s) {
		System.err.println(s);
	}

	private void tokenize(final String s) {
		boolean inQuotes = false;
		boolean inObject = false;
		boolean inEscape = false;
		int timetout = 0;

		this.counter = 0;

		int i = 0;
		char c = ' ';
		final char escapeChar = '\\';
		String currentItem = "";
		boolean endOfString = i >= s.length();
		boolean done = false;
		while (!endOfString) {
			timetout++;
			if (timetout > 1000) {
				break;
			}
			inEscape = false;
			endOfString = i >= s.length();
			if (endOfString) {
				break;
			}
			c = ' ';
			if (!endOfString) {
				c = s.charAt(i);
			}
			done = false;
			if (c == escapeChar) {
				if ((i + 1) < s.length()) {
					i++;
					c = s.charAt(i);
					inEscape = true;
				} else {
					c = ' ';
					endOfString = true;
				}
			}
			if (inQuotes) {
				if (!endOfString && ((c != '\'') || inEscape)) {
					currentItem = currentItem + c;
				}
				if (((c == '\'') && !inEscape) || endOfString) {
					inQuotes = false;
					this.values[this.counter] = currentItem;
					this.items[this.counter++] = 1;
					currentItem = "";
				}
				done = true;
			} else if (inObject) {
				if (c == '@') {
					this.items[this.counter++] = 0;
					done = true;
					inObject = false;
				} else if (c == '=') {
					this.addItem(currentItem);
					currentItem = "";
					this.items[this.counter++] = 2;
					done = true;
				} else if (c == '\'') {
					this.addItem(currentItem);
					currentItem = "";
					inQuotes = true;
					done = true;
				} else if (c == ' ') {
					this.addItem(currentItem);
					currentItem = "";

					done = true;
				} else {
					currentItem = currentItem + c;
					done = true;
				}
			} else if ((c == '@') && !inEscape) {
				this.addItem(currentItem);
				currentItem = "";
				this.items[this.counter++] = 0;
				inObject = true;
				done = true;
			} else if (inEscape && (c == 'n')) {
				this.addItem(currentItem);
				currentItem = "";
				this.items[this.counter++] = 3;
				done = true;
			} else if (inEscape && (c == 'b')) {
				this.addItem(currentItem);
				currentItem = "";
				this.items[this.counter++] = 5;
				done = true;
			} else if (inEscape && (c == 'r')) {
				this.addItem(currentItem);
				currentItem = "";
				this.items[this.counter++] = 4;
				done = true;
			} else {
				currentItem = currentItem + c;
				done = true;
			}
			if (done) {
				i++;
				c = ' ';
			}
			if (endOfString) {
				break;
			}
		}
		this.addItem(currentItem);
		this.items[this.counter++] = 6;
	}
}
