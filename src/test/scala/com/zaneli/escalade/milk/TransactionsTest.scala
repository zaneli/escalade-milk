package com.zaneli.escalade.milk

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TransactionsTest extends Specification with Mockito {

  "undo" should {
    "取り消し" in {
      val client = TestUtil.getClient()
      client.transactions.undo(111, 222)
      client.methodName must_== "rtm.transactions.undo"
    }
  }
}