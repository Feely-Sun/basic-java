// Generated from /Users/sunzihan/personal/basic-java/src/main/java/com/basic/leanring/java/antfeature/featurelifetime/antlr/Cacl.g4 by ANTLR 4.7
package com.basic.leanring.java.antfeature.featurelifetime.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CaclParser}.
 */
public interface CaclListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CaclParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(CaclParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link CaclParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(CaclParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code printExpr}
	 * labeled alternative in {@link CaclParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterPrintExpr(CaclParser.PrintExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code printExpr}
	 * labeled alternative in {@link CaclParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitPrintExpr(CaclParser.PrintExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assign}
	 * labeled alternative in {@link CaclParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterAssign(CaclParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assign}
	 * labeled alternative in {@link CaclParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitAssign(CaclParser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parens}
	 * labeled alternative in {@link CaclParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParens(CaclParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link CaclParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParens(CaclParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link CaclParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMulDiv(CaclParser.MulDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link CaclParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMulDiv(CaclParser.MulDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link CaclParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAddSub(CaclParser.AddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link CaclParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAddSub(CaclParser.AddSubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code id}
	 * labeled alternative in {@link CaclParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterId(CaclParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code id}
	 * labeled alternative in {@link CaclParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitId(CaclParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code int}
	 * labeled alternative in {@link CaclParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterInt(CaclParser.IntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code int}
	 * labeled alternative in {@link CaclParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitInt(CaclParser.IntContext ctx);
}