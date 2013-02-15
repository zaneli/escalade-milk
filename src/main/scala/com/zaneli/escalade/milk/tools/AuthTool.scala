package com.zaneli.escalade.milk.tools

import com.zaneli.escalade.milk.AuthUtil
import com.zaneli.escalade.milk.model.Perms

object AuthTool {

  private val url = "http://www.rememberthemilk.com/services/auth/"

  def createAuthUrl(apiKey: String, sharedSecret: String, perms: Perms, frob: String): String = {
    val queryString = AuthUtil.createQueryString(
      sharedSecret, Map("api_key" -> apiKey, "perms" -> perms.name, "frob" -> frob))
    url + "?" + queryString
  }
}