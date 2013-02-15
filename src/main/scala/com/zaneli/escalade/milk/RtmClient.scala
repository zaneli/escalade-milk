package com.zaneli.escalade.milk

import com.zaneli.escalade.milk.model.ArrayResult
import com.zaneli.escalade.milk.model.EmptyResult
import com.zaneli.escalade.milk.model.ModelUtil
import com.zaneli.escalade.milk.model.Param
import com.zaneli.escalade.milk.model.StringsResult
import com.zaneli.escalade.milk.model.Transaction
import com.zaneli.escalade.milk.model.Transactional

import java.util.Date

abstract class RtmClient[A <: TokenChecker] private[milk] (apiKey: String, sharedSecret: String, authToken: Option[String]) {
  private[RtmClient] case class ClientCondition[TokenChecker]()
  type TokenSetClient = ClientCondition[HasToken]
  type TokenUnsetClient = ClientCondition[NotHasToken]
  type Condition = ClientCondition[A]

  private val executor = new MethodExecutor(apiKey, sharedSecret)

  private[milk] val callApi: ((String, Map[String, String]) => scala.xml.Node)

  val auth = new Auth
  private val _contacts = new Contacts
  private val _groups = new Groups
  private val _lists = new Lists
  private val _locations = new Locations
  val reflection = new Reflection
  private val _settings = new Settings
  private val _tasks = new Tasks
  val test = new Test
  val time = new Time
  private val _timelines = new Timelines
  val timezones = new Timezones
  private val _transactions = new Transactions

  def contacts()(implicit HAS_TOKEN: Condition =:= TokenSetClient): Contacts = {
    _contacts
  }

  def groups()(implicit HAS_TOKEN: Condition =:= TokenSetClient): Groups = {
    _groups
  }

  def lists()(implicit HAS_TOKEN: Condition =:= TokenSetClient): Lists = {
    _lists
  }

  def locations()(implicit HAS_TOKEN: Condition =:= TokenSetClient): Locations = {
    _locations
  }

  def settings()(implicit HAS_TOKEN: Condition =:= TokenSetClient): Settings = {
    _settings
  }

  def tasks()(implicit HAS_TOKEN: Condition =:= TokenSetClient): Tasks = {
    _tasks
  }

  def timelines()(implicit HAS_TOKEN: Condition =:= TokenSetClient): Timelines = {
    _timelines
  }

  def transactions()(implicit HAS_TOKEN: Condition =:= TokenSetClient): Transactions = {
    _transactions
  }

  class Auth private[RtmClient] () {
    import com.zaneli.escalade.milk.model.Frob
    import com.zaneli.escalade.milk.model.Token

    private val categoryName = "auth"

    def checkToken(authToken: String): Token = {
      executor.execute(
        getMethodName(categoryName, "checkToken"),
        createParams(Map("auth_token" -> authToken)),
        classOf[Token])(callApi)
    }

    def getFrob(): Frob = {
      executor.execute(
        getMethodName(categoryName, "getFrob"),
        createParams(),
        classOf[Frob])(callApi)
    }

    def getToken(frob: String): Token = {
      executor.execute(
        getMethodName(categoryName, "getToken"),
        createParams(Map("frob" -> frob)),
        classOf[Token])(callApi)
    }
  }

  class Contacts private[RtmClient] () {
    import com.zaneli.escalade.milk.model.Contact

    private val categoryName = "contacts"

    def add(timeline: Long, contact: String): Transactional[Contact] = {
      executor.execute(
        getMethodName(categoryName, "add"),
        createParams(Map("timeline" -> timeline, "contact" -> contact)),
        classOf[Transactional[Contact]])(callApi)
    }

    def delete(timeline: Long, contactId: Long): Transaction = {
      executor.execute(
        getMethodName(categoryName, "delete"),
        createParams(Map("timeline" -> timeline, "contact_id" -> contactId)),
        classOf[Transaction])(callApi)
    }

    def getList(): Array[Contact] = {
      executor.execute(
        getMethodName(categoryName, "getList"),
        createParams(),
        classOf[ArrayResult[Contact]])(callApi).values
    }
  }

  class Groups private[RtmClient] () {
    import com.zaneli.escalade.milk.model.Group

    private val categoryName = "groups"

    def add(timeline: Long, group: String): Transactional[Group] = {
      executor.execute(
        getMethodName(categoryName, "add"),
        createParams(Map("timeline" -> timeline, "group" -> group)),
        classOf[Transactional[Group]])(callApi)
    }

