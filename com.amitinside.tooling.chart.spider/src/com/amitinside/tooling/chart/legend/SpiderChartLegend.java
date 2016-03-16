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
package com.amitinside.tooling.chart.legend;

import static com.amitinside.tooling.chart.api.annotations.processor.SpiderChartAnnotationProcessor.getAreaColor;
import static com.amitinside.tooling.chart.api.annotations.processor.SpiderChartAnnotationProcessor.getLegend;
import static com.amitinside.tooling.chart.gc.AbstractChartColor.BLACK;
import static com.amitinside.tooling.chart.gc.AbstractChartFont.PLAIN;
import static com.amitinside.tooling.chart.gc.AbstractGraphicsSupplier.getColor;
import static com.amitinside.tooling.chart.gc.AbstractGraphicsSupplier.getFont;
import static com.amitinside.tooling.chart.gc.Fonts.VERDANA;
import static com.amitinside.tooling.chart.style.LineStyle.NORMAL_LINE;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.amitinside.tooling.chart.SpiderChartComponent;
import com.amitinside.tooling.chart.gc.AbstractChartColor;
import com.amitinside.tooling.chart.gc.AbstractChartFont;
import com.amitinside.tooling.chart.gc.AbstractChartGraphics;
import com.amitinside.tooling.chart.gc.AbstractChartImage;
import com.amitinside.tooling.chart.label.SpiderChartLabel;
import com.amitinside.tooling.chart.style.FillStyle;
import com.amitinside.tooling.chart.style.LineStyle;

/**
 * Represents legend to be used on the spider chart
 *
 * @author AMIT KUMAR MONDAL
 *
 */
public final class SpiderChartLegend extends SpiderChartComponent {

	/** Legend Background Style */
	private FillStyle background;

	/** Legend Border Line Style */
	private LineStyle border;

	/** Legend Color */
	private AbstractChartColor color = getColor(BLACK);

	/** Legend Font */
	private AbstractChartFont font = getFont(VERDANA, PLAIN, 10);

	/** */
	private final List<Object> items = new ArrayList<>();

	/** Legend Label */
	private String legendLabel = "";

	/** Legend Margin */
	private int legendMargin = 10;

	/** Legend Offset Used to position the legend on vertical basis */
	private int legendOffset = 250;

	/** */
	private final List<String> names = new ArrayList<>();

	/** Legend Title */
	private String title = null;

	/** */
	private boolean verticalLayout = true;

	/** Constructor */
	public SpiderChartLegend() {
		if (this.title != null) {
			this.addItem(this.title, null);
		}
	}

	/** */
	public void addItem(final String name, final Object icon) {
		this.items.add(icon);
		this.names.add(name);
	}

	/** */
	public void addItem(final Supplier<Object> pojo) {
		final Object data = pojo.get();
		final LineStyle ls = LineStyle.of(1, getColor(getAreaColor(data)), NORMAL_LINE);
		this.addItem(getLegend(data), ls);
	}

	/** Draws the provided graphics */
	public void draw(final AbstractChartGraphics g) {
		if ((this.legendLabel != null) && (this.legendLabel.length() > 0)) {
			final SpiderChartLabel cl = new SpiderChartLabel(this.legendLabel, "", false, true);
			cl.initialize(g, this.chart);
			cl.paint(g, this.x, this.y, this.width, this.height);
			return;
		}
		if (this.verticalLayout) {
			this.drawVertical(g);
		} else {
			this.drawHorizontal(g);
		}
	}

