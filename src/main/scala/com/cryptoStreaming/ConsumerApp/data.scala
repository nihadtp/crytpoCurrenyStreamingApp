package com.cryptoStreaming.ConsumerApp

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.joda.time.DateTime
import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._

class data(consumerRecord: ConsumerRecord[String, String])
    extends Serializable {

  private final val topic = consumerRecord.topic()
  private final val offset = consumerRecord.offset()
  private final val partition = consumerRecord.partition()
  private final val payloadValue = consumerRecord.value()
  private final val currentTime = DateTime
    .now()
    .toString("d MMMM yyyy HH:mm:ss")
  val parsedPayload = parse(payloadValue)

  var parsedJValue = generate()
  def isValid(): Boolean = parsedPayload match {
    case JObject(v) => v.toMap.keys.toList == List("metadata", "payload")
    case _          => false
  }

  def generate(): JValue = {
    if (!isValid()) {
      val json = ("metadata" -> ("receipt_time" -> currentTime) ~
        ("from_topic" -> topic) ~ ("partition_number" -> partition) ~ ("offset" -> offset)) ~
        ("payload" -> parsedPayload)
      parsedJValue = render(json)
      render(json)
    } else parsedJValue
  }

  def getPayload(): JValue = render((parsedJValue \ "payload"))
  def getMetadata(): JValue = render((parsedJValue \ "metadata"))

  override def toString: String = pretty(compact(render(parsedJValue)))
}
