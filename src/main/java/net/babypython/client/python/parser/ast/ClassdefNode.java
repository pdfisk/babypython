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
import net.babypython.client.vm.containers.lists.StringList;

public class ClassdefNode extends AstNode {

    public ClassdefNode(Python3Parser.ClassdefContext ctx, Visitor visitor) {
        super(NodeConstants._classdef, ctx, visitor);
        classdefContext = ctx;
        if (ctx.NAME() != null)
            clsName = ctx.NAME().getText();
    }

//    @Override
//    public Expression generateStatementWithCollection(ExpressionList statements) {
//        buildBases();
//        buildSuite();
//        int a = classdefContext.start.getStartIndex();
//        int b = classdefContext.stop.getStopIndex();
//        Interval interval = new Interval(a, b);
//        String src = classdefContext.start.getInputStream().getText(interval);
//        PyWorkspace.getInstance().setGlobalValue(clsName + "__src__", src);
//        return new ClassDefStatement(clsName, baseNames, suiteStatements, src);
//    }

    void buildBases() {
        baseNames = new StringList();
        ArglistNode arglistNode = null;
        for (int i = 0; i < getChildCount(); i++) {
            AstNode node = getChild(i);
            if (node.isArglist()) {
                arglistNode = (ArglistNode) node;
                break;
            }
        }
        if (arglistNode != null) {
            for (int i = 0; i < arglistNode.getChildCount(); i++) {
                AstNode child = arglistNode.getChild(i);
                if (!child.isArgument())
                    continue;
                ArgumentNode argumentNode = (ArgumentNode) child;
                String clsName = argumentNode.getText();
                if (!baseNames.contains(clsName))
                    baseNames.add(clsName);
            }
        }
        if (!baseNames.isEmpty())
            baseNames.add("object");
    }

    void buildSuite() {
        SuiteNode suiteNode = null;
        for (int i = 0; i < getChildCount(); i++) {
            AstNode node = getChild(i);
            if (node.isSuite()) {
                suiteNode = (SuiteNode) node;
                break;
            }
        }
//        if (suiteNode == null) {
//            suiteStatements = new ExpressionList();
//            return;
//        }
//        suiteStatements = suiteNode.generateStatements();
    }

    @Override
    public boolean isClassdef() {
        return true;
    }

    public void writeAttributes(StringList sw, int indent) {
        super.writeAttributes(sw, indent);
        writeAttribute(sw, indent, "clsName", clsName);
    }

    StringList baseNames;
    Python3Parser.ClassdefContext classdefContext;
    String clsName = null;
//    ExpressionList suiteStatements;
}
