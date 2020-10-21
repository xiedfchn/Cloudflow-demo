
lazy val sensorData =  (project in file("."))
  .enablePlugins(CloudflowApplicationPlugin, CloudflowAkkaPlugin)
  .settings(
    runLocalConfigFile := Some("src/main/resources/local.conf"),
    runLocalLog4jConfigFile := Some("src/main/resources/log4j.xml"),
    name := "sensor-data-scala",
    scalaVersion := "2.12.11",
    version := "0.1",

    libraryDependencies ++= Seq(
      "com.lightbend.akka"     %% "akka-stream-alpakka-file"  % "1.1.2",
      "com.typesafe.akka"      %% "akka-http-spray-json"      % "10.1.12",
      "ch.qos.logback"         %  "logback-classic"           % "1.2.3",
      "com.typesafe.akka"      %% "akka-http-testkit"         % "10.1.12" % "test",
      "org.scalatest"          %% "scalatest"                 % "3.0.8"  % "test"
    )
  )