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
import net.babypython.client.ui.util.Logable;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

public class AtomNode extends AstNode {

    public AtomNode(Python3Parser.AtomContext ctx, Visitor visitor) {
        super(NodeConstants._atom, ctx, visitor);
        atomCtx = ctx;
        setTokenTextAndType(ctx);
        addChildren(ctx, visitor);
    }

    @Override
    public AstNode asNameNode() {
        if (isName())
            return this;
        return super.asNameNode();
    }

    @Override
    public boolean isAtom() {
        return true;
    }

    protected boolean isCloseBrace() {
        return atomCtx.CLOSE_BRACE() != null;
    }

    protected boolean isCloseBrack() {
        return atomCtx.CLOSE_BRACK() != null;
    }

    protected boolean isCloseParen() {
        return atomCtx.CLOSE_PAREN() != null;
    }

    protected boolean hasDictOrSetMaker() {
        return atomCtx.dictorsetmaker() != null;
    }

    protected boolean isFalse() {
        return atomCtx.FALSE() != null;
    }

    protected boolean isEllipsis() {
        return atomCtx.ELLIPSIS() != null;
    }

    @Override
    public boolean isName() {
        return tokenType == "name";
    }

    protected boolean isNone() {
        return atomCtx.NONE() != null;
    }

    protected boolean isNumber() {
        return atomCtx.NUMBER() != null;
    }

    protected boolean isOpenBrace() {
        return atomCtx.OPEN_BRACE() != null;
    }

    protected boolean isOpenBrack() {
        return atomCtx.OPEN_BRACK() != null;
    }

    protected boolean isOpenParen() {
        return atomCtx.OPEN_PAREN() != null;
    }

    protected boolean isString() {
        return atomCtx.STRING() != null && atomCtx.STRING().size() > 0;
    }

    protected boolean hasCommas() {
        if (atomCtx.testlist_comp() == null)
            return false;
        if (atomCtx.testlist_comp().COMMA() == null)
            return false;
        return atomCtx.testlist_comp().COMMA().size() > 0;
    }

    protected boolean hasColons() {
        Python3Parser.DictorsetmakerContext dictorsetmakerContext = atomCtx.dictorsetmaker();
        if (dictorsetmakerContext == null)
            return false;
        List<TerminalNode> colons = dictorsetmakerContext.COLON();
        if (colons == null)
            return false;
        return colons.size() > 0;
    }

    protected boolean hasTestListComp() {
        return atomCtx.testlist_comp() != null;
    }

    protected boolean isTrue() {
        return atomCtx.TRUE() != null;
    }

    @Override
    public void addChildren(RuleContext ruleContext, Visitor visitor) {
    }

    void addChildren(Python3Parser.AtomContext ctx, Visitor visitor) {
        if (isLiteral() || isName())
            return;
        if (isOpenParen())
            addParenNode(visitor);
        else if (hasDictOrSetMaker())
            addDictOrSetMakerNode(visitor);
        else if (hasTestListComp())
            addTestListCompNode(visitor);
    }

    void addDictOrSetMakerNode(Visitor visitor) {
        addChild(new DictorsetmakerNode(atomCtx.dictorsetmaker(), visitor));
    }

    void addParenNode(Visitor visitor) {
        tokenType = "<none>";
        tokenText = "<none>";
        if (atomCtx.getChildCount() == 3) {
            AstNode astNode = visitor.visit(atomCtx.getChild(1));
            if (astNode != null)
                addChild(astNode);
        }
    }

    void addTestListCompNode(Visitor visitor) {
        addChild(new TestlistCompNode(atomCtx.testlist_comp(), visitor));
    }

    void setTokenTextAndType(Python3Parser.AtomContext ctx) {
        if (ctx == null || ctx.start == null)
            return;
        setTokenText(ctx.start.getText());
        setTokenType(ctx.start.getType());
    }

//    public Expression generateLiteral() {
//        return new LiteralExpression(getLiteralValue());
//    }
//
//    @Override
//    public Expression generateStatementWithCollection(ExpressionList statements) {
//        if (!hasChildren()) {
//            if (isName() && !getIsLhs())
//                return new VariableExpression(getName());
//            return generateLiteral();
//        }
//        Expression expression = super.generateStatementWithCollection(statements);
//        if (expression == null)
//            info("AtomNode expression==null", getChildCount());
//        return expression;
//    }
//
//    public ExpressionList getDictOrSetItems() {
//        if (hasDictOrSetMaker())
//            return getLastChild().generateStatements();
//        else
//            return new ExpressionList();
//    }
//
//    public ExpressionList getListItems() {
//        if (hasTestListComp())
//            return getLastChild().generateStatements();
//        else
//            return new ExpressionList();
//    }

