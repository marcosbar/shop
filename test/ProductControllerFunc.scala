import com.shop.common.configuration.Configuration
import common.{TestProduct, DatabaseHelper, FutureHelper}
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play._
import play.api.libs.json.{JsObject, JsString, Json}
import play.api.libs.ws.WS
import play.api.test.Helpers._
import scala.concurrent.ExecutionContext.Implicits.global

class ProductControllerFunc extends PlaySpec with OneServerPerSuite with DatabaseHelper with FutureHelper with BeforeAndAfterEach{

  override lazy val port = 9000

  override def beforeEach() = {
    productRepository.collection.drop()
    super.beforeEach()
  }

  "Get product" should {

    "should return 200 when the product exists" in {

      //GIVEN
      createProduct().waitUntilDone

      //WHEN
      val response = WS.url(Configuration.serviceURL+s"/product/1").get().whenValue

      //THEN
      response.status mustBe OK
      Json.parse(response.body) mustBe JsObject(
        Seq("_id" -> JsString("1"),
          "description" -> JsString("description 1"))
      )
    }

    "should return 404 when the product doesn't exist" in {

      //WHEN
      val response = WS.url(Configuration.serviceURL+s"/product/1").get().whenValue

      //THEN
      response.status mustBe NOT_FOUND
    }
  }

  "Insert product" should {

    "should return 201 when the product is stored" in {

      //WHEN
      val response = WS.url(Configuration.serviceURL+s"/product").post(Json.toJson(TestProduct(_id = "1", description = "description 1"))).whenValue

      //THEN
      response.status mustBe CREATED
    }

    "should return 400 when the body is empty" in {

      //WHEN
      val response = WS.url(Configuration.serviceURL+s"/product").post("").whenValue

      //THEN
      response.status mustBe BAD_REQUEST
    }

    "should return 400 when the body is malformed" in {

      //WHEN
      val response = WS.url(Configuration.serviceURL+s"/product").post("""{"key":"value"}}""").whenValue

      //THEN
      response.status mustBe BAD_REQUEST
    }

    "should return 400 when all the mandatory fields are not provided" in {

      //WHEN
      val response = WS.url(Configuration.serviceURL+s"/product").post("""{"_id":"1"}""").whenValue

      //THEN
      response.status mustBe BAD_REQUEST
    }

    "should return 500 when the product already exists" in {
      //GIVEN
      createProduct().waitUntilDone

      //WHEN
      val response = WS.url(Configuration.serviceURL+s"/product").post(Json.toJson(TestProduct(_id = "1", description = "description 1"))).whenValue

      //THEN
      response.status mustBe INTERNAL_SERVER_ERROR
      response.header(CONTENT_TYPE) mustBe Some("application/json; charset=utf-8")
      response.body mustBe """{"message":"Duplicate product"}"""
    }
  }
}
