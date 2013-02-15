package com.zaneli.escalade.milk.model

import scala.xml.Node
import scala.xml.Elem

private[milk] case class LongResult private (value: Int) extends Result {

}

private[model] object LongResult {
  def apply(node: Node): LongResult = {
    new LongResult(node.text.toInt)
  }
}