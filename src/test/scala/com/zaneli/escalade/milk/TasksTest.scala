package com.zaneli.escalade.milk

import com.zaneli.escalade.milk.model.Priority
import com.zaneli.escalade.milk.model.PriorityDirection
import com.zaneli.escalade.milk.model.Url

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TasksTest extends Specification with Mockito {

  "add" should {
    "タスク追加" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="0" id="4487151511"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-13T08:21:38Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.add(123, "TestTask")
      client.methodName must_== "rtm.tasks.add"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 0
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4487151511L
      transaction.undoable must beFalse
    }
  }

  "addTags" should {
    "タグ1個追加" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4487308543"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-13T08:45:34Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags><tag>tag1</tag></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.addTags(111, 222, 333, 444, "tag1")
      client.methodName must_== "rtm.tasks.addTags"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-13T08:45:34Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 0
      task.estimate must beNone
      taskseries.rrule must beNone

      taskseries.tags.size must_== 1
      taskseries.tags.head must_== "tag1"

      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4487308543L
      transaction.undoable must beTrue
    }

    "タグ複数個追加" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4487352219"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-13T08:53:10Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags><tag>tag1</tag><tag>tag2</tag><tag>tag3</tag><tag>tag4</tag></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.addTags(111, 222, 333, 444, "tag2", "tag3", "tag4")
      client.methodName must_== "rtm.tasks.addTags"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-13T08:53:10Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 0
      task.estimate must beNone
      taskseries.rrule must beNone

      taskseries.tags.size must_== 4
      taskseries.tags(0) must_== "tag1"
      taskseries.tags(1) must_== "tag2"
      taskseries.tags(2) must_== "tag3"
      taskseries.tags(3) must_== "tag4"

      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4487352219L
      transaction.undoable must beTrue
    }
  }

  "complete" should {
    "タスク完了" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4506768702"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-15T09:41:50Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="2" priority="N" deleted="" completed="2013-02-15T09:41:50Z" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.complete(111, 222, 333, 444)
      client.methodName must_== "rtm.tasks.complete"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-15T09:41:50Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must_== Some(TestUtil.toDate("2013-02-15T09:41:50Z"))
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 2
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4506768702L
      transaction.undoable must beTrue
    }
  }

  "delete" should {
    "タスク完了" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4507784657"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-15T12:29:13Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="2" priority="N" deleted="2013-02-15T12:29:13Z" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.delete(111, 222, 333, 444)
      client.methodName must_== "rtm.tasks.delete"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-15T12:29:13Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must_== Some(TestUtil.toDate("2013-02-15T12:29:13Z"))
      task.priority must_== Priority.NONE
      task.postponed must_== 2
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4507784657L
      transaction.undoable must beTrue
    }
  }

  "getList" should {
    "タスクのみ存在" in {
      val expectedResponse =
        <rsp stat="ok">
          <tasks rev="8ozzec54pdyk595yd19vnnv9ummsqwf">
            <list id="26186830"></list>
            <list id="26186831">
              <taskseries location_id="" url="" source="js" name="TaskName" modified="2013-02-10T15:18:19Z" created="2012-04-23T04:33:52Z" id="157101103">
                <tags></tags>
                <participants></participants>
                <notes></notes>
                <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2012-04-23T04:33:52Z" has_due_time="0" due="" id="247503204"></task>
              </taskseries>
            </list>
          </tasks>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val tasks = client.tasks.getList()
      client.methodName must_== "rtm.tasks.getList"

      tasks.rev must_== "8ozzec54pdyk595yd19vnnv9ummsqwf"
      tasks.taskLists.size must_== 2

      {
        val taskList = tasks.taskLists(0)
        taskList.id must_== 26186830
        taskList.current must beNone
        taskList.taskseries must beEmpty
        taskList.deleted must beEmpty
      }
      {
        val taskList = tasks.taskLists(1)
        taskList.id must_== 26186831
        taskList.current must beNone

        taskList.taskseries.size must_== 1
        val taskseries = taskList.taskseries.head
        taskseries.id must_== 157101103
        taskseries.name must_== "TaskName"
        taskseries.created must_== TestUtil.toDate("2012-04-23T04:33:52Z")
        taskseries.modified must_== TestUtil.toDate("2013-02-10T15:18:19Z")
        taskseries.source must_== "js"
        taskseries.locationId must beNone
        taskseries.url must beNone

        val task = taskseries.task
        task.id must_== 247503204
        task.due must beNone
        task.hasDueTime must beFalse
        task.added must_== TestUtil.toDate("2012-04-23T04:33:52Z")
        task.completed must beNone
        task.deleted must beNone
        task.priority must_== Priority.NONE
        task.postponed must_== 0
        task.estimate must beNone

        taskseries.rrule must beNone
        taskseries.tags must beEmpty
        taskseries.participants must beEmpty
        taskseries.notes must beEmpty

        taskList.deleted must beEmpty
      }
    }

    "繰り返しルールあり" in {
      val expectedResponse =
        <rsp stat="ok">
          <tasks rev="jv21c6pfgg00g8ow4wo8gkkgwg0g0o0">
            <list id="30881735">
              <taskseries location_id="" url="" source="js" name="Task1" modified="2013-02-12T07:30:58Z" created="2012-04-23T04:33:52Z" id="157101103">
                <rrule every="1">FREQ=WEEKLY;INTERVAL=1;BYDAY=TU</rrule>
                <tags></tags>
                <participants></participants>
                <notes></notes>
                <task estimate="" postponed="0" priority="1" deleted="" completed="" added="2012-04-23T04:33:52Z" has_due_time="0" due="" id="247503204"></task>
              </taskseries>
              <taskseries location_id="" url="" source="js" name="Task2" modified="2013-02-12T07:29:09Z" created="2013-02-12T07:29:09Z" id="188171058">
                <tags></tags>
                <participants></participants>
                <notes></notes>
                <task estimate="30 minutes" postponed="0" priority="2" deleted="" completed="" added="2013-02-12T07:29:09Z" has_due_time="0" due="" id="302552443"></task>
              </taskseries>
            </list>
          </tasks>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val tasks = client.tasks.getList(Some(30881735))
      client.methodName must_== "rtm.tasks.getList"

      tasks.rev must_== "jv21c6pfgg00g8ow4wo8gkkgwg0g0o0"
      tasks.taskLists.size must_== 1

      val taskList = tasks.taskLists.head
      taskList.id must_== 30881735
      taskList.current must beNone

      taskList.taskseries.size must_== 2

      {
        val taskseries = taskList.taskseries(0)
        taskseries.id must_== 157101103
        taskseries.name must_== "Task1"
        taskseries.created must_== TestUtil.toDate("2012-04-23T04:33:52Z")
        taskseries.modified must_== TestUtil.toDate("2013-02-12T07:30:58Z")
        taskseries.source must_== "js"
        taskseries.locationId must beNone
        taskseries.url must beNone

        val task = taskseries.task
        task.id must_== 247503204
        task.due must beNone
        task.hasDueTime must beFalse
        task.added must_== TestUtil.toDate("2012-04-23T04:33:52Z")
        task.completed must beNone
        task.deleted must beNone
        task.priority must_== Priority.ONE
        task.postponed must_== 0
        task.estimate must beNone

        val rrule = taskseries.rrule.get
        rrule.every must_== 1
        rrule.value must_== "FREQ=WEEKLY;INTERVAL=1;BYDAY=TU"

        taskseries.tags must beEmpty
        taskseries.participants must beEmpty
        taskseries.notes must beEmpty
      }
      {
        val taskseries = taskList.taskseries(1)
        taskseries.id must_== 188171058
        taskseries.name must_== "Task2"
        taskseries.created must_== TestUtil.toDate("2013-02-12T07:29:09Z")
        taskseries.modified must_== TestUtil.toDate("2013-02-12T07:29:09Z")
        taskseries.source must_== "js"
        taskseries.locationId must beNone
        taskseries.url must beNone

        val task = taskseries.task
        task.id must_== 302552443
        task.due must beNone
        task.hasDueTime must beFalse
        task.added must_== TestUtil.toDate("2013-02-12T07:29:09Z")
        task.completed must beNone
        task.deleted must beNone
        task.priority must_== Priority.TWO
        task.postponed must_== 0
        task.estimate must_== Some("30 minutes")

        taskseries.rrule must beNone
        taskseries.tags must beEmpty
        taskseries.participants must beEmpty
        taskseries.notes must beEmpty
      }
      taskList.deleted must beEmpty
    }

    "タグあり" in {
      val expectedResponse =
        <rsp stat="ok">
          <tasks rev="cx8397lo5i8ggw8g8ogkk0wwcw0k4g8">
            <list id="30881735">
              <taskseries location_id="" url="" source="js" name="TestTask" modified="2013-02-12T07:32:33Z" created="2012-04-23T04:33:52Z" id="157101103">
                <tags><tag>a</tag><tag>b</tag><tag>c</tag></tags>
                <participants></participants>
                <notes></notes>
                <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2012-04-23T04:33:52Z" has_due_time="1" due="2013-12-31T15:00:00Z" id="247503204"></task>
              </taskseries>
            </list>
          </tasks>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val tasks = client.tasks.getList(Some(30881735))
      client.methodName must_== "rtm.tasks.getList"

      tasks.rev must_== "cx8397lo5i8ggw8g8ogkk0wwcw0k4g8"
      tasks.taskLists.size must_== 1

      val taskList = tasks.taskLists.head
      taskList.id must_== 30881735
      taskList.current must beNone

      taskList.taskseries.size must_== 1

      {
        val taskseries = taskList.taskseries(0)
        taskseries.id must_== 157101103
        taskseries.name must_== "TestTask"
        taskseries.created must_== TestUtil.toDate("2012-04-23T04:33:52Z")
        taskseries.modified must_== TestUtil.toDate("2013-02-12T07:32:33Z")
        taskseries.source must_== "js"
        taskseries.locationId must beNone
        taskseries.url must beNone

        val task = taskseries.task
        task.id must_== 247503204
        task.due must_== Some(TestUtil.toDate("2013-12-31T15:00:00Z"))
        task.hasDueTime must beTrue
        task.added must_== TestUtil.toDate("2012-04-23T04:33:52Z")
        task.completed must beNone
        task.deleted must beNone
        task.priority must_== Priority.NONE
        task.postponed must_== 0
        task.estimate must beNone
        taskseries.rrule must beNone

        taskseries.tags.size must_== 3
        taskseries.tags(0) must_== "a"
        taskseries.tags(1) must_== "b"
        taskseries.tags(2) must_== "c"

        taskseries.participants must beEmpty
        taskseries.notes must beEmpty
      }
      taskList.deleted must beEmpty
    }

    "共有あり" in {
      val expectedResponse =
        <rsp stat="ok">
          <tasks rev="dktodzl5quosgw8koo04ks40co08ggk">
            <list id="30881735">
              <taskseries location_id="" url="http://www.rememberthemilk.com/" source="js" name="Share" modified="2013-02-12T07:39:38Z" created="2013-02-12T07:39:38Z" id="188171669">
                <tags></tags>
                <participants><contact username="omar" fullname="Omar Kilani" id="1"></contact></participants>
                <notes></notes>
                <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2013-02-12T07:39:38Z" has_due_time="0" due="" id="302553215"></task>
              </taskseries>
            </list>
          </tasks>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val tasks = client.tasks.getList()
      client.methodName must_== "rtm.tasks.getList"
      tasks.rev must_== "dktodzl5quosgw8koo04ks40co08ggk"
      tasks.taskLists.size must_== 1

      val taskList = tasks.taskLists.head
      taskList.id must_== 30881735
      taskList.current must beNone

      taskList.taskseries.size must_== 1

      {
        val taskseries = taskList.taskseries(0)
        taskseries.id must_== 188171669
        taskseries.name must_== "Share"
        taskseries.created must_== TestUtil.toDate("2013-02-12T07:39:38Z")
        taskseries.modified must_== TestUtil.toDate("2013-02-12T07:39:38Z")
        taskseries.source must_== "js"
        taskseries.locationId must beNone
        taskseries.url must_== Some(Url("http://www.rememberthemilk.com/"))

        val task = taskseries.task
        task.id must_== 302553215
        task.due must beNone
        task.hasDueTime must beFalse
        task.added must_== TestUtil.toDate("2013-02-12T07:39:38Z")
        task.completed must beNone
        task.deleted must beNone
        task.priority must_== Priority.NONE
        task.postponed must_== 0
        task.estimate must beNone
        taskseries.rrule must beNone
        taskseries.tags must beEmpty

        taskseries.participants.size must_== 1
        val participant = taskseries.participants.head
        participant.id must_== 1
        participant.username must_== "omar"
        participant.fullname must_== "Omar Kilani"

        taskseries.notes must beEmpty
      }
      taskList.deleted must beEmpty
    }

    "ノートあり" in {
      val expectedResponse =
        <rsp stat="ok">
          <tasks rev="lxr9x30obxssgsoks4sk08gks0k4g8g">
            <list id="26186831">
              <taskseries location_id="1021439" url="" source="js" name="TestTask" modified="2013-02-12T07:33:21Z" created="2012-04-23T04:33:52Z" id="157101103">
                <tags></tags>
                <participants></participants>
                <notes>
                  <note title="NoteTitle1" modified="2013-02-12T07:33:16Z" created="2013-02-12T07:33:16Z" id="35018148">Note Text 1</note>
                  <note title="NoteTitle2" modified="2013-02-12T07:33:21Z" created="2013-02-12T07:33:21Z" id="35018149">Note Text 2</note>
                </notes>
                <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2012-04-23T04:33:52Z" has_due_time="0" due="2013-12-31T00:00:00Z" id="247503204"></task>
              </taskseries>
            </list>
          </tasks>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val tasks = client.tasks.getList()
      client.methodName must_== "rtm.tasks.getList"
      tasks.rev must_== "lxr9x30obxssgsoks4sk08gks0k4g8g"
      tasks.taskLists.size must_== 1

      val taskList = tasks.taskLists.head
      taskList.id must_== 26186831
      taskList.current must beNone

      taskList.taskseries.size must_== 1

      {
        val taskseries = taskList.taskseries(0)
        taskseries.id must_== 157101103
        taskseries.name must_== "TestTask"
        taskseries.created must_== TestUtil.toDate("2012-04-23T04:33:52Z")
        taskseries.modified must_== TestUtil.toDate("2013-02-12T07:33:21Z")
        taskseries.source must_== "js"
        taskseries.locationId must_== Some(1021439)
        taskseries.url must beNone

        val task = taskseries.task
        task.id must_== 247503204
        task.due must_== Some(TestUtil.toDate("2013-12-31T00:00:00Z"))
        task.hasDueTime must beFalse
        task.added must_== TestUtil.toDate("2012-04-23T04:33:52Z")
        task.completed must beNone
        task.deleted must beNone
        task.priority must_== Priority.NONE
        task.postponed must_== 0
        task.estimate must beNone
        taskseries.rrule must beNone
        taskseries.tags must beEmpty
        taskseries.participants must beEmpty

        taskseries.notes.size must_== 2

        {
          val note = taskseries.notes(0)
          note.id must_== 35018148
          note.title must_== "NoteTitle1"
          note.body must_== "Note Text 1"
          note.created must_== TestUtil.toDate("2013-02-12T07:33:16Z")
          note.modified must_== TestUtil.toDate("2013-02-12T07:33:16Z")
        }
        {
          val note = taskseries.notes(1)
          note.id must_== 35018149
          note.title must_== "NoteTitle2"
          note.body must_== "Note Text 2"
          note.created must_== TestUtil.toDate("2013-02-12T07:33:21Z")
          note.modified must_== TestUtil.toDate("2013-02-12T07:33:21Z")
        }
      }
      taskList.deleted must beEmpty
    }

    "削除リストあり" in {
      val expectedResponse =
        <rsp stat="ok">
          <tasks rev="8obdp0vfwcg04wwkw4wks8so8884o4s">
            <list current="2006-05-07T00:00:00Z" id="26186831">
              <taskseries location_id="" url="" source="js" name="TestTask" modified="2013-02-12T07:29:09Z" created="2013-02-12T07:29:09Z" id="188171058">
                <tags></tags>
                <participants></participants>
                <notes></notes>
                <task estimate="" postponed="0" priority="1" deleted="" completed="" added="2013-02-12T07:29:09Z" has_due_time="0" due="" id="302552443"></task>
              </taskseries>
              <deleted>
                <taskseries id="157018809">
                  <task deleted="2012-04-22T08:56:51Z" id="247365480"></task>
                </taskseries>
              </deleted>
              <deleted>
                <taskseries id="157018731">
                  <task deleted="2012-04-22T08:22:06Z" id="247365394"></task>
                </taskseries>
              </deleted>
            </list>
          </tasks>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val tasks = client.tasks.getList(lastSync = Some(TestUtil.toDate("2006-05-07T00:00:00Z")))
      client.methodName must_== "rtm.tasks.getList"
      tasks.rev must_== "8obdp0vfwcg04wwkw4wks8so8884o4s"
      tasks.taskLists.size must_== 1

      val taskList = tasks.taskLists.head
      taskList.id must_== 26186831
      taskList.current must_== Some(TestUtil.toDate("2006-05-07T00:00:00Z"))

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188171058
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-12T07:29:09Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-12T07:29:09Z")
      taskseries.source must_== "js"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302552443
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-12T07:29:09Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.ONE
      task.postponed must_== 0
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted.size must_== 2

      {
        val deleted = taskList.deleted(0)
        deleted.taskseriesId must_== 157018809
        deleted.taskId must_== 247365480
        deleted.deleted must_== TestUtil.toDate("2012-04-22T08:56:51Z")
      }
      {
        val deleted = taskList.deleted(1)
        deleted.taskseriesId must_== 157018731
        deleted.taskId must_== 247365394
        deleted.deleted must_== TestUtil.toDate("2012-04-22T08:22:06Z")
      }
    }
  }

  "movePriority" should {
    "優先度を上げる" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4488320159"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-13T11:30:37Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="0" priority="1" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.movePriority(111, 222, 333, 444, PriorityDirection.UP)
      client.methodName must_== "rtm.tasks.movePriority"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-13T11:30:37Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.ONE
      task.postponed must_== 0
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4488320159L
      transaction.undoable must beTrue
    }

    "優先度を下げる" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4488347540"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-13T11:35:01Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="0" priority="3" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.movePriority(111, 222, 333, 444, PriorityDirection.DOWN)
      client.methodName must_== "rtm.tasks.movePriority"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-13T11:35:01Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.THREE
      task.postponed must_== 0
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4488347540L
      transaction.undoable must beTrue
    }
  }

  "moveTo" should {
    "タスクを別のリストに移動" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4506537044"></transaction>
          <list id="223">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-15T09:11:58Z" created="2013-02-13T08:21:38Z" id="333">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="2" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="444"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.moveTo(111, 222, 223, 333, 444)
      client.methodName must_== "rtm.tasks.moveTo"

      val taskList = transactional.result
      taskList.id must_== 223

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 333
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-15T09:11:58Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 444
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 2
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4506537044L
      transaction.undoable must beTrue
    }
  }

  "postpone" should {
    "期日設定" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4505994722"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-15T07:48:37Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="1" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="1" due="2013-02-16T07:38:12Z" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.postpone(111, 222, 333, 444)
      client.methodName must_== "rtm.tasks.postpone"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-15T07:48:37Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must_== Some(TestUtil.toDate("2013-02-16T07:38:12Z"))
      task.hasDueTime must beTrue
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 1
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4505994722L
      transaction.undoable must beTrue
    }
  }

  "removeTags" should {
    "タグ1個削除" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4487523973"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-13T09:23:13Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags><tag>tag2</tag><tag>tag3</tag><tag>tag4</tag></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.removeTags(111, 222, 333, 444, "tag1")
      client.methodName must_== "rtm.tasks.removeTags"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-13T09:23:13Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 0
      task.estimate must beNone
      taskseries.rrule must beNone

      taskseries.tags.size must_== 3
      taskseries.tags(0) must_== "tag2"
      taskseries.tags(1) must_== "tag3"
      taskseries.tags(2) must_== "tag4"

      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4487523973L
      transaction.undoable must beTrue
    }

    "タグ複数個削除" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4487553457"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-13T09:28:28Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.removeTags(111, 222, 333, 444, "tag2", "tag3", "tag4")
      client.methodName must_== "rtm.tasks.removeTags"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-13T09:28:28Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 0
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4487553457L
      transaction.undoable must beTrue
    }
  }

  "setDueDate" should {
    "期日設定" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4505923683"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-15T07:37:58Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="1" due="2013-02-15T07:38:12Z" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.setDueDate(111, 222, 333, 444, Some(TestUtil.toDate("2013-02-14T09:47:20Z")), true)
      client.methodName must_== "rtm.tasks.setDueDate"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-15T07:37:58Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must_== Some(TestUtil.toDate("2013-02-15T07:38:12Z"))
      task.hasDueTime must beTrue
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 0
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4505923683L
      transaction.undoable must beTrue
    }

    "期日設定取り消し" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4506482576"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-15T09:04:42Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="2" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.setDueDate(111, 222, 333, 444, None)
      client.methodName must_== "rtm.tasks.setDueDate"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-15T09:04:42Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must_== None
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 2
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4506482576L
      transaction.undoable must beTrue
    }
  }

  "setEstimate" should {
    "予測時間設定" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4497406566"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-14T09:47:20Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="2 hours" postponed="0" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.setEstimate(111, 222, 333, 444, Some("2 hours"))
      client.methodName must_== "rtm.tasks.setEstimate"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-14T09:47:20Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 0
      task.estimate must_== Some("2 hours")
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4497406566L
      transaction.undoable must beTrue
    }

    "予測時間削除" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4497434121"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-14T09:51:30Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.setEstimate(111, 222, 333, 444, None)
      client.methodName must_== "rtm.tasks.setEstimate"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-14T09:51:30Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 0
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4497434121L
      transaction.undoable must beTrue
    }
  }

  "setLocation" should {
    "場所設定" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4497353183"></transaction>
          <list id="26186830">
            <taskseries location_id="1021439" url="" source="api" name="TestTask" modified="2013-02-14T09:39:05Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.setLocation(111, 222, 333, 444, Some(1021439))
      client.methodName must_== "rtm.tasks.setLocation"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-14T09:39:05Z")
      taskseries.source must_== "api"
      taskseries.locationId must_== Some(1021439)
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 0
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4497353183L
      transaction.undoable must beTrue
    }

    "場所削除" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4497379681"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-14T09:43:14Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.setLocation(111, 222, 333, 444, None)
      client.methodName must_== "rtm.tasks.setLocation"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-14T09:43:14Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 0
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4497379681L
      transaction.undoable must beTrue
    }
  }

  "setName" should {
    "名前設定" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4487601550"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="NewName" modified="2013-02-13T09:37:03Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.setName(111, 222, 333, 444, "NewName")
      client.methodName must_== "rtm.tasks.setName"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "NewName"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-13T09:37:03Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 0
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4487601550L
      transaction.undoable must beTrue
    }
  }

  "setPriority" should {
    "優先度設定" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4488219205"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="NewName" modified="2013-02-13T11:14:21Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="0" priority="3" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.setPriority(111, 222, 333, 444, Priority.THREE)
      client.methodName must_== "rtm.tasks.setPriority"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "NewName"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-13T11:14:21Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.THREE
      task.postponed must_== 0
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4488219205L
      transaction.undoable must beTrue
    }
  }

  "setRecurrence" should {
    "繰り返しルール設定" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4497535029"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-14T10:07:35Z" created="2013-02-13T08:21:38Z" id="188294516">
              <rrule every="1">FREQ=WEEKLY;INTERVAL=2</rrule>
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.setRecurrence(111, 222, 333, 444, Some("Every 2 week"))
      client.methodName must_== "rtm.tasks.setRecurrence"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-14T10:07:35Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 0
      task.estimate must beNone

      val rrule = taskseries.rrule.get
      rrule.every must_== 1
      rrule.value must_== "FREQ=WEEKLY;INTERVAL=2"

      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4497535029L
      transaction.undoable must beTrue
    }

    "繰り返しルール削除" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4497573044"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-14T10:13:53Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.setRecurrence(111, 222, 333, 444, None)
      client.methodName must_== "rtm.tasks.setRecurrence"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-14T10:13:53Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 0
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4497573044L
      transaction.undoable must beTrue
    }
  }

  "setURL" should {
    "URL設定" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4497460328"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="http://www.rememberthemilk.com/" source="api" name="TestTask" modified="2013-02-14T09:55:37Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.setURL(111, 222, 333, 444, Some(Url("http://www.rememberthemilk.com/")))
      client.methodName must_== "rtm.tasks.setURL"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-14T09:55:37Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must_== Some(Url("http://www.rememberthemilk.com/"))

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 0
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4497460328L
      transaction.undoable must beTrue
    }

    "URL削除" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4497502597"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-14T10:02:12Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.setURL(111, 222, 333, 444, None)
      client.methodName must_== "rtm.tasks.setURL"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-14T10:02:12Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 0
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4497502597L
      transaction.undoable must beTrue
    }
  }

  "setTags" should {
    "タグ1個設定" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4487417438"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-13T09:04:32Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags><tag>newtag1</tag></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      // 大文字「newTag1」を指定しても小文字「newtag1」として登録される。Rtm 側の仕様と思われる。
      val transactional = client.tasks.setTags(111, 222, 333, 444, "newTag1")
      client.methodName must_== "rtm.tasks.setTags"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-13T09:04:32Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 0
      task.estimate must beNone
      taskseries.rrule must beNone

      taskseries.tags.size must_== 1
      taskseries.tags.head must_== "newtag1"

      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4487417438L
      transaction.undoable must beTrue
    }

    "タグ複数個設定" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4487444692"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-13T09:09:22Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags><tag>newtag2</tag><tag>newtag3</tag><tag>newtag4</tag></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.setTags(111, 222, 333, 444, "newTag2", "newTag3", "newTag4")
      client.methodName must_== "rtm.tasks.setTags"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-13T09:09:22Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 0
      task.estimate must beNone
      taskseries.rrule must beNone

      taskseries.tags.size must_== 3
      taskseries.tags(0) must_== "newtag2"
      taskseries.tags(1) must_== "newtag3"
      taskseries.tags(2) must_== "newtag4"

      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4487444692L
      transaction.undoable must beTrue
    }

    "タグ全削除" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4487459807"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-13T09:11:59Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="0" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.setTags(111, 222, 333, 444)
      client.methodName must_== "rtm.tasks.setTags"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-13T09:11:59Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 0
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4487459807L
      transaction.undoable must beTrue
    }
  }

  "uncomplete" should {
    "タスク完了" in {
      val expectedResponse =
        <rsp stat="ok">
          <transaction undoable="1" id="4507746964"></transaction>
          <list id="26186830">
            <taskseries location_id="" url="" source="api" name="TestTask" modified="2013-02-15T12:24:20Z" created="2013-02-13T08:21:38Z" id="188294516">
              <tags></tags>
              <participants></participants>
              <notes></notes>
              <task estimate="" postponed="2" priority="N" deleted="" completed="" added="2013-02-13T08:21:38Z" has_due_time="0" due="" id="302764000"></task>
            </taskseries>
          </list>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val transactional = client.tasks.uncomplete(111, 222, 333, 444)
      client.methodName must_== "rtm.tasks.uncomplete"

      val taskList = transactional.result
      taskList.id must_== 26186830

      taskList.taskseries.size must_== 1
      val taskseries = taskList.taskseries.head
      taskseries.id must_== 188294516
      taskseries.name must_== "TestTask"
      taskseries.created must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      taskseries.modified must_== TestUtil.toDate("2013-02-15T12:24:20Z")
      taskseries.source must_== "api"
      taskseries.locationId must beNone
      taskseries.url must beNone

      val task = taskseries.task
      task.id must_== 302764000
      task.due must beNone
      task.hasDueTime must beFalse
      task.added must_== TestUtil.toDate("2013-02-13T08:21:38Z")
      task.completed must beNone
      task.deleted must beNone
      task.priority must_== Priority.NONE
      task.postponed must_== 2
      task.estimate must beNone
      taskseries.rrule must beNone
      taskseries.tags must beEmpty
      taskseries.participants must beEmpty
      taskseries.notes must beEmpty

      taskList.deleted must beEmpty

      val transaction = transactional.transaction
      transaction.id must_== 4507746964L
      transaction.undoable must beTrue
    }
  }
}