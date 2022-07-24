package net.babypython.client.ui.windows.projects.widgets;

import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.vm.containers.dictionaries.RequestParamsDictionary;

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

    @Override
    public void save() {
        RequestParamsDictionary requestParams = new RequestParamsDictionary();
        requestParams.put("One", 123);
        requestParams.put("Two", 456);
        requestParams.put("Three", 789);
        projectsLoader.updateUserProject(requestParams);
    }

}
