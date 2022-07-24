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
package net.babypython.client.python.compiler;

import net.babypython.client.ui.constants.ErrorMessages;
import net.babypython.client.python.parser.Parser;
import net.babypython.client.python.parser.Visitor;
import net.babypython.client.python.parser.ast.AstNode;
import net.babypython.client.vm.classes.special.classes.UndefinedClass;
import net.babypython.client.vm.compiler.CompilerUtil;
import net.babypython.client.vm.core.VClass;
import net.babypython.client.ui.util.Logable;
import net.babypython.client.vm.vm.context.CompiledMethod;
import org.antlr.v4.runtime.tree.ParseTree;

public class PythonCompiler extends Logable {

    public static CompiledMethod compile(String src) {
        AstNode rootNode = parseToAstWithJava(src);
        if (rootNode == null)
            return null;
        VClass compilingClass = UndefinedClass.getInstance();
        CompiledMethod compiledMethod = new CompiledMethod(compilingClass);
        compiledMethod.setIsCompiled();
        CompilerUtil compilerUtil = new CompilerUtil(compiledMethod);
        rootNode.generateByteCode(compilerUtil);
        compilerUtil.op_RETURN_VALUE();
        return compiledMethod;
    }

    public static AstNode parseToAstWithJava(String src) {
        try {
            ParseTree parseTree = Parser.parseSrc(src);
            Visitor visitor = new Visitor();
            return visitor.visit(parseTree);
        } catch (Exception e) {
            Logable.err(ErrorMessages.ParsingError, e.getMessage());
            return null;
        }
    }

    public static String compileToTreeString(String src) {
        AstNode rootNode = parseToAstWithJava(src);
        return rootNode.toTreeString();
    }

}
