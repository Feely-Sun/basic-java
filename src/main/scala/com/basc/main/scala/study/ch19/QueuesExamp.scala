package com.basc.main.scala.study.ch19

import scala.collection.mutable.ListBuffer


/**
  * @author sunzihan
  * @version $Id: QueuesExamp.java V 0.1 4/10/17 19:22 sunzihan EXP $
  */
class QueuesExamp {

}

class Queue[+T] private (private val leading: List[T], private val trailing: List[T]){

           private def mirror = {
             if (leading.isEmpty){
               new Queue[T](trailing.reverse,Nil)
             } else{
               this
             }
           }
       //辅助构造器
       def this(elems : T*){
         this(elems.toList,Nil)
       }


         def head = mirror.leading.head

         def tail = {
           val q= mirror
           new Queue[T](q.leading.tail,q.trailing)
         }

         def append[U >: T](x : U ) = new Queue[U](leading, x :: trailing)


         override def toString(): String ={
           (this.leading ::: this.trailing).toString()
         }



}



object Queue{

  def apply[T](xs : T*) = new Queue[T](xs.toList,Nil)


}



trait Queues[T]{

  def head:T
  def tail : Queues[T]

  def append(x :T) : Queues[T]

}

/**
  *
  * 私有类
  * */
object Queues{


  def apply[T] (xs : T*) : Queues[T]  = new QueuesImpl(xs.toList,Nil)

  private class QueuesImpl[T](private val leading :List[T],private val trailing : List[T] ) extends Queues[T]{


    private def mirror = {
      if (leading.isEmpty){
        new QueuesImpl[T](trailing.reverse,Nil)
      } else{
        this
      }
    }

    override def head: T = mirror.leading.head

    override def append(x: T): Queues[T] = new QueuesImpl[T](leading,x::trailing)

    override def tail: Queues[T] = {
     // val q
      val q = mirror
      new QueuesImpl[T](q.leading.tail,trailing)
    }
  }

}




object QueuesExamp{


  def main(args: Array[String]) {

    def qe(q :Queue[Int]): Unit = {
      println(q.tail)
    }


    val ar = Queue(1,3,4)

    val nar = ar.append(5L)

    println("queue heard===" + ar.head)

    println("new queue tail=" + nar.tail)
  }





}
