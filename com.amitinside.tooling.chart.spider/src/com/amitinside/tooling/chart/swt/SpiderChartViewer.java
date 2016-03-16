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

import static org.eclipse.swt.SWT.CURSOR_ARROW;
import static org.eclipse.swt.SWT.CURSOR_HAND;
import static org.eclipse.swt.SWT.HORIZONTAL;
import static org.eclipse.swt.SWT.NONE;
import static org.eclipse.swt.SWT.VERTICAL;

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

/**
 * Represents a viewer on the canvas to display the chart
 *
 * @author AMIT KUMAR MONDAL
 *
 */
public final class SpiderChartViewer extends Composite {

	/** allows zoom functionality on the chart */
	private boolean allowZoom = true;

	/** the actual canvas to be used */
	private SpiderChartCanvas canvas = null;

	/** allows to change pointer on hovering data points */
	private boolean changePointer = true;

	/** Default Chart Listeners for hovering behaviours */
	private final SpiderChartAdapter chartAdapter = new SpiderChartAdapter() {
		/** {@inheritDoc} */
		@Override
		public void onChartEvent(final SpiderChart c, final int type) {
			if (type == 2) {
				SpiderChartViewer.this.canvas.setCursor(SpiderChartViewer.this.pointCursor);
			}
			if (type == 3) {
				SpiderChartViewer.this.canvas.setCursor(SpiderChartViewer.this.defaultCursor);
			}
		}
	};

	/** Active Zoom Percentage */
	private int currentZoom = 100;

	/** Default cursor object */
	private Cursor defaultCursor = null;

	/** horizontal slider */
	private Slider hSlider = null;

	/** height of the canvas (used to restore from zoom) */
	private int lastHeight = 0;

	/** width of the canvas (used to restore from zoom) */
	private int lastWidth = 0;

	/** tracks last zoom percentage */
	private int lastZoom = 0;

	/** Maximum Zoom for Spider Chart */
	private int maxZoom = 200;

	/** Minimum Zoom for Spider Chart */
	private int minZoom = 50;

	/** Actual height of the canvas */
	private int originalHeight = -1;

	/** Actual width of the canvas */
	private int originalWidth = -1;

	/** Cursor object in use */
	private Cursor pointCursor = null;

	/** scrollbar width */
	private final int scrollBarWidth = 18;

	/** vertical slider */
	private Slider vSlider = null;

	/** zoom label */
	private Label zoom;

	/** zoom in button */
	private Button zoomInButton;

	/** zoom increment value */
	private int zoomIncrement = 25;

	/** zoom out button */
	private Button zoomOutButton;

	/** zoom panel composite */
	private Composite zoomPanel;

