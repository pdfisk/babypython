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
package net.babypython.client.python.parser;

import net.babypython.client.python.antlr.Python3Parser;
import net.babypython.client.python.antlr.Python3ParserVisitor;
import net.babypython.client.python.parser.ast.*;
import net.babypython.client.ui.util.Logable;
import org.antlr.v4.runtime.tree.*;

public class Visitor extends Logable implements Python3ParserVisitor<AstNode> {

    @Override
    public AstNode visit(ParseTree ctx) {
        if (ctx == null) {
            info("ctx == null");
            return null;
        }
//        info("visit:" + ctx.getClass().getSimpleName());
        if (ctx instanceof Python3Parser.Single_inputContext)
            return this.visitSingle_input((Python3Parser.Single_inputContext) ctx);
        if (ctx instanceof Python3Parser.File_inputContext)
            return this.visitFile_input((Python3Parser.File_inputContext) ctx);
        if (ctx instanceof Python3Parser.Eval_inputContext)
            return this.visitEval_input((Python3Parser.Eval_inputContext) ctx);
        if (ctx instanceof Python3Parser.DecoratorContext)
            return this.visitDecorator((Python3Parser.DecoratorContext) ctx);
        if (ctx instanceof Python3Parser.DecoratorsContext)
            return this.visitDecorators((Python3Parser.DecoratorsContext) ctx);
        if (ctx instanceof Python3Parser.DecoratedContext)
            return this.visitDecorated((Python3Parser.DecoratedContext) ctx);
        if (ctx instanceof Python3Parser.Async_funcdefContext)
            return this.visitAsync_funcdef((Python3Parser.Async_funcdefContext) ctx);
        if (ctx instanceof Python3Parser.FuncdefContext)
            return this.visitFuncdef((Python3Parser.FuncdefContext) ctx);
        if (ctx instanceof Python3Parser.ParametersContext)
            return this.visitParameters((Python3Parser.ParametersContext) ctx);
        if (ctx instanceof Python3Parser.TypedargslistContext)
            return this.visitTypedargslist((Python3Parser.TypedargslistContext) ctx);
        if (ctx instanceof Python3Parser.TfpdefContext)
            return this.visitTfpdef((Python3Parser.TfpdefContext) ctx);
        if (ctx instanceof Python3Parser.VarargslistContext)
            return this.visitVarargslist((Python3Parser.VarargslistContext) ctx);
        if (ctx instanceof Python3Parser.VfpdefContext)
            return this.visitVfpdef((Python3Parser.VfpdefContext) ctx);
        if (ctx instanceof Python3Parser.StmtContext)
            return this.visitStmt((Python3Parser.StmtContext) ctx);
        if (ctx instanceof Python3Parser.Simple_stmtContext)
            return this.visitSimple_stmt((Python3Parser.Simple_stmtContext) ctx);
        if (ctx instanceof Python3Parser.Small_stmtContext)
            return this.visitSmall_stmt((Python3Parser.Small_stmtContext) ctx);
        if (ctx instanceof Python3Parser.Expr_stmtContext)
            return this.visitExpr_stmt((Python3Parser.Expr_stmtContext) ctx);
        if (ctx instanceof Python3Parser.AnnassignContext)
            return this.visitAnnassign((Python3Parser.AnnassignContext) ctx);
        if (ctx instanceof Python3Parser.Testlist_star_exprContext)
            return this.visitTestlist_star_expr((Python3Parser.Testlist_star_exprContext) ctx);
        if (ctx instanceof Python3Parser.AugassignContext)
            return this.visitAugassign((Python3Parser.AugassignContext) ctx);
        if (ctx instanceof Python3Parser.Del_stmtContext)
            return this.visitDel_stmt((Python3Parser.Del_stmtContext) ctx);
        if (ctx instanceof Python3Parser.Pass_stmtContext)
            return this.visitPass_stmt((Python3Parser.Pass_stmtContext) ctx);
        if (ctx instanceof Python3Parser.Flow_stmtContext)
            return this.visitFlow_stmt((Python3Parser.Flow_stmtContext) ctx);
        if (ctx instanceof Python3Parser.Break_stmtContext)
            return this.visitBreak_stmt((Python3Parser.Break_stmtContext) ctx);
        if (ctx instanceof Python3Parser.Continue_stmtContext)
            return this.visitContinue_stmt((Python3Parser.Continue_stmtContext) ctx);
        if (ctx instanceof Python3Parser.Return_stmtContext)
            return this.visitReturn_stmt((Python3Parser.Return_stmtContext) ctx);
        if (ctx instanceof Python3Parser.Yield_stmtContext)
            return this.visitYield_stmt((Python3Parser.Yield_stmtContext) ctx);
        if (ctx instanceof Python3Parser.Raise_stmtContext)
            return this.visitRaise_stmt((Python3Parser.Raise_stmtContext) ctx);
        if (ctx instanceof Python3Parser.Import_stmtContext)
            return this.visitImport_stmt((Python3Parser.Import_stmtContext) ctx);
        if (ctx instanceof Python3Parser.Import_nameContext)
            return this.visitImport_name((Python3Parser.Import_nameContext) ctx);
        if (ctx instanceof Python3Parser.Import_fromContext)
            return this.visitImport_from((Python3Parser.Import_fromContext) ctx);
        if (ctx instanceof Python3Parser.Import_as_nameContext)
            return this.visitImport_as_name((Python3Parser.Import_as_nameContext) ctx);
        if (ctx instanceof Python3Parser.Dotted_as_nameContext)
            return this.visitDotted_as_name((Python3Parser.Dotted_as_nameContext) ctx);
        if (ctx instanceof Python3Parser.Import_as_namesContext)
            return this.visitImport_as_names((Python3Parser.Import_as_namesContext) ctx);
        if (ctx instanceof Python3Parser.Dotted_as_namesContext)
            return this.visitDotted_as_names((Python3Parser.Dotted_as_namesContext) ctx);
        if (ctx instanceof Python3Parser.Dotted_nameContext)
            return this.visitDotted_name((Python3Parser.Dotted_nameContext) ctx);
        if (ctx instanceof Python3Parser.Global_stmtContext)
            return this.visitGlobal_stmt((Python3Parser.Global_stmtContext) ctx);
        if (ctx instanceof Python3Parser.Nonlocal_stmtContext)
            return this.visitNonlocal_stmt((Python3Parser.Nonlocal_stmtContext) ctx);
        if (ctx instanceof Python3Parser.Assert_stmtContext)
            return this.visitAssert_stmt((Python3Parser.Assert_stmtContext) ctx);
        if (ctx instanceof Python3Parser.Compound_stmtContext)
            return this.visitCompound_stmt((Python3Parser.Compound_stmtContext) ctx);
        if (ctx instanceof Python3Parser.Async_stmtContext)
            return this.visitAsync_stmt((Python3Parser.Async_stmtContext) ctx);
        if (ctx instanceof Python3Parser.If_stmtContext)
            return this.visitIf_stmt((Python3Parser.If_stmtContext) ctx);
        if (ctx instanceof Python3Parser.While_stmtContext)
            return this.visitWhile_stmt((Python3Parser.While_stmtContext) ctx);
        if (ctx instanceof Python3Parser.For_stmtContext)
            return this.visitFor_stmt((Python3Parser.For_stmtContext) ctx);
        if (ctx instanceof Python3Parser.Try_stmtContext)
            return this.visitTry_stmt((Python3Parser.Try_stmtContext) ctx);
        if (ctx instanceof Python3Parser.With_stmtContext)
            return this.visitWith_stmt((Python3Parser.With_stmtContext) ctx);
        if (ctx instanceof Python3Parser.With_itemContext)
            return this.visitWith_item((Python3Parser.With_itemContext) ctx);
        if (ctx instanceof Python3Parser.Except_clauseContext)
            return this.visitExcept_clause((Python3Parser.Except_clauseContext) ctx);
        if (ctx instanceof Python3Parser.SuiteContext)
            return this.visitSuite((Python3Parser.SuiteContext) ctx);
        if (ctx instanceof Python3Parser.TestContext)
            return this.visitTest((Python3Parser.TestContext) ctx);
        if (ctx instanceof Python3Parser.Test_nocondContext)
            return this.visitTest_nocond((Python3Parser.Test_nocondContext) ctx);
        if (ctx instanceof Python3Parser.LambdefContext)
            return this.visitLambdef((Python3Parser.LambdefContext) ctx);
        if (ctx instanceof Python3Parser.Lambdef_nocondContext)
            return this.visitLambdef_nocond((Python3Parser.Lambdef_nocondContext) ctx);
        if (ctx instanceof Python3Parser.Or_testContext)
            return this.visitOr_test((Python3Parser.Or_testContext) ctx);
        if (ctx instanceof Python3Parser.And_testContext)
            return this.visitAnd_test((Python3Parser.And_testContext) ctx);
        if (ctx instanceof Python3Parser.Not_testContext)
            return this.visitNot_test((Python3Parser.Not_testContext) ctx);
        if (ctx instanceof Python3Parser.ComparisonContext)
            return this.visitComparison((Python3Parser.ComparisonContext) ctx);
        if (ctx instanceof Python3Parser.Comp_opContext)
            return this.visitComp_op((Python3Parser.Comp_opContext) ctx);
        if (ctx instanceof Python3Parser.Star_exprContext)
            return this.visitStar_expr((Python3Parser.Star_exprContext) ctx);
        if (ctx instanceof Python3Parser.ExprContext)
            return this.visitExpr((Python3Parser.ExprContext) ctx);
        if (ctx instanceof Python3Parser.Xor_exprContext)
            return this.visitXor_expr((Python3Parser.Xor_exprContext) ctx);
        if (ctx instanceof Python3Parser.And_exprContext)
            return this.visitAnd_expr((Python3Parser.And_exprContext) ctx);
        if (ctx instanceof Python3Parser.Shift_exprContext)
            return this.visitShift_expr((Python3Parser.Shift_exprContext) ctx);
        if (ctx instanceof Python3Parser.Arith_exprContext)
            return this.visitArith_expr((Python3Parser.Arith_exprContext) ctx);
        if (ctx instanceof Python3Parser.TermContext)
            return this.visitTerm((Python3Parser.TermContext) ctx);
        if (ctx instanceof Python3Parser.FactorContext)
            return this.visitFactor((Python3Parser.FactorContext) ctx);
        if (ctx instanceof Python3Parser.PowerContext)
            return this.visitPower((Python3Parser.PowerContext) ctx);
        if (ctx instanceof Python3Parser.Atom_exprContext)
            return this.visitAtom_expr((Python3Parser.Atom_exprContext) ctx);
        if (ctx instanceof Python3Parser.AtomContext)
            return this.visitAtom((Python3Parser.AtomContext) ctx);
        if (ctx instanceof Python3Parser.Testlist_compContext)
            return this.visitTestlist_comp((Python3Parser.Testlist_compContext) ctx);
        if (ctx instanceof Python3Parser.TrailerContext)
            return this.visitTrailer((Python3Parser.TrailerContext) ctx);
        if (ctx instanceof Python3Parser.SubscriptlistContext)
            return this.visitSubscriptlist((Python3Parser.SubscriptlistContext) ctx);
        if (ctx instanceof Python3Parser.Subscript_Context)
            return this.visitSubscript_((Python3Parser.Subscript_Context) ctx);
        if (ctx instanceof Python3Parser.SliceopContext)
            return this.visitSliceop((Python3Parser.SliceopContext) ctx);
        if (ctx instanceof Python3Parser.ExprlistContext)
            return this.visitExprlist((Python3Parser.ExprlistContext) ctx);
        if (ctx instanceof Python3Parser.TestlistContext)
            return this.visitTestlist((Python3Parser.TestlistContext) ctx);
        if (ctx instanceof Python3Parser.DictorsetmakerContext)
            return this.visitDictorsetmaker((Python3Parser.DictorsetmakerContext) ctx);
        if (ctx instanceof Python3Parser.ClassdefContext)
            return this.visitClassdef((Python3Parser.ClassdefContext) ctx);
        if (ctx instanceof Python3Parser.ArglistContext)
            return this.visitArglist((Python3Parser.ArglistContext) ctx);
        if (ctx instanceof Python3Parser.ArgumentContext)
            return this.visitArgument((Python3Parser.ArgumentContext) ctx);
        if (ctx instanceof Python3Parser.Comp_iterContext)
            return this.visitComp_iter((Python3Parser.Comp_iterContext) ctx);
        if (ctx instanceof Python3Parser.Comp_forContext)
            return this.visitComp_for((Python3Parser.Comp_forContext) ctx);
        if (ctx instanceof Python3Parser.Comp_ifContext)
            return this.visitComp_if((Python3Parser.Comp_ifContext) ctx);
        if (ctx instanceof Python3Parser.Encoding_declContext)
            return this.visitEncoding_decl((Python3Parser.Encoding_declContext) ctx);
        if (ctx instanceof Python3Parser.Yield_exprContext)
            return this.visitYield_expr((Python3Parser.Yield_exprContext) ctx);
        if (ctx instanceof Python3Parser.Yield_argContext)
            return this.visitYield_arg((Python3Parser.Yield_argContext) ctx);
        if (ctx instanceof TerminalNodeImpl)
            return null;
        info("unmatched: " + ctx.getClass().getSimpleName());
        return null;
    }

