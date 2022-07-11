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
import com.google.gwt.user.client.ui.*;
import net.babypython.client.vm.util.StringUtil;

public class GwtForm extends FormPanel {

    public GwtForm() {
        super();
        grid = new FlexTable();
        grid.setWidth("100%");
        grid.setHeight("100%");
        grid.getElement().getStyle().setPaddingLeft(3, Style.Unit.PX);
        grid.getElement().getStyle().setPaddingRight(3, Style.Unit.PX);
        labelWidth = defaultLabelWidth();
        widgetAdjust = defaultWidgetAdjust();
        setWidget(grid);
        addFields();
    }

    protected void addFields() {
    }

    public GwtTextBox addTextBox(String text) {
        GwtTextBox textBox = new GwtTextBox();
        addLabeledWidget(text, textBox);
        return textBox;
    }

    public void addLabeledWidget(String text, Widget widget) {
        addLabeledWidget(text, widget, null);
    }

    public void addLabeledWidget(String text, Widget widget, String name) {
        if (name == null)
            name = StringUtil.tagify(text);
        Label label = new Label(text + ":");
        label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        label.setWidth(labelWidth + "px");
        widget.setWidth((getElement().getClientWidth() - labelWidth - widgetAdjust) + "px");
        int row = grid.getRowCount();
        grid.setWidget(row, 0, label);
        grid.setWidget(row, 1, widget);
    }

    protected int defaultLabelWidth() {
        return 30;
    }

    protected int defaultWidgetAdjust() {
        return 0;
    }

    FlexTable grid;
    int labelWidth;
    int widgetAdjust;
}
