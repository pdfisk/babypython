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

public class SuiteNode extends AstNode {

    public SuiteNode(Python3Parser.SuiteContext ctx, Visitor visitor) {
        super(NodeConstants._suite, ctx, visitor);
//        suiteStatements = new ExpressionList();
    }

//    @Override
//    public Expression generateStatementWithCollection(ExpressionList statements) {
//        int markStart = statements.size();
//        Expression statement = null;
//        for (int i = 0; i < children.size(); i++) {
//            statement = getChild(i).generateStatementWithCollection(statements);
//            if (statement != null && statements != null)
//                statements.add(statement);
//        }
//        int markEnd = statements.size();
//        for (int i = markStart; i < markEnd; i++)
//            suiteStatements.add(statements.get(i));
//        if (statement != null && statements == null)
//            suiteStatements.add(statement);
//        return statement;
//    }

    @Override
    public boolean isSuite() {
        return true;
    }

//    public void setEndTargets(int target) {
//        for (int i = 0; i < suiteStatements.size(); i++) {
//            Expression statement = suiteStatements.get(i);
//            statement.setEndTarget(target);
//        }
//    }

//    public void setLoopTargets(int target) {
//        for (int i = 0; i < suiteStatements.size(); i++) {
//            Expression statement = suiteStatements.get(i);
//            statement.setLoopTarget(target);
//        }
//    }

//    ExpressionList suiteStatements;
}