    @Override
    public SingleInputNode visitSingle_input(Python3Parser.Single_inputContext ctx) {
//        DomUtils.log("visitSingle_input");
        return new SingleInputNode(ctx, this);
    }

    @Override
    public FileInputNode visitFile_input(Python3Parser.File_inputContext ctx) {
//        DomUtils.log("visitFile_input");
        return new FileInputNode(ctx, this);
    }

    @Override
    public EvalInputNode visitEval_input(Python3Parser.Eval_inputContext ctx) {
//        DomUtils.log("visitEval_input");
        return new EvalInputNode(ctx, this);
    }

    @Override
    public DecoratorNode visitDecorator(Python3Parser.DecoratorContext ctx) {
//        DomUtils.log("visitDecorator");
        return new DecoratorNode(ctx, this);
    }

    @Override
    public DecoratorsNode visitDecorators(Python3Parser.DecoratorsContext ctx) {
//        DomUtils.log("visitDecorators");
        return new DecoratorsNode(ctx, this);
    }

    @Override
    public DecoratedNode visitDecorated(Python3Parser.DecoratedContext ctx) {
//        DomUtils.log("visitDecorated");
        return new DecoratedNode(ctx, this);
    }

    @Override
    public AsyncFuncdefNode visitAsync_funcdef(Python3Parser.Async_funcdefContext ctx) {
//        DomUtils.log("visitAsync_funcdef");
        return new AsyncFuncdefNode(ctx, this);
    }

