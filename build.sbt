name := "CryptoStreaming"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies += "org.json4s" %% "json4s-native" % "3.5.5"
libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.5.3"
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.6"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.4.6"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.4.7"
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.4.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % "test"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.9"
libraryDependencies += "joda-time" % "joda-time" % "2.9.5"
libraryDependencies += "org.joda" % "joda-convert" % "1.9"

libraryDependencies += "org.scalamock" %% "scalamock" % "4.3.0" % Test

dependencyOverrides += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.8.8"
