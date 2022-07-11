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
package net.babypython.client.python.builtins;

import net.babypython.client.python.builtins.functions.PythonBalloonFunction;
import net.babypython.client.python.builtins.functions.PythonPrintFunction;
import net.babypython.client.python.builtins.functions.PythonSleepFunction;
import net.babypython.client.python.core.abstract_.PythonFunction;
import net.babypython.client.vm.containers.dictionaries.BuiltinFunctionDictionary;
import net.babypython.client.vm.vm.context.Context;

public class PythonBuiltinFunctions {

    PythonBuiltinFunctions() {
        builtinFunctions = new BuiltinFunctionDictionary();
        addBuiltinFunctions();
    }

    void addBuiltinFunction(net.babypython.client.python.core.builtin.PythonBuiltinFunction pythonBuiltinFunction) {
        builtinFunctions.put(pythonBuiltinFunction.getName(), pythonBuiltinFunction);
    }

    void addBuiltinFunctions() {
        addBuiltinFunction(new PythonBalloonFunction());
        addBuiltinFunction(new PythonPrintFunction());
        addBuiltinFunction(new PythonSleepFunction());
    }

    boolean invokeFunction(Context context, String fnName, Object[] args) {
        if (!builtinFunctions.containsKey(fnName))
            return false;
        PythonFunction pythonBuiltinFunction = builtinFunctions.get(fnName);
        return pythonBuiltinFunction.invoke(context, args);
    }

    public static boolean invoke(Context context, String fnName, Object[] args) {
        return getInstance().invokeFunction(context, fnName, args);
    }

    public static PythonBuiltinFunctions getInstance() {
        if (instance == null)
            instance = new PythonBuiltinFunctions();
        return instance;
    }

    BuiltinFunctionDictionary builtinFunctions;
    static PythonBuiltinFunctions instance;

}
