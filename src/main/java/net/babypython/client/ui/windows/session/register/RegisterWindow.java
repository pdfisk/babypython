/**
 * MIT License
 * <p>
 * Copyright (c) 2022 Peter Fisk
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.babypython.client.ui.windows.session.register;

import com.google.gwt.user.client.ui.Widget;
import net.babypython.client.ui.constants.CommonWindowConstants;
import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.ui.gwt.window.widgets.GwtWindowButtonBar;
import net.babypython.client.ui.session.Session;
import net.babypython.client.ui.session.SessionState;
import net.babypython.client.ui.util.Logable;
import net.babypython.client.ui.windows.session.register.widgets.ButtonBar;
import net.babypython.client.ui.windows.session.register.widgets.RegisterForm;
import net.babypython.client.vm.events.error.ErrorEventBus;

public class RegisterWindow extends GwtWindow {

    @Override
    protected GwtWindowButtonBar defaultButtonBar() {
        return new ButtonBar(this);
    }

    @Override
    protected Widget defaultContent() {
        return registerForm = new RegisterForm();
    }

    @Override
    protected int defaultHeight() {
        return CommonWindowConstants.RegisterWindowHeight;
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
        return CommonWindowConstants.RegisterWindowHeader;
    }

    @Override
    protected int defaultWidth() {
        return CommonWindowConstants.RegisterWindowWidth;
    }

    @Override
    public void onButtonClicked(String tag) {
        switch (tag) {
            case "cancel":
                onCancel();
                break;
            case "register":
                onRegister();
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

    void onRegister() {
        String username = registerForm.getName();
        String password = registerForm.getPassword();
        String errorMessage = registerForm.validateForm();
        if (errorMessage != null) {
            ErrorEventBus.fireInfoEvent(errorMessage);
            Logable.info("onRegister error", username, password, errorMessage);
            return;
        }
        Session.getInstance().tryRegister(username,  password);
    }

    void onReset() {
        registerForm.reset();
    }

    @Override
    public void onSessionStateChanged(SessionState sessionState) {
        switch (sessionState) {
            case LoggedIn:
            case LoggedInAsAdmin:
                hideOrClose();
        }
    }

    public static RegisterWindow getInstance() {
        if (instance == null)
            instance = new RegisterWindow();
        return instance;
    }

    RegisterForm registerForm;
    static RegisterWindow instance;
}
