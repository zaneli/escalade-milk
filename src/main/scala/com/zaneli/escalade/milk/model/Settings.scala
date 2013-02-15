package com.zaneli.escalade.milk.model

import scala.xml.Node

case class Settings private (timezone: String, dateformat: Dateformat, timeformat: Timeformat, defaultlist: Option[Long], language: String) extends Result {

}
object Settings {
  def apply(node: Node): Settings = {
    new Settings(
      (node \ "timezone").text,
      Dateformat((node \ "dateformat").text.toInt),
      Timeformat((node \ "timeformat").text.toInt),
      if ((node \ "defaultlist").text.trim.isEmpty) None else Some((node \ "defaultlist").text.toLong),
      (node \ "language").text)
  }
}

sealed abstract case class Timeformat private (value: Int, name: String) {
  override def toString(): String = {
    name
  }
}
object Timeformat {
  def apply(value: Int): Timeformat = value match {
    case TWELVE_HOUR_WITH_PERIOD.value => TWELVE_HOUR_WITH_PERIOD
    case TWENTY_FOUR_HOUR.value => TWENTY_FOUR_HOUR
  }

  object TWELVE_HOUR_WITH_PERIOD extends Timeformat(0, "12 hour time with day period")
  object TWENTY_FOUR_HOUR extends Timeformat(1, "24 hour time")
}