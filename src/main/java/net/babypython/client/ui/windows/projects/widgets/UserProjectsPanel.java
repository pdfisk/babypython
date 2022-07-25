package net.babypython.client.ui.windows.projects.widgets;

import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.ui.session.Session;
import net.babypython.client.ui.windows.projects.UserProjectsWindow;
import net.babypython.client.vm.containers.dictionaries.RequestParamsDictionary;

public class UserProjectsPanel extends ProjectsPanel {

    public UserProjectsPanel(GwtWindow parentWindow) {
        super(parentWindow);
    }

    @Override
    public void delete() {
        RequestParamsDictionary requestParams = new RequestParamsDictionary();
        String name = getProjectName();
        requestParams.put("name", name);
        projectsLoader.deleteUserProject(requestParams);
    }

    protected String getProjectName() {
        if (!(parentWindow instanceof UserProjectsWindow))
            return "";
        return ((UserProjectsWindow) parentWindow).getProjectName();
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
        String name = getProjectName();
        String code = getCode();
        requestParams.put("name", name);
        requestParams.put("description", "");
        requestParams.put("is_shared", false);
        requestParams.put("owner_name", Session.getCurrentUser().username);
        requestParams.put("code", code);
        projectsLoader.updateUserProject(requestParams);
    }

}
