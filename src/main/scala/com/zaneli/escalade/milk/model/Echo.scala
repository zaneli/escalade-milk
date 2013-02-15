package com.zaneli.escalade.milk.model

import scala.xml.Node
import scala.xml.XML

private[milk] case class Echo private (params: Map[String, String]) extends Result {

}

private[milk] object Echo {
  val excludeParamKeys = Array("method", "api_sig", "api_key", "auth_token")

  def apply(nodes: Array[Node]): Echo = {
    val params = nodes.filter({ node => !excludeParamKeys.contains(node.label) }).foldLeft(Map[String, String]()) {
      (a, b) => a + (b.label -> b.text)
    }
    new Echo(params)
  }
}