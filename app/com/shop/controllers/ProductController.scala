package com.shop.controllers

import javax.inject.Inject

import com.shop.application.ProductApplication
import com.shop.db.model.ProductEntity
import play.api.libs.json.Json
import play.api.mvc.Action
import play.api.mvc.Results._

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by marcos on 13/12/15.
 */
class ProductController @Inject()(productApplication: ProductApplication) {
  def findProduct(id: String) = Action.async { request =>
    productApplication.findProductById(id).map{
      case Some(product:ProductEntity) => Ok(Json.toJson(product))
      case _ => NotFound
    }
  }

  def saveProduct() = Action.async{ request =>
    productApplication.saveProduct(Json.fromJson[ProductEntity](request.body.asJson.get).get).map(_ => Created)
  }
}
