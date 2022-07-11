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
package net.babypython.client.ui.util;

import com.google.gwt.user.client.Window;
import net.babypython.client.ui.constants.DimensionConstants;
import net.babypython.client.vm.util.structures.Point;

public class WindowUtil {

    public static Point getAdjustedPoint(Point p, int width, int height) {
        int x, y;
        if (p.x < 0)
            x = getScreenWidth() - width + p.x;
        else
            x = p.x;
        if (p.y < 0)
            y = getScreenHeight() - height + p.y;
        else
            y = p.y + DimensionConstants.NavBarHeight;
        return new Point(x, y);
    }

    public static int getPctScreenHeight(int pct) {
        return getPctScreenHeight(pct, 0);
    }

    public static int getPctScreenHeight(int pct, int insets) {
        return (int) (getScreenHeight() * (pct / 100.0) - insets);
    }

    public static int getPctScreenWidth(int pct) {
        return getPctScreenWidth(pct, 0);
    }

    public static int getPctScreenWidth(int pct, int insets) {
        return (int) (getScreenWidth() * (pct / 100.0) - insets);
    }

    public static int getScreenHeight() {
        return Window.getClientHeight();
    }

    public static int getScreenWidth() {
        return Window.getClientWidth();
    }
}
