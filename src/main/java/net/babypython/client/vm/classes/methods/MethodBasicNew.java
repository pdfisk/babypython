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
import net.babypython.client.vm.core.VClass;
import net.babypython.client.vm.classes.special.objects.NilObject;
import net.babypython.client.vm.vm.context.Context;

public class MethodBasicNew implements IMethod {

    @Override
    public String getClassName() {
        return PythonMethodConstants.ClassBuiltinMethod;
    }

    @Override
    public String getSelector() {
        return PythonMethodConstants.MessageBasicNew;
    }

    @Override
    public String getSource() {
        return PythonMethodConstants.SourceBuiltin;
    }

    public Object perform(Context frame, Object receiverObject, String selector, Object[] arg) {
        if (receiverObject instanceof VClass)
            return ((VClass) receiverObject).createInstance();
        return NilObject.getInstance();
    }

    public static MethodBasicNew getInstance() {
        if (instance == null)
            instance = new MethodBasicNew();
        return instance;
    }

    static MethodBasicNew instance;
}
