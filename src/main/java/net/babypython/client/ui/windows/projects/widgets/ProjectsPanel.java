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
package net.babypython.client.ui.windows.projects.widgets;

import com.google.gwt.user.client.Timer;
import net.babypython.client.ui.gwt.widgets.GwtSplitLayoutPanel;
import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.ui.interfaces.IAfterResize;
import net.babypython.client.ui.interfaces.IHandleStringListData;
import net.babypython.client.ui.interfaces.IHandleTextValue;
import net.babypython.client.ui.widgets.monaco.PythonEditor;
import net.babypython.client.vm.containers.lists.StringList;
import net.babypython.client.vm.stores.projects.ProjectsStore;

public class ProjectsPanel extends GwtSplitLayoutPanel implements IAfterResize, IHandleStringListData, IHandleTextValue {

    public ProjectsPanel(GwtWindow parentWindow) {
        super();
        this.parentWindow = parentWindow;
        projectsLoader = ProjectsStore.getInstance();
        lhsProjectsPanel = new ProjectsListPanel(this.parentWindow, this);
        rhsPythonEditor = new PythonEditor(this.parentWindow);
        addWest(lhsProjectsPanel, this.parentWindow.defaultContentWidth() * 0.3);
        add(rhsPythonEditor);
    }

    @Override
    public void afterResize() {
    }

    public void clear() {
        rhsPythonEditor.clear();
    }

    public String getCode() {
        return rhsPythonEditor.getValue();
    }

    @Override
    public void handleStringListData(StringList fileNames) {
        lhsProjectsPanel.updateFileNames(fileNames);
    }

    @Override
    public void handleTextValue(String value) {
        rhsPythonEditor.setValue(value);
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        Timer t = new Timer() {
            @Override
            public void run() {
                refresh();
            }
        };
        t.schedule(0);
    }

    public void onSelectFileName(String fileName) {
        projectsLoader.getFileText(fileName, this);
    }

    public void refresh() {
        clear();
        projectsLoader.loadProjects(this);
    }

    GwtWindow parentWindow;
    ProjectsListPanel lhsProjectsPanel;
    ProjectsStore projectsLoader;
    PythonEditor rhsPythonEditor;
}
