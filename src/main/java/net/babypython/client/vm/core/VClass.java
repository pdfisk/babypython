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
package net.babypython.client.vm.core;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.TreeItem;
import net.babypython.client.vm.classes.methods.MethodBasicNew;
import net.babypython.client.vm.classes.methods.MethodClass;
import net.babypython.client.vm.classes.methods.MethodDebug;
import net.babypython.client.vm.classes.methods.MethodPerform;
import net.babypython.client.vm.constants.JsonVClassConstants;
import net.babypython.client.vm.constants.PythonMethodConstants;
import net.babypython.client.vm.containers.lists.StringList;
import net.babypython.client.vm.interfaces.IMethod;
import net.babypython.client.vm.vm.context.Context;
import net.babypython.client.vm.workspace.WorkspaceBuilder;
import net.babypython.client.vm.workspace.Workspace;
import net.babypython.client.vm.interfaces.IClass;
import net.babypython.client.vm.util.streams.StringStream;
import net.babypython.client.vm.vm.context.CompiledMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class VClass extends VObject implements IClass {

    public static VClass createFromJsonObject(JSONObject jsonObject) {
        String className = jsonObject.get(JsonVClassConstants._className).isString().stringValue();
        VClass vClass = WorkspaceBuilder.getVClass(className);
        if (vClass == null)
            vClass = new VClass(className);
        vClass.fromJsonObject(jsonObject);
        Workspace.getInstance().putGlobal(vClass.className, vClass);
        return vClass;
    }

    public VClass(String name) {
        this(name, "");
    }

    public VClass(String className, String instVarNames) {
        super(null);
        this.subClasses = new ArrayList<>();
        this.className = className;
        this.isBuiltin = false;
        this.methods = new HashMap<>();
        this.superClass = null;
        this.setInstanceVariables(instVarNames);
        this.setClassVariables("");
        this.setPoolDictionaries("");
        this.setCategory("All-Objects");
    }

    protected VClass(String name, VClass superClass, String instVarNames) {
        this(name, instVarNames);
        this.superClass = superClass;
    }

    public CompiledMethod addMethod(CompiledMethod method) {
        this.methods.put(method.getSelector(), method);
        return method;
    }

    public VClass addSubclass(String name, String instVarNames) {
        VClass subClass = (VClass) findSubclass(name);
        if (subClass == null) {
            subClass = new VClass(name, this, instVarNames);
            addSubclass(subClass);
        } else
            subClass.setInstanceVariables(instVarNames);
        return subClass;
    }

    public VClass addSubclass(VClass aClass) {
        VClass oldSubclass = (VClass) findSubclass(aClass.getClassName());
        if (oldSubclass != null)
            removeSubclass(oldSubclass);
        this.subClasses.add(aClass);
        aClass.setSuperClass(this);
        return aClass;
    }

    public void adjustSuperClasses() {
        for (IClass subclass : subClasses) {
            if (subclass instanceof VClass) {
                VClass vClass = (VClass) subclass;
                vClass.setSuperClass(this);
                vClass.adjustSuperClasses();
            }
        }
    }

    public JSONObject asJsonObject() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonSubclasses = new JSONArray();
        JSONObject jsonMethods = new JSONObject();
        JSONObject jsonMetaClassObject = getVClass().asJsonObject();
        for (IClass subClass : subClasses)
            jsonSubclasses.set(jsonSubclasses.size(), subClass.asJsonObject());
        for (String methodName : methods.keySet())
            jsonMethods.put(methodName, methods.get(methodName).asJsonObject());
        jsonObject.put(JsonVClassConstants._category, new JSONString(getCategory()));
        jsonObject.put(JsonVClassConstants._className, new JSONString(getClassName()));
        jsonObject.put(JsonVClassConstants._instanceVariables, new JSONString(getInstanceVariables()));
        jsonObject.put(JsonVClassConstants._classVariables, new JSONString(getClassVariables()));
        jsonObject.put(JsonVClassConstants._poolDictionaries, new JSONString(getPoolDictionaries()));
        jsonObject.put(JsonVClassConstants._isBuiltin, JSONBoolean.getInstance(getIsBuiltin()));
        jsonObject.put(JsonVClassConstants._subClasses, jsonSubclasses);
        jsonObject.put(JsonVClassConstants._methods, jsonMethods);
        jsonObject.put(JsonVClassConstants._metaClassObject, jsonMetaClassObject);
        return jsonObject;
    }

    public void fromJsonObject(JSONObject jsonClassObject) {
        setCategory(jsonClassObject.get(JsonVClassConstants._category).isString().stringValue());
        setClassName(jsonClassObject.get(JsonVClassConstants._className).isString().stringValue());
        setInstanceVariables(jsonClassObject.get(JsonVClassConstants._instanceVariables).isString().stringValue());
        setClassVariables(jsonClassObject.get(JsonVClassConstants._classVariables).isString().stringValue());
        setPoolDictionaries(jsonClassObject.get(JsonVClassConstants._poolDictionaries).isString().stringValue());
        setIsBuiltin(jsonClassObject.get(JsonVClassConstants._isBuiltin).isBoolean().booleanValue());
        if (jsonClassObject.containsKey(JsonVClassConstants._subClasses)) {
            JSONArray jsonSubclassesArray = jsonClassObject.get(JsonVClassConstants._subClasses).isArray();
            for (int i = 0; i < jsonSubclassesArray.size(); i++) {
                JSONObject jsonSubclassObject = jsonSubclassesArray.get(i).isObject();
                VClass subclass = createFromJsonObject(jsonSubclassObject);
                if (subclass != null) {
                    addSubclass(subclass);
                    subclass.setSuperClass(this);
                }
            }
        }
        if (jsonClassObject.containsKey(JsonVClassConstants._methods)) {
            JSONObject jsonMethods = jsonClassObject.get(JsonVClassConstants._methods).isObject();
            for (String methodName : jsonMethods.keySet()) {
                CompiledMethod compiledMethod = new CompiledMethod(this);
                JSONObject jsonMethodObject = jsonMethods.get(methodName).isObject();
                compiledMethod.fromJsonObject(jsonMethodObject);
                addMethod(compiledMethod);
            }
        }
        if (jsonClassObject.containsKey(JsonVClassConstants._metaClassObject)) {
            JSONObject jsonMetaClassObject = jsonClassObject.get(JsonVClassConstants._metaClassObject).isObject();
            getVClass().fromJsonObject(jsonMetaClassObject);
        }
    }

    public TreeItem asTreeItem() {
        TreeItem treeItem = new TreeItem();
        treeItem.setText(getClassName());
        Collections.sort(this.subClasses);
        for (IClass subClass : this.subClasses) {
            if (!(subClass instanceof VClass))
                continue;
            treeItem.addItem(((VClass) subClass).asTreeItem());
        }
        return treeItem;
    }

    @Override
    public int compareTo(IClass o) {
        return getClassName().compareTo(o.getClassName());
    }

    public Object createInstance() {
        Object instance;
        if (getSuperClass() instanceof VClass)
            instance = ((VClass) getSuperClass()).createInstance();
        else
            instance = new VObject(this);
        if (instance instanceof VObject)
            ((VObject) instance).setVClass(this);
        return instance;
    }

    public void delete(Workspace objectSpace) {
        objectSpace.deleteGlobal(getClassName());
        for (IClass subClass : subClasses)
            ((VClass) subClass).delete(objectSpace);
        if (getSuperClass() instanceof VClass)
            ((VClass) getSuperClass()).removeSubclass(this);
    }

    private IClass findSubclass(String name) {
        for (IClass subClass : this.subClasses)
            if (subClass.getClassName().equals(name))
                return subClass;
        return null;
    }

    public String getCategory() {
        return category;
    }

    public String getClassDefinition() {
        StringStream sw = new StringStream();
        if (superClass == null || !(superClass instanceof VClass))
            sw.pr("nil");
        else
            sw.pr(((VClass) superClass).getClassName());
        sw.space();
        sw.pr("subclass: #");
        sw.prn(getClassName());
        sw.space4();
        sw.prn("instanceVariableNames: '" + instVarNames.asStringWithSpaces() + "'");
        sw.space4();
        sw.prn("classVariableNames: ''");
        sw.space4();
        sw.prn("poolDictionaries: ''");
        sw.space4();
        sw.prn("category: 'All-Objects'");
        return sw.toString();
    }

    public ArrayList<String> getClassStringList() {
        ArrayList<String> list = new ArrayList<>();
        getClassStringList(list, 0);
        return list;
    }

    public void getClassStringList(ArrayList<String> list, int depth) {
        String pad = "";
        for (int i = 0; i < depth; i++)
            pad += "...";
        list.add(pad + this.className);
        if (this.subClasses.size() > 0) {
            Collections.sort(this.subClasses);
            for (IClass cls : this.subClasses)
                cls.getClassStringList(list, depth + 1);
        }
    }

    public ArrayList<String> getInstVarNames() {
        return this.instVarNames;
    }

    public String getClassVariables() {
        return classVariables;
    }

    public String getInstanceVariables() {
        return instanceVariables;
    }

    public String getPoolDictionaries() {
        return poolDictionaries;
    }

    public boolean getIsBuiltin() {
        return isBuiltin;
    }

    public String getClassName() {
        return this.className;
    }

    public ArrayList<IClass> getSubClasses() {
        return this.subClasses;
    }

    public boolean hasInstanceVariable(String name) {
        return instVarNames.contains(name);
    }

    public boolean isClass() {
        return true;
    }
    
    public IMethod lookupMethod(String selector, boolean isSuper) {
        return findMethod(selector, isSuper);
    }

    @Override
    public void register(Workspace objectSpace) {
        String name = getClassName();
        objectSpace.putGlobal(name, this);
        for (IClass subClass : subClasses) {
            ((VClass) subClass).setSuperClass(this);
            subClass.register(objectSpace);
        }
    }

    public IMethod removeMethod(String selector) {
        return this.methods.remove(selector);
    }

    public void removeSubclass(VClass subClass) {
        if (this.subClasses.contains(subClass))
            this.subClasses.remove(subClass);
    }

    public ArrayList<String> selectors() {
        ArrayList<String> methodSelectors = new ArrayList<String>();
        for (String selector : this.methods.keySet())
            methodSelectors.add(selector);
        Collections.sort(methodSelectors);
        return methodSelectors;
    }

    public void setClassVariables(String classVarsString) {
        this.classVariables = classVarsString;
    }

    public void setCategory(String categoryString) {
        category = categoryString;
    }

    public void setInstanceVariables(String instanceVarsString) {
        this.instanceVariables = instanceVarsString;
        this.instVarNames = new StringList();
        String[] names = (instanceVarsString + " ").split(" ");
        for (String name : names)
            if (name.length() > 0)
                this.instVarNames.add(name);
    }

    public void setIsBuiltin() {
        setIsBuiltin(true);
    }

    public void setIsBuiltin(boolean value) {
        isBuiltin = value;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setPoolDictionaries(String poolDictsString) {
        this.poolDictionaries = poolDictsString;
    }

    public String toString() {
        return this.className;
    }
    
    public IMethod findMethod(String selector, boolean isSuper) {
        VClass testClass = this;
        testClass = findMethodClass(selector, testClass);
        if (testClass != null && isSuper)
            testClass = findMethodClass(selector, testClass.getSuperClassOrNull());
        if (testClass != null)
            return testClass.getMethod(selector);
        switch (selector) {
            case PythonMethodConstants.MessageBasicNew:
                return MethodBasicNew.getInstance();
            case PythonMethodConstants.MessageClass:
                return MethodClass.getInstance();
            case PythonMethodConstants.MessageDebug:
                return MethodDebug.getInstance();
            case PythonMethodConstants.MessagePerform:
                return MethodPerform.getInstance();
            default:
                return null;
        }
    }

    protected VClass findMethodClass(String selector, VClass testClass) {
        while (testClass != null && !testClass.hasMethod(selector))
            testClass = testClass.getSuperClassOrNull();
        return testClass;
    }

    public IMethod getMethod(String selector) {
        return this.methods.get(selector);
    }

    protected void getMethodBuiltins(StringList methodNames) {
    }

    public StringList getMethodNames() {
        StringList methodNames = new StringList();
        for (String methodName : methods.keySet())
            methodNames.add(methodName);
        getMethodBuiltins(methodNames);
        methodNames.sort();
        return methodNames;
    }

    public String getMethodSource(String methodName) {
        if (isBuiltinMethod(methodName))
            return getBuiltinMethodSource(methodName);
        IMethod method = getMethod(methodName);
        if (method == null)
            return "";
        return method.getSource();
    }

    protected String getBuiltinMethodSource(String methodName) {
        String src = getExpandedMethodName(methodName);
        src += "\n\n";
        src += "\t\"This is a builtin method\"\n";
        return src;
    }

    String getExpandedMethodName(String methodName) {
        if (methodName.indexOf(":") < 0)
            return methodName;
        String[] parts = methodName.split("\\\\:");
        String expandedName = "";
        for (int i = 0; i < parts.length; i++)
            expandedName += parts[i] + " arg" + i;
        return expandedName;
    }

    public VObject getSuperClass() {
        return this.superClass;
    }

    public VClass getSuperClassOrNull() {
        if (getSuperClass() instanceof VClass)
            return (VClass) getSuperClass();
        return null;
    }

    public boolean hasMethod(String selector) {
        return methods.containsKey(selector);
    }

    public boolean isBuiltinMethod(String selector) {
        switch (selector) {
            case PythonMethodConstants.MessageSuperclass:
                return true;
            default:
                return false;
        }
    }

    @Override
    public Object perform(Context blockContext, Object receiverObject, String selector, Object[] args) {
        switch (selector) {
            case PythonMethodConstants.MessageSuperclass:
                return this.getSuperClass();
            default:
                return super.perform(blockContext, receiverObject, selector, args);
        }
    }

    public void setSuperClass(VObject anObject) {
        this.superClass = anObject;
    }

    protected String category;
    protected String className;
    protected String classVariables;
    protected StringList instVarNames;
    protected String instanceVariables;
    protected boolean isBuiltin;
    protected MethodPerform methodPerform = MethodPerform.getInstance();
    protected HashMap<String, CompiledMethod> methods;
    protected String poolDictionaries;
    protected ArrayList<IClass> subClasses;
    protected VObject superClass;
}
