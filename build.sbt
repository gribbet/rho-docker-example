import sbt.Keys._
import sbt._
import sbtdocker._

val http4sVersion = "0.17.0"

lazy val project = Project(
  id = "rho-docker-example",
  base = file("."))
  .settings(
    version := "1.0.0",
    organization := "example",
    scalaVersion := "2.12.4",
    scalacOptions := Seq("-unchecked", "-deprecation"),
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-blaze-server" % http4sVersion,
      "org.http4s" %% "rho-swagger" % http4sVersion,
      "org.slf4j" % "slf4j-simple" % "1.7.25"),
    dockerfile in docker := {
      val artifact = assembly.value
      val artifactTargetPath = s"/app/${artifact.name}"

      new Dockerfile {
        from("alpine:3.6")
        run("apk", "add", "--update", "openjdk8")
        add(artifact, artifactTargetPath)
        entryPoint("java", "-jar", artifactTargetPath)
        expose(8080)
      }
    })
  .enablePlugins(DockerPlugin)
