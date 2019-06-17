package spark.starter.core

trait CommonJob {

  /** Configure application arguments */
  def configure(startTime: String, endTime: String)

  /** start application */
  def start()
}
