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
package net.babypython.client.vm.vm.context;

import net.babypython.client.vm.containers.dictionaries.LocalDictionary;
import net.babypython.client.vm.interfaces.IMethod;
import net.babypython.client.vm.vm.runtime.VmProcess;

public class HomeContext extends Context {

    public HomeContext(VmProcess process, Object receiver, IMethod method) {
        super(process);
        this.receiver = receiver;
        this.method = method;
        try {
            compiledMethod = getCompiledMethod();
        } catch (Exception e) {
            info("error getCompiledMethod", e.getMessage());
        }
        if (compiledMethod != null) {
            LocalDictionary initialValues = null;
            try {
                initialValues = compiledMethod.getLocalDictionary();
            } catch (Exception e) {
                info("getLocalDictionary error", e.getMessage());
            }
            if (initialValues != null) {
                for (String key : initialValues.keySet()) {
                    Object value = initialValues.get(key);
                    setLocalValue(key, value);
                }
            }
        }
    }

    @Override
    public String displayString() {
        return "HomeContext(" + getSelector() + " " + getPc() + " " + getStack().peek() + ")";
    }

    @Override
    public CompiledMethod getCompiledMethod() {
        if (method instanceof CompiledMethod) {
            CompiledMethod compiledMethod = (CompiledMethod) method;
            compiledMethod.ensureCompiled();
            return compiledMethod;
        }
        return null;
    }

    @Override
    public HomeContext getHomeContext() {
        return this;
    }

    @Override
    public Object getReceiver() {
        return receiver;
    }

    @Override
    public boolean isHomeContext() {
        return true;
    }

    protected CompiledMethod compiledMethod;
    public IMethod method;
    public Object receiver;
}
