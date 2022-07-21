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
package net.babypython.client.ui.viewport;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;
import net.babypython.client.ui.constants.DimensionConstants;
import net.babypython.client.ui.events.resize.ResizeEventBus;
import net.babypython.client.ui.gwt.widgets.GwtDockPanel;
import net.babypython.client.ui.session.Session;
import net.babypython.client.ui.session.SessionState;
import net.babypython.client.ui.util.Logable;
import net.babypython.client.ui.viewport.navbar.NavBar;
import net.babypython.client.ui.viewport.widgets.desktop.Desktop;
import net.babypython.client.ui.windows.welcome.WelcomeWindow;
import net.babypython.client.vm.events.session.SessionEvent;
import net.babypython.client.vm.events.session.SessionEventBus;
import net.babypython.client.vm.interfaces.ISessionState;
import net.babypython.client.vm.workspace.Workspace;

public class Viewport extends GwtDockPanel implements ISessionState {

    public Viewport() {
        super();
        initializeWorkspace();
        addNavBar();
        addDesktop();
        startup();
    }

    protected NavBar createNavBar() {
        return NavBar.getInstance();
    }

    protected void initializeWorkspace() {
        Workspace.getInstance();
    }

    void addDesktop() {
        desktop = new Desktop();
        add(desktop);
    }

    void addNavBar() {
        navBar = createNavBar();
        addNorth(navBar, DimensionConstants.NavBarHeight);
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        resizeHandlerRegistration = resizeEventBinder.bindEventHandlers(this, ResizeEventBus.getInstance());
        sessionHandlerRegistration = sessionEventBinder.bindEventHandlers(this, SessionEventBus.getInstance());
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                ResizeEventBus.fireResizeEvent();
            }
        });
        Session.setLoggedOut();
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        sessionHandlerRegistration.removeHandler();
    }

    @EventHandler
    void onSessionStateChanged(SessionEvent event) {
        onSessionStateChanged(event.getSessionState());
    }

    @Override
    public void onSessionStateChanged(SessionState sessionState) {
        navBar.onSessionStateChanged(sessionState);
    }

    void startup() {
        new WelcomeWindow();
    }

    interface ResizeEventBinder extends EventBinder<Viewport> {
    }

    interface SessionEventBinder extends EventBinder<Viewport> {
    }

    private final Viewport.ResizeEventBinder resizeEventBinder = GWT.create(Viewport.ResizeEventBinder.class);
    private final Viewport.SessionEventBinder sessionEventBinder = GWT.create(Viewport.SessionEventBinder.class);

    protected Desktop desktop;
    protected NavBar navBar;
    HandlerRegistration resizeHandlerRegistration;
    HandlerRegistration sessionHandlerRegistration;
}
