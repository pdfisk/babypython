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
import net.babypython.client.vm.containers.lists.AstList;
import net.babypython.client.python.parser.Visitor;
import net.babypython.client.vm.compiler.CompilerUtil;
import net.babypython.client.vm.containers.lists.IntegerList;
import net.babypython.client.ui.util.Logable;

public class IfStmtNode extends AstNode {

    public IfStmtNode(Python3Parser.If_stmtContext ctx, Visitor visitor) {
        super(NodeConstants._if_stmt, ctx, visitor);
        jumpToEndPatches = new IntegerList();
        buildTestsAndSuites();
    }

    @Override
    public void generateByteCode(CompilerUtil compilerUtil) {
        generateLineNumber(compilerUtil);
        if (testNodes.size() >= 1 && suiteNodes.size() >= testNodes.size())
            generateIfStmt(compilerUtil);
        else
            super.generateByteCode(compilerUtil);
    }

    void generateIfStmt(CompilerUtil compilerUtil) {
        generateLineNumber(compilerUtil);
        for (int i = 0; i < testNodes.size(); i++) {
            testNodes.get(i).generateByteCode(compilerUtil);
            compilerUtil.op_POP_JUMP_IF_FALSE(0);
            int jumpIfFalsePatch = compilerUtil.mark();
            suiteNodes.get(i).generateByteCode(compilerUtil);
            if (i < testNodes.size() - 1 || elseNode != null) {
                compilerUtil.op_JUMP_ABSOLUTE(0);
                jumpToEndPatches.add(compilerUtil.mark());
            }
            compilerUtil.patch(jumpIfFalsePatch - 1, compilerUtil.mark());
        }
        if (elseNode != null)
            elseNode.generateByteCode(compilerUtil);
        int endTarget = compilerUtil.mark();
        for (int jumpToEndPatch : jumpToEndPatches)
            compilerUtil.patch(jumpToEndPatch - 1, endTarget);
    }

    void buildTestsAndSuites() {
        for (int i = 0; i < getChildCount(); i++) {
            AstNode child = getChild(i);
            if (child.isTest())
                testNodes.add(child);
            else if (child.isSuite()) {
                if (suiteNodes.size() < testNodes.size())
                    suiteNodes.add(child);
                else
                    elseNode = child;
            } else
                Logable.err("IfStmt unknown child: " + child.getClass().getSimpleName());
        }
    }

    @Override
    public boolean isIfStmt() {
        return true;
    }

    IntegerList jumpToEndPatches;
    AstNode elseNode = null;
    AstList suiteNodes = new AstList();
    AstList testNodes = new AstList();
}
