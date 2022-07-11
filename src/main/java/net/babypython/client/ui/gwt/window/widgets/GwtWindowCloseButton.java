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

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Image;
import net.babypython.client.ui.constants.DimensionConstants;
import net.babypython.client.ui.constants.ImageConstants;

public class GwtWindowCloseButton extends Image {

    public GwtWindowCloseButton(GwtWindowHeaderBar headerPanel) {
        super();
        this.headerPanel = headerPanel;
        setWidth(DimensionConstants.NavBarButtonSize + "px");
        setHeight(DimensionConstants.NavBarButtonSize + "px");
        getElement().getStyle().setMarginLeft(DimensionConstants.NavBarButtonMarginRight, Style.Unit.PX);
        getElement().getStyle().setCursor(Style.Cursor.POINTER);
        setUrl(ImageConstants.windowCloseBtnUrl);
        sinkEvents(Event.ONCLICK);
    }

    @Override
    public void onBrowserEvent(Event event) {
        switch (DOM.eventGetType(event)) {
            case Event.ONCLICK:
                headerPanel.onCloseButtonClicked();
                break;
        }
    }

    GwtWindowHeaderBar headerPanel;
}
