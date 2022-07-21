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
package net.babypython.client.ui.windows.welcome;

import net.babypython.client.ui.constants.CommonWindowConstants;
import net.babypython.client.ui.constants.WindowButtonConstants;
import net.babypython.client.ui.gwt.widgets.GwtDeckPanel;
import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.ui.gwt.window.widgets.GwtWindowButtonBar;
import net.babypython.client.ui.windows.projects.SharedProjectsWindow;
import net.babypython.client.ui.windows.welcome.widgets.ButtonBar;
import net.babypython.client.ui.windows.welcome.widgets.baby_python_page.BabyPythonPage;
import net.babypython.client.ui.util.WindowUtil;

public class WelcomeWindow extends GwtWindow {

    public WelcomeWindow() {
        super();
        setContent(createContent());
    }

    protected void addPanels() {
        gwtDeckPanel.addWidget(createBabyPythonPage());
    }

    protected BabyPythonPage createBabyPythonPage() {
        return babyPythonPage = new BabyPythonPage();
    }

    protected GwtDeckPanel createContent() {
        gwtDeckPanel = new GwtDeckPanel();
        addPanels();
        selectBabyPython();
        return gwtDeckPanel;
    }

    @Override
    protected GwtWindowButtonBar defaultButtonBar() {
        return new ButtonBar(this);
    }

    @Override
    protected int defaultHeight() {
        return WindowUtil.getPctScreenHeight(CommonWindowConstants.WelcomeWindowHeightPct);
    }

    @Override
    protected String defaultTitle() {
        return CommonWindowConstants.WelcomeWindowTitle;
    }

    @Override
    protected int defaultWidth() {
        return WindowUtil.getPctScreenWidth(CommonWindowConstants.WelcomeWindowWidthPct);
    }

    @Override
    public void onButtonClicked(String actionTag) {
        switch (actionTag) {
            case WindowButtonConstants.WelcomeActionOpenProjects:
                onOpenProjects();
                break;
        }
    }

    void onOpenProjects() {
        minimize();
        SharedProjectsWindow.getInstance().show();
    }

    public void selectBabyPython() {
        gwtDeckPanel.showWidget(babyPythonPage);
    }

    protected BabyPythonPage babyPythonPage;
    protected GwtDeckPanel gwtDeckPanel;
}
