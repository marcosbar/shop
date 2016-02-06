import com.shop.db.model.ProductEntity
import com.shop.db.persistence.mongo.MongoProductRepository
import common.{FutureHelper, TestProduct}
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.libs.json.Json
import play.api.mvc

import play.api.test._
import play.api.test.Helpers._

import scala.concurrent.Future

@RunWith(classOf[JUnitRunner])
class ProductControllerFunc extends Specification with FutureHelper{

  val productRepository = new MongoProductRepository

  "Product Controller" should {

    "create a product when requested" in new WithServer{
      //GIVEN
      val product = TestProduct("1","name")
      val expectedProduct = ProductEntity("1","name")

      //WHEN
      val result: Option[Future[mvc.Result]] = route(FakeRequest(PUT, "/product").withJsonBody(Json.toJson(product)))

      //THEN
      status(result.get) === CREATED
      whenValue(productRepository.findAll()).head === expectedProduct
    }

    "render the index page" in new WithApplication{
      val home = route(FakeRequest(GET, "/")).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "text/html")
      contentAsString(home) must contain ("Your new com.shop.application is ready.")
    }
  }
}
