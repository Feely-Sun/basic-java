package com.basc.main.scala.collection

/**
  * @author sunzihan
  * @version $Id: CollectionsDe.java V 0.1 4/5/17 17:00 sunzihan EXP $
  */
object CollectionsDe {


  def main(args: Array[String]) {



    //映射

    var jetSet = Set("Boeing","Airbus")


    jetSet+= "Lear"

//    println(jetSet.contains("Cessna"))
//


    val romanNumeral = Map(1-> "I",2 -> "II",3 -> "III")


//    println(romanNumeral.getOrElse(20,"10"))
//
//
//    val s = formatArgs(Array("a","b","c"))
//
//    println(s)



    val lst = Array(1,2)

    val rt = lst.map((x) => {val s = x+ 1 ; if (s>2) s else -1})


    println(rt)

    for (r <- rt){
      println(r)
    }


  }


  def formatArgs(args:Array[String]) = args.mkString("\n")

}
