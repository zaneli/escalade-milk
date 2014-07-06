name := "escalade-milk"

version := "0.0.1"

scalaVersion := "2.11.1"

organization := "com.zaneli"

crossScalaVersions := Seq("2.10.4", "2.11.1")

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:implicitConversions")

libraryDependencies ++= {
  Seq(
    "com.github.nscala-time" %% "nscala-time" % "1.2.0" % "compile",
    "junit" % "junit" % "4.11" % "test",
    "org.mockito" % "mockito-core" % "1.9.5" % "test",
    "org.specs2" %% "specs2" % "2.3.12" % "test"
  )
}

libraryDependencies <++=  scalaVersion { sv =>
  if (sv.startsWith("2.11")) {
    Seq("org.scala-lang.modules" %% "scala-xml" % "1.0.2")
  } else {
    Nil
  }
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath + "/.m2/repository")))
