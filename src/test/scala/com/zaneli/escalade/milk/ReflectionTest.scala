package com.zaneli.escalade.milk

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import com.zaneli.escalade.milk.model.Perms

@RunWith(classOf[JUnitRunner])
class ReflectionTest extends Specification with Mockito {

  "getMethodInfo" should {
    "権限が不要なメソッド" in {
      val expectedResponse =
        <rsp stat="ok">
          <method requiredperms="0" needssigning="1" needslogin="0" name="rtm.auth.getFrob">
            <description>
              Returns a frob to be used during authentication.
 &lt;b&gt;This method call must be signed.&lt;/b&gt;
            </description>
            <response>&lt;frob&gt;0a56717c3561e53584f292bb7081a533c197270c&lt;/frob&gt;</response>
            <arguments>
              <argument optional="0" name="api_key">Your API application key. &lt;a href=&quot;/services/api/keys.rtm&quot;&gt;See here&lt;/a&gt; for more details.</argument>
            </arguments>
            <errors>
              <error message="Invalid signature" code="96">The passed signature was invalid.</error>
              <error message="Missing signature" code="97">The call required signing but no signature was sent.</error>
              <error message="Invalid API Key" code="100">The API key passed was not valid or has expired.</error>
              <error message="Service currently unavailable" code="105">The requested service is temporarily unavailable.</error>
              <error message="Format &quot;xxx&quot; not found" code="111">The requested response format was not found.</error>
              <error message="Method &quot;xxx&quot; not found" code="112">The requested method was not found.</error>
              <error message="Invalid SOAP envelope" code="114">The SOAP envelope sent in the request could not be parsed.</error>
              <error message="Invalid XML-RPC Method Call" code="115">The XML-RPC request document could not be parsed.</error>
            </errors>
          </method>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val method = client.reflection.getMethodInfo("rtm.auth.getFrob")
      client.methodName must_== "rtm.reflection.getMethodInfo"

      method.name must_== "rtm.auth.getFrob"
      method.needslogin must beFalse
      method.needssigning must beTrue
      method.requiredperms must_== Perms.NONE
      method.description must_== """Returns a frob to be used during authentication.
 <b>This method call must be signed.</b>"""
      method.response must_== Some(<frob>0a56717c3561e53584f292bb7081a533c197270c</frob>)

      method.arguments.size must_== 1
      val argument = method.arguments(0)
      argument.name must_== "api_key"
      argument.optional must beFalse
      argument.description must_== """Your API application key. <a href="/services/api/keys.rtm">See here</a> for more details."""

      method.errors.size must_== 8

      {
        val error = method.errors(0)
        error.code must_== 96
        error.message must_== "Invalid signature"
        error.description must_== "The passed signature was invalid."
      }
      {
        val error = method.errors(1)
        error.code must_== 97
        error.message must_== "Missing signature"
        error.description must_== "The call required signing but no signature was sent."
      }
      {
        val error = method.errors(2)
        error.code must_== 100
        error.message must_== "Invalid API Key"
        error.description must_== "The API key passed was not valid or has expired."
      }
      {
        val error = method.errors(3)
        error.code must_== 105
        error.message must_== "Service currently unavailable"
        error.description must_== "The requested service is temporarily unavailable."
      }
      {
        val error = method.errors(4)
        error.code must_== 111
        error.message must_== """Format "xxx" not found"""
        error.description must_== "The requested response format was not found."
      }
      {
        val error = method.errors(5)
        error.code must_== 112
        error.message must_== """Method "xxx" not found"""
        error.description must_== "The requested method was not found."
      }
      {
        val error = method.errors(6)
        error.code must_== 114
        error.message must_== "Invalid SOAP envelope"
        error.description must_== "The SOAP envelope sent in the request could not be parsed."
      }
      {
        val error = method.errors(7)
        error.code must_== 115
        error.message must_== "Invalid XML-RPC Method Call"
        error.description must_== "The XML-RPC request document could not be parsed."
      }
    }

    "読み取り権限が必要なメソッド" in {
      val expectedResponse =
        <rsp stat="ok">
          <method requiredperms="1" needssigning="1" needslogin="1" name="rtm.locations.getList">
            <description>
              Retrieves a list of locations.
 &lt;b&gt;This method call must be signed.&lt;/b&gt;
            </description>
            <response>
              &lt;locations&gt;
  &lt;location id=&quot;987654321&quot; name=&quot;Berlin&quot; longitude=&quot;13.411508&quot;
          latitude=&quot;52.524008&quot; zoom=&quot;9&quot; address=&quot;Berlin, Germany&quot; viewable=&quot;1&quot;/&gt;
  &lt;location id=&quot;987654322&quot; name=&quot;New York&quot; longitude=&quot;-74.00713&quot;
          latitude=&quot;40.71449&quot; zoom=&quot;9&quot; address=&quot;New York, NY, USA&quot; viewable=&quot;1&quot;/&gt;
  &lt;location id=&quot;987654323&quot; name=&quot;Sydney&quot; longitude=&quot;151.216667&quot;
           latitude=&quot;-33.8833333&quot; zoom=&quot;7&quot;
           address=&quot;Sydney, New South Wales, Australia&quot; viewable=&quot;1&quot;/&gt;
  ...
&lt;/locations&gt;
            </response>
            <arguments>
              <argument optional="0" name="api_key">Your API application key. &lt;a href=&quot;/services/api/keys.rtm&quot;&gt;See here&lt;/a&gt; for more details.</argument>
            </arguments>
            <errors>
              <error message="Invalid signature" code="96">The passed signature was invalid.</error>
              <error message="Missing signature" code="97">The call required signing but no signature was sent.</error>
              <error message="Login failed / Invalid auth token" code="98">The login details or auth token passed were invalid.</error>
              <error message="Invalid API Key" code="100">The API key passed was not valid or has expired.</error>
              <error message="Service currently unavailable" code="105">The requested service is temporarily unavailable.</error>
              <error message="Format &quot;xxx&quot; not found" code="111">The requested response format was not found.</error>
              <error message="Method &quot;xxx&quot; not found" code="112">The requested method was not found.</error>
              <error message="Invalid SOAP envelope" code="114">The SOAP envelope sent in the request could not be parsed.</error>
              <error message="Invalid XML-RPC Method Call" code="115">The XML-RPC request document could not be parsed.</error>
            </errors>
          </method>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val method = client.reflection.getMethodInfo("rtm.locations.getList")
      client.methodName must_== "rtm.reflection.getMethodInfo"

      method.name must_== "rtm.locations.getList"
      method.needslogin must beTrue
      method.needssigning must beTrue
      method.requiredperms must_== Perms.READ
      method.description must_== """Retrieves a list of locations.
 <b>This method call must be signed.</b>"""
      method.response.get must ==/(
        <locations>
          <location id="987654321" name="Berlin" longitude="13.411508" latitude="52.524008" zoom="9" address="Berlin, Germany" viewable="1"/>
          <location id="987654322" name="New York" longitude="-74.00713" latitude="40.71449" zoom="9" address="New York, NY, USA" viewable="1"/>
          <location id="987654323" name="Sydney" longitude="151.216667" latitude="-33.8833333" zoom="7" address="Sydney, New South Wales, Australia" viewable="1"/>
          ...
        </locations>)

      method.arguments.size must_== 1
      val argument = method.arguments(0)
      argument.name must_== "api_key"
      argument.optional must beFalse
      argument.description must_== """Your API application key. <a href="/services/api/keys.rtm">See here</a> for more details."""

      method.errors.size must_== 9

      {
        val error = method.errors(0)
        error.code must_== 96
        error.message must_== "Invalid signature"
        error.description must_== "The passed signature was invalid."
      }
      {
        val error = method.errors(1)
        error.code must_== 97
        error.message must_== "Missing signature"
        error.description must_== "The call required signing but no signature was sent."
      }
      {
        val error = method.errors(2)
        error.code must_== 98
        error.message must_== "Login failed / Invalid auth token"
        error.description must_== "The login details or auth token passed were invalid."
      }
      {
        val error = method.errors(3)
        error.code must_== 100
        error.message must_== "Invalid API Key"
        error.description must_== "The API key passed was not valid or has expired."
      }
      {
        val error = method.errors(4)
        error.code must_== 105
        error.message must_== "Service currently unavailable"
        error.description must_== "The requested service is temporarily unavailable."
      }
      {
        val error = method.errors(5)
        error.code must_== 111
        error.message must_== """Format "xxx" not found"""
        error.description must_== "The requested response format was not found."
      }
      {
        val error = method.errors(6)
        error.code must_== 112
        error.message must_== """Method "xxx" not found"""
        error.description must_== "The requested method was not found."
      }
      {
        val error = method.errors(7)
        error.code must_== 114
        error.message must_== "Invalid SOAP envelope"
        error.description must_== "The SOAP envelope sent in the request could not be parsed."
      }
      {
        val error = method.errors(8)
        error.code must_== 115
        error.message must_== "Invalid XML-RPC Method Call"
        error.description must_== "The XML-RPC request document could not be parsed."
      }
    }

    "書き込み権限が必要なメソッド" in {
      val expectedResponse =
        <rsp stat="ok">
          <method needstimeline="1" requiredperms="2" needssigning="1" needslogin="1" name="rtm.contacts.add">
            <description>
              Adds a new contact. &lt;code&gt;contact&lt;/code&gt; should be a username or email address of a Remember The Milk user.
 &lt;b&gt;This method call must be signed.&lt;/b&gt;
 &lt;b&gt;This method call requires a timeline.&lt;/b&gt;
            </description>
            <response>&lt;contact id=&quot;1&quot; fullname=&quot;Omar Kilani&quot; username=&quot;omar&quot;/&gt;</response>
            <arguments>
              <argument optional="0" name="api_key">Your API application key. &lt;a href=&quot;/services/api/keys.rtm&quot;&gt;See here&lt;/a&gt; for more details.</argument>
              <argument optional="0" name="timeline">The timeline within which to run a method. &lt;a href=&quot;/services/api/timelines.rtm&quot;&gt;See here&lt;/a&gt; for more details.</argument>
              <argument optional="0" name="contact">The contact to add. Can be a username or an email address of a registered Remember The Milk user.</argument>
            </arguments>
            <errors>
              <error message="Invalid signature" code="96">The passed signature was invalid.</error>
              <error message="Missing signature" code="97">The call required signing but no signature was sent.</error>
              <error message="Login failed / Invalid auth token" code="98">The login details or auth token passed were invalid.</error>
              <error message="Invalid API Key" code="100">The API key passed was not valid or has expired.</error>
              <error message="Service currently unavailable" code="105">The requested service is temporarily unavailable.</error>
              <error message="Format &quot;xxx&quot; not found" code="111">The requested response format was not found.</error>
              <error message="Method &quot;xxx&quot; not found" code="112">The requested method was not found.</error>
              <error message="Invalid SOAP envelope" code="114">The SOAP envelope sent in the request could not be parsed.</error>
              <error message="Invalid XML-RPC Method Call" code="115">The XML-RPC request document could not be parsed.</error>
              <error message="Timeline invalid or not provided" code="300">No timeline provided / Timeline invalid.</error>
              <error message="Contact provided is invalid." code="1000">The contact provided is invalid.</error>
              <error message="Contact provided already exists." code="1010">The contact provided is already listed as a contact for this user.</error>
              <error message="Contact requested does not exist." code="1020">The contact requested does not exist.</error>
              <error message="Cannot add yourself as a contact." code="1030">You cannot add yourself as a contact.</error>
            </errors>
          </method>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val method = client.reflection.getMethodInfo("rtm.contacts.add")
      client.methodName must_== "rtm.reflection.getMethodInfo"

      method.name must_== "rtm.contacts.add"
      method.needslogin must beTrue
      method.needssigning must beTrue
      method.requiredperms must_== Perms.READ_WRITE
      method.description must_== """Adds a new contact. <code>contact</code> should be a username or email address of a Remember The Milk user.
 <b>This method call must be signed.</b>
 <b>This method call requires a timeline.</b>"""
      method.response must_== Some(<contact id="1" fullname="Omar Kilani" username="omar"/>)

      method.arguments.size must_== 3

      {
        val argument = method.arguments(0)
        argument.name must_== "api_key"
        argument.optional must beFalse
        argument.description must_== """Your API application key. <a href="/services/api/keys.rtm">See here</a> for more details."""
      }
      {
        val argument = method.arguments(1)
        argument.name must_== "timeline"
        argument.optional must beFalse
        argument.description must_== """The timeline within which to run a method. <a href="/services/api/timelines.rtm">See here</a> for more details."""
      }
      {
        val argument = method.arguments(2)
        argument.name must_== "contact"
        argument.optional must beFalse
        argument.description must_== """The contact to add. Can be a username or an email address of a registered Remember The Milk user."""
      }

      method.errors.size must_== 14

      {
        val error = method.errors(0)
        error.code must_== 96
        error.message must_== "Invalid signature"
        error.description must_== "The passed signature was invalid."
      }
      {
        val error = method.errors(1)
        error.code must_== 97
        error.message must_== "Missing signature"
        error.description must_== "The call required signing but no signature was sent."
      }
      {
        val error = method.errors(2)
        error.code must_== 98
        error.message must_== "Login failed / Invalid auth token"
        error.description must_== "The login details or auth token passed were invalid."
      }
      {
        val error = method.errors(3)
        error.code must_== 100
        error.message must_== "Invalid API Key"
        error.description must_== "The API key passed was not valid or has expired."
      }
      {
        val error = method.errors(4)
        error.code must_== 105
        error.message must_== "Service currently unavailable"
        error.description must_== "The requested service is temporarily unavailable."
      }
      {
        val error = method.errors(5)
        error.code must_== 111
        error.message must_== """Format "xxx" not found"""
        error.description must_== "The requested response format was not found."
      }
      {
        val error = method.errors(6)
        error.code must_== 112
        error.message must_== """Method "xxx" not found"""
        error.description must_== "The requested method was not found."
      }
      {
        val error = method.errors(7)
        error.code must_== 114
        error.message must_== "Invalid SOAP envelope"
        error.description must_== "The SOAP envelope sent in the request could not be parsed."
      }
      {
        val error = method.errors(8)
        error.code must_== 115
        error.message must_== "Invalid XML-RPC Method Call"
        error.description must_== "The XML-RPC request document could not be parsed."
      }
      {
        val error = method.errors(9)
        error.code must_== 300
        error.message must_== "Timeline invalid or not provided"
        error.description must_== "No timeline provided / Timeline invalid."
      }
      {
        val error = method.errors(10)
        error.code must_== 1000
        error.message must_== "Contact provided is invalid."
        error.description must_== "The contact provided is invalid."
      }
      {
        val error = method.errors(11)
        error.code must_== 1010
        error.message must_== "Contact provided already exists."
        error.description must_== "The contact provided is already listed as a contact for this user."
      }
      {
        val error = method.errors(12)
        error.code must_== 1020
        error.message must_== "Contact requested does not exist."
        error.description must_== "The contact requested does not exist."
      }
      {
        val error = method.errors(13)
        error.code must_== 1030
        error.message must_== "Cannot add yourself as a contact."
        error.description must_== "You cannot add yourself as a contact."
      }
    }

    "削除権限が必要なメソッド" in {
      val expectedResponse =
        <rsp stat="ok">
          <method needstimeline="1" requiredperms="3" needssigning="1" needslogin="1" name="rtm.transactions.undo">
            <description>
              Reverts the affects of an action.
 &lt;b&gt;This method call must be signed.&lt;/b&gt;
 &lt;b&gt;This method call requires a timeline.&lt;/b&gt;
            </description>
            <response>
            </response>
            <arguments>
              <argument optional="0" name="api_key">Your API application key. &lt;a href=&quot;/services/api/keys.rtm&quot;&gt;See here&lt;/a&gt; for more details.</argument>
              <argument optional="0" name="timeline">The timeline within which to run a method. &lt;a href=&quot;/services/api/timelines.rtm&quot;&gt;See here&lt;/a&gt; for more details.</argument>
              <argument optional="0" name="transaction_id">The id of transaction within a &lt;a href=&quot;/services/api/timelines.rtm&quot;&gt;timeline&lt;/a&gt;.</argument>
            </arguments>
            <errors>
              <error message="Invalid signature" code="96">The passed signature was invalid.</error>
              <error message="Missing signature" code="97">The call required signing but no signature was sent.</error>
              <error message="Login failed / Invalid auth token" code="98">The login details or auth token passed were invalid.</error>
              <error message="Invalid API Key" code="100">The API key passed was not valid or has expired.</error>
              <error message="Service currently unavailable" code="105">The requested service is temporarily unavailable.</error>
              <error message="Format &quot;xxx&quot; not found" code="111">The requested response format was not found.</error>
              <error message="Method &quot;xxx&quot; not found" code="112">The requested method was not found.</error>
              <error message="Invalid SOAP envelope" code="114">The SOAP envelope sent in the request could not be parsed.</error>
              <error message="Invalid XML-RPC Method Call" code="115">The XML-RPC request document could not be parsed.</error>
              <error message="Timeline invalid or not provided" code="300">No timeline provided / Timeline invalid.</error>
              <error message="transaction_id invalid or not provided" code="310">No transaction_id provided / transaction_id invalid.</error>
            </errors>
          </method>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val method = client.reflection.getMethodInfo("rtm.transactions.undo")
      client.methodName must_== "rtm.reflection.getMethodInfo"

      method.name must_== "rtm.transactions.undo"
      method.needslogin must beTrue
      method.needssigning must beTrue
      method.requiredperms must_== Perms.ALL
      method.description must_== """Reverts the affects of an action.
 <b>This method call must be signed.</b>
 <b>This method call requires a timeline.</b>"""
      method.response must beNone

      method.arguments.size must_== 3

      {
        val argument = method.arguments(0)
        argument.name must_== "api_key"
        argument.optional must beFalse
        argument.description must_== """Your API application key. <a href="/services/api/keys.rtm">See here</a> for more details."""
      }
      {
        val argument = method.arguments(1)
        argument.name must_== "timeline"
        argument.optional must beFalse
        argument.description must_== """The timeline within which to run a method. <a href="/services/api/timelines.rtm">See here</a> for more details."""
      }
      {
        val argument = method.arguments(2)
        argument.name must_== "transaction_id"
        argument.optional must beFalse
        argument.description must_== """The id of transaction within a <a href="/services/api/timelines.rtm">timeline</a>."""
      }

      method.errors.size must_== 11

      {
        val error = method.errors(0)
        error.code must_== 96
        error.message must_== "Invalid signature"
        error.description must_== "The passed signature was invalid."
      }
      {
        val error = method.errors(1)
        error.code must_== 97
        error.message must_== "Missing signature"
        error.description must_== "The call required signing but no signature was sent."
      }
      {
        val error = method.errors(2)
        error.code must_== 98
        error.message must_== "Login failed / Invalid auth token"
        error.description must_== "The login details or auth token passed were invalid."
      }
      {
        val error = method.errors(3)
        error.code must_== 100
        error.message must_== "Invalid API Key"
        error.description must_== "The API key passed was not valid or has expired."
      }
      {
        val error = method.errors(4)
        error.code must_== 105
        error.message must_== "Service currently unavailable"
        error.description must_== "The requested service is temporarily unavailable."
      }
      {
        val error = method.errors(5)
        error.code must_== 111
        error.message must_== """Format "xxx" not found"""
        error.description must_== "The requested response format was not found."
      }
      {
        val error = method.errors(6)
        error.code must_== 112
        error.message must_== """Method "xxx" not found"""
        error.description must_== "The requested method was not found."
      }
      {
        val error = method.errors(7)
        error.code must_== 114
        error.message must_== "Invalid SOAP envelope"
        error.description must_== "The SOAP envelope sent in the request could not be parsed."
      }
      {
        val error = method.errors(8)
        error.code must_== 115
        error.message must_== "Invalid XML-RPC Method Call"
        error.description must_== "The XML-RPC request document could not be parsed."
      }
      {
        val error = method.errors(9)
        error.code must_== 300
        error.message must_== "Timeline invalid or not provided"
        error.description must_== "No timeline provided / Timeline invalid."
      }
      {
        val error = method.errors(10)
        error.code must_== 310
        error.message must_== "transaction_id invalid or not provided"
        error.description must_== "No transaction_id provided / transaction_id invalid."
      }
    }
  }

  "getMethods" should {
    "メソッド名取得" in {
      val expectedResponse =
        <rsp stat="ok">
          <methods>
            <method>rtm.tasks.add</method>
            <method>rtm.tasks.addTags</method>
            <method>rtm.tasks.complete</method>
            <method>rtm.tasks.delete</method>
            ...
          </methods>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val methods = client.reflection.getMethods
      client.methodName must_== "rtm.reflection.getMethods"

      methods.size must_== 4
      methods(0) must_== "rtm.tasks.add"
      methods(1) must_== "rtm.tasks.addTags"
      methods(2) must_== "rtm.tasks.complete"
      methods(3) must_== "rtm.tasks.delete"
    }
  }
}