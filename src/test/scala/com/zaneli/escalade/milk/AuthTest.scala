package com.zaneli.escalade.milk

import com.zaneli.escalade.milk.model.Perms

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class AuthTest extends Specification with Mockito {

  "checkToken" should {
    "読み取り権限" in {
      val expectedResponse =
        <rsp stat="ok">
          <auth>
            <token>6410bde19b6dfb474fec71f186bc715831ea6842</token>
            <perms>read</perms>
            <user id="987654321" username="bob" fullname="Bob T. Monkey"/>
          </auth>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val token = client.auth.checkToken("6410bde19b6dfb474fec71f186bc715831ea6842")
      client.methodName must_== "rtm.auth.checkToken"

      token.token must_== "6410bde19b6dfb474fec71f186bc715831ea6842"
      token.perms must_== Perms.READ

      val user = token.user
      user.id must_== 987654321
      user.username must_== "bob"
      user.fullname must_== Some("Bob T. Monkey")
    }

    "書き込み権限" in {
      val expectedResponse =
        <rsp stat="ok">
          <auth>
            <token>6410bde19b6dfb474fec71f186bc715831ea6842</token>
            <perms>write</perms>
            <user id="987654321" username="bob" fullname="Bob T. Monkey"/>
          </auth>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val token = client.auth.checkToken("6410bde19b6dfb474fec71f186bc715831ea6842")
      client.methodName must_== "rtm.auth.checkToken"

      token.token must_== "6410bde19b6dfb474fec71f186bc715831ea6842"
      token.perms must_== Perms.READ_WRITE

      val user = token.user
      user.id must_== 987654321
      user.username must_== "bob"
      user.fullname must_== Some("Bob T. Monkey")
    }

    "削除権限" in {
      val expectedResponse =
        <rsp stat="ok">
          <auth>
            <token>6410bde19b6dfb474fec71f186bc715831ea6842</token>
            <perms>delete</perms>
            <user id="987654321" username="bob" fullname="Bob T. Monkey"/>
          </auth>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val token = client.auth.checkToken("6410bde19b6dfb474fec71f186bc715831ea6842")
      client.methodName must_== "rtm.auth.checkToken"

      token.token must_== "6410bde19b6dfb474fec71f186bc715831ea6842"
      token.perms must_== Perms.ALL

      val user = token.user
      user.id must_== 987654321
      user.username must_== "bob"
      user.fullname must_== Some("Bob T. Monkey")
    }
  }

  "getFrob" should {
    "Frob取得" in {
      val expectedResponse = <rsp stat="ok"><frob>0a56717c3561e53584f292bb7081a533c197270c</frob></rsp>
      val client = TestUtil.getClient(expectedResponse)
      val frob = client.auth.getFrob
      client.methodName must_== "rtm.auth.getFrob"

      frob.frob must_== "0a56717c3561e53584f292bb7081a533c197270c"
    }
  }

  "getToken" should {
    "読み取り権限" in {
      val expectedResponse =
        <rsp stat="ok">
          <auth>
            <token>6410bde19b6dfb474fec71f186bc715831ea6842</token>
            <perms>read</perms>
            <user id="987654321" username="bob" fullname="Bob T. Monkey"/>
          </auth>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val token = client.auth.getToken("test-frob")
      client.methodName must_== "rtm.auth.getToken"

      token.token must_== "6410bde19b6dfb474fec71f186bc715831ea6842"
      token.perms must_== Perms.READ

      val user = token.user
      user.id must_== 987654321
      user.username must_== "bob"
      user.fullname must_== Some("Bob T. Monkey")
    }

    "書き込み権限" in {
      val expectedResponse =
        <rsp stat="ok">
          <auth>
            <token>6410bde19b6dfb474fec71f186bc715831ea6842</token>
            <perms>write</perms>
            <user id="987654321" username="bob" fullname="Bob T. Monkey"/>
          </auth>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val token = client.auth.getToken("test-frob")
      client.methodName must_== "rtm.auth.getToken"

      token.token must_== "6410bde19b6dfb474fec71f186bc715831ea6842"
      token.perms must_== Perms.READ_WRITE

      val user = token.user
      user.id must_== 987654321
      user.username must_== "bob"
      user.fullname must_== Some("Bob T. Monkey")
    }

    "削除権限" in {
      val expectedResponse =
        <rsp stat="ok">
          <auth>
            <token>6410bde19b6dfb474fec71f186bc715831ea6842</token>
            <perms>delete</perms>
            <user id="987654321" username="bob" fullname="Bob T. Monkey"/>
          </auth>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val token = client.auth.getToken("test-frob")
      client.methodName must_== "rtm.auth.getToken"

      token.token must_== "6410bde19b6dfb474fec71f186bc715831ea6842"
      token.perms must_== Perms.ALL

      val user = token.user
      user.id must_== 987654321
      user.username must_== "bob"
      user.fullname must_== Some("Bob T. Monkey")
    }
  }
}