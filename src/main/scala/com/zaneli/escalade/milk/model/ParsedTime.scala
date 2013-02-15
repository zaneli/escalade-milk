package com.zaneli.escalade.milk.model

import scala.xml.Node

case class ParsedTime private (precision: String, value: String) extends Result {

}

object ParsedTime {
  def apply(node: Node): ParsedTime = {
    new ParsedTime((node \ "@precision").text, node.text)
  }
}