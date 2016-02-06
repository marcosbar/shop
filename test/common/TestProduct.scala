package common

import play.api.libs.json.Json

/**
 * Created by marcos on 10/01/16.
 */
case class TestProduct(_id : String, description: String)

object TestProduct{
  implicit val productFormat = Json.format[TestProduct]
}
