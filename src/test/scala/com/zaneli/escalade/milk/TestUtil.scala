package com.zaneli.escalade.milk

import scala.xml.XML
import scala.xml.Elem

object TestUtil {

  def getClient(xml: Elem = <rsp stat="ok"/>): MockRtmClient[HasToken] = {
    new MockRtmClient(xml)
  }

  class MockRtmClient[A <: TokenChecker](xml: Elem) extends RtmClient[A]("", "", Some("")) {
    var methodName: String = _

    private[milk] implicit val callApi: ((String, Map[String, String]) => Elem) = {
      (methodName: String, params: Map[String, String]) =>
        {
          this.methodName = methodName
          xml
        }
    }
  }

  private[this] val dateFormatter = com.github.nscala_time.time.Imports.DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
  def toDate(dateStr: String): java.util.Date = {
    dateFormatter.parseDateTime(dateStr).toDate
  }
}