    @Override
    public FuncdefNode visitFuncdef(Python3Parser.FuncdefContext ctx) {
//        DomUtils.log("visitFuncdef");
        return new FuncdefNode(ctx, this);
    }

    @Override
    public ParametersNode visitParameters(Python3Parser.ParametersContext ctx) {
//        DomUtils.log("visitParameters");
        return new ParametersNode(ctx, this);
    }

    @Override
    public TypedargslistNode visitTypedargslist(Python3Parser.TypedargslistContext ctx) {
//        DomUtils.log("visitTypedargslist");
        return new TypedargslistNode(ctx, this);
    }

    @Override
    public TfpdefNode visitTfpdef(Python3Parser.TfpdefContext ctx) {
//        DomUtils.log("visitTfpdef");
        return new TfpdefNode(ctx, this);
    }

    @Override
    public VarargslistNode visitVarargslist(Python3Parser.VarargslistContext ctx) {
//        DomUtils.log("visitVarargslist");
        return new VarargslistNode(ctx, this);
    }

    @Override
    public VpfdefNode visitVfpdef(Python3Parser.VfpdefContext ctx) {
//        DomUtils.log("visitVfpdef");
        return new VpfdefNode(ctx, this);
    }

    @Override
    public StmtNode visitStmt(Python3Parser.StmtContext ctx) {
//        DomUtils.log("visitStmt");
        return new StmtNode(ctx, this);
    }

