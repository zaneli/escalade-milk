package com.zaneli.escalade.milk.model

import scala.xml.Node
import scala.reflect.ClassTypeManifest

private[milk] object ModelUtil {

  private val iso8601formatPattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"

  private[milk] def createModelCompanion[A <: Result: ClassManifest](model: Class[A], node: Node): A = {
    val cls = Class.forName(model.getName + "$")
    val obj = cls.getField("MODULE$").get(null)
    if (model.equals(classOf[ArrayResult[_]])) {
      val method = cls.getDeclaredMethod("apply", classOf[Node], classOf[Class[_]], classOf[ClassManifest[_]])
      val manifest = implicitly[ClassManifest[A]]
      val elementType = manifest.typeArguments.head
      method.setAccessible(true)
      method.invoke(obj, node, elementType.asInstanceOf[ClassManifest[_]].erasure, elementType).asInstanceOf[A]
    } else {
      val method = cls.getDeclaredMethod("apply", classOf[Node])
      method.setAccessible(true)
      method.invoke(obj, node).asInstanceOf[A]
    }
  }

  private[milk] def createModelCompanion[A <: Result: ClassManifest](model: Class[A], nodes: Array[Node]): A = {
    val cls = Class.forName(model.getName + "$")
    val obj = cls.getField("MODULE$").get(null)
    val method = cls.getDeclaredMethod("apply", classOf[Array[Node]])
    method.setAccessible(true)
    method.invoke(obj, nodes).asInstanceOf[A]
  }

  private[milk] def formatDate(date: java.util.Date, utc: Boolean = true): String = {
    import com.github.nscala_time.time.Imports.{ DateTime, DateTimeZone }
    (if (utc) {
      new DateTime(date, DateTimeZone.UTC)
    } else {
      new DateTime(date)
    }).toString(iso8601formatPattern)
  }

  def parseDate(dateStr: String): Option[java.util.Date] = dateStr match {
    case _ if dateStr.trim.isEmpty => None
    case _ => {
      import com.github.nscala_time.time.Imports.DateTimeFormat
      Some(DateTimeFormat.forPattern(iso8601formatPattern).parseDateTime(dateStr match {
        case x if x.trim.endsWith("Z") => x.trim
        case x => x.trim + "Z"
      }).toDate)
    }
  }
}