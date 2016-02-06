package com.shop.db.persistence.mongo

import com.shop.db.persistence.PersistenceRepository
import play.api.Play._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json.JSONSerializationPack.{Reader, Writer}
import play.modules.reactivemongo.json._
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future

/**
 * Created by marcos on 29/11/15.
 */
abstract class MongoPersistenceRepository[A, B](implicit writer: Writer[B], reader: Reader[B]) extends PersistenceRepository[A, B] {

  lazy val reactiveMongoApi = current.injector.instanceOf[ReactiveMongoApi]
  val collection: JSONCollection

  override def save(model: B): Future[Unit] = {
    collection.insert(model).map(validateResult(_))
  }

  override def update(id: A, model: B): Future[Unit] = {
    collection.update(Json.obj("_id" -> id.toString), model).map(validateResult(_))
  }

  override def delete(id: A): Future[Unit] = {
    collection.remove(Json.obj("_id" -> id.toString)).map(validateResult(_))
  }

  override def findById(id: A): Future[Option[B]] = {
    collection.find(Json.obj("_id" -> id.toString)).one[B]
  }

  override def findAll(maxResults: Int = 100): Future[List[B]] = {
    collection.find(Json.obj()).cursor[B]().collect[List](upTo = maxResults)
  }

  private def validateResult(writeResult: WriteResult): Unit = {
    writeResult match {
      case writeResult if writeResult.ok == true =>
      case writeResult => throw writeResult.getCause
    }
  }
}
