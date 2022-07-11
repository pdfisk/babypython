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
package net.babypython.client.ui.windows.transcript;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;
import net.babypython.client.ui.windows.transcript.widgets.ButtonBar;
import net.babypython.client.ui.constants.CommonWindowConstants;
import net.babypython.client.vm.constants.OutputFormatting;
import net.babypython.client.vm.events.info.InfoEvent;
import net.babypython.client.vm.events.info.InfoEventBus;
import net.babypython.client.ui.gwt.widgets.GwtTranscriptPanel;
import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.ui.gwt.window.widgets.GwtWindowButtonBar;
import net.babypython.client.vm.util.structures.Point;
import net.babypython.client.vm.vm.interfaces.IStdOut;

public class TranscriptWindow extends GwtWindow implements IStdOut {

    public static IStdOut asStdOut() {
        return getInstance().transcriptPanel;
    }

    public static void clr() {
        asStdOut().clear();
    }

    public static void prn(String text) {
        asStdOut().prn(text);
    }

    @Override
    protected boolean defaultAutoShow() {
        return false;
    }

    @Override
    protected GwtWindowButtonBar defaultButtonBar() {
        return new ButtonBar(this);
    }

    @Override
    protected Widget defaultContent() {
        return transcriptPanel = new GwtTranscriptPanel(this);
    }

    @Override
    protected int defaultHeight() {
        return CommonWindowConstants.TranscriptWindowHeight;
    }

    @Override
    public boolean defaultHideOnClose() {
        return true;
    }

    @Override
    protected Point defaultPosition() {
        return CommonWindowConstants.TranscriptWindowPosition;
    }

    @Override
    public boolean defaultShowMinimizeButton() {
        return false;
    }

    @Override
    protected String defaultTitle() {
        return CommonWindowConstants.TranscriptWindowHeader;
    }

    @Override
    protected int defaultWidth() {
        return CommonWindowConstants.TranscriptWindowWidth;
    }

    @EventHandler
    public void onInfoEvent(InfoEvent event) {
        switch (event.logEventType) {
            case ERROR:
                onErrorMessage(event.message);
                break;
            case INFO:
                onInfoMessage(event.message);
                break;
        }
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        infoHandlerRegistration = eventBinder.bindEventHandlers(this, InfoEventBus.getInstance());
    }

    @Override
    public void onButtonClicked(String actionTag) {
        switch (actionTag) {
            case "clear":
                onClear();
                break;
        }
    }

    void onClear() {
        transcriptPanel.clear();
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        infoHandlerRegistration.removeHandler();
    }

    void onErrorMessage(String message) {
        show();
        prn(message);
    }

    void onInfoMessage(String message) {
        show();
        prn(message);
    }

    public static TranscriptWindow getInstance() {
        if (instance == null)
            instance = new TranscriptWindow();
        return instance;
    }

    @Override
    public OutputFormatting getFormatting() {
        return null;
    }

    @Override
    public void newline() {
        asStdOut().newline();
    }

    @Override
    public void pr(Object... args) {
        asStdOut().pr(args);
    }

    @Override
    public void prn(Object... args) {
        asStdOut().prn(args);
    }

    @Override
    public void setFormatting(OutputFormatting formatting) {

    }

    @Override
    public void setRepr() {

    }

    @Override
    public void setStr() {

    }

    interface InfoEventBinder extends EventBinder<TranscriptWindow> {
    }

    private final InfoEventBinder eventBinder = GWT.create(InfoEventBinder.class);

    HandlerRegistration infoHandlerRegistration;
    GwtTranscriptPanel transcriptPanel;
    static TranscriptWindow instance;
}
