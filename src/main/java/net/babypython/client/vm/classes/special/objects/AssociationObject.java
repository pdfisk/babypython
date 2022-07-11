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
package net.babypython.client.vm.classes.special.objects;

import net.babypython.client.vm.constants.PythonMethodConstants;
import net.babypython.client.vm.interfaces.IPerform;
import net.babypython.client.vm.core.VClass;
import net.babypython.client.vm.core.VObject;
import net.babypython.client.vm.util.ArgsUtil;
import net.babypython.client.vm.vm.context.Context;

public class AssociationObject extends VObject implements IPerform {

    public AssociationObject(VClass vClass) {
        super(vClass);
        key = "<undefined>";
        value = getNilObject();
    }

    public AssociationObject(VClass vClass, String key, Object value) {
        super(vClass);
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public Object perform(Context context, Object receiverObject, String selector, Object[] args) {
        switch (selector) {
            case PythonMethodConstants.MessageKey:
                return onKey(context, args);
            case PythonMethodConstants.MessageKeyArg:
                return onKeyArg(context, args);
            case PythonMethodConstants.MessageSize:
                return onSize(context, args);
            case PythonMethodConstants.MessageValue:
                return onValue(context, args);
            case PythonMethodConstants.MessageValueArg:
                return onValueArg(context, args);
            default:
                return this;
        }
    }

    Object onKey(Context context, Object[] args) {
        return key;
    }

    Object onKeyArg(Context context, Object[] args) {
        key = ArgsUtil.getStringArg(args);
        return this;
    }

    Object onSize(Context context, Object[] args) {
        return 2;
    }

    Object onValue(Context context, Object[] args) {
        return value;
    }

    Object onValueArg(Context context, Object[] args) {
        value = args[0];
        return this;
    }

    public String toString() {
        return "Association(" + key + " -> " + value.toString() +  ")";
    }

    protected String key;
    protected  Object value;
}
