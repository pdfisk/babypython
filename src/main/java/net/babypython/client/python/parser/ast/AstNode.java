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

import net.babypython.client.vm.containers.lists.AstList;
import net.babypython.client.python.parser.Visitor;
import net.babypython.client.vm.compiler.CompilerUtil;
import net.babypython.client.vm.containers.lists.StringList;
import net.babypython.client.ui.util.Logable;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

public class AstNode extends Logable {

    public AstNode(String name, ParserRuleContext ctx, Visitor visitor) {
        this.name = name;
        this.ctx = ctx;
        this.lhsFlag = false;
        this.visitor = visitor;
        this.children = new AstList();
        this.addChildren(this.ctx, visitor);
        this.startToken = ctx.start;
    }

    public AstNode() {
    }

    public void addChildren(RuleContext ruleContext, Visitor visitor) {
        if (ruleContext == null)
            return;
        for (int i = 0; i < ruleContext.getChildCount(); i++) {
            ParseTree childCtx = ruleContext.getChild(i);
            AstNode child = visitor.visit(childCtx);
            if (child != null && selectChild(child))
                addChild(child);
        }
    }

    public AstNode addChild(AstNode child) {
        children.add(child);
        child.parent = this;
        return this;
    }

    public AstNode asNameNode() {
        if (getChildCount() == 1)
            return getChild(0).asNameNode();
        return null;
    }

    public void display() {
        showClassTree();
    }

    public void generateByteCode(CompilerUtil compilerUtil) {
//        info("generateByteCode", getClass().getSimpleName());
        generateLineNumber(compilerUtil);
        generateChildrenByteCode(compilerUtil);
    }

    public void generateLineNumber(CompilerUtil compilerUtil) {
        compilerUtil.generateLineNumber(getLineNumber());
    }

    void generateChildrenByteCode(CompilerUtil compilerUtil) {
        for (int i = 0; i < getChildCount(); i++) {
            AstNode child = getChild(i);
            if (child == null)
                continue;
            try {
                child.generateByteCode(compilerUtil);
            } catch (Exception e) {
                info("generateChildrenByteCode exception", child.getClass().getSimpleName());
            }
        }
    }

    public int getArgCount() {
        return 0;
    }

    public AstNode getChild(int index) {
        return children.get(index);
    }

    public int getChildCount() {
        return children.size();
    }

    public Object[] getChildren() {
        return children.toArray();
    }

    public String getCompOp() {
        return null;
    }

    public int getLineNumber() {
        return startToken.getLine();
    }

    public String getName() {
        if (hasChildren())
            return getChild(0).getName();
        return "<none>";
    }

    public AstNode getAtomOrLastChild() {
        if (isAtom() || getChildCount() == 0)
            return this;
        return getChild(getChildCount() - 1).getAtomOrLastChild();
    }

    public AstNode getFirstChild() {
        return children.get(0);
    }

    public AstNode getLastChild() {
        return children.get(children.size() - 1);
    }

    public AstNode getLastMostChild() {
        if (getChildCount() == 0)
            return this;
        return getLastChild().getLastMostChild();
    }

    public boolean hasLastArgList() {
        return hasChildren() && getLastChild().isArglist();
    }

    public boolean hasChildren() {
        return children.size() > 0;
    }

    public boolean isAnnassign() {
        return false;
    }

    public boolean isAndExpr() {
        return false;
    }

    public boolean isAndText() {
        return false;
    }

    public boolean isArglist() {
        return false;
    }

    public boolean isArgument() {
        return false;
    }

    public boolean isArithExpr() {
        return false;
    }

    public boolean isAssertStmt() {
        return false;
    }

    public boolean isAsync() {
        return isAsync;
    }

    public boolean isAsyncFuncdef() {
        return false;
    }

    public boolean isAsyncStmt() {
        return false;
    }

    public boolean isAtom() {
        return false;
    }

    public boolean isAtomExpr() {
        return false;
    }

    public boolean isAugassign() {
        return false;
    }

    public boolean isBreakStmt() {
        return false;
    }

    public boolean isClassdef() {
        return false;
    }

    public boolean isComparison() {
        return false;
    }

    public boolean isCompFor() {
        return false;
    }

    public boolean isCompIf() {
        return false;
    }

    public boolean isCompIter() {
        return false;
    }

    public boolean isCompOp() {
        return false;
    }

    public boolean isCompoundStmt() {
        return false;
    }

    public boolean isContinueStmt() {
        return false;
    }

    public boolean isDecorated() {
        return false;
    }

    public boolean isDecorator() {
        return false;
    }

    public boolean isDecorators() {
        return false;
    }

    public boolean isDelStmt() {
        return false;
    }

    public boolean isDict() {
        return false;
    }

    public boolean isDot() {
        return false;
    }

    public boolean isDotName() {
        return false;
    }

    public boolean isDottedAsName() {
        return false;
    }

    public boolean isDottedAsNames() {
        return false;
    }

    public boolean isDottedName() {
        return false;
    }

    public boolean isEncodingDecl() {
        return false;
    }

    public boolean isEvalInput() {
        return false;
    }

    public boolean isExceptClause() {
        return false;
    }

    public boolean isExpr() {
        return false;
    }

    public boolean isExprlist() {
        return false;
    }

    public boolean isExprStmt() {
        return false;
    }

    public boolean isFactor() {
        return false;
    }

