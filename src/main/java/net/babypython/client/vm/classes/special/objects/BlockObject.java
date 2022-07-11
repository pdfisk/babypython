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
import net.babypython.client.vm.classes.special.classes.BlockContextClass;
import net.babypython.client.vm.containers.lists.IntegerList;
import net.babypython.client.vm.containers.lists.ObjectList;
import net.babypython.client.vm.interfaces.IPerform;
import net.babypython.client.python.process.PythonTimeSliceProcess;
import net.babypython.client.vm.core.VObject;
import net.babypython.client.vm.vm.context.BlockContext;
import net.babypython.client.vm.vm.context.CompiledMethod;
import net.babypython.client.vm.vm.context.Context;
import net.babypython.client.vm.vm.runtime.VmProcess;

import java.util.Collections;

public class BlockObject extends VObject implements IPerform {

    public BlockObject(VmProcess process, int startPc) {
        super(BlockContextClass.getInstance());
        this.process = process;
        this.startPc = startPc;
        this.blockContext = this.process.pushBlockContext();
    }

    public static boolean canPerform(String selector) {
        switch (selector) {
            case PythonMethodConstants.MessageFork:
            case PythonMethodConstants.MessageForkArg:
            case PythonMethodConstants.MessageForkArg2:
            case PythonMethodConstants.MessageForkArg3:
            case PythonMethodConstants.MessageValue:
            case PythonMethodConstants.MessageValueArg:
            case PythonMethodConstants.MessageValueArg2:
            case PythonMethodConstants.MessageValueArg3:
            case PythonMethodConstants.MessageWhileFalseArg:
            case PythonMethodConstants.MessageWhileTrueArg:
                return true;
            default:
                return false;
        }
    }

    void ensureBlockMethod() {
        CompiledMethod compiledMethod = getCompiledMethod();
        blockMethod = new CompiledMethod(compiledMethod.getVClass());
        blockMethod.setIsCompiled();
        blockMethod.setLiterals(compiledMethod.getLiterals());
        IntegerList source = compiledMethod.getByteCodes();
        IntegerList dest = blockMethod.getByteCodes();
        for (int pc = startPc; pc < source.size(); pc++)
            dest.add(source.get(pc));
    }

    public PythonTimeSliceProcess fork() {
        return fork(null);
    }

    public PythonTimeSliceProcess fork(Object[] args) {
        PythonTimeSliceProcess forkedProcess = PythonTimeSliceProcess.create(this, args);
        forkedProcess.schedule();
        return forkedProcess;
    }

    public void fork(int nArgs) {
        ObjectList argList = new ObjectList();
        for (int i = 0; i < nArgs; i++)
            argList.add(process.pop());
        Collections.reverse(argList);
        Object[] args = argList.toArray();
        process.push(fork(args));
    }

    public CompiledMethod getCompiledMethod() {
        return blockContext.getCompiledMethod();
    }

    public CompiledMethod getCompiledMethodForBlockOnly() {
        ensureBlockMethod();
        return blockMethod;
    }

    public Object getReceiver() {
        return blockContext.getReceiver();
    }

    @Override
    public Object perform(Context frame, Object receiverObject, String selector, Object[] args) {
        return null;
    }

    public void value(Context currentContext, int nArgs) {
        blockContext.setOuterContext(currentContext);
        process.setCurrentContext(blockContext);
        blockContext.pushArgs(nArgs);
        process.jumpTo(startPc);
    }

    public String toString() {
        return "a Block";
    }

    public BlockContext blockContext;
    public CompiledMethod blockMethod;
    public VmProcess process;
    public int startPc;
}
