#escalade-milk
[Remember The Milk](http://www.rememberthemilk.com/ "Remember The Milk") API Scala wrapper library.

[![Build Status](https://travis-ci.org/zaneli/escalade-milk.png?branch=master)](https://travis-ci.org/zaneli/escalade-milk)

##Authenticate
- Request [API Key and Shared secret](http://www.rememberthemilk.com/services/api/keys.rtm).  
- Get frob.  

```scala
val client = RtmClientFactory.createClient(<API Key>, <Shared secret>)
val frob = client.auth.getFrob.frob
```  

- Create Auth URL and Authorize API Access.  
(Permission is com.zaneli.escalade.milk.model.Perms.READ, Perms.READ_WRITE or Perms.ALL)  

```scala
println(AuthTool.createAuthUrl(<API Key>, <Shared secret>, <Permission>, frob))
```  

- Get token.  

```scala
val token = client.auth.getToken(frob).token
```  

##Call Method
- To use method what requires authentication, create RtmClient with token.  

```scala
val client = RtmClientFactory.createClient(<API Key>, <Shared secret>, token)
```  

Otherwise, create RtmClient either with or without token.  

```scala
val client = RtmClientFactory.createClient(<API Key>, <Shared secret>)
```  

- Call method.(Sample)  

```scala
val timeline = client.timelines.create

val addResult = client.tasks.add(timeline, "Task")
val taskList = addResult.result
val taskseries = taskList.taskseries.head

val setNameResult = client.tasks.setName(timeline, taskList.id, taskseries.id, taskseries.task.id, "NewName")
client.transactions.undo(timeline, setNameResult.transaction.id)
```  

More detail, read [API reference](http://www.rememberthemilk.com/services/api/methods/ "Remember The Milk API reference").
