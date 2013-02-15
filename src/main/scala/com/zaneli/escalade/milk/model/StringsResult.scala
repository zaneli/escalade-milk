package com.zaneli.escalade.milk.model

import scala.xml.Node
import scala.xml.Elem

private[milk] case class StringsResult private (values: Array[String]) extends Result {

}

private[model] object StringsResult {
  def apply(node: Node): StringsResult = {
    new StringsResult(node.child.filter(_.isInstanceOf[Elem]).map(_.text).toArray)
  }
}