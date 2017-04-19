package com.basc.main.scala.study.ch21


import scala.collection.mutable.ListBuffer

/**
  * @author sunzihan
  * @version $Id: AbstractDemo.java V 0.1 4/11/17 20:53 sunzihan EXP $
  */
class AbstractDemo {

  abstract class Abs{

    // 's' 代表变量s的值
    val s : String

    //'m' 代表方法
    def m : String
  }


  class ConcretClass(override val s : String = "h") extends Abs{
    //'m' 代表方法
    override def m: String = "hello world"
  }
}

object AbstractDemo{
  def main(args: Array[String]) {
    //隐士转换
    implicit def IntToStr(x : Int) = x.toString()


    def foo(str : String) = {
      println(str)
    }

    val s :List[String] = List("1")

    foo(2)

  }

  implicit def stringWarpper(str : String) = new Rand {
    override def exists( met: String => Int): String  = {
      if (met(str) != -1) "find" else "not find"
    }
  }
}

trait Rand {
  def exists( s: String => Int) : String
}
object Test{

}


//scala 的特质把java的抽象类和接口合2为1的感觉
trait Similarity {
  this:Similarity=>
  def isSimilar(x: Any): Boolean
  def isNotSimilar(x: Any): Boolean = isSimilar(x)


}


class A

class B

class C


class SimilarityImpl(key : String, v: Int) extends Similarity{
  self=> //this  别名

  override def isSimilar(x: Any): Boolean = x match {
    case y:String => if (y.length > 2) true else false
  }

  def printlnKV(): Unit ={
    println("key:" + self.key + ",value:" + self.v )
  }

}


trait AA{
  this:Similarity=>


}

class AAImpl extends AA  with Similarity{
  override def isSimilar(x: Any): Boolean = true
}


object MainTest{

  def main(args: Array[String]) {
    val si =  SimilarityImpl("1",1)


    if (si.isNotSimilar("204")){
       if (si.isInstanceOf[SimilarityImpl]){
         val s = si.asInstanceOf[SimilarityImpl]
         s.printlnKV()
       }
    }
  }

}


object SimilarityImpl {

  def apply(key: String,value : Int) : Similarity = new SimilarityImpl(key,value)

}


package animal{
   package  cat{
     package types{

       class TestPack

     }
   }

}

package com {

  package horstmann {
    object Utils {
      def percentof (value: Double, rate: Double) = value * rate / 100

    }
      package impatient {
        class Employee{
          def giveRaise(value : Double,rate : Double) {
            var salary : Double = 0
            salary= Utils.percentof(value ,rate )
        }
      }
  }
}
}




