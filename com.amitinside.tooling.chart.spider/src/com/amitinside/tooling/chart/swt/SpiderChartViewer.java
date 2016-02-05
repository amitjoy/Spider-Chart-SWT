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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Slider;

import com.amitinside.tooling.chart.SpiderChart;
import com.amitinside.tooling.chart.listener.SpiderChartAdapter;

public final class SpiderChartViewer extends Composite {

	/** */
	public boolean allowZoom = true;
	/** */
	SpiderChartCanvas canvas = null;
	/** */
	public boolean changePointer = true;
	/** */
	SpiderChartAdapter chartAdapter = new SpiderChartAdapter() {
		/** {@inheritDoc} */
		@Override
		public void chartEvent(final SpiderChart c, final int type) {
			if (type == 2) {
				SpiderChartViewer.this.canvas.setCursor(SpiderChartViewer.this.pointCursor);
			}
			if (type == 3) {
				SpiderChartViewer.this.canvas.setCursor(SpiderChartViewer.this.defaultCursor);
			}
		}
	};
	/** */
	public int currentZoom = 100;
	/** */
	private Cursor defaultCursor = null;
	/** */
	Slider hSlider = null;
	/** */
	private int lastHeight = 0;
	/** */
	private int lastWidth = 0;
	/** */
	private int lastZoom = 0;
	/** Maximum Zoom for Spider Chart */
	public int maxZoom = 200;
	/** */
	private Button minusZoom;
	/** Minimum Zoom for Spider Chart */
	public int minZoom = 50;
	/** */
	private int originalHeight = -1;
	/** */
	private int originalWidth = -1;
	/** */
	private Button plusZoom;
	/** */
	private Cursor pointCursor = null;
	/** */
	private final int scrollBarWidth = 18;
	/** */
	Slider vSlider = null;
	/** */
	private Label zoom;
	/** */
	public int zoomIncrement = 25;
	/** */
	private Composite zoomPanel;

