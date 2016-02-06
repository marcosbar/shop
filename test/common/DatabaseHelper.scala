package common

import com.shop.db.model.ProductEntity
import com.shop.db.persistence.mongo.MongoProductRepository

/**
  * Created by mgb on 06/02/2016.
  */
trait DatabaseHelper {
  //val reactive: ReactiveMongoApi = ???
  lazy val productRepository = new MongoProductRepository
  //lazy val reactiveMongoApi = current.injector.instanceOf[ReactiveMongoApi]

  def createProduct(id:String = "1") = productRepository.save(ProductEntity(id,s"description ${id}"))
}
