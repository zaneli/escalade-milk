package com.zaneli.escalade.milk.model

import scala.xml.Elem
import scala.xml.Node
import scala.xml.XML

case class Method private (
  name: String,
  needslogin: Boolean,
  needssigning: Boolean,
  requiredperms: Perms,
  description: String,
  response: Option[Elem],
  arguments: Array[Argument],
  errors: Array[Error]) extends Result {

}

object Method {
  def apply(node: Node): Method = {
    new Method(
      (node \ "@name").text,
      (node \ "@needslogin").text == "1",
      (node \ "@needssigning").text == "1",
      Perms((node \ "@requiredperms").text.toInt),
      (node \ "description").text.trim,
      if ((node \ "response").text.trim.isEmpty) None else Some(XML.loadString((node \ "response").text)),
      (node \ "arguments" \ "argument").filter(_.isInstanceOf[Elem]).map(Argument(_)).toArray,
      (node \ "errors" \ "error").filter(_.isInstanceOf[Elem]).map(Error(_)).toArray)
  }
}

case class Argument private (name: String, optional: Boolean, description: String) {

}

object Argument {
  def apply(node: Node): Argument = {
    new Argument((node \ "@name").text, (node \ "@optional").text == "1", node.text.trim)
  }
}

case class Error private (code: Int, message: String, description: String) {

}

object Error {
  def apply(node: Node): Error = {
    new Error((node \ "@code").text.toInt, (node \ "@message").text, node.text.trim)
  }
}