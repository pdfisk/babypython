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
        return byteCodes.get(byteCodes.size() - 1) == ByteCodeConstants.OpReturnFromMethod;
    }

    public int mark() {
        return byteCodes.size();
    }

    public void normalizeStack() {
        while (recordingStack.getStackDepth() > 1)
            opPop();
    }

    public void opAssignGlobalVariable(String varName) {
        addByteCode(ByteCodeConstants.OpAssignGlobalVariable);
        addByteCode(getLiteralIndex(varName));
    }

    public void opAssignInstanceVariable(String varName) {
        addByteCode(ByteCodeConstants.OpAssignInstanceVariable);
        addByteCode(getLiteralIndex(varName));
    }

    public void opAssignLocalVariable(String varName) {
        addByteCode(ByteCodeConstants.OpAssignLocalVariable);
        addByteCode(getLiteralIndex(varName));
    }

    public void opBinaryDivide() {
        addByteCode(ByteCodeConstants.OpBinaryDivide);
        recordingStack.recordPop(2);
        recordingStack.recordPush();
    }

    public void opBinaryMinus() {
        addByteCode(ByteCodeConstants.OpBinaryMinus);
        recordingStack.recordPop(2);
        recordingStack.recordPush();
    }

    public void opBinaryMod() {
        addByteCode(ByteCodeConstants.OpBinaryMod);
        recordingStack.recordPop(2);
        recordingStack.recordPush();
    }

    public void opBinaryMultiply() {
        addByteCode(ByteCodeConstants.OpBinaryMultiply);
        recordingStack.recordPop(2);
        recordingStack.recordPush();
    }

    public void opBinaryPlus() {
        addByteCode(ByteCodeConstants.OpBinaryPlus);
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

    public void opDup() {
        addByteCode(ByteCodeConstants.OpDup);
    }

    public void opFork() {
        addByteCode(ByteCodeConstants.OpFork);
    }

    public void opForkWithArgs(int nArgs) {
        addByteCode(ByteCodeConstants.OpForkWithArgs);
        addByteCode(nArgs);
    }

    public void opJumpIfFalse(int n) {
        addByteCode(ByteCodeConstants.OpJumpIfFalse);
        addByteCode(n);
        recordingStack.recordPop();
    }

    public void opJumpTo(int n) {
        addByteCode(ByteCodeConstants.OpJumpTo);
        addByteCode(n);
    }

    public void opPop() {
        addByteCode(ByteCodeConstants.OpPop);
        recordingStack.recordPop();
    }

    public void opPopIntoLocalVariable(String varName) {
        addByteCode(ByteCodeConstants.OpPopIntoLocalVariable);
        addByteCode(getLiteralIndex(varName));
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

    public void opPushGlobalVariable(String varName) {
        addByteCode(ByteCodeConstants.OpPushGlobalVariable);
        addByteCode(getLiteralIndex(varName));
        recordingStack.recordPush();
    }

    public void opPushInstanceVariable(String varName) {
        addByteCode(ByteCodeConstants.OpPushInstanceVariable);
        addByteCode(getLiteralIndex(varName));
        recordingStack.recordPush();

    }

    public void opPushLiteral(Object literal) {
        addByteCode(ByteCodeConstants.OpPushConstant);
        addByteCode(getLiteralIndex(literal));
        recordingStack.recordPush();
    }

    public void opPushLocalVariable(String varName) {
        addByteCode(ByteCodeConstants.OpPushLocalVariable);
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

    public void opReturnFromMethod() {
        if (!isLastReturnFromMethod()) {
            addByteCode(ByteCodeConstants.OpReturnFromMethod);
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
