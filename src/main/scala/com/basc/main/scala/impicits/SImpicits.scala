package com.basc.main.scala.impicits

/**
  * @author sunzihan
  * @version $Id: SImpicits.java V 0.1 4/25/17 10:47 sunzihan EXP $
  */
class SImpicits {


  /**
    * 1,定义在class中还是object中?
    */

  def sum(x: Int, y: Int) = x + y

//  implicit def stringToInt(from :String):Int =  from.toInt

}


object SImpicits {

  def main(args: Array[String]) {

    val sp = new SImpicits
    val rt = sp.sum(1, 2)
    println(rt)
  }


}
