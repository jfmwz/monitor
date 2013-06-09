resolvers ++= Seq(
	"Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
	"Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
)

addSbtPlugin("com.typesafe.akka" % "akka-sbt-plugin" % "2.0.2")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.2.0-SNAPSHOT")

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.1.0")