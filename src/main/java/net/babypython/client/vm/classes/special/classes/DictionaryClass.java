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
import net.babypython.client.vm.classes.methods.MethodPerformDictionary;
import net.babypython.client.vm.interfaces.IMethod;
import net.babypython.client.vm.core.VClass;
import net.babypython.client.vm.classes.special.objects.DictionaryObject;
import net.babypython.client.vm.containers.lists.StringList;

public class DictionaryClass extends VClass {

    DictionaryClass() {
        this(PythonTypeConstants.ClassDictionary);
    }

    protected DictionaryClass(String name) {
        super(name);
    }

    @Override
    public DictionaryObject createInstance() {
        return new DictionaryObject(this);
    }

    public IMethod lookupMethod(String selector) {
        return methodPerformDictionary;
    }

    public static DictionaryClass getInstance() {
        if (instance == null)
            instance = new DictionaryClass();
        return instance;
    }

    @Override
    protected void getMethodBuiltins(StringList methodNames) {
        super.getMethodBuiltins(methodNames);
        methodNames.add(PythonMethodConstants.MessageAtArg);
        methodNames.add(PythonMethodConstants.MessageAtPutArg);
        methodNames.add(PythonMethodConstants.MessageIncludesKeyArg);
        methodNames.add(PythonMethodConstants.MessageIsEmpty);
        methodNames.add(PythonMethodConstants.MessageRemoveAll);
        methodNames.add(PythonMethodConstants.MessageRemoveAtArg);
        methodNames.add(PythonMethodConstants.MessageSize);
    }

    @Override
    public boolean isBuiltinMethod(String methodName) {
        switch (methodName) {
            case PythonMethodConstants.MessageAtArg:
            case PythonMethodConstants.MessageAtPutArg:
            case PythonMethodConstants.MessageIncludesKeyArg:
            case PythonMethodConstants.MessageIsEmpty:
            case PythonMethodConstants.MessageRemoveAll:
            case PythonMethodConstants.MessageRemoveAtArg:
            case PythonMethodConstants.MessageSize:
                return true;
            default:
                return false;
        }
    }

    protected MethodPerformDictionary methodPerformDictionary = MethodPerformDictionary.getInstance();
    static DictionaryClass instance;
}