	/** draws the graphics horizontally */
	public void drawHorizontal(final AbstractChartGraphics g) {
		g.setFont(this.font);

		int textWidth = 0;
		int iconWidth = 0;
		int totalWidth = 0;
		int iconHeight = 0;
		int w = 0;
		int h = 0;
		final int textHeight = g.getFontHeight();
		final int iconSeparator = 3;
		final int textSeparator = 5;
		for (final Object element : this.names) {
			final String s = (String) element;
			w = g.getFontWidth(s);
			if (w > textWidth) {
				textWidth = w;
			}
		}
		totalWidth = (textWidth + textSeparator) * this.names.size();
		for (final Object o : this.items) {
			w = 0;
			h = 0;
			if (o instanceof LineStyle) {
				w = 10;
				h = 10;
			}
			if (o instanceof FillStyle) {
				w = 10;
				h = 10;
			}
			if (o instanceof AbstractChartImage) {
				w = ((AbstractChartImage) o).getWidth();
				h = ((AbstractChartImage) o).getHeight();
			}
			if (w > iconWidth) {
				iconWidth = w;
			}
			if (h > iconHeight) {
				iconHeight = h;
			}
		}
		totalWidth += (iconWidth + iconSeparator) * this.names.size();

		int itemHeight = textHeight;
		if (iconHeight > itemHeight) {
			itemHeight = iconHeight;
		}
		int toCenterX = (this.width - totalWidth) / 2;
		int toCenterY = (this.height - itemHeight) / 2;
		if (toCenterY < 0) {
			toCenterY = 0;
		}
		if (toCenterX < 0) {
			toCenterX = 0;
		}
		final int legendX1 = this.x + toCenterX;
		final int legendY1 = this.y + toCenterY;
		final int legendX2 = this.x + toCenterX + totalWidth;
		final int legendY2 = this.y + toCenterY + itemHeight;
		if (this.background != null) {
			this.background.draw(g, legendX1 - this.legendMargin, legendY1 - this.legendMargin,
					legendX2 + this.legendMargin, legendY2 + this.legendMargin);
		}
		if (this.border != null) {
			this.border.drawRect(g, legendX1 - this.legendMargin, legendY1 - this.legendMargin,
					legendX2 + this.legendMargin, legendY2 + this.legendMargin);
		}
		int offset = 0;
		for (int i = 1; i <= this.names.size(); i++) {
			g.setColor(this.color);
			g.drawText(this.names.get(i - 1), toCenterX + offset + iconWidth + iconSeparator + this.x,
					toCenterY + this.y + itemHeight);
			offset = offset + iconWidth + iconSeparator + textWidth + textSeparator;
		}
		offset = 0;
		for (int i = 1; i <= this.names.size(); i++) {
			final Object icon = this.items.get(i - 1);
			if (icon instanceof AbstractChartImage) {
				g.drawImage((AbstractChartImage) icon, toCenterX + this.x + offset, toCenterY + this.y);
			}
			if (icon instanceof LineStyle) {
				final LineStyle l = (LineStyle) icon;
				l.draw(g, toCenterX + this.x + offset, toCenterY + this.y + (iconHeight / 2),
						((toCenterX + this.x + iconWidth) - 2) + offset, toCenterY + this.y + (iconHeight / 2));
			}
			if (icon instanceof FillStyle) {
				final int sidelentgh = iconWidth / 2;

				final FillStyle f = (FillStyle) icon;
				f.draw(g, toCenterX + this.x + offset, toCenterY + this.y, toCenterX + this.x + offset + sidelentgh,
						toCenterY + this.y + sidelentgh);
			}
			offset = offset + iconWidth + iconSeparator + textWidth + textSeparator;
		}
	}

