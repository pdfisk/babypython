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

public class ForStmtNode extends AstNode {

    public ForStmtNode(Python3Parser.For_stmtContext ctx, Visitor visitor) {
        super(NodeConstants._for_stmt, ctx, visitor);
    }

//    @Override
//    public Expression generateStatementWithCollection(ExpressionList statements) {
//        if (getChildCount() != 3) {
//            info("ForStmtNode generateStatement childCount error:", getChildCount());
//            return super.generateStatementWithCollection(statements);
//        }
//        String varName = getFirstChild().getLastMostChild().getName();
//        AstNode iterableNode = getChild(1);
//        if (!getChild(2).isSuite()) {
//            info("missing suite:", getChild(2).getClass().getSimpleName());
//            return super.generateStatementWithCollection(statements);
//        }
//        SuiteNode suiteNode = (SuiteNode) getChild(2);
//        Expression iterableExpr = iterableNode.generateStatement();
//        iterableExpr.setLineNumber(iterableNode.getLineNumber());
//        IterationExpression iterationExpression = new IterationExpression(varName, iterableExpr);
//        iterationExpression.setLineNumber(iterableNode.getLineNumber());
//        ResetStatement resetStatement = new ResetStatement(iterationExpression);
//        resetStatement.setLineNumber(iterableNode.getLineNumber());
//        statements.add(resetStatement);
//        int loop = statements.size();
//        statements.add(iterationExpression);
//        suiteNode.generateStatementWithCollection(statements);
//        GoToStatement goToStatement = new GoToStatement(loop);
//        int goToLineNumber = iterableNode.getLineNumber();
//        if (suiteNode.suiteStatements.size() > 0)
//            goToLineNumber = suiteNode.suiteStatements.get(suiteNode.suiteStatements.size() - 1).getLineNumber() + 1;
//        goToStatement.setLineNumber(goToLineNumber);
//        statements.add(goToStatement);
//        int end = statements.size();
//        iterationExpression.setNextStatement(end);
//        suiteNode.setLoopTargets(loop);
//        suiteNode.setEndTargets(end);
//        return null;
//    }

    @Override
    public boolean isForStmt() {
        return true;
    }

}