    @Override
    public SimpleStmtNode visitSimple_stmt(Python3Parser.Simple_stmtContext ctx) {
//        DomUtils.log("visitSimple_stmt");
        return new SimpleStmtNode(ctx, this);
    }

    @Override
    public SmallStmtNode visitSmall_stmt(Python3Parser.Small_stmtContext ctx) {
//        DomUtils.log("visitSmall_stmt");
        return new SmallStmtNode(ctx, this);
    }

    @Override
    public ExprStmtNode visitExpr_stmt(Python3Parser.Expr_stmtContext ctx) {
//        DomUtils.log("visitExpr_stmt");
        return new ExprStmtNode(ctx, this);
    }

    @Override
    public AnnassignNode visitAnnassign(Python3Parser.AnnassignContext ctx) {
//        DomUtils.log("visitAnnassign");
        return new AnnassignNode(ctx, this);
    }

    @Override
    public TestlistStarExprNode visitTestlist_star_expr(Python3Parser.Testlist_star_exprContext ctx) {
//        DomUtils.log("visitTestlist_star_expr");
        return new TestlistStarExprNode(ctx, this);
    }

    @Override
    public AugassignNode visitAugassign(Python3Parser.AugassignContext ctx) {
//        DomUtils.log("visitAugassign");
        return new AugassignNode(ctx, this);
    }

