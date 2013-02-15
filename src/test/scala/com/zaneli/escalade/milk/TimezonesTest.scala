package com.zaneli.escalade.milk

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TimezonesTest extends Specification with Mockito {

  "getList" should {
    "タイムゾーン一覧取得" in {
      val expectedResponse =
        <rsp stat="ok">
          <timezones>
            <timezone id="216" name="Asia/Hong_Kong" dst="0" offset="28800" current_offset="28800"/>
            <timezone id="217" name="Asia/Hovd" dst="1" offset="28800" current_offset="25200"/>
            <timezone id="226" name="Asia/Kashgar" dst="0" offset="28800" current_offset="28800"/>
          </timezones>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val timezones = client.timezones.getList
      client.methodName must_== "rtm.timezones.getList"

      timezones.size must_== 3

      {
        val timezone = timezones(0)
        timezone.id must_== 216
        timezone.name must_== "Asia/Hong_Kong"
        timezone.dst must beFalse
        timezone.offset must_== 28800
        timezone.currentOffset must_== 28800
      }
      {
        val timezone = timezones(1)
        timezone.id must_== 217
        timezone.name must_== "Asia/Hovd"
        timezone.dst must beTrue
        timezone.offset must_== 28800
        timezone.currentOffset must_== 25200
      }
      {
        val timezone = timezones(2)
        timezone.id must_== 226
        timezone.name must_== "Asia/Kashgar"
        timezone.dst must beFalse
        timezone.offset must_== 28800
        timezone.currentOffset must_== 28800
      }
    }
  }
}