    def addContact(timeline: Long, groupId: Long, contactId: Long): Transaction = {
      executor.execute(
        getMethodName(categoryName, "addContact"),
        createParams(Map("timeline" -> timeline, "group_id" -> groupId, "contact_id" -> contactId)),
        classOf[Transaction])(callApi)
    }

    def delete(timeline: Long, groupId: Long): Transaction = {
      executor.execute(
        getMethodName(categoryName, "delete"),
        createParams(Map("timeline" -> timeline, "group_id" -> groupId)),
        classOf[Transaction])(callApi)
    }

    def getList(): Array[Group] = {
      executor.execute(
        getMethodName(categoryName, "getList"),
        createParams(),
        classOf[ArrayResult[Group]])(callApi).values
    }

    def removeContact(timeline: Long, groupId: Long, contactId: Long): Transaction = {
      executor.execute(
        getMethodName(categoryName, "removeContact"),
        createParams(Map("timeline" -> timeline, "group_id" -> groupId, "contact_id" -> contactId)),
        classOf[Transaction])(callApi)
    }
  }

  class Lists private[RtmClient] () {
    import com.zaneli.escalade.milk.model.List

    private val categoryName = "lists"

    def add(timeline: Long, name: String): Transactional[List] = {
      executor.execute(
        getMethodName(categoryName, "add"),
        createParams(Map("timeline" -> timeline, "name" -> name)),
        classOf[Transactional[List]])(callApi)
    }

    def archive(timeline: Long, listId: Long): Transactional[List] = {
      executor.execute(
        getMethodName(categoryName, "archive"),
        createParams(Map("timeline" -> timeline, "list_id" -> listId)),
        classOf[Transactional[List]])(callApi)
    }

    def delete(timeline: Long, listId: Long): Transactional[List] = {
      executor.execute(
        getMethodName(categoryName, "delete"),
        createParams(Map("timeline" -> timeline, "list_id" -> listId)),
        classOf[Transactional[List]])(callApi)
    }

    def getList(): Array[List] = {
      executor.execute(
        getMethodName(categoryName, "getList"),
        createParams(),
        classOf[ArrayResult[List]])(callApi).values
    }

    def setDefaultList(timeline: Long, listId: Long): Transaction = {
      executor.execute(
        getMethodName(categoryName, "setDefaultList"),
        createParams(Map("timeline" -> timeline, "list_id" -> listId)),
        classOf[Transaction])(callApi)
    }

    def setName(timeline: Long, listId: Long, name: String): Transactional[List] = {
      executor.execute(
        getMethodName(categoryName, "setName"),
        createParams(Map("timeline" -> timeline, "list_id" -> listId, "name" -> name)),
        classOf[Transactional[List]])(callApi)
    }

    def unarchive(timeline: Long, listId: Long): Transactional[List] = {
      executor.execute(
        getMethodName(categoryName, "unarchive"),
        createParams(Map("timeline" -> timeline, "list_id" -> listId)),
        classOf[Transactional[List]])(callApi)
    }
  }

  class Locations private[RtmClient] () {
    import com.zaneli.escalade.milk.model.Location

    private val categoryName = "locations"

    def getList(): Array[Location] = {
      executor.execute(
        getMethodName(categoryName, "getList"),
        createParams(),
        classOf[ArrayResult[Location]])(callApi).values
    }
  }

  class Reflection private[RtmClient] () {
    import com.zaneli.escalade.milk.model.Method

    private val categoryName = "reflection"

    def getMethodInfo(methodName: String): Method = {
      executor.execute(
        getMethodName(categoryName, "getMethodInfo"),
        createParams(Map("method_name" -> methodName)),
        classOf[Method])(callApi)
    }

    def getMethods(): Array[String] = {
      executor.execute(
        getMethodName(categoryName, "getMethods"),
        createParams(),
        classOf[StringsResult])(callApi).values
    }
  }

  class Settings private[RtmClient] () {
    type SettingsModel = com.zaneli.escalade.milk.model.Settings

    private val categoryName = "settings"

    def getList(): SettingsModel = {
      executor.execute(
        getMethodName(categoryName, "getList"),
        createParams(),
        classOf[SettingsModel])(callApi)
    }
  }

  class Tasks private[RtmClient] () {
    val notes = new Notes

