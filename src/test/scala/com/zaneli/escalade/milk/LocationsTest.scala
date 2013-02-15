package com.zaneli.escalade.milk

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class LocationsTest extends Specification with Mockito {

  "getList" should {
    "リスト1件取得" in {
      val expectedResponse =
        <rsp stat="ok">
          <locations>
            <location id="987654321" name="Berlin" longitude="13.411508" latitude="52.524008" zoom="9" address="Berlin, Germany" viewable="1"/>
          </locations>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val locations = client.locations.getList
      client.methodName must_== "rtm.locations.getList"

      locations.size must_== 1

      {
        val location = locations(0)
        location.id must_== 987654321
        location.name must_== "Berlin"
        location.longitude must_== 13.411508F
        location.latitude must_== 52.524008F
        location.zoom must_== 9
        location.address must_== "Berlin, Germany"
        location.viewable must beTrue
      }
    }

    "リスト複数件取得" in {
      val expectedResponse =
        <rsp stat="ok">
          <locations>
            <location id="987654321" name="Berlin" longitude="13.411508" latitude="52.524008" zoom="9" address="Berlin, Germany" viewable="1"/>
            <location id="987654322" name="New York" longitude="-74.00713" latitude="40.71449" zoom="9" address="New York, NY, USA" viewable="1"/>
            <location id="987654323" name="Sydney" longitude="151.216667" latitude="-33.8833333" zoom="7" address="Sydney, New South Wales, Australia" viewable="1"/>
          </locations>
        </rsp>

      val client = TestUtil.getClient(expectedResponse)
      val locations = client.locations.getList
      client.methodName must_== "rtm.locations.getList"

      locations.size must_== 3

      {
        val location = locations(0)
        location.id must_== 987654321
        location.name must_== "Berlin"
        location.longitude must_== 13.411508F
        location.latitude must_== 52.524008F
        location.zoom must_== 9
        location.address must_== "Berlin, Germany"
        location.viewable must beTrue
      }
      {
        val location = locations(1)
        location.id must_== 987654322
        location.name must_== "New York"
        location.longitude must_== -74.00713F
        location.latitude must_== 40.71449F
        location.zoom must_== 9
        location.address must_== "New York, NY, USA"
        location.viewable must beTrue
      }
      {
        val location = locations(2)
        location.id must_== 987654323
        location.name must_== "Sydney"
        location.longitude must_== 151.216667F
        location.latitude must_== -33.8833333F
        location.zoom must_== 7
        location.address must_== "Sydney, New South Wales, Australia"
        location.viewable must beTrue
      }
    }
  }
}