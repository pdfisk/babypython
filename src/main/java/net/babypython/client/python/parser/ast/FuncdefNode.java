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

import net.babypython.client.vm.containers.lists.StringList;
import net.babypython.client.python.antlr.Python3Parser;
import net.babypython.client.python.constants.NodeConstants;
import net.babypython.client.python.core.user.PythonUserFunction;
import net.babypython.client.python.parser.Visitor;
import net.babypython.client.vm.compiler.CompilerUtil;
import net.babypython.client.vm.vm.context.CompiledMethod;

public class FuncdefNode extends AstNode {

    public FuncdefNode(Python3Parser.FuncdefContext ctx, Visitor visitor) {
        super(NodeConstants._funcdef, ctx, visitor);
        funcdefContext = ctx;
        if (ctx.NAME() != null)
            this.fnName = ctx.NAME().getText();
        buildParametersAndSuite();
    }

    void buildParametersAndSuite() {
        if (getChildCount() != 2)
            return;
        AstNode parametersChild = getChild(0);
        AstNode suiteChild = getChild(1);
        if (!parametersChild.isParameters())
            return;
        if (!suiteChild.isSuite())
            return;
        parametersNode = (ParametersNode) parametersChild;
        suiteNode = (SuiteNode) suiteChild;
    }

    @Override
    public void generateByteCode(CompilerUtil compilerUtil) {
        try {
            generateLineNumber(compilerUtil);
            CompiledMethod fnCompiledMethod = new CompiledMethod();
            CompilerUtil fnCompilerUtil = new CompilerUtil(fnCompiledMethod);
            parametersNode.generateByteCode(fnCompilerUtil);
            suiteNode.generateByteCode(fnCompilerUtil);
            PythonUserFunction userFunction = new PythonUserFunction(getFnName(), fnCompiledMethod);
            compilerUtil.opDefFunction(userFunction);
        } catch (Exception e) {
            info("FuncdefNode", e.getMessage());
        }
    }

    public String getFnName() {
        return fnName;
    }

    @Override
    public boolean isFuncdef() {
        return true;
    }

    public void setFnName(String name) {
        this.fnName = name;
    }

    @Override
    public void setIsAsync() {
        isAsync = true;
    }

    public void writeAttributes(StringList sw, int indent) {
        super.writeAttributes(sw, indent);
        writeAttribute(sw, indent, "fnName", fnName);
    }

    String fnName = null;
    Python3Parser.FuncdefContext funcdefContext;
    ParametersNode parametersNode = null;
    SuiteNode suiteNode = null;
}
