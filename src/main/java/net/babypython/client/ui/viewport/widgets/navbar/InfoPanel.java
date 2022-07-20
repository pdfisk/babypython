package net.babypython.client.ui.viewport.widgets.navbar;

import com.google.gwt.dom.client.Style;
import net.babypython.client.constants.AppConstants;
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
        leftMessagePanel = new MiddleLeftPanel();
        centerMessagePanel = new MiddleCenterPanel();
        sessionPanel = new MiddleRightPanel();
        addWest(leftMessagePanel, DimensionConstants.NavBarInfoProjectPanelWidth);
        addEast(sessionPanel, DimensionConstants.NavBarInfoSessionPanelWidth);
        add(centerMessagePanel);
        showSignedOut();
        showNoProjectLoaded();
        showVersion();
    }

    void showCenterText(String text) {
        centerMessagePanel.setText(text);
    }

    void showProjectText(String text) {
        leftMessagePanel.setText(text);
    }

    void setSessionText(String statusText) {
        sessionPanel.setText(statusText);
    }

    public void showNoProjectLoaded() {
        showProjectText("No project loaded");
    }

    public void showSignedInAs(String username) {
        setSessionText("signed in(" + username + ")");
    }

    public void showSignedOut() {
        setSessionText("<signed out>");
    }

    public void showVersion() {
        String version = "";
        version += "Version: " + AppConstants.VERSION;
        version += " build(" + AppConstants.BUILD_NUMBER + ")";
        version += " " + AppConstants.TIMESTAMP;
        showCenterText(version);
    }

    public static InfoPanel getInstance() {
        if (instance == null)
            instance = new InfoPanel();
        return instance;
    }

    MiddleLeftPanel leftMessagePanel;
    MiddleCenterPanel centerMessagePanel;
    MiddleRightPanel sessionPanel;
    static InfoPanel instance;
}
