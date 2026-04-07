package payment

import org.apache.kafka.clients.consumer._
import java.util.{Collections, Properties}
import scala.jdk.CollectionConverters._

import common.{KafkaProducerService, PaymentEvent}
import spray.json._
import java.time.Instant

object PaymentConsumer extends App with PaymentJsonProtocol{

  val consumerProps = new Properties()
  consumerProps.put("bootstrap.servers", "localhost:9092")
  consumerProps.put("group.id", "payment-group")
  consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  consumerProps.put("auto.offset.reset", "earliest")

  val consumer = new KafkaConsumer[String, String](consumerProps)
  consumer.subscribe(Collections.singletonList("orders"))

  val producer = new KafkaProducerService()

  println("Payment Service Started...")

  while (true) {
    val records = consumer.poll(java.time.Duration.ofMillis(1000))

    for (record <- records.asScala) {

      println(s"Received Order: ${record.value()}")

      val orderId = record.key()

      // Simulate payment processing
      println("Processing Payment...")
      Thread.sleep(1000)

      val paymentEvent = PaymentEvent(
        orderId = orderId,
        status = "PAYMENT_SUCCESS",
        amount = 500.0,
        timestamp = Instant.now().toString
      )

      val paymentJson = paymentEvent.toJson.toString()

      producer.send("payments", orderId, paymentJson)

      println(s"Payment Event Sent for Order: $orderId")
    }
  }
}