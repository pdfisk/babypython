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
import net.babypython.client.vm.classes.methods.MethodPerformSymbol;
import net.babypython.client.vm.containers.lists.StringList;
import net.babypython.client.vm.interfaces.IMethod;
import net.babypython.client.vm.core.VClass;
import net.babypython.client.vm.classes.special.objects.SymbolObject;

import java.util.HashMap;

public class SymbolClass extends VClass {

    public static SymbolObject intern(String value) {
        return getInstance().internString(value);
    }

    SymbolClass() {
        super(PythonTypeConstants.ClassSymbol);
    }

    SymbolObject internString(String value) {
        if (symbolTable.containsKey(value))
            return symbolTable.get(value);
        SymbolObject symbolObject = new SymbolObject(value);
        symbolTable.put(value, symbolObject);
        return symbolObject;
    }

    @Override
    public IMethod getMethod(String selector) {
        if (isBuiltinMethod(selector))
            return methodPerformSymbol;
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
    public boolean isBuiltinMethod(String selector) {
        if (builtinMethods.contains(selector))
            return true;
        return super.isBuiltinMethod(selector);
    }

    public static StringList builtinMethods = new StringList(
            PythonMethodConstants.MessageAsString
    );

    public static SymbolClass getInstance() {
        if (instance == null)
            instance = new SymbolClass();
        return instance;
    }

    MethodPerformSymbol methodPerformSymbol = MethodPerformSymbol.getInstance();
    HashMap<String, SymbolObject> symbolTable = new HashMap<>();
    static SymbolClass instance;
}
