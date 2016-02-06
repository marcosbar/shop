package com.shop.db.model

import play.api.libs.json.{Writes, OWrites, Json}
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
 * Created by marcos on 29/11/15.
 */

case class ProductEntity(_id : String, description: String)

object ProductEntity{
  implicit val productFormat = Json.format[ProductEntity]

  implicit val productWrites: OWrites[ProductEntity] = (
  (JsPath \ "_id").write[String] and
  (JsPath \ "description").write[String]
  )(unlift(ProductEntity.unapply))

}
