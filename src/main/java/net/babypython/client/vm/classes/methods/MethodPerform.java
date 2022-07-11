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
package net.babypython.client.vm.classes.methods;

import net.babypython.client.vm.constants.PythonMethodConstants;
import net.babypython.client.vm.interfaces.IMethod;
import net.babypython.client.vm.interfaces.IPerform;
import net.babypython.client.vm.classes.special.objects.NilObject;
import net.babypython.client.vm.vm.context.Context;

public class MethodPerform implements IMethod {

    public boolean canHandle(String selector) {
        return false;
    }

    @Override
    public String getClassName() {
        return PythonMethodConstants.ClassBuiltinMethod;
    }

    @Override
    public String getSelector() {
        return PythonMethodConstants.MessagePerform;
    }

    @Override
    public String getSource() {
        return PythonMethodConstants.SourceBuiltin;
    }

    public Object perform(Context context, Object receiverObject, String selector, Object[] args) {
        if (receiverObject instanceof IPerform)
            return ((IPerform) receiverObject).perform(context, receiverObject, selector, args);
        return NilObject.getInstance();
    }

    public static MethodPerform getInstance() {
        if (instance == null)
            instance = new MethodPerform();
        return instance;
    }

    static MethodPerform instance;
}
