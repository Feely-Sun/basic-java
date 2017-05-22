package com.basic.leanring.java.antfeature.featurelifetime.antlr;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author sunzihan
 * @version $Id: Calc.java V 0.1 5/17/17 13:04 sunzihan EXP $
 */
public class Calc {

    private static final String root = "/Users/sunzihan/personal/basic-java/src/main/java/com/basic/leanring/java/antfeature/featurelifetime/antlr";

    public static void main(String[] args) throws Exception {
  //        String rootPath= Calc.class.getResource(".").getFile().toString();
  //
  //        System.out.println(rootPath);

            InputStream is = new FileInputStream(root+"/cacl.txt") ;
            ANTLRInputStream input = new ANTLRInputStream(is);

            CaclLexer lexer = new CaclLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CaclParser parser = new CaclParser(tokens);
            ParseTree tree = parser.prog();

//            EvalVisitor eval = new EvalVisitor();
//            // 开始遍历语法分析树
//            eval.visit(tree);


        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new DirectiveListener(), tree);

        // print LISP-style tree
        System.out.println(tree.toStringTree(parser));

        }




}

