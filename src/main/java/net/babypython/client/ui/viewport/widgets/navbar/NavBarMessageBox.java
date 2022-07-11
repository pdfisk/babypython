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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;
import net.babypython.client.ui.constants.ColorConstants;
import net.babypython.client.ui.constants.ViewportConstants;
import net.babypython.client.vm.events.error.ErrorEvent;
import net.babypython.client.vm.events.error.ErrorEventBus;

public class NavBarMessageBox extends HTML {

    public static void showMessage(String text) {
        getInstance().showTimedMessage(text);
    }

    public void clearMessage() {
        setHTML("");
    }

    void clearMessageAfterDelay() {
        Timer t = new Timer() {
            @Override
            public void run() {
                clearMessage();
            }
        };
        t.schedule(ViewportConstants.MessageBoxClearDelay);
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        errorHandlerRegistration = errorEventBinder.bindEventHandlers(this, ErrorEventBus.getInstance());
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        errorHandlerRegistration.removeHandler();
    }

    @EventHandler
    public void onErrorEvent(ErrorEvent event) {
        onError(event.message);
    }

    void onError(String message) {
        showErrorMessage(message);
    }

    void setMessageHTML(String message, String textColor, String backgroundColor) {
        String style = "font-size:1.1em;";
        style += "text-overflow:ellipsis;";
        style += "overflow:hidden;";
        style += "font-style:italic;";
        style += "color:"+textColor+";";
        style += "padding:1px 2px;";
        style += "border-radius:3px;";
        style += "border:1px solid slategray;";
        String html = "<div style=\"" + style + "\">" + message + "</div>";
        setHTML(html);
        getElement().getStyle().setBackgroundColor(backgroundColor);
    }

    void showTimedMessage(String message) {
        setMessageHTML(message, ColorConstants.MessageBoxStandardColor, ColorConstants.MessageBoxStandardBackground);
        clearMessageAfterDelay();
    }

    public void showErrorMessage(String message) {
        setMessageHTML(message, ColorConstants.MessageBoxErrorColor, ColorConstants.MessageBoxErrorBackground);
        clearMessageAfterDelay();
    }

    interface ErrorEventBinder extends EventBinder<NavBarMessageBox> {
    }

    interface SessionEventBinder extends EventBinder<NavBarMessageBox> {
    }

    public static NavBarMessageBox getInstance() {
        if (instance == null)
            instance = new NavBarMessageBox();
        return instance;
    }

    NavBarMessageBox() {
        super();
    }

    static NavBarMessageBox instance;

    private final NavBarMessageBox.ErrorEventBinder errorEventBinder = GWT.create(NavBarMessageBox.ErrorEventBinder.class);
    private final NavBarMessageBox.SessionEventBinder sessionEventBinder = GWT.create(NavBarMessageBox.SessionEventBinder.class);
    int loggedOutCount = 0;
    HandlerRegistration errorHandlerRegistration;
    HandlerRegistration sessionHandlerRegistration;
}
