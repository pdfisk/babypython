package net.babypython.client.ui.viewport.widgets.navbar;

import com.google.gwt.user.client.ui.MenuBar;
import net.babypython.client.ui.session.SessionState;
import net.babypython.client.vm.interfaces.ISessionState;

public class SessionMenuBar extends MenuBar implements ISessionState {

    public SessionMenuBar() {
        super();
    }

    public SessionMenuBar(boolean vertical) {
        super(vertical);
    }

    @Override
    public void onSessionStateChanged(SessionState sessionState) {
    }

}
