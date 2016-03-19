package com.shop.controllers

import javax.inject._

import play.api.http.DefaultHttpErrorHandler
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import play.api.mvc.Results._
import play.api.routing.Router
import scala.concurrent._

/**
  * Created by mgb on 19/03/2016.
  */
class ErrorHandler @Inject() (
                               env: Environment,
                               config: Configuration,
                               sourceMapper: OptionalSourceMapper,
                               router: Provider[Router]
                             ) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {

  implicit val format = Json.writes[ErrorModel]

  override def onServerError(request: RequestHeader, exception: Throwable):Future[Result] = {
    Future.successful(InternalServerError(Json.toJson(ErrorModel(exception.getMessage))))
  }

}

case class ErrorModel(message: String)