    @Override
    public DelStmtNode visitDel_stmt(Python3Parser.Del_stmtContext ctx) {
//        DomUtils.log("visitDel_stmt");
        return new DelStmtNode(ctx, this);
    }

    @Override
    public PassStmtNode visitPass_stmt(Python3Parser.Pass_stmtContext ctx) {
//        DomUtils.log("visitPass_stmt");
        return new PassStmtNode(ctx, this);
    }

    @Override
    public FlowStmtNode visitFlow_stmt(Python3Parser.Flow_stmtContext ctx) {
//        DomUtils.log("visitFlow_stmt");
        return new FlowStmtNode(ctx, this);
    }

    @Override
    public BreakStmtNode visitBreak_stmt(Python3Parser.Break_stmtContext ctx) {
//        DomUtils.log("visitBreak_stmt");
        return new BreakStmtNode(ctx, this);
    }

    @Override
    public ContinueStmtNode visitContinue_stmt(Python3Parser.Continue_stmtContext ctx) {
//        DomUtils.log("visitContinue_stmt");
        return new ContinueStmtNode(ctx, this);
    }

    @Override
    public ReturnStmtNode visitReturn_stmt(Python3Parser.Return_stmtContext ctx) {
//        DomUtils.log("visitReturn_stmt");
        return new ReturnStmtNode(ctx, this);
    }

    @Override
    public YieldStmtNode visitYield_stmt(Python3Parser.Yield_stmtContext ctx) {
//        DomUtils.log("visitYield_stmt");
        return new YieldStmtNode(ctx, this);
    }

    @Override
    public RaiseStmtNode visitRaise_stmt(Python3Parser.Raise_stmtContext ctx) {
//        DomUtils.log("visitRaise_stmt");
        return new RaiseStmtNode(ctx, this);
    }

    @Override
    public ImportStmtNode visitImport_stmt(Python3Parser.Import_stmtContext ctx) {
//        DomUtils.log("visitImport_stmt");
        return new ImportStmtNode(ctx, this);
    }

    @Override
    public ImportNameNode visitImport_name(Python3Parser.Import_nameContext ctx) {
//        DomUtils.log("visitImport_name");
        return new ImportNameNode(ctx, this);
    }

