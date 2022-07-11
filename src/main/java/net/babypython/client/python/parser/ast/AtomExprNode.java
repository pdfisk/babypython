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
import net.babypython.client.vm.containers.lists.StringList;
import net.babypython.client.vm.util.StringUtil;

import java.util.ArrayList;

public class AtomExprNode extends AstNode {

    public AtomExprNode(Python3Parser.Atom_exprContext ctx, Visitor visitor) {
        super(NodeConstants._atom_expr, ctx, visitor);
        atom_exprContext = ctx;
//        args = getArgs();
        names = getNames();
        generateTrailers();
    }

//    @Override
//    public Expression generateStatementWithCollection(ExpressionList statements) {
//        Expression expression = generateFirstPass();
//        if (expression == null)
//            expression = super.generateStatementWithCollection(statements);
//        return expression;
//    }

//    Expression generateFirstPass() {
//        Expression primaryExpression = generatePrimaryExpression();
//        if (primaryExpression == null)
//            return null;
//        if (isAttributeAccessor())
//            return generateAttributeAccessor(primaryExpression);
//        if (hasIndexList())
//            return generateStatementWithIndices(primaryExpression);
//        if (isFunctionOrConstructor(primaryExpression))
//            return generateFunctionOrConstructorCall((VariableExpression) primaryExpression);
//        if (isMethod())
//            return generateMethodCall(primaryExpression);
//        return primaryExpression;
//    }

//    Expression generateStatementWithIndices(Expression primaryExpression) {
//        setIsLhs(false);
//        ExpressionList indices = new ExpressionList();
//        for (int i = 1; i < getChildCount(); i++)
//            indices.add(getChild(i).generateStatement());
//        return new IndexExpression(primaryExpression, indices);
//    }

//    Expression generatePrimaryExpression() {
//        Expression primaryExpression = null;
//        if (isList())
//            primaryExpression = generateListExpression();
//        else if (isTuple())
//            primaryExpression = generateTupleExpression();
//        else if (isDict())
//            primaryExpression = generateDictExpression();
//        else if (isSet())
//            primaryExpression = generateSetExpression();
//        else if (isLiteral())
//            primaryExpression = generateLiteralExpression();
//        else if (isVariable())
//            primaryExpression = generateVariableExpression();
//        return primaryExpression;
//    }

    public void generateByteCode(CompilerUtil compilerUtil) {
        generateLineNumber(compilerUtil);
        if (isProperty())
            generateProperty(compilerUtil);
        else if (isMethod())
            generateMethod(compilerUtil);
        else if (isFunctionCallOrClassInstantiation()) {
            generateFunctionCallOrClassInstantiation(compilerUtil);
        } else
            super.generateByteCode(compilerUtil);
    }

    void generateMethod(CompilerUtil compilerUtil) {
        getChild(0).generateByteCode(compilerUtil);
        StringList dotNames = getDotNames();
        for (String propertyName : dotNames)
            compilerUtil.opPushProperty(propertyName);
        int nArgs = generateTrailerArgs(compilerUtil);
        compilerUtil.opPythonCallFunction(nArgs);
    }

    void generateProperty(CompilerUtil compilerUtil) {
        getChild(0).generateByteCode(compilerUtil);
        StringList dotNames = getDotNames();
        for (String propertyName : dotNames)
            compilerUtil.opPushProperty(propertyName);
    }

    boolean isFunctionCallOrClassInstantiation() {
        return hasFirstNameChild() && hasLastArgList();
    }

    boolean isMethod() {
        return isPropertyOrMethod() && hasLastArgList();
    }

    boolean isProperty() {
        return isPropertyOrMethod() && !hasLastArgList();
    }

    boolean isPropertyOrMethod() {
        return getChildCount() > 1 && hasFirstNameChildOrLiteralChild() && hasDotNames();
    }

    void generateFunctionCallOrClassInstantiation(CompilerUtil compilerUtil) {
        String functionOrClassName = getName();
        int nArgs = generateTrailerArgs(compilerUtil);
        if (StringUtil.startsWithLowerCase(functionOrClassName))
            generateFunctionCall(compilerUtil, functionOrClassName, nArgs);
        else
            generateClassInstantiation(compilerUtil, functionOrClassName, nArgs);
    }

    int generateTrailerArgs(CompilerUtil compilerUtil) {
        int nArgs = 0;
        try {
            if (trailerNodes != null && trailerNodes.size() > 0) {
                for (int i = trailerNodes.size() - 1; i >= 0; i--) {
                    AstNode trailerNode = trailerNodes.get(i);
                    if (trailerNode == null)
                        continue;
                    trailerNode.generateByteCode(compilerUtil);
                    nArgs += trailerNodes.get(i).getArgCount();
                }
            }
        } catch (Exception e) {
        }
        return nArgs;
    }

    void generateClassInstantiation(CompilerUtil compilerUtil, String clsName, int nArgs) {
        compilerUtil.opPythonInstantiateClass(clsName, nArgs);
    }

    void generateFunctionCall(CompilerUtil compilerUtil, String fnName, int nArgs) {
        compilerUtil.opPythonCallNamedFunction(fnName, nArgs);
    }

    void generateTrailers() {
        trailerNodes = new ArrayList<>();
        for (Python3Parser.TrailerContext trailerContext : atom_exprContext.trailer())
            trailerNodes.add(new TrailerNode(trailerContext, visitor));
    }

//    Expression generateAttributeAccessor(Expression primaryExpression) {
//        StringList names = getTrailerNames();
//        return new AttributeExpression(primaryExpression, names);
//    }

