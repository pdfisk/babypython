package net.babypython.client.ui.viewport.widgets.navbar;

import com.google.gwt.dom.client.Style;
import net.babypython.client.ui.constants.DimensionConstants;
import net.babypython.client.ui.gwt.widgets.GwtDockPanel;

public class InfoPanel extends GwtDockPanel {

    InfoPanel() {
        super();
        initialize();
        getElement().getStyle().setPaddingLeft(DimensionConstants.NavBarInfoPanelLeft, Style.Unit.PX);
        getElement().getStyle().setPaddingRight(DimensionConstants.NavBarInfoPanelRight, Style.Unit.PX);
    }

    void initialize() {
        sessionStatusPanel = new MiddleRightPanel();
        messagePanel = new MiddleCenterPanel();
        addEast(sessionStatusPanel, DimensionConstants.NavBarInfoSessionPanelWidth);
        add(messagePanel);
        showSignedOut();
        showMessage("HELLO WORLD!");
    }

    void showMessage(String text) {
        messagePanel.setText(text);
    }

    void setSessionText(String statusText) {
        sessionStatusPanel.setText(statusText);
    }

    public void showSignedInAs(String username) {
        setSessionText("signed in(" + username + ")");
    }

    public void showSignedOut() {
        setSessionText("<signed out>");
    }

    public static InfoPanel getInstance() {
        if (instance == null)
            instance = new InfoPanel();
        return instance;
    }

    MiddleCenterPanel messagePanel;
    MiddleRightPanel sessionStatusPanel;
    static InfoPanel instance;
}
