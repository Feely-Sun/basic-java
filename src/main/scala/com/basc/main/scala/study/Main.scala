package com.basc.main.scala.study

/**
  * @author sunzihan
  * @version $Id: Main.java V 0.1 4/7/17 10:27 sunzihan EXP $
  */
class Main {

}




object Main {


  def main(args: Array[String]) {
    val s : String = "hello,world"
    val rt = s.split("[, ! .] + ")
    rt.length

    val res = rt.isInstanceOf[Array[String]]

    print(res)



  }

  def xx(i:Int)(j:Int) = i+j
   xx(1){2} //result: 3
  (xx(1)_){3} //curry化调用
  (xx(1)_)(3) //curry化调用，不信你不懵





}