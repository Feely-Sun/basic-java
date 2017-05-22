package com.basc.main.scala

/**
  * @author sunzihan
  * @version $Id: MixinComposition.java V 0.1 4/25/17 21:26 sunzihan EXP $
  */
class MixinComposition {

}



abstract class AbsIterator{
  type T

  def hasNext : Boolean

  def next: T

}


trait RichIterator extends AbsIterator{

  def foreach (f : T => Unit): Unit ={
    while(hasNext){
      f(next)
    }
  }
}

class StringIterator(s: String) extends AbsIterator{
  override type T = Char

  override def next: T = {

    val ch = s.charAt(i)

    i +=1

    ch
  }

  private var i = 0

  override def hasNext: Boolean = {
    i < s.length()
  }

}


object StringIteratorTest{



  def main(args: Array[String]) {
//
//    class Iter extends StringIterator("hello") with RichIterator
//    val iter = new Iter
//
//    iter.foreach(x => print(x))



  }

}

class AnyMous{



  new Function1[Int, Int]{
    override def apply(v1: Int): Int = v1 + 1
  }
}

object AnonymousFunction{
  //functions
  val pulsone = (x:Int) => x+1


  def main(args: Array[String]) {
//    val f = (x : Int) => x.toString
//
//
//    def apply(f: Int => String, v: Int) = f(v)
//    val decorator = new Decorator("[", "]")
//    println(apply(decorator.layout, 7))
//    println(factorial(5))
    def containsScala(x: String): Boolean = {
      val z: Seq[Char] = x
      z match {
        case Seq('s','c','a','l','a', rest @_*) =>
          println("rest is "+rest)
          true
        case Seq(_*) =>
          false
      }
    }

    println(containsScala("scala is good languane"))

    val x = Twice(21)

    println(x.isInstanceOf[Int])
    x match {
      case Twice(n) => Console.println(n)
    } // prints 21

    val pattern = "(\\d+) (\\w+)".r //定义正则
    "1234 abc" match {
      case pattern(number, chars) => println( number +" " + chars)
    }

    val ALIBABA = "hello"

    val  Wather = "dfds"

    def test(site:String) = site match {
      case Wather => println(Wather)
      case _ => println("not match")
    }

    def foo(s:String) {
      s match {
        case ALIBABA => println("ok")
      }
    }

    foo("hello")


    test("sdf")


    List(1,2) match{ case List(x,2) => println(x) }


  }

  def factorial(x: Int): Int = {
      if (x <= 1) x
      else x * factorial(x - 1)
  }
}

class Decorator(left: String, right: String) {
  def layout[T](x:T) = x.toString

}

class X {
  import X._
 // def blah = foo
  private def cx = 1200
  X.hi()
  val x = new X

}



object X {
  import com.basc.main.scala.X._
  private def foo = 42
  val tmp = 100
  def hi()= {
    println("HI,Good Moning")
  }
}


object Twice {
  def apply(x: Int): Int = x * 2
  def unapply(z: Int): Option[Int] = if (z%2 == 0) Some(z/2) else None
}


class ImplicitParameterDemo {

}

object ImplicitParameterDemo {

  def testParam(implicit name: String) {
    println(name)
  }

  implicit val name = "lujinhong"



  def main(args: Array[String]) {
    testParam("My name")
    testParam
  }
}


object MyTestApp{
  def main(args: Array[String]): Unit = {
    val myTest = new MyTest();
    myTest.printInt(4)
    myTest.printYourTest(myTest)  // the source type is of MyTest while the target require YourTest
    myTest.fuxkTest()  //  there is no method on MyTest but we can still call it
  }
}

class YourTest{
  override def toString = "Your Test"
  def  fuxkTest() = print("fuxk Test");
}

object YourTest{
  implicit def myTest2YourTest = new YourTest
}

class MyTest{
  import MyTest._
  def printStr(str: String) = println(str)

  // you can't do it like `printStr(i)` unless you bring the implicit converter `MyTest.int2String`into scope
  def printInt(i: Int) = printStr(i)

  def printYourTest(obj: YourTest) = println(obj)

  def getYorTest(): YourTest = this;
}

object  MyTest {
  implicit def int2String(i: Int ): String = i.toString
  implicit def myTest2YourTest(obj: MyTest): YourTest= new YourTest
  //  implicit val myTest2YourTest = (obj : MyTest) => new YourTest
}

//抽象节点
trait Node
//具体的节点实现，有两个子节点
case class TreeNode(v:String, left:Node, right:Node) extends Node
//Tree，构造参数是根节点
case class Tree(root:TreeNode)


object caseTest{

  val tree = Tree(TreeNode("root",TreeNode("left",null,null),TreeNode("right",null,null)))

  def main(args: Array[String]) {
    tree.root match {
      case TreeNode(_,TreeNode("left",_,_),TreeNode("right",null,null)) => println("bingo")
      case _ => println("not match....")
    }

     foo(List("ab","b"))
     foo(List(1,2,3,4))
    val  cs = A (2,3)

    val t = (100,100)
    val tuple = A.tupled
  }
  def foo(a:Any) = a match {
    case b :List[_] => println(b.::("abc"));
    case _ => println("cannot find")
  }


  def test(x:String ,y : String) = {
    x+y
  }



  val part = test("a",_:String)

  println(part("b"))


  (test _).curried


}

case class A(x: Int, y:Int)

case class B(x: Int)



