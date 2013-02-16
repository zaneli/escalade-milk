package com.zaneli.escalade.milk.model

import com.zaneli.escalade.milk.RtmException

import java.util.Date

import scala.xml.Node

case class TaskList private (id: Long, current: Option[Date], taskseries: Array[Taskseries], deleted: Array[DeletedList]) extends Result {

}
object TaskList {
  def apply(node: Node): TaskList = {
    new TaskList(
      (node \ "@id").text.toLong,
      ModelUtil.parseDate((node \ "@current").text),
      (node \ "taskseries").map(Taskseries(_)).toArray,
      (node \ "deleted").map(DeletedList(_)).toArray)
  }
}

case class Taskseries private (
  id: Long,
  name: String,
  created: Date,
  modified: Date,
  source: String,
  locationId: Option[Long],
  url: Option[Url],
  task: Task,
  rrule: Option[Rrule],
  tags: Array[String],
  participants: Array[Contact],
  notes: Array[Note]) {

}
object Taskseries {
  def apply(node: Node): Taskseries = {
    new Taskseries(
      (node \ "@id").text.toLong,
      (node \ "@name").text,
      ModelUtil.parseDate((node \ "@created").text).get,
      ModelUtil.parseDate((node \ "@modified").text).get,
      (node \ "@source").text,
      (node \ "@location_id").text match {
        case "" => None
        case x => Some(x.toLong)
      },
      (node \ "@url").text match {
        case "" => None
        case x => Some(Url(x))
      },
      Task((node \ "task").head),
      (node \ "rrule").size match {
        case 0 => None
        case 1 => Some(Rrule((node \ "rrule").head))
        case _ => throw new RtmException(-1, "Unexpected Taskseries data: " + node)
      },
      StringsResult((node \ "tags").head).values,
      (node \ "participants" \ "contact").map(Contact(_)).toArray,
      (node \ "notes" \ "note").map(Note(_)).toArray)
  }
}

case class Task private (
  id: Long,
  due: Option[Date],
  hasDueTime: Boolean,
  added: Date,
  completed: Option[Date],
  deleted: Option[Date],
  priority: Priority,
  postponed: Int,
  estimate: Option[String]) {

}
object Task {
  def apply(node: Node): Task = {
    new Task(
      (node \ "@id").text.toLong,
      ModelUtil.parseDate((node \ "@due").text),
      (node \ "@has_due_time").text == "1",
      ModelUtil.parseDate((node \ "@added").text).get,
      ModelUtil.parseDate((node \ "@completed").text),
      ModelUtil.parseDate((node \ "@deleted").text),
      Priority((node \ "@priority").text),
      (node \ "@postponed").text.toInt,
      (node \ "@estimate").text match {
        case "" => None
        case x => Some(x)
      })
  }
}

case class Rrule private (repeatType: RepeatType, value: Map[String, String]) {

}
object Rrule {
  val regex = "(.+)=(.+)".r
  def apply(node: Node): Rrule = {
    new Rrule(RepeatType((node \ "@every").text.toInt), node.text.split(";").map(
      _ match { case regex(k, v) => (k, v) }).toMap)
  }
}

sealed abstract case class RepeatType private (name:String) {
  override def toString(): String = {
    name
  }
}
object RepeatType {
  def apply(every: Int): RepeatType = every match {
    case 1 => EVERY
    case 0 => AFTER
    case _ => throw new RtmException(-1, "Unexpected rrule [every] value: " + every)
  }
  object EVERY extends RepeatType("every")
  object AFTER extends RepeatType("after")
}

case class DeletedList private (taskseriesId: Long, taskId: Long, deleted: Date) {

}
object DeletedList {
  def apply(node: Node): DeletedList = {
    new DeletedList(
      (node \ "taskseries" \ "@id").text.toLong,
      (node \ "taskseries" \ "task" \ "@id").text.toLong,
      ModelUtil.parseDate((node \ "taskseries" \ "task" \ "@deleted").text).get)
  }
}
