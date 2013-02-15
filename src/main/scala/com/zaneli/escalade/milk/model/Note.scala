package com.zaneli.escalade.milk.model

import java.util.Date

import scala.xml.Node

case class Note private (id: Long, created: Date, modified: Date, title: String, body: String) extends Result {

}

object Note {
  def apply(node: Node): Note = {
    new Note(
      (node \ "@id").text.toLong,
      ModelUtil.parseDate((node \ "@created").text).get,
      ModelUtil.parseDate((node \ "@modified").text).get,
      (node \ "@title").text,
      node.text)
  }
}