package com.zaneli.escalade.milk

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ContactsTest extends Specification with Mockito {

  "add" should {
    "コンタクト追加" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="0" id="4475612753"></transaction>
          <contact id="1" fullname="Omar Kilani" username="omar"/>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.contacts.add(123, "omar")
      client.methodName must_== "rtm.contacts.add"

      val contact = transactional.result
      contact.id must_== 1
      contact.fullname must_== "Omar Kilani"
      contact.username must_== "omar"

      val transaction = transactional.transaction
      transaction.id must_== 4475612753L
      transaction.undoable must beFalse
    }
  }

  "delete" should {
    "コンタクト削除" in {
      val expectedResponse = <rsp stat="ok"><transaction undoable="0" id="4475637651"></transaction></rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transaction = client.contacts.delete(123, 12345)
      client.methodName must_== "rtm.contacts.delete"

      transaction.id must_== 4475637651L
      transaction.undoable must beFalse
    }
  }

  "getList" should {
    "1件取得" in {
      val expectedResponse =
        <rsp stat="ok">
          <contacts>
            <contact id="1" fullname="Omar Kilani" username="omar"/>
          </contacts>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val contacts = client.contacts.getList
      client.methodName must_== "rtm.contacts.getList"

      contacts.size must_== 1
      val contact = contacts(0)
      contact.id must_== 1
      contact.fullname must_== "Omar Kilani"
      contact.username must_== "omar"
    }

    "複数件取得" in {
      val expectedResponse =
        <rsp stat="ok">
          <contacts>
            <contact id="1" fullname="Omar Kilani" username="omar"/>
            <contact id="2" fullname="Bob T. Monkey" username="bob"/>
            <contact id="3" fullname="Shunsuke Ohtani" username="zaneli"/>
          </contacts>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val contacts = client.contacts.getList
      client.methodName must_== "rtm.contacts.getList"

      contacts.size must_== 3

      {
        val contact = contacts(0)
        contact.id must_== 1
        contact.fullname must_== "Omar Kilani"
        contact.username must_== "omar"
      }
      {
        val contact = contacts(1)
        contact.id must_== 2
        contact.fullname must_== "Bob T. Monkey"
        contact.username must_== "bob"
      }
      {
        val contact = contacts(2)
        contact.id must_== 3
        contact.fullname must_== "Shunsuke Ohtani"
        contact.username must_== "zaneli"
      }
    }
  }
}