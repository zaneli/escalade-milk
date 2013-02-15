package com.zaneli.escalade.milk.model

import com.zaneli.escalade.milk.RtmException

sealed abstract class Url private[model] (url: String) {
  override def toString(): String = {
    url
  }
}

object Url {
  def apply(url: String): Url = url match {
    case x if x.startsWith("http://") => HttpUrl(x)
    case x if x.startsWith("https://") => HttpsUrl(x)
    case x if x.startsWith("ftp://") => FtpUrl(x)
    case x if x.startsWith("file://") => FileUrl(x)
    case _ => throw new RtmException(-1, "invalid url: " + url)
  }
}

case class HttpUrl private[model] (url: String) extends Url(url) {

}

case class HttpsUrl private[model] (url: String) extends Url(url) {

}

case class FtpUrl private[model] (url: String) extends Url(url) {

}

case class FileUrl private[model] (url: String) extends Url(url) {

}