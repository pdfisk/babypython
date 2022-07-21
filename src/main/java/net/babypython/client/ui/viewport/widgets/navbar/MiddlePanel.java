/**
 * MIT License
 * <p>
 * Copyright (c) 2022 Peter Fisk
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.babypython.client.ui.viewport.widgets.navbar;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import net.babypython.client.ui.gwt.widgets.GwtHorizontalPanel;

public class MiddlePanel extends GwtHorizontalPanel {

    public MiddlePanel() {
        super();
        setWidth("100%");
        setHeight("100%");
        setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        this.htmlWidget = new HTML();
        add(this.htmlWidget);
        setStyles();
    }

    protected void setHorizontalAlignment() {
    }

    public void setHtml(String html) {
        htmlWidget.setHTML(html);
    }

    protected void setStyles() {
        Style style = htmlWidget.getElement().getStyle();
        setStyles(style);
    }

    protected void setStyles(Style style) {
        style.setFontWeight(Style.FontWeight.BOLD);
        style.setFontSize(12, Style.Unit.PX);
        style.setColor("slategray");
    }

    public void setText(String text) {
        htmlWidget.setText(text);
    }

    protected HTML htmlWidget;
}
