ThisBuild / scalaVersion := "2.11.12"
ThisBuild / version := "0.2"
ThisBuild / organization := "spark.starter"
ThisBuild / organizationName := ""
ThisBuild / name := "etl"

lazy val sparkVersion = "2.4.2"

lazy val dsetl = (project in file(".")).settings(assemblySettings)
  .aggregate(core,jobs,app)

/*
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion,
  "commons-cli" % "commons-cli" % "1.4",
  "com.typesafe" % "config" % "1.3.3"
)
*/

lazy val sparkDependencies = Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion %  Provided,
  "org.apache.spark" %% "spark-sql" % sparkVersion % Provided
)
lazy val commonDependencies = Seq(
  "com.typesafe" % "config" % "1.3.3"
)

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)


scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-encoding",
  "utf-8", // Specify character encoding used by source files.
  "-explaintypes", // Explain type errors in more detail.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
  "-language:experimental.macros", // Allow macro definition (besides implementation and application)
  "-language:higherKinds", // Allow higher-kinded types
  "-language:implicitConversions", // Allow definition of implicit functions called views
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
//  "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Xfuture", // Turn on future language features.
  "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
  "-Xlint:by-name-right-associative", // By-name parameter of right associative operator.
  "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
  "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
  "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
  "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
  "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
  "-Xlint:nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
  "-Xlint:option-implicit", // Option.apply used implicit view.
  "-Xlint:package-object-classes", // Class or object defined in package object.
  "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
  "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
  "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
  "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
  "-Xlint:unsound-match", // Pattern match may not be typesafe.
  "-Yno-adapted-args", // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
  "-Ypartial-unification", // Enable partial unification in type constructor inference
  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
  "-Ywarn-infer-any", // Warn when a type argument is inferred to be `Any`.
  "-Ywarn-nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Ywarn-nullary-unit", // Warn when nullary methods return Unit.
  "-Ywarn-numeric-widen", // Warn when numerics are widened.
  "-Ywarn-value-discard" // Warn when non-Unit expression results are unused.
)

wartremoverWarnings in (Compile, compile) ++= Warts.unsafe

wartremoverWarnings in (Compile, compile) ++= Seq(
  ContribWart.Apply,
  ContribWart.ExposedTuples,
  ContribWart.MissingOverride,
  ContribWart.NoNeedForMonad,
  ContribWart.OldTime,
  ContribWart.SealedCaseClass,
  ContribWart.SomeApply,
  ContribWart.SymbolicName,
  ContribWart.UnintendedLaziness,
  ContribWart.UnsafeInheritance
)

scapegoatVersion in ThisBuild := "1.3.8"

scalacOptions in (Compile, console) --= Seq("-Ywarn-unused:imports", "-Xfatal-warnings")

resolvers ++= Seq(
  Resolver.typesafeRepo("releases"),
  Resolver.sonatypeRepo("public"),
  Resolver.bintrayRepo("spark-packages", "maven"),
  "Artima Maven Repository" at "https://repo.artima.com/releases",
  "MavenRepository" at "https://mvnrepository.com/"
)

lazy val core = (project in file("core")).disablePlugins(AssemblyPlugin).settings(
	name:= "core",
	settings,
	libraryDependencies ++= commonDependencies ++ sparkDependencies
	)

lazy val jobs = (project in file("jobs")).disablePlugins(AssemblyPlugin).settings(
	name:= "jobs",
        settings,
        libraryDependencies ++= commonDependencies ++ sparkDependencies
        ).dependsOn(core)

lazy val app = (project in file("app")).settings(
	name:= "app",
        settings,
        libraryDependencies ++= commonDependencies ++ sparkDependencies
        ).dependsOn(core).dependsOn(jobs)

lazy val assemblySettings = Seq(
  assemblyJarName in assembly := name.value + ".jar",
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case _                             => MergeStrategy.first
    case "application.conf"            => MergeStrategy.concat
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  }

)
lazy val compilerOptions = Seq(
  "-unchecked",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-deprecation",
  "-encoding",
  "utf8"
)

lazy val commonSettings = Seq(
  scalacOptions ++= compilerOptions,
  resolvers ++= Seq(
  Resolver.typesafeRepo("releases"),
  Resolver.sonatypeRepo("public"),
  Resolver.bintrayRepo("spark-packages", "maven"),
  "Artima Maven Repository" at "https://repo.artima.com/releases",
  "MavenRepository" at "https://mvnrepository.com/"
)
)

lazy val settings = commonSettings
//lazy val compileScalastyle = taskKey[Unit]("compileScalastyle ")

//compileScalastyle := scalastyle.in(Compile).toTask("").value

// libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime
// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
