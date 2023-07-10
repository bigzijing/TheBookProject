scalaVersion := "2.13.11"
organization := "com.zij.tbp"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "2.0.2",
  "com.github.ghostdogpr" %% "caliban" % "2.0.2",
  "com.github.ghostdogpr" %% "caliban-zio-http" % "2.0.2",
  "io.d11" %% "zhttp" % "2.0.0-RC10",
  "com.leadiq" %% "reactivemongo-zio" % "2.0.0-RC6",
  "dev.zio" %% "zio-logging" % "2.1.13",
  "dev.zio" %% "zio-logging-slf4j" % "2.1.13",
  "dev.zio" %% "zio-logging-slf4j-bridge" % "2.1.13",
  "com.beachape" %% "enumeratum" % "1.7.2",
  "com.github.tototoshi" %% "scala-csv" % "1.3.10",
  "io.scalaland" %% "chimney" % "0.7.5"
)