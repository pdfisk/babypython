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
package net.babypython.client.vm.util.requests;

import net.babypython.client.ui.constants.DocumentationConstants;
import net.babypython.client.ui.interfaces.IRequestHandler;
import net.babypython.client.ui.util.Logable;

public class DocUtil extends Logable {

    public static void getDocumentationIndexJson(IRequestHandler handler) {
        RequestUtil.sendGetRequest(DocumentationConstants.IndexJson, handler);
    }

    public static void getDocumentationMarkdown(String path, IRequestHandler handler) {
        String url = DocumentationConstants.MarkdownFolder + "/" + path + ".md";
        RequestUtil.sendGetRequest(url, handler);
    }

    public static void getPanelTemplate(IRequestHandler handler) {
        RequestUtil.sendGetRequest(DocumentationConstants.PanelTemplate, handler);
    }

    public static void getPageTemplate(IRequestHandler handler) {
        RequestUtil.sendGetRequest(DocumentationConstants.PageTemplate, handler);
    }

}
