package api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.{HttpEntity, MediaTypes, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import org.scalatest.{Matchers, WordSpec}
import service._


/**
  * Test for the API specification. Brought directly from the README file
  *
  * Created by gonzalo on 03/02/17.
  */
class URLShortenerAPITest extends WordSpec with Matchers with ScalatestRouteTest {
  var api: URLShortenerAPI = _
  var svc: URLService = _
  val OK_REQUEST = ByteString(
    s"""
       |{
       |    "url":"http://www.google.com"
       |}
        """.stripMargin)
  val ERROR_REQUEST = ByteString(
    s"""
       |{
       |    "url":"htt://www.google.com"
       |}
        """.stripMargin)
  "The API" should {

    "When a URL is submitted, a much shorter URL is returned" in {
      Post("/", HttpEntity(MediaTypes.`application/json`, OK_REQUEST)) ~> api.route ~> check {
        response.status shouldBe StatusCodes.OK
        responseAs[String] shouldEqual "http://bit.ly/0"
      }
    }

    "The same input URL should always result in the same short URL" in {
      Post("/", HttpEntity(MediaTypes.`application/json`, OK_REQUEST)) ~> api.route ~> check {
        response.status shouldBe StatusCodes.OK
        responseAs[String] shouldEqual "http://bit.ly/0"
      }
      Post("/", HttpEntity(MediaTypes.`application/json`, OK_REQUEST)) ~> api.route ~> check {
        response.status shouldBe StatusCodes.OK
        responseAs[String] shouldEqual "http://bit.ly/0"
      }
    }

    "An invalid URL should result in an HTTP 400 error" in {

      Post("/", HttpEntity(MediaTypes.`application/json`, ERROR_REQUEST)) ~> api.route ~> check {
        response.status shouldBe StatusCodes.BadRequest
        responseAs[String] shouldEqual "Not a valid URL"
      }
    }

    "When a short URL is visited, the user is redirected to the associated input URL" in {
      val result = svc.shorten(LongURL("http://gonzalo.cool"))
      assert (result.isRight)
      Get(s"/${result.right.get.slug}") ~> api.route ~> check {
        response.status shouldBe StatusCodes.TemporaryRedirect
        headers.filter(_.is("location")).map(_.value()).head shouldEqual "http://gonzalo.cool"
      }
    }

    "If a short URL does not exist, an HTTP 404 error should be returned" in {
      Get("/ABCD") ~> api.route ~> check {
        response.status shouldBe StatusCodes.NotFound
        responseAs[String] shouldEqual "URL doesn't exist"
      }
    }
  }
  "The front-end" should {
    "When we visit root, we get a page" in {
      Get("/") ~> api.route ~> check {
        response.status shouldBe StatusCodes.OK
        responseAs[String] should include ("Hello Blackboard!")
      }
    }
  }
  override def withFixture(test: NoArgTest) = {
    svc = new SimpleURLService
    api = new URLShortenerAPI {
      override val domain: String = "http://bit.ly/"
      override val service: URLService = svc
    }
    super.withFixture(test)
  }
}
