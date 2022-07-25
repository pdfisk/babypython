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
package net.babypython.client.vm.util.requests;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import net.babypython.client.ui.interfaces.IRequestHandler;
import net.babypython.client.ui.util.Logable;
import net.babypython.client.vm.containers.dictionaries.ObjectDictionary;
import net.babypython.client.vm.containers.dictionaries.RequestParamsDictionary;
import net.babypython.client.vm.util.JsonUtil;
import net.babypython.client.vm.vm.runtime.operations.StringOps;

public class RequestUtil extends Logable {

    public static void sendDeleteRequest(String url, IRequestHandler handler, RequestParamsDictionary requestParams) {
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.DELETE, url);
        requestBuilder.setHeader("Content-Type", "application/json;charset=UTF-8");
        requestBuilder.setRequestData(buildPostJsonString(requestParams));
        requestBuilder.setCallback(new RequestCallback() {
            @Override
            public void onResponseReceived(Request request, Response response) {
                handleCallback(handler, response.getText());
            }

            @Override
            public void onError(Request request, Throwable exception) {
                handleCallback(handler, "Error: " + exception.getMessage());
            }
        });
        try {
            requestBuilder.send();
        } catch (Exception e) {
            handleCallback(handler, "Error: " + e.getMessage());
        }
    }

    public static void sendGetRequest(String url, IRequestHandler handler) {
        sendGetRequest(url, handler, null);
    }

    public static void sendGetRequest(String url, IRequestHandler handler, RequestParamsDictionary requestParams) {
        if (requestParams != null)
            url = buildGetUrl(url, requestParams);
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, url);
        requestBuilder.setCallback(new RequestCallback() {
            @Override
            public void onResponseReceived(Request request, Response response) {
                handleCallback(handler, response.getText());
            }

            @Override
            public void onError(Request request, Throwable exception) {
                handleCallback(handler, "Error: " + exception.getMessage());
            }
        });
        try {
            requestBuilder.send();
        } catch (Exception e) {
            handleCallback(handler, "Error: " + e.getMessage());
        }
    }

    public static void sendPostRequest(String url, IRequestHandler handler, RequestParamsDictionary requestParams) {
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, url);
        requestBuilder.setHeader("Content-Type", "application/json;charset=UTF-8");
        requestBuilder.setRequestData(buildPostJsonString(requestParams));
        requestBuilder.setCallback(new RequestCallback() {
            @Override
            public void onResponseReceived(Request request, Response response) {
                handleCallback(handler, response.getText());
            }

            @Override
            public void onError(Request request, Throwable exception) {
                handleCallback(handler, "Error: " + exception.getMessage());
            }
        });
        try {
            requestBuilder.send();
        } catch (Exception e) {
            handleCallback(handler, "Error: " + e.getMessage());
        }
    }

    static String buildGetUrl(String url, RequestParamsDictionary requestParams) {
        if (requestParams.isEmpty())
            return url;
        String paramsString = "?";
        String sep = "";
        for (String key : requestParams.keySet()) {
            paramsString += sep;
            sep = "&";
            paramsString += key;
            paramsString += "=";
            paramsString += StringOps.coerce(requestParams.get(key));
        }
        return url + paramsString;
    }

   static String buildPostJsonString(RequestParamsDictionary paramsDictionary) {
        JSONObject jsonObject = new JSONObject();
        for(String key:paramsDictionary.keySet()) {
            Object value = paramsDictionary.get(key);
            jsonObject.put(key, JsonUtil.toJsonValue(paramsDictionary.get(key)));
        }
        return jsonObject.toString();
    }

    static void handleCallback(IRequestHandler handler, String text) {
        if (handler != null)
            handler.handleCallback(text);
    }

}