    public boolean isFileInput() {
        return false;
    }

    public boolean isFlowStmt() {
        return false;
    }

    public boolean isForStmt() {
        return false;
    }

    public boolean isFuncdef() {
        return false;
    }

    public boolean isGlobalStmt() {
        return false;
    }

    public boolean isIfStmt() {
        return false;
    }

    public boolean isImportAsName() {
        return false;
    }

    public boolean isImportAsNames() {
        return false;
    }

    public boolean isImportFrom() {
        return false;
    }

    public boolean isImportName() {
        return false;
    }

    public boolean isImportStmt() {
        return false;
    }

    public boolean isIndexList() {
        return false;
    }

    public boolean isLamdadefNocond() {
        return false;
    }

    public boolean isList() {
        return false;
    }

    public boolean isLiteral() {
        return false;
    }

    public boolean isLamdadef() {
        return false;
    }

    public boolean isName() {
        return false;
    }

    public boolean isNonlocalStmt() {
        return false;
    }

    public boolean isNotTest() {
        return false;
    }

    public boolean isOperator() {
        return false;
    }

    public boolean isOrTest() {
        return false;
    }

    public boolean isParameters() {
        return false;
    }

    public boolean isParen() {
        return false;
    }

    public boolean isPassStmt() {
        return false;
    }

    public boolean isPower() {
        return false;
    }

    public boolean isRaiseStmt() {
        return false;
    }

    public boolean isReturnStmt() {
        return false;
    }

    public boolean isSet() {
        return false;
    }

    public boolean isShiftExpr() {
        return false;
    }

    public boolean isSimpleStmt() {
        return false;
    }

    public boolean isSingleInput() {
        return false;
    }

    public boolean isSliceop() {
        return false;
    }

    public boolean isSmallStmt() {
        return false;
    }

    public boolean isStarExpr() {
        return false;
    }

    public boolean isStmt() {
        return false;
    }

    public boolean isSubscript() {
        return false;
    }

    public boolean isSubscriptlist() {
        return false;
    }

    public boolean isSuite() {
        return false;
    }

    public boolean isTerm() {
        return false;
    }

    public boolean isTest() {
        return false;
    }

    public boolean isTestlist() {
        return false;
    }

    public boolean isTestlistComp() {
        return false;
    }

    public boolean isTestlistNocond() {
        return false;
    }

    public boolean isTestlistStarExpr() {
        return false;
    }

    public boolean isTfpdef() {
        return false;
    }

    public boolean isTrailer() {
        return false;
    }

    public boolean isTryStmt() {
        return false;
    }

    public boolean isTuple() {
        return false;
    }

    public boolean isTypedargslist() {
        return false;
    }

    public boolean isVarargslist() {
        return false;
    }

    public boolean isVpdef() {
        return false;
    }

    public boolean isWhileStmt() {
        return false;
    }

    public boolean isWithItem() {
        return false;
    }

    public boolean isWithStmt() {
        return false;
    }

    public boolean isXorExpr() {
        return false;
    }

    public boolean isYieldArg() {
        return false;
    }

    public boolean isYieldExpr() {
        return false;
    }

    public boolean isYieldStmt() {
        return false;
    }

    public boolean getIsLhs() {
        return isLhs;
    }

    protected boolean selectChild(AstNode child) {
        return true;
    }

    public void setHasMinusSign() {
        hasMinusSign = true;
        for (int i = 0; i < children.size(); i++) {
            getChild(i).setHasMinusSign();
        }
    }

    public void setIsAsync() {
    }

    public void setIsLhs(boolean value) {
        isLhs = value;
        for (int i = 0; i < children.size(); i++) {
            getChild(i).setIsLhs(value);
        }
    }

    public void showClassTree() {
        showClassChildren(0);
    }

    public void showClassChildren(int indent) {
        String indentStr = "";
        for (int i = 0; i < indent; i++) {
            indentStr += " ";
        }
        info(indentStr + getClass().getSimpleName());
        for (AstNode child : children)
            child.showClassChildren(indent + 1);
    }

    public String toTreeString() {
        StringList sw = new StringList();
        writeTree(sw, 0);
        String str = "";
        for (int i = 0; i < sw.size(); i++) {
            str += sw.get(i);
        }
        return str;
    }

    public String toString() {
        return this.getClass().getName() + "(" + children.size() + ")";
    }

    public void writeAttribute(StringList sw, int indent, String attributeName, String attributeValue) {
        writeIndent(sw, indent);
        sw.add("--- " + attributeName + ": " + attributeValue + '\n');
    }

    public void writeAttributes(StringList sw, int indent) {
    }

    public void writeIndent(StringList sw, int indent) {
        for (int i = 0; i < indent; i++) {
            sw.add(" ");
        }
    }

    public void writeTree(StringList sw, int indent) {
        writeIndent(sw, indent);
        String clsName = this.getClass().getSimpleName();
        sw.add(clsName + '\n');
        writeAttributes(sw, indent);
        for (int i = 0; i < children.size(); i++) {
            AstNode child = children.get(i);
            child.writeTree(sw, indent + 1);
        }
    }

    public AstList children = new AstList();
    public ParserRuleContext ctx;
    public boolean hasMinusSign = false;
    public boolean isAsync = false;
    public boolean isLhs = false;
    public boolean lhsFlag;
    public String name;
    public AstNode parent;
    public Token startToken;
    public Visitor visitor;
}