	/** draws the graphics vertically */
	public void drawVertical(final AbstractChartGraphics g) {
		g.setFont(this.font);

		int textWidth = 0;
		int iconWidth = 0;
		int iconHeight = 0;
		int w = 0;
		int h = 0;
		final int textHeight = g.getFontHeight();
		for (final Object element : this.names) {
			final String s = (String) element;
			w = g.getFontWidth(s);
			if (w > textWidth) {
				textWidth = w;
			}
		}
		for (final Object o : this.items) {
			w = 0;
			h = 0;
			if (o instanceof LineStyle) {
				w = 10;
				h = 10;
			}
			if (w > iconWidth) {
				iconWidth = w;
			}
			if (h > iconHeight) {
				iconHeight = h;
			}
		}
		int itemHeight = textHeight;
		if (iconHeight > itemHeight) {
			itemHeight = iconHeight;
		}
		int toCenterX = (this.width - (iconWidth + textWidth)) / 2;
		int toCenterY = (this.height - (this.names.size() * itemHeight)) / 2;
		if (toCenterY < 0) {
			toCenterY = 0;
		}
		if (toCenterX < 0) {
			toCenterX = 0;
		}
		final int legendX1 = this.x + toCenterX;
		final int legendY1 = this.y + toCenterY;
		final int legendX2 = this.x + toCenterX + iconWidth + textWidth;
		final int legendY2 = this.y + toCenterY + (this.names.size() * itemHeight);
		final int x1 = legendX1 - this.legendMargin;
		final int y1 = legendY1 - this.legendMargin;
		final int x2 = legendX2 + this.legendMargin;
		final int y2 = legendY2 + this.legendMargin;

		if (this.background != null) {
			this.background.draw(g, x1, y1 + this.legendOffset, x2, y2 + this.legendOffset);
		}
		if (this.border != null) {
			this.border.drawRect(g, x1, y1 + this.legendOffset, x2, y2 + this.legendOffset);
		}
		for (int i = 1; i <= this.names.size(); i++) {
			g.setColor(this.color);
			g.drawText(this.names.get(i - 1), toCenterX + iconWidth + this.x,
					toCenterY + this.y + (i * itemHeight) + this.legendOffset);
		}
		for (int i = 1; i <= this.names.size(); i++) {
			final Object icon = this.items.get(i - 1);
			if (icon instanceof LineStyle) {
				final LineStyle l = (LineStyle) icon;
				l.setWidth(10);
				l.draw(g, toCenterX + this.x,
						toCenterY + this.y + (iconHeight / 2) + (((i - 1) * itemHeight) + this.legendOffset),
						(toCenterX + this.x + iconWidth) - 2,
						toCenterY + this.y + (iconHeight / 2) + ((i - 1) * itemHeight) + this.legendOffset);
			}
		}
	}

	/** */
	public FillStyle getBackground() {
		return this.background;
	}

	/** */
	public LineStyle getBorder() {
		return this.border;
	}

	/** */
	public AbstractChartColor getChartColor() {
		return this.color;
	}

	/** */
	public AbstractChartFont getChartFont() {
		return this.font;
	}

	/** */
	public List<Object> getItems() {
		return this.items;
	}

	/** */
	public String getLegendLabel() {
		return this.legendLabel;
	}

	/** */
	public int getLegendMargin() {
		return this.legendMargin;
	}

	/** */
	public int getLegendOffset() {
		return this.legendOffset;
	}

	/** */
	public List<String> getNames() {
		return this.names;
	}

	/** */
	public String getTitle() {
		return this.title;
	}

	/** */
	public boolean isVerticalLayout() {
		return this.verticalLayout;
	}

	/** */
	public void setBackground(final FillStyle background) {
		this.background = background;
	}

	/** */
	public void setBorder(final LineStyle border) {
		this.border = border;
	}

	/** */
	public void setColor(final AbstractChartColor color) {
		this.color = color;
	}

	/** */
	public void setFont(final AbstractChartFont font) {
		this.font = font;
	}

	/** */
	public void setLegendLabel(final String legendLabel) {
		this.legendLabel = legendLabel;
	}

	/** */
	public void setLegendMargin(final int legendMargin) {
		this.legendMargin = legendMargin;
	}

	/** */
	public void setLegendOffset(final int legendOffset) {
		this.legendOffset = legendOffset;
	}

	/** */
	public void setTitle(final String title) {
		this.title = title;
	}

	/** */
	public void setVerticalLayout(final boolean verticalLayout) {
		this.verticalLayout = verticalLayout;
	}
}
