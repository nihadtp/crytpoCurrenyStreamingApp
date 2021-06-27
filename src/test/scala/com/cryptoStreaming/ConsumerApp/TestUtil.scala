package com.cryptoStreaming.ConsumerApp

import org.apache.kafka.clients.consumer.ConsumerRecord

import scala.io.Source
import scala.util.{Failure, Success, Try}

object TestUtil {
  def getFile(filename: String): Option[String] = {
    Try {
      val input = this.getClass.getResourceAsStream(s"/${filename}")
      input
    } match {
      case Success(value) => {
        val file = Source.fromInputStream(value).getLines().mkString
        Some(file)
      }
      case Failure(exception) => {
        println(s"Check the file ${filename} exists in your resources folder")
        exception.printStackTrace()
        None
      }
    }
  }

  def getConsumerRecord(filename: String): ConsumerRecord[String, String] =
    new ConsumerRecord[String, String](
      "test-topic",
      1,
      1L,
      null,
      getFile(filename).get
    )
}
