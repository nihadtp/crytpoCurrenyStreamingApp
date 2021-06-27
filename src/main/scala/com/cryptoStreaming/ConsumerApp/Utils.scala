package com.cryptoStreaming.ConsumerApp

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Utils {
  def createStreamingContext(): StreamingContext = {
    val conf =
      new SparkConf().setMaster("local[*]").setAppName("ConsumerApp")
    val ssc = new StreamingContext(conf, Seconds(1))
    ssc
  }

  val kafkaParams = Map[String, Object](
    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "localhost:9092, localhost:9093",
    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[
      StringDeserializer
    ],
    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> classOf[
      StringDeserializer
    ],
    ConsumerConfig.GROUP_ID_CONFIG -> "cg1",
    ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG -> (false: java.lang.Boolean),
    ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> "earliest"
  )

}
