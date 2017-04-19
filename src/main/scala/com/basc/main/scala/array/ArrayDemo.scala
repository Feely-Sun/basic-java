package com.basc.main.scala.array

import scala.collection.mutable.ArrayBuffer

/**
  * @author sunzihan
  * @version $Id: ArrayDemo.java V 0.1 4/5/17 12:33 sunzihan EXP $
  */
object ArrayDemo {


  def main(args: Array[String]) {
    //1,定义一个数组
    val arry = new Array[Int](10);

    //存值
    arry(0) = 2
    arry(1) = 3
    println(arry(1))

    //2 构造初始化的数组

    val initArry = Array("I","am","array","with","init")// 内部调用了 Array.applay()

    //边长数组


    val change = new ArrayBuffer[Int](20)

    change+=2; // == chanage.+=(2)

    //数组的遍历

    //直接遍历元素
    for (ele <- initArry if ele.eq("init")){
      println(ele+" " )
    }

    //遍历index
    for(i <- 0 until initArry.length) {
      println(initArry(i))


    }


    //指定step

    for (i <- 0 until (initArry.length,2)){

    }








  }









}


class SomeClass {

  def apply(key: String) :String = {
    println("apply method called, key is : " + key)
    "Hello World"
  }

}