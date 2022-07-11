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
package net.babypython.client.ui.windows.welcome.widgets.baby_python_page;

import com.google.gwt.dom.client.Style;
import net.babypython.client.ui.constants.CommonWindowConstants;
import net.babypython.client.ui.constants.UrlConstants;
import net.babypython.client.ui.gwt.widgets.GwtTextHtml;
import net.babypython.client.vm.containers.lists.StringList;

public class Text extends GwtTextHtml {

    public Text(int pct) {
        super();
        setWidth(CommonWindowConstants.percentRestStr(pct));
        setHeight(CommonWindowConstants.Auto);
    }

    protected void addTextItems(StringList stringList) {
        stringList.add(WELCOME_1);
        stringList.add(WELCOME_2);
        stringList.add(NEWLINE_2);
        stringList.add(CLICK_OPEN_PROJECTS);
        stringList.add(NEWLINE_2);
        stringList.add(NEWLINE_2);
        stringList.add(READ_DOCS_1);
        stringList.add(READ_DOCS_2);
        stringList.add(READ_DOCS_3);
        stringList.add(READ_DOCS_4);
    }

    protected void setStyles(Style style) {
        super.setStyles(style);
        style.setPaddingLeft(15, Style.Unit.PX);
        style.setPaddingTop(15, Style.Unit.PX);
    }

    static final String CLICK_OPEN_PROJECTS = "Click on the 'Open Projects' button below to get started.";
    static final String NEWLINE_2 = "<br><br>";
    static final String READ_DOCS_1 = "The docs can be found here: ";
    static final String READ_DOCS_2 = "<a href=\"" + UrlConstants.Docs + "\" target=\"_blank\">";
    static final String READ_DOCS_3 = "BabyPython Docs";
    static final String READ_DOCS_4 = "</a>";
    static final String WELCOME_1 = "Welcome to Baby Python - ";
    static final String WELCOME_2 = "the learning environment for beginners of all ages.";
}
