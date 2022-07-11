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

import net.babypython.client.vm.constants.ByteCodeConstants;
import net.babypython.client.ui.windows.transcript.TranscriptWindow;
import net.babypython.client.ui.util.Logable;
import net.babypython.client.vm.util.StringUtil;
import net.babypython.client.vm.vm.context.CompiledMethod;
import net.babypython.client.vm.vm.interfaces.IStdOut;
import net.babypython.client.vm.containers.lists.IntegerList;

public class ByteCodeUtil extends Logable {

    static boolean atEnd(int pc) {
        return pc < 0 || pc >= byteCodes.size();
    }

    public static void display() {
        int pc = 0;
        while (!atEnd(pc))
            pc = displayPc(pc);
    }

    public static int displayPc(int pc) {
        int bc = byteCodes.get(pc);
        String pcStr = StringUtil.padLeadingZeroes(pc);
        String selectorName = StringUtil.padSize(selector, 10);
        String name = StringUtil.padSize(bcName(bc), 22);
        String target = pcTarget(pc);
        info(pcStr, selectorName, name, target);
        return pc + bcInc(bc);
    }

    static String bcName(int bc) {
        switch (bc) {
            case ByteCodeConstants.OpAssignGlobalVariable:
                return "AssignGlobal";
            case ByteCodeConstants.OpAssignLocalVariable:
                return "AssignLocal";
            case ByteCodeConstants.OpBinaryDivide:
                return "BinaryDivide";
            case ByteCodeConstants.OpBinaryMinus:
                return "BinaryMinus";
            case ByteCodeConstants.OpBinaryMod:
                return "BinaryMod";
            case ByteCodeConstants.OpBinaryMultiply:
                return "BinaryMultiply";
            case ByteCodeConstants.OpBinaryPlus:
                return "BinaryPlus";
            case ByteCodeConstants.OpCompEq:
                return "CompEq";
            case ByteCodeConstants.OpCompGe:
                return "CompGe";
            case ByteCodeConstants.OpCompGt:
                return "CompGt";
            case ByteCodeConstants.OpCompLe:
                return "CompLe";
            case ByteCodeConstants.OpCompLt:
                return "CompLt";
            case ByteCodeConstants.OpCompNeq:
                return "CompNeq";
            case ByteCodeConstants.OpFork:
                return "Fork";
            case ByteCodeConstants.OpJumpIfFalse:
                return "JumpIfFalse";
            case ByteCodeConstants.OpJumpTo:
                return "JumpTo";
            case ByteCodeConstants.OpPop:
                return "Pop";
            case ByteCodeConstants.OpPopIntoLocalVariable:
                return "PopIntoLocalVariable";
            case ByteCodeConstants.OpPushArray:
                return "PushArray";
            case ByteCodeConstants.OpPushBlock:
                return "PushBlock";
            case ByteCodeConstants.OpPushFalse:
                return "PushFalse";
            case ByteCodeConstants.OpPushGlobalVariable:
                return "PushGlobal";
            case ByteCodeConstants.OpPushConstant:
                return "PushLiteral";
            case ByteCodeConstants.OpPushLocalVariable:
                return "PushLocal";
            case ByteCodeConstants.OpPushNone:
                return "PushNil";
            case ByteCodeConstants.OpPushSelf:
                return "PushSelf";
            case ByteCodeConstants.OpPushTrue:
                return "PushTrue";
            case ByteCodeConstants.OpReturnFromBlock:
                return "ReturnFromBlock";
            case ByteCodeConstants.OpReturnFromMethod:
                return "ReturnFromMethod";
            case ByteCodeConstants.OpSendMessage:
                return "SendMessage";
            case ByteCodeConstants.OpSendMessageWithArgs:
                return "SendMessageWithArgs";
            case ByteCodeConstants.OpTraceMessage:
                return "TraceMessage";
            case ByteCodeConstants.OpTraceVariable:
                return "TraceVariable";
            case ByteCodeConstants.OpValue:
                return "Value";
            case ByteCodeConstants.OpValueWithArgs:
                return "ValueWithArgs";
            default:
                return "*" + bc + "*";
        }
    }

