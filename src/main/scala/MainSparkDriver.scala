package spark.starter

import org.apache.spark._
import SparkContext._

object MainSparkDriver {
  def main(args: Array[String]) {
   val sc  = new SparkContext("spark://master:7077", "SparkStarter", System.getenv("SPARK_HOME"), List("target/scala-2.9.3/spark-starter-assembly-0.1.jar"))
    val num_task = 2
    val n = 100000
    val count = sc.parallelize(1 to n, num_task).map { i => i * i 
    }.reduce(_ + _)
    println("Sum of square: " + count)
    System.exit(0)
  }
}
