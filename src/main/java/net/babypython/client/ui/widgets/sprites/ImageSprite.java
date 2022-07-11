/**
 * MIT License
 *
 * Copyright (c) 2022 Peter Fisk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.babypython.client.ui.widgets.sprites;

import net.babypython.client.ui.widgets.sprites.images.ImagesHolder;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;

public class ImageSprite extends Sprite {

    public ImageSprite(String tag, int x, int y) {
        this(tag,  x, y, 1.0);
    }

    public ImageSprite(String imageTag, int x, int y, double scale) {
        super(x, y);
        this.tag = imageTag;
        this.scale = scale;
    }

    public void draw(Context2d context) {
        super.draw(context);
        ImageElement imageElement = ImagesHolder.getImageElementFor(tag);
        if (imageElement == null)
            return;
        int w = imageElement.getWidth();
        int h = imageElement.getHeight();
        context.drawImage(imageElement, x, y, w * scale, h * scale);
    }

    double scale;
    String tag;
}
