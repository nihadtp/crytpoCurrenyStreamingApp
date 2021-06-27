package com.cryptoStreaming.ConsumerApp

import com.cryptoStreaming.ConsumerApp.Utils._
import org.apache.log4j.{Level, Logger}
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.{
  CanCommitOffsets,
  HasOffsetRanges,
  KafkaUtils,
  OffsetRange
}
import org.apache.spark.TaskContext

import scala.util.{Failure, Success, Try}

object producerApp {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache").setLevel(Level.WARN)

    val ssc = createStreamingContext()
    val topics = Set("bitcoin", "etherium")

    val stream = Try {
      KafkaUtils.createDirectStream[String, String](
        ssc,
        PreferConsistent,
        new CustomConsumerStrategy[String, String](topics, kafkaParams)
      )
    } match {
      case Success(value) => Some(value)
      case Failure(exception) => {
        exception.printStackTrace()
        System.exit(0)
        None
      }
    }
//    stream.get.foreachRDD { rdd =>
//      val offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
//      rdd.foreachPartition { iter =>
//        val o: OffsetRange = offsetRanges(TaskContext.get.partitionId)
//        iter.foreach(x => {
//          val data = new data(x)
//          println(data.toString)
//        })
//      }
//      stream.get.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
//    }

    stream.foreach(
      x =>
        x.foreachRDD { rdd =>
          val offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
          rdd.foreachPartition { iter =>
            val o: OffsetRange = offsetRanges(TaskContext.get.partitionId)
            iter.foreach(x => {
              val data = new data(x)
              println(data.toString)
            })
          }
          x.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
        }
    )

    ssc.start()
    ssc.awaitTermination()
  }
}
