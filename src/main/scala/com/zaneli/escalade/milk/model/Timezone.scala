package com.zaneli.escalade.milk.model

import scala.xml.Node

case class Timezone private (id: Long, name: String, dst: Boolean, offset: Int, currentOffset: Int) extends Result {

}

object Timezone {
  def apply(node: Node): Timezone = {
    new Timezone(
      (node \ "@id").text.toLong,
      (node \ "@name").text,
      (node \ "@dst").text == "1",
      (node \ "@offset").text.toInt,
      (node \ "@current_offset").text.toInt)
  }
}