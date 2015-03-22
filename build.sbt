name := "typesuffering"

version := "0.0.1"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test" withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % "1.12.2" % "test" withSources() withJavadoc(),
  "com.chuusai" %% "shapeless" % "2.1.0"
)


