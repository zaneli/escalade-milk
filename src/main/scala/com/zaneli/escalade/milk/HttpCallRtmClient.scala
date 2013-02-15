package com.zaneli.escalade.milk

import scala.xml.Node
import scala.xml.XML

private[milk] class HttpCallRtmClient[A <: TokenChecker](
  apiKey: String, sharedSecret: String, authToken: Option[String] = None) extends RtmClient[A](apiKey, sharedSecret, authToken) {

  private val apiUrl = "https://api.rememberthemilk.com/services/rest/"

  private[milk] val callApi: ((String, Map[String, String]) => Node) = {
    (methodName: String, params: Map[String, String]) =>
      val queryString = AuthUtil.createQueryString(sharedSecret, params + ("method" -> methodName))
      XML.load(apiUrl + "?" + queryString)
  }
}