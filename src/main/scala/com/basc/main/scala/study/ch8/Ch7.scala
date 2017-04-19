package com.basc.main.scala.study.ch8

import java.io.File

import scala.io.Source

/**
  * @author sunzihan
  * @version $Id: Ch7.java V 0.1 4/7/17 10:31 sunzihan EXP $
  */
object Ch7 {

  def processFile(filename : String, withth :Int): Unit ={

    //本地函数
    def processLine(line : String) ={
      if (line .length > withth){
        println(filename + ": " + line)
      }

    }

    val source = Source.fromFile(filename)

    for (line <- source.getLines()){
      processLine(line)
    }
  }



  def sum(a : Int , b : Int , c : Int) = a+b+c


  //偏函数,sum参数全部未应用
  val partSum = sum _

  //部分偏函数
  val partly = sum(_:Int,2,3)





//  def cls = (x : Int) => x + more



  def closer(x:Int) = {
    val more = 1

    def cls = (x:Int) => x+more

    cls(x)
  }


  def makeIncreaser(more : Int) = (x: Int)=> x+more



  //柯丽华
  def withPrintWriter(file: File)(op:Ch7 => Unit) {
    val ch7 = new Ch7
    op(ch7)
  }


  //传名参数
  def myAssert(predicate :  => Boolean) = {

    if (!predicate)
      throw new AssertionError()

  }






  def inc = (x: Int) => x+1

  def main(args: Array[String]) {


    val res = partly.apply(2) /// partly.apply(2)

//    println("res ==" + res)
//
//
//    println(closer(3))
//

    val inc1=makeIncreaser(1)

//    processFile("/Users/sunzihan/input/kinglear.txt",50)
//    val rt = inc(1)
//    print(rt)


    val some = List(1,2,3,4,5,6,-10)

//    val r = some.filter((x) => x < 0)

//    //部分应用函数
//    some.foreach(println _)




   // print(r)



  }

  private def fileHere =  (new  java.io.File(".")).listFiles()

  //控制抽象 ,高阶函数
  def filesMatching(query:String,matcher:String => Boolean) = {

    for (file <- fileHere if matcher(file.getName) )
      yield file

  }

  //使用高阶函数简化代码
  def fileEnding (query:String) =filesMatching(query,_.endsWith(query))
 //柯丽
  def plainOldSum(x :Int,y :Int) = x+y
  def currentSum(x:Int)(y:Int) = x+y
  val file = new java.io.File("/Users/sunzihan/input/kinglear.txt")
  withPrintWriter(file){
    w =>w.prt("name=" + file.getName)
  }

  myAssert(5>3)
}


class Ch7{
  def prt(str : String) = println(str)

  def prtlin(): Unit ={

    println("x->y")

  }
}

class Animal {}

class Bird extends Animal {}





//class Consumer[-S, +T]() {
//  def m1[U >: T](u: U): T = {
//    new T
//  }
//
//  //协变，下界
//  def m2[U <: S](s: S): U = {
//    new U
//  } //逆变，上界
//}


//class TestextendsApp{
//  val c1 = new Consumer[Animal,Bird]
//
//
//  val c2 : Consumer[Bird,Animal] = c1
//
//  c2.m1(new Animal)
//  c2.m2(new Bird)
//
//
//
//
//}



