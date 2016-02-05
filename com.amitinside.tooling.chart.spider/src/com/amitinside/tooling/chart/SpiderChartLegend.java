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

import static com.amitinside.tooling.chart.gc.SWTGraphicsSupplier.getColor;

import java.util.Vector;
import java.util.function.Supplier;

import com.amitinside.tooling.chart.api.ISpiderChartDrawable;
import com.amitinside.tooling.chart.gc.SWTGraphicsSupplier;
import com.amitinside.tooling.chart.gc.SpiderChartColor;
import com.amitinside.tooling.chart.gc.SpiderChartFont;
import com.amitinside.tooling.chart.gc.SpiderChartGraphics;
import com.amitinside.tooling.chart.gc.SpiderChartImage;

public class SpiderChartLegend extends SpiderChartComponent {

	/** Legend Background Style */
	public FillStyle background;

	/** Legend Border Line Style */
	public LineStyle border;

	/** Legend Color */
	public SpiderChartColor color = SWTGraphicsSupplier.getColor(SpiderChartColor.BLACK);

	/** Legend Font */
	public SpiderChartFont font = SWTGraphicsSupplier.getFont("Verdana", SpiderChartFont.PLAIN, 10);

	/** */
	Vector<Object> items = new Vector<>(10, 10);

	/** Legend Label */
	public String legendLabel = "";

	/** Legend Margin */
	public int legendMargin = 8;

	/** */
	Vector<String> names = new Vector<>(10, 10);

	/** Legend Title */
	public String title = null;

	/** */
	public boolean verticalLayout = true;

	/** Constructor */
	public SpiderChartLegend() {
		if (this.title != null) {
			this.addItem(this.title, null);
		}
	}

	/** */
	public void addItem(final String name, final Object icon) {
		this.items.addElement(icon);
		this.names.addElement(name);
	}

	/** */
	public void addItem(final Supplier<ISpiderChartDrawable> pojo) {
		final LineStyle ls = LineStyle.of(1, getColor(pojo.get().areaColor()), LineStyle.NORMAL_LINE);
		this.addItem(pojo.get().legend(), ls);
	}

	/** */
	public void draw(final SpiderChartGraphics g) {
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

	/** */
	public void drawHorizontal(final SpiderChartGraphics g) {
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
			if (o instanceof SpiderChartImage) {
				w = ((SpiderChartImage) o).getWidth();
				h = ((SpiderChartImage) o).getHeight();
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
			g.drawString(this.names.elementAt(i - 1), toCenterX + offset + iconWidth + iconSeparator + this.x,
					toCenterY + this.y + itemHeight);
			offset = offset + iconWidth + iconSeparator + textWidth + textSeparator;
		}
		offset = 0;
		for (int i = 1; i <= this.names.size(); i++) {
			final Object icon = this.items.elementAt(i - 1);
			if (icon instanceof SpiderChartImage) {
				g.drawImage((SpiderChartImage) icon, toCenterX + this.x + offset, toCenterY + this.y);
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

	/** */
	public void drawVertical(final SpiderChartGraphics g) {
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
			if (o instanceof FillStyle) {
				w = 10;
				h = 10;
			}
			if (o instanceof SpiderChartImage) {
				w = ((SpiderChartImage) o).getWidth();
				h = ((SpiderChartImage) o).getHeight();
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
		if (this.background != null) {
			this.background.draw(g, legendX1 - this.legendMargin, legendY1 - this.legendMargin,
					legendX2 + this.legendMargin, legendY2 + this.legendMargin);
		}
		if (this.border != null) {
			this.border.drawRect(g, legendX1 - this.legendMargin, legendY1 - this.legendMargin,
					legendX2 + this.legendMargin, legendY2 + this.legendMargin);
		}
		for (int i = 1; i <= this.names.size(); i++) {
			g.setColor(this.color);
			g.drawString(this.names.elementAt(i - 1), toCenterX + iconWidth + this.x,
					toCenterY + this.y + (i * itemHeight));
		}
		for (int i = 1; i <= this.names.size(); i++) {
			final Object icon = this.items.elementAt(i - 1);
			if (icon instanceof SpiderChartImage) {
				g.drawImage((SpiderChartImage) icon, toCenterX + this.x, toCenterY + this.y + ((i - 1) * itemHeight));
			}
			if (icon instanceof LineStyle) {
				final LineStyle l = (LineStyle) icon;
				l.draw(g, toCenterX + this.x, toCenterY + this.y + (iconHeight / 2) + ((i - 1) * itemHeight),
						(toCenterX + this.x + iconWidth) - 2,
						toCenterY + this.y + (iconHeight / 2) + ((i - 1) * itemHeight));
			}
			if (icon instanceof FillStyle) {
				final int sidelentgh = (iconWidth / 2);

				final FillStyle f = (FillStyle) icon;
				f.draw(g, toCenterX + this.x, toCenterY + this.y + (itemHeight / 2) + ((i - 1) * itemHeight),
						toCenterX + this.x + sidelentgh,
						toCenterY + this.y + (itemHeight / 2) + ((i - 1) * itemHeight) + sidelentgh);
			}
		}
	}
}