    import com.zaneli.escalade.milk.model.Priority
    import com.zaneli.escalade.milk.model.PriorityDirection
    import com.zaneli.escalade.milk.model.TaskList
    import com.zaneli.escalade.milk.model.Url

    type TasksModel = com.zaneli.escalade.milk.model.Tasks

    private val categoryName = "tasks"

    def add(timeline: Long, name: String, listId: Option[Long] = None, parse: Option[String] = None): Transactional[TaskList] = {
      executor.execute(
        getMethodName(categoryName, "add"),
        createParams(Map("timeline" -> timeline, "name" -> name, "list_id" -> listId, "parse" -> parse)),
        classOf[Transactional[TaskList]])(callApi)
    }

    def addTags(timeline: Long, listId: Long, taskseriesId: Long, taskId: Long, tags: String*): Transactional[TaskList] = {
      executor.execute(
        getMethodName(categoryName, "addTags"),
        createParams(Map("timeline" -> timeline, "list_id" -> listId, "taskseries_id" -> taskseriesId, "task_id" -> taskId, "tags" -> tags.mkString(","))),
        classOf[Transactional[TaskList]])(callApi)
    }

    def complete(timeline: Long, listId: Long, taskseriesId: Long, taskId: Long): Transactional[TaskList] = {
      executor.execute(
        getMethodName(categoryName, "complete"),
        createParams(Map("timeline" -> timeline, "list_id" -> listId, "taskseries_id" -> taskseriesId, "task_id" -> taskId)),
        classOf[Transactional[TaskList]])(callApi)
    }

    def delete(timeline: Long, listId: Long, taskseriesId: Long, taskId: Long): Transactional[TaskList] = {
      executor.execute(
        getMethodName(categoryName, "delete"),
        createParams(Map("timeline" -> timeline, "list_id" -> listId, "taskseries_id" -> taskseriesId, "task_id" -> taskId)),
        classOf[Transactional[TaskList]])(callApi)
    }

    def getList(listId: Option[Long] = None, filter: Option[String] = None, lastSync: Option[Date] = None): TasksModel = {
      executor.execute(
        getMethodName(categoryName, "getList"),
        createParams(Map("list_id" -> listId, "filter" -> filter, "last_sync" -> lastSync)),
        classOf[TasksModel])(callApi)
    }

    def movePriority(timeline: Long, listId: Long, taskseriesId: Long, taskId: Long, direction: PriorityDirection): Transactional[TaskList] = {
      executor.execute(
        getMethodName(categoryName, "movePriority"),
        createParams(Map("timeline" -> timeline, "list_id" -> listId, "taskseries_id" -> taskseriesId, "task_id" -> taskId, "direction" -> direction)),
        classOf[Transactional[TaskList]])(callApi)
    }

    def moveTo(timeline: Long, fromListId: Long, toListId: Long, taskseriesId: Long, taskId: Long): Transactional[TaskList] = {
      executor.execute(
        getMethodName(categoryName, "moveTo"),
        createParams(Map("timeline" -> timeline, "from_list_id" -> fromListId, "to_list_id" -> toListId, "taskseries_id" -> taskseriesId, "task_id" -> taskId)),
        classOf[Transactional[TaskList]])(callApi)
    }

    def postpone(timeline: Long, listId: Long, taskseriesId: Long, taskId: Long): Transactional[TaskList] = {
      executor.execute(
        getMethodName(categoryName, "postpone"),
        createParams(Map("timeline" -> timeline, "list_id" -> listId, "taskseries_id" -> taskseriesId, "task_id" -> taskId)),
        classOf[Transactional[TaskList]])(callApi)
    }

    def removeTags(timeline: Long, listId: Long, taskseriesId: Long, taskId: Long, tags: String*): Transactional[TaskList] = {
      executor.execute(
        getMethodName(categoryName, "removeTags"),
        createParams(Map("timeline" -> timeline, "list_id" -> listId, "taskseries_id" -> taskseriesId, "task_id" -> taskId, "tags" -> tags.mkString(","))),
        classOf[Transactional[TaskList]])(callApi)
    }

