scalaVersion := "2.13.11"
organization := "com.zij.tbp"

libraryDependencies ++= Seq(
  "dev.zio"                     %% "zio"                      % "2.1.20",
  "dev.zio"                     %% "zio-http"                 % "3.3.2",
  "dev.zio"                     %% "zio-logging"              % "2.5.1",
  "dev.zio"                     %% "zio-logging-slf4j"        % "2.5.1",
  "dev.zio"                     %% "zio-logging-slf4j-bridge" % "2.5.1",
  "com.github.ghostdogpr"       %% "caliban"                  % "2.11.1",
  "com.github.ghostdogpr"       %% "caliban-quick"            % "2.11.1",
  "com.softwaremill.sttp.tapir" %% "tapir-json-circe"         % "1.4.0",
  "com.leadiq"                  %% "reactivemongo-zio"        % "2.0.0-RC13",
  "com.beachape"                %% "enumeratum"               % "1.9.0",
  "com.github.tototoshi"        %% "scala-csv"                % "1.4.1",
  "io.scalaland"                %% "chimney"                  % "1.7.3",
  "org.typelevel"               %% "cats-core"                % "2.13.0"
)
