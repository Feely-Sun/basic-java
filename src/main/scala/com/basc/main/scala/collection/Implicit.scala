package com.basc.main.scala.collection

/**
  * @author sunzihan
  * @version $Id: Implicit.java V 0.1 4/14/17 10:53 sunzihan EXP $
  */
object Implicit extends App{
  implicit val s:Int =1

  implicit val bfd : String = "abc"

  val test  = new Test()

  implicit def test2RunTest(test:Test) : RunTest= new RunTest(test)


  def testParam(implicit bf : RunTest): Unit ={
    println("name==" + bf)
  }

  test.run

  val arry = Array(("a","b"),("c","d"))

  val rr = arry.flatMap( entry=> entry._1 )

  rr.foreach({x => val  res = x.toString();

    println("res==" + res)})

  /***
    *  业务:
    *   1,在线配置一个Velocity策略进行累计

    *   2,此时需要配置一个新的策略的变量
    *
    *
    *   需要具备的能力:

           累计还没有开始,这条新的策略的累计数据需要从离线回流出来

    现在情况:
        首先,变量在策略,模型中使用触发的时候---> 关联指标--->协议--->运行velocity查询,

        从策略id_主体 拼接rowkey ---> 查询 返回结果




    把历史某一个时间窗内的历史事件  进行离线回放触发策略
    *
    *
    *
    *
    *
    *
    *
    *
    *
    */
}

class Test{

}

class RunTest(test:Test){
  def run: Unit ={
    println("RunTest--->"+ test)
  }

}
