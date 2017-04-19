package com.basc.main.scala.DI.obj

import com.basc.main.scala.DI._

/**
  * @author sunzihan
  * @version $Id: ComponentRegistry.java V 0.1 4/13/17 13:20 sunzihan EXP $
  */
object ComponentRegistry {


  def main(args: Array[String]) {
    val uservie = new UserService {

    }
//    val a = FakeUserServiceRepo.getInstance(uservie)
//    println("a=====" + a)
  }
}

//class FakeUserRepo( override val userSevice: UserService) extends UserServiceComponent with UserRepositoryComponent{
//  override val userRepository: UserRepository ;
//}


class FakeUserService {
  val s = "a"
  val b = 1
   class InnerService{}
}

//hello
object FakeUserServiceRepo{
//  def getInstance(userService :UserService): FakeUserRepo ={
//     new FakeUserRepo(userService)
//  }
}


//abstract class Graph {
//  type Edge
//  type Node <: NodeIntf
//  abstract class NodeIntf {
//    def connectWith(node: Node): Edge
//  }
//  def nodes: List[Node]
//  def edges: List[Edge]
//  def addNode: Node
//}


//abstract class DirectedGraph extends Graph {
//  type Edge <: EdgeImpl
//  class EdgeImpl(origin: Node, dest: Node) {
//    def from = origin
//    def to = dest
//  }
//  class NodeImpl extends NodeIntf {
//    def connectWith(node: Node): Edge = {
//      val edge = newEdge(this, node)
//      edges = edge :: edges
//      edge
//    }
//  }
//  protected def newNode: Node
//  protected def newEdge(from: Node, to: Node): Edge
//  var nodes: List[Node] = Nil
//  var edges: List[Edge] = Nil
//  def addNode: Node = {
//    val node = newNode
//    nodes = node :: nodes
//    node
//  }
//
//  class ConcreteDirectedGraph extends DirectedGraph {
//    type Edge = EdgeImpl
//    type Node = NodeImpl
//    protected def newNode: Node = new NodeImpl
//    protected def newEdge(f: Node, t: Node): Edge =
//      new EdgeImpl(f, t)
//  }





//}

