package spark.starter.app
import java.io.Serializable
import spark.starter.core.CommonJob
import spark.starter.jobs.SparkExample

import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Options
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.ParseException
import org.apache.commons.cli.OptionBuilder

object MainDriver extends Serializable {

private var startTime: String = ""
  private var appName: String = ""
  private var endTime: String = ""

  def main(args: Array[String]): Unit = {

    try {
      val opt: Options = new Options()
      opt.addOption("help", false, "Print help for this application")
      opt.addOption("appName", true, "Application Name to be started")
      opt.addOption("startTime", true, "Application Config file name")
      opt.addOption("endTime", true, "Application specific Spark Properies file")

      val parser: DefaultParser = new DefaultParser()
      val cl: CommandLine = parser.parse(opt, args)

      if (cl.hasOption("help")) {
        val f: HelpFormatter = new HelpFormatter()
        f.printHelp("Driver Params:", opt)
        System.exit(0)
      } else if (cl.hasOption("appName") && cl.hasOption("startTime") && cl.hasOption("endTime")) {
 appName = cl.getOptionValue("appName")
        startTime = cl.getOptionValue("startTime")
        endTime = cl.getOptionValue("endTime")
        // print the value of block-size
        println(appName)
        println(startTime)
        println(endTime)

      } else {
        println("Incorrect no of Arguments, Please run with -help option to know usages")
        System.exit(0)
      }
    } catch {
      case e: ParseException => e.printStackTrace()
    }
    val job: CommonJob = appName match {
      case "example"   => new SparkExample()
    }
    job.configure(startTime, endTime)
    job.start()
    println("Application Successful")
  }
}
