package com.basic.leanring.java.tiercomponent;

/**
 * @author sunzihan
 * @version $Id: AnalyComponent.java V 0.1 2/27/17 13:32 sunzihan EXP $
 */
public class AnalyComponent implements TierComponent {

    @Override
    public void execute(String type) {
        if (type.equals("A")){
            System.out.println("accsyn");
        }else{
            System.out.println("other");
        }
    }


    @Override
    public void addtion() {
        System.out.println("addtion");
    }
}

