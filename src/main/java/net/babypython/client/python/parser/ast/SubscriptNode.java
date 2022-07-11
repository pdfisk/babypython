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
import net.babypython.client.python.parser.Visitor;

public class SubscriptNode extends AstNode {

    public SubscriptNode(Python3Parser.Subscript_Context ctx, Visitor visitor) {
        super(NodeConstants._subscript, ctx, visitor);
        subscriptContext = ctx;
    }

//    Expression generateSliceExpression() {
//        Object startExpression = getStartExpression();
//        Object stopExpression = getStopExpression();
//        Object stepExpression = getStepExpression();
//        return new SliceExpression(getStartExpression(), getStopExpression(), getStepExpression());
//    }

//    @Override
//    public Expression generateStatementWithCollection(ExpressionList statements) {
//        if (hasColon())
//            return generateSliceExpression();
//        return super.generateStatementWithCollection(statements);
//    }
//
//    Expression getStartExpression() {
//        if (hasDefaultStart())
//            return null;
//        if (getChildCount() > 0)
//            return getFirstChild().generateStatement();
//        return null;
//    }
//
//    Expression getStepExpression() {
//        if (hasSliceopNode())
//            return getLastChild().generateStatement();
//        return null;
//    }
//
//    Expression getStopExpression() {
//        if (hasDefaultStart()) {
//            if ((getChildCount() == 1 && getStepExpression()==null) || getChildCount() > 1)
//                return getFirstChild().generateStatement();
//        }
//        if ((getChildCount() == 2 && getStepExpression()==null) || getChildCount() > 2)
//            return getChild(1).generateStatement();
//        return null;
//    }

    boolean hasColon() {
        return subscriptContext.COLON() != null;
    }

    boolean hasSliceopNode() {
        return getChildCount() > 0 && hasColon() && getLastChild().isSliceop();
    }

    boolean hasDefaultStart() {
        return subscriptContext.getText().startsWith(":");
    }

    Python3Parser.Subscript_Context subscriptContext;
}
