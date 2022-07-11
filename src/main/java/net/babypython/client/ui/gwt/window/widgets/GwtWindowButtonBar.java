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
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import net.babypython.client.ui.constants.CommonWindowConstants;
import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.vm.containers.lists.StringList;

public class GwtWindowButtonBar extends FlowPanel {

    public GwtWindowButtonBar(GwtWindow window) {
        super();
        this.window = window;
        addStyleName("buttonBarBorders");
        setWidth("100%");
        setStyle();
        if (hasTextBox())
            addTextBox();
        addButtons(defaultButtons());
        addSpecialButtons();
    }

    public GwtWindowButton addButton(String label) {
        GwtWindowButton button = new GwtWindowButton(label, window);
        button.getElement().getStyle().setMarginRight(CommonWindowConstants.ButtonBarSpacing, Style.Unit.PX);
        add(button);
        return button;
    }

    protected void addButtons(StringList buttons) {
        for (int i = 0; i < buttons.size(); i++) {
            String label = buttons.get(i);
            addButton(label);
        }
    }

    protected void addSpecialButtons() {
    }

    protected void addTextBox() {
        textBox = new TextBox();
        textBox.getElement().getStyle().setBorderStyle(Style.BorderStyle.NONE);
        textBox.getElement().getStyle().setMargin(0, Style.Unit.PX);
        textBox.getElement().getStyle().setMarginRight(CommonWindowConstants.ButtonBarRightPadding + CommonWindowConstants.ButtonBarSpacing, Style.Unit.PX);
        textBox.setHeight(CommonWindowConstants.ButtonBarTextBoxHeight + "px");
        textBox.setWidth(defaultTextBoxWidth() + "px");
        textBox.getElement().addClassName("buttonBarTextBox");
        add(textBox);
    }

    protected StringList defaultButtons() {
        return new StringList();
    }

    protected int defaultTextBoxWidth() {
        return CommonWindowConstants.ButtonBarTextBoxWidth;
    }

    public String getTextBoxValue() {
        if (textBox == null)
            return "";
        return textBox.getValue();
    }

    protected boolean hasTextBox() {
        return false;
    }

    void setStyle() {
        Style style = getElement().getStyle();
        int leftPadding = hasTextBox() ? 0 : CommonWindowConstants.ButtonBarLeftPadding;
        style.setPaddingLeft(leftPadding, Style.Unit.PX);
        style.setPaddingRight(CommonWindowConstants.ButtonBarRightPadding, Style.Unit.PX);
        style.setPaddingTop(CommonWindowConstants.ButtonBarTopPadding, Style.Unit.PX);
    }

    public void setTextBoxValue(String value) {
        if (textBox == null)
            return;
        textBox.setValue(value);
    }

    TextBox textBox;
    GwtWindow window;
}
