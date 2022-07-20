package net.babypython.client.ui.viewport.widgets.navbar;

import net.babypython.client.ui.gwt.widgets.GwtHorizontalPanel;

public class InfoPanel extends GwtHorizontalPanel {

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
