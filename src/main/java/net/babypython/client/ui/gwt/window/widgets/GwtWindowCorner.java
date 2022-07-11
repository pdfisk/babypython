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
package net.babypython.client.ui.gwt.window.widgets;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;
import net.babypython.client.ui.events.drag.DragEventBus;
import net.babypython.client.ui.events.drag.DragState;
import net.babypython.client.ui.gwt.window.GwtWindow;

public class GwtWindowCorner extends SimplePanel {

    public GwtWindowCorner(GwtWindow window, CornerLocation cornerLocation, String bgColor) {
        super();
        this.window = window;
        this.cornerLocation = cornerLocation;
        windowWidth = Window.getClientWidth();
        clientLeft = Document.get().getBodyOffsetLeft();
        clientTop = Document.get().getBodyOffsetTop();
        setWidth("100%");
        setHeight("100%");
        getElement().getStyle().setBackgroundColor(bgColor);
        setCursor();
        sinkEvents(Event.ONMOUSEDOWN | Event.ONMOUSEUP | Event.ONMOUSEMOVE);
    }

    @Override
    public void onBrowserEvent(Event event) {
        switch (DOM.eventGetType(event)) {
            case Event.ONMOUSEDOWN:
                onMouseDown(event);
                break;
            case Event.ONMOUSEMOVE:
                onMouseMove(event);
                break;
            case Event.ONMOUSEUP:
                onMouseUp();
                break;
        }
        if (dragging) {
            event.preventDefault();
            event.stopPropagation();
        }
    }

    void onMouseDown(Event event) {
        window.moveToFront();
        if (DOM.getCaptureElement() == null) {
            setDragging(true);
            DOM.setCapture(getElement());
            dragStartX = event.getClientX();
            dragStartY = event.getClientY();
            windowStartWidth = window.getWidth();
            windowStartHeight = window.getHeight();
            windowStartX = window.getAbsoluteLeft();
            windowStartY = window.getAbsoluteTop();
        }
    }

    void onMouseMove(Event event) {
        if (dragging) {
            int deltaX = event.getClientX() - dragStartX;
            int deltaY = event.getClientY() - dragStartY;
            int newX = windowStartX;
            int newY = windowStartY;

            int newWidth = -1;
            int newHeight = -1;

            switch (cornerLocation) {
                case TOP_LEFT:
                    newX = windowStartX + deltaX;
                    newY = windowStartY + deltaY;
                    newWidth = windowStartWidth - deltaX;
                    newHeight = windowStartHeight - deltaY;
                    break;
                case TOP_RIGHT:
                    newY = windowStartY + deltaY;
                    newWidth = windowStartWidth + deltaX;
                    newHeight = windowStartHeight - deltaY;
                    break;
                case BOTTOM_LEFT:
                    newX = windowStartX + deltaX;
                    newWidth = windowStartWidth - deltaX;
                    newHeight = windowStartHeight + deltaY;
                    break;
                case BOTTOM_RIGHT:
                    newWidth = windowStartWidth + deltaX;
                    newHeight = windowStartHeight + deltaY;
                    break;
            }

            if (newX < clientLeft || newX >= windowWidth || newY < clientTop)
                return;

            if (window.setWindowSize(newWidth, newHeight))
                window.setWindowPosition(newX, newY);
        }
    }

    void onMouseUp() {
        setDragging(false);
        DOM.releaseCapture(getElement());
        window.afterResize();
    }

    void setCursor() {
        Style style = getElement().getStyle();
        switch (cornerLocation) {
            case BOTTOM_LEFT:
                style.setCursor(Style.Cursor.SW_RESIZE);
                break;
            case BOTTOM_RIGHT:
                style.setCursor(Style.Cursor.SE_RESIZE);
                break;
            case TOP_LEFT:
                style.setCursor(Style.Cursor.NW_RESIZE);
                break;
            case TOP_RIGHT:
                style.setCursor(Style.Cursor.NE_RESIZE);
                break;
        }
    }

    void setDragging(boolean value) {
        DragEventBus.fireDragEvent(value ? DragState.STARTED : DragState.ENDED);
        dragging = value;
    }

    public enum CornerLocation {
        BOTTOM_LEFT, BOTTOM_RIGHT, TOP_LEFT, TOP_RIGHT
    }

    int clientLeft;
    int clientTop;
    boolean dragging;
    int dragStartX;
    int dragStartY;
    int windowStartX;
    int windowStartY;
    int windowWidth;
    int windowStartWidth;
    int windowStartHeight;
    CornerLocation cornerLocation;
    GwtWindow window;
}
