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
package net.babypython.client.ui.gwt.widgets;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ScrollPanel;

public class GwtCanvas extends ScrollPanel {

    public GwtCanvas() {
        super();
        setWidth("100%");
        setHeight("100%");
        canvas = Canvas.createIfSupported();
        setWidget(canvas);
    }

    protected int getMinCanvasHeight() {
        return getElement().getClientHeight();
    }

    protected int getMinCanvasWidth() {
        return getElement().getClientWidth();
    }

    protected void onAttach() {
        super.onAttach();
        if (canvas == null)
            return;
        context = canvas.getContext2d();
        Timer t = new Timer() {
            @Override
            public void run() {
                redraw();
            }
        };
        t.schedule(500);
    }

    public void redraw() {
        resizeCanvas();
    }

    protected void resizeCanvas() {
        int width = getMinCanvasWidth();
        int height = getMinCanvasHeight();
        resizeCanvas(width, height);
    }

    protected void resizeCanvas(int width, int height) {
        canvas.setWidth(width + "px");
        canvas.setHeight(height + "px");
        canvas.setCoordinateSpaceWidth(width);
        canvas.setCoordinateSpaceHeight(height);
    }

    protected Canvas canvas;
    protected Context2d context;
}
