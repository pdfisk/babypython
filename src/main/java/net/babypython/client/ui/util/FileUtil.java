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
package net.babypython.client.ui.util;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import net.babypython.client.vm.constants.FileConstants;
import net.babypython.client.vm.containers.lists.StringList;

public class FileUtil extends Logable {

    public static StringList getChickletFileNames(JSONObject jsonObject) {
        StringList fileNames = new StringList();
        if (!jsonObject.containsKey(FileConstants.ScriptsFolder))
            return fileNames;
        JSONArray scriptsArray = jsonObject.get(FileConstants.ScriptsFolder).isArray();
        if (scriptsArray == null || scriptsArray.size() == 0)
            return fileNames;
        JSONObject chickObject = scriptsArray.get(0).isObject();
        if (!chickObject.containsKey(FileConstants.ChickletFolder))
            return fileNames;
        JSONArray chickletArray = chickObject.get(FileConstants.ChickletFolder).isArray();
        for (int i = 0; i < chickletArray.size(); i++) {
            JSONString jsonString = chickletArray.get(i).isString();
            if (jsonString == null)
                continue;
            fileNames.add(jsonString.stringValue());
        }
        return fileNames;
    }

}