    @Override
    public ImportFromNode visitImport_from(Python3Parser.Import_fromContext ctx) {
//        DomUtils.log("visitImport_from");
        return new ImportFromNode(ctx, this);
    }

    @Override
    public ImportAsNameNode visitImport_as_name(Python3Parser.Import_as_nameContext ctx) {
//        DomUtils.log("visitImport_as_name");
        return new ImportAsNameNode(ctx, this);
    }

    @Override
    public DottedAsNameNode visitDotted_as_name(Python3Parser.Dotted_as_nameContext ctx) {
//        DomUtils.log("visitDotted_as_name");
        return new DottedAsNameNode(ctx, this);
    }

    @Override
    public ImportAsNamesNode visitImport_as_names(Python3Parser.Import_as_namesContext ctx) {
//        DomUtils.log("visitImport_as_names");
        return new ImportAsNamesNode(ctx, this);
    }

    @Override
    public DottedAsNamesNode visitDotted_as_names(Python3Parser.Dotted_as_namesContext ctx) {
//        DomUtils.log("visitDotted_as_names");
        return new DottedAsNamesNode(ctx, this);
    }

    @Override
    public DottedNameNode visitDotted_name(Python3Parser.Dotted_nameContext ctx) {
//        DomUtils.log("visitDotted_name");
        return new DottedNameNode(ctx, this);
    }

    @Override
    public GlobalStmtNode visitGlobal_stmt(Python3Parser.Global_stmtContext ctx) {
//        DomUtils.log("visitGlobal_stmt");
        return new GlobalStmtNode(ctx, this);
    }

    @Override
    public NonlocalStmtNode visitNonlocal_stmt(Python3Parser.Nonlocal_stmtContext ctx) {
//        DomUtils.log("visitNonlocal_stmt");
        return new NonlocalStmtNode(ctx, this);
    }

    @Override
    public AssertStmtNode visitAssert_stmt(Python3Parser.Assert_stmtContext ctx) {
//        DomUtils.log("visitAssert_stmt");
        return new AssertStmtNode(ctx, this);
    }

    @Override
    public CompoundStmtNode visitCompound_stmt(Python3Parser.Compound_stmtContext ctx) {
//        DomUtils.log("visitCompound_stmt");
        return new CompoundStmtNode(ctx, this);
    }

    @Override
    public AsyncStmtNode visitAsync_stmt(Python3Parser.Async_stmtContext ctx) {
//        DomUtils.log("visitAsync_stmt");
        return new AsyncStmtNode(ctx, this);
    }

    @Override
    public IfStmtNode visitIf_stmt(Python3Parser.If_stmtContext ctx) {
//        DomUtils.log("visitIf_stmt");
        return new IfStmtNode(ctx, this);
    }

    @Override
    public WhileStmtNode visitWhile_stmt(Python3Parser.While_stmtContext ctx) {
//        DomUtils.log("visitWhile_stmt");
        return new WhileStmtNode(ctx, this);
    }

    @Override
    public ForStmtNode visitFor_stmt(Python3Parser.For_stmtContext ctx) {
//        DomUtils.log("visitFor_stmt");
        return new ForStmtNode(ctx, this);
    }

    @Override
    public TryStmtNode visitTry_stmt(Python3Parser.Try_stmtContext ctx) {
//        DomUtils.log("visitTry_stmt");
        return new TryStmtNode(ctx, this);
    }

    @Override
    public WithStmtNode visitWith_stmt(Python3Parser.With_stmtContext ctx) {
//        DomUtils.log("visitWith_stmt");
        return new WithStmtNode(ctx, this);
    }

    @Override
    public WithItemNode visitWith_item(Python3Parser.With_itemContext ctx) {
//        DomUtils.log("visitWith_item");
        return new WithItemNode(ctx, this);
    }

    @Override
    public ExceptClauseNode visitExcept_clause(Python3Parser.Except_clauseContext ctx) {
//        DomUtils.log("visitExcept_clause");
        return new ExceptClauseNode(ctx, this);
    }

