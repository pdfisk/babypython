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
package net.babypython.client.ui.windows.projects;

import com.google.gwt.user.client.ui.Widget;
import net.babypython.client.ui.constants.CommonWindowConstants;
import net.babypython.client.ui.constants.WindowButtonConstants;
import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.ui.gwt.window.widgets.GwtWindowButtonBar;
import net.babypython.client.ui.viewport.widgets.desktop.WindowManager;
import net.babypython.client.ui.windows.projects.widgets.ButtonBar;
import net.babypython.client.ui.windows.projects.widgets.ProjectsPanel;
import net.babypython.client.ui.windows.workbench.WorkbenchWindow;

public class ProjectsWindow extends GwtWindow {

    @Override
    protected boolean defaultAutoShow() {
        return false;
    }

    @Override
    protected GwtWindowButtonBar defaultButtonBar() {
        return new ButtonBar(this);
    }

    @Override
    public boolean defaultHideOnClose() {
        return true;
    }

    @Override
    protected Widget defaultContent() {
        return projectsPanel = new ProjectsPanel(this);
    }

    @Override
    protected int defaultHeight() {
        return CommonWindowConstants.ProjectsBrowserWindowHeight;
    }

    @Override
    protected String defaultTitle() {
        return CommonWindowConstants.ProjectsBrowserWindowHeader;
    }

    @Override
    protected int defaultWidth() {
        return CommonWindowConstants.ProjectsBrowserWindowWidth;
    }

    public ProjectsPanel getProjectsPanel() {
        return projectsPanel;
    }

    @Override
    public void onButtonClicked(String actionTag) {
        switch (actionTag) {
            case WindowButtonConstants.ProjectsActionOpenInWorkbench:
                onOpenInWorkbench();
                break;
            case WindowButtonConstants.ProjectsActionRefresh:
                onRefresh();
                break;
        }
    }

    void onOpenInWorkbench() {
        String code = projectsPanel.getCode();
        WorkbenchWindow workbenchWindow = WindowManager.getInstance().getOrOpenWorkbenchWindow();
        workbenchWindow.setCode(code);
        minimize();
    }

    void onRefresh() {
        projectsPanel.refresh();
    }

    public void toggleVisible() {
        if (visible) {
            hide();
            visible = false;
        } else {
            show();
            visible = true;
        }
    }

    public static ProjectsWindow getInstance() {
        if (instance == null)
            instance = new ProjectsWindow();
        return instance;
    }

    boolean visible = false;

    protected ProjectsPanel projectsPanel;
    static ProjectsWindow instance;
}
