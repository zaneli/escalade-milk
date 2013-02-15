package com.zaneli.escalade.milk.model

import scala.xml.Node
import scala.xml.Elem

private[milk] case class ArrayResult[A <: Result] private (values: Array[A]) extends Result {

}

private[milk] object ArrayResult {
  def apply[A <: Result: ClassManifest](node: Node, model: Class[A]): ArrayResult[A] = {
    val children = node.child
    val modelClass = implicitly[ClassManifest[A]]
    val results = children.filter { child: Node =>
      child.isInstanceOf[Elem]
    }.map { child: Node =>
      ModelUtil.createModelCompanion(model, child)
    }
    new ArrayResult(results.toArray)
  }
}