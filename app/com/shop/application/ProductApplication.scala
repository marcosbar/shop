package com.shop.application

import javax.inject.Inject

import com.shop.common.exceptions.BadRequestException
import com.shop.db.model.ProductEntity
import com.shop.db.persistence.mongo.MongoProductRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by marcos on 29/11/15.
 */
class ProductApplication @Inject()(mongoProductRepository: MongoProductRepository){
  def saveProduct(product: ProductEntity): Future[Unit] = {
    //if a race condition happens the second insert will get a internal error
    mongoProductRepository.findById(product._id).map{
      case Some(_) => throw BadRequestException("Duplicate product")
      case None => mongoProductRepository.save(product)
    }
  }


  def findProductById(id:String): Future[Option[Product]] ={
    mongoProductRepository.findById(id)
  }
}
