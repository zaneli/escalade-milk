package com.zaneli.escalade.milk.model

sealed abstract case class Perms private (code: Int, name: String) {
  override def toString(): String = {
    name
  }
}

object Perms {
  def apply(code: Int): Perms = code match {
    case NONE.code => NONE
    case READ.code => READ
    case READ_WRITE.code => READ_WRITE
    case ALL.code => ALL
  }

  def apply(name: String): Perms = name.toLowerCase match {
    case READ.name => READ
    case READ_WRITE.name => READ_WRITE
    case ALL.name => ALL
    case _ => NONE
  }

  object NONE extends Perms(0, "none")
  object READ extends Perms(1, "read")
  object READ_WRITE extends Perms(2, "write")
  object ALL extends Perms(3, "delete")
}