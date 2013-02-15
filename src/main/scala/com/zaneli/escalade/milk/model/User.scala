package com.zaneli.escalade.milk.model

import scala.xml.Node

case class User private (id: Long, username: String, fullname: Option[String] = None) extends Result {

}

object User {
  def apply(id: Long, username: String, fullname: String): User = {
    new User(id, username, Some(fullname))
  }

  def apply(node: Node): User = {
    new User((node \ "@id").text.toLong, (node \ "username").text)
  }
}