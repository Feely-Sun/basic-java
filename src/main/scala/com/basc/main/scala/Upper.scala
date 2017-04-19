package com.basc.main.scala

/**
  * @author sunzihan
  * @version $Id: Upper.java V 0.1 4/1/17 17:50 sunzihan EXP $
  */
class Upper {

  def upper(strings : String*) : Seq[String] = {

    strings.map((s :String) => s.toUpperCase())

  }

}
