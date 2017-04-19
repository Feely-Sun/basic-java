package com.basc.main.scala.study.ch10



/**
  * @author sunzihan
  * @version $Id: Ch10.java V 0.1 4/8/17 20:41 sunzihan EXP $
  */
class Ch10 {




}

abstract class Element{
  //声明一个抽象方法 //返回结果的类型为字符串数组
  //该抽象方法作为抽象类Element的 一个成员
  def contents: Array[String]

  def height: Int = contents.length

  def width : Int = if (height == 0) 0 else contents(0).length

  def demo(): Unit ={
    println("Element's implementation invoked")
  }

}


class ArrayElement(override val contents : Array[String]) extends Element{
  //声明一个抽象方法 //返回结果的类型为字符串数组

  override def demo: Unit ={
    println("ArrayElement's implements invokded")
  }
}


class LineElement(s:String) extends ArrayElement(Array(s)){
  override def width = s.length
  override def height = 1

  override def demo: Unit ={

    println("LineElement invoked")

  }
}

class UniformElement(ch:Char, override val width:Int,override val height : Int) extends Element {
  private val line = ch.toString * width
  //def contents = Array.make(height,line)
  //声明一个抽象方法 //返回结果的类型为字符串数组
  override def contents: Array[String] = Array("dddddd")


}



object Ch10{


  def main(args: Array[String]) {

//    val ae : Element = new ArrayElement(Array("Hello","World"))
//    println(ae.width)
//
//
//
//    def invokeDemo(e : Element): Unit ={
//      e.demo()
//    }
//
//
//    invokeDemo(new UniformElement('x',2,3))
//
//
//    invokeDemo(new LineElement("r"))


//    val a : Animal = new Cat
//
//
//    a.test()
//
//
//    println(a.isInstanceOf[Animal])
//
//    val n = a.asInstanceOf[Animal]
//
//    print(n == a)
//
//
//    print(n.eq(a))
//
//
//
//    val ant = new Ant
//
//    print(ant.env.length)




//    val acct = new SavingsAccount with ConsoleLogger with TimestampLogger
//
//    acct.withDraw(2000)
//
//
//    var sign :Int = 1
//
//    val ch:Char = 'a'
//
//     ch match{
//      case 'a' => sign =  1
//      case 'b' => sign = 2
//    }
//


//    val buf = new ListBuffer[Int]
//
//
//    buf += 1
//
//    buf+=2
//
//
//    println(buf)



    import java.util
    import java.util.List

    import scala.collection.mutable.ListBuffer

    val list = new util.ArrayList[String]

    val jlist: List[String]  = list.asInstanceOf[util.ArrayList[String]]

    jlist.add("a")

    jlist.add("b")

    println("list==="  + list.toString() +",,jlist=="+jlist.toString)




  }

}


class Cat extends Animal {

  def miaomiao: Unit ={
    println("cat can miaomiao")
  }


}





class Createure {
  val range : Int =10

  val env : Array[Int] = new Array[Int](range)
}



class Ant extends {
  override val range = 2
} with Createure{

}





trait Animal{

  def test(): Unit ={
    println("traint method")

  }

}





trait Logged {

  def log(msg : String) {}

}


trait ConsoleLogger extends Logged{

  override def log(msg : String): Unit = {
    println(msg)
  }

}

trait TimestampLogger extends Logged{

  override def log(msg:String): Unit ={

    super.log(new java.util.Date() + ":" + msg)
  }

}


class SavingsAccount extends  Logged{

  def withDraw (amont :Int): Unit ={
    if (amont > 200) log("not enough money")
  }

}











