package net.babypython.client.ui.viewport.widgets.navbar;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItem;
import net.babypython.client.ui.constants.ViewportConstants;
import net.babypython.client.ui.session.Session;
import net.babypython.client.ui.session.SessionState;
import net.babypython.client.ui.util.Logable;
import net.babypython.client.ui.viewport.navbar.NavBar;

public class ProjectsMenu extends SessionMenuBar {

    public ProjectsMenu(NavBar navBar) {
        super(true);
        this.navBar = navBar;
        addItems();
    }

    void addItems() {
        sharedProjectsItem = addItem(ViewportConstants.ProjectsMenuSharedProjects, new Command() {
            @Override
            public void execute() {
                navBar.onSharedProjects();
            }
        });

        addSeparator();

        myProjectsItem = addItem(ViewportConstants.ProjectsMenuMyProjects, new Command() {
            @Override
            public void execute() {
                navBar.onMyProjects();
            }
        });

    }

    @Override
    public void onSessionStateChanged(SessionState sessionState) {
        switch (sessionState) {
            case LoggedIn:
                setLoggedInState();
                break;
            case LoggedInAsAdmin:
                setLoggedInAsAdminState();
                break;
            case LoggedOut:
                setLoggedOutState();
                break;
        }
    }

    void setLoggedInAsAdminState() {
        setLoggedInState();
    }

    void setLoggedInState() {
        myProjectsItem.setEnabled(true);
    }

    void setLoggedOutState() {
        myProjectsItem.setEnabled(false);
    }

    MenuItem myProjectsItem;
    NavBar navBar;
    MenuItem sharedProjectsItem;
}
