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
import net.babypython.client.vm.classes.special.objects.NilObject;
import net.babypython.client.vm.vm.context.Context;

public class MethodPerformBoolean extends MethodPerform {

    @Override
    public Object perform(Context context, Object receiverObject, String selector, Object[] args) {
        if (receiverObject instanceof Boolean)
            return performBoolean(context, (Boolean) receiverObject, selector, args);
        return super.perform(context, receiverObject, selector, args);
    }

    Object performBoolean( Context frame, boolean booleanValue, String selector, Object[] args) {
        if (booleanValue)
            return performBooleanTrue( frame, selector, args);
        else
            return performBooleanFalse( frame, selector, args);
    }

    Object performBooleanFalse( Context frame, String selector, Object[] args) {
        if (args.length > 0) {
            if (selector == PythonMethodConstants.MessageIfFalse)
                return performBlock( frame, args[0]);
        }
        return NilObject.getInstance();
    }

    Object performBooleanTrue( Context frame, String selector, Object[] args) {
        if (args.length > 0) {
            if (selector == PythonMethodConstants.MessageIfTrue)
                return performBlock( frame, args[0]);
        }
        return NilObject.getInstance();
    }

    Object performBlock( Context frame, Object maybeBlock) {
        return NilObject.getInstance();
    }

    public static MethodPerformBoolean getInstance() {
        if (instance == null)
            instance = new MethodPerformBoolean();
        return instance;
    }

    static MethodPerformBoolean instance;
}