    def setDueDate(
      timeline: Long, listId: Long, taskseriesId: Long, taskId: Long, due: Option[Date], hasDueTime: Boolean = false): Transactional[TaskList] = {
      executor.execute(
        getMethodName(categoryName, "setDueDate"),
        createParams(Map(
          "timeline" -> timeline,
          "list_id" -> listId,
          "taskseries_id" -> taskseriesId,
          "task_id" -> taskId,
          "due" -> due,
          "has_due_time" -> (if (hasDueTime) "1" else "0"))),
        classOf[Transactional[TaskList]])(callApi)
    }

    def setEstimate(timeline: Long, listId: Long, taskseriesId: Long, taskId: Long, estimate: Option[String]): Transactional[TaskList] = {
      executor.execute(
        getMethodName(categoryName, "setEstimate"),
        createParams(Map("timeline" -> timeline, "list_id" -> listId, "taskseries_id" -> taskseriesId, "task_id" -> taskId, "estimate" -> estimate)),
        classOf[Transactional[TaskList]])(callApi)
    }

    def setLocation(timeline: Long, listId: Long, taskseriesId: Long, taskId: Long, locationId: Option[Long]): Transactional[TaskList] = {
      executor.execute(
        getMethodName(categoryName, "setLocation"),
        createParams(Map("timeline" -> timeline, "list_id" -> listId, "taskseries_id" -> taskseriesId, "task_id" -> taskId, "location_id" -> locationId)),
        classOf[Transactional[TaskList]])(callApi)
    }

    def setName(timeline: Long, listId: Long, taskseriesId: Long, taskId: Long, name: String): Transactional[TaskList] = {
      executor.execute(
        getMethodName(categoryName, "setName"),
        createParams(Map("timeline" -> timeline, "list_id" -> listId, "taskseries_id" -> taskseriesId, "task_id" -> taskId, "name" -> name)),
        classOf[Transactional[TaskList]])(callApi)
    }

    def setPriority(timeline: Long, listId: Long, taskseriesId: Long, taskId: Long, priority: Priority): Transactional[TaskList] = {
      executor.execute(
        getMethodName(categoryName, "setPriority"),
        createParams(Map("timeline" -> timeline, "list_id" -> listId, "taskseries_id" -> taskseriesId, "task_id" -> taskId, "priority" -> priority)),
        classOf[Transactional[TaskList]])(callApi)
    }

    def setRecurrence(timeline: Long, listId: Long, taskseriesId: Long, taskId: Long, repeat: Option[String]): Transactional[TaskList] = {
      executor.execute(
        getMethodName(categoryName, "setRecurrence"),
        createParams(Map("timeline" -> timeline, "list_id" -> listId, "taskseries_id" -> taskseriesId, "task_id" -> taskId, "repeat" -> repeat)),
        classOf[Transactional[TaskList]])(callApi)
    }

    def setTags(timeline: Long, listId: Long, taskseriesId: Long, taskId: Long, tags: String*): Transactional[TaskList] = {
      executor.execute(
        getMethodName(categoryName, "setTags"),
        createParams(Map("timeline" -> timeline, "list_id" -> listId, "taskseries_id" -> taskseriesId, "task_id" -> taskId, "tags" -> tags.mkString(","))),
        classOf[Transactional[TaskList]])(callApi)
    }

    def setURL(timeline: Long, listId: Long, taskseriesId: Long, taskId: Long, url: Option[Url]): Transactional[TaskList] = {
      executor.execute(
        getMethodName(categoryName, "setURL"),
        createParams(Map("timeline" -> timeline, "list_id" -> listId, "taskseries_id" -> taskseriesId, "task_id" -> taskId, "url" -> url)),
        classOf[Transactional[TaskList]])(callApi)
    }

    def uncomplete(timeline: Long, listId: Long, taskseriesId: Long, taskId: Long): Transactional[TaskList] = {
      executor.execute(
        getMethodName(categoryName, "uncomplete"),
        createParams(Map("timeline" -> timeline, "list_id" -> listId, "taskseries_id" -> taskseriesId, "task_id" -> taskId)),
        classOf[Transactional[TaskList]])(callApi)
    }

    class Notes private[Tasks] () {
      import com.zaneli.escalade.milk.model.Note

      private val categoryName = "tasks.notes"

      def add(timeline: Long, listId: Long, taskseriesId: Long, taskId: Long, noteTitle: String, noteText: String): Transactional[Note] = {
        executor.execute(
          getMethodName(categoryName, "add"),
          createParams(Map(
            "timeline" -> timeline,
            "list_id" -> listId,
            "taskseries_id" -> taskseriesId,
            "task_id" -> taskId,
            "note_title" -> noteTitle,
            "note_text" -> noteText)),
          classOf[Transactional[Note]])(callApi)
      }

