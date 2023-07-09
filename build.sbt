scalaVersion := "2.13.11"
organization := "com.zij.tbp"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "2.0.2",
  "com.github.ghostdogpr" %% "caliban" % "2.0.2",
  "com.github.ghostdogpr" %% "caliban-zio-http" % "2.0.2",
  "io.d11" %% "zhttp" % "2.0.0-RC10"
)