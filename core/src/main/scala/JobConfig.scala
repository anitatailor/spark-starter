package spark.starter.core
import scala.collection.JavaConversions._
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

class JobConf(config_file: String) {

  val conf = ConfigFactory.load(config_file)

  def getConfig(uri: String): String =
    conf.getString(uri)

  def getConfigListOrElse(uri: String): java.util.List[_ <: Config] =
    // returns Java list which is converted to scala list by import JavaConversions
    try {
      conf.getConfigList(uri)
    } catch {
      case _: Throwable => java.util.Collections.emptyList()
    }

  def getConfigOrElse(uri: String, defaultVal: String): String =
    try {
      conf.getString(uri)
    } catch {
      case _: Throwable => defaultVal
    }
}
