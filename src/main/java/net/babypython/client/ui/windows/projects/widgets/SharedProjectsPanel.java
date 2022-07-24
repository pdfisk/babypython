package net.babypython.client.ui.windows.projects.widgets;

import net.babypython.client.ui.gwt.window.GwtWindow;

public class SharedProjectsPanel extends ProjectsPanel {

    public SharedProjectsPanel(GwtWindow parentWindow) {
        super(parentWindow);
    }

    @Override
    public void onSelectProjectName(String fileName) {
        projectsLoader.getSharedProjectCode(fileName, this);
    }

    @Override
    public void refresh() {
        clear();
        projectsLoader.loadSharedProjects(this);
    }

}
