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
import com.google.gwt.user.client.ui.HTML;
import net.babypython.client.ui.constants.StyleConstants;
import net.babypython.client.vm.containers.lists.StringList;

public class GwtTextHtml extends HTML {

    public GwtTextHtml() {
        super();
        setStyles();
        addText();
    }

    protected void addText() {
        StringList stringList = new StringList();
        addTextItems(stringList);
        setHTML(stringList.asStringWithSpaces());
    }

    protected void addTextItems(StringList stringList) {
    }

    void setStyles() {
        Style style = getElement().getStyle();
        setStyles(style);
    }

    protected void setStyles(Style style) {
        style.setProperty(STYLE_FONT_FAMILY, StyleConstants.HtmlTextFontFamily);
        style.setFontSize(StyleConstants.HtmlTextFontSize, Style.Unit.PX);
        style.setFontWeight(Style.FontWeight.BOLD);
        style.setDisplay(Style.Display.INLINE_BLOCK);
        style.setFloat(Style.Float.LEFT);
    }

    private static final String STYLE_FONT_FAMILY = "fontFamily";
}
