package com.zaneli.escalade.milk

import com.zaneli.escalade.milk.model.Perms

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GroupsTest extends Specification with Mockito {

  "add" should {
    "グループ追加" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="0" id="4475816030"></transaction>
          <group id="987654321" name="Friends">
            <contacts/>
          </group>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.groups.add(123, "Friends")
      client.methodName must_== "rtm.groups.add"

      val group = transactional.result
      group.id must_== 987654321
      group.name must_== "Friends"
      group.contacts must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4475816030L
      transaction.undoable must beFalse
    }
  }

  "addContact" should {
    "コンタクト追加" in {
      val expectedResponse = <rsp stat="ok"><transaction undoable="0" id="4475835595"></transaction></rsp>
      val client = TestUtil.getClient(expectedResponse)
      val transaction = client.groups.addContact(123, 456, 789)
      client.methodName must_== "rtm.groups.addContact"

      transaction.id must_== 4475835595L
      transaction.undoable must beFalse
    }
  }

  "delete" should {
    "グループ削除" in {
      val expectedResponse = <rsp stat="ok"><transaction undoable="0" id="4475866767"></transaction></rsp>
      val client = TestUtil.getClient(expectedResponse)
      val transaction = client.groups.delete(123, 456)
      client.methodName must_== "rtm.groups.delete"

      transaction.id must_== 4475866767L
      transaction.undoable must beFalse
    }
  }

  "getList" should {
    "1件取得" in {
      val expectedResponse =
        <rsp stat="ok">
          <groups>
            <group id="987654321" name="Friends">
              <contacts><contact id="1"/></contacts>
            </group>
          </groups>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val groups = client.groups.getList
      client.methodName must_== "rtm.groups.getList"

      groups.size must_== 1
      val group = groups(0)
      group.id must_== 987654321
      group.name must_== "Friends"
      group.contacts.size must_== 1
      val contact = group.contacts(0)
      contact.id must_== 1
    }

    "複数件取得" in {
      val expectedResponse =
        <rsp stat="ok">
          <groups>
            <group id="987654321" name="Friends">
              <contacts><contact id="1"/></contacts>
            </group>
            <group id="123456789" name="Colleagues">
              <contacts>
                <contact id="1"/>
                <contact id="2"/>
                <contact id="3"/>
              </contacts>
            </group>
          </groups>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val groups = client.groups.getList
      client.methodName must_== "rtm.groups.getList"

      groups.size must_== 2

      {
        val group = groups(0)
        group.id must_== 987654321
        group.name must_== "Friends"
        group.contacts.size must_== 1
        val contact = group.contacts(0)
        contact.id must_== 1
      }
      {
        val group = groups(1)
        group.id must_== 123456789
        group.name must_== "Colleagues"
        group.contacts.size must_== 3

        {
          val contact = group.contacts(0)
          contact.id must_== 1
        }
        {
          val contact = group.contacts(1)
          contact.id must_== 2
        }
        {
          val contact = group.contacts(2)
          contact.id must_== 3
        }
      }
    }
  }

  "removeContact" should {
    "コンタクト削除" in {
      val expectedResponse = <rsp stat="ok"><transaction undoable="0" id="4475853723"></transaction></rsp>
      val client = TestUtil.getClient(expectedResponse)
      val transaction = client.groups.removeContact(123, 456, 789)
      client.methodName must_== "rtm.groups.removeContact"

      transaction.id must_== 4475853723L
      transaction.undoable must beFalse
    }
  }
}