    @Override
    public SuiteNode visitSuite(Python3Parser.SuiteContext ctx) {
//        DomUtils.log("visitSuite");
        return new SuiteNode(ctx, this);
    }

    @Override
    public TestNode visitTest(Python3Parser.TestContext ctx) {
//        DomUtils.log("visitTest");
        return new TestNode(ctx, this);
    }

    @Override
    public TestNocondNode visitTest_nocond(Python3Parser.Test_nocondContext ctx) {
//        DomUtils.log("visitTest_nocond");
        return new TestNocondNode(ctx, this);
    }

    @Override
    public LambdefNode visitLambdef(Python3Parser.LambdefContext ctx) {
//        DomUtils.log("visitLambdef");
        return new LambdefNode(ctx, this);
    }

    @Override
    public LambdefNocondNode visitLambdef_nocond(Python3Parser.Lambdef_nocondContext ctx) {
//        DomUtils.log("visitLambdef_nocond");
        return new LambdefNocondNode(ctx, this);
    }

    @Override
    public OrTestNode visitOr_test(Python3Parser.Or_testContext ctx) {
//        DomUtils.log("visitOr_test");
        return new OrTestNode(ctx, this);
    }

    @Override
    public AndTestNode visitAnd_test(Python3Parser.And_testContext ctx) {
//        DomUtils.log("visitAnd_test");
        return new AndTestNode(ctx, this);
    }

    @Override
    public NotTestNode visitNot_test(Python3Parser.Not_testContext ctx) {
//        DomUtils.log("visitNot_test");
        return new NotTestNode(ctx, this);
    }

    @Override
    public ComparisonNode visitComparison(Python3Parser.ComparisonContext ctx) {
//        DomUtils.log("visitComparison");
        return new ComparisonNode(ctx, this);
    }

    @Override
    public CompOpNode visitComp_op(Python3Parser.Comp_opContext ctx) {
//        DomUtils.log("visitComp_op");
        return new CompOpNode(ctx, this);
    }

    @Override
    public StarExprNode visitStar_expr(Python3Parser.Star_exprContext ctx) {
//        DomUtils.log("visitStar_expr");
        return new StarExprNode(ctx, this);
    }

    @Override
    public ExprNode visitExpr(Python3Parser.ExprContext ctx) {
//        DomUtils.log("visitExpr");
        return new ExprNode(ctx, this);
    }

    @Override
    public XorExprNode visitXor_expr(Python3Parser.Xor_exprContext ctx) {
//        DomUtils.log("visitXor_expr");
        return new XorExprNode(ctx, this);
    }

    @Override
    public AndExprNode visitAnd_expr(Python3Parser.And_exprContext ctx) {
//        DomUtils.log("visitAnd_expr");
        return new AndExprNode(ctx, this);
    }

    @Override
    public ShiftExprNode visitShift_expr(Python3Parser.Shift_exprContext ctx) {
//        DomUtils.log("visitShift_expr");
        return new ShiftExprNode(ctx, this);
    }

    @Override
    public ArithExprNode visitArith_expr(Python3Parser.Arith_exprContext ctx) {
//        DomUtils.log("visitArith_expr");
        return new ArithExprNode(ctx, this);
    }

    @Override
    public TermNode visitTerm(Python3Parser.TermContext ctx) {
//        DomUtils.log("visitTerm");
        return new TermNode(ctx, this);
    }

    @Override
    public FactorNode visitFactor(Python3Parser.FactorContext ctx) {
//        DomUtils.log("visitFactor");
        return new FactorNode(ctx, this);
    }

    @Override
    public PowerNode visitPower(Python3Parser.PowerContext ctx) {
//        DomUtils.log("visitPower");
        return new PowerNode(ctx, this);
    }

    @Override
    public AtomExprNode visitAtom_expr(Python3Parser.Atom_exprContext ctx) {
//        DomUtils.log("visitAtom_expr");
        return new AtomExprNode(ctx, this);
    }

