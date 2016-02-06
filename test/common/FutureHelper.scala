package common

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

/**
 * Created by marcos on 11/01/16.
 */
trait FutureHelper {
  implicit def whenValue[R](f: Future[R]): R = Await.result(f, 5 seconds)
}
