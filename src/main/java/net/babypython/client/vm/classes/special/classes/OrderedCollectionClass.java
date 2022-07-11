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
package net.babypython.client.vm.classes.special.classes;

import net.babypython.client.vm.constants.PythonTypeConstants;
import net.babypython.client.vm.constants.PythonMethodConstants;
import net.babypython.client.vm.classes.methods.MethodPerform;
import net.babypython.client.vm.containers.lists.ObjectList;
import net.babypython.client.vm.interfaces.IMethod;
import net.babypython.client.vm.core.VClass;
import net.babypython.client.vm.classes.special.objects.OrderedCollectionObject;
import net.babypython.client.vm.containers.lists.StringList;

public class OrderedCollectionClass extends VClass {

    OrderedCollectionClass() {
        this(PythonTypeConstants.ClassOrderedCollection);
    }

    protected OrderedCollectionClass(String name) {
        super(name);
    }

    @Override
    public Object createInstance() {
        return new OrderedCollectionObject(this);
    }

    public Object createInstance(ObjectList objectList) {
        return new OrderedCollectionObject(this, objectList);
    }

    public static OrderedCollectionClass getInstance() {
        if (instance == null)
            instance = new OrderedCollectionClass();
        return instance;
    }

    @Override
    public IMethod getMethod(String selector) {
        if (isBuiltinMethod(selector))
            return methodPerform;
        return super.getMethod(selector);
    }

    @Override
    protected void getMethodBuiltins(StringList methodNames) {
        super.getMethodBuiltins(methodNames);
        methodNames.addAll(builtinMethods);
     }

    @Override
    public boolean hasMethod(String selector) {
        return isBuiltinMethod(selector) || super.hasMethod(selector);
    }

    @Override
    public boolean isBuiltinMethod(String methodName) {
        if (builtinMethods.contains(methodName))
            return true;
        return super.isBuiltinMethod(methodName);
    }

    public static StringList builtinMethods = new StringList(
            PythonMethodConstants.MessageAddArg,
            PythonMethodConstants.MessageAddAtArg,
            PythonMethodConstants.MessageAddFirstArg,
            PythonMethodConstants.MessageAddLastArg,
            PythonMethodConstants.MessageAsArray,
            PythonMethodConstants.MessageAtArg,
            PythonMethodConstants.MessageAtPutArg,
            PythonMethodConstants.MessageFirst,
            PythonMethodConstants.MessageIsEmpty,
            PythonMethodConstants.MessageLast,
            PythonMethodConstants.MessageRemoveAll,
            PythonMethodConstants.MessageRemoveAtArg,
            PythonMethodConstants.MessageRemoveFirst,
            PythonMethodConstants.MessageRemoveLast,
            PythonMethodConstants.MessageSize
    );

    protected MethodPerform methodPerform = MethodPerform.getInstance();
    static OrderedCollectionClass instance;
}
