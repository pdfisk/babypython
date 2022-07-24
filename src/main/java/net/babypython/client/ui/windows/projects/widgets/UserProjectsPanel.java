package net.babypython.client.ui.windows.projects.widgets;

import net.babypython.client.ui.gwt.window.GwtWindow;

public class UserProjectsPanel extends ProjectsPanel {

    public UserProjectsPanel(GwtWindow parentWindow) {
        super(parentWindow);
    }

    @Override
    public void onSelectProjectName(String fileName) {
        projectsLoader.getUserProjectCode(fileName, this);
    }

    @Override
    public void refresh() {
        clear();
        projectsLoader.loadUserProjects(this);
    }

}
