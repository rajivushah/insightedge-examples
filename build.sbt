name := "insightedge-examples"

version := "1.1.0-SNAPSHOT"

scalaVersion := "2.10.6"

val insightEdgeVersion = "1.1.0-SNAPSHOT"

resolvers += Resolver.mavenLocal
resolvers += "Openspaces Maven Repository" at "http://maven-repository.openspaces.org"

libraryDependencies ++= Seq(
  "org.gigaspaces.insightedge" % "insightedge-core" % insightEdgeVersion % "provided" exclude("javax.jms", "jms"),
  "org.gigaspaces.insightedge" % "insightedge-scala" % insightEdgeVersion % "provided" exclude("javax.jms", "jms"),
  "org.apache.spark" %% "spark-streaming-twitter" % "1.6.0",
  "org.scalatest" %% "scalatest" % "2.0" % "test"
)

test in assembly := {}

assemblyOutputPath in assembly := new File("target/insightedge-examples.jar")

assemblyMergeStrategy in assembly := {
  case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") => MergeStrategy.first
  case x => (assemblyMergeStrategy in assembly).value(x)
}

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)