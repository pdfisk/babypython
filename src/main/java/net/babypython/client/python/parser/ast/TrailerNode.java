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

public class TrailerNode extends AstNode {

    public TrailerNode(Python3Parser.TrailerContext ctx, Visitor visitor) {
        super(NodeConstants._trailer, ctx, visitor);
        trailerContext = ctx;
        if (ctx.OPEN_PAREN() != null)
            isArgList = true;
        if (ctx.DOT() != null)
            isDot = true;
        if (ctx.OPEN_BRACK() != null)
            isIndexList = true;
        if (ctx.NAME() != null)
            name = ctx.NAME().toString();
    }

//    @Override
//    public ExpressionList asArguments() {
//        if (hasChildren())
//            return getLastChild().asArguments();
//        return super.asArguments();
//    }

    @Override
    public int getArgCount() {
        if (isArglist())
            return getFirstChild().getArgCount();
        return super.getArgCount();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isArglist() {
        return isArgList;
    }

    public boolean isArgs() {
        return isOpenParen();
    }

    @Override
    public boolean isDot() {
        return isDot;
    }

    @Override
    public boolean isDotName() {
        return isDot() && isName();
    }

    @Override
    public boolean isIndexList() {
        return isIndexList;
    }

    @Override
    public boolean isName() {
        return name != null;
    }

    public boolean isOpenParen() {
        return trailerContext.OPEN_PAREN() != null;
    }

    public boolean isProperty() {
        return isName();
    }

    @Override
    public boolean isTrailer() {
        return true;
    }

    @Override
    public String toString() {
        return "TrailerNode isDot:" + isDot() + " isName: " + isName() + " isOpenParen: " + isOpenParen();
    }

    public void writeAttributes(StringList sw, int indent) {
        super.writeAttributes(sw, indent);
        if (isArgList)
            writeAttribute(sw, indent, "isArgList", isArgList + "");
        if (isDot)
            writeAttribute(sw, indent, "isDot", isDot + "");
        if (isIndexList)
            writeAttribute(sw, indent, "isIndexList", isIndexList + "");
        if (name != null)
            writeAttribute(sw, indent, "name", name);
        writeAttribute(sw, indent, "trailerType", trailerType);
    }

    boolean isArgList = false;
    boolean isDot = false;
    boolean isIndexList = false;
    String name = null;
    Python3Parser.TrailerContext trailerContext;
    String trailerType = "<none>";
}
