package com.zaneli.escalade.milk

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ListsTest extends Specification with Mockito {

  "add" should {
    "リスト追加" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="0" id="4476036010"></transaction>
          <list id="987654321" name="New List" deleted="0" locked="0" archived="0" position="0" smart="0" sort_order="0"/>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.lists.add(123, "New List")
      client.methodName must_== "rtm.lists.add"

      val list = transactional.result
      list.id must_== 987654321
      list.name must_== "New List"
      list.deleted must beFalse
      list.locked must beFalse
      list.archived must beFalse
      list.position must_== 0
      list.sortOrder must_== Some(0)
      list.smart must beFalse

      val transaction = transactional.transaction
      transaction.id must_== 4476036010L
      transaction.undoable must beFalse
    }
  }

  "archive" should {
    "リストアーカイブ" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4476098953"></transaction>
          <list id="987654321" name="New List" deleted="0" locked="0" archived="1" position="0" smart="0" sort_order="0"/>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.lists.archive(123, 987654321)
      client.methodName must_== "rtm.lists.archive"

      val list = transactional.result
      list.id must_== 987654321
      list.name must_== "New List"
      list.deleted must beFalse
      list.locked must beFalse
      list.archived must beTrue
      list.position must_== 0
      list.sortOrder must_== Some(0)
      list.smart must beFalse

      val transaction = transactional.transaction
      transaction.id must_== 4476098953L
      transaction.undoable must beTrue
    }
  }

  "delete" should {
    "リスト削除" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4476202319"></transaction>
          <list id="987654321" name="New List" deleted="1" locked="0" archived="0" position="0" sort_order="0" smart="0"/>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.lists.delete(123, 987654321)
      client.methodName must_== "rtm.lists.delete"

      val list = transactional.result
      list.id must_== 987654321
      list.name must_== "New List"
      list.deleted must beTrue
      list.locked must beFalse
      list.archived must beFalse
      list.position must_== 0
      list.sortOrder must_== Some(0)
      list.smart must beFalse

      val transaction = transactional.transaction
      transaction.id must_== 4476202319L
      transaction.undoable must beTrue
    }
  }

  "getList" should {
    "リスト1件取得" in {
      val expectedResponse =
        <rsp stat="ok">
          <lists>
            <list id="100653" name="Inbox" deleted="0" locked="1" archived="0" position="-1" sort_order="0" smart="0"/>
          </lists>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val lists = client.lists.getList
      client.methodName must_== "rtm.lists.getList"

      lists.size must_== 1

      {
        val list = lists(0)
        list.id must_== 100653
        list.name must_== "Inbox"
        list.deleted must beFalse
        list.locked must beTrue
        list.archived must beFalse
        list.position must_== -1
        list.sortOrder must_== Some(0)
        list.smart must beFalse
      }
    }

    "リスト複数件取得" in {
      val expectedResponse =
        <rsp stat="ok">
          <lists>
            <list id="100653" name="Inbox" deleted="0" locked="1" archived="0" position="-1" sort_order="0" smart="0"/>
            <list id="387549" name="High Priority" deleted="0" locked="0" archived="0" position="0" sort_order="1" smart="1">
              <filter>(priority:1)</filter>
            </list>
            <list id="100656" name="Work" deleted="0" locked="0" archived="0" position="0" smart="0"/>
            <list id="100657" name="Sent" deleted="0" locked="1" archived="0" position="1" smart="0"/>
          </lists>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val lists = client.lists.getList
      client.methodName must_== "rtm.lists.getList"

      lists.size must_== 4

      {
        val list = lists(0)
        list.id must_== 100653
        list.name must_== "Inbox"
        list.deleted must beFalse
        list.locked must beTrue
        list.archived must beFalse
        list.position must_== -1
        list.sortOrder must_== Some(0)
        list.smart must beFalse
      }
      {
        val list = lists(1)
        list.id must_== 387549
        list.name must_== "High Priority"
        list.deleted must beFalse
        list.locked must beFalse
        list.archived must beFalse
        list.position must_== 0
        list.sortOrder must_== Some(1)
        list.smart must beTrue
      }
      {
        val list = lists(2)
        list.id must_== 100656
        list.name must_== "Work"
        list.deleted must beFalse
        list.locked must beFalse
        list.archived must beFalse
        list.position must_== 0
        list.sortOrder must beNone
        list.smart must beFalse
      }
      {
        val list = lists(3)
        list.id must_== 100657
        list.name must_== "Sent"
        list.deleted must beFalse
        list.locked must beTrue
        list.archived must beFalse
        list.position must_== 1
        list.sortOrder must beNone
        list.smart must beFalse
      }
    }
  }

  "setDefaultList" should {
    "デフォルトリスト設定" in {
      val expectedResponse = <rsp stat="ok"><transaction undoable="0" id="4476180497"></transaction></rsp>
      val client = TestUtil.getClient(expectedResponse)
      val transaction = client.lists.setDefaultList(123, 987654321)
      client.methodName must_== "rtm.lists.setDefaultList"

      transaction.id must_== 4476180497L
      transaction.undoable must beFalse
    }
  }

  "setName" should {
    "リスト名設定" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4476062060"></transaction>
          <list id="987654321" name="New List" deleted="0" locked="0" archived="0" position="0" smart="0" sort_order="0"/>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.lists.setName(123, 987654321, "New List")
      client.methodName must_== "rtm.lists.setName"

      val list = transactional.result
      list.id must_== 987654321
      list.name must_== "New List"
      list.deleted must beFalse
      list.locked must beFalse
      list.archived must beFalse
      list.position must_== 0
      list.sortOrder must_== Some(0)
      list.smart must beFalse

      val transaction = transactional.transaction
      transaction.id must_== 4476062060L
      transaction.undoable must beTrue
    }
  }

  "unarchive" should {
    "アーカイブをほどく" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4476121978"></transaction>
          <list id="987654321" name="New List" deleted="0" locked="0" archived="0" position="0" smart="0" sort_order="0"/>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.lists.unarchive(123, 987654321)
      client.methodName must_== "rtm.lists.unarchive"

      val list = transactional.result
      list.id must_== 987654321
      list.name must_== "New List"
      list.deleted must beFalse
      list.locked must beFalse
      list.archived must beFalse
      list.position must_== 0
      list.sortOrder must_== Some(0)
      list.smart must beFalse

      val transaction = transactional.transaction
      transaction.id must_== 4476121978L
      transaction.undoable must beTrue
    }
  }
}