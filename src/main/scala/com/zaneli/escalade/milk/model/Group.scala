package com.zaneli.escalade.milk.model

import com.zaneli.escalade.milk.RtmException

import scala.xml.Node

case class Group private (id: Long, name: String, contacts: Array[Contact]) extends Result {

}

object Group {
  def apply(node: Node): Group = {
    val contactsNode = node \ "contacts"
    val contacts = contactsNode.length match {
      case 1 => ArrayResult.apply(contactsNode.head, classOf[Contact])
      case _ => throw new RtmException(-1, "Unexpected response: " + node)
    }
    new Group((node \ "@id").text.toLong, (node \ "@name").text, contacts.values)
  }
}