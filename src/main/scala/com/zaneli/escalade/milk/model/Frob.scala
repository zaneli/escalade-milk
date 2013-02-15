package com.zaneli.escalade.milk.model

import scala.xml.Node

case class Frob private (frob: String) extends Result {

}

object Frob {
  def apply(node: Node): Frob = {
    new Frob(node.text)
  }
}