package com.zaneli.escalade.milk.model

import scala.xml.Node

case class Token private (token: String, perms: Perms, user: User) extends Result {

}

object Token {
  def apply(node: Node): Token = {
    new Token(
      (node \ "token").text,
      Perms((node \ "perms").text),
      User((node \ "user" \ "@id").text.toLong, (node \ "user" \ "@username").text, (node \ "user" \ "@fullname").text))
  }
}