	/** Constructor */
	public SpiderChartViewer(final Composite parent, final int style) {
		super(parent, style);

		this.canvas = new SpiderChartCanvas(this, 0);
		this.hSlider = new Slider(this, 256);
		this.vSlider = new Slider(this, 512);
		this.canvas.setFocus();

		this.zoomPanel = new Composite(this, 0);
		this.zoomPanel.setSize(90, this.scrollBarWidth);
		this.plusZoom = new Button(this.zoomPanel, 0);
		this.plusZoom.setLocation(0, 0);

		// TODO (AKM) To be implemented properly : ZOOM Functionality
		// this.plusZoom.setImage(new Image(getDisplay(), getClass()
		// .getClassLoader().getResourceAsStream("plus.bmp")));
		this.plusZoom.setSize(25, this.scrollBarWidth);
		this.minusZoom = new Button(this.zoomPanel, 0);
		this.minusZoom.setLocation(26, 0);
		this.minusZoom.setSize(25, this.scrollBarWidth);
		// this.minusZoom.setImage(new Image(getDisplay(), getClass()
		// .getClassLoader().getResourceAsStream("minus.bmp")));
		this.zoom = new Label(this.zoomPanel, 16777216);
		this.zoom.setLocation(52, 0);
		this.zoom.setSize(38, this.scrollBarWidth);

		this.zoom.setText("" + this.currentZoom + " %");

		this.minusZoom.addSelectionListener(new SelectionListener() {
			/** {@inheritDoc} */
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
			}

			/** {@inheritDoc} */
			@Override
			public void widgetSelected(final SelectionEvent e) {
				SpiderChartViewer.this.minusZoom();
			}
		});
		this.plusZoom.addSelectionListener(new SelectionListener() {
			/** {@inheritDoc} */
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
			}

			/** {@inheritDoc} */
			@Override
			public void widgetSelected(final SelectionEvent e) {
				SpiderChartViewer.this.plusZoom();
			}
		});
		this.hSlider.addSelectionListener(new SelectionListener() {
			/** {@inheritDoc} */
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
			}

			/** {@inheritDoc} */
			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (SpiderChartViewer.this.canvas.getChart() == null) {
					return;
				}
				SpiderChartViewer.this.hSliderScroll();
			}
		});
		this.vSlider.addSelectionListener(new SelectionListener() {
			/** {@inheritDoc} */
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
			}

			/** {@inheritDoc} */
			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (SpiderChartViewer.this.canvas.getChart() == null) {
					return;
				}
				SpiderChartViewer.this.vSliderScroll();
			}
		});
		this.addControlListener(new ControlListener() {
			/** {@inheritDoc} */
			@Override
			public void controlMoved(final ControlEvent e) {
			}

			/** {@inheritDoc} */
			@Override
			public void controlResized(final ControlEvent e) {
				if (SpiderChartViewer.this.canvas.getChart() == null) {
					return;
				}
				SpiderChartViewer.this.placeControls();
				SpiderChartViewer.this.updateScrollBarsConfiguration();
				SpiderChartViewer.this.updateSize();
			}
		});
		this.defaultCursor = new Cursor(parent.getDisplay(), 0);
		this.pointCursor = new Cursor(parent.getDisplay(), 21);
	}

	/** {@inheritDoc} */
	@Override
	public void dispose() {
		super.dispose();
		if ((this.pointCursor != null) && !this.pointCursor.isDisposed()) {
			this.pointCursor.dispose();
		}
		if ((this.defaultCursor != null) && !this.defaultCursor.isDisposed()) {
			this.defaultCursor.dispose();
		}
	}

	/** */
	public SpiderChart getChart() {
		return this.canvas.getChart();
	}

	/** */
	private void hSliderScroll() {
		int newBase = 0;
		final int newValue = this.hSlider.getSelection();
		if ((newValue + this.canvas.getChart().getWidth()) < this.canvas.getChart().virtualWidth) {
			newBase = newValue;
		} else {
			newBase = this.canvas.getChart().virtualWidth - this.canvas.getChart().getWidth();
		}
		if (newBase < 0) {
			newBase = 0;
		}
		this.canvas.getChart().offsetX = newBase;
		this.canvas.redraw();
	}

	/** */
	private void minusZoom() {
		if ((this.currentZoom - this.zoomIncrement) > this.minZoom) {
			this.currentZoom -= this.zoomIncrement;
		} else {
			this.currentZoom = this.minZoom;
		}
		this.zoomUpdated();
	}

	/** */
	private void placeControls() {
		int hSliderHeight = 0;
		int vSliderWidth = 0;

		if (this.canvas.getChart().virtualWidth > 0) {
			hSliderHeight = this.scrollBarWidth;
		}
		if (this.canvas.getChart().virtualHeight > 0) {
			vSliderWidth = this.scrollBarWidth;
		}
		this.canvas.setSize(this.getSize().x - vSliderWidth, this.getSize().y - hSliderHeight);

		this.canvas.setLocation(0, 0);
		if (this.allowZoom
				&& ((this.canvas.getChart().virtualHeight > 0) || (this.canvas.getChart().virtualWidth > 0))) {
			this.zoomPanel.setVisible(true);
			this.zoomPanel.setLocation(0, this.canvas.getSize().y);
		} else {
			this.zoomPanel.setVisible(false);
		}
		if (this.canvas.getChart().virtualWidth > 0) {
			if (this.allowZoom) {
				this.hSlider.setLocation(this.zoomPanel.getSize().x, this.canvas.getSize().y);
				this.hSlider.setSize(this.getSize().x - vSliderWidth - this.zoomPanel.getSize().x, hSliderHeight);
			} else {
				this.hSlider.setSize(this.getSize().x - vSliderWidth, hSliderHeight);
				this.hSlider.setLocation(0, this.canvas.getSize().y);
			}
			this.hSlider.setVisible(true);
			this.canvas.getChart().withScroll = true;
		} else {
			this.hSlider.setVisible(false);
		}
		if (this.canvas.getChart().virtualHeight > 0) {
			this.vSlider.setSize(vSliderWidth, this.getSize().y - hSliderHeight);
			this.vSlider.setLocation(this.canvas.getSize().x, 0);
			this.vSlider.setVisible(true);
			this.canvas.getChart().withScroll = true;
		} else {
			this.vSlider.setVisible(false);
		}
	}

	/** */
	private void plusZoom() {
		if ((this.currentZoom + this.zoomIncrement) < this.maxZoom) {
			this.currentZoom += this.zoomIncrement;
		} else {
			this.currentZoom = this.maxZoom;
		}
		this.zoomUpdated();
	}

	/** */
	public void redrawChart() {
		this.canvas.redraw();
	}

	/** */
	private void resetChart() {
		this.lastWidth = 0;
		this.lastHeight = 0;
		this.lastZoom = 0;
	}

	/** */
	public void setChart(final SpiderChart c) {
		if (this.canvas.getChart() != null) {
			this.canvas.getChart().removeChartListener(this.chartAdapter);
		}
		this.canvas.setChart(c);

		this.originalHeight = this.canvas.getChart().virtualHeight;
		this.originalWidth = this.canvas.getChart().virtualWidth;

		this.resetChart();
		this.placeControls();
		this.updateScrollBarsConfiguration();
		this.updateSize();
		if (this.changePointer) {
			this.canvas.getChart().addChartListener(this.chartAdapter);
		}
	}

	/** */
	private void updateScrollBarsConfiguration() {
		if (this.canvas.getChart().virtualWidth > 0) {
			final int w = this.canvas.getChart().getWidth();
			this.hSlider.setMinimum(0);
			this.hSlider.setMaximum(this.canvas.getChart().virtualWidth);
			this.hSlider.setIncrement((int) (this.canvas.getChart().getWidth() * 0.2D));
			this.hSlider.setPageIncrement((int) (this.canvas.getChart().getWidth() * 0.75D));
			if (this.canvas.getChart().offsetX > (this.canvas.getChart().virtualWidth - w)) {
				this.canvas.getChart().offsetX = this.canvas.getChart().virtualWidth - w;
			}
			if (this.canvas.getChart().offsetX < 0) {
				this.canvas.getChart().offsetX = 0;
			}
			if (this.canvas.getChart().offsetX > (this.canvas.getChart().virtualWidth - w)) {
				this.canvas.getChart().offsetX = 0;
			}
			this.hSlider.setThumb(w);
			this.hSlider.setSelection(this.canvas.getChart().offsetX);
		}
		if (this.canvas.getChart().virtualHeight > 0) {
			final int h = this.canvas.getChart().getHeight();
			this.vSlider.setMinimum(0);
			this.vSlider.setMaximum(this.canvas.getChart().virtualHeight);

			this.vSlider.setIncrement((int) (this.canvas.getChart().getHeight() * 0.2D));
			this.vSlider.setPageIncrement((int) (this.canvas.getChart().getHeight() * 0.75D));
			if (this.canvas.getChart().offsetY > (this.canvas.getChart().virtualHeight - h)) {
				this.canvas.getChart().offsetY = this.canvas.getChart().virtualHeight - h;
			}
			if (this.canvas.getChart().offsetY < 0) {
				this.canvas.getChart().offsetY = 0;
			}
			if (this.canvas.getChart().offsetY > (this.canvas.getChart().virtualHeight - h)) {
				this.canvas.getChart().offsetY = 0;
			}
			this.vSlider.setThumb(h);
			this.vSlider.setSelection(this.canvas.getChart().offsetY);
		}
	}

	/** */
	private void updateSize() {
		this.canvas.getChart().repaintAll = true;
		if ((this.lastWidth != this.getSize().x) || (this.lastHeight != this.getSize().y)
				|| (this.lastZoom != this.currentZoom)) {
			this.canvas.getChart().setSize(this.getSize().x, this.getSize().y);
			this.lastWidth = this.getSize().x;
			this.lastHeight = this.getSize().y;
			this.lastZoom = this.currentZoom;
			if (this.canvas.getChart().withScroll) {
				if (this.allowZoom) {
					if (this.originalHeight > 0) {
						this.canvas.getChart().virtualHeight = (this.originalHeight * this.currentZoom) / 100;
					} else {
						this.canvas.getChart().virtualHeight = 0;
					}
					if (this.originalWidth > 0) {
						this.canvas.getChart().virtualWidth = (this.originalWidth * this.currentZoom) / 100;
					} else {
						this.canvas.getChart().virtualWidth = 0;
					}
				}
				int w = 1 + this.getSize().x;
				if (this.originalHeight > 0) {
					w -= this.vSlider.getSize().x;
				}
				int h = 1 + this.getSize().y;
				if (this.originalWidth > 0) {
					h -= this.hSlider.getSize().y;
				}
				this.canvas.getChart().repaintAll = true;

				this.canvas.getChart().setSize(w, h);
			}
		}
	}

	/** */
	private void vSliderScroll() {
		int newBase = 0;
		final int newValue = this.vSlider.getSelection();
		if ((newValue + this.canvas.getChart().getHeight()) < this.canvas.getChart().virtualHeight) {
			newBase = newValue;
		} else {
			newBase = this.canvas.getChart().virtualHeight - this.canvas.getChart().getHeight();
		}
		if (newBase < 0) {
			newBase = 0;
		}
		this.canvas.getChart().offsetY = newBase;
		this.canvas.redraw();
	}

	/** */
	private void zoomUpdated() {
		this.zoom.setText("" + this.currentZoom + " %");

		this.updateSize();
		this.updateScrollBarsConfiguration();
		this.canvas.redraw();
	}
}
