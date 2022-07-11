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
package net.babypython.client.ui.viewport.widgets.desktop;

import net.babypython.client.ui.constants.CommonWindowConstants;
import net.babypython.client.ui.events.resize.ResizeEvent;
import net.babypython.client.ui.events.resize.ResizeEventBus;
import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.ui.windows.workbench.WorkbenchWindow;
import net.babypython.client.ui.util.Logable;
import net.babypython.client.ui.util.WindowUtil;
import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

import java.util.ArrayList;

public class WindowManager extends Logable {

    WindowManager() {
        super();
        eventBinder.bindEventHandlers(this, ResizeEventBus.getInstance());
    }

    public void addActiveWindow(GwtWindow gwtWindow) {
        if (!activeWindows.contains(gwtWindow))
            activeWindows.add(gwtWindow);
        arrangeActiveWindows();
    }

    public void addMinimizedWindow(GwtWindow gwtWindow) {
        removeActiveWindow(gwtWindow);
        if (!minimizedWindows.contains(gwtWindow))
            minimizedWindows.add(gwtWindow);
        arrangeMinimizedWindows();
    }

    public void arrangeActiveWindows() {
        for (int i = 0; i < activeWindows.size(); i++)
            activeWindows.get(i).setZIndex(10 * (i + 1));
    }

    public void arrangeMinimizedWindows() {
        int screenWidth = WindowUtil.getScreenWidth();
        int screenHeight = WindowUtil.getScreenHeight();
        int vSpace = 10;
        int hSpace = 10;
        int yPos = screenHeight - vSpace - CommonWindowConstants.MinimizedWindowHeight;
        int xPos = hSpace;
        for (int i = 0; i < minimizedWindows.size(); i++) {
            GwtWindow gwtWindow = minimizedWindows.get(i);
            gwtWindow.setWindowPosition(xPos, yPos);
            xPos += CommonWindowConstants.MinimizedWindowWidth + hSpace;
            if (xPos + CommonWindowConstants.MinimizedWindowWidth > screenWidth) {
                xPos = hSpace;
                yPos -= vSpace + CommonWindowConstants.MinimizedWindowHeight;
            }
        }
    }

    public void closeAllWindows() {
        for (int i = 0; i < activeWindows.size(); i++)
            activeWindows.get(i).hideOrClose();
    }

    public ArrayList<GwtWindow> getActiveWindows() {
        return activeWindows;
    }

    public WorkbenchWindow getOrOpenWorkbenchWindow() {
        for (int i = 0; i < minimizedWindows.size(); i++) {
            if (minimizedWindows.get(i) instanceof WorkbenchWindow) {
                minimizedWindows.get(i).restore();
                break;
            }
        }
        for (int i = 0; i < activeWindows.size(); i++) {
            if (activeWindows.get(i) instanceof WorkbenchWindow)
                return (WorkbenchWindow) activeWindows.get(i);
        }
        return new WorkbenchWindow();
    }

    public void minimizeAllWindows() {
        for (int i = 0; i < activeWindows.size(); i++)
            activeWindows.get(i).minimize();
    }

    public void moveToFront(GwtWindow gwtWindow) {
        removeActiveWindow(gwtWindow);
        addActiveWindow(gwtWindow);
        arrangeActiveWindows();
    }

    @EventHandler
    public void onProcessEvent(ResizeEvent event) {
        arrangeMinimizedWindows();
    }

    public void removeActiveWindow(GwtWindow gwtWindow) {
        if (activeWindows.contains(gwtWindow))
            activeWindows.remove(gwtWindow);
    }

    public void removeMinimizedWindow(GwtWindow gwtWindow) {
        if (minimizedWindows.contains(gwtWindow))
            minimizedWindows.remove(gwtWindow);
        addActiveWindow(gwtWindow);
        arrangeMinimizedWindows();
    }

    public void restoreAllWindows() {
        for (int i = 0; i < minimizedWindows.size(); i++)
            minimizedWindows.get(i).restore();
    }

    public static WindowManager getInstance() {
        if (instance == null)
            instance = new WindowManager();
        return instance;
    }

    interface ResizeEventBinder extends EventBinder<WindowManager> {
    }

    private final ResizeEventBinder eventBinder = GWT.create(ResizeEventBinder.class);

    ArrayList<GwtWindow> activeWindows = new ArrayList<>();
    ArrayList<GwtWindow> minimizedWindows = new ArrayList<>();
    static WindowManager instance;
}
