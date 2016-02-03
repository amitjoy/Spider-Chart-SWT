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

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;

import com.amitinside.tooling.chart.gc.ChartColor;
import com.amitinside.tooling.chart.gc.ChartFont;
import com.amitinside.tooling.chart.gc.ChartGraphics;
import com.amitinside.tooling.chart.gc.ChartImage;
import com.amitinside.tooling.chart.gc.GraphicsProvider;

public class ChartSwtGraphics extends ChartGraphics {
	private GC graphics;
	private Font currentFont;
	private Color currentColor;
	protected float alphaValue = 1.0F;
	protected Image srcImage;
	private Color transparent = null;
	protected Image imageForTransparentFilling = null;

	public ChartSwtGraphics(final Object g) {
		this.graphics = (GC) g;
	}

	public void createFadeArea(final ChartColor colorFrom,
			final ChartColor colorUntil, final int x, final int y, final int w,
			final int h, final boolean vertical, final boolean cyclic) {
		final Color fore = ((ChartSwtColor) colorFrom).getColor();
		final Color back = ((ChartSwtColor) colorUntil).getColor();

		this.graphics.setForeground(fore);
		this.graphics.setBackground(back);

		this.graphics.fillGradientRectangle(x, y, w, h, vertical);
		if (this.currentColor != null) {
			this.graphics.setForeground(this.currentColor);
			this.graphics.setBackground(this.currentColor);
		}
		fore.dispose();
		back.dispose();
	}

	public void dispose() {
		disposeCurrentColor();
		disposeCurrentFont();
		if (this.graphics != null && !this.graphics.isDisposed()) {
			this.graphics.dispose();
		}
		this.graphics = null;
	}

	private void disposeCurrentColor() {
		if (this.currentColor != null && !this.currentColor.isDisposed()) {
			this.currentColor.dispose();
		}
		this.currentColor = null;
	}

	private void disposeCurrentFont() {
		if (this.currentFont != null && !this.currentFont.isDisposed()) {
			this.currentFont.dispose();
		}
		this.currentFont = null;
	}

	public void drawArc(final int x, final int y, final int w, final int h,
			final int a1, final int a2) {
		this.graphics.drawArc(x, y, w, h, a1, a2);
	}

	public void drawImage(final ChartImage image, final int x, final int y) {
		if (image == null) {
			return;
		}
		if (((ChartSwtImage) image).getImage() == null) {
			return;
		}
		this.graphics.drawImage(((ChartSwtImage) image).getImage(), x, y);
	}

	public void drawImage(final ChartImage image, final int x1Dest,
			final int y1Dest, final int x2Dest, final int y2Dest,
			final int x1Source, final int y1Source, final int x2Source,
			final int y2Source) {
		if (image == null) {
			return;
		}
		if (((ChartSwtImage) image).getImage() == null) {
			return;
		}
		this.graphics.drawImage(((ChartSwtImage) image).getImage(), x1Source,
				y1Source, x2Source - x1Source, y2Source - y1Source, x1Dest,
				y1Dest, x2Dest - x1Dest, y2Dest - y1Dest);
	}

	public void drawLine(final int x1, final int y1, final int x2, final int y2) {
		this.graphics.drawLine(x1, y1, x2, y2);
	}

	public void drawPolygon(final int[] x1, final int[] y1, final int count) {
		final int[] points = new int[count * 2];
		int j = 0;
		for (int i = 0; i < count; i++) {
			points[j++] = x1[i];
			points[j++] = y1[i];
		}
		this.graphics.drawPolygon(points);
	}

	public void drawRect(final int x1, final int y1, final int w, final int h) {
		super.drawRect(x1, y1, w, h);
	}

