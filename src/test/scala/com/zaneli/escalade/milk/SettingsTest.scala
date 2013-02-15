package com.zaneli.escalade.milk

import com.zaneli.escalade.milk.model.Dateformat
import com.zaneli.escalade.milk.model.Timeformat

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SettingsTest extends Specification with Mockito {

  "getList" should {
    "デフォルトリスト未設定" in {
      val expectedResponse =
        <rsp stat="ok">
          <settings>
            <timezone>Asia/Tokyo</timezone>
            <dateformat>1</dateformat>
            <timeformat>0</timeformat>
            <defaultlist></defaultlist>
            <language>ja</language>
          </settings>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val settings = client.settings.getList
      client.methodName must_== "rtm.settings.getList"

      settings.timezone must_== "Asia/Tokyo"
      settings.dateformat must_== Dateformat.AMERICAN
      settings.timeformat must_== Timeformat.TWELVE_HOUR_WITH_PERIOD
      settings.defaultlist must beNone
      settings.language must_== "ja"
    }

    "デフォルトリスト設定済み" in {
      val expectedResponse =
        <rsp stat="ok">
          <settings>
            <timezone>Asia/Tokyo</timezone>
            <dateformat>0</dateformat>
            <timeformat>1</timeformat>
            <defaultlist>123456</defaultlist>
            <language>en</language>
          </settings>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val settings = client.settings.getList
      client.methodName must_== "rtm.settings.getList"

      settings.timezone must_== "Asia/Tokyo"
      settings.dateformat must_== Dateformat.EUROPEAN
      settings.timeformat must_== Timeformat.TWENTY_FOUR_HOUR
      settings.defaultlist must_== Some(123456)
      settings.language must_== "en"
    }
  }
}