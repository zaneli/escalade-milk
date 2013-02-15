package com.zaneli.escalade.milk.model

import scala.xml.Node

case class Location private (
  id: Long, name: String, longitude: Float, latitude: Float, zoom: Int, address: String, viewable: Boolean) extends Result {

}

object Location {
  def apply(node: Node): Location = {
    new Location(
      (node \ "@id").text.toLong,
      (node \ "@name").text,
      (node \ "@longitude").text.toFloat,
      (node \ "@latitude").text.toFloat,
      (node \ "@zoom").text.toInt,
      (node \ "@address").text,
      (node \ "@viewable").text == "1")
  }
}