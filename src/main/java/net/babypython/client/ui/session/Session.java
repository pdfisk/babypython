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
package net.babypython.client.ui.session;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import net.babypython.client.constants.AppConstants;
import net.babypython.client.ui.constants.UrlConstants;
import net.babypython.client.ui.interfaces.IRequestHandler;
import net.babypython.client.ui.util.Logable;
import net.babypython.client.ui.viewport.widgets.navbar.InfoPanel;
import net.babypython.client.ui.windows.session.login.LoginWindow;
import net.babypython.client.ui.windows.session.register.RegisterWindow;
import net.babypython.client.vm.containers.records.UserRecord;
import net.babypython.client.vm.events.error.ErrorEventBus;
import net.babypython.client.vm.events.session.SessionEventBus;
import net.babypython.client.vm.util.JsonUtil;
import net.babypython.client.vm.util.requests.RequestUtil;

import java.sql.Timestamp;

public class Session extends Logable {

    public static UserRecord getCurrentUser() {
        return getInstance().currentUser;
    }

    public static SessionState getSessionState() {
        return getInstance().sessionState;
    }

    public static void setLoggedIn(UserRecord user) {
        getInstance().setSessionState(SessionState.LoggedIn, user);
    }

    public static void setLoggedInAsAdmin(UserRecord user) {
        getInstance().setSessionState(SessionState.LoggedInAsAdmin, user);
    }

    public static void setLoggedOut() {
        getInstance().setSessionState(SessionState.LoggedOut, null);
    }

    void setSessionState(SessionState sessionState, UserRecord user) {
        this.sessionState = sessionState;
        currentUser = user;
        switch (sessionState) {
            case LoggedIn:
            case LoggedInAsAdmin:
                InfoPanel.getInstance().showSignedInAs(currentUser.username);
                break;
            default:
                InfoPanel.getInstance().showSignedOut();
                break;
        }
        SessionEventBus.fireSessionStateEvent(sessionState);
    }

    void showInvalidLogin() {
        ErrorEventBus.fireInfoEvent("Invalid name or password");
    }

    void showInvalidRegistration(String message) {
        ErrorEventBus.fireInfoEvent(message);
    }

    void showServerLoginError() {
        ErrorEventBus.fireInfoEvent("Server error on login");
    }

    void showServerRegisterError() {
        ErrorEventBus.fireInfoEvent("Server error on register");
    }

    public void tryLogin(String username, String password) {
        String requestData = "";
        requestData += "username=" + username + "&";
        requestData += "password=" + password;
        String serverUrl = AppConstants.IS_DEBUG ? UrlConstants.LocalUser : UrlConstants.HerokuUser;
        RequestUtil.getUrlText(serverUrl, new IRequestHandler() {
            @Override
            public void handleCallback(String jsonStr) {
                handleLoginJsonStr(jsonStr);
            }
        }, requestData);
    }

    void handleLoginJsonStr(String jsonStr) {
        if (jsonStr == null) {
            showInvalidLogin();
            return;
        }
        try {
            JSONValue jsonValue = JsonUtil.decode(jsonStr);
            JSONObject jsonObject = jsonValue.isObject();
            currentUser = new UserRecord();
            currentUser.id = JsonUtil.getIntField(jsonObject, "id");
            currentUser.username = JsonUtil.getStringField(jsonObject, "username");
            currentUser.level = JsonUtil.getIntField(jsonObject, "level");
            currentUser.created_at = Timestamp.valueOf(JsonUtil.getStringField(jsonObject, "created_at"));
            currentUser.updated_at = Timestamp.valueOf(JsonUtil.getStringField(jsonObject, "updated_at"));
            LoginWindow.getInstance().hide();
            setSessionState(SessionState.LoggedIn, currentUser);
        } catch (Exception e) {
            showServerLoginError();
        }
    }

    public void tryRegister(String username, String password) {
        String requestData = "";
        requestData += "username=" + username + "&";
        requestData += "password=" + password;
        String serverUrl = AppConstants.IS_DEBUG ? UrlConstants.LocalRegister : UrlConstants.HerokuRegister;
        RequestUtil.getUrlText(serverUrl, new IRequestHandler() {
            @Override
            public void handleCallback(String jsonStr) {
                handleRegisterJsonStr(jsonStr);
            }
        }, requestData);
    }

    void handleRegisterJsonStr(String jsonStr) {
        if (jsonStr == null) {
            showInvalidLogin();
            return;
        }
        try {
            JSONValue jsonValue = JsonUtil.decode(jsonStr);
            JSONObject jsonObject = jsonValue.isObject();
            currentUser = new UserRecord();
            currentUser.id = JsonUtil.getIntField(jsonObject, "id");
            currentUser.username = JsonUtil.getStringField(jsonObject, "username");
            currentUser.level = JsonUtil.getIntField(jsonObject, "level");
            currentUser.created_at = Timestamp.valueOf(JsonUtil.getStringField(jsonObject, "created_at"));
            currentUser.updated_at = Timestamp.valueOf(JsonUtil.getStringField(jsonObject, "updated_at"));
            RegisterWindow.getInstance().hide();
            setSessionState(SessionState.LoggedIn, currentUser);
        } catch (Exception e) {
            showServerRegisterError();
        }
    }

    Session() {
        currentUser = null;
        sessionState = SessionState.LoggedOut;
    }

    public static Session getInstance() {
        if (instance == null)
            instance = new Session();
        return instance;
    }

    UserRecord currentUser;
    SessionState sessionState;
    static Session instance;
}
