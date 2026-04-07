package order

import spray.json._
import common.OrderEvent

trait OrderJsonProtocol extends DefaultJsonProtocol {
  implicit val orderFormat = jsonFormat4(OrderEvent)
}