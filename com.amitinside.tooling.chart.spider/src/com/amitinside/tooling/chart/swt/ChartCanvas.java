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
package com.amitinside.tooling.chart.swt;

import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import com.amitinside.tooling.chart.Chart;
import com.amitinside.tooling.chart.ChartListener;
import com.amitinside.tooling.chart.gc.ChartGraphics;
import com.amitinside.tooling.chart.gc.GraphicsProvider;

public class ChartCanvas extends Canvas implements ChartListener {
	private Chart chart = null;

	public ChartCanvas(final Composite parent, final int style) {
		super(parent, style | 0x40000);

		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(final PaintEvent e) {
				ChartCanvas.this.paintChart(e);
			}
		});
		final MouseMoveListener mouseMove = new MouseMoveListener() {
			@Override
			public void mouseMove(final MouseEvent e) {
				ChartCanvas.this.mouseMoved(e);
			}
		};
		addMouseMoveListener(mouseMove);

		final MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseDown(final MouseEvent e) {
				ChartCanvas.this.mouseClick();
			}
		};
		addMouseListener(mouseAdapter);

		addControlListener(new ControlListener() {
			@Override
			public void controlMoved(final ControlEvent e) {
			}

			@Override
			public void controlResized(final ControlEvent e) {
				ChartCanvas.this.resizeChart();
			}
		});
	}

	@Override
	public void chartEvent(final Chart c, final int type) {
		if (type == 4) {
			redraw();
		}
		if (type == 1) {
			GraphicsProvider.startUIThread(new Runnable() {
				@Override
				public void run() {
					if (!ChartCanvas.this.isDisposed()) {
						ChartCanvas.this.redraw();
					}
				}
			});
		}
	}

	public Chart getChart() {
		return this.chart;
	}

	private void mouseClick() {
		if (this.chart != null) {
			this.chart.mouseClick();
		}
	}

	private void mouseMoved(final MouseEvent e) {
		if (this.chart != null) {
			this.chart.mouseMoved(e.x, e.y);
		}
	}

	protected void paintChart(final PaintEvent e) {
		try {
			resizeChart();
			final ChartGraphics g = GraphicsProvider.getGraphics(e.gc);
			this.chart.paint(g);
			g.dispose();
		} catch (final Exception err) {
			err.printStackTrace();
		}
	}

	@Override
	public void paintUserExit(final Chart c, final ChartGraphics g) {
	}

	protected void resizeChart() {
		this.chart.setWidth(getSize().x + 1);
		this.chart.setHeight(getSize().y + 1);
	}

	public void setChart(final Chart c) {
		if (this.chart != null) {
			this.chart.removeChartListener(this);
		}
		this.chart = c;

		this.chart.addChartListener(this);
	}
}
