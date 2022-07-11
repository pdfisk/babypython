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

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;

public class GwtTextPanel extends SimplePanel {

    public GwtTextPanel() {
        super();
        setWidth("100%");
        setHeight("100%");
        textArea = new TextArea();
        textArea.setHeight("100%");
        textArea.setWidth("100%");
        textArea.getElement().addClassName("textPanel");
        setWidget(textArea);
        getElement().getStyle().setDisplay(Style.Display.FLEX);
        textArea.getElement().getStyle().setProperty("flex", "1");
        clear();
    }

    @Override
    public void clear() {
        textArea.setValue("");
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public String getValue() {
        return textArea.getValue();
    }

    public void scrollToEnd() {
        textArea.getElement().setScrollTop(1000000);
    }

    public void setValue(String value) {
        textArea.setValue(value);
    }

    TextArea textArea;
}
