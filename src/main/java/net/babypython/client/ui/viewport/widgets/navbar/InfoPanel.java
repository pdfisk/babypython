package net.babypython.client.ui.viewport.widgets.navbar;

import net.babypython.client.ui.gwt.widgets.GwtDockPanel;

public class InfoPanel extends GwtDockPanel {

    InfoPanel() {
        super();
        initialize();
        getElement().getStyle().setBackgroundColor("yellow");
    }

    void initialize() {

    }

    public static InfoPanel getInstance() {
        if (instance == null)
            instance = new InfoPanel();
        return instance;
    }

    static InfoPanel instance;
}
