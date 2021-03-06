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
package net.babypython.client.ui.windows.projects;

import com.google.gwt.user.client.ui.Widget;
import net.babypython.client.ui.constants.CommonWindowConstants;
import net.babypython.client.ui.constants.WindowButtonConstants;
import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.ui.gwt.window.widgets.GwtWindowButtonBar;
import net.babypython.client.ui.util.WindowUtil;
import net.babypython.client.ui.viewport.widgets.desktop.WindowManager;
import net.babypython.client.ui.windows.projects.widgets.SharedProjectsButtonBar;
import net.babypython.client.ui.windows.projects.widgets.ProjectsPanel;
import net.babypython.client.ui.windows.projects.widgets.SharedProjectsPanel;
import net.babypython.client.ui.windows.workbench.WorkbenchWindow;

public class SharedProjectsWindow extends GwtWindow {

    @Override
    protected boolean defaultAutoShow() {
        return false;
    }

    @Override
    protected GwtWindowButtonBar defaultButtonBar() {
        return new SharedProjectsButtonBar(this);
    }

    @Override
    public boolean defaultHideOnClose() {
        return true;
    }

    @Override
    protected Widget defaultContent() {
        return projectsPanel = new SharedProjectsPanel(this);
    }

    @Override
    protected int defaultHeight() {
        return WindowUtil.getPctScreenHeight(CommonWindowConstants.ProjectsWindowHeightPct);
    }

    @Override
    protected String defaultTitle() {
        return CommonWindowConstants.ProjectsSharedProjectsWindowHeader;
    }

    @Override
    protected int defaultWidth() {
        return WindowUtil.getPctScreenWidth(CommonWindowConstants.ProjectsWindowWidthPct);
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

    public static SharedProjectsWindow getInstance() {
        if (instance == null)
            instance = new SharedProjectsWindow();
        return instance;
    }

    boolean visible = false;

    protected ProjectsPanel projectsPanel;
    static SharedProjectsWindow instance;
}
