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
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import net.babypython.client.ui.constants.ColorConstants;
import net.babypython.client.ui.gwt.window.GwtWindow;

public class GwtWindowHeaderBar extends HorizontalPanel {

    public GwtWindowHeaderBar(GwtWindow gwtWindow) {
        super();
        this.gwtWindow = gwtWindow;
        windowWidth = Window.getClientWidth();
        clientLeft = Document.get().getBodyOffsetLeft();
        clientTop = Document.get().getBodyOffsetTop();
        addStyleName("headerBarBorders");
        setWidth("100%");
        setStyle();
        addTitle();
        addButtons();
        sinkEvents(Event.ONDBLCLICK | Event.ONMOUSEDOWN | Event.ONMOUSEUP | Event.ONMOUSEMOVE);
    }

    void addButtons() {
        buttonsPanel = new HorizontalPanel();
        if (this.gwtWindow.defaultShowMinimizeButton())
            addMinimizeButton();
        if (this.gwtWindow.defaultShowMaximizeButton())
            addMaximizeButton();
        if (this.gwtWindow.defaultShowCloseButton())
            addCloseButton();
        add(buttonsPanel);
        setCellHorizontalAlignment(buttonsPanel, HasHorizontalAlignment.ALIGN_RIGHT);
    }

    void addCloseButton() {
        closeButton = new GwtWindowCloseButton(this);
        buttonsPanel.add(closeButton);
        setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_RIGHT);
        setCellVerticalAlignment(closeButton, HasVerticalAlignment.ALIGN_TOP);
    }

    void addMinimizeButton() {
        minimizeButton = new GwtWindowMinimizeButton(this);
        buttonsPanel.add(minimizeButton);
        setCellHorizontalAlignment(minimizeButton, HasHorizontalAlignment.ALIGN_RIGHT);
        setCellVerticalAlignment(minimizeButton, HasVerticalAlignment.ALIGN_TOP);
    }

    void addMaximizeButton() {
        maximizeButton = new GwtWindowMaximizeButton(this);
        buttonsPanel.add(maximizeButton);
        setCellHorizontalAlignment(maximizeButton, HasHorizontalAlignment.ALIGN_RIGHT);
        setCellVerticalAlignment(maximizeButton, HasVerticalAlignment.ALIGN_TOP);
    }

    void addTitle() {
        title1 = new Label("");
        title1.setWordWrap(false);
        title1.addStyleName("windowTitle");
        title1.addStyleName("noselect");
        title1.getElement().getStyle().setColor(ColorConstants.WindowTitle1Color);
        title1.getElement().getStyle().setTextOverflow(Style.TextOverflow.ELLIPSIS);
        title1.getElement().getStyle().setOverflow(Style.Overflow.HIDDEN);
        title2 = new Label("");
        title2.setWordWrap(false);
        title2.addStyleName("windowTitle");
        title2.addStyleName("noselect");
        title2.getElement().getStyle().setColor(ColorConstants.WindowTitle2Color);
        title2.getElement().getStyle().setMarginLeft(5, Style.Unit.PX);
        title2.getElement().getStyle().setTextOverflow(Style.TextOverflow.ELLIPSIS);
        title2.getElement().getStyle().setOverflow(Style.Overflow.HIDDEN);
        HorizontalPanel titlesPanel = new HorizontalPanel();
        titlesPanel.add(title1);
        titlesPanel.add(title2);
        add(titlesPanel);
        setCellHorizontalAlignment(titlesPanel, HasHorizontalAlignment.ALIGN_LEFT);
        setCellVerticalAlignment(titlesPanel, HasVerticalAlignment.ALIGN_TOP);
    }

    public String getTitle() {
        return title1.getText();
    }

    @Override
    public void onBrowserEvent(Event event) {
        switch (DOM.eventGetType(event)) {
            case Event.ONDBLCLICK:
                onDblClick();
                break;
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

    public void onCloseButtonClicked() {
        gwtWindow.hideOrClose();
    }

    public void onDblClick() {
        gwtWindow.onHeaderDoubleClick();
    }

    public void onMaximizeButtonClicked() {
        gwtWindow.maximizeOrRestore();
    }

    public void onMinimizeButtonClicked() {
        gwtWindow.minimizeOrRestore();
    }

    void onMouseDown(Event event) {
        gwtWindow.moveToFront();
        if (DOM.getCaptureElement() == null) {
            setDragging(true);
            DOM.setCapture(getElement());
            dragStartX = event.getClientX();
            dragStartY = event.getClientY();
            windowStartX = getAbsoluteLeft();
            windowStartY = getAbsoluteTop();
        }
    }

    void onMouseMove(Event event) {
        if (dragging) {

            int deltaX = event.getClientX() - dragStartX;
            int deltaY = event.getClientY() - dragStartY;

            int newX = windowStartX + deltaX;
            int newY = windowStartY + deltaY;

            if (newX < clientLeft || newX >= windowWidth || newY < clientTop)
                return;

            gwtWindow.setWindowPosition(newX, newY);
        }
    }

    void onMouseUp() {
        setDragging(false);
        DOM.releaseCapture(getElement());
    }

    @Override
    public void setTitle(String text) {
        title1.setText(text);
    }

    public void setTitle2(String text) {
        title2.setText(text);
    }

    void setDragging(boolean value) {
        dragging = value;
    }

    void setStyle() {
        Style style = getElement().getStyle();
        style.setPaddingLeft(7, Style.Unit.PX);
        style.setPaddingRight(7, Style.Unit.PX);
    }

    HorizontalPanel buttonsPanel;
    int clientLeft;
    int clientTop;
    GwtWindowCloseButton closeButton;
    boolean dragging;
    int dragStartX;
    int dragStartY;
    GwtWindow gwtWindow;
    GwtWindowMaximizeButton maximizeButton;
    GwtWindowMinimizeButton minimizeButton;
    Label title1;
    Label title2;
    int windowStartX;
    int windowStartY;
    int windowWidth;
}