	public boolean drawRotatedText(final ChartFont descFont,
			final ChartColor descColor, final String txt, final int angle,
			final int x, final int y, final boolean b) {
		setFont(descFont);
		setColor(descColor);

		final int h = getFontHeight();
		final int w = getFontWidth(txt);
		int size = w;
		int toCenterX = 0;
		int toCenterY = 0;
		if (h > w) {
			size = h;
			toCenterX = (size - w) / 2;
		} else {
			toCenterY = (size - h) / 2;
		}
		Image tmpImage = new Image(SwtGraphicsProvider.getDefaultDisplay(),
				size, size);
		final ImageData imageData = tmpImage.getImageData();
		tmpImage.dispose();

		Color transparent = null;
		if (descColor.getRed() == 255 && descColor.getBlue() == 255
				&& descColor.getGreen() == 255) {
			transparent = new Color(SwtGraphicsProvider.getDefaultDisplay(), 0,
					0, 0);
			imageData.transparentPixel = imageData.palette.getPixel(new RGB(0,
					0, 0));
		} else {
			transparent = new Color(SwtGraphicsProvider.getDefaultDisplay(),
					255, 255, 255);
			imageData.transparentPixel = imageData.palette.getPixel(new RGB(
					255, 255, 255));
		}
		tmpImage = new Image(SwtGraphicsProvider.getDefaultDisplay(), imageData);

		final GC g = new GC(tmpImage);
		final Color c = ((ChartSwtColor) descColor).getColor();
		final Font f = ((ChartSwtFont) descFont).getFont();

		g.setForeground(transparent);
		g.setBackground(transparent);
		g.fillRectangle(0, 0, size, size);

		g.setFont(f);
		g.setForeground(c);
		g.drawText(txt, toCenterX, toCenterY, true);

		g.dispose();
		c.dispose();
		f.dispose();

		final ChartImage tmpChartImage = GraphicsProvider.getImage(tmpImage);
		paintRotatedImage(tmpChartImage, angle, x - (w - h) / 2, y + 4,
				ChartGraphics.ROTATE_CENTER);

		tmpChartImage.dispose();
		tmpImage.dispose();

		return true;
	}

	public void drawString(final String s, final int x, final int y) {
		this.graphics.drawString(s, x, y
				- this.graphics.getFontMetrics().getHeight(), true);
	}

	public void fillArc(final int x, final int y, final int w, final int h,
			final int a1, final int a2) {
		final GC g = getGraphicForFilling();
		g.fillArc(x, y, w, h, a1, a2);
		processFilling(g);
	}

	public void fillPolygon(final int[] x1, final int[] y1, final int count) {
		final int[] points = new int[count * 2];
		int j = 0;
		for (int i = 0; i < count; i++) {
			points[j++] = x1[i];
			points[j++] = y1[i];
		}
		final GC g = getGraphicForFilling();
		g.fillPolygon(points);
		processFilling(g);
	}

	public void fillRect(final int x1, final int y1, final int w, final int h) {
		final GC g = getGraphicForFilling();
		g.fillRectangle(new Rectangle(x1, y1, w, h));
		processFilling(g);
	}

	public Object getAlphaComposite() {
		return new Float(this.alphaValue);
	}

	public ChartColor getColor() {
		return new ChartSwtColor(this.currentColor);
	}

	public ChartFont getFont() {
		return new ChartSwtFont(this.currentFont);
	}

	public int getFontHeight(final ChartFont font) {
		final Font tmpFont = this.currentFont;
		this.currentFont = null;
		if (font != null) {
			setFont(font);
		}
		final int result = this.graphics.getFontMetrics().getHeight();

		disposeCurrentFont();
		this.currentFont = tmpFont;

		return result;
	}

	public int getFontWidth(final ChartFont font, final String s) {
		final Font tmpFont = this.currentFont;
		this.currentFont = null;
		if (font != null) {
			setFont(font);
		}
		int result = 0;

		result = this.graphics.textExtent(s).x;

		disposeCurrentFont();
		this.currentFont = tmpFont;

		return result;
	}

	protected GC getGraphicForFilling() {
		if (this.alphaValue >= 1.0F || this.srcImage == null) {
			this.imageForTransparentFilling = null;
			return this.graphics;
		}
		this.transparent = null;
		if (this.currentColor.getRed() == 255
				&& this.currentColor.getBlue() == 255
				&& this.currentColor.getGreen() == 255) {
			this.transparent = new Color(
					SwtGraphicsProvider.getDefaultDisplay(), 0, 0, 0);
		} else {
			this.transparent = new Color(
					SwtGraphicsProvider.getDefaultDisplay(), 255, 255, 255);
		}
		final RGB[] rgbs = new RGB['Ā'];
		rgbs[0] = this.transparent.getRGB();
		for (int i = 1; i <= 255; i++) {
			rgbs[i] = this.currentColor.getRGB();
		}
		final ImageData imageData = new ImageData(
				this.srcImage.getBounds().width,
				this.srcImage.getBounds().height,
				this.srcImage.getImageData().depth,
				this.srcImage.getImageData().palette);

		this.imageForTransparentFilling = new Image(
				SwtGraphicsProvider.getDefaultDisplay(), imageData);

		final GC g = new GC(this.imageForTransparentFilling);

		g.setForeground(this.transparent);
		g.setBackground(this.transparent);
		g.fillRectangle(0, 0, this.srcImage.getBounds().width,
				this.srcImage.getBounds().height);

		g.setForeground(this.currentColor);
		g.setBackground(this.currentColor);

		return g;
	}

