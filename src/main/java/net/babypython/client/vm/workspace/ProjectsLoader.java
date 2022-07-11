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
package net.babypython.client.vm.workspace;

import com.google.gwt.json.client.JSONObject;
import net.babypython.client.ui.interfaces.IHandleStringListData;
import net.babypython.client.ui.interfaces.IHandleTextValue;
import net.babypython.client.ui.interfaces.IRequestHandler;
import net.babypython.client.vm.constants.FileConstants;
import net.babypython.client.vm.containers.lists.StringList;
import net.babypython.client.ui.util.FileUtil;
import net.babypython.client.vm.util.JsonUtil;
import net.babypython.client.ui.util.Logable;
import net.babypython.client.vm.util.requests.RequestUtil;

public class ProjectsLoader extends Logable {

    ProjectsLoader() {
        super();
        basePath = FileConstants.PythonFolder + "/" + FileConstants.ScriptsFolder + "/" + FileConstants.ChickletFolder + "/";
    }

    public void getFileText(String fileName, IHandleTextValue handleTextValue) {
        String path = basePath + fileName + ".py";
        RequestUtil.getUrlText(path, new IRequestHandler() {
            @Override
            public void handleCallback(String value) {
                handleTextValue.handleTextValue(value);
            }
        });
    }


    public void loadProjects(IHandleStringListData stringListDataHandler) {
        RequestUtil.getUrlText(FileConstants.PythonIndexPath, new IRequestHandler() {
            @Override
            public void handleCallback(String jsonStr) {
                JSONObject jsonObject = JsonUtil.decode(jsonStr);
                StringList fileNames = FileUtil.getChickletFileNames(jsonObject);
                stringListDataHandler.handleStringListData(fileNames);
            }
        });
    }

    public static ProjectsLoader getInstance() {
        if (instance == null)
            instance = new ProjectsLoader();
        return instance;
    }

    String basePath;
    static ProjectsLoader instance;
}
