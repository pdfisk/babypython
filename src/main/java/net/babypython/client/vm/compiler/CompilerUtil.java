/**
 * MIT License
 * <p>
 * Copyright (c) 2022 Peter Fisk
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.babypython.client.vm.compiler;

import net.babypython.client.vm.containers.lists.IntegerList;
import net.babypython.client.vm.containers.lists.ObjectList;
import net.babypython.client.python.core.abstract_.PythonFunction;
import net.babypython.client.vm.core.VClass;
import net.babypython.client.vm.constants.ByteCodeConstants;
import net.babypython.client.ui.util.Logable;
import net.babypython.client.vm.vm.context.CompiledMethod;

public class CompilerUtil extends Logable {

    public CompilerUtil(CompiledMethod compiledMethod) {
        this(compiledMethod.getVClass(), compiledMethod);
    }

    public CompilerUtil(VClass compilingClass, CompiledMethod compiledMethod) {
        this.compilingClass = compilingClass;
        this.compiledMethod = compiledMethod;
        byteCodes = compiledMethod.getByteCodes();
        literals = compiledMethod.getLiterals();
        recordingStack = new RecordingStack(this);
    }

    void addByteCode(int byteCode) {
        byteCodes.add(byteCode);
    }

    public void generateLineNumber(int lineNumber) {
        if (lastLineNumber != lineNumber) {
            lastLineNumber = lineNumber;
            addByteCode(ByteCodeConstants.OpLineChanged);
            addByteCode(lineNumber);
        }
    }

    public int getLiteralIndex(Object value) {
        int index = literals.indexOf(value);
        if (index >= 0)
            return index;
        literals.add(value);
        return literals.size() - 1;
    }

    public String getUniqueVarName() {
        return "$var:" + idCounter++;
    }

    public boolean isInstanceVariable(String varName) {
        return compilingClass.getInstVarNames().contains(varName);
    }

    boolean isLastReturnFromMethod() {
        if (byteCodes.size() == 0)
            return false;
        return byteCodes.get(byteCodes.size() - 1) == ByteCodeConstants.RETURN_VALUE;
    }

    public int mark() {
        return byteCodes.size();
    }

    public void normalizeStack() {
        while (recordingStack.getStackDepth() > 1)
            op_POP_TOP();
    }

    public void op_STORE_GLOBAL(String varName) {
        addByteCode(ByteCodeConstants.STORE_GLOBAL);
        addByteCode(getLiteralIndex(varName));
    }

    public void op_STORE_ATTR(String varName) {
        addByteCode(ByteCodeConstants.STORE_ATTR);
        addByteCode(getLiteralIndex(varName));
    }

    public void op_STORE_NAME(String varName) {
        addByteCode(ByteCodeConstants.STORE_NAME);
        addByteCode(getLiteralIndex(varName));
    }

    public void op_BINARY_TRUE_DIVIDE() {
        addByteCode(ByteCodeConstants.BINARY_TRUE_DIVIDE);
        recordingStack.recordPop(2);
        recordingStack.recordPush();
    }

    public void op_BINARY_SUBTRACT() {
        addByteCode(ByteCodeConstants.BINARY_SUBTRACT);
        recordingStack.recordPop(2);
        recordingStack.recordPush();
    }

    public void op_BINARY_MODULO() {
        addByteCode(ByteCodeConstants.BINARY_MODULO);
        recordingStack.recordPop(2);
        recordingStack.recordPush();
    }

    public void op_BINARY_MULTIPLY() {
        addByteCode(ByteCodeConstants.BINARY_MULTIPLY);
        recordingStack.recordPop(2);
        recordingStack.recordPush();
    }

    public void op_BINARY_ADD() {
        addByteCode(ByteCodeConstants.BINARY_ADD);
        recordingStack.recordPop(2);
        recordingStack.recordPush();
    }

    public void opCompEq() {
        addByteCode(ByteCodeConstants.OpCompEq);
        recordingStack.recordPop(2);
        recordingStack.recordPush();
    }

    public void opCompGe() {
        addByteCode(ByteCodeConstants.OpCompGe);
        recordingStack.recordPop(2);
        recordingStack.recordPush();
    }

    public void opCompGt() {
        addByteCode(ByteCodeConstants.OpCompGt);
        recordingStack.recordPop(2);
        recordingStack.recordPush();
    }

    public void opCompLe() {
        addByteCode(ByteCodeConstants.OpCompLe);
        recordingStack.recordPop(2);
        recordingStack.recordPush();
    }

    public void opCompLt() {
        addByteCode(ByteCodeConstants.OpCompLt);
        recordingStack.recordPop(2);
        recordingStack.recordPush();
    }

    public void opCompNeq() {
        addByteCode(ByteCodeConstants.OpCompNeq);
        recordingStack.recordPop(2);
        recordingStack.recordPush();
    }

    public void opDefFunction(PythonFunction fn) {
        addByteCode(ByteCodeConstants.OpDefFunction);
        addByteCode(getLiteralIndex(fn));
        recordingStack.recordPush();
    }

    public void op_DUP_TOP() {
        addByteCode(ByteCodeConstants.DUP_TOP);
    }

    public void opFork() {
        addByteCode(ByteCodeConstants.OpFork);
    }

    public void opForkWithArgs(int nArgs) {
        addByteCode(ByteCodeConstants.OpForkWithArgs);
        addByteCode(nArgs);
    }

    public void op_POP_JUMP_IF_FALSE(int n) {
        addByteCode(ByteCodeConstants.POP_JUMP_IF_FALSE);
        addByteCode(n);
        recordingStack.recordPop();
    }

    public void op_JUMP_ABSOLUTE(int n) {
        addByteCode(ByteCodeConstants.JUMP_ABSOLUTE);
        addByteCode(n);
    }

    public void op_POP_TOP() {
        addByteCode(ByteCodeConstants.POP_TOP);
        recordingStack.recordPop();
    }

    public void opPushArray(int nItems) {
        addByteCode(ByteCodeConstants.OpPushArray);
        addByteCode(nItems);
        recordingStack.recordPop(nItems);
        recordingStack.recordPush();
    }

    public void opPushAssociation() {
        addByteCode(ByteCodeConstants.OpPushAssociation);
        recordingStack.recordPop(2);
        recordingStack.recordPush();
    }

    public void opPushDictionary(int nItems) {
        addByteCode(ByteCodeConstants.OpPushDictionary);
        addByteCode(nItems);
        recordingStack.recordPop(nItems);
        recordingStack.recordPush();
    }

    public void opPushFalse() {
        addByteCode(ByteCodeConstants.OpPushFalse);
        recordingStack.recordPush();
    }

    public void op_LOAD_GLOBAL(String varName) {
        addByteCode(ByteCodeConstants.LOAD_GLOBAL);
        addByteCode(getLiteralIndex(varName));
        recordingStack.recordPush();
    }

    public void op_LOAD_ATTR(String varName) {
        addByteCode(ByteCodeConstants.LOAD_ATTR);
        addByteCode(getLiteralIndex(varName));
        recordingStack.recordPush();

    }

    public void op_LOAD_CONST(Object literal) {
        addByteCode(ByteCodeConstants.LOAD_CONST);
        addByteCode(getLiteralIndex(literal));
        recordingStack.recordPush();
    }

    public void op_LOAD_NAME(String varName) {
        addByteCode(ByteCodeConstants.LOAD_NAME);
        addByteCode(getLiteralIndex(varName));
        recordingStack.recordPush();
    }

    public void opPushNil() {
        addByteCode(ByteCodeConstants.OpPushNone);
        recordingStack.recordPush();
    }

    public void opPushProperty(String propertyName) {
        addByteCode(ByteCodeConstants.OpPushProperty);
        addByteCode(getLiteralIndex(propertyName));
        recordingStack.recordPush();
    }

    public void opPushSelf() {
        addByteCode(ByteCodeConstants.OpPushSelf);
    }

    public void opPushTrue() {
        addByteCode(ByteCodeConstants.OpPushTrue);
        recordingStack.recordPush();
    }

    public void opPythonCallFunction(int nArgs) {
        addByteCode(ByteCodeConstants.OpPythonCallFunction);
        addByteCode(nArgs);
        recordingStack.recordPop(nArgs);
        recordingStack.recordPush();
    }

    public void opPythonCallNamedFunction(String fnName, int nArgs) {
        addByteCode(ByteCodeConstants.OpPythonCallNamedFunction);
        addByteCode(getLiteralIndex(fnName));
        addByteCode(nArgs);
        recordingStack.recordPop(nArgs);
        recordingStack.recordPush();
    }

    public void opPythonInstantiateClass(String clsName, int nArgs) {
        addByteCode(ByteCodeConstants.OpPythonInstantiateType);
        addByteCode(getLiteralIndex(clsName));
        addByteCode(nArgs);
        recordingStack.recordPop(nArgs);
        recordingStack.recordPush();
    }

    public void op_RETURN_VALUE() {
        if (!isLastReturnFromMethod()) {
            addByteCode(ByteCodeConstants.RETURN_VALUE);
            recordingStack.recordPush();
        }
    }

    public void opSendMessage(String selector) {
        addByteCode(ByteCodeConstants.OpSendMessage);
        addByteCode(getLiteralIndex(selector));
    }

    public void opSendMessageWithArgs(String selector, int nArgs) {
        addByteCode(ByteCodeConstants.OpSendMessageWithArgs);
        addByteCode(getLiteralIndex(selector));
        addByteCode(nArgs);
        recordingStack.recordPop(nArgs);
    }

    public void opSendSuperMessage(String selector) {
        addByteCode(ByteCodeConstants.OpSendSuperMessage);
        addByteCode(getLiteralIndex(selector));
    }

    public void opSendSuperMessageWithArgs(String selector, int nArgs) {
        addByteCode(ByteCodeConstants.OpSendSuperMessageWithArgs);
        addByteCode(getLiteralIndex(selector));
        addByteCode(nArgs);
        recordingStack.recordPop(nArgs);
    }

    public void opTraceMessage(String message) {
        addByteCode(ByteCodeConstants.OpTraceMessage);
        addByteCode(getLiteralIndex(message));
    }

    public void opTraceVariable(String varName) {
        addByteCode(ByteCodeConstants.OpTraceVariable);
        addByteCode(getLiteralIndex(varName));
    }

    public void opValue() {
        addByteCode(ByteCodeConstants.OpValue);
        recordingStack.recordPush();
    }

    public void opValueWithArgs(int nArgs) {
        addByteCode(ByteCodeConstants.OpValueWithArgs);
        addByteCode(nArgs);
        recordingStack.recordPop(nArgs);
        recordingStack.recordPush();
    }

    public void patch(int index, int value) {
        if (byteCodes.get(index) == 0)
            byteCodes.set(index, value);
    }

    public void showCode() {
        showCode(0, byteCodes.size() - 1);
    }

    public void showCode(int start, int end) {
        if (start < 0 || end < 0 || start >= byteCodes.size() || end >= byteCodes.size() || start > end)
            return;
        ByteCodeUtil.setByteCodes(compiledMethod.getSelector(), byteCodes);
        for (int pc = start; pc <= end; pc += ByteCodeUtil.pcInc(pc))
            ByteCodeUtil.displayPc(pc);
    }

    IntegerList byteCodes;
    CompiledMethod compiledMethod;
    VClass compilingClass;
    ObjectList literals;
    RecordingStack recordingStack;
    static int idCounter = 0;
    static int lastLineNumber = 0;
}
