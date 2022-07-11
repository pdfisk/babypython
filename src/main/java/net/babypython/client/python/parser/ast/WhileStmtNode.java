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
import net.babypython.client.vm.compiler.CompilerUtil;

public class WhileStmtNode extends AstNode {

    public WhileStmtNode(Python3Parser.While_stmtContext ctx, Visitor visitor) {
        super(NodeConstants._while_stmt, ctx, visitor);
    }

    @Override
    public void generateByteCode(CompilerUtil compilerUtil) {
        generateLineNumber(compilerUtil);
        if (getChildCount() == 2)
            generateWhileStmt(compilerUtil);
        else
            super.generateByteCode(compilerUtil);
    }

    void generateWhileStmt(CompilerUtil compilerUtil) {
        generateLineNumber(compilerUtil);
        int loop = compilerUtil.mark();
        getChild(0).generateByteCode(compilerUtil);
        compilerUtil.opJumpIfFalse(0);
        int jumpIfFalsePatch = compilerUtil.mark();
        getChild(1).generateByteCode(compilerUtil);
        compilerUtil.opJumpTo(loop);
        compilerUtil.patch(jumpIfFalsePatch - 1, compilerUtil.mark());
    }

    @Override
    public boolean isWhileStmt() {
        return true;
    }

}
