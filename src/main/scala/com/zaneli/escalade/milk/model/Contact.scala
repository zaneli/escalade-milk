package com.zaneli.escalade.milk.model

import scala.xml.Node

case class Contact private (id: Long, username: String, fullname: String) extends Result {

}

object Contact {
  def apply(node: Node): Contact = {
    new Contact((node \ "@id").text.toLong, (node \ "@username").text, (node \ "@fullname").text)
  }
}