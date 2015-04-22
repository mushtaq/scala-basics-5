name := "scala-basics-5"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.3.8",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
)

transitiveClassifiers in Global := Seq(Artifact.SourceClassifier)
