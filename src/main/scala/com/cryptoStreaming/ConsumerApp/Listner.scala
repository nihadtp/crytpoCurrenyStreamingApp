package com.cryptoStreaming.ConsumerApp

import org.apache.kafka.clients.consumer.{Consumer, ConsumerRebalanceListener}
import org.apache.kafka.common.TopicPartition
import org.apache.log4j.{Level, Logger}

import java.util
import collection.JavaConverters._

class Listner[K, V](consumer: Consumer[K, V])
    extends ConsumerRebalanceListener {
  val logger = Logger.getLogger(getClass)
  logger.setLevel(Level.INFO)
  override def onPartitionsAssigned(
      partitions: util.Collection[TopicPartition]
  ): Unit = {
    val topics =
      partitions.asScala
        .map(x => s"${x.topic()} at ${x.partition().toString}")
        .mkString(", ")
    logger.info(s"New Partitions Assigned for ${topics}")
  }

  override def onPartitionsRevoked(
      partitions: util.Collection[TopicPartition]
  ): Unit = {
    val topic_partition = partitions.asScala
      .map(x => {
        val topic = x.topic()
        val partitionNum = x.partition().toString
        s"${topic} at ${partitionNum}"
      })
      .mkString(", ")
    try {
      consumer.commitSync()
      logger.info(s"Committing ${topic_partition} before rebalancing")
    } catch {
      case e: Exception =>
        logger.error(s"Commit failed at ${topic_partition} when rebalancing")
        e.printStackTrace()
    }
  }
}
