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

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;
import net.babypython.client.ui.events.drag.DragEvent;
import net.babypython.client.ui.events.drag.DragEventBus;
import net.babypython.client.ui.events.drag.DragState;
import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.ui.interfaces.IMouseEvents;
import net.babypython.client.ui.interfaces.IOnLoad;

public abstract class GwtFramePanel extends SimplePanel implements IMouseEvents {

    public GwtFramePanel(GwtWindow window) {
        this(window, null);
    }

    public GwtFramePanel(GwtWindow window, IOnLoad onLoadHandler) {
        super();
        this.gwtWindow = window;
        this.onLoadHandler = onLoadHandler;
        isLoaded = false;
        setHeight("100%");
        setWidth("100%");
        gwtFrame = new GwtFrame(this);
        gwtFrame.setHeight("100%");
        gwtFrame.setWidth("100%");
        shieldPanel = new SimplePanel();
        shieldPanel.setHeight("100%");
        shieldPanel.setWidth("100%");
        deckLayoutPanel = new DeckLayoutPanel();
        deckLayoutPanel.setHeight("100%");
        deckLayoutPanel.setWidth("100%");
        deckLayoutPanel.add(gwtFrame);
        deckLayoutPanel.add(shieldPanel);
        setWidget(deckLayoutPanel);
        setUrl(defaultUrl());
        showFrame();
    }

    abstract protected String defaultUrl();

    public JavaScriptObject getContentWindow() {
        return contentWindow;
    }

    public GwtWindow getGwtWindow() {
        return gwtWindow;
    }

    protected void hideFrame() {
        deckLayoutPanel.showWidget(shieldPanel);
    }

    public String getUrl() {
        return gwtFrame.getUrl();
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        dragHandlerRegistration = eventBinder.bindEventHandlers(this, DragEventBus.getInstance());
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        dragHandlerRegistration.removeHandler();
    }

    @EventHandler
    void onDragStateChanged(DragEvent event) {
        onDragStateChanged(event.dragState);
    }

    void onDragStateChanged(DragState dragState) {
        if (dragState == DragState.STARTED)
            hideFrame();
        else
            showFrame();
    }

    @Override
    protected void onLoad() {
        isLoaded = true;
        contentWindow = nativeGetContentWindow(gwtFrame.getElement());
        if (onLoadHandler != null)
            onLoadHandler.run();
    }

    public void onMouseMove(Event event) {
        gwtWindow.onMouseMove(event);
    }

    public void onMouseUp(Event event) {
        gwtWindow.onMouseUp(event);
    }

    public void setLoadHandler(IOnLoad onLoadHandler) {
        this.onLoadHandler = onLoadHandler;
    }

    public void setUrl(String url) {
        if (url != null)
            gwtFrame.setUrl(url);
    }

    protected void showFrame() {
        deckLayoutPanel.showWidget(gwtFrame);
    }

    native JavaScriptObject nativeGetContentWindow(JavaScriptObject element) /*-{
        return element.contentWindow;
    }-*/;

    interface GwtFrameEventBinder extends EventBinder<GwtFramePanel> {
    }

    private final GwtFrameEventBinder eventBinder = GWT.create(GwtFramePanel.GwtFrameEventBinder.class);

    protected JavaScriptObject contentWindow;
    protected DeckLayoutPanel deckLayoutPanel;
    HandlerRegistration dragHandlerRegistration;
    protected GwtFrame gwtFrame;
    protected boolean isLoaded;
    protected IOnLoad onLoadHandler;
    protected SimplePanel shieldPanel;
    protected GwtWindow gwtWindow;
}