      def delete(timeline: Long, noteId: Long): Transaction = {
        executor.execute(
          getMethodName(categoryName, "delete"),
          createParams(Map("timeline" -> timeline, "note_id" -> noteId)),
          classOf[Transaction])(callApi)
      }

      def edit(timeline: Long, noteId: Long, noteTitle: String, noteText: String): Transactional[Note] = {
        executor.execute(
          getMethodName(categoryName, "edit"),
          createParams(Map("timeline" -> timeline, "note_id" -> noteId, "note_title" -> noteTitle, "note_text" -> noteText)),
          classOf[Transactional[Note]])(callApi)
      }
    }
  }

  class Test private[RtmClient] () {
    import com.zaneli.escalade.milk.model.Echo
    import com.zaneli.escalade.milk.model.User

    private val categoryName = "test"

    def echo(params: Map[String, String]): Map[String, String] = {
      executor.execute(
        getMethodName(categoryName, "echo"),
        createParams(params.filterKeys(!Echo.excludeParamKeys.contains(_))),
        classOf[Echo])(callApi).params
    }

    def login()(implicit HAS_TOKEN: Condition =:= TokenSetClient): User = {
      executor.execute(
        getMethodName(categoryName, "login"),
        createParams(),
        classOf[User])(callApi)
    }
  }

  class Time private[RtmClient] () {
    import com.zaneli.escalade.milk.model.Dateformat
    import com.zaneli.escalade.milk.model.ParsedTime
    type TimeModel = com.zaneli.escalade.milk.model.Time

    private val categoryName = "time"

    def convert(toTimezone: String, fromTimezone: Option[String] = None, time: Option[Date] = None): TimeModel = {
      executor.execute(
        getMethodName(categoryName, "convert"),
        createParams(Map("to_timezone" -> toTimezone, "from_timezone" -> fromTimezone, "time" -> (time match {
          case Some(d) => ModelUtil.formatDate(d, false)
          case None => ModelUtil.formatDate(new Date, false)
        }))), classOf[TimeModel])(callApi)
    }

    def parse(text: String, timezone: Option[String] = None, dateformat: Option[Dateformat] = None): ParsedTime = {
      executor.execute(
        getMethodName(categoryName, "parse"),
        createParams(Map("text" -> text, "timezone" -> timezone, "dateformat" -> dateformat)),
        classOf[ParsedTime])(callApi)
    }
  }

  class Timelines private[RtmClient] () {
    import com.zaneli.escalade.milk.model.LongResult

    private val categoryName = "timelines"

    def create(): Long = {
      executor.execute(
        getMethodName(categoryName, "create"),
        createParams(),
        classOf[LongResult])(callApi).value
    }
  }

  class Timezones private[RtmClient] () {
    import com.zaneli.escalade.milk.model.Timezone

    private val categoryName = "timezones"

    def getList(): Array[Timezone] = {
      executor.execute(
        getMethodName(categoryName, "getList"),
        createParams(),
        classOf[ArrayResult[Timezone]])(callApi).values
    }
  }

  class Transactions private[RtmClient] () {
    private val categoryName = "transactions"

    def undo(timeline: Long, transactionId: Long) {
      executor.execute(
        getMethodName(categoryName, "undo"),
        createParams(Map("timeline" -> timeline, "transaction_id" -> transactionId)),
        classOf[EmptyResult])(callApi)
    }
  }

  private def getMethodName(categoryName: String, methodName: String): String = {
    "rtm." + categoryName + "." + methodName
  }

  private def createParams(params: Map[String, Any] = Map()): Map[String, String] = {
    // params の要素に api_key, auth_token が含まれている場合、そちらを優先させるため、
    // Mapの結合順は params を後にする
    (authToken match {
      case Some(token) => Map("api_key" -> apiKey, "auth_token" -> token)
      case None => Map("api_key" -> apiKey)
    }) ++ params.filter(_._2 != None).map(getParam)
  }

  private def getParam(elem: (String, Any)): (String, String) = elem match {
    case (k, Some(v)) => getParam(k, v)
    case (k, v: Param) => k -> v.paramValue
    case (k, v: Date) => k -> ModelUtil.formatDate(v)
    case (k, v) => k -> v.toString
  }
}