    public void generateByteCode(CompilerUtil compilerUtil) {
        generateLineNumber(compilerUtil);
        if (isLiteral())
            compilerUtil.op_LOAD_CONST(getLiteralValue());
        else if (isName())
            compilerUtil.op_LOAD_NAME(getName());
        super.generateByteCode(compilerUtil);
    }

    Object getLiteralValue() {
        if (isName())
            return getName();
//        else if (isNone())
//            return PyNone.getInstance();
        else if (isNumber())
            return getNumber();
        else if (isString())
            return parseString();
        else if (isFalse())
            return false;
        else if (isTrue())
            return true;
        Logable.err("unknown tokenType: " + tokenType);
        return tokenText;
    }

    @Override
    public String getName() {
        return atomCtx.NAME().getText();
    }

    public Object getNumber() {
        String numberText = hasMinusSign ? "-" : "";
        numberText += atomCtx.NUMBER().getText();
        if (numberText.indexOf('.') >= 0)
            return Double.parseDouble(numberText);
        return Integer.parseInt(numberText);
    }

    public String getString() {
        return atomCtx.STRING(0).getText();
    }

    @Override
    public boolean isDict() {
        if (!isOpenBrace())
            return false;
        else if (hasColons())
            return true;
        return getChildCount() == 0;
    }

    public boolean isEof() {
        return tokenType == "eof";
    }

    @Override
    public boolean isList() {
        return isOpenBrack();
    }

    @Override
    public boolean isLiteral() {
        return isFalse() || isNone() || isNumber() || isString() || isTrue();
    }

    @Override
    public boolean isSet() {
        if (!isOpenBrace() || getChildCount() == 0)
            return false;
        return !hasColons();
    }

    @Override
    public boolean isTuple() {
        if (!isOpenParen())
            return false;
        else if (hasTestListComp() && hasCommas())
            return true;
        return getChildCount() == 0;
    }

    String parseString() {
        return tokenText.substring(1, tokenText.length() - 1);
    }

    public void setTokenText(String tokenText) {
        this.tokenText = tokenText;
    }

