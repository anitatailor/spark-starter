package spark.starter.jobs
import java.time.LocalDate

import org.apache.spark.sql.functions._
import spark.starter.core.CommonSparkSession
import spark.starter.core.CommonJob
import org.apache.spark.sql.Row
import org.apache.spark.sql.types._
import scala.collection.JavaConversions._

class SparkExample extends CommonSparkSession with CommonJob {

  private var startDate = LocalDate.now.toString
  private var endDate = LocalDate.now.toString
  import spark.implicits._

  def configure(startTime: String, endTime: String) = {
    startDate = startTime
    endDate = endTime
  }

  def start() = {
    val data = Seq(Row("Michael", "29"), Row("Andy", "30"), Row("Justin", "19"))
    val schemaString = "name age"
    val fields =
      schemaString.split(" ").map(fieldName => StructField(fieldName, StringType, nullable = true))

    val schema = StructType(fields)

    val df = spark.createDataFrame(data, schema)
    println("Total Elements in DF" + df.count())
  }
}
