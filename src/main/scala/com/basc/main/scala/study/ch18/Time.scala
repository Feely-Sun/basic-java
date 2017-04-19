package com.basc.main.scala.study.ch18

import java.util

import scala.collection.mutable

/**
  * @author sunzihan
  * @version $Id: Time.java V 0.1 4/10/17 19:13 sunzihan EXP $
  */
class Time {



  println("hello")



  import java.util._

  import scala.collection.mutable._

  object T {

    def main(args: Array[String]) {






    }

  }




}



object Time{

}



class Cell[T](init : T){
  private[this] var current = init
  def get = current

  def set(x : T) = {
    current = x
  }
}

//出版物类
class Publication(val title: String)
//书籍类
class Book(title: String) extends Publication(title)
//图书库类
object Library {
  //定义图书库内所有的书籍
  val books: Set[Book] =
    Set(
      new Book("Programming in Scala"),
      new Book("Walden")
    )
  //打印所有图书内容，使用外部传入的函数来实现
  def printBookList(info: Book => AnyRef) {
    //确认Scala中一个参数的函数实际上是Function1特征的实例
    assert(info.isInstanceOf[Function1[_, _]])
    //打印
    for (book <- books)
      println(info(book))
  }
  //打印所有图书内容，使用外部传入的GetInfoAction特征的实例来实现
  def printBokkListByTrait[P >: Book, R <: AnyRef](
                                                    //传名参数 ()=> 简化 =>
                                                    action : => GetInfoAction[P, R]) {
    //打印
    for (book <- books)
      println(action(book))
  }

}
//取得图书内容特征，P类型参数的类型下界是Book，R类型参数的类型上界是AnyRef
trait GetInfoAction[P >: Book, R <: AnyRef] {
  //取得图书内容的文本描述，对应（）操作符
  def apply(book : P) : R
}

//函数的参数是逆变 而函数的返回值是协变
trait Function2[-S, +T] {
  def apply(x: S): T
}


//单例对象，文件的主程序
object Customer extends App {

//  Library.printBookList(_.title)
//  //使用特征GetInfoAction的实例来打印
//  Library.printBokkListByTrait(new GetInfoAction[Publication, String] {
//    def apply(p : Publication) : String = p.title })


  val foo = F

  val s:Function[Any,String] =  foo;
  val bar:Function[String,Any] = s

  println(s)

}

trait Function[-Arg,+Return]{

  def apply(x:Arg) : Return

}


object F extends Function[Any,String]{
  override def apply(arg: Any): String = s"Hello ,I received a ${arg}"
}














