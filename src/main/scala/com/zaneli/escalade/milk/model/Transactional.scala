package com.zaneli.escalade.milk.model

import scala.xml.Node

case class Transactional[A <: Result] (transaction: Transaction, result: A) extends Result {

}

case class Transaction private (id: Long, undoable: Boolean) extends Result {

}
object Transaction {
  def apply(node: Node): Transaction = {
    new Transaction((node \ "@id").text.toLong, (node \ "@undoable").text == "1")
  }
}
