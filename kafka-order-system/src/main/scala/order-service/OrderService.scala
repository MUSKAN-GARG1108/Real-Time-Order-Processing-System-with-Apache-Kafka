package order

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import common.{KafkaProducerService, OrderEvent}
import spray.json._
import java.time.Instant

class OrderService(producer: KafkaProducerService) extends OrderJsonProtocol {

  val route: Route =
    path("order") {
      post {
        entity(as[String]) { body =>

          val order = body.parseJson.convertTo[OrderEvent]

          val event = order.copy(
            status = "ORDER_PLACED",
            timestamp = Instant.now().toString
          )

          producer.send("orders", event.orderId, event.toJson.toString())

          complete(s"Order ${event.orderId} sent to Kafka")
        }
      }
    }
}