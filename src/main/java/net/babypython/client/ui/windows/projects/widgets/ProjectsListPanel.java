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

import net.babypython.client.ui.gwt.widgets.GwtListPanel;
import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.ui.interfaces.IHandleTextValue;
import net.babypython.client.ui.interfaces.IListSelectionHandler;
import net.babypython.client.vm.containers.lists.StringList;

public class ProjectsListPanel extends GwtListPanel implements IListSelectionHandler {

    public ProjectsListPanel(GwtWindow window, ProjectsPanel projectsPanel) {
        super(window);
        this.projectsPanel = projectsPanel;
        setSelectionHandler(this);
    }

    @Override
    public void onListSelection(int index) {
        if (projectNames == null)
            return;
        String projectName = projectNames.get(index);
        if (getWindow() instanceof IHandleTextValue)
            ((IHandleTextValue)getWindow()).handleTextValue(projectName);
        projectsPanel.onSelectProjectName(projectName);
    }

    public void updateProjectNames(StringList projectNames) {
        this.projectNames = projectNames;
        clear();
        for (String projectName : projectNames)
            addItem(projectName);
    }

    StringList projectNames;
    ProjectsPanel projectsPanel;
}
