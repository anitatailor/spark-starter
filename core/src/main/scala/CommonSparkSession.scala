package spark.starter.core

import org.apache.spark.sql.SparkSession

trait CommonSparkSession {
  lazy val spark: SparkSession = {
    SparkSession
      .builder()
      .appName("ETL Driver")
      .config("spark.sql.files.ignoreCorruptFiles", true)
      .getOrCreate()
  }
}
