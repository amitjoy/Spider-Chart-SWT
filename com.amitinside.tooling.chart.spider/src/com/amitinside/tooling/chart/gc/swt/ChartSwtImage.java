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
import java.io.OutputStream;
import java.util.Hashtable;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

import com.amitinside.tooling.chart.gc.ChartColor;
import com.amitinside.tooling.chart.gc.ChartGraphics;
import com.amitinside.tooling.chart.gc.ChartImage;

public class ChartSwtImage extends ChartImage {
	private Image image = null;
	private ChartColor transparentColor = null;

	public ChartSwtImage(final int w, final int h) {
		this.image = new Image(SwtGraphicsProvider.getDefaultDisplay(), w, h);
	}

	public ChartSwtImage(final int w, final int h, final ChartColor transparent) {
		this.transparentColor = transparent;
		final Color trans = ((ChartSwtColor) transparent).getColor();
		final Image tmpImage = new Image(
				SwtGraphicsProvider.getDefaultDisplay(), w, h);
		final ImageData imageData = tmpImage.getImageData();
		tmpImage.dispose();
		imageData.transparentPixel = imageData.palette.getPixel(trans.getRGB());

		this.image = new Image(SwtGraphicsProvider.getDefaultDisplay(),
				imageData);

		final GC g = new GC(this.image);
		g.setForeground(trans);
		g.setBackground(trans);
		g.fillRectangle(0, 0, w, h);

		g.dispose();
		trans.dispose();
	}

	public ChartSwtImage(final Object o) {
		try {
			if (o instanceof String) {
				final InputStream is = ChartSwtImage.class.getClassLoader()
						.getResourceAsStream((String) o);
				if (is != null) {
					this.image = new Image(
							SwtGraphicsProvider.getDefaultDisplay(), is);
					return;
				}
				this.image = new Image(SwtGraphicsProvider.getDefaultDisplay(),
						(String) o);
			} else if (o instanceof Image) {
				this.image = (Image) o;
			} else if (o instanceof InputStream) {
				this.image = new Image(SwtGraphicsProvider.getDefaultDisplay(),
						(InputStream) o);
			} else {
				throw new Exception("Class not supported");
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public void dispose() {
		if (this.image != null && !this.image.isDisposed()) {
			this.image.dispose();
		}
		this.image = null;
	}

	protected ChartSwtImage forRotation() {
		final int h = getHeight();
		final int w = getWidth();

		ChartSwtImage dest = null;
		if (w > h) {
			dest = new ChartSwtImage(w, w, this.transparentColor);
		} else if (h > w) {
			dest = new ChartSwtImage(h, h, this.transparentColor);
		} else {
			return this;
		}
		final ChartGraphics g = dest.getGraphics();
		g.drawImage(this, 0, 0);
		g.dispose();

		return dest;
	}

	public ChartGraphics getGraphics() {
		final ChartSwtGraphics g = new ChartSwtGraphics(new GC(this.image));
		g.srcImage = this.image;
		return g;
	}

	public int getHeight() {
		if (this.image == null) {
			return 0;
		}
		return this.image.getBounds().height;
	}

	public Image getImage() {
		return this.image;
	}

	private RGB getRGBFromImage(final ImageData data, final int x, final int y) {
		return data.palette.getRGB(data.getPixel(x, y));
	}

	private Integer getRGBInt(final RGB rgb) {
		return new Integer((rgb.red << 16) + (rgb.green >> 8) + rgb.blue);
	}

	public int getWidth() {
		if (this.image == null) {
			return 0;
		}
		return this.image.getBounds().width;
	}

	public boolean saveToStream(final String sFormat, final OutputStream os) {
		try {
			final ImageLoader encoder = new ImageLoader();
			final ImageData[] iData = new ImageData[1];
			iData[0] = this.image.getImageData();
			encoder.data = iData;
			if (sFormat.toUpperCase().compareTo("GIF") == 0) {
				final Hashtable colors = new Hashtable();

				final RGB[] rgbs = new RGB['Ā'];
				int count = 0;
				final ImageData imageData = this.image.getImageData();
				for (int i = 0; i < this.image.getBounds().width; i++) {
					for (int j = 0; j < this.image.getBounds().height; j++) {
						final RGB rgb = getRGBFromImage(imageData, i, j);
						final Integer iRGB = getRGBInt(rgb);
						if (!colors.containsKey(iRGB)) {
							colors.put(iRGB, rgb);
							rgbs[count++] = rgb;
						}
						if (count >= 256) {
							break;
						}
					}
				}
				for (int i = count; i < 256; i++) {
					rgbs[count++] = new RGB(count - 1, count - 1, count - 1);
				}
				final ImageData image2Data = new ImageData(
						this.image.getBounds().width,
						this.image.getBounds().height, 8, new PaletteData(rgbs));
				final Image image2 = new Image(
						SwtGraphicsProvider.getDefaultDisplay(), image2Data);

				final GC g2 = new GC(image2);
				g2.drawImage(this.image, 0, 0);
				g2.dispose();

				final ImageData[] image2DataArray = new ImageData[1];
				image2DataArray[0] = image2.getImageData();

				final ImageLoader encoder2 = new ImageLoader();
				encoder2.data = image2DataArray;
				encoder2.save(os, 2);
				image2.dispose();
			}
			if (sFormat.toUpperCase().compareTo("JPEG") == 0) {
				encoder.save(os, 4);
			}
			if (sFormat.toUpperCase().compareTo("JPG") == 0) {
				encoder.save(os, 4);
			}
			if (sFormat.toUpperCase().compareTo("PNG") == 0) {
				encoder.save(os, 5);
			}
			if (sFormat.toUpperCase().compareTo("ICO") == 0) {
				encoder.save(os, 3);
			}
			if (sFormat.toUpperCase().compareTo("BMP") == 0) {
				encoder.save(os, 0);
			}
			if (sFormat.toUpperCase().compareTo("BMP_RLE") == 0) {
				encoder.save(os, 1);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
