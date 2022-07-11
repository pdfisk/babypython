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
package net.babypython.client.vm.workspace;

import net.babypython.client.vm.classes.special.objects.NilObject;
import net.babypython.client.vm.constants.PythonTypeConstants;
import net.babypython.client.vm.core.VClass;
import net.babypython.client.vm.core.VObject;
import net.babypython.client.vm.containers.dictionaries.ObjectDictionary;

public class Workspace extends ObjectDictionary {

    Workspace() {
        super();
    }

    public void deleteGlobal(String key) {
        remove(key);
    }

    public VClass getClass(String className) {
        return (VClass) getGlobal(className);
    }

    public Object getGlobal(String key) {
        return get(key);
    }

    public VObject getNoneObject() {
        return NilObject.getInstance();
    }

    public VClass getMetaClass() {
        return getClass(PythonTypeConstants.ClassMetaClass);
    }

    public VClass getObjectClass() {
        return getClass(PythonTypeConstants.ClassObject);
    }

    public void putGlobal(String key, Object value) {
        put(key, value);
    }

    public static Workspace getInstance() {
        if (instance == null)
            instance = new Workspace();
        return instance;
    }

    static Workspace instance;
}
