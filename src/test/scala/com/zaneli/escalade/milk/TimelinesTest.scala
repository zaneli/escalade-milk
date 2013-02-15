package com.zaneli.escalade.milk

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TimelinesTest extends Specification with Mockito {

  "add" should {
    "タイムライン作成" in {
      val expectedResponse = <rsp stat="ok"><timeline>12741021</timeline></rsp>
      val client = TestUtil.getClient(expectedResponse)
      val timeline = client.timelines.create
      client.methodName must_== "rtm.timelines.create"

      timeline must_== 12741021
    }
  }
}