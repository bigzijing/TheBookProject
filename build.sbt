scalaVersion := "2.13.11"
organization := "com.zij.tbp"

libraryDependencies ++= Seq(
  "dev.zio"                     %% "zio"                      % "2.0.15",
  "dev.zio"                     %% "zio-http"                 % "3.0.0-RC1",
  "dev.zio"                     %% "zio-logging"              % "2.1.13",
  "dev.zio"                     %% "zio-logging-slf4j"        % "2.1.13",
  "dev.zio"                     %% "zio-logging-slf4j-bridge" % "2.1.13",
  "com.github.ghostdogpr"       %% "caliban"                  % "2.2.1",
  "com.github.ghostdogpr"       %% "caliban-zio-http"         % "2.2.1",
  "com.softwaremill.sttp.tapir" %% "tapir-json-circe"         % "1.4.0",
  "com.leadiq"                  %% "reactivemongo-zio"        % "2.0.0-RC6",
  "com.beachape"                %% "enumeratum"               % "1.7.2",
  "com.github.tototoshi"        %% "scala-csv"                % "1.3.10",
  "io.scalaland"                %% "chimney"                  % "0.7.5",
  "org.typelevel"               %% "cats-core"                % "2.9.0"
)
