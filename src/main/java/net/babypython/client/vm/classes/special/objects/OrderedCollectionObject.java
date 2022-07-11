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
import net.babypython.client.vm.classes.special.classes.ArrayClass;
import net.babypython.client.vm.classes.special.classes.OrderedCollectionClass;
import net.babypython.client.vm.containers.lists.ObjectList;
import net.babypython.client.vm.interfaces.IPerform;
import net.babypython.client.vm.core.VClass;
import net.babypython.client.vm.core.VObject;
import net.babypython.client.vm.vm.context.Context;
import net.babypython.client.vm.vm.runtime.operations.IntOps;

public class OrderedCollectionObject extends VObject implements IPerform {

    public OrderedCollectionObject(VClass vClass) {
        this(vClass, new ObjectList());
    }

    public OrderedCollectionObject(VClass vClass, ObjectList objectList) {
        super(vClass);
        this.objectList = objectList;
    }

    public static boolean canPerform(String selector) {
        return OrderedCollectionClass.getInstance().isBuiltinMethod(selector);
    }

    @Override
    public Object perform(Context context, Object receiverObject, String selector, Object[] args) {
        switch (selector) {
            case PythonMethodConstants.MessageAddArg:
                return onAddArg(context, args);
            case PythonMethodConstants.MessageAddAtArg:
                return onAddAtArg(context, args);
            case PythonMethodConstants.MessageAddFirstArg:
                return onAddFirstArg(context, args);
            case PythonMethodConstants.MessageAddLastArg:
                return onAddLastArg(context, args);
            case PythonMethodConstants.MessageAsArray:
                return onAsArray(context, args);
            case PythonMethodConstants.MessageAtArg:
                return onAtArg(context, args);
            case PythonMethodConstants.MessageAtPutArg:
                return onAtPutArg(context, args);
            case PythonMethodConstants.MessageFirst:
                return onFirst(context, args);
            case PythonMethodConstants.MessageIsEmpty:
                return onIsEmpty(context, args);
            case PythonMethodConstants.MessageLast:
                return onLast(context, args);
            case PythonMethodConstants.MessageRemoveAll:
                return onRemoveAll(context, args);
            case PythonMethodConstants.MessageRemoveAtArg:
                return onRemoveAtArg(context, args);
            case PythonMethodConstants.MessageRemoveFirst:
                return onRemoveFirst(context, args);
            case PythonMethodConstants.MessageRemoveLast:
                return onRemoveLast(context, args);
            case PythonMethodConstants.MessageSize:
                return onSize(context, args);
            default:
                return this;
        }
    }

    Object onAddArg(Context context, Object[] args) {
        Object item = args[0];
        objectList.add(item);
        return (item);
    }

    Object onAddAtArg(Context context, Object[] args) {
        int index = utilGetIntArg(0, args);
        Object item = args[1];
        objectList.add(index - 1, item);
        return (item);
    }

    Object onAddFirstArg(Context context, Object[] args) {
        Object item = args[0];
        objectList.add(0, item);
        return (item);
    }

    Object onAddLastArg(Context context, Object[] args) {
        Object item = args[0];
        objectList.add(item);
        return (item);
    }

    Object onAsArray(Context context, Object[] args) {
        return ArrayClass.getInstance().createInstance(objectList);
    }

    Object onAtArg(Context context, Object[] args) {
        int index = utilGetIntArg(0, args);
        return (objectList.get(index - 1));
    }

    Object onAtPutArg(Context context, Object[] args) {
        int index = utilGetIntArg(0, args);
        Object item = args[1];
        objectList.set(index - 1, item);
        return (item);
    }

    Object onFirst(Context context, Object[] args) {
        return (objectList.get(0));
    }

    Object onIsEmpty(Context context, Object[] args) {
        return (objectList.isEmpty());
    }

    Object onLast(Context context, Object[] args) {
        return (objectList.get(objectList.size() - 1));
    }

    Object onRemoveAll(Context context, Object[] args) {
        objectList.clear();
        return (objectList);
    }

    Object onRemoveAtArg(Context context, Object[] args) {
        int index = utilGetIntArg(0, args);
        Object item = objectList.get(index - 1);
        objectList.remove(index - 1);
        return (item);
    }

    Object onRemoveFirst(Context context, Object[] args) {
        Object item = objectList.get(0);
        objectList.remove(0);
        return (item);
    }

    Object onRemoveLast(Context context, Object[] args) {
        int index = objectList.size() - 1;
        Object item = objectList.get(index);
        objectList.remove(index);
        return (item);
    }

    Object onSize(Context context, Object[] args) {
        return (objectList.size());
    }

    @Override
    public String toString() {
        return "OrderedCollection(" + objectList.size() + ")";
    }

    int utilGetIntArg(int index, Object[] args) {
        if (args.length < index + 1)
            return -1;
        return IntOps.coerce(args[index]);
    }

    protected ObjectList objectList;
}
