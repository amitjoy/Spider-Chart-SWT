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
package com.amitinside.tooling.chart;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;

public class ParamInput extends Dialog {

	private static final long serialVersionUID = 8934075852388346034L;
	Button button1 = new Button();
	Button button2 = new Button();
	public boolean cancelled = true;
	Label label1 = new Label();
	public String result = "";
	TextField textField1 = new TextField();

	public ParamInput(final Frame f, final String paramName) {
		super(f);
		this.setModal(true);
		this.setBounds(100, 100, 330, 135);
		try {
			this.jbInit();
			this.label1.setText("Please enter a value for parameter " + paramName + ":");
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	void button1_actionPerformed(final ActionEvent e) {
		this.cancelled = false;
		this.result = this.textField1.getText();
		this.setVisible(false);
	}

	void button2_actionPerformed(final ActionEvent e) {
		this.cancelled = true;
		this.setVisible(false);
	}

	private void jbInit() throws Exception {
		this.label1.setText("Please enter a value for parameter");
		this.label1.setBounds(new Rectangle(5, 34, 312, 17));
		this.setTitle("Input dialog");
		this.setLayout(null);
		this.textField1.setBounds(new Rectangle(5, 59, 311, 21));
		this.button1.setLabel("OK");
		this.button1.setBounds(new Rectangle(70, 92, 75, 27));
		this.button1.addActionListener(e -> ParamInput.this.button1_actionPerformed(e));
		this.button2.setLabel("Skip");
		this.button2.setBounds(new Rectangle(161, 92, 75, 27));
		this.button2.addActionListener(e -> ParamInput.this.button2_actionPerformed(e));
		this.add(this.label1, null);
		this.add(this.textField1, null);
		this.add(this.button2, null);
		this.add(this.button1, null);
	}
}
