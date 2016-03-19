package com.shop.common.json

import play.api.libs.json.{Json, Reads}
import play.api.mvc.Results._
import play.api.mvc.{AnyContent, Request}

import scala.concurrent.Future

/**
  * Created by mgb on 06/02/2016.
  */
trait WithJsonBody {
    def parse[B](f: B => Future[play.api.mvc.Result])(implicit request: Request[AnyContent], reads: Reads[B]): Future[play.api.mvc.Result] = {
      request.body.asJson match {
        case Some(value) => f(Json.fromJson[B](value).get)
        case None => Future.successful(BadRequest)
      }
    }
}
