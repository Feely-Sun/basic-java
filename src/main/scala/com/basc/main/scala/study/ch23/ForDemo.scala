package com.basc.main.scala.study.ch23

import scala.collection.mutable.ListBuffer

/**
  * @author sunzihan
  * @version $Id: ForDemo.java V 0.1 4/13/17 10:16 sunzihan EXP $
  */
class ForDemo{
}


object ForDemo extends App{

  def map[A,B](xs: List[A],f:A=>B): List[B] ={
    for (x <-xs )  yield f(x)
  }

  val rt:List[String]  = ForDemo.map[Int,String](List(1,2,3,4,-1,10), _.toString())

  println("restult ==" + rt)

  def filter[A](xs:List[A],f:A=>Boolean) : List[A] ={
    for (x <-xs if f(x) ) yield x
  }

  //val cx = new DefList



//  val ilist =cx.map(_.toString())
//
//  println("Ilist ===" + ilist)

 val b = new B

  println(b.a())



}

class B extends A{
  override def a(): A = {
    new B
  }
}


abstract class A {

  def a():A

}


abstract class MyList[A]{
  //定义map
  def map[B](f : A => B):MyList[B]

  //定义FlatMap
  def flatMap[B](f : A=>MyList[B]):MyList[B]

  //filter
  def filter(p:A=>Boolean) : MyList[A]

  //foreach
  def foreach(b: A=>Unit):Unit

}

//class DefList extends MyList[Int]{
//
//  var tmp = new ListBuffer[Int]
//
//  val myList = List(1,2,3,4,5,6,7,8)
//
//}




