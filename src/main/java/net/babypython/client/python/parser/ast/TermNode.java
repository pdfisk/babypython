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
package net.babypython.client.python.parser.ast;

import net.babypython.client.python.antlr.Python3Parser;
import net.babypython.client.python.constants.NodeConstants;
import net.babypython.client.python.constants.TokenTypes;
import net.babypython.client.python.parser.Visitor;
import net.babypython.client.vm.compiler.CompilerUtil;

public class TermNode extends OperatorNode {

    public TermNode(Python3Parser.TermContext ctx, Visitor visitor) {
        super(NodeConstants._term, ctx, visitor);
        addOperators(ctx);
    }

    public void generateByteCode(CompilerUtil compilerUtil) {
        generateLineNumber(compilerUtil);
        super.generateByteCode(compilerUtil);
        for (int operatorType : operatorTypes)
            generateBinaryOperator(operatorType, compilerUtil);
    }

    void generateBinaryOperator(int operatorType, CompilerUtil compilerUtil) {
        switch (operatorType) {
            case TokenTypes.STAR:
                compilerUtil.op_BINARY_MULTIPLY();
                break;
            case TokenTypes.DIV:
                compilerUtil.op_BINARY_TRUE_DIVIDE();
                break;
            case TokenTypes.MOD:
                compilerUtil.op_BINARY_MODULO();
                break;
            default:
                info("unknown operator", operatorType);
                break;
        }
    }

    @Override
    protected boolean selectChild(AstNode child) {
        return child.isFactor();
    }

    @Override
    public boolean isTerm() {
        return true;
    }

}
