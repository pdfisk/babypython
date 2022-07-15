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
package net.babypython.client.vm.constants;

public class ByteCodeConstants {
    public static final int OpAssignGlobalVariable = 1;
    public static final int OpAssignInstanceVariable = 2;
    public static final int OpAssignLocalVariable = 3;
    public static final int OpBinaryDivide = 4;
    public static final int OpBinaryMinus = 5;
    public static final int OpBinaryMod = 6;
    public static final int OpBinaryMultiply = 7;
    public static final int OpBinaryPlus = 8;
    public static final int OpCompEq = 9;
    public static final int OpCompGe = 10;
    public static final int OpCompGt = 11;
    public static final int OpCompLe = 12;
    public static final int OpCompLt = 13;
    public static final int OpCompNeq = 14;
    public static final int OpDefFunction = 15;
    public static final int OpDup = 16;
    public static final int OpFork = 17;
    public static final int OpForkWithArgs = 18;
    public static final int OpJumpIfFalse = 19;
    public static final int OpJumpTo = 20;
    public static final int OpLineChanged = 21;
    public static final int OpPop = 22;
    public static final int OpPopIntoLocalVariable = 23;
    public static final int OpPushArray = 24;
    public static final int OpPushAssociation = 25;
    public static final int OpPushConstant = 26;
    public static final int OpPushDictionary = 27;
    public static final int OpPushFalse = 28;
    public static final int OpPushGlobalVariable = 29;
    public static final int OpPushInstanceVariable = 30;
    public static final int OpPushLocalVariable = 31;
    public static final int OpPushNone = 32;
    public static final int OpPushProperty = 33;
    public static final int OpPushSelf = 34;
    public static final int OpPushTrue = 35;
    public static final int OpPythonCallFunction = 36;
    public static final int OpPythonCallNamedFunction = 37;
    public static final int OpPythonInstantiateType = 38;
    public static final int OpReturnFromMethod = 39;
    public static final int OpSendMessage = 40;
    public static final int OpSendMessageWithArgs = 41;
    public static final int OpSendSuperMessage = 42;
    public static final int OpSendSuperMessageWithArgs = 43;
    public static final int OpTraceMessage = 44;
    public static final int OpTraceVariable = 45;
    public static final int OpValue = 46;
    public static final int OpValueWithArgs = 47;
}
