package com.basc.main.scala.clss


/**
  * @author sunzihan
  * @version $Id: Persion.java V 0.1 4/5/17 14:00 sunzihan EXP $
  */
 class Persion {






}




object Persion {




  def main(args: Array[String]) {

    //    val thrill = "will" :: "fill" :: "unitl" :: Nil
//    println(thrill(1))
//
//    def rt = thrill.count(x => x.eq("unitl"))
//
//    val r = thrill.forall(s => s.endsWith("1"))
//    println(r)
//    println(rt)
//
//    //元祖
//    val pair = (99,"Luftballons")
//    println(pair._1)
//    println(pair._2)

    implicit class Cat(val name:String){
      def call(): String ={
        name

      }
    }

    implicit def convert(v : String) = v.toInt


    val rt =  "kite".call()

    val s :String = "2"+4

    println(s)



    println(rt)


  }


}
