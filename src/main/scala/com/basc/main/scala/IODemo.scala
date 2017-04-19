package com.basc.main.scala

import scala.io.Source

/**
  * @author sunzihan
  * @version $Id: IODemo.java V 0.1 4/5/17 18:20 sunzihan EXP $
  */
class IODemo {


  def read(path :String ) = {
    if (path.length > 0){
      for (line <- Source.fromFile(path).getLines() ){
        println(line.length + "  " + line )
      }
    }

  }





}


object IODemo{

  def main(args: Array[String]) {


    val io = new IODemo;

    //io.read("/Users/sunzihan/input/kinglear.txt");

    val lines =Source.fromFile("/Users/sunzihan/input/kinglear.txt").getLines()

   for (line <- lines) println(line)

    //val longest = lines.reduceLeft((a,b) => if(a.length > b.length) a else b)

   // print(longest)




  }












}


class IO[T,B]{

//  def using[ A <:def close():Unit , B](resource: A)(f: A => B): B ={
//    try {
//      f(resource)
//    } finally {
//      resource.close()
//    }
//  }


//  using(io.Source.fromFile("example.txt")) { source => {
//    for (line <- source.getLines) {
//      println(line)
//    }
//  }
}