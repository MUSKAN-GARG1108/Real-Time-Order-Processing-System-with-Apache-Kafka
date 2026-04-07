package notification

import org.apache.kafka.clients.consumer._
import java.util.{Collections, Properties}
import scala.jdk.CollectionConverters._

object NotificationConsumer extends App {

  val props = new Properties()

  props.put("bootstrap.servers", "localhost:9092")
  props.put("group.id", "notification-group")
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("auto.offset.reset", "earliest")

  val consumer = new KafkaConsumer[String, String](props)

  consumer.subscribe(Collections.singletonList("payments"))

  println("Notification Service Started...")

  while (true) {
    val records = consumer.poll(java.time.Duration.ofMillis(1000))

    for (record <- records.asScala) {

      println(s"Payment Update Received: ${record.value()}")

      val orderId = record.key()

      // Simulate notification
      println(s"Sending notification for Order: $orderId")

      Thread.sleep(500)

      println(s"Notification Sent for Order: $orderId")
    }
  }
}