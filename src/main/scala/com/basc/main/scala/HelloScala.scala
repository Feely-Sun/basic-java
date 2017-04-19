package com.basc.main.scala

import scala.collection.mutable.ArrayBuffer;

/**
  * @author sunzihan
  * @version $Id: HelloScala.java V 0.1 3/25/17 21:44 sunzihan EXP $
  */
object HelloScala {


  def main(args: Array[String]) {
    //未初始化,定长数组
    val nums =new Array[Int](10)

    //初始化 省略new
    val str = Array("Hello","World")

    //print(str(0))



    val b = ArrayBuffer[Int]()

    b.+=:(20)

    b += (1,2,3,4,5)


    b.++=(intArrayOps(Array(8, 23, 32)))


//    for (i <- b.indices){
//      println("index=" + i+" and value=" + b(i))
//    }

    for (ele <- b){
      println(ele)
    }




   // print(b)

  }


}


class HelloScala{
  def say(content:String):String = {
     return "I am saying:[" + content + "]";
  }


}


object app extends App{

  println(
    """ Welocime to Ultmaix 3000.
      | Type "HELP" for help
    """.stripMargin)

  val ra = new Rationnal(3)
  val rt = ra.lessThan(new Rationnal(1,2))
  println(ra gcd(27,6))
  val x = new Rationnal(2,3)
  val y = new Rationnal(1,4)
  val z = x+y

  println("z====" + z)

  println("x+4=" + (x + 4) )
}

class Rationnal(n : Int ,d :Int){
  require(d != 0)
  override def toString = n + "/" + d

  val num:Int = n
  val denom : Int = d

  def add(that:Rationnal):Rationnal = new Rationnal(num * that.denom + that.num*denom,denom*that.denom)

  def + (that:Rationnal):Rationnal = {
    new Rationnal(this.num * that.denom + that.num * this.denom,this.denom * that.denom)
  }

  def + (i : Int) :Rationnal = new Rationnal(this.num + i * this.denom,denom)

  def lessThan(that: Rationnal) = {
    println("this.num=>" + this.num)
    println("this.denom= >" + this.denom)
    this.num * that.denom < that.num * this.denom
  }

  def max (that : Rationnal) ={
    if(this.lessThan(that)) that else this
  }



  implicit def intToRational(x : Int) = new Rationnal(x)


  //辅助构造器( 等同于java中的有参构造方法 )
  def this(n :Int) = this(n,1)

  def gcd(a:Int,b:Int) : Int = if (b==0) a else gcd(b,a%b)
}





class Ra(f : Int, s : String)



