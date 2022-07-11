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
import net.babypython.client.vm.vm.context.Context;
import net.babypython.client.vm.vm.runtime.operations.StringOps;
import net.babypython.client.vm.containers.dictionaries.ObjectDictionary;

public class DictionaryObject extends VObject implements IPerform {

    public DictionaryObject(VClass vClass) {
        super(vClass);
        objectDictionary = new ObjectDictionary();
    }

    public static boolean canPerform(String selector) {
        switch (selector) {
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

    public Object get(String key) {
        return objectDictionary.get(key);
    }

    @Override
    public Object perform(Context context, Object receiverObject, String selector, Object[] args) {
        return null;
    }

    public Object perform(Object receiverObject, String selector, Object[] args) {
        switch (selector) {
             case PythonMethodConstants.MessageAtArg:
                return onAtArg(args);
            case PythonMethodConstants.MessageAtPutArg:
                return onAtPutArg(args);
            case PythonMethodConstants.MessageIncludesKeyArg:
                return onIncludesKeyArg(args);
            case PythonMethodConstants.MessageIsEmpty:
                return onIsEmpty(args);
            case PythonMethodConstants.MessageRemoveAll:
                return onRemoveAll(args);
            case PythonMethodConstants.MessageRemoveAtArg:
                return onRemoveAtArg(args);
            case PythonMethodConstants.MessageSize:
                return onSize(args);
            default:
                return this;
        }
    }
    
    Object onAtArg(Object[] args) {
        String key = utilGetStringArg(0, args);
        return objectDictionary.get(key);
    }

    Object onAtPutArg(Object[] args) {
        String key = utilGetStringArg(0, args);
        Object item = args[1];
        objectDictionary.put(key, item);
        return (item);
    }

    Object onIncludesKeyArg(Object[] args) {
        String key = utilGetStringArg(0, args);
        return objectDictionary.containsKey(key);
    }

    Object onIsEmpty(Object[] args) {
        return objectDictionary.isEmpty();
    }
    
    Object onRemoveAll(Object[] args) {
        objectDictionary.clear();
        return objectDictionary;
    }

    Object onRemoveAtArg(Object[] args) {
        String key = utilGetStringArg(0, args);
        Object item = objectDictionary.get(key);
        objectDictionary.remove(key);
        return item;
    }
    
    Object onSize(Object[] args) {
        return objectDictionary.size();
    }

    public Object put(String key, Object value) {
        objectDictionary.put(key, value);
        return key;
    }

    @Override
    public String toString() {
        return "Dictionary(" + objectDictionary.size() + ")";
    }

    String utilGetStringArg(int index, Object[] args) {
        if (args.length < index + 1)
            return "";
        return StringOps.coerce(args[index]);
    }

    protected ObjectDictionary objectDictionary;
}