    static String pcTarget(int pc) {
        int bc = byteCodes.get(pc);
        switch (bc) {
            case ByteCodeConstants.OpJumpIfFalse:
            case ByteCodeConstants.OpJumpTo:
            case ByteCodeConstants.OpPushArray:
            case ByteCodeConstants.OpValueWithArgs:
                return StringUtil.padLeadingZeroes(byteCodes.get(pc + 1));
            case ByteCodeConstants.OpAssignGlobalVariable:
            case ByteCodeConstants.OpAssignLocalVariable:
            case ByteCodeConstants.OpPopIntoLocalVariable:
            case ByteCodeConstants.OpPushConstant:
            case ByteCodeConstants.OpPushLocalVariable:
            case ByteCodeConstants.OpPushGlobalVariable:
            case ByteCodeConstants.OpSendMessage:
            case ByteCodeConstants.OpTraceMessage:
            case ByteCodeConstants.OpTraceVariable:
                return compilerUtil.literals.get(byteCodes.get(pc + 1)).toString();
            case ByteCodeConstants.OpSendMessageWithArgs:
                return compilerUtil.literals.get(byteCodes.get(pc + 1)).toString() + "  " + byteCodes.get(pc + 2).toString();
            default:
                return "";
        }
    }

    public static int pcInc(int pc) {
        return bcInc(byteCodes.get(pc));
    }

    static int bcInc(int bc) {
        switch (bc) {
            case ByteCodeConstants.OpAssignGlobalVariable:
            case ByteCodeConstants.OpAssignLocalVariable:
            case ByteCodeConstants.OpJumpIfFalse:
            case ByteCodeConstants.OpJumpTo:
            case ByteCodeConstants.OpPopIntoLocalVariable:
            case ByteCodeConstants.OpPushArray:
            case ByteCodeConstants.OpPushGlobalVariable:
            case ByteCodeConstants.OpPushConstant:
            case ByteCodeConstants.OpPushLocalVariable:
            case ByteCodeConstants.OpSendMessage:
            case ByteCodeConstants.OpTraceMessage:
            case ByteCodeConstants.OpTraceVariable:
            case ByteCodeConstants.OpValueWithArgs:
                return 2;
            case ByteCodeConstants.OpSendMessageWithArgs:
                return 3;
            default:
                return 1;
        }
    }

    public static void copyByteCodesForBlock(int start, IntegerList sourceByteCodes, IntegerList blockByteCodes) {
        for (int pc = start; pc < sourceByteCodes.size(); ) {
            int bc = sourceByteCodes.get(pc++);
            blockByteCodes.add(bc);
            if (bc == ByteCodeConstants.OpReturnFromBlock) {
                blockByteCodes.add(ByteCodeConstants.OpReturnFromMethod);
                break;
            }
            if (pcInc(pc) > 1) {
                int n = pcInc(pc) - 1;
                for (int i = 0; i < n; i++)
                    blockByteCodes.add(sourceByteCodes.get(pc++));
            }
            if (pc >= sourceByteCodes.size())
                break;
        }
    }

    static void setByteCodes(String selector, IntegerList list) {
        byteCodes = list;
        setSelector(selector);
    }

    public static void setCompiledMethod(CompiledMethod compiledMethod) {
        setCompilerUtil(new CompilerUtil(compiledMethod));
    }

    public static void setCompilerUtil(CompilerUtil compilerUtil) {
        ByteCodeUtil.compilerUtil = compilerUtil;
        setByteCodes(ByteCodeUtil.compilerUtil.compiledMethod.getSelector(), ByteCodeUtil.compilerUtil.byteCodes);
    }

    public static void setSelector(String selector) {
        ByteCodeUtil.selector = selector;
    }

    public static IntegerList byteCodes = new IntegerList();
    public static CompilerUtil compilerUtil;
    public static String selector = "<none>";
    public static IStdOut stdOut = TranscriptWindow.asStdOut();
}
