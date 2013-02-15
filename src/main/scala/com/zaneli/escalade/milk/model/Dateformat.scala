package com.zaneli.escalade.milk.model

sealed abstract case class Dateformat private (value: Int, name: String) extends Param {
  override def toString(): String = {
    name
  }

  private[milk] override def paramValue(): String = {
    value.toString
  }
}

object Dateformat {
  def apply(value: Int): Dateformat = value match {
    case EUROPEAN.value => EUROPEAN
    case AMERICAN.value => AMERICAN
  }

  object EUROPEAN extends Dateformat(0, "European")
  object AMERICAN extends Dateformat(1, "American")
}