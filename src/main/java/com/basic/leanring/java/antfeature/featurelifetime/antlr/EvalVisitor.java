package com.basic.leanring.java.antfeature.featurelifetime.antlr;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sunzihan
 * @version $Id: EvalVisitor.java V 0.1 5/17/17 12:19 sunzihan EXP $
 */
public class EvalVisitor extends CaclBaseVisitor<Integer> {

    /** "memory" for our calculator; variable/value pairs go here */
    Map<String, Integer> memory = new HashMap<String, Integer>();

    /** ID '=' expr */
    @Override
    public Integer visitAssign(CaclParser.AssignContext ctx) {
        String id = ctx.ID().getText();  // id is left-hand side of '='
        int value = visit(ctx.expr());   // compute value of expression on right
        memory.put(id, value);           // store it in our memory
        return value;
    }

    /** expr */
    @Override
    public Integer visitPrintExpr(CaclParser.PrintExprContext ctx) {
        Integer value = visit(ctx.expr()); // evaluate the expr child
        System.out.println("value ===" + value);         // print the result
        return 0;                          // return dummy value
    }

    /** INT */
    @Override
    public Integer visitInt(CaclParser.IntContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }

    /** ID */
    @Override
    public Integer visitId(CaclParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if ( memory.containsKey(id) ){
            return memory.get(id);
        }
        return 0;
    }

    /** expr op=('*'|'/') expr */
    @Override
    public Integer visitMulDiv(CaclParser.MulDivContext ctx) {
        int left = visit(ctx.expr(0));  // get value of left subexpression
        int right = visit(ctx.expr(1)); // get value of right subexpression
        if ( ctx.op.getType() == CaclParser.MUL ) {
            return left * right;
        }
        return left / right; // must be DIV
    }

    /** expr op=('+'|'-') expr */
    @Override
    public Integer visitAddSub(CaclParser.AddSubContext ctx) {
        int left = visit(ctx.expr(0));  // get value of left subexpression
        int right = visit(ctx.expr(1)); // get value of right subexpression
        if ( ctx.op.getType() == CaclParser.ADD ){
            return left + right;
        }
        return left - right; // must be SUB
    }

    /** '(' expr ')' */
    @Override
    public Integer visitParens(CaclParser.ParensContext ctx) {
        return visit(ctx.expr()); // return child expr's value
    }




}

