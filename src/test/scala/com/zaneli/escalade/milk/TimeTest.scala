package com.zaneli.escalade.milk

import com.zaneli.escalade.milk.model.Dateformat

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TimeTest extends Specification with Mockito {

  "convert" should {
    "変換先タイムゾーンのみ指定" in {
      val expectedResponse = <rsp stat="ok"><time timezone="Africa/Abidjan">2013-02-12T05:30:42</time></rsp>
      val client = TestUtil.getClient(expectedResponse)
      val time = client.time.convert("Africa/Abidjan")
      client.methodName must_== "rtm.time.convert"

      time.timezone must_== "Africa/Abidjan"
      time.value must_== "2013-02-12T05:30:42"
    }

    "変換元タイムゾーンを指定" in {
      val expectedResponse = <rsp stat="ok"><time timezone="Africa/Abidjan">2013-02-12T05:33:28</time></rsp>
      val client = TestUtil.getClient(expectedResponse)
      val time = client.time.convert("Africa/Abidjan", Some("Asia/Tokyo"))
      client.methodName must_== "rtm.time.convert"

      time.timezone must_== "Africa/Abidjan"
      time.value must_== "2013-02-12T05:33:28"
    }

    "日時を指定" in {
      val expectedResponse = <rsp stat="ok"><time timezone="Africa/Abidjan">2013-02-12T12:34:56</time></rsp>
      val client = TestUtil.getClient(expectedResponse)
      val time = client.time.convert("Africa/Abidjan", time = Some(TestUtil.toDate("2013-02-12T12:34:56Z")))
      client.methodName must_== "rtm.time.convert"

      time.timezone must_== "Africa/Abidjan"
      time.value must_== "2013-02-12T12:34:56"
    }
  }

  "parse" should {
    "日付文字列のみ指定" in {
      val expectedResponse = <rsp stat="ok"><time precision="date">2006-02-14T00:00:00Z</time></rsp>
      val client = TestUtil.getClient(expectedResponse)
      val time = client.time.parse("02/14/2006")
      client.methodName must_== "rtm.time.parse"

      time.precision must_== "date"
      time.value must_== "2006-02-14T00:00:00Z"
    }

    "タイムゾーンを指定" in {
      val expectedResponse = <rsp stat="ok"><time precision="time">2006-02-13T21:00:00Z</time></rsp>
      val client = TestUtil.getClient(expectedResponse)
      val time = client.time.parse("02/14/2006 06:00", Some("Asia/Tokyo"))
      client.methodName must_== "rtm.time.parse"

      time.precision must_== "time"
      time.value must_== "2006-02-13T21:00:00Z"
    }

    "フォーマットを指定" in {
      val expectedResponse = <rsp stat="ok"><time precision="date">2006-03-02T00:00:00Z</time></rsp>
      val client = TestUtil.getClient(expectedResponse)
      val time = client.time.parse("02/03/2006", dateformat = Some(Dateformat.EUROPEAN))
      client.methodName must_== "rtm.time.parse"

      time.precision must_== "date"
      time.value must_== "2006-03-02T00:00:00Z"
    }
  }
}