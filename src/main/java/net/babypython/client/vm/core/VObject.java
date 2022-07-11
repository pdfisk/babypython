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

import com.google.gwt.user.client.ui.Widget;
import net.babypython.client.vm.constants.PythonMethodConstants;
import net.babypython.client.vm.interfaces.IMethod;
import net.babypython.client.vm.classes.special.objects.NilObject;
import net.babypython.client.vm.vm.context.Context;

import java.util.ArrayList;
import java.util.HashMap;

public class VObject {

    public static VObject getNilObject() {
        return NilObject.getInstance();
    }

    public VObject(VClass VClass) {
        this.vClass = VClass;
    }

    public Object getInstanceVariable(String name) {
        if (!instanceVariablesMap.containsKey(name))
            return NilObject.getInstance();
        return instanceVariablesMap.get(name);
    }

    public VClass getVClass() {
        return this.vClass;
    }

    public Widget getWidget() {
        return null;
    }

    public boolean isClass() {
        return false;
    }

    public boolean isWidget() {
        return false;
    }

    public Object performMethod(String selector, ArrayList<Object> args) {
        return getClass().getSimpleName() + " perform: " + selector + " " + args.size();
    }

    public Object perform(String selector) {
        return perform(selector, false);
    }

    public Object perform(String selector, boolean isSuper) {
        IMethod method = getVClass().lookupMethod(selector, isSuper);
        return NilObject.getInstance();
    }

    public Object perform(Context frame, Object receiverObject, String selector, Object[] args) {
        switch (selector) {
            case PythonMethodConstants.MessageClass:
                return this.getVClass();
            default:
                IMethod method = getVClass().lookupMethod(selector, false);
                return method.perform(frame, receiverObject, selector, args);
        }
    }

    public Object performSuper(String selector) {
        return perform(selector, true);
    }

    public void setInstanceVariable(String name, Object value) {
        instanceVariablesMap.put(name, value);
    }

    public void setVClass(VClass vClass) {
        this.vClass = vClass;
    }

    public String toString() {
        String clsName = getVClass().getClassName();
        String sizeStr = "(" + getVClass().instVarNames.size() + ")";
        char firstCh = Character.toLowerCase(clsName.charAt(0));
        if (firstCh == 'a' || firstCh == 'e' || firstCh == 'i'
                || firstCh == 'o' || firstCh == 'u')
            return "an " + clsName + sizeStr;
        else
            return "a " + clsName + sizeStr;
    }

    protected HashMap<String, Object> instanceVariablesMap = new HashMap<>();
    private VClass vClass;
}