    @Override
    public AtomNode visitAtom(Python3Parser.AtomContext ctx) {
//        DomUtils.log("visitAtom");
        return new AtomNode(ctx, this);
    }

    @Override
    public TestlistCompNode visitTestlist_comp(Python3Parser.Testlist_compContext ctx) {
//        DomUtils.log("visitTestlist_comp");
        return new TestlistCompNode(ctx, this);
    }

    @Override
    public TrailerNode visitTrailer(Python3Parser.TrailerContext ctx) {
//        DomUtils.log("visitTrailer");
        return new TrailerNode(ctx, this);
    }

    @Override
    public SubscriptlistNode visitSubscriptlist(Python3Parser.SubscriptlistContext ctx) {
//        DomUtils.log("visitSubscriptlist");
        return new SubscriptlistNode(ctx, this);
    }

    @Override
    public AstNode visitSubscript_(Python3Parser.Subscript_Context ctx) {
//        DomUtils.log("visitSubscript_");
        return new SubscriptNode(ctx, this);
    }

    @Override
    public SliceopNode visitSliceop(Python3Parser.SliceopContext ctx) {
//        DomUtils.log("visitSliceop");
        return new SliceopNode(ctx, this);
    }

    @Override
    public ExprlistNode visitExprlist(Python3Parser.ExprlistContext ctx) {
//        DomUtils.log("visitExprlist");
        return new ExprlistNode(ctx, this);
    }

    @Override
    public AstNode visitTestlist(Python3Parser.TestlistContext ctx) {
//        DomUtils.log("visitTestlist");
        return new TestlistNode(ctx, this);
    }

    @Override
    public AstNode visitDictorsetmaker(Python3Parser.DictorsetmakerContext ctx) {
//        DomUtils.log("visitDictorsetmaker");
        return new DictorsetmakerNode(ctx, this);
    }

    @Override
    public AstNode visitClassdef(Python3Parser.ClassdefContext ctx) {
//        DomUtils.log("visitClassdef");
        return new ClassdefNode(ctx, this);
    }

    @Override
    public AstNode visitArglist(Python3Parser.ArglistContext ctx) {
//        DomUtils.log("visitArglist");
        return new ArglistNode(ctx, this);
    }

    @Override
    public AstNode visitArgument(Python3Parser.ArgumentContext ctx) {
//        DomUtils.log("visitArgument");
        return new ArgumentNode(ctx, this);
    }

    @Override
    public AstNode visitComp_iter(Python3Parser.Comp_iterContext ctx) {
//        DomUtils.log("visitComp_iter");
        return new CompIterNode(ctx, this);
    }

    @Override
    public AstNode visitComp_for(Python3Parser.Comp_forContext ctx) {
//        DomUtils.log("visitComp_for");
        return new CompForNode(ctx, this);
    }

    @Override
    public AstNode visitComp_if(Python3Parser.Comp_ifContext ctx) {
//        DomUtils.log("visitComp_if");
        return new CompIfNode(ctx, this);
    }

    @Override
    public AstNode visitEncoding_decl(Python3Parser.Encoding_declContext ctx) {
//        DomUtils.log("visitEncoding_decl");
        return new EncodingDeclNode(ctx, this);
    }

    @Override
    public AstNode visitYield_expr(Python3Parser.Yield_exprContext ctx) {
//        DomUtils.log("visitYield_expr");
        return new YieldExprNode(ctx, this);
    }

    @Override
    public AstNode visitYield_arg(Python3Parser.Yield_argContext ctx) {
//        DomUtils.log("visitYield_arg");
        return new YieldArgNode(ctx, this);
    }

    @Override
    public AstNode visitChildren(RuleNode node) {
//        DomUtils.log("visitChildren");
        return null;
    }

    @Override
    public AstNode visitTerminal(TerminalNode node) {
//        DomUtils.log("visitTerminal");
        return null;
    }

    @Override
    public AstNode visitErrorNode(ErrorNode node) {
//        DomUtils.log("visitErrorNode");
        return null;
    }

}
