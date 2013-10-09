set SPARK_HOME to path where spark is installed on your cluster
In MainSparkDriver.scala change "master" to the host name of Master of Spark cluster 
run following commands
sbt/sbt assembly
sbt/sbt run
