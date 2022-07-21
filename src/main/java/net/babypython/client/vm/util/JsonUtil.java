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
package net.babypython.client.vm.util;

import com.google.gwt.json.client.*;

public class JsonUtil {

    public static JSONValue decode(String jsonStr) {
        return JSONParser.parseStrict(jsonStr);
    }

    public static JSONValue decode64(String jsonStr64) {
        try {
            jsonStr64 = jsonStr64.trim();
            String jsonStr = decodeBase64(jsonStr64);
            return decode(jsonStr);
        } catch (Exception e) {
            return null;
        }
    }

    public static String encode64(JSONObject jsonObject) {
        return encodeBase64(jsonObject.toString());
    }

    public static int getInt(JSONValue jsonValue) {
        if (jsonValue == null)
            return 0;
        JSONNumber jsonNumber = jsonValue.isNumber();
        if (jsonNumber != null)
            return (int) jsonNumber.doubleValue();
        return 0;
    }

    public static int getIntField(JSONObject jsonObject, String key) {
        return getInt(jsonObject.get(key));
    }

    public static String getString(JSONValue jsonValue) {
        if (jsonValue == null)
            return "";
        JSONString jsonString = jsonValue.isString();
        if (jsonString != null)
            return jsonString.stringValue();
        return "";
    }

    public static String getStringField(JSONObject jsonObject, String key) {
        return getString(jsonObject.get(key));
    }

    native static String decodeBase64(String base64String) /*-{
        return $wnd.atob(base64String);
    }-*/;

    native static String encodeBase64(String aString) /*-{
        return $wnd.btoa(aString);
    }-*/;

}
