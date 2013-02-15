package com.zaneli.escalade.milk

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TasksNotesTest extends Specification with Mockito {

  "add" should {
    "ノート追加" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="0" id="4461874205"></transaction>
          <note id="169624" created="2006-05-07T11:26:49Z" modified="2006-05-07T11:26:49Z" title="Note Title">Note Body</note>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.notes.add(111, 222, 333, 444, "Note Title", "Note Body")
      client.methodName must_== "rtm.tasks.notes.add"

      val note = transactional.result
      note.id must_== 169624
      note.created must_== TestUtil.toDate("2006-05-07T11:26:49Z")
      note.modified must_== TestUtil.toDate("2006-05-07T11:26:49Z")
      note.title must_== "Note Title"
      note.body must_== "Note Body"

      val transaction = transactional.transaction
      transaction.id must_== 4461874205L
      transaction.undoable must beFalse
    }
  }

  "delete" should {
    "ノート削除" in {
      val expectedResponse = <rsp stat="ok"><transaction undoable="1" id="4462459761"></transaction></rsp>
      val client = TestUtil.getClient(expectedResponse)
      val transaction = client.tasks.notes.delete(111, 4461874205L)
      client.methodName must_== "rtm.tasks.notes.delete"

      transaction.id must_== 4462459761L
      transaction.undoable must beTrue
    }
  }

  "add" should {
    "ノート編集" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="0" id="4462420428"></transaction>
          <note id="169624" created="2006-05-07T11:26:49Z" modified="2006-05-07T11:28:52Z" title="New Note Title">New Note Body</note>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.notes.edit(111, 169624, "New Note Title", "New Note Body")
      client.methodName must_== "rtm.tasks.notes.edit"

      val note = transactional.result
      note.id must_== 169624
      note.created must_== TestUtil.toDate("2006-05-07T11:26:49Z")
      note.modified must_== TestUtil.toDate("2006-05-07T11:28:52Z")
      note.title must_== "New Note Title"
      note.body must_== "New Note Body"

      val transaction = transactional.transaction
      transaction.id must_== 4462420428L
      transaction.undoable must beFalse
    }
  }

}