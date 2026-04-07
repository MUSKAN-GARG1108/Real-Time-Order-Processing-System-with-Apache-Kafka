package payment

import spray.json._
import common.PaymentEvent

trait PaymentJsonProtocol extends DefaultJsonProtocol {
  implicit val paymentFormat = jsonFormat4(PaymentEvent)
}