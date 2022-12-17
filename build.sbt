ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "rules-engine-poc",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.2",
      "io.github.jamsesso" % "json-logic-java" % "1.0.7",
      "io.circe" %% "circe-core" % "0.14.3",
      "io.circe" %% "circe-parser" % "0.14.3",
      "io.circe" %% "circe-generic" % "0.14.3",
      "org.scalatest" %% "scalatest" % "3.2.14" % Test
    )
  )
