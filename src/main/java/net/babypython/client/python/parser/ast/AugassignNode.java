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
import net.babypython.client.python.constants.JavaConstants;
import net.babypython.client.python.constants.NodeConstants;
import net.babypython.client.python.constants.TokenTypes;
import net.babypython.client.python.parser.Visitor;
import net.babypython.client.vm.containers.lists.StringList;

public class AugassignNode extends AstNode {

    public AugassignNode(Python3Parser.AugassignContext ctx, Visitor visitor) {
        super(NodeConstants._augassign, ctx, visitor);
        setAugOp(ctx);
    }

    void setAugOp(Python3Parser.AugassignContext ctx) {
        int tokenType = ctx.start.getType();
        switch (tokenType) {
            case TokenTypes.ADD_ASSIGN:
                augOp = JavaConstants.INPLACE_ADD;
                break;
            case TokenTypes.AND_ASSIGN:
                augOp = JavaConstants.INPLACE_AND;
                break;
            case TokenTypes.DIV_ASSIGN:
                augOp = JavaConstants.INPLACE_TRUE_DIVIDE;
                break;
            case TokenTypes.IDIV_ASSIGN:
                augOp = JavaConstants.INPLACE_FLOOR_DIVIDE;
                break;
            case TokenTypes.LEFT_SHIFT_ASSIGN:
                augOp = JavaConstants.INPLACE_LSHIFT;
                break;
            case TokenTypes.MOD_ASSIGN:
                augOp = JavaConstants.INPLACE_MODULO;
                break;
            case TokenTypes.MULT_ASSIGN:
                augOp = JavaConstants.INPLACE_MULTIPLY;
                break;
            case TokenTypes.OR_ASSIGN:
                augOp = JavaConstants.INPLACE_OR;
                break;
            case TokenTypes.POWER_ASSIGN:
                augOp = JavaConstants.INPLACE_POWER;
                break;
            case TokenTypes.RIGHT_SHIFT_ASSIGN:
                augOp = JavaConstants.INPLACE_RSHIFT;
                break;
            case TokenTypes.SUB_ASSIGN:
                augOp = JavaConstants.INPLACE_SUBTRACT;
                break;
            default:
                augOp = JavaConstants.INPLACE_UNKNOWN;
        }
    }

//    @Override
//    public Expression generateStatementWithCollection(ExpressionList statements) {
//        for (int i = 0; i < getChildCount(); i++) {
//            AstNode child = getChild(i);
//        }
//        return super.generateStatementWithCollection(statements);
//    }

    public String getAugOp() {
        return augOp;
    }

    @Override
    public boolean isAugassign() {
        return true;
    }


    public void writeAttributes(StringList sw, int indent) {
        super.writeAttributes(sw, indent);
        writeAttribute(sw, indent, "augOp", augOp);
    }

    String augOp = null;
}
