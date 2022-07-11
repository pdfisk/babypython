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

import net.babypython.client.vm.classes.special.objects.NilObject;
import net.babypython.client.vm.core.VClass;
import net.babypython.client.vm.containers.lists.IntegerList;
import net.babypython.client.vm.containers.lists.ObjectList;
import net.babypython.client.vm.containers.lists.StringList;
import net.babypython.client.vm.containers.stacks.ObjectStack;
import net.babypython.client.ui.util.Logable;
import net.babypython.client.vm.vm.interfaces.IStdOut;
import net.babypython.client.vm.vm.runtime.VmProcess;

import java.util.HashMap;

public abstract class Context extends Logable {

    public Context(VmProcess process) {
        this.process = process;
        locals = new HashMap<>();
        localVariableNames = new StringList();
        pc = 0;
        stack = new ObjectStack();
        push(NilObject.getInstance());
    }

    public boolean atEnd() {
        return getCompiledMethod() == null || pc >= getByteCodes().size();
    }

    public void declareLocalVariable(String variableName) {
        if (!localVariableNames.contains(variableName))
            localVariableNames.add(variableName);
    }

    public abstract String displayString();

    public IntegerList getByteCodes() {
        if (getCompiledMethod() == null)
            return IntegerList.emptyList;
        return getCompiledMethod().getByteCodes();
    }

    public CompiledMethod getCompiledMethod() {
        return getHomeContext().getCompiledMethod();
    }

    public VClass getCompilingClass() {
        return getCompiledMethod().getVClass();
    }

    public abstract HomeContext getHomeContext();

    public Context getHomeOuterContext() {
        return getHomeContext().getOuterContext();
    }

    public ObjectList getLiterals() {
        if (getCompiledMethod() == null)
            return ObjectList.emptyList;
        return getCompiledMethod().getLiterals();
    }

    public Object getLocalValue(String variableName) {
        if (locals.containsKey(variableName))
            return locals.get(variableName);
        if (outerContext != null)
            return outerContext.getLocalValue(variableName);
        return process.getGlobal(variableName);
    }

    public Context getOuterContext() {
        return this.outerContext;
    }

    public int getPc() {
        return pc;
    }

    public VmProcess getProcess() {
        return process;
    }

    public Object getReceiver() {
        HomeContext homeContext = getHomeContext();
        if (homeContext != null)
            return homeContext.getReceiver();
        return null;
    }

    public String getSelector() {
        return getCompiledMethod().getSelector();
    }

    public Object getSelf() {
        return getHomeContext().getReceiver();
    }

    public ObjectStack getStack() {
        return stack;
    }

    public IStdOut getStdOut() {
        return process.stdOut;
    }

    protected boolean hasLocalVariable(String variableName) {
        return localVariableNames.contains(variableName);
    }

    public void halt() {
        getProcess().halt();
    }

    private int incPc() {
        int temp = getPc();
        setPc(temp + 1);
        return temp;
    }

    public boolean isBlockContext() {
        return false;
    }

    public boolean isHomeContext() {
        return false;
    }

    public int nextByteCode() {
        if (atEnd())
            return -1;
        return getCompiledMethod().getByteCodes().get(incPc());
    }

    public Object pop() {
        return stack.pop();
    }

    public void push(Object item) {
        stack.push(item);
    }

    public void setLocalValue(String variableName, Object value) {
        if (outerContext == null)
            process.setGlobal(variableName, value);
        else
            locals.put(variableName, value);
        return;
    }

    public void setOuterContext(Context outerContext) {
        this.outerContext = outerContext;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public void sleep(double seconds) {
        getProcess().sleep(seconds);
    }

    @Override
    public String toString() {
        return displayString();
    }

    protected HashMap<String, Object> locals;
    protected StringList localVariableNames;
    protected Context outerContext = null;
    protected int pc;
    protected VmProcess process;
    protected ObjectStack stack;
}
