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
package net.babypython.client.python.parser.ast;

import net.babypython.client.python.antlr.Python3Parser;
import net.babypython.client.python.constants.NodeConstants;
import net.babypython.client.python.constants.TokenTypes;
import net.babypython.client.python.parser.Visitor;
import net.babypython.client.vm.compiler.CompilerUtil;
import net.babypython.client.vm.containers.lists.StringList;

public class CompOpNode extends OperatorNode {

    public CompOpNode(Python3Parser.Comp_opContext ctx, Visitor visitor) {
        super(NodeConstants._comp_op, ctx, visitor);
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
            case TokenTypes.LESS_THAN:
                compilerUtil.opCompLt();
                break;
            case TokenTypes.LT_EQ:
                compilerUtil.opCompLe();
                break;
            case TokenTypes.EQUALS:
                compilerUtil.opCompEq();
                break;
            case TokenTypes.GT_EQ:
                compilerUtil.opCompGe();
                break;
            case TokenTypes.GREATER_THAN:
                compilerUtil.opCompGt();
                break;
            default:
                info("unknown operator", operatorType);
                break;
        }
    }

    @Override
    public boolean isCompOp() {
        return true;
    }

    public void writeAttributes(StringList sw, int indent) {
        super.writeAttributes(sw, indent);
        for (String operator : operators)
            writeAttribute(sw, indent, "operator", operator);
    }

}
