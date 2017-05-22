package com.basc.main.scala

import scala.reflect.ClassTag

/**
  * @author sunzihan
  * @version $Id: ScalaTest.java V 0.1 4/24/17 14:19 sunzihan EXP $
  */
class ScalaTest {
  def func(msg:String) = println(msg)


}

object Test{

  class IntWritable(_value:Int){
    def value = _value
    def +(that:IntWritable): IntWritable ={
      new IntWritable(that.value + value)
    }
  }
  //
  implicit  def intToWritable(int:Int)= new IntWritable(int)
//  implicit  def writableToInt(that:IntWritable)=that.value


  /**
    * 发生类型不匹配的函数调用时,
    * scala会尝试进行类型隐式转换;首先优先进行函数参数的类型转换,如果可以转换,
    * 那么就完成函数的执行;
    * 否则尝试去对函数调用对象的类型进行转换; 如果两个尝试都失败了,就会报方法不存在或者类型不匹配的错误;
    *
    *
    */
//  def main(args: Array[String]) {
//
////    val rt   = 10.+ (new IntWritable(10))
////    println(rt)
//
//
//
//    val inp = new In[String]
//
//    inp.fn(new ScalaTest)
//
//  }

}


class Point(val x: Int = 0, val y: Int = 0){
  val origin = new Point  // x and y are both set to 0
  val point1 = new Point(1)
  println(point1.x)  // prints 1
}


class Points {
  private var _x = 0

  private val bound = 100

  def x = _x

  def x_= (newValue : Int) : Unit = {
    if (newValue < bound) _x = newValue else printWarning

  }


  private def printWarning = printf("warning")
}





class In[A] {
  def fn[T >: A](x:T): Unit ={
    println(x)


  }
}

class Link[+T](val head: T, val tail: Link[T]) {
  def prepend[U >: T](newHead: U): Link[U] = new Link(newHead, this)
}


class Abc

/**
  * 1,scala的类和方法、函数都可以是泛型。
   *
  * 6
  * 7  * 2,关于对类型边界的限定分为上边界和下边界（对类进行限制）
  * 8  * 上边界：表达了泛型的类型必须是"某种类型"或某种类型的"子类"，语法为“<:”,
  * 9  * 下边界：表达了泛型的类型必须是"某种类型"或某种类型的"父类"，语法为“>:”,
  * 10  *
  * 11
  * 12  * 3, "<%" :view bounds可以进行某种神秘的转换，把你的类型在没有知觉的情况下转换成目标类型，
  * 13  * 其实你可以认为view bounds是上下边界的加强和补充，语法为："<%"，要用到implicit进行隐式转换（见下面例子）
  * 14  *
  * 15
  * 16  * 4,"T:classTag":相当于动态类型，你使用时传入什么类型就是什么类型，（spark的程序的编译和运行是区分了Driver和Executor的，只有在运行的时候才知道完整的类型信息）
  * 17  * 语法为："[T:ClassTag]"下面有列子
  * 18  *
  * 19
  * 20  * 5,逆变和协变：-T和+T（下面有具体例子）+T可以传入其子类和本身（与继承关系一至）-T可以传入其父类和本身（与继承的关系相反），
  * 21  *
  * 22
  * 23  * 6,"T:Ordering" :表示将T变成Ordering[T],可以直接用其方法进行比大小,可完成排序等工作
  * 24  */

 class Person(val name:String){
     def talk(person:Person){
         println(this.name+" speak to "+person.name)
       }
   }

 class Worker(name:String)extends Person(name)

 class Dog(val name:String)




 //注意泛型用的是[]
 class Club[T<:Person](p1:T,p2:T){//"<:"必须是person或person的子类
     def comminicate = p1.talk(p2)
   }


 class Club2[T<%Person](p1:T,p2:T){
     def comminicate = p1.talk(p2)
   }

 class Engineer

 class Expert extends Engineer
 //如果是+T，指定类型为某类时，传入其子类或其本身
 //如果是-T，指定类型为某类时，传入其父类或其本身
 class Meeting[+T]//可以传入T或T的子类

 class Maximum[T:Ordering](val x:T,val y:T){
     def bigger(implicit ord:Ordering[T])={
         if(ord.compare(x, y)>0)x else y
       }
   }

// object HelloScalaTypeSystem {
//     def main(args: Array[String]): Unit = {
//          val p= new Person("Spark")
//          val w= new Worker("Scala")
//
//          val dd = new Dog("dahuang")
//          new Club(p,w).comminicate
//
//     //"<%"的列子
//          //只是提供了一个转换的方法，在遇到<%时会调用看dog是否被转换了。
//          implicit def dog2Person(dog:Dog)=new Person(dog.name)
//          val d = new Dog("dahuang")
//          //注意必须强制类型转换，implicit中虽然是将dog隐式转换成person，
//          //但是其实是对象擦除，变成了object，所以还要强制类型转换成person后才能使用
//          //用[person]强制转换
//          new Club2[Person](p,d).comminicate
//
//      //-T +T例子,下面的participateMeeting方法指定具体是什么泛型
//          val p1=new Meeting[Engineer]
//          val p2=new Meeting[Expert]
//          participateMeeting(p1)
//          participateMeeting(p2)
//      // T:Ordering 的例子
//          println(new Maximum(3,5).bigger)
//          println(new Maximum("Scala","Java").bigger)
//
//
//
//       }
//     //这里指定传入的泛型具体是什么
//     def participateMeeting(meeting:Meeting[Engineer])=  println("welcome")
//
//   }



/*
 *  泛型[]，中括号F、S、T都表示运行时参数类型，
 * ClassTag[T]保存了泛型擦除后的原始类型T,提供给被运行时的。
 */
class Triple[F: ClassTag, S, T](val first: F, val second: S, val third: T)

object HelloTypeParam {
//  def main(args: Array[String]): Unit = {
//
//    // 运行执行代码：val triple: Triple[String, Int, Double]
//    val triple = new Triple("Spark", 3, 3.1415)
//
//    // 运行执行代码：val bigData: Triple[String, String, Char]
//    val bigData = new Triple[String, String, Char]("Spark", "Hadoop", 'R');
//
//    // getData函数传入泛型为T的运行时List类型参数，返回list.length / 2的整数。
//    def getData[T](list:List[T]) = list(list.length / 2)
//    // List索引从0开始，执行结果：Hadoop
//    println(getData(List("Spark","Hadoop",'R')));
//
//    // 获得getData函数引用
//    val f = getData[Int] _
//    // 调用getData函数，执行结果：4
//    println(f(List(1,2,3,4,5,6)));
//
//
//
//
//  }

  def main(args: Array[String]): Unit = {
//    // A =:=B // 表示A类型等同于B类型
//    // A <:<B   // 表示A类型是B类型的子类
//    def rocky[T](i: T)(implicit ev: T <:< java.io.Serializable) {
//      // 执行结果：Life is short ,you need spark!!!
//      println("Life is short ,you need spark!!!" + i + ev)
//    }
//    rocky("Spark")
//
//
//    /*
// * ClassTag:在运行时指定，在编译时无法确定的
// */
//    def mkArray[T:ClassTag](elems:T*) = Array[T](elems:_*)
//
//    /*
//     *  执行结果：
//     *  42
//     *  13
//     */
//    mkArray(42,13).foreach(println)
//
//    /*
//     * 执行结果：
//     * Japan
//     * Brazil
//     * Germany
//     */
//    mkArray("Japan","Brazil","Germany").foreach(println)
//
//
    val p = new Points

    p.x =200


  }




}