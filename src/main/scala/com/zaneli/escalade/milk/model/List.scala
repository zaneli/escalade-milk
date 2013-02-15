package com.zaneli.escalade.milk.model

import scala.xml.Node

case class List private (
  id: Long,
  name: String,
  deleted: Boolean,
  locked: Boolean,
  archived: Boolean,
  position: Int,
  sortOrder: Option[Int], // sort_order は API リファレンスに記載が無く、必ず付与するか不明だったため Option 型としている。
  smart: Boolean) extends Result {

}

object List {
  def apply(node: Node): List = {
    new List(
      (node \ "@id").text.toLong,
      (node \ "@name").text,
      (node \ "@deleted").text == "1",
      (node \ "@locked").text == "1",
      (node \ "@archived").text == "1",
      (node \ "@position").text.toInt,
      (node \ "@sort_order").text match {
        case "" => None
        case x => Some(x.toInt)
      },
      (node \ "@smart").text == "1")
  }
}