    StringList getTrailerNames() {
        StringList names = new StringList();
        for (TrailerNode trailerNode : trailerNodes) {
            if (!trailerNode.isName())
                break;
            names.add(trailerNode.getName());
        }
        return names;
    }

//    Expression generateDictExpression() {
//        ExpressionList items;
//        if (getFirstChild().isAtom())
//            items = ((AtomNode) getFirstChild()).getDictOrSetItems();
//        else
//            items = new ExpressionList();
//        return new DictionaryExpression(items);
//    }

//    Expression generateListExpression() {
//        ExpressionList items;
//        if (getFirstChild().isAtom())
//            items = ((AtomNode) getFirstChild()).getListItems();
//        else
//            items = new ExpressionList();
//        return new ListExpression(items);
//    }

//    Expression generateLiteralExpression() {
//        return ((AtomNode) getFirstChild()).generateLiteral();
//    }

//    Expression generateSetExpression() {
//        ExpressionList items;
//        if (getFirstChild().isAtom())
//            items = ((AtomNode) getFirstChild()).getDictOrSetItems();
//        else
//            items = new ExpressionList();
//        return new SetExpression(items);
//    }

//    Expression generateTupleExpression() {
//        ExpressionList items;
//        if (getFirstChild().isAtom())
//            items = ((AtomNode) getFirstChild()).getListItems();
//        else
//            items = new ExpressionList();
//        return new TupleExpression(items);
//    }

//    Expression generateVariableExpression() {
//        return new VariableExpression(getFirstChild().getName());
//    }

//    Expression generateFunctionOrConstructorCall(VariableExpression variableExpression) {
//        return new FunctionOrConstructorExpression(variableExpression, getName(), args);
//    }

//    Expression generateMethodCall(Expression primaryExpression) {
//        int start = (primaryExpression instanceof VariableExpression) ? 1 : 0;
//        StringList names2 = new StringList();
//        for (int i = start; i < names.size() - 1; i++)
//            names2.add(names.get(i));
//        AttributeExpression attributeExpression = new AttributeExpression(primaryExpression, names2);
//        return new MethodCallExpression(attributeExpression, names.get(names.size() - 1), args);
//    }

//    ExpressionList getArgs() {
//        if (!hasArgs())
//            return null;
//        return getLastChild().asArguments();
//    }

    public String getName() {
        if (!hasFirstNameChild())
            return super.getName();
        StringList nameList = new StringList();
        for (AstNode child : children)
            if (child.isName())
                nameList.add(child.getName());
        return String.join(".", nameList);
    }

    StringList getNames() {
        StringList names = new StringList();
        for (int i = 0; i < getChildCount() - 1; i++) {
            AstNode child = getChild(i);
            if (child.isName())
                names.add(child.getName());
        }
        return names;
    }

    public boolean hasDotNames() {
        int lastIndex = getChildCount() - 1;
        if (hasLastArgList())
            lastIndex--;
        if (lastIndex < 1)
            return false;
        boolean result = true;
        for (int i = 1; i <= lastIndex; i++) {
            if (!hasDotNameAt(i))
                result = false;
        }
        return result;
    }

    StringList getDotNames() {
        StringList dotNames = new StringList();
        int lastIndex = getChildCount() - 1;
        if (hasLastArgList())
            lastIndex--;
        if (lastIndex < 1)
            return dotNames;
        for (int i = 1; i <= lastIndex; i++) {
            AstNode astNode = getChild(i);
            if (astNode.isDotName())
                dotNames.add(astNode.getName());
        }
        return dotNames;
    }

    public boolean hasDotNameAt(int index) {
        if (getChildCount() <= index)
            return false;
        return getChild(index).isDotName();
    }

    @Override
    public boolean hasLastArgList() {
        if (getChildCount() < 1)
            return false;
        return getLastChild().isArglist();
    }

    public boolean hasFirstNameChildOrLiteralChild() {
        return hasFirstNameChild() || hasFirstLiteralChild();
    }

    public boolean hasFirstNameChild() {
        return getChildCount() > 0 && getChild(0).isName();
    }

    public boolean hasFirstLiteralChild() {
        return getChildCount() > 0 && getChild(0).isLiteral();
    }

    public boolean hasIndexList() {
        if (getChildCount() < 1)
            return false;
        return getLastChild().isIndexList();
    }

    public boolean hasTrailer() {
        return atom_exprContext.trailer().size() > 0;
    }

    boolean isAttributeAccessor() {
        return isAttributeAccessor(trailerNodes.size());
    }

    boolean isAttributeAccessor(int maxIndex) {
        if (maxIndex == 0)
            return false;
        for (int i = 0; i < maxIndex; i++) {
            if (!trailerNodes.get(i).isProperty())
                return false;
        }
        return true;
    }

    @Override
    public boolean isDict() {
        return getChildCount() > 0 && getFirstChild().isDict();
    }

//    boolean isFunctionOrConstructor(Expression primaryExpression) {
//        return primaryExpression instanceof VariableExpression && names.size() == 1;
//    }

    @Override
    public boolean isList() {
        return getChildCount() > 0 && getFirstChild().isList();
    }

    @Override
    public boolean isLiteral() {
        return getChildCount() > 0 && getFirstChild().isAtom() && getFirstChild().isLiteral();
    }

    public boolean isMethod2() {
        return hasTrailer() && names.size() > 0;
    }

    public boolean isProperty2() {
        return getChildCount() > 0 && getLastChild().isName();
    }

    @Override
    public boolean isSet() {
        return getChildCount() > 0 && getFirstChild().isSet();
    }

    @Override
    public boolean isTuple() {
        return getChildCount() > 0 && getFirstChild().isTuple();
    }

    boolean isVariable() {
        return getChildCount() > 0 && getFirstChild().isName();
    }

    //    ExpressionList args;
    Python3Parser.Atom_exprContext atom_exprContext;
    StringList names;
    ArrayList<TrailerNode> trailerNodes;
}
