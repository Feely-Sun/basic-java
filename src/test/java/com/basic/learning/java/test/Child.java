package com.basic.learning.java.test;

/**
 * @author sunzihan
 * @version $Id: Child.java V 0.1 4/9/17 16:05 sunzihan EXP $
 */
public class Child extends Par {

    public void tests(){
        super.par();
    }

    @Override
    public void ap() {

    }

    @Override
    public void par() {
        System.out.println("childsssss");
    }


    public static void main(String[] args) {
        new Child().tests();


        int r =2;
        switch (r) {
            case 2 :
                System.out.println("r === "+ 2);
                break;

            default:
                System.out.println("cant find");
                break;
        }
    }
}