	public void paintRotatedImage(ChartImage srcImage, final int angle,
			final int x, final int y, final int alignment) {
		Image srcSwtImage = ((ChartSwtImage) srcImage).getImage();
		int size = srcSwtImage.getImageData().width;
		int h = srcSwtImage.getImageData().height;
		int w = srcSwtImage.getImageData().width;
		final int originalH = h;
		final int originalW = w;
		if (h != w) {
			srcImage = ((ChartSwtImage) srcImage).forRotation();
			srcSwtImage = ((ChartSwtImage) srcImage).getImage();

			size = srcSwtImage.getImageData().width;
			h = srcSwtImage.getImageData().height;
			w = srcSwtImage.getImageData().width;
		}
		final ImageData srcData = srcSwtImage.getImageData();
		final ImageData destData = new ImageData(size, size, srcData.depth,
				srcData.palette);
		destData.transparentPixel = srcData.transparentPixel;
		if (destData.transparentPixel != -1) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					destData.setPixel(i, j, destData.transparentPixel);
				}
			}
		}
		int c = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				final int x2 = rotateX(i - size / 2, j - size / 2, -angle)
						+ size / 2;
				final int y2 = rotateY(i - size / 2, j - size / 2, -angle)
						+ size / 2;

				c++;
				if (x2 < size && y2 < size && x2 >= 0 && y2 >= 0) {
					destData.setPixel(i, j, srcData.getPixel(x2, y2));
				}
			}
		}
		final Image destImage = new Image(
				SwtGraphicsProvider.getDefaultDisplay(), destData);
		if (alignment == ChartGraphics.ROTATE_LEFTTOP) {
			if (angle == 90) {
				this.graphics.drawImage(destImage, x - (w - originalH), y);
			} else if (angle == -90) {
				this.graphics.drawImage(destImage, x, y);
			}
		} else if (angle == 90) {
			this.graphics.drawImage(destImage, x, y);
		} else if (angle == -90) {
			this.graphics.drawImage(destImage, x, y);
		}
		destImage.dispose();
	}

	protected void processFilling(final GC g) {
		if (g != this.graphics) {
			g.dispose();

			final ImageData imageData = this.imageForTransparentFilling
					.getImageData();
			for (int i = 0; i < this.srcImage.getBounds().width; i++) {
				for (int j = 0; j < this.srcImage.getBounds().height; j++) {
					final RGB rgb = imageData.palette.getRGB(imageData
							.getPixel(i, j));
					if (rgb.red == this.transparent.getRed()
							&& rgb.green == this.transparent.getGreen()
							&& rgb.blue == this.transparent.getBlue()) {
						imageData.setAlpha(i, j, 0);
					} else {
						imageData.setAlpha(i, j,
								(int) (this.alphaValue * 255.0F));
					}
				}
			}
			this.transparent.dispose();
			this.imageForTransparentFilling.dispose();

			this.imageForTransparentFilling = new Image(
					SwtGraphicsProvider.getDefaultDisplay(), imageData);

			this.graphics.drawImage(this.imageForTransparentFilling, 0, 0);
			this.imageForTransparentFilling.dispose();
			this.imageForTransparentFilling = null;
		}
	}

	private int rotateX(final int x, final int y, final int angle) {
		if (angle == 90) {
			return y * -1;
		}
		if (angle == -90) {
			return y * 1;
		}
		return (int) (x * Math.cos(Math.toRadians(angle)) - y
				* Math.sin(Math.toRadians(angle)));
	}

	private int rotateY(final int x, final int y, final int angle) {
		if (angle == 90) {
			return x * 1;
		}
		if (angle == -90) {
			return x * -1;
		}
		return (int) (x * Math.sin(Math.toRadians(angle)) + y
				* Math.cos(Math.toRadians(angle)));
	}

	public void setAlpha(final float a) {
		this.alphaValue = a;
	}

	public void setAlphaComposite(final Object a) {
		if (a instanceof Float) {
			this.alphaValue = ((Float) a).floatValue();
		}
	}

	public void setColor(final ChartColor color) {
		disposeCurrentColor();
		this.currentColor = ((ChartSwtColor) color).getColor();

		this.graphics.setForeground(this.currentColor);
		this.graphics.setBackground(this.currentColor);
	}

	public void setFont(final ChartFont font) {
		disposeCurrentFont();
		this.currentFont = ((ChartSwtFont) font).getFont();

		this.graphics.setFont(this.currentFont);
	}

	public void setLineStyle(final int style) {
		super.setLineStyle(style);
	}

	public void setLineWidth(final int w) {
		super.setLineWidth(w);
	}
}
