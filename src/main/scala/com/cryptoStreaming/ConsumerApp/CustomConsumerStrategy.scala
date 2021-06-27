package com.cryptoStreaming.ConsumerApp

import org.apache.kafka.clients.consumer.{Consumer, KafkaConsumer}
import org.apache.kafka.common.TopicPartition
import org.apache.log4j.{Level, Logger}
import org.apache.spark.streaming.kafka010.ConsumerStrategy

import java.{lang, util}
import collection.JavaConverters._

class CustomConsumerStrategy[K, V](
    topics: Iterable[String],
    kafkaParam: Map[String, Object]
) extends ConsumerStrategy[K, V] {
  val logger = Logger.getLogger(getClass)
  logger.setLevel(Level.INFO)
  private val consumer = new KafkaConsumer[K, V](executorKafkaParams)
  override def executorKafkaParams: util.Map[String, Object] =
    kafkaParam.asJava

  override def onStart(
      currentOffsets: util.Map[TopicPartition, lang.Long]
  ): Consumer[K, V] = {
    consumer.subscribe(topics.asJavaCollection, new Listner(consumer))
    consumer
  }

}
