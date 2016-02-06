package com.shop.db.persistence.mongo

import com.shop.db.model.ProductEntity
import com.shop.db.persistence.PersistenceRepository
import play.modules.reactivemongo.json.collection.JSONCollection

trait ProductPersistenceRepository extends PersistenceRepository[String, ProductEntity]
/**
 * Created by marcos on 29/11/15.
 */
class MongoProductRepository extends MongoPersistenceRepository[String,ProductEntity] with ProductPersistenceRepository{
  val collection = reactiveMongoApi.db.collection[JSONCollection]("products")
}
