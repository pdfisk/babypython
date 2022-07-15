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

import com.google.gwt.json.client.JSONObject;
import net.babypython.client.vm.constants.ProjectFields;
import net.babypython.client.vm.util.JsonUtil;

public class ProjectRecord {

    public ProjectRecord(JSONObject jsonObject) {
        this(JsonUtil.getInt(jsonObject.get(ProjectFields.ProjectId)),
                JsonUtil.getString(jsonObject.get(ProjectFields.ProjectName)),
                JsonUtil.getString(jsonObject.get(ProjectFields.ProjectDescription)),
                JsonUtil.getString(jsonObject.get(ProjectFields.ProjectOwnerName)),
                JsonUtil.getString(jsonObject.get(ProjectFields.ProjectCode)),
                JsonUtil.getString(jsonObject.get(ProjectFields.ProjectCreatedAt)),
                JsonUtil.getString(jsonObject.get(ProjectFields.ProjectUpdatedAt)));
    }

    public ProjectRecord(int id, String name, String description,
                         String ownerName, String code, String createAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownerName = ownerName;
        this.code = code;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
    }

    public int id;
    public String name;
    public String description;
    public String ownerName;
    public String code;
    public String createAt;
    public String updatedAt;
}
