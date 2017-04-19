package com.basc.main.scala.DI

/**
  * @author sunzihan
  * @version $Id: UserServiceComponent.java V 0.1 4/13/17 13:17 sunzihan EXP $
  */
trait UserServiceComponent {
  //依赖
  self : UserRepositoryComponent =>


  val userSevice : UserService



}
