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

import com.google.gwt.core.client.JavaScriptObject;

public class DomUtils {

    public static native int clientHeight(JavaScriptObject o) /*-{
        return o.clientHeight;
    }-*/;

    public static native String decodeBase64(String base64Str) /*-{
        return atob(base64Str);
    }-*/;

    public static native String encodeBase64(String str) /*-{
        return btoa(str);
    }-*/;

    public static native Object err(Object... args) /*-{
        $wnd.console.log.apply(null, args);
        return null;
    }-*/;

    public static native String getHtml(JavaScriptObject o) /*-{
        return o.getElementHTML();
    }-*/;

    public static native boolean isBlazor() /*-{
        return $wnd['DotNet'] ? true : false;
    }-*/;

    public static native boolean isWebView() /*-{
        return ($wnd['chrome'] && $wnd['chrome']['webview']) ? true : false;
    }-*/;

    public static native void log(Object... args) /*-{
        $wnd.console.log.apply(null, args);
    }-*/;

    public static native void openBrowserTab(String url) /*-{
        $wnd.open(url);
    }-*/;

    public static native void setX(Object... args) /*-{
        $wnd.X = args;
    }-*/;

}
