package com.cryptoStreaming.ConsumerApp

import com.cryptoStreaming.ConsumerApp.TestUtil.{getConsumerRecord}

class dataSpec extends ProducerSpec {

  def fixture = new {
    val invalidMessage = getConsumerRecord("invalid.json")
    val validMessage = getConsumerRecord("valid.json")
  }
  "Invalid json" should "return false" in {
    val f = fixture
    val msg = f.invalidMessage
    val data = new data(msg)
    println(msg)
    assert(data.isValid() === false)
  }

  "Valid json" should "return true" in {
    val f = fixture
    val msg = f.validMessage
    val data = new data(msg)
    println(msg)
    assert(data.isValid() === true)
  }

  "Generate messages payload" should "equal to original if original does not have payload key" in {
    val f = fixture
    val msg = f.invalidMessage
    val data = new data(msg)
    assert(data.getPayload() == data.parsedPayload)
  }
  "Generate messages payload" should "not equal to original if original have payload key" in {
    val f = fixture
    val msg = f.validMessage
    val data = new data(msg)
    assert(data.getPayload() != data.parsedPayload)
  }
}
