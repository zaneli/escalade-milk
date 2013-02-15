package com.zaneli.escalade.milk.model

import java.util.Date

import scala.xml.Node

case class Tasks private (rev: String, taskLists: Array[TaskList]) extends Result {

}

object Tasks {
  def apply(node: Node): Tasks = {
    new Tasks((node \ "@rev").text, (node \ "list").map(TaskList(_)).toArray)
  }
}
