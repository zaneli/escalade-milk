package com.zaneli.escalade.milk.model

private[milk] abstract trait Param {

  private[milk] def paramValue(): String
}