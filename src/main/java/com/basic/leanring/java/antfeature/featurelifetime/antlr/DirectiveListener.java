package com.basic.leanring.java.antfeature.featurelifetime.antlr;

/**
 * @author sunzihan
 * @version $Id: DirectiveListener.java V 0.1 5/17/17 14:32 sunzihan EXP $
 */
public class DirectiveListener extends CaclBaseListener {


    @Override
    public void exitPrintExpr(CaclParser.PrintExprContext ctx) {
        System.out.println("RET\n");
    }

    @Override
    public void exitAssign(CaclParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        System.out.println("STR " + id);
    }

    @Override
    public void exitMulDiv(CaclParser.MulDivContext ctx) {
        if (ctx.op.getType() == CaclParser.MUL) {
            System.out.println("MUL");
        } else {
            System.out.println("DIV");
        }
    }

    @Override
    public void exitAddSub(CaclParser.AddSubContext ctx) {
        if (ctx.op.getType() == CaclParser.ADD) {
            System.out.println("ADD");
            int left = ctx.ADD().getChildCount();


        } else {
            System.out.println("SUB");
        }
    }

    @Override
    public void exitId(CaclParser.IdContext ctx) {
        System.out.println("LDV " + ctx.ID().getText());
    }

    @Override
    public void exitInt(CaclParser.IntContext ctx) {
        System.out.println("LDC " + ctx.INT().getText());
    }

}

