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
import net.babypython.client.python.parser.Visitor;
import net.babypython.client.vm.compiler.CompilerUtil;
import net.babypython.client.vm.containers.lists.StringList;
import org.antlr.v4.runtime.tree.TerminalNode;

public class ExprStmtNode extends AstNode {

    public ExprStmtNode(Python3Parser.Expr_stmtContext ctx, Visitor visitor) {
        super(NodeConstants._expr_stmt, ctx, visitor);
        addAssignOps(ctx);
    }

    public void addAssignOps(Python3Parser.Expr_stmtContext ctx) {
        for (TerminalNode node : ctx.ASSIGN())
            assignOps.add(node.toString());
    }

    @Override
    public void generateByteCode(CompilerUtil compilerUtil) {
        generateLineNumber(compilerUtil);
        if (isAssignmentStatement())
            generateByteCodeAssignStatement(compilerUtil);
        else
            super.generateByteCode(compilerUtil);
    }

    protected void generateByteCodeAssignStatement(CompilerUtil compilerUtil) {
        generateLineNumber(compilerUtil);
        AstNode valueNode = getChild(getChildCount() - 1);
        valueNode.generateByteCode(compilerUtil);
        for (int i = getChildCount() - 2; i >= 0; i--) {
            AstNode nameNode = getChild(i).asNameNode();
            if (nameNode == null)
                continue;
            if (i > 0)
                compilerUtil.op_DUP_TOP();
            compilerUtil.op_STORE_NAME(nameNode.getName());
        }
    }

//    @Override
//    public Expression generateStatementWithCollection(ExpressionList statements) {
//        if (getChildCount() < 2)
//            return super.generateStatementWithCollection(statements);
//        else if (isAssignmentStatement())
//            return generateAssignStatement();
//        else if (isAugAssignStatement())
//            return generateAugAssignStatement();
//        else return super.generateStatementWithCollection(statements);
//    }

    boolean isAssignmentStatement() {
        return assignOps.size() > 0;
    }

    boolean isAugAssignStatement() {
        if (getChildCount() != 3)
            return false;
        return getChild(1).isAugassign();
    }

//    Expression generateAugAssignStatement() {
//        AstNode lhsNode = getFirstChild().getLastMostChild();
//        Expression lhsExpression = lhsNode.generateStatement();
//        String lhsName = lhsNode.getName();
//        Expression valueStatement = getLastChild().generateStatement();
//        String augOp = ((AugassignNode) getChild(1)).getAugOp();
//        Expression rhsExpression = generateAugAssignRhs(lhsName, augOp, valueStatement);
//        if (rhsExpression == null)
//            return null;
//        ExpressionList lhsExpressions = new ExpressionList();
//        lhsExpressions.add(lhsExpression);
//        return new AssignExpression(lhsExpressions, rhsExpression);
//    }

//    Expression generateAugAssignRhs(String varName, String augOp, Expression valueExpression) {
//        VariableExpression variableExpression = new VariableExpression(varName);
//        String op = augOp.substring(0, augOp.length() - 1);
//        switch (op) {
//            case "+":
//            case "-":
//                return new AddExpression(op, variableExpression, valueExpression);
//            case "*":
//            case "/":
//            case "%":
//            case "//":
//            case "**":
//                return new MultExpression(op, variableExpression, valueExpression);
//            default:
//                return null;
//        }
//    }

//    Expression generateAssignStatement() {
//        AstNode atomOrLastChild = getChild(getChildCount() - 1);
//        Expression valueStatement = atomOrLastChild.generateStatement();
//        ExpressionList lhsExpressions = new ExpressionList();
//        for (int i = getChildCount() - 2; i >= 0; i--) {
//            AstNode child = getChild(i);
//            child.setIsLhs(true);
//            Expression lhsStatement = child.generateStatement();
//            if (child != null)
//                lhsExpressions.add(lhsStatement);
//            else
//                info("no statement generated");
//        }
//        return new AssignExpression(lhsExpressions, valueStatement);
//    }

    public void writeAttributes(StringList sw, int indent) {
        super.writeAttributes(sw, indent);
        for (int i = 0; i < this.assignOps.size(); i++) {
            String assignOp = this.assignOps.get(i);
            writeAttribute(sw, indent, "assignOp", assignOp);
        }
    }

    StringList assignOps = new StringList();
}
