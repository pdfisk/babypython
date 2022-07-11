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

import net.babypython.client.python.parser.Visitor;
import net.babypython.client.vm.containers.lists.IntegerList;
import net.babypython.client.vm.containers.lists.StringList;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

public abstract class OperatorNode extends AstNode {

    public OperatorNode(String or_test, ParserRuleContext ctx, Visitor visitor) {
        super(or_test, ctx, visitor);
    }

    protected void addOperator(ParseTree operatorCtx) {
        if (operatorCtx instanceof TerminalNode) {
            operators.add(operatorCtx.toString());
            operatorTypes.add(((TerminalNode) operatorCtx).getSymbol().getType());
        }
    }

    protected void addOperators(ParserRuleContext parentCtx) {
        for (int i = 0; i < parentCtx.getChildCount(); i++)
            addOperator(parentCtx.getChild(i));
    }

    public void writeAttributes(StringList sw, int indent) {
        super.writeAttributes(sw, indent);
        for (int i = 0; i < this.operators.size(); i++) {
            String operator = this.operators.get(i);
            writeAttribute(sw, indent, "operator", operator);
        }
    }

    protected StringList operators = new StringList();
    protected IntegerList operatorTypes = new IntegerList();
}