	/** Constructor */
	public SpiderChartViewer(final Composite parent, final int style) {
		super(parent, style);

		this.canvas = new SpiderChartCanvas(this, NONE);
		this.hSlider = new Slider(this, HORIZONTAL);
		this.vSlider = new Slider(this, VERTICAL);
		this.canvas.setFocus();

		this.zoomPanel = new Composite(this, NONE);
		this.zoomPanel.setSize(90, this.scrollBarWidth);
		this.zoomInButton = new Button(this.zoomPanel, NONE);
		this.zoomInButton.setLocation(0, 0);
		this.zoomInButton.setText("Maximize");

		this.zoomInButton.setSize(25, this.scrollBarWidth);
		this.zoomOutButton = new Button(this.zoomPanel, NONE);
		this.zoomOutButton.setLocation(26, 0);
		this.zoomOutButton.setSize(25, this.scrollBarWidth);
		this.zoomOutButton.setText("Minimize");
		this.zoom = new Label(this.zoomPanel, 16777216);
		this.zoom.setLocation(52, 0);
		this.zoom.setSize(38, this.scrollBarWidth);

		this.zoom.setText("" + this.currentZoom + " %");

		this.zoomOutButton.addSelectionListener(new SelectionListener() {
			/** {@inheritDoc} */
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
			}

			/** {@inheritDoc} */
			@Override
			public void widgetSelected(final SelectionEvent e) {
				SpiderChartViewer.this.zoomOut();
			}
		});
		this.zoomInButton.addSelectionListener(new SelectionListener() {
			/** {@inheritDoc} */
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
			}

			/** {@inheritDoc} */
			@Override
			public void widgetSelected(final SelectionEvent e) {
				SpiderChartViewer.this.zoomIn();
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
				SpiderChartViewer.this.placeZoomControls();
				SpiderChartViewer.this.updateSize();
			}
		});
		this.defaultCursor = new Cursor(parent.getDisplay(), CURSOR_ARROW);
		this.pointCursor = new Cursor(parent.getDisplay(), CURSOR_HAND);
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

	/** getter for the canvas in use */
	public SpiderChartCanvas getCanvas() {
		return this.canvas;
	}

	/** getter for the chart in use */
	public SpiderChart getChart() {
		return this.canvas.getChart();
	}

	/** getter for the chart listener in use */
	public SpiderChartAdapter getChartAdapter() {
		return this.chartAdapter;
	}

	/** getter for the current zoom percentage */
	public int getCurrentZoom() {
		return this.currentZoom;
	}

	/** getter for the default cursor */
	public Cursor getDefaultCursor() {
		return this.defaultCursor;
	}

	/** getter for horizontal slider */
	public Slider gethSlider() {
		return this.hSlider;
	}

	/** getter for height after zoom */
	public int getLastHeight() {
		return this.lastHeight;
	}

	/** getter for width after zoom */
	public int getLastWidth() {
		return this.lastWidth;
	}

	/** getter for last zoom percentage */
	public int getLastZoom() {
		return this.lastZoom;
	}

	/** getter for max zoom percentage */
	public int getMaxZoom() {
		return this.maxZoom;
	}

	/** getter for min zoom percentage */
	public int getMinZoom() {
		return this.minZoom;
	}

	/** getter for original canvas height */
	public int getOriginalHeight() {
		return this.originalHeight;
	}

	/** getter for original canvas width */
	public int getOriginalWidth() {
		return this.originalWidth;
	}

	/** getter for cursor in use */
	public Cursor getPointCursor() {
		return this.pointCursor;
	}

	/** getter for scrollbar width */
	public int getScrollBarWidth() {
		return this.scrollBarWidth;
	}

	/** getter for vertical slider */
	public Slider getvSlider() {
		return this.vSlider;
	}

	/** getter for zoom label */
	public Label getZoom() {
		return this.zoom;
	}

	/** getter for zoom in button */
	public Button getZoomInButton() {
		return this.zoomInButton;
	}

	/** getter for zoom increment value */
	public int getZoomIncrement() {
		return this.zoomIncrement;
	}

	/** getter for zoom out button */
	public Button getZoomOutButton() {
		return this.zoomOutButton;
	}

	/** getter for zoom panel composite */
	public Composite getZoomPanel() {
		return this.zoomPanel;
	}

	/** horizontal scrolling functionality */
	private void hSliderScroll() {
		// TODO Need to refactor horizontal scrolling
		int newBase = 0;
		final int newValue = this.hSlider.getSelection();
		if ((newValue + this.canvas.getChart().getWidth()) < this.canvas.getChart().getVirtualWidth()) {
			newBase = newValue;
		} else {
			newBase = this.canvas.getChart().getVirtualWidth() - this.canvas.getChart().getWidth();
		}
		if (newBase < 0) {
			newBase = 0;
		}
		this.canvas.getChart().setOffsetX(newBase);
		this.canvas.redraw();
	}

	/** getter for configuration of allowing zoom */
	public boolean isAllowZoom() {
		return this.allowZoom;
	}

	/**
	 * getter for configuration of changing pointer on data point mouse hover
	 */
	public boolean isChangePointer() {
		return this.changePointer;
	}

	/** Placing zoom controls on the canvas */
	private void placeZoomControls() {
		// TODO Need to refactor for ZOOM Controls
		int hSliderHeight = 0;
		int vSliderWidth = 0;

		if (this.canvas.getChart().getVirtualWidth() > 0) {
			hSliderHeight = this.scrollBarWidth;
		}
		if (this.canvas.getChart().getVirtualHeight() > 0) {
			vSliderWidth = this.scrollBarWidth;
		}
		this.canvas.setSize(this.getSize().x - vSliderWidth, this.getSize().y - hSliderHeight);

		this.canvas.setLocation(0, 0);
		if (this.allowZoom && ((this.canvas.getChart().getVirtualHeight() > 0)
				|| (this.canvas.getChart().getVirtualWidth() > 0))) {
			this.zoomPanel.setVisible(true);
			this.zoomPanel.setLocation(0, this.canvas.getSize().y);
		} else {
			this.zoomPanel.setVisible(false);
		}
		if (this.canvas.getChart().getVirtualWidth() > 0) {
			if (this.allowZoom) {
				this.hSlider.setLocation(this.zoomPanel.getSize().x, this.canvas.getSize().y);
				this.hSlider.setSize(this.getSize().x - vSliderWidth - this.zoomPanel.getSize().x, hSliderHeight);
			} else {
				this.hSlider.setSize(this.getSize().x - vSliderWidth, hSliderHeight);
				this.hSlider.setLocation(0, this.canvas.getSize().y);
			}
			this.hSlider.setVisible(true);
			this.canvas.getChart().setWithScroll(true);
		} else {
			this.hSlider.setVisible(false);
		}
		if (this.canvas.getChart().getVirtualWidth() > 0) {
			this.vSlider.setSize(vSliderWidth, this.getSize().y - hSliderHeight);
			this.vSlider.setLocation(this.canvas.getSize().x, 0);
			this.vSlider.setVisible(true);
			this.canvas.getChart().setWithScroll(true);
		} else {
			this.vSlider.setVisible(false);
		}
	}

	/** Redraws the chart */
	public void redrawChart() {
		this.canvas.redraw();
	}

	/** Resets the chart */
	private void resetChart() {
		this.lastWidth = 0;
		this.lastHeight = 0;
		this.lastZoom = 0;
	}

	/** setter for allowing zoom */
	public void setAllowZoom(final boolean allowZoom) {
		this.allowZoom = allowZoom;
	}

	/** setter for chart canvas */
	public void setCanvas(final SpiderChartCanvas canvas) {
		this.canvas = canvas;
	}

	/** setter for changing pointer */
	public void setChangePointer(final boolean changePointer) {
		this.changePointer = changePointer;
	}

	/** setter for chart instance */
	public void setChart(final SpiderChart c) {
		if (this.canvas.getChart() != null) {
			this.canvas.getChart().removeChartListener(this.chartAdapter);
		}
		this.canvas.setChart(c);

		this.originalHeight = this.canvas.getChart().getVirtualHeight();
		this.originalWidth = this.canvas.getChart().getVirtualWidth();

		this.resetChart();
		this.placeZoomControls();
		this.updateSize();
		if (this.changePointer) {
			this.canvas.getChart().addChartListener(this.chartAdapter);
		}
	}

	/** setter for current zoom percentage */
	public void setCurrentZoom(final int currentZoom) {
		this.currentZoom = currentZoom;
	}

	/** setter for default cursor */
	public void setDefaultCursor(final Cursor defaultCursor) {
		this.defaultCursor = defaultCursor;
	}

	/** setter for horizontal slider */
	public void sethSlider(final Slider hSlider) {
		this.hSlider = hSlider;
	}

	/** setter for last height after zoom */
	public void setLastHeight(final int lastHeight) {
		this.lastHeight = lastHeight;
	}

	/** setter for last width after zoom */
	public void setLastWidth(final int lastWidth) {
		this.lastWidth = lastWidth;
	}

	/** setter for last zoom percentage */
	public void setLastZoom(final int lastZoom) {
		this.lastZoom = lastZoom;
	}

	/** setter for max zoom percentage */
	public void setMaxZoom(final int maxZoom) {
		this.maxZoom = maxZoom;
	}

	/** setter for min zoom percentage */
	public void setMinZoom(final int minZoom) {
		this.minZoom = minZoom;
	}

	/** setter for original height of the canvas */
	public void setOriginalHeight(final int originalHeight) {
		this.originalHeight = originalHeight;
	}

	/** setter for original width of the canvas */
	public void setOriginalWidth(final int originalWidth) {
		this.originalWidth = originalWidth;
	}

	/** setter for the cursor instance in use */
	public void setPointCursor(final Cursor pointCursor) {
		this.pointCursor = pointCursor;
	}

	/** setter for vertical slider */
	public void setvSlider(final Slider vSlider) {
		this.vSlider = vSlider;
	}

	/** setter for zoom label */
	public void setZoom(final Label zoom) {
		this.zoom = zoom;
	}

	/** setter for zoom in button */
	public void setZoomInButton(final Button zoomInButton) {
		this.zoomInButton = zoomInButton;
	}

	/** setter for zoom increment value */
	public void setZoomIncrement(final int zoomIncrement) {
		this.zoomIncrement = zoomIncrement;
	}

	/** setter for zoom out button */
	public void setZoomOutButton(final Button zoomOutButton) {
		this.zoomOutButton = zoomOutButton;
	}

	/** setter for zoom panel composite */
	public void setZoomPanel(final Composite zoomPanel) {
		this.zoomPanel = zoomPanel;
	}

	/** updates the canvas size based on scrolling and zoom */
	private void updateSize() {
		this.canvas.getChart().setRepaintAll(true);
		if ((this.lastWidth != this.getSize().x) || (this.lastHeight != this.getSize().y)
				|| (this.lastZoom != this.currentZoom)) {
			this.canvas.getChart().setSize(this.getSize().x, this.getSize().y);
			this.lastWidth = this.getSize().x;
			this.lastHeight = this.getSize().y;
			this.lastZoom = this.currentZoom;
			if (this.canvas.getChart().isWithScroll()) {
				if (this.allowZoom) {
					if (this.originalHeight > 0) {
						this.canvas.getChart().setVirtualHeight((this.originalHeight * this.currentZoom) / 100);
					} else {
						this.canvas.getChart().setVirtualHeight(0);
					}
					if (this.originalWidth > 0) {
						this.canvas.getChart().setVirtualWidth((this.originalWidth * this.currentZoom) / 100);
					} else {
						this.canvas.getChart().setVirtualWidth(0);
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
				this.canvas.getChart().setRepaintAll(true);

				this.canvas.getChart().setSize(w, h);
			}
		}
	}

	/** vertical sliding functionality */
	private void vSliderScroll() {
		// TODO Need to refactor vertical scrolling
		int newBase = 0;
		final int newValue = this.vSlider.getSelection();
		if ((newValue + this.canvas.getChart().getHeight()) < this.canvas.getChart().getVirtualHeight()) {
			newBase = newValue;
		} else {
			newBase = this.canvas.getChart().getVirtualHeight() - this.canvas.getChart().getHeight();
		}
		if (newBase < 0) {
			newBase = 0;
		}
		this.canvas.getChart().setOffsetY(newBase);
		this.canvas.redraw();
	}

	/** zooms in */
	private void zoomIn() {
		if ((this.currentZoom + this.zoomIncrement) < this.maxZoom) {
			this.currentZoom += this.zoomIncrement;
		} else {
			this.currentZoom = this.maxZoom;
		}
		this.zoomUpdated();
	}

	/** zooms out */
	private void zoomOut() {
		if ((this.currentZoom - this.zoomIncrement) > this.minZoom) {
			this.currentZoom -= this.zoomIncrement;
		} else {
			this.currentZoom = this.minZoom;
		}
		this.zoomUpdated();
	}

	/** zoom updated */
	private void zoomUpdated() {
		this.zoom.setText("" + this.currentZoom + " %");
		this.updateSize();
		this.canvas.redraw();
	}
}
