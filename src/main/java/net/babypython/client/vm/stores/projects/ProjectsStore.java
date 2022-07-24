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
package net.babypython.client.vm.stores.projects;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import net.babypython.client.constants.AppConstants;
import net.babypython.client.ui.constants.UrlConstants;
import net.babypython.client.ui.interfaces.IHandleStringListData;
import net.babypython.client.ui.interfaces.IHandleTextValue;
import net.babypython.client.ui.interfaces.IRequestHandler;
import net.babypython.client.ui.session.Session;
import net.babypython.client.ui.util.Logable;
import net.babypython.client.vm.containers.dictionaries.ObjectDictionary;
import net.babypython.client.vm.containers.dictionaries.ProjectsDictionary;
import net.babypython.client.vm.containers.dictionaries.RequestParamsDictionary;
import net.babypython.client.vm.containers.lists.StringList;
import net.babypython.client.vm.util.JsonUtil;
import net.babypython.client.vm.util.requests.RequestUtil;

public class ProjectsStore extends Logable {

    ProjectsStore() {
        super();
        sharedProjectsDictionary = new ProjectsDictionary();
        userProjectsDictionary = new ProjectsDictionary();
    }

    public void getSharedProjectCode(String name, IHandleTextValue handler) {
        if (!sharedProjectsDictionary.containsKey(name))
            return;
        String code = sharedProjectsDictionary.get(name).code;
        handler.handleTextValue(code);
    }

    public void getUserProjectCode(String name, IHandleTextValue handler) {
        if (!userProjectsDictionary.containsKey(name))
            return;
        String code = userProjectsDictionary.get(name).code;
        handler.handleTextValue(code);
    }

    public void loadSharedProjects(IHandleStringListData stringListDataHandler) {
        sharedProjectsDictionary.clear();
        String serverUrl = AppConstants.IS_DEBUG ? UrlConstants.LocalSharedProjects : UrlConstants.HerokuSharedProjects;
        RequestUtil.sendGetRequest(serverUrl, new IRequestHandler() {
            @Override
            public void handleCallback(String jsonStr) {
                JSONArray jsonArray = JsonUtil.decode(jsonStr).isArray();
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.size(); i++)
                        loadSharedProject(jsonArray.get(i).isObject());
                }
                StringList stringList = new StringList();
                for (String name : sharedProjectsDictionary.keySet())
                    stringList.add(name);
                stringList.sort();
                stringListDataHandler.handleStringListData(stringList);
            }
        });
    }

    public void loadUserProjects(IHandleStringListData stringListDataHandler) {
        userProjectsDictionary.clear();
        String serverUrl = AppConstants.IS_DEBUG ? UrlConstants.LocalUserProjects : UrlConstants.HerokuUserProjects;
        String username = Session.getCurrentUser().username;
        serverUrl = serverUrl + "/" + username;
        RequestUtil.sendGetRequest(serverUrl, new IRequestHandler() {
            @Override
            public void handleCallback(String jsonStr) {
                JSONArray jsonArray = JsonUtil.decode(jsonStr).isArray();
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.size(); i++)
                        loadUserProject(jsonArray.get(i).isObject());
                }
                StringList stringList = new StringList();
                for (String name : userProjectsDictionary.keySet())
                    stringList.add(name);
                stringList.sort();
                stringListDataHandler.handleStringListData(stringList);
            }
        });
    }

    public void updateUserProject(RequestParamsDictionary requestParams) {
        String serverUrl = AppConstants.IS_DEBUG ? UrlConstants.LocalUserProject : UrlConstants.HerokuUserProject;
        String username = Session.getCurrentUser().username;
        serverUrl = serverUrl + "/" + username;
        RequestUtil.sendPostRequest(serverUrl, new IRequestHandler() {
            @Override
            public void handleCallback(String jsonStr) {
                info("updateUserProject handleCallback", jsonStr);
            }
        }, requestParams);
    }

    void loadSharedProject(JSONObject jsonObject) {
        if (jsonObject == null)
            return;
        ProjectRecord projectRecord = new ProjectRecord(jsonObject);
        sharedProjectsDictionary.put(projectRecord.name, projectRecord);
    }

    void loadUserProject(JSONObject jsonObject) {
        if (jsonObject == null)
            return;
        ProjectRecord projectRecord = new ProjectRecord(jsonObject);
        userProjectsDictionary.put(projectRecord.name, projectRecord);
    }

    public static ProjectsStore getInstance() {
        if (instance == null)
            instance = new ProjectsStore();
        return instance;
    }

    ProjectsDictionary sharedProjectsDictionary;
    ProjectsDictionary userProjectsDictionary;
    static ProjectsStore instance;
}
