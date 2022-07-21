/**
 * MIT License
 *
 * Copyright (c) 2022 Peter Fisk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.babypython.client.ui.windows.session.login;

import com.google.gwt.user.client.ui.Widget;
import net.babypython.client.ui.constants.CommonWindowConstants;
import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.ui.gwt.window.widgets.GwtWindowButtonBar;
import net.babypython.client.ui.session.Session;
import net.babypython.client.ui.session.SessionState;
import net.babypython.client.ui.windows.session.login.widgets.ButtonBar;
import net.babypython.client.ui.windows.session.login.widgets.LoginForm;

public class LoginWindow extends GwtWindow {

    public LoginWindow() {
        super();
    }

    @Override
    protected GwtWindowButtonBar defaultButtonBar() {
        return new ButtonBar(this);
    }

    @Override
    protected Widget defaultContent() {
        return loginForm = new LoginForm();
    }

    @Override
    protected int defaultHeight() {
        return CommonWindowConstants.LoginWindowHeight;
    }

    @Override
    public boolean defaultHideOnClose() {
        return true;
    }

    @Override
    protected boolean defaultModal() {
        return true;
    }

    @Override
    public boolean defaultShowMaximizeButton() {
        return false;
    }

    @Override
    public boolean defaultShowMinimizeButton() {
        return false;
    }

    @Override
    protected String defaultTitle() {
        return CommonWindowConstants.LoginWindowHeader;
    }

    @Override
    protected int defaultWidth() {
        return CommonWindowConstants.LoginWindowWidth;
    }

    @Override
    public void onButtonClicked(String tag) {
        switch (tag) {
            case "cancel":
                onCancel();
                break;
            case "login":
                onLogin();
                break;
            case "reset":
                onReset();
                break;
            default:
                super.onButtonClicked(tag);
                break;
        }
    }

    void onCancel() {
        hideOrClose();
    }

    void onLogin() {
        String name = loginForm.getName();
        String password = loginForm.getPassword();
        Session.getInstance().tryLogin(name, password);
    }

    void onReset() {
        loginForm.reset();
    }

    @Override
    public void onSessionStateChanged(SessionState sessionState) {
        switch (sessionState) {
            case LoggedIn:
            case LoggedInAsAdmin:
                hideOrClose();
        }
    }

    public static LoginWindow getInstance() {
        if (instance == null)
            instance = new LoginWindow();
        return instance;
    }

    LoginForm loginForm;
    static LoginWindow instance;
}
