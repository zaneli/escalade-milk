package com.zaneli.escalade.milk.model

import scala.xml.Node

case class Time private (timezone: String, value: String) extends Result {

}

object Time {
  def apply(node: Node): Time = {
    new Time((node \ "@timezone").text, node.text)
  }
}