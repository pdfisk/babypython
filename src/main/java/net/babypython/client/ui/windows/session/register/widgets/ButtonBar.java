package net.babypython.client.ui.windows.session.register.widgets;

import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.ui.gwt.window.widgets.GwtWindowButtonBar;
import net.babypython.client.vm.containers.lists.StringList;

public class ButtonBar extends GwtWindowButtonBar {

    public ButtonBar(GwtWindow window) {
        super(window);
    }

    @Override
    protected StringList defaultButtons() {
        StringList buttons = super.defaultButtons();
        buttons.add("Register");
        buttons.add("Reset");
        buttons.add("Cancel");
        return buttons;
    }

}