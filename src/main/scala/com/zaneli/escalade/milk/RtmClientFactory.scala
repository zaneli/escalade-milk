package com.zaneli.escalade.milk

object RtmClientFactory {
  def createClient(apiKey: String, sharedSecret: String): RtmClient[NotHasToken] = {
    new HttpCallRtmClient(apiKey, sharedSecret)
  }

  def createClient(apiKey: String, sharedSecret: String, authToken: String): RtmClient[HasToken] = {
    new HttpCallRtmClient(apiKey, sharedSecret, Some(authToken))
  }
}

private[milk] sealed abstract trait TokenChecker
private[milk] trait HasToken extends TokenChecker
private[milk] trait NotHasToken extends TokenChecker
