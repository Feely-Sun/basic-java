package com.basc.main.scala.clss

/**
  * @author sunzihan
  * @version $Id: Counter.java V 0.1 4/5/17 13:54 sunzihan EXP $
  */
class Counter {


  private var value = 0


  def increment(): Unit ={
    value +=1
  }


  def current = value



}


object Counter{


  def main(args: Array[String]) {



    val ct = new Counter;

    ct.increment()

    //取值 不用()
    val rt = ct.current


    print(rt)




  }
}
