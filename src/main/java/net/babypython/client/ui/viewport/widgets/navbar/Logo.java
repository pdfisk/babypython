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
package net.babypython.client.ui.viewport.widgets.navbar;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Image;
import net.babypython.client.ui.constants.DimensionConstants;
import net.babypython.client.ui.constants.ImageConstants;

public class Logo extends Image {

    public Logo() {
        super();
        setWidth(DimensionConstants.NavBarLogoWidth + "px");
        setHeight(DimensionConstants.NavBarLogoHeight + "px");
        getElement().getStyle().setMarginRight(DimensionConstants.NavBarLogoMarginRight, Style.Unit.PX);
        setUrl(ImageConstants.logoUrl);
    }

}
