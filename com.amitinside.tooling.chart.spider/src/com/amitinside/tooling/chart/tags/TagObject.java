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

import java.util.Hashtable;

public class TagObject {
	public static String ATT_OBJECT_NAME = "NAME";
	public static String ATT_TYPE = "TYPE";
	public static String ATT_VALUE = "VALUE";
	public static String OBJ_ALIGN = "ALIGN";
	public static String OBJ_ANCHOR = "ANCHOR";
	public static String OBJ_BACKGROUND = "BACKGROUND";
	public static String OBJ_BORDER = "BORDER";
	public static String OBJ_CLICKINFO = "CLICKINFO";
	public static String OBJ_COLOR = "COLOR";
	public static String OBJ_FONT = "FONT";
	public static String OBJ_IMAGE = "IMAGE";
	public static String OBJ_MARGIN = "MARGIN";
	public static String OBJ_NAME = "NAME";
	public static String OBJ_POSITION = "POSITION";
	public static String OBJ_ROTATION = "ROTATION";
	public static String OBJ_SIZE = "SIZE";
	public static String OBJ_TIP = "TIP";
	public static String TAG_BACKSPACE = "BACK";
	public static String TAG_CR = "CR";
	public static String TAG_LF = "LF";
	public static String TAG_OBJECT = "OBJECT";
	public static String TAG_STRING = "STRING";
	Hashtable attributes = new Hashtable();

	public TagObject(final String type) {
		this.setAttribute(ATT_TYPE, type);
	}

	public TagObject(final String type, final String value) {
		this.setAttribute(ATT_TYPE, type);
		this.setAttribute(ATT_VALUE, value);
	}

	public boolean compareAtt(final String attribute, final String value) {
		return this.getAttribute(attribute).toLowerCase().equals(value.toLowerCase());
	}

	public String getAttribute(final String name) {
		if (!this.attributes.containsKey(name)) {
			return "";
		}
		return (String) this.attributes.get(name);
	}

	public String getName() {
		return this.getAttribute(ATT_OBJECT_NAME);
	}

	public String getType() {
		return this.getAttribute(ATT_TYPE);
	}

	public String getValue() {
		return this.getAttribute(ATT_VALUE);
	}

	public void setAttribute(final String name, final String value) {
		if (value == null) {
			return;
		}
		this.attributes.put(name, value);
	}
}
