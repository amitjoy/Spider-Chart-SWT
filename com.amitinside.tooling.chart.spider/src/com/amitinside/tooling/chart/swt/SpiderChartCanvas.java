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

import static com.amitinside.tooling.chart.gc.AbstractGraphicsSupplier.getGraphics;
import static com.amitinside.tooling.chart.gc.AbstractGraphicsSupplier.startUiThread;
import static org.eclipse.swt.SWT.CTRL;

import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import com.amitinside.tooling.chart.SpiderChart;
import com.amitinside.tooling.chart.gc.AbstractChartGraphics;
import com.amitinside.tooling.chart.listener.ISpiderChartListener;

public final class SpiderChartCanvas extends Canvas implements ISpiderChartListener {

	/** Actual Spider Chart Ref */
	private SpiderChart chart = null;

	/** Constructor */
	public SpiderChartCanvas(final Composite parent, final int style) {
		super(parent, style | CTRL);

		this.addPaintListener(e -> SpiderChartCanvas.this.paintChart(e));
		final MouseMoveListener mouseMove = e -> SpiderChartCanvas.this.mouseMoved(e);
		this.addMouseMoveListener(mouseMove);

		final MouseAdapter mouseAdapter = new MouseAdapter() {
			/** {@inheritDoc} */
			@Override
			public void mouseDown(final MouseEvent e) {
				SpiderChartCanvas.this.mouseClick();
			}
		};
		this.addMouseListener(mouseAdapter);

		this.addControlListener(new ControlListener() {
			/** {@inheritDoc} */
			@Override
			public void controlMoved(final ControlEvent e) {
			}

			/** {@inheritDoc} */
			@Override
			public void controlResized(final ControlEvent e) {
				SpiderChartCanvas.this.resizeChart();
			}
		});
	}

	/** Returns the spider chart */
	public SpiderChart getChart() {
		return this.chart;
	}

	/** Mouse Click event */
	private void mouseClick() {
		if (this.chart != null) {
			this.chart.mouseClick();
		}
	}

	/** Mouse Moved Event */
	private void mouseMoved(final MouseEvent e) {
		if (this.chart != null) {
			this.chart.mouseMoved(e.x, e.y);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void onChartEvent(final SpiderChart c, final int type) {
		if (type == 4) {
			this.redraw();
		}
		if (type == 1) {
			startUiThread(() -> {
				if (!SpiderChartCanvas.this.isDisposed()) {
					SpiderChartCanvas.this.redraw();
				}
			});
		}
	}

	/** {@inheritDoc} */
	@Override
	public void onPaintUserExit(final SpiderChart c, final AbstractChartGraphics g) {
	}

	/** Draws the Spider Chart */
	protected void paintChart(final PaintEvent e) {
		try {
			this.resizeChart();
			final AbstractChartGraphics g = getGraphics(e.gc);
			this.chart.paint(g);
			g.dispose();
		} catch (final Exception err) {
			err.printStackTrace();
		}
	}

	/** Resizes the spider chart */
	protected void resizeChart() {
		this.chart.setWidth(this.getSize().x + 1);
		this.chart.setHeight(this.getSize().y + 1);
	}

	/** Setter for Spider Chart */
	public void setChart(final SpiderChart c) {
		if (this.chart != null) {
			this.chart.removeChartListener(this);
		}
		this.chart = c;
		this.chart.addChartListener(this);
	}
}
