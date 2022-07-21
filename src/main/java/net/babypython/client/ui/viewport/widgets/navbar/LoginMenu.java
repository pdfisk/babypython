package net.babypython.client.ui.viewport.widgets.navbar;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItem;
import net.babypython.client.ui.session.Session;
import net.babypython.client.ui.session.SessionState;
import net.babypython.client.ui.util.Logable;
import net.babypython.client.ui.viewport.navbar.NavBar;

public class LoginMenu extends SessionMenuBar {

    public LoginMenu(NavBar navBar) {
        super(true);
        this.navBar = navBar;
        addItems();
    }

    void addItems() {
        loginItem = addItem("Login", new Command() {
            @Override
            public void execute() {
                onLoginOrLogout();
            }
        });

        addSeparator();

        registerItem = addItem("Register", new Command() {
            @Override
            public void execute() {
                onRegister();
            }
        });

        addSeparator();

        addItem("Settings", new Command() {
            @Override
            public void execute() {
                onSettings();
            }
        });
    }

    void onLoginOrLogout() {
        switch (Session.getSessionState()) {
            case LoggedIn:
            case LoggedInAsAdmin:
                Session.setLoggedOut();
                break;
            case LoggedOut:
                navBar.onLogin();
                break;
        }
    }

    void onRegister() {
        navBar.onRegister();
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


    void onSettings() {
        Logable.info("onSettings ");
    }

    void setLoggedInAsAdminState() {
        setLoggedInState();
    }

    void setLoggedInState() {
        parentMenuItem.setText("Logout");
        loginItem.setText("Logout");
        registerItem.setEnabled(false);
    }

    void setLoggedOutState() {
        parentMenuItem.setText("Login");
        loginItem.setText("Login");
        registerItem.setEnabled(true);
    }

    public void setParentMenuItem(MenuItem menuItem) {
        parentMenuItem = menuItem;
    }

    MenuItem loginItem;
    NavBar navBar;
    MenuItem parentMenuItem;
    MenuItem registerItem;
}
