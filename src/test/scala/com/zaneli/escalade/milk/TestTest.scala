package com.zaneli.escalade.milk

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestTest extends Specification with Mockito {

  "echo" should {
    "パラメータを渡さない" in {
      val expectedResponse =
        <rsp stat="ok">
          <api_sig>xxxx</api_sig>
          <method>rtm.test.echo</method>
          <auth_token>yyyy</auth_token>
          <api_key>zzzz</api_key>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val params = client.test.echo(Map())
      client.methodName must_== "rtm.test.echo"

      params must beEmpty
    }

    "パラメータを1つ渡す" in {
      val expectedResponse =
        <rsp stat="ok">
          <method>rtm.test.echo</method>
          <api_sig>xxxx</api_sig>
          <api_key>yyyy</api_key>
          <auth_token>zzzz</auth_token>
          <foo>bar</foo>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val params = client.test.echo(Map("foo" -> "bar"))
      client.methodName must_== "rtm.test.echo"

      params.size must_== 1
      params.get("foo") must_== Some("bar")
    }

    "パラメータを複数渡す" in {
      val expectedResponse =
        <rsp stat="ok">
          <method>rtm.test.echo</method>
          <api_sig>xxxx</api_sig>
          <test1>1</test1>
          <test2>2</test2>
          <test3>3</test3>
          <api_key>yyyy</api_key>
          <auth_token>zzzz</auth_token>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val params = client.test.echo(Map("foo" -> "bar"))
      client.methodName must_== "rtm.test.echo"

      params.size must_== 3
      params.get("test1") must_== Some("1")
      params.get("test2") must_== Some("2")
      params.get("test3") must_== Some("3")
    }

    "パラメータを複数渡す" in {
      val expectedResponse =
        <rsp stat="ok">
          <method>rtm.test.echo</method>
          <api_sig>xxxx</api_sig>
          <test1>1</test1>
          <test2>2</test2>
          <test3>3</test3>
          <api_key>yyyy</api_key>
          <auth_token>zzzz</auth_token>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val params = client.test.echo(Map("foo" -> "bar"))
      client.methodName must_== "rtm.test.echo"

      params.size must_== 3
      params.get("test1") must_== Some("1")
      params.get("test2") must_== Some("2")
      params.get("test3") must_== Some("3")
    }
  }

  "login" should {
    "ログインチェック" in {
      val expectedResponse = <rsp stat="ok"><user id="987654321"><username>bob</username></user></rsp>
      val client = TestUtil.getClient(expectedResponse)
      val user = client.test.login
      client.methodName must_== "rtm.test.login"

      user.id must_== 987654321
      user.username must_== "bob"
      user.fullname must beNone
    }
  }
}