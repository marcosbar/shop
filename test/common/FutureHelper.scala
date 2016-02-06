package common

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

/**
 * Created by marcos on 11/01/16.
 */
trait FutureHelper {
  implicit class FutureHelper[R](future: Future[R]){
    def whenValue: R = Await.result(future, 5 seconds)

    def waitUntilDone: Unit = Await.ready(future, 5 seconds)
  }
}
