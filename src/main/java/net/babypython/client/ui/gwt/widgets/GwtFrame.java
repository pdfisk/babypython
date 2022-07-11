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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Frame;
import net.babypython.client.ui.interfaces.IMouseEvents;

public class GwtFrame extends Frame implements IMouseEvents {

    public GwtFrame(GwtFramePanel gwtFramePanel) {
        super();
        this.gwtFramePanel = gwtFramePanel;
        sinkEvents(Event.ONCLICK);
        sinkEvents(Event.ONMOUSEMOVE);
        sinkEvents(Event.ONMOUSEUP);
    }

    protected void addContentWindowHandlers() {
        nativeAddOnClickHandler(contentWindow, nativeOnClickFn());
        nativeAddOnMouseMoveHandler(contentWindow, nativeOnMouseMoveFn());
        nativeAddOnMouseUpHandler(contentWindow, nativeOnMouseUpFn());
    }

    @Override
    public void onBrowserEvent(Event event) {
        switch (DOM.eventGetType(event)) {
            case Event.ONCLICK:
                onClick(event);
                break;
            case Event.ONMOUSEMOVE:
                onMouseMove(event);
                break;
            case Event.ONMOUSEUP:
                onMouseUp(event);
                break;
        }
    }

    protected void onClick(Event event) {
        gwtFramePanel.getGwtWindow().moveToFront();
    }

    @Override
    protected void onLoad() {
        isLoaded = true;
        contentWindow = nativeGetContentWindow(getElement());
        addContentWindowHandlers();
    }

    public void onMouseMove(Event event) {
        gwtFramePanel.onMouseMove(event);
    }

    public void onMouseUp(Event event) {
        gwtFramePanel.onMouseUp(event);
    }

    native void nativeAddOnClickHandler(JavaScriptObject contentWindow, JavaScriptObject onClickFn) /*-{
        contentWindow.onclick = onClickFn;
    }-*/;

    native void nativeAddOnMouseMoveHandler(JavaScriptObject contentWindow, JavaScriptObject onMouseMoveFn) /*-{
        contentWindow.onmousemove = onMouseMoveFn;
    }-*/;

    native void nativeAddOnMouseUpHandler(JavaScriptObject contentWindow, JavaScriptObject onMouseUpFn) /*-{
        contentWindow.onmouseup = onMouseUpFn;
    }-*/;

    native JavaScriptObject nativeGetContentWindow(JavaScriptObject element) /*-{
        return element.contentWindow;
    }-*/;

    native JavaScriptObject nativeOnClickFn() /*-{
        var self = this;
        return function(event) {
            self.@GwtFrame::onClick(*)(event);
        };
    }-*/;

    native JavaScriptObject nativeOnMouseMoveFn() /*-{
        var self = this;
        return function(event) {
            self.@GwtFrame::onMouseMove(*)(event);
        };
    }-*/;

    native JavaScriptObject nativeOnMouseUpFn() /*-{
        var self = this;
        return function(event) {
            self.@GwtFrame::onMouseUp(*)(event);
        };
    }-*/;

    protected JavaScriptObject contentWindow;
    GwtFramePanel gwtFramePanel;
    protected boolean isLoaded = false;
}
