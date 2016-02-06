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
package com.amitinside.tooling.chart.gc.swt;

import java.io.InputStream;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import com.amitinside.tooling.chart.gc.AbstractChartColor;
import com.amitinside.tooling.chart.gc.AbstractChartGraphics;
import com.amitinside.tooling.chart.gc.AbstractChartImage;

public final class SpiderChartSwtImage extends AbstractChartImage {

	/** */
	private Image image = null;
	/** */
	private AbstractChartColor transparentColor = null;

	/** Constructor */
	public SpiderChartSwtImage(final int w, final int h) {
		this.image = new Image(SwtGraphicsProvider.getDisplay(), w, h);
	}

	/** Constructor */
	public SpiderChartSwtImage(final int w, final int h, final AbstractChartColor transparent) {
		this.transparentColor = transparent;
		final Color trans = ((SpiderChartSwtColor) transparent).getColor();
		final Image tmpImage = new Image(SwtGraphicsProvider.getDisplay(), w, h);
		final ImageData imageData = tmpImage.getImageData();
		tmpImage.dispose();
		imageData.transparentPixel = imageData.palette.getPixel(trans.getRGB());

		this.image = new Image(SwtGraphicsProvider.getDisplay(), imageData);

		final GC g = new GC(this.image);
		g.setForeground(trans);
		g.setBackground(trans);
		g.fillRectangle(0, 0, w, h);

		g.dispose();
		trans.dispose();
	}

	/** Constructor */
	public SpiderChartSwtImage(final Object o) {
		try {
			if (o instanceof String) {
				final InputStream is = SpiderChartSwtImage.class.getClassLoader().getResourceAsStream((String) o);
				if (is != null) {
					this.image = new Image(SwtGraphicsProvider.getDisplay(), is);
					return;
				}
				this.image = new Image(SwtGraphicsProvider.getDisplay(), (String) o);
			} else if (o instanceof Image) {
				this.image = (Image) o;
			} else if (o instanceof InputStream) {
				this.image = new Image(SwtGraphicsProvider.getDisplay(), (InputStream) o);
			} else {
				throw new Exception("Class not supported");
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void dispose() {
		if ((this.image != null) && !this.image.isDisposed()) {
			this.image.dispose();
		}
		this.image = null;
	}

	/** */
	protected SpiderChartSwtImage forRotation() {
		final int h = this.getHeight();
		final int w = this.getWidth();

		SpiderChartSwtImage dest = null;
		if (w > h) {
			dest = new SpiderChartSwtImage(w, w, this.transparentColor);
		} else if (h > w) {
			dest = new SpiderChartSwtImage(h, h, this.transparentColor);
		} else {
			return this;
		}
		final AbstractChartGraphics g = dest.getGraphics();
		g.drawImage(this, 0, 0);
		g.dispose();

		return dest;
	}

	/** {@inheritDoc} */
	@Override
	public AbstractChartGraphics getGraphics() {
		final SpiderChartSwtGraphics g = new SpiderChartSwtGraphics(new GC(this.image));
		g.srcImage = this.image;
		return g;
	}

	/** {@inheritDoc} */
	@Override
	public int getHeight() {
		if (this.image == null) {
			return 0;
		}
		return this.image.getBounds().height;
	}

	public Image getImage() {
		return this.image;
	}

	/** {@inheritDoc} */
	@Override
	public int getWidth() {
		if (this.image == null) {
			return 0;
		}
		return this.image.getBounds().width;
	}

}
