package com.zaneli.escalade.milk

import com.zaneli.escalade.milk.model.Result
import com.zaneli.escalade.milk.model.EmptyResult
import com.zaneli.escalade.milk.model.ModelUtil
import com.zaneli.escalade.milk.model.Transaction
import com.zaneli.escalade.milk.model.Transactional

import scala.xml.Elem
import scala.xml.Node

private[milk] class MethodExecutor private[milk] (apiKey: String, sharedSecret: String) {

  private[milk] def execute[A <: Result: ClassManifest](
    methodName: String, params: Map[String, String], model: Class[A])(callApi: ((String, Map[String, String]) => Node)): A = {
    val response = callApi(methodName, params)
    val stat = response \ "@stat"
    stat.text match {
      case "ok" => {
        createResult(response, model)
      }
      case "fail" => {
        val code = response \ "err" \ "@code"
        val msg = response \ "err" \ "@msg"
        throw new RtmException(code.text.toInt, msg.text)
      }
      case _ => throw new RtmException(-1, "Unexpected response: " + response)
    }
  }

  private def createResult[A <: Result: ClassManifest](response: Node, model: Class[A]): A = {
    val children = response.child.filter(_.isInstanceOf[Elem])
    try {
      children.length match {
        case 0 => {
          if (!model.equals(classOf[EmptyResult])) {
            throw new RtmException(-1, "Unexpected response: " + response)
          }
          EmptyResult().asInstanceOf[A]
        }
        case 1 => ModelUtil.createModelCompanion(model, children.head)
        case 2 => {
          if (model.equals(classOf[Transactional[_]])) {
            val (transaction, rest) = children.partition(_.label == "transaction")
            if (transaction.size != 1 || rest.size != 1) {
              throw new RtmException(-1, "Unexpected response: " + response)
            }
            val manifest = implicitly[ClassManifest[A]]
            val elementType = manifest.typeArguments.head
            createTransactionalResult(
              transaction.head,
              rest.head,
              elementType.asInstanceOf[ClassManifest[_]].erasure.asInstanceOf[Class[Result]]).asInstanceOf[A]
          } else {
            ModelUtil.createModelCompanion(model, children.toArray)
          }
        }
        case _ => ModelUtil.createModelCompanion(model, children.toArray)
      }
    } catch {
      case _: NoSuchMethodException => throw new RtmException(-1, "Unexpected response: " + response)
    }
  }

  private def createTransactionalResult[A <: Result: ClassManifest](transaction: Node, rest: Node, model: Class[A]): Transactional[A] = {
    Transactional[A](Transaction(transaction.head), ModelUtil.createModelCompanion(model, rest.head))
  }
}