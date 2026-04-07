package common

import org.apache.kafka.clients.producer._
import java.util.Properties

class KafkaProducerService {

  private val props = new Properties()

  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  private val producer = new KafkaProducer[String, String](props)

  def send(topic: String, key: String, value: String): Unit = {
    val record = new ProducerRecord[String, String](topic, key, value)
    producer.send(record)
    println(s"[PRODUCER] Sent to $topic: $value")
  }
}