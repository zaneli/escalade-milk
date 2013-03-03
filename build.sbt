name := "escalade-milk"

version := "0.0.1"

scalaVersion := "2.9.2"

organization := "com.zaneli"

scalacOptions ++= Seq("-unchecked", "-deprecation")

libraryDependencies ++= {
  Seq(
    "org.scalaj" %% "scalaj-time" % "0.6" % "compile",
    "junit" % "junit" % "4.11" % "test",
    "org.mockito" % "mockito-core" % "1.9.5" % "test",
    "org.specs2" %% "specs2" % "1.12.3" % "test"
  )
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

publishTo := Some(Resolver.file(
  "file", new java.io.File(System.getProperty("repository.url", ""))))