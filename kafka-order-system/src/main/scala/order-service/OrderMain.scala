package order

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.Materializer
import scala.concurrent.ExecutionContextExecutor
import common.KafkaProducerService
import scala.io.StdIn


object OrderMain extends App {

  implicit val system: ActorSystem = ActorSystem("order-system")
  implicit val ec: ExecutionContextExecutor = system.dispatcher
  implicit val materializer: Materializer = Materializer(system)

  val producer = new KafkaProducerService()
  val service = new OrderService(producer)

  Http().newServerAt("localhost", 8080).bind(service.route)

  println("Order Service running at http://localhost:8080")

  StdIn.readLine()
  system.terminate()
}