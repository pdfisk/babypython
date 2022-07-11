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
package net.babypython.client.ui.session;

import net.babypython.client.vm.containers.records.UserRecord;
import net.babypython.client.vm.events.error.ErrorEventBus;
import net.babypython.client.vm.events.session.SessionEventBus;
import net.babypython.client.ui.util.Logable;

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

    public void tryLogin(String name, String password) {
//        ServiceApi.getInstance().getUser(name, password, new AsyncCallback<TimedUser>() {
//            @Override
//            public void onFailure(Throwable caught) {
//                showServerLoginError();
//            }
//
//            @Override
//            public void onSuccess(TimedUser timedUser) {
//                timedUser.timingRecord.setReplyReceived();
//                ServiceMonitor.recordTiming(timedUser);
//                User user = timedUser.user;
//                if (user == null)
//                    showInvalidLogin();
//                else if (user.isAdmin())
//                    setLoggedInAsAdmin(user);
//                else
//                    setLoggedIn(user);
//            }
//        });
    }

    public void tryRegister(String name, String email, String password) {
//        ServiceApi.getInstance().registerUser(name, email, password, new AsyncCallback<TimedUser>() {
//            @Override
//            public void onFailure(Throwable caught) {
//                showServerLoginError();
//            }
//
//            @Override
//            public void onSuccess(TimedUser timedUser) {
//                timedUser.timingRecord.setReplyReceived();
//                ServiceMonitor.recordTiming(timedUser);
//                User user = timedUser.user;
//                if (user == null) {
//                    String message = timedUser.timingRecord.message;
//                    if (message == null)
//                        message = "registration error";
//                    showInvalidRegistration(message);
//                } else if (user.isAdmin())
//                    setLoggedInAsAdmin(user);
//                else
//                    setLoggedIn(user);
//            }
//        });
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