    public void setTokenType(int tokenType) {
        switch (tokenType) {
            case -1:
                this.tokenType = "eof";
                break;
            case 1:
                this.tokenType = "indent";
                break;
            case 2:
                this.tokenType = "dedent";
                break;
            case 3:
                this.tokenType = "string";
                break;
            case 4:
                this.tokenType = "number";
                break;
            case 5:
                this.tokenType = "integer";
                break;
            case 6:
                this.tokenType = "def";
                break;
            case 7:
                this.tokenType = "return";
                break;
            case 8:
                this.tokenType = "raise";
                break;
            case 9:
                this.tokenType = "from";
                break;
            case 10:
                this.tokenType = "import";
                break;
            case 11:
                this.tokenType = "as";
                break;
            case 12:
                this.tokenType = "global";
                break;
            case 13:
                this.tokenType = "nonlocal";
                break;
            case 14:
                this.tokenType = "assert";
                break;
            case 15:
                this.tokenType = "if";
                break;
            case 16:
                this.tokenType = "elif";
                break;
            case 17:
                this.tokenType = "else";
                break;
            case 18:
                this.tokenType = "while";
                break;
            case 19:
                this.tokenType = "for";
                break;
            case 20:
                this.tokenType = "in";
                break;
            case 21:
                this.tokenType = "try";
                break;
            case 22:
                this.tokenType = "finally";
                break;
            case 23:
                this.tokenType = "with";
                break;
            case 24:
                this.tokenType = "except";
                break;
            case 25:
                this.tokenType = "lambda";
                break;
            case 26:
                this.tokenType = "or";
                break;
            case 27:
                this.tokenType = "and";
                break;
            case 28:
                this.tokenType = "not";
                break;
            case 29:
                this.tokenType = "is";
                break;
            case 30:
                this.tokenType = "None";
                break;
            case 31:
                this.tokenType = "True";
                break;
            case 32:
                this.tokenType = "32";
                break;
            case 33:
                this.tokenType = "class";
                break;
            case 34:
                this.tokenType = "yield";
                break;
            case 35:
                this.tokenType = "del";
                break;
            case 36:
                this.tokenType = "pass";
                break;
            case 37:
                this.tokenType = "continue";
                break;
            case 38:
                this.tokenType = "break";
                break;
            case 39:
                this.tokenType = "async";
                break;
            case 40:
                this.tokenType = "await";
                break;
            case 41:
                this.tokenType = "newline";
                break;
            case 42:
                this.tokenType = "name";
                break;
            case 43:
                this.tokenType = "string_literal";
                break;
            case 44:
                this.tokenType = "bytes_literal";
                break;
            case 45:
                this.tokenType = "decimal_integer";
                break;
            case 46:
                this.tokenType = "oct_integer";
                break;
            case 47:
                this.tokenType = "hex_integer";
                break;
            case 48:
                this.tokenType = "bin_integer";
                break;
            case 49:
                this.tokenType = "float_number";
                break;
            case 50:
                this.tokenType = "imag_number";
                break;
            case 51:
                this.tokenType = "dot";
                break;
            case 52:
                this.tokenType = "ellipsis";
                break;
            case 53:
                this.tokenType = "star";
                break;
            case 54:
                this.tokenType = "open_paren";
                break;
            case 55:
                this.tokenType = "close_paren";
                break;
            case 56:
                this.tokenType = "comma";
                break;
            case 57:
                this.tokenType = "colon";
                break;
            case 58:
                this.tokenType = "semi_colon";
                break;
            case 59:
                this.tokenType = "power";
                break;
            case 60:
                this.tokenType = "assign";
                break;
            case 61:
                this.tokenType = "open_brack";
                break;
            case 62:
                this.tokenType = "close_brack";
                break;
            case 63:
                this.tokenType = "op_or";
                break;
            case 64:
                this.tokenType = "xor";
                break;
            case 65:
                this.tokenType = "and_op";
                break;
            case 66:
                this.tokenType = "left_shift";
                break;
            case 67:
                this.tokenType = "right_shift";
                break;
            case 68:
                this.tokenType = "add";
                break;
            case 69:
                this.tokenType = "minus";
                break;
            case 70:
                this.tokenType = "div";
                break;
            case 71:
                this.tokenType = "mod";
                break;
            case 72:
                this.tokenType = "idiv";
                break;
            case 73:
                this.tokenType = "not_op";
                break;
            case 74:
                this.tokenType = "open_brace";
                break;
            case 75:
                this.tokenType = "close_brace";
                break;
            case 76:
                this.tokenType = "less_than";
                break;
            case 77:
                this.tokenType = "greater_then";
                break;
            case 78:
                this.tokenType = "equals";
                break;
            case 79:
                this.tokenType = "gt_eq";
                break;
            case 80:
                this.tokenType = "lt_eq";
                break;
            case 81:
                this.tokenType = "not_eq_1";
                break;
            case 82:
                this.tokenType = "not_eq_2";
                break;
            case 83:
                this.tokenType = "at";
                break;
            case 84:
                this.tokenType = "arrow";
                break;
            case 85:
                this.tokenType = "add_assign";
                break;
            case 86:
                this.tokenType = "sub_assign";
                break;
            case 87:
                this.tokenType = "mult_assign";
                break;
            case 88:
                this.tokenType = "at_assign";
                break;
            case 89:
                this.tokenType = "div_assign";
                break;
            case 90:
                this.tokenType = "mod_assign";
                break;
            case 91:
                this.tokenType = "and_assign";
                break;
            case 92:
                this.tokenType = "or_assign";
                break;
            case 93:
                this.tokenType = "xor_assign";
                break;
            case 94:
                this.tokenType = "left_shift_assign";
                break;
            case 95:
                this.tokenType = "right_shift_assign";
                break;
            case 96:
                this.tokenType = "power_assign";
                break;
            case 97:
                this.tokenType = "idiv_assign";
                break;
            case 98:
                this.tokenType = "skip_";
                break;
            case 99:
                this.tokenType = "unknown_char";
                break;
            default:
                this.tokenType = "<unknown token type: " + tokenType;
                break;
        }

    }

    public void setTokenTypeStr(String tokenType) {
        this.tokenType = tokenType;
    }

    public void writeAttributes(StringList sw, int indent) {
        super.writeAttributes(sw, indent);
        writeAttribute(sw, indent, "tokenType", tokenType);
        writeAttribute(sw, indent, "tokenText", tokenText);
    }

    Python3Parser.AtomContext atomCtx;
    String tokenText = "<none>";
    String tokenType = "<none>";
}
