package com.zaneli.escalade.milk.model

sealed abstract case class Priority private (value: Option[Int], name: String) extends Param {
  override def toString(): String = {
    name
  }

  private[milk] override def paramValue(): String = {
    value match {
      case Some(n) => n.toString
      case None => ""
    }
  }
}
object Priority {
  def apply(name: String): Priority = name.toLowerCase match {
    case ONE.name => ONE
    case TWO.name => TWO
    case THREE.name => THREE
    case _ => NONE
  }

  object NONE extends Priority(None, "N")
  object ONE extends Priority(Some(1), "1")
  object TWO extends Priority(Some(2), "2")
  object THREE extends Priority(Some(3), "3")
}

sealed abstract case class PriorityDirection private (value: String) extends Param {
  override def toString(): String = {
    value
  }

  private[milk] override def paramValue(): String = {
    value
  }
}
object PriorityDirection {
  def apply(value: String): PriorityDirection = value.toLowerCase match {
    case UP.value => UP
    case DOWN.value => DOWN
  }

  object UP extends PriorityDirection("up")
  object DOWN extends PriorityDirection("down")
}
