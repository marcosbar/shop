package com.shop.application

import javax.inject.Inject

import com.shop.db.model.ProductEntity
import com.shop.db.persistence.mongo.MongoProductRepository
import play.api.libs.iteratee.Enumeratee
import play.api.libs.json.JsValue
import play.api.mvc.Result

import scala.concurrent.Future

/**
 * Created by marcos on 29/11/15.
 */
class ProductApplication @Inject()(mongoProductRepository: MongoProductRepository){
  def saveProduct(product: ProductEntity): Future[Unit] = mongoProductRepository.save(product)


  def findProductById(id:String): Future[Option[Product]] ={
    mongoProductRepository.findById(id)
  }
}
