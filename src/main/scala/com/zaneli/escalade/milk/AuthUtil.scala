package com.zaneli.escalade.milk

import java.security.MessageDigest

private[milk] object AuthUtil {

  private[milk] def createQueryString(sharedSecret: String, params: Map[String, String]): String = {
    (params + ("api_sig" -> AuthUtil.createApiSig(sharedSecret, params))).map {
      case (k, v) => k + "=" + v
    }.mkString("&")
  }

  private def createApiSig(sharedSecret: String, params: Map[String, String]): String = {
    digest(sharedSecret + params.toList.sorted.map { case (k, v) => k + v }.mkString)
  }

  private def digest(value: String): String = {
    MessageDigest.getInstance("MD5").digest(value.getBytes).map("%02x".format(_)).mkString
  }
}