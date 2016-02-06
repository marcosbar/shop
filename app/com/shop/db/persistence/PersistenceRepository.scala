package com.shop.db.persistence

import scala.concurrent.Future
import scala.util.Try

/**
 * Created by marcos on 29/11/15.
 */
trait PersistenceRepository[A, B] {

  /** throws exception if the model can't be saved
   *
   * @param model to save
   * @return future of unit
   */
  def save(model: B): Future[Unit]

  /** throws exception if the model can't be updated
   *
   * @param id identifier of the model to update
   * @param model to update
   * @return future of unit
   */
  def update(id: A, model: B): Future[Unit]

  /** throws exception if the document can't be deleted
   *
   * @param id identifier of the document to delete
   * @return future of unit
   */
  def delete(id: A): Future[Unit]

  def findById(id: A): Future[Option[B]]

  def findAll(maxResults: Int): Future[List[B]]

}
