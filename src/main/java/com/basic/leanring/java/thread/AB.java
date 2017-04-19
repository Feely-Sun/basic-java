package com.basic.leanring.java.thread;

/**
 * @author sunzihan
 * @version $Id: AB.java V 0.1 3/24/17 12:08 sunzihan EXP $
 */
public class AB {


    public static void main(String[] args) {

//        int x =3 <<2;
//        int y = 3 * 1 <<2;
//        int z =137 ;
//        byte i= 4;
//
//
//        byte[] array = new byte[8];
//        for(int n = 7 ; n >=0 ; n--){
//            array[n] = (byte) (i & 1);
//            i= (byte) (i >> 1);
//        }
//
//
//       for (int j = 0 ; j < array.length ; j ++){
//           System.out.print(array[j]+",");
//       }
//
//
//        byte b = 4>>>1 & 0xf;
//



//        byte b1 = - 4;
//
//        byte pos = 1;
//
//        int res = (b1 >>> pos);
//
//        System.out.println(res >> 6);

        int i = 4;

        int s = Integer.highestOneBit(20);

        System.out.println(i - (i >>> 1));



